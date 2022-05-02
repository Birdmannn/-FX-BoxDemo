package com.example.boxdemo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BoxDemo extends Application {
    private final static int SQUARE_SIZE = 45;
    private double positionX = 95;
    private double positionY = 30;
    private double positionX2 = 150;
    private double positionY2 = 30;
    private boolean dragRed;
    private double pointX, pointY;

    private boolean dragging;
    private Canvas canvas;



    public void start(Stage stage) {
        canvas = new Canvas(450,450);
        drawBox();

        canvas.setOnMousePressed(this::mousePressed);
        canvas.setOnMouseDragged(this::mouseDragged);
        canvas.setOnMouseReleased(this::mouseReleased);


        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Box Demo");
        stage.setResizable(false);

        scene.setOnKeyPressed(evt -> {
            KeyCode key = evt.getCode();
            if(key == KeyCode.ESCAPE) {
                positionX = 95;
                positionY = 30;
                positionX2 = 150;
                positionY2 = 30;
                drawBox();
            }
        });

        stage.show();


    }

    public void mousePressed(MouseEvent evt) {

        if(dragging || evt.isSecondaryButtonDown())
            return;
        double x = evt.getX();
        double y = evt.getY();

        if(x >= positionX && x <= positionX + SQUARE_SIZE) {
            if(y >= positionY && y <= positionY + SQUARE_SIZE ) { //+SQUARE_SIZE missing before, mousePressed() method didn't work.
                dragging = true;                                  //Now Working.
                dragRed = true;
                pointX = x - positionX;
                pointY = y - positionY;
            }
        }
        else if(x >= positionX2 && x <= positionX2 + SQUARE_SIZE) {
            if(y >= positionY2 && y <= positionY2 + SQUARE_SIZE) {
                dragging = true;
                dragRed = false;
                pointX = x - positionX2;
                pointY = y - positionY2;
            }

        }

    }

    public void mouseDragged(MouseEvent evt) {
        if(evt.isSecondaryButtonDown() || !dragging)
            return;
        double x = evt.getX();
        double y = evt.getY();

        if(dragRed) {
            positionX = x - pointX;
            positionY = y - pointY;
        }
        else {
            positionX2 = x - pointX;
            positionY2 = y - pointY;

        }
        drawBox();

    }

    public void mouseReleased(MouseEvent evt) {
        dragging = false;
    }

    public void drawBox() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.LIGHTBLUE);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.setFill(Color.RED);
        g.fillRect(positionX, positionY, SQUARE_SIZE, SQUARE_SIZE);
        g.setFill(Color.BLUE);
        g.fillRect(positionX2, positionY2, SQUARE_SIZE, SQUARE_SIZE);
    }




    public static void main(String[] args) {
        launch();
    }
}