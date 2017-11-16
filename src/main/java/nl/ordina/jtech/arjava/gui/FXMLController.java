package nl.ordina.jtech.arjava.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import nl.ordina.jtech.arjava.drone.Drone;

public class FXMLController {
    private Drone drone;
    private UltrasonicData ultrasonicData;

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
            ultrasonicData = new UltrasonicData(labelUltrasonicFront, labelUltrasonicLeft,
                    labelUltrasonicRight, labelUltrasonicBack, labelUltrasonicTop);
            drone.startUltrasonicData(ultrasonicData);
        } else {
            drone.stopUltrasonicData();
            ultrasonicData.resetLabels();
        }
    }

    @FXML
    private void onKeyPressed(final KeyEvent keyEvent) {
        if (!drone.isInManualControl()) {
            return;
        }
        if (keyEvent.getCode() == KeyCode.Q) {
            labelRotateCounterClockwise.setTextFill(Color.LIMEGREEN);
            labelRotateClockwise.setTextFill(Color.BLACK);
            drone.setRequestedRotate(-0.1F);
        }
        if (keyEvent.getCode() == KeyCode.W) {
            labelMoveForward.setTextFill(Color.LIMEGREEN);
            labelMoveBackwards.setTextFill(Color.BLACK);
            drone.setRequestedPitch(-0.1F);
        }
        if (keyEvent.getCode() == KeyCode.E) {
            labelRotateClockwise.setTextFill(Color.LIMEGREEN);
            labelRotateCounterClockwise.setTextFill(Color.BLACK);
            drone.setRequestedRotate(0.1F);
        }
        if (keyEvent.getCode() == KeyCode.R) {
            labelIncreaseHeight.setTextFill(Color.LIMEGREEN);
            labelDecreaseHeight.setTextFill(Color.BLACK);
            drone.setRequestedGaz(0.1F);
        }
        if (keyEvent.getCode() == KeyCode.A) {
            labelMoveLeft.setTextFill(Color.LIMEGREEN);
            labelMoveRight.setTextFill(Color.BLACK);
            drone.setRequestedRoll(-0.1F);
        }
        if (keyEvent.getCode() == KeyCode.S) {
            labelMoveBackwards.setTextFill(Color.LIMEGREEN);
            labelMoveForward.setTextFill(Color.BLACK);
            drone.setRequestedPitch(0.1F);
        }
        if (keyEvent.getCode() == KeyCode.D) {
            labelMoveRight.setTextFill(Color.LIMEGREEN);
            labelMoveLeft.setTextFill(Color.BLACK);
            drone.setRequestedRoll(0.1F);
        }
        if (keyEvent.getCode() == KeyCode.F) {
            labelDecreaseHeight.setTextFill(Color.LIMEGREEN);
            labelIncreaseHeight.setTextFill(Color.BLACK);
            drone.setRequestedGaz(-0.1F);
        }
    }

    @FXML
    private void onKeyReleased(final KeyEvent keyEvent) {
        if (!drone.isInManualControl()) {
            return;
        }
        if (keyEvent.getCode() == KeyCode.Q) {
            labelRotateCounterClockwise.setTextFill(Color.BLACK);
            drone.setRequestedRotate(0);
        }
        if (keyEvent.getCode() == KeyCode.W) {
            labelMoveForward.setTextFill(Color.BLACK);
            drone.setRequestedPitch(0);
        }
        if (keyEvent.getCode() == KeyCode.E) {
            labelRotateClockwise.setTextFill(Color.BLACK);
            drone.setRequestedRotate(0);
        }
        if (keyEvent.getCode() == KeyCode.R) {
            labelIncreaseHeight.setTextFill(Color.BLACK);
            drone.setRequestedGaz(0);
        }
        if (keyEvent.getCode() == KeyCode.A) {
            labelMoveLeft.setTextFill(Color.BLACK);
            drone.setRequestedRoll(0);
        }
        if (keyEvent.getCode() == KeyCode.S) {
            labelMoveBackwards.setTextFill(Color.BLACK);
            drone.setRequestedPitch(0);
        }
        if (keyEvent.getCode() == KeyCode.D) {
            labelMoveRight.setTextFill(Color.BLACK);
            drone.setRequestedRoll(0);
        }
        if (keyEvent.getCode() == KeyCode.F) {
            labelDecreaseHeight.setTextFill(Color.BLACK);
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
