package nl.ordina.jtech.arjava.gui;

import javafx.scene.control.Label;

public class UltrasonicData {
    private Label labelUltrasonicFront;
    private Label labelUltrasonicLeft;
    private Label labelUltrasonicRight;
    private Label labelUltrasonicBack;
    private Label labelUltrasonicTop;

    public UltrasonicData(Label labelUltrasonicFront, Label labelUltrasonicLeft,
                          Label labelUltrasonicRight, Label labelUltrasonicBack, Label labelUltrasonicTop) {
        this.labelUltrasonicFront = labelUltrasonicFront;
        this.labelUltrasonicLeft = labelUltrasonicLeft;
        this.labelUltrasonicRight = labelUltrasonicRight;
        this.labelUltrasonicBack = labelUltrasonicBack;
        this.labelUltrasonicTop = labelUltrasonicTop;
    }

    public void setFrontValue(int value) {
        labelUltrasonicFront.setText("Front: " + value);
    }

    public void setLeftValue(int value) {
        labelUltrasonicLeft.setText("Left: " + value);
    }

    public void setRightValue(int value) {
        labelUltrasonicRight.setText("Right: " + value);
    }

    public void setBackValue(int value) {
        labelUltrasonicBack.setText("Back: " + value);
    }

    public void setTopValue(int value) {
        labelUltrasonicTop.setText("Top: " + value);
    }

    public void resetLabels() {
        labelUltrasonicFront.setText("Front: 0");
        labelUltrasonicLeft.setText("Left: 0");
        labelUltrasonicRight.setText("Right: 0");
        labelUltrasonicBack.setText("Back: 0");
        labelUltrasonicTop.setText("Top: 0");
    }
}
