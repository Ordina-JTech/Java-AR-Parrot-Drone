package nl.ordina.jtech.arjava.gui;

import javafx.application.Platform;
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
        Platform.runLater(new Runnable() {
            public void run() {
                labelRotateCounterClockwise.setTextFill(Color.LIMEGREEN);
                labelRotateClockwise.setTextFill(Color.BLACK);
            }
        });
    }

    public void forwardOn() {
        Platform.runLater(new Runnable() {
            public void run() {
                labelMoveForward.setTextFill(Color.LIMEGREEN);
                labelMoveBackwards.setTextFill(Color.BLACK);
            }
        });
    }

    public void clockwiseOn() {
        Platform.runLater(new Runnable() {
            public void run() {
                labelRotateClockwise.setTextFill(Color.LIMEGREEN);
                labelRotateCounterClockwise.setTextFill(Color.BLACK);
            }
        });
    }

    public void upOn() {
        Platform.runLater(new Runnable() {
            public void run() {
                labelIncreaseHeight.setTextFill(Color.LIMEGREEN);
                labelDecreaseHeight.setTextFill(Color.BLACK);
            }
        });
    }

    public void leftOn() {
        Platform.runLater(new Runnable() {
            public void run() {
                labelMoveLeft.setTextFill(Color.LIMEGREEN);
                labelMoveRight.setTextFill(Color.BLACK);
            }
        });
    }

    public void backwardsOn() {
        Platform.runLater(new Runnable() {
            public void run() {
                labelMoveBackwards.setTextFill(Color.LIMEGREEN);
                labelMoveForward.setTextFill(Color.BLACK);
            }
        });
    }

    public void rightOn() {
        Platform.runLater(new Runnable() {
            public void run() {
                labelMoveRight.setTextFill(Color.LIMEGREEN);
                labelMoveLeft.setTextFill(Color.BLACK);
            }
        });
    }

    public void downOn() {
        Platform.runLater(new Runnable() {
            public void run() {
                labelDecreaseHeight.setTextFill(Color.LIMEGREEN);
                labelIncreaseHeight.setTextFill(Color.BLACK);
            }
        });
    }

    public void counterClockwiseOff() {
        Platform.runLater(new Runnable() {
            public void run() {
                labelRotateCounterClockwise.setTextFill(Color.BLACK);
            }
        });
    }

    public void forwardOff() {
        Platform.runLater(new Runnable() {
            public void run() {
                labelMoveForward.setTextFill(Color.BLACK);
            }
        });
    }

    public void clockwiseOff() {
        Platform.runLater(new Runnable() {
            public void run() {
                labelRotateClockwise.setTextFill(Color.BLACK);
            }
        });
    }

    public void upOff() {
        Platform.runLater(new Runnable() {
            public void run() {
                labelIncreaseHeight.setTextFill(Color.BLACK);
            }
        });
    }

    public void leftOff() {
        Platform.runLater(new Runnable() {
            public void run() {
                labelMoveLeft.setTextFill(Color.BLACK);
            }
        });
    }

    public void backwardsOff() {
        Platform.runLater(new Runnable() {
            public void run() {
                labelMoveBackwards.setTextFill(Color.BLACK);
            }
        });
    }

    public void rightOff() {
        Platform.runLater(new Runnable() {
            public void run() {
                labelMoveRight.setTextFill(Color.BLACK);
            }
        });
    }

    public void downOff() {
        Platform.runLater(new Runnable() {
            public void run() {
                labelDecreaseHeight.setTextFill(Color.BLACK);
            }
        });
    }

    public void reset() {
        Platform.runLater(new Runnable() {
            public void run() {
                labelRotateCounterClockwise.setTextFill(Color.BLACK);
                labelMoveForward.setTextFill(Color.BLACK);
                labelRotateClockwise.setTextFill(Color.BLACK);
                labelIncreaseHeight.setTextFill(Color.BLACK);
                labelMoveLeft.setTextFill(Color.BLACK);
                labelMoveBackwards.setTextFill(Color.BLACK);
                labelMoveRight.setTextFill(Color.BLACK);
                labelDecreaseHeight.setTextFill(Color.BLACK);
            }
        });
    }
}
