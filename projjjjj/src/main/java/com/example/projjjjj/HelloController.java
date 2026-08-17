package com.example.projjjjj;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;


import javax.swing.*;

import java.io.IOException;

public class HelloController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToScene1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

     public void switchToScene2 (ActionEvent event) throws IOException{
         Parent root = FXMLLoader.load(getClass().getResource("new.fxml"));
         stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
    }


    @FXML
    private Label walktext;

    @FXML
    private Label waterlabel;

    @FXML
    private  Label sleeplabel;

    @FXML
    private Label eatlabel;

    @FXML
    private TextField masstext;

    @FXML
    private TextField heighttext;

    @FXML
    private  Label resultlabel;

    @FXML
    protected void onclick(){ walktext.setText("your Stepcount for today is 1000");}

    @FXML
    protected void waterclick(){ waterlabel.setText("You should drink 2.5l of water today");}

    @FXML
    protected  void sleepclick(){sleeplabel.setText("you should get at least 8 hours of sleep");}

    @FXML
    protected void eatclick(){eatlabel.setText("Eat your vegetables");}

    @FXML
    private void calculateResult() {
        try {
            double number1 = Double.parseDouble(masstext.getText());
            double number2 = Double.parseDouble(heighttext.getText());
            double result = number1 / (number2*number2);

            resultlabel.setText(result + "kg/m^2");
        } catch (NumberFormatException e) {
            resultlabel.setText("Invalid input! Please enter numbers.");
        }
    }






}