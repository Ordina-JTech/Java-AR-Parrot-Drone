package nl.ordina.jtech.arjava.video;

import com.xuggle.xuggler.*;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import nl.ordina.jtech.arjava.communication.VideoChannel;

import java.awt.image.BufferedImage;

public class VideoReceiver implements Runnable {
    private VideoChannel videoChannel;
    private IContainer container;
    private boolean stopRequested;
    private ImageView imageView;

    public VideoReceiver() {
        videoChannel = new VideoChannel();
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void run() {
        stopRequested = false;
        videoChannel.connectToDrone();

        container = IContainer.make();
        if (container.open(videoChannel.getSocketInputStream(), null) < 0) {
            System.err.println("Could not open container.");
            return;
        }

        int numStreams = container.getNumStreams();

        int videoStreamId = -1;
        IStreamCoder videoCoder = null;
        for(int i = 0; i < numStreams; i++) {
            IStream stream = container.getStream(i);
            IStreamCoder coder = stream.getStreamCoder();

            if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
                videoStreamId = i;
                videoCoder = coder;
                break;
            }
        }
        if (videoStreamId == -1) {
            System.err.println("Could not find video stream in container.");
            return;
        }

        if (videoCoder.open() < 0) {
            System.err.println("Could not open video decoder for container.");
            return;
        }

        IVideoResampler resampler = null;
        if (videoCoder.getPixelType() != IPixelFormat.Type.BGR24)
        {
            resampler = IVideoResampler.make(videoCoder.getWidth(),
                    videoCoder.getHeight(), IPixelFormat.Type.BGR24,
                    videoCoder.getWidth(), videoCoder.getHeight(), videoCoder.getPixelType());
            if (resampler == null) {
                System.err.println("Could not create color space resampler.");
                return;
            }
        }

        IPacket packet = IPacket.make();
        long firstTimestampInStream = Global.NO_PTS;
        long systemClockStartTime = 0;
        while(container.readNextPacket(packet) >= 0 && !stopRequested) {
            if (packet.getStreamIndex() == videoStreamId) {
                IVideoPicture picture = IVideoPicture.make(videoCoder.getPixelType(),
                        videoCoder.getWidth(), videoCoder.getHeight());

                int offset = 0;
                while(offset < packet.getSize()) {
                    int bytesDecoded = videoCoder.decodeVideo(picture, packet, offset);
                    if (bytesDecoded < 0) {
                        System.err.println("Video decoding error.");
                        return;
                    }
                    offset += bytesDecoded;

                    if (picture.isComplete()) {
                        IVideoPicture newPic = picture;

                        if (resampler != null) {
                            newPic = IVideoPicture.make(resampler.getOutputPixelFormat(),
                                    picture.getWidth(), picture.getHeight());
                            if (resampler.resample(newPic, picture) < 0) {
                                System.err.println("Resampling error.");
                                return;
                            }
                        }
                        if (newPic.getPixelType() != IPixelFormat.Type.BGR24) {
                            System.err.println("Error decoding video as BGR24");
                            return;
                        }

                        if (firstTimestampInStream == Global.NO_PTS) {
                            firstTimestampInStream = picture.getTimeStamp();
                            systemClockStartTime = System.currentTimeMillis();
                        } else {
                            long systemClockCurrentTime = System.currentTimeMillis();
                            long millisecondsClockTimeSinceStartofVideo =
                                    systemClockCurrentTime - systemClockStartTime;

                            long millisecondsStreamTimeSinceStartOfVideo =
                                    (picture.getTimeStamp() - firstTimestampInStream)/1000;
                            final long millisecondsTolerance = 50;
                            final long millisecondsToSleep =
                                    (millisecondsStreamTimeSinceStartOfVideo -
                                            (millisecondsClockTimeSinceStartofVideo +
                                                    millisecondsTolerance));
                            if (millisecondsToSleep > 0) {
                                try {
                                    Thread.sleep(millisecondsToSleep);
                                } catch (InterruptedException e) {
                                    return;
                                }
                            }
                        }

                        final BufferedImage javaImage = Utils.videoPictureToImage(newPic);

                        Platform.runLater(new Runnable() {
                            public void run() {
                                imageView.setImage(SwingFXUtils.toFXImage(javaImage, null));
                            }
                        });
                    }
                }
            }
        }

        if (videoCoder != null) {
            videoCoder.close();
            videoCoder = null;
        }
        if (container !=null) {
            container.close();
            container = null;
        }

        videoChannel.disconnectFromDrone();
    }

    public void requestStop() {
        stopRequested = true;
    }
}
