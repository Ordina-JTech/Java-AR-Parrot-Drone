package nl.ordina.jtech.arjava.deeplearning;

import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.VGG16ImagePreProcessor;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.shade.jackson.databind.ObjectMapper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class DeepLearning implements Runnable {

    private static final int HEIGHT = 224;
    private static final int WIDTH = 224;
    private static final int CHANNELS = 3;

    /**
     * Computational graph for deep learning
     */
    private ComputationGraph computationGraphVGG16;
    private NativeImageLoader nativeImageLoader;
    private Deque<BufferedImage> images;
    private boolean stopRequested;

    public DeepLearning() {
        this.nativeImageLoader = new NativeImageLoader(HEIGHT, WIDTH, CHANNELS);
        images = new ArrayDeque<>();
    }

    @Override
    public void run() {
        stopRequested = false;
        loadDataModel();

        while (!stopRequested) {
            BufferedImage image = getMostRecentImage();
            clearImages();

            if (image != null) {
                updateLabels(image);
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void addImage(BufferedImage image) {
        if (images.size() < 10) {
            images.addFirst(image);
        }
    }

    private synchronized BufferedImage getMostRecentImage() {
        if (!images.isEmpty()) {
            return images.getFirst();
        } else {
            return null;
        }
    }

    private synchronized void clearImages() {
        images.clear();
    }

    /**
     * Load the dataset and the computational graph for deep learning
     */
    public void loadDataModel() {
        try {
            if (computationGraphVGG16 == null) {
                System.out.println("Loading data model.");
                computationGraphVGG16 = ModelSerializer.restoreComputationGraph("resources/VGG16.zip");
                System.out.println("Data model loaded.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pre process the image to create a matrix
     */
    private INDArray processImage(BufferedImage bufferedImage) {
        INDArray imageMatrix = null;
        try {
            imageMatrix = nativeImageLoader.asMatrix(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }


        normalizeImage(imageMatrix);
        return imageMatrix;
    }

    /**
     * Classify images
     */
    private List<Prediction> classify(BufferedImage bufferedImage) {
        INDArray processedImage = processImage(cropImageToSquare(bufferedImage));
        INDArray[] output = computationGraphVGG16.output(false, processedImage);
        List<Prediction> predictions = decodePredictions(output[0]);
        return predictions;
    }

    /**
     * Map each name of the output node to a human readable name from the imagenet class json
     */
    private ArrayList<String> getLabels() {
        ArrayList<String> predictionLabels = null;

        try {
            File imagenetClassFile = new File("resources/imagenet_class_index.json");
            HashMap<String, ArrayList<String>> jsonMap = (HashMap) (new ObjectMapper()).readValue(imagenetClassFile, HashMap.class);
            predictionLabels = new ArrayList(jsonMap.size());

            for (int i = 0; i < jsonMap.size(); ++i) {
                predictionLabels.add((String) ((ArrayList) jsonMap.get(String.valueOf(i))).get(1));
            }
        } catch (IOException var2) {
            var2.printStackTrace();
        }
        return predictionLabels;
    }

    /**
     * Shows the predicted items and the percentage of how certain the model is that it is this item.
     */
    void updateLabels(BufferedImage bufferedImage) {
        List<Prediction> predictionList = classify(bufferedImage);
        // TODO feed back the predictions to GUI
        for (Prediction p : predictionList) {
            System.out.println(p.toString());
        }
        System.out.println("");
    }

    private void normalizeImage(final INDArray image) {
        DataNormalization scaler = new VGG16ImagePreProcessor();
        scaler.transform(image);
    }

    /**
     * Create and return a prediction object for the decodePredictions method.
     */
    private Prediction createPrediction(String label, double percentage) {
        return new Prediction(label, percentage);
    }

    /**
     * Rank the activation of the output nodes to create a top 5 of predictions
     */
    private List<Prediction> decodePredictions(INDArray predictions) {
        List<Prediction> predictionList = new ArrayList<>();
        int[] top5 = new int[5];
        float[] top5Prob = new float[5];

        ArrayList<String> labels = getLabels();
        int i = 0;

        for (INDArray currentBatch = predictions.getRow(0).dup(); i < 5; ++i) {

            top5[i] = Nd4j.argMax(currentBatch, 1).getInt(0, 0);
            top5Prob[i] = currentBatch.getFloat(0, top5[i]);
            currentBatch.putScalar(0, top5[i], 0.0D);

            predictionList.add(createPrediction(labels.get(top5[i]), (top5Prob[i] * 100.0F)));
        }

        return predictionList;

    }

    /**
     * Crops an image to a square for a better recognition
     *
     * @param bufferedImage
     * @return
     */
    private BufferedImage cropImageToSquare(final BufferedImage bufferedImage) {
        int minWidthHeight = Math.min(bufferedImage.getHeight(), bufferedImage.getWidth());
        int x = minWidthHeight == bufferedImage.getWidth() ? 0 : (bufferedImage.getWidth() - minWidthHeight) / 2;
        int y = minWidthHeight == bufferedImage.getHeight() ? 0 : (bufferedImage.getHeight() - minWidthHeight) / 2;
        return bufferedImage.getSubimage(x, y, minWidthHeight, minWidthHeight);
    }

    public void requestStop() {
        stopRequested = true;
    }
}
