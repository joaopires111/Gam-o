/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;

/**
 *
 * @author User
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    private Stage stage;
    private Scene scene;
    private Parent root;
    //---------------------------------server-----------------------------------------
    ServerSocket ss;
    Socket s;
    ObjectInputStream din;
    ObjectOutputStream dout;
    static final int PORT = 3192;
    //---------------------------------FXML-----------------------------------------
    @FXML
    Pane pane;
    Label ronda;
    tabuleiro tab1;
    jogador jog1, jog2;
    packet p;
    ArrayList<Rectangle> Rects;
    @FXML
    ArrayList<Circle> Circs;
    Server servidor;
    casa casacopy;
    TranslateTransition moves; 
    int iniX, iniY, finX, finY, iniID, finID, phase;

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
        phase = 0;
         //---------------------------------Setmove-----------------------------------------       

        //---------------------------------server-----------------------------------------
        try {
            servidor = new Server();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("pausa");
        try {
            servidor.enviarPecas(tab1);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        phase = 1;
        //--------------------------IMPRIMIR------------------------------------
        imprimeretangulos();
        imprimepecas();        
        System.out.println("Roda do jogador 1");
        ronda = new Label("fase do jogo: "+phase);
        ronda.setLayoutX(300);
        ronda.setLayoutY(180);
        pane.getChildren().add(ronda);   

//-------------------------------inicio do jogo---------------------------------

     
        
   
        
        
        
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
            if (tab1.casas.get(i).cor == "AQUA") {
                Rects.get(i).setFill(Color.AQUA);
            }
            if (tab1.casas.get(i).cor == "CHOCOLATE") {
                Rects.get(i).setFill(Color.CHOCOLATE);
            }
           

            Rects.get(i).setStroke(Color.BLACK);
            Rects.get(i).setId(String.valueOf(i));
            Rects.get(i).setOnMousePressed(event -> pressed(event));
            pane.getChildren().add(Rects.get(i));

        }
    }
//necessario repetir "for" para garantir que as peças estejam no topo da hierarquia
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
                Circs.get(cont).setId(i+""+h);
                pane.getChildren().add(Circs.get(cont));
                cont++;
            }
        }
    }

    private void pressed(MouseEvent event) {
        if(phase == 1){
        Rectangle b = (Rectangle) event.getSource();
        iniID = Integer.parseInt(b.getId());
        iniX = tab1.casas.get(iniID).posX;
        iniY = tab1.casas.get(iniID).posY;
        System.out.println("ID inicial:" + iniID);
        System.out.println("X inicial:" + iniX);
        System.out.println("Y inicial:" + iniY);
        Rects.get(iniID).setFill(Color.RED);
        phase = 2;
        ronda.setText("fase do jogo: "+phase);   
        }
        else if(phase == 2){
        Rectangle b = (Rectangle) event.getSource();
        finID = Integer.parseInt(b.getId());
        finX = tab1.casas.get(finID).posX;
        finY = tab1.casas.get(finID).posY;
        System.out.println("ID inicial:" + finID);
        System.out.println("X inicial:" + finX);
        System.out.println("Y inicial:" + finY);
        Rects.get(finID).setFill(Color.BLUE);
        phase = 3;
        ronda.setText("fase do jogo: "+phase);
        
        movepeca(finX - iniX, finY - iniY);
        }
    }
    
    private void movepeca(int diferencaX, int diferencaY){
        //Instantiating TranslateTransition class   
        TranslateTransition translate = new TranslateTransition();
        //shifting the X coordinate of the centre of the circle by 400   
        translate.setByX(diferencaX);
        translate.setByY(diferencaY);        
        //setting the duration for the Translate transition   
        translate.setDuration(Duration.millis(500));
        //setting cycle count for the Translate transition   
        translate.setCycleCount(1);
        //the transition will set to be auto reversed by setting this to true   
        translate.setAutoReverse(true);  
        //setting Circle as the node onto which the transition will be applied  
        translate.setNode(Circs.get(3));
        //playing the transition   
        translate.play();
    }
}
