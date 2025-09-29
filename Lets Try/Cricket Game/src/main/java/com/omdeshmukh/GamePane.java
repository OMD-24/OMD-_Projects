package com.omdeshmukh;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GamePane extends Pane {
    private Circle ball;
    private Rectangle bat;
    private double ballSpeed = 3;
    private int score = 0;
    private int ballsLeft = 10;
    private boolean ballHit = false;

    public GamePane() {
        setStyle("-fx-background-color: lightgreen;");
        ball = new Circle(15, Color.RED);
        bat = new Rectangle(100, 20, Color.BLUE);

        resetBall();
        bat.setX(350);
        bat.setY(500);

        getChildren().addAll(ball, bat);

        setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SPACE -> hitBall();
            }
        });

        setFocusTraversable(true);
    }

    private void resetBall() {
        ball.setCenterX(400);
        ball.setCenterY(0);
        ballHit = false;
    }

    public void hitBall() {
        if (!ballHit && Math.abs(ball.getCenterY() - bat.getY()) < 30) {
            score += 4;
            System.out.println("Hit! Score: " + score);
            ballHit = true;
        } else {
            System.out.println("Miss!");
        }
    }

    public void startGame() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (ballsLeft > 0) {
                    ball.setCenterY(ball.getCenterY() + ballSpeed);
                    if (ball.getCenterY() > 600) {
                        ballsLeft--;
                        resetBall();
                        System.out.println("Balls left: " + ballsLeft);
                    }
                } else {
                    stop();
                    System.out.println("Game Over! Final Score: " + score);
                }
            }
        };
        timer.start();
    }
}
