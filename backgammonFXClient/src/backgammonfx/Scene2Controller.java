/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import static backgammonfx.Client.PORT;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author User
 */
public class Scene2Controller implements Initializable {

    private Label label;
    private Stage stage;
    private Scene scene;
    private Parent root;

    //---------------------------------FXML-----------------------------------------
    @FXML
    Pane pane;
    Label ronda;
    tabuleiro tab1;
    jogador jog1, jog2;
    peca pec;
    packet p;
    @FXML
    ArrayList<Rectangle> Rects;
    Client cliente;
    ArrayList<Circle> Circs;

    //  ArrayList<Circle> pecas;
    @FXML
    private void handleButtonAction(ActionEvent event) {

        System.out.println("You clicked me!");
        label.setText("Hello World!");

    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Rects = new ArrayList<>();
        Circs = new ArrayList<>();
        tab1 = new tabuleiro();
        jog1 = new jogador();
        jog2 = new jogador();
        //---------------------------------server-----------------------------------------
        try {
            cliente = new Client();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            tab1.casas = cliente.receberpecas();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        //--------------------------Criar retangulos------------------------------------
        imprimeretangulos();
//necessario repetir "for" para garantir que as peças estejam no topo da hierarquia
        imprimepecas();
//inicio do jogo

        System.out.println("Roda do jogador 1");
        ronda = new Label("Ronda 1");
        ronda.setLayoutX(300);
        ronda.setLayoutY(180);       

        //    Criar retangulos
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

    @FXML
    public void imprimeretangulos() {
        Rects.clear();
        for (int i = 0; i <= 25; i++) {

            Rects.add(i, new Rectangle(50, 180));
            Rects.get(i).setLayoutX(tab1.casas.get(i).posX);
            Rects.get(i).setLayoutY(tab1.casas.get(i).posY);
            if ("AQUA".compareTo(tab1.casas.get(i).cor) == 0) {
                Rects.get(i).setFill(Color.BLUEVIOLET);
            }
            if ("CHOCOLATE".compareTo(tab1.casas.get(i).cor) == 0) {
                Rects.get(i).setFill(Color.TOMATO);
            }

            Rects.get(i).setStroke(Color.BLACK);
            Rects.get(i).setId(String.valueOf(i));
            System.out.println(Rects.get(i));
            Rects.get(i).setOnMousePressed(event -> pressed(event));
            pane.getChildren().add(Rects.get(i));

        }
    }

    @FXML
    public void imprimepecas() {
        Circs.clear();
        int cont = 0;
        for (int i = 0; i <= 25; i++) {
            for (int h = 0; h < tab1.casas.get(i).pecas.size(); h++) {

                Circs.add(cont, new Circle(tab1.casas.get(i).pecas.get(h).posX, tab1.casas.get(i).pecas.get(h).posY, 18));

                if ("jog1".compareTo(tab1.casas.get(i).pecas.get(h).jogador) == 0) {
                    Circs.get(cont).setFill(Color.WHITE);
                    Circs.get(cont).setStroke(Color.BLACK);
                }
                if ("jog2".compareTo(tab1.casas.get(i).pecas.get(h).jogador) == 0) {
                    Circs.get(cont).setFill(Color.BLACK);
                    Circs.get(cont).setStroke(Color.WHITE);
                }
                pane.getChildren().add(Circs.get(cont));
                cont++;
            }
        }
    }

    private void pressed(MouseEvent event) {
        Rectangle b = (Rectangle) event.getSource();
        int id = Integer.parseInt(b.getId());
        System.out.println(id);
        tab1.casas.get(id);
    }
}
