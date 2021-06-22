/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author User
 */
public class FXMLDocumentController implements Initializable {

    
    private Label label;
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    GridPane grelha;
    @FXML
    Circle p1, p2;

    tabuleiro tab1;
    jogador jog1, jog2;

    //  ArrayList<Circle> pecas;
    @FXML
    private void handleButtonAction(ActionEvent event) {

        System.out.println("You clicked me!");
        label.setText("Hello World!");

    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        tab1 = new tabuleiro();
        jog1 = new jogador();
        jog2 = new jogador();
        p1.setFill(Color.BROWN);

    }

    public void switchscene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("scene1.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchscene2(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addpeca(MouseEvent event) {

        System.out.println(p1.getFill());
        grelha.add(new Circle(0, 0, 18, Color.BLUEVIOLET), 2, 1);

    }

}
