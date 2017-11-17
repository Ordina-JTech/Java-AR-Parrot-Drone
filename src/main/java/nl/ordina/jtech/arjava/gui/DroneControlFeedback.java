package nl.ordina.jtech.arjava.gui;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class DroneControlFeedback {
    private Label labelRotateCounterClockwise;
    private Label labelMoveForward;
    private Label labelRotateClockwise;
    private Label labelIncreaseHeight;
    private Label labelMoveLeft;
    private Label labelMoveBackwards;
    private Label labelMoveRight;
    private Label labelDecreaseHeight;

    public DroneControlFeedback(Label labelRotateCounterClockwise, Label labelMoveForward,
                                Label labelRotateClockwise, Label labelIncreaseHeight,
                                Label labelMoveLeft, Label labelMoveBackwards,
                                Label labelMoveRight, Label labelDecreaseHeight) {
        this.labelRotateCounterClockwise = labelRotateCounterClockwise;
        this.labelMoveForward = labelMoveForward;
        this.labelRotateClockwise = labelRotateClockwise;
        this.labelIncreaseHeight = labelIncreaseHeight;
        this.labelMoveLeft = labelMoveLeft;
        this.labelMoveBackwards = labelMoveBackwards;
        this.labelMoveRight = labelMoveRight;
        this.labelDecreaseHeight = labelDecreaseHeight;
    }

    public void counterClockwiseOn() {
        labelRotateCounterClockwise.setTextFill(Color.LIMEGREEN);
        labelRotateClockwise.setTextFill(Color.BLACK);
    }

    public void forwardOn() {
        labelMoveForward.setTextFill(Color.LIMEGREEN);
        labelMoveBackwards.setTextFill(Color.BLACK);
    }

    public void clockwiseOn() {
        labelRotateClockwise.setTextFill(Color.LIMEGREEN);
        labelRotateCounterClockwise.setTextFill(Color.BLACK);
    }

    public void upOn() {
        labelIncreaseHeight.setTextFill(Color.LIMEGREEN);
        labelDecreaseHeight.setTextFill(Color.BLACK);
    }

    public void leftOn() {
        labelMoveLeft.setTextFill(Color.LIMEGREEN);
        labelMoveRight.setTextFill(Color.BLACK);
    }

    public void backwardsOn() {
        labelMoveBackwards.setTextFill(Color.LIMEGREEN);
        labelMoveForward.setTextFill(Color.BLACK);
    }

    public void rightOn() {
        labelMoveRight.setTextFill(Color.LIMEGREEN);
        labelMoveLeft.setTextFill(Color.BLACK);
    }

    public void downOn() {
        labelDecreaseHeight.setTextFill(Color.LIMEGREEN);
        labelIncreaseHeight.setTextFill(Color.BLACK);
    }

    public void counterClockwiseOff() {
        labelRotateCounterClockwise.setTextFill(Color.BLACK);
    }

    public void forwardOff() {
        labelMoveForward.setTextFill(Color.BLACK);
    }

    public void clockwiseOff() {
        labelRotateClockwise.setTextFill(Color.BLACK);
    }

    public void upOff() {
        labelIncreaseHeight.setTextFill(Color.BLACK);
    }

    public void leftOff() {
        labelMoveLeft.setTextFill(Color.BLACK);
    }

    public void backwardsOff() {
        labelMoveBackwards.setTextFill(Color.BLACK);
    }

    public void rightOff() {
        labelMoveRight.setTextFill(Color.BLACK);
    }

    public void downOff() {
        labelDecreaseHeight.setTextFill(Color.BLACK);
    }
}
