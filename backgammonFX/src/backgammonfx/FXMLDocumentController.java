/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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
    Pane pane;
    Label ronda;
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
        boolean b = true;
        tab1 = new tabuleiro();
        jog1 = new jogador();
        jog2 = new jogador();

        for (int i = 0; i <= 25; i++) {
            pane.getChildren().add(tab1.casas.get(i).rect);
            System.out.println("g");
        }

        //necessario repetir for para garantir que as peças estejam no topo da hierarquia
        for (int i = 0; i <= 25; i++) {
            for (int h = 0; h < tab1.casas.get(i).pecas.size(); h++) {
                pane.getChildren().add(tab1.casas.get(i).pecas.get(h).c);
            }
        }
        //inicio do jogo
        while (b == true) {
        System.out.println("Roda do jogador 1");
        ronda = new Label("ronda1");
        
        }

    }

    public void switchscene1(MouseEvent event) throws IOException {
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


        Circle B = new Circle(0, 0, 18, Color.BLUEVIOLET);

    }

}
