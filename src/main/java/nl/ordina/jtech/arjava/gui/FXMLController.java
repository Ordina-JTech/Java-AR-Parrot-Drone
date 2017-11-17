package nl.ordina.jtech.arjava.gui;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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


    public FXMLController() {
        initialize();
    }

    private void initialize() {
        drone = new Drone();
    }

    @FXML
    private void inputDroneTakeOff() {
        drone.takeOff();
    }

    @FXML
    private void inputDroneLand() {
        drone.land();
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
        ultrasonicData = new UltrasonicData(labelUltrasonicFront, labelUltrasonicLeft,
                labelUltrasonicRight, labelUltrasonicBack, labelUltrasonicTop);
        droneControlFeedback = new DroneControlFeedback(labelRotateCounterClockwise,
                labelMoveForward, labelRotateClockwise, labelIncreaseHeight,
                labelMoveLeft, labelMoveBackwards, labelMoveRight, labelDecreaseHeight);
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
    }

    @FXML
    private void inputManualControl() {
        if (checkboxManualControl.isSelected()) {
            drone.enableManualControl();
        } else {
            drone.disableManualControl();
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
            droneControlFeedback.counterClockwiseOn();
            drone.setRequestedRotate(-0.1F);
        }
        if (keyEvent.getCode() == KeyCode.W) {
            droneControlFeedback.forwardOn();
            drone.setRequestedPitch(-0.1F);
        }
        if (keyEvent.getCode() == KeyCode.E) {
            droneControlFeedback.clockwiseOn();
            drone.setRequestedRotate(0.1F);
        }
        if (keyEvent.getCode() == KeyCode.R) {
            droneControlFeedback.upOn();
            drone.setRequestedGaz(0.1F);
        }
        if (keyEvent.getCode() == KeyCode.A) {
            droneControlFeedback.leftOn();
            drone.setRequestedRoll(-0.1F);
        }
        if (keyEvent.getCode() == KeyCode.S) {
            droneControlFeedback.backwardsOn();
            drone.setRequestedPitch(0.1F);
        }
        if (keyEvent.getCode() == KeyCode.D) {
            droneControlFeedback.rightOn();
            drone.setRequestedRoll(0.1F);
        }
        if (keyEvent.getCode() == KeyCode.F) {
            droneControlFeedback.downOn();
            drone.setRequestedGaz(-0.1F);
        }
    }

    @FXML
    private void onKeyReleased(final KeyEvent keyEvent) {
        if (!drone.isInManualControl()) {
            return;
        }
        if (keyEvent.getCode() == KeyCode.Q) {
            droneControlFeedback.counterClockwiseOff();
            drone.setRequestedRotate(0);
        }
        if (keyEvent.getCode() == KeyCode.W) {
            droneControlFeedback.forwardOff();
            drone.setRequestedPitch(0);
        }
        if (keyEvent.getCode() == KeyCode.E) {
            droneControlFeedback.clockwiseOff();
            drone.setRequestedRotate(0);
        }
        if (keyEvent.getCode() == KeyCode.R) {
            droneControlFeedback.upOff();
            drone.setRequestedGaz(0);
        }
        if (keyEvent.getCode() == KeyCode.A) {
            droneControlFeedback.leftOff();
            drone.setRequestedRoll(0);
        }
        if (keyEvent.getCode() == KeyCode.S) {
            droneControlFeedback.backwardsOff();
            drone.setRequestedPitch(0);
        }
        if (keyEvent.getCode() == KeyCode.D) {
            droneControlFeedback.rightOff();
            drone.setRequestedRoll(0);
        }
        if (keyEvent.getCode() == KeyCode.F) {
            droneControlFeedback.downOff();
            drone.setRequestedGaz(0);
        }
    }

    @FXML
    private void inputStartCamera() {
        drone.startCamera(imageViewCamera);
    }

    @FXML
    private void inputStopCamera() {
        drone.stopCamera();
    }
}
