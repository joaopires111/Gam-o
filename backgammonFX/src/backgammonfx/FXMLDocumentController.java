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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.Button;
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
    @FXML
    Button cancelar;
    Label ronda;
    Button bdados;
    tabuleiro tab1;
    jogador jog1, jog2;
    packet p;
    ArrayList<Rectangle> Rects;
    @FXML
    static Circle[][] Circs;
    Server servidor;
    TranslateTransition moves;
    int finX, finY, finID, finH;
    int iniX, iniY, iniID, iniH;
    int posID1, posID2;
    int phase;
    final String jogador = "jog1";

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tab1 = new tabuleiro();
        jog1 = new jogador();
        jog2 = new jogador();

        
        phase = 0;

        //---------------------------------server-----------------------------------------
        try {
            servidor = new Server();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("pausa");

        //--------------------------IMPRIMIR------------------------------------
        phase = 1;
                pane.getChildren().clear();
        imprime();
        imprimeronda();
        imprimebotao();

//-------------------------------inicio do jogo---------------------------------
    }

    private void pressed(MouseEvent event) {
        if (phase == 2) {
            Rectangle b = (Rectangle) event.getSource();
            iniID = Integer.parseInt(b.getId());
            if (tab1.casas.get(iniID).jogavel && Rects.get(iniID).isDisable() == false) {
                click1();
            }

        } else if (phase == 3) {
            Rectangle b = (Rectangle) event.getSource();
            finID = Integer.parseInt(b.getId());
            if (Rects.get(finID).isDisable() == false){
            click2();
            System.out.println("cringe");
            }
        }
    }

    private void movepeca(int diferencaX, int diferencaY) {
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
        translate.setNode(Circs[iniID][iniH]);
        //playing the transition   
        translate.play();
        translate.setOnFinished(e -> imprime());

        System.out.println("ANIMACAO ACABOU");
    }

    //----------------------------IMPRIME----------------------------------------
    private void imprime() {
        imprimeretangulos();
        imprimepecas();
    }

    public void imprimeretangulos() {
        Rects = new ArrayList<>();
        Rects.clear();

        for (int i = 0; i <= 25; i++) {

            Rects.add(i, new Rectangle(50, 180));
            Rects.get(i).setLayoutX(tab1.casas.get(i).posX);
            Rects.get(i).setLayoutY(tab1.casas.get(i).posY);

            if ("AQUA".equals(tab1.casas.get(i).cor)) {
                Rects.get(i).setFill(Color.AQUA);
            }
            if ("CHOCOLATE".equals(tab1.casas.get(i).cor)) {
                Rects.get(i).setFill(Color.CHOCOLATE);
            }
            /*       if (tab1.casas.get(i).clicavel() && phase == 2) {
                Rects.get(i).setFill(Color.DIMGRAY);
                Rects.get(i).setDisable(true);
                
            }*/
            Rects.get(i).setStroke(Color.BLACK);
            Rects.get(i).setId(String.valueOf(i));
            //Rects.get(i).setDisable(false);

            Rects.get(i).setOnMousePressed(event -> pressed(event));

        }
        pane.getChildren().addAll(Rects);
        System.out.println("rectangulos imprimidos");
    }
