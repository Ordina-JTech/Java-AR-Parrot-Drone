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

public class Controller {
    private Drone drone;

    @FXML Button inputConnect;
    @FXML Button inputDisconnect;
    @FXML Button inputTakeOff;
    @FXML Button inputLand;
    @FXML Button inputStartCamera;
    @FXML Button inputStopCamera;
    @FXML Label labelRotateCounterClockwise;
    @FXML Label labelMoveForward;
    @FXML Label labelRotateClockwise;
    @FXML Label labelIncreaseHeight;
    @FXML Label labelMoveLeft;
    @FXML Label labelMoveBackwards;
    @FXML Label labelMoveRight;
    @FXML Label labelDecreaseHeight;
    @FXML CheckBox checkboxManualControl;
    @FXML ImageView imageViewCamera;

    public Controller() {
        initializeDrone();
    }

    public void initializeDrone() {
        drone = new Drone();
    }

    @FXML
    public void inputDroneTakeOff() {
        drone.takeOff();
    }

    @FXML
    public void inputDroneLand() {
        drone.land();
    }

    @FXML
    void inputConnectToDrone() {
        drone.connectToDrone();
        inputConnect.setDisable(true);
        checkboxManualControl.setDisable(false);
        inputDisconnect.setDisable(false);
        inputTakeOff.setDisable(false);
        inputLand.setDisable(false);
        inputStartCamera.setDisable(false);
        inputStopCamera.setDisable(false);
    }

    @FXML
    void inputDisconnectFromDrone() {
        inputConnect.setDisable(false);
        checkboxManualControl.setSelected(false);
        drone.disconnectFromDrone();
        checkboxManualControl.setDisable(true);
        inputDisconnect.setDisable(true);
        inputTakeOff.setDisable(true);
        inputLand.setDisable(true);
        inputStartCamera.setDisable(true);
        inputStopCamera.setDisable(true);
    }

    @FXML
    void inputManualControl() {
        if (checkboxManualControl.isSelected()) {
            drone.enableManualControl();
        } else {
            drone.disableManualControl();
        }
    }

    @FXML
    void onKeyPressed(final KeyEvent keyEvent) {
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
    void onKeyReleased(final KeyEvent keyEvent) {
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
    void inputStartCamera() {
        drone.startCamera(imageViewCamera);
    }

    @FXML
    void inputStopCamera() {
        drone.stopCamera();
    }
}
