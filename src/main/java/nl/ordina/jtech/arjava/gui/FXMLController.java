package nl.ordina.jtech.arjava.gui;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import nl.ordina.jtech.arjava.drone.Drone;

import java.awt.image.BufferedImage;

public class FXMLController {
    private Drone drone;
    private UltrasonicData ultrasonicData;
    private DroneControlFeedback droneControlFeedback;

    @FXML private Button inputConnect;
    @FXML private Button inputDisconnect;
    @FXML private Button inputTakeOff;
    @FXML private Button inputLand;
    @FXML private Button inputStartCamera;
    @FXML private Button inputStopCamera;
    @FXML private Button inputStartDetection;
    @FXML private Button inputStopDetection;
    @FXML private Button inputFlightPath;
    @FXML private Label labelRotateCounterClockwise;
    @FXML private Label labelMoveForward;
    @FXML private Label labelRotateClockwise;
    @FXML private Label labelIncreaseHeight;
    @FXML private Label labelMoveLeft;
    @FXML private Label labelMoveBackwards;
    @FXML private Label labelMoveRight;
    @FXML private Label labelDecreaseHeight;
    @FXML private CheckBox checkboxManualControl;
    @FXML private ImageView imageViewCamera;
    @FXML private CheckBox checkboxUltrasonicData;
    @FXML private Label labelUltrasonicFront;
    @FXML private Label labelUltrasonicLeft;
    @FXML private Label labelUltrasonicRight;
    @FXML private Label labelUltrasonicBack;
    @FXML private Label labelUltrasonicTop;
    @FXML private TextArea outputDetections;
    @FXML private TextArea outputLog;


    public FXMLController() {
        initialize();
    }

    private void initialize() {
        drone = new Drone();
    }

    @FXML
    private void inputDroneTakeOff() {
        drone.takeOff();
        logLine("Sending take off command.");
    }

    @FXML
    private void inputDroneLand() {
        drone.land();
        logLine("Sending land command.");
    }

    @FXML
    private void inputConnectToDrone() {
        drone.connectToDrone();
        inputConnect.setDisable(true);
        checkboxManualControl.setDisable(false);
        checkboxUltrasonicData.setDisable(false);
        inputDisconnect.setDisable(false);
        inputTakeOff.setDisable(false);
        inputLand.setDisable(false);
        inputStartCamera.setDisable(false);
        inputStopCamera.setDisable(false);
        inputStartDetection.setDisable(false);
        inputStopDetection.setDisable(false);
        inputFlightPath.setDisable(false);
        ultrasonicData = new UltrasonicData(labelUltrasonicFront, labelUltrasonicLeft,
                labelUltrasonicRight, labelUltrasonicBack, labelUltrasonicTop);
        droneControlFeedback = new DroneControlFeedback(labelRotateCounterClockwise,
                labelMoveForward, labelRotateClockwise, labelIncreaseHeight,
                labelMoveLeft, labelMoveBackwards, labelMoveRight, labelDecreaseHeight);
        drone.setDroneControlFeedback(droneControlFeedback);
        drone.setDetectionOuputFeedback(outputDetections);
        logLine("Connected to drone.");
    }

    @FXML
    private void inputDisconnectFromDrone() {
        inputConnect.setDisable(false);
        checkboxManualControl.setSelected(false);
        checkboxUltrasonicData.setSelected(false);
        drone.disconnectFromDrone();
        checkboxManualControl.setDisable(true);
        checkboxUltrasonicData.setDisable(true);
        ultrasonicData.resetLabels();
        inputDisconnect.setDisable(true);
        inputTakeOff.setDisable(true);
        inputLand.setDisable(true);
        inputStartCamera.setDisable(true);
        inputStopCamera.setDisable(true);
        inputStartDetection.setDisable(true);
        inputStopDetection.setDisable(true);
        inputFlightPath.setDisable(true);
        logLine("Disconnected from drone.");
    }

    @FXML
    private void inputManualControl() {
        if (checkboxManualControl.isSelected()) {
            if (!drone.enableManualControl()) {
                checkboxManualControl.setSelected(false);
                logLine("Cannot enable manual control, flight path may still be running.");
            } else {
                logLine("Manual control enabled.");
            }
        } else {
            drone.disableManualControl();
            logLine("Manual control disabled.");
        }
    }

    @FXML
    private void inputUltrasonicData() {
        if (checkboxUltrasonicData.isSelected()) {
            drone.startUltrasonicData(ultrasonicData);
        } else {
            drone.stopUltrasonicData();
        }
    }

    @FXML
    private void onKeyPressed(final KeyEvent keyEvent) {
        if (!drone.isInManualControl()) {
            return;
        }
        if (keyEvent.getCode() == KeyCode.Q) {
            drone.setRequestedRotate(-0.1F);
        }
        if (keyEvent.getCode() == KeyCode.W) {
            drone.setRequestedPitch(-0.1F);
        }
        if (keyEvent.getCode() == KeyCode.E) {
            drone.setRequestedRotate(0.1F);
        }
        if (keyEvent.getCode() == KeyCode.R) {
            drone.setRequestedGaz(0.1F);
        }
        if (keyEvent.getCode() == KeyCode.A) {
            drone.setRequestedRoll(-0.1F);
        }
        if (keyEvent.getCode() == KeyCode.S) {
            drone.setRequestedPitch(0.1F);
        }
        if (keyEvent.getCode() == KeyCode.D) {
            drone.setRequestedRoll(0.1F);
        }
        if (keyEvent.getCode() == KeyCode.F) {
            drone.setRequestedGaz(-0.1F);
        }
    }

    @FXML
    private void onKeyReleased(final KeyEvent keyEvent) {
        if (!drone.isInManualControl()) {
            return;
        }
        if (keyEvent.getCode() == KeyCode.Q) {
            drone.setRequestedRotate(0);
        }
        if (keyEvent.getCode() == KeyCode.W) {
            drone.setRequestedPitch(0);
        }
        if (keyEvent.getCode() == KeyCode.E) {
            drone.setRequestedRotate(0);
        }
        if (keyEvent.getCode() == KeyCode.R) {
            drone.setRequestedGaz(0);
        }
        if (keyEvent.getCode() == KeyCode.A) {
            drone.setRequestedRoll(0);
        }
        if (keyEvent.getCode() == KeyCode.S) {
            drone.setRequestedPitch(0);
        }
        if (keyEvent.getCode() == KeyCode.D) {
            drone.setRequestedRoll(0);
        }
        if (keyEvent.getCode() == KeyCode.F) {
            drone.setRequestedGaz(0);
        }
    }

    @FXML
    private void inputStartCamera() {
        drone.startCamera(imageViewCamera);
        logLine("Starting camera.");

    }

    @FXML
    private void inputStopCamera() {
        drone.stopCamera();
        logLine("Stopping camera.");
    }

    @FXML
    private void inputStartDetection() {
        drone.startDeepLearning();
        logLine("Starting deep learning.");
    }

    @FXML
    private void inputStopDetection() {
        drone.stopDeepLearning();
        logLine("Stopping deep learning.");
    }

    @FXML
    private void inputRunFlightPath() {
        if (checkboxManualControl.isSelected()) {
            logLine("Cannot run flight path in manual control.");
        } else {
            drone.runFlightPath();
            logLine("Running flight path.");
        }

    }

    private void logLine(String line) {
        outputLog.setText(outputLog.getText() + line + "\n");
        outputLog.setScrollTop(Integer.MAX_VALUE);
    }
}