//necessario repetir "for" para garantir que as peças estejam no topo da hierarquia

    @FXML
    public void imprimepecas() {
        Circs = new Circle[25][25];
        for (int i = 0; i <= 25; i++) {
            for (int h = 0; h < tab1.casas.get(i).pecas.size(); h++) {

                Circs[i][h] = new Circle(tab1.casas.get(i).pecas.get(h).posX, tab1.casas.get(i).pecas.get(h).posY, 18);

                if ("jog1".compareTo(tab1.casas.get(i).pecas.get(h).jogador) == 0) {
                    Circs[i][h].setFill(Color.WHITE);
                    Circs[i][h].setStroke(Color.BLACK);
                    tab1.casas.get(i).setjogavel(jogador, true);
                }
                if ("jog2".compareTo(tab1.casas.get(i).pecas.get(h).jogador) == 0) {
                    Circs[i][h].setFill(Color.BLACK);
                    Circs[i][h].setStroke(Color.WHITE);
                    tab1.casas.get(i).setjogavel(jogador, false);
                }
                Circs[i][h].setId(i + "" + h);
                pane.getChildren().add(Circs[i][h]);
            }
        }

    }

    public void imprimeronda() {
        ronda = new Label("fase do jogo: " + phase);
        ronda.setLayoutX(300);
        ronda.setLayoutY(180);
        pane.getChildren().add(ronda);
    }

    public void imprimebotao() {
        bdados = new Button("Rodar dados");
        bdados.setLayoutX(400);
        bdados.setLayoutY(180);
        bdados.setOnMouseClicked(event -> rodardados());
        cancelar = new Button("Cancelar");
        cancelar.setLayoutX(100);
        cancelar.setLayoutY(180);
        cancelar.setOnMouseClicked(event -> cancelarjog());
        pane.getChildren().add(bdados);
        pane.getChildren().add(cancelar);

    }

    private void rodardados() {
        tab1.dado1.rodadado();
        tab1.dado2.rodadado();
        phase = 2;
        ronda.setText("fase do jogo: " + phase + "\nDado1:" + tab1.dado1.face + "\nDado2:" + tab1.dado2.face);
        bdados.setDisable(true);
    }

    //--------------------------------------------JOGADAS-----------------------------------------------
    //verifica se posicao de destino encontra se vazia e caso nao esteja vazia se contem peças do oponente
    private boolean clicavel(int posID) {
        if (!tab1.casas.get(posID).vazio()) {
            if (jogador.compareTo(tab1.casas.get(iniID).pecas.get(0).jogador) == 0) {
                return true;
            }else if(finH == 1){
                tab1.casas.get(finID).rempeca();
                return true;

            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    private void click1() {
        
        iniH = tab1.casas.get(iniID).pecas.size() - 1;
        iniX = tab1.casas.get(iniID).pecas.get(iniH).posX;
        iniY = tab1.casas.get(iniID).pecas.get(iniH).posY;
        phase = 3;
        ronda.setText("fase do jogo: " + phase + "\nDado1:" + tab1.dado1.face + "\nDado2:" + tab1.dado2.face);
        
        if ("jog1".equals(jogador)){
        posID1 = iniID + tab1.dado1.face;
        posID2 = iniID + tab1.dado2.face;    
        } 
        else {
        posID1 = iniID - tab1.dado1.face;
        posID2 = iniID - tab1.dado2.face;              
        }
        //correção automatica de foreach feita pelo netbeans (functional operation ?)
        Rects.forEach((f) -> {
            f.setDisable(true);
                    System.out.print(f.getId());
            
        });

        Rects.get(iniID).setFill(Color.RED);
        if (tab1.dado1.uso == false && clicavel(posID1)) {
            Rects.get(posID1).setFill(Color.DARKSEAGREEN);
            Rects.get(posID1).setDisable(false);
        }
        if (tab1.dado2.uso == false && clicavel(posID2)) {
            Rects.get(posID2).setFill(Color.DARKSEAGREEN);
            Rects.get(posID2).setDisable(false);
        }
    }

    private void click2() {
        System.out.print(finID);
        //Adicionar peça para acertar a posição da animação final            
        tab1.casas.get(finID).addpecablank();

        //verificar o  H (id da ultima peça dentro da casa)
        finH = tab1.casas.get(finID).pecas.size() - 1;
        finX = tab1.casas.get(finID).pecas.get(finH).posX;
        finY = tab1.casas.get(finID).pecas.get(finH).posY;
        //Remove peça blank
        tab1.casas.get(finID).rempeca();
        if ("jog1".compareTo(jogador) == 0) {
            tab1.casas.get(finID).addpecabranca();
        }
        if ("jog2".compareTo(jogador) == 0) {
            tab1.casas.get(finID).addpecapreta();
        }
        

        //Remove peça do inicio
        tab1.casas.get(iniID).rempeca();
        Rects.get(finID).setFill(Color.BLUE);

        //verificar que dado escolheu
        if (finID == posID1 && tab1.dado1.uso == false) {
            tab1.dado1.uso = true;
            phase = 2;
        } else if (finID == posID2) {
            tab1.dado2.uso = true;
            phase = 2;
        }
        if (tab1.dado1.uso && tab1.dado2.uso) {
            phase = 4;
            bdados.setText("Passar Jogada");
            bdados.setDisable(false);
            bdados.setOnMouseClicked(event1 -> passarjogada());
        }

        ronda.setText("fase do jogo: " + phase + "\nDado1:" + tab1.dado1.face + "\nDado2:" + tab1.dado2.face);
        movepeca(finX - iniX, finY - iniY);


        /*  for (Rectangle f : Rects) {
                f.setDisable(true);
            }*/
    }

    private void cancelarjog() {
        if (phase == 3) {
            imprime();
            phase = 2;
        ronda.setText("fase do jogo: " + phase + "\nDado1:" + tab1.dado1.face + "\nDado2:" + tab1.dado2.face);
        }
    }

    private void passarjogada() {
                bdados.setDisable(true);
        try {
            servidor.enviarPecas(tab1);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        phase = 5;
        ronda.setText("a esperar pelo outro utilizador \n ronda:" + phase);

        receberjogada();
    }

    private void receberjogada() {
        try {
            tab1.casas = servidor.receberpecas();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        pane.getChildren().clear();
        imprime();
        imprimeronda();
        imprimebotao();
        bdados.setDisable(false);
        bdados.setText("rodardados");
        bdados.setOnMouseClicked(event -> rodardados());
        phase = 1;
        tab1.dado1.uso = false;
        tab1.dado2.uso = false;        
        ronda.setText("jogada recebida \n ronda:" + phase);

    }

}
