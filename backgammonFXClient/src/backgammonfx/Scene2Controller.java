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
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Scene2Controller implements Initializable {

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
    Label ronda, labelfim;
    Button bdados;
    tabuleiro tab1;
    jogador jog, adv;
    packet p;
    ArrayList<Rectangle> Rects;
    @FXML
    static Circle[][] Circs;
    Client cliente;
    casa casacopy;
    TranslateTransition moves;
    int finX, finY, finID, finH;
    int iniX, iniY, iniID, iniH;
    int posID1, posID2;
    int phase;
    boolean fimjogo;
    final String jogador = "jog2";

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //---------------------------------cliente envia msg de teste-----------------------------------------
        try {
            cliente = new Client();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        Button iniciarjogo = new Button("Receber primeira jogada");
        iniciarjogo.setLayoutX(350);
        iniciarjogo.setLayoutY(200);
        iniciarjogo.setOnMouseClicked(even -> iniciarjogo());
        pane.getChildren().add(iniciarjogo);

    }

    public void iniciarjogo() {

        tab1 = new tabuleiro();
        labelfim = new Label("is" + fimjogo);
        labelfim.setLayoutX(600);
        labelfim.setLayoutY(180);
        fimjogo = false;
        phase = 0;
        //---------------------------------Client recebe pecas e jogadores-----------------------------------------     
            receberjogada();
        //--------------------------IMPRIMIR------------------------------------
        phase = 1;
        pane.getChildren().clear();
        imprime();
        imprimeronda();
        imprimebotao();
    }

    private void pressed(MouseEvent event) {
        if (phase == 2) {
            Rectangle b = (Rectangle) event.getSource();
            iniID = Integer.parseInt(b.getId());
            if (tab1.casas.get(iniID).jogavel) {
                click1();
            }

        } else if (phase == 3) {
            Rectangle b = (Rectangle) event.getSource();
            finID = Integer.parseInt(b.getId());

            if ((finID < 25 && finID != 0) || fimjogo) {
                click2();
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

    private void animadados() {
        //Instantiating TranslateTransition class   
        TranslateTransition translate = new TranslateTransition();
        //shifting the X coordinate of the centre of the circle by 400   
        translate.setByX(20);
        translate.setByY(20);
        //setting the duration for the Translate transition   
        translate.setDuration(Duration.millis(50));
        //setting cycle count for the Translate transition   
        translate.setCycleCount(10);
        //the transition will set to be auto reversed by setting this to true   
        translate.setAutoReverse(true);
        //setting Circle as the node onto which the transition will be applied  
        translate.setNode(ronda);
        //playing the transition   
        translate.play();

        translate.setOnFinished(e -> imprimedados());

        System.out.println("ANIMACAO ACABOU");
    }

    //----------------------------IMPRIME----------------------------------------
    private void imprime() {
        imprimeretangulos();
        imprimepecas();
        labelfim.setText("Fase final:" + fimjogo);
        pane.getChildren().remove(labelfim);
        pane.getChildren().add(labelfim);

    }

    public void criardados() {
        Rectangle dado1 = new Rectangle();
        Rectangle dado2 = new Rectangle();
        dado1.setLayoutX(200);
        dado2.setLayoutY(200);
        pane.getChildren().add(dado1);

    }
//Imprime os retangulos de ambos os jogadores

    public void imprimeretangulosjog(int id, jogador jog) {
        Rects.add(id, new Rectangle(50, 180));
        Rects.get(id).setLayoutX(jog.posX);
        Rects.get(id).setLayoutY(jog.posY);

        if ("jog1".equals(jog.jogador)) {
            Rects.get(id).setFill(Color.BLACK);
            Rects.get(id).setStroke(Color.GOLD);
            Rects.get(id).setStrokeWidth(3);
        }
        if ("jog2".equals(jog.jogador)) {
            Rects.get(id).setFill(Color.RED);
            Rects.get(id).setStroke(Color.GOLD);
            Rects.get(id).setStrokeWidth(3);
        }
        Rects.get(id).setId(String.valueOf(id));

        Rects.get(id).setOnMousePressed(event -> pressed(event));
    }

    public void imprimepecasjog(int id, jogador jog) {
        for (int h = 0; h < jog.pecas.size(); h++) {

            Circs[id][h] = new Circle(jog.pecas.get(h).posX, jog.pecas.get(h).posY, 18);

            if ("jog1".compareTo(jog.jogador) == 0) {
                Circs[id][h].setFill(Color.WHITE);
                Circs[id][h].setStroke(Color.BLACK);
            }
            if ("jog2".compareTo(jog.jogador) == 0) {
                Circs[id][h].setFill(Color.BLACK);
                Circs[id][h].setStroke(Color.WHITE);
            }
            Circs[id][h].setId(id + "" + h);

            pane.getChildren().add(Circs[id][h]);
        }

    }

    public void imprimeretangulos() {
        Rects = new ArrayList<>();
        Rects.clear();
        pane.getChildren().removeIf(node -> node instanceof Rectangle);
        for (int i = 0; i <= 25; i++) {

            Rects.add(i, new Rectangle(50, 180));
            Rects.get(i).setLayoutX(tab1.casas.get(i).posX);
            Rects.get(i).setLayoutY(tab1.casas.get(i).posY);

            if ("AQUA".equals(tab1.casas.get(i).cor)) {
                Rects.get(i).setFill(Color.AQUAMARINE);
                Rects.get(i).setStroke(Color.BLACK);
            }
            if ("CHOCOLATE".equals(tab1.casas.get(i).cor)) {
                Rects.get(i).setFill(Color.BROWN);
                Rects.get(i).setStroke(Color.BLACK);
            }
            if ("".equals(tab1.casas.get(i).cor)) {
                Rects.get(i).setOpacity(0.10);
            }
            Rects.get(i).setId(String.valueOf(i));

            Rects.get(i).setOnMousePressed(event -> pressed(event));
        }   
        //unica alteração entre projeto do cliente e do servidor
        //jog1 tem de ser primeiro
        imprimeretangulosjog(adv.id, adv);      
        imprimeretangulosjog(jog.id, jog);  
        pane.getChildren().addAll(Rects);
        if(tab1.dado1.rect != null){
        tab1.dado1.redraw(pane);
        tab1.dado2.redraw(pane);    }     
    }
//necessario repetir "for" para garantir que as peças estejam no topo da hierarquia

    @FXML
    public void imprimepecas() {
        Circs = new Circle[30][16];
        pane.getChildren().removeIf(node -> node instanceof Circle);
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
        imprimepecasjog(jog.id, jog);
        imprimepecasjog(adv.id, adv);
        //IMPRIMIR CIRCULOS DE CADA JOGADOR ---------------------------------------------------------------------------------------------------------

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
        bdados.setLayoutY(187);
        bdados.setOnMouseClicked(event -> animadados());
        cancelar = new Button("Cancelar");
        cancelar.setLayoutX(500);
        cancelar.setLayoutY(187);
        cancelar.setOnMouseClicked(event -> cancelarjog());
        pane.getChildren().add(bdados);
        pane.getChildren().add(cancelar);

    }

    private void imprimedados() {

        tab1.dado1.rodadado(pane, 1);
        tab1.dado2.rodadado(pane, 2);
        phase = 2;
        ronda.setText("fase do jogo: " + phase + "\nDado1:" + tab1.dado1.face + "\nDado2:" + tab1.dado2.face);
        bdados.setText("Passar Jogada");
        bdados.setDisable(false);
        bdados.setOnMouseClicked(event1 -> passarjogada());

    }

    //--------------------------------------------JOGADAS-----------------------------------------------
    //verifica se posicao de destino encontra se vazia e caso nao esteja vazia se contem peças do oponente
    private boolean clicavel(int posID) {
        try {
            if (!tab1.casas.get(posID).pecas.isEmpty()) {
                if (jogador.compareTo(tab1.casas.get(posID).pecas.get(0).jogador) == 0) {
                    return true;
                } else if (tab1.casas.get(posID).pecas.size() == 1) {
                    System.out.println("SIZE :" + tab1.casas.get(posID).pecas.size());
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }

    }

    private void click1() {

        iniH = tab1.casas.get(iniID).pecas.size() - 1;
        iniX = tab1.casas.get(iniID).pecas.get(iniH).posX;
        iniY = tab1.casas.get(iniID).pecas.get(iniH).posY;
        phase = 3;
        ronda.setText("fase do jogo: " + phase + "\nDado1:" + tab1.dado1.face + "\nDado2:" + tab1.dado2.face);

        //Caso tenha casas no meio fica automaticamente selecionado o centro;
        if ("jog1".equals(jogador)) {
            if (!tab1.casas.get(0).vazio()) {
                ronda.setText("Tem de jogar a casa do meio");
                iniID = 0;
                iniH = tab1.casas.get(iniID).pecas.size() - 1;
                iniX = tab1.casas.get(iniID).pecas.get(iniH).posX;
                iniY = tab1.casas.get(iniID).pecas.get(iniH).posY;
            }

            posID1 = iniID + tab1.dado1.face;
            posID2 = iniID + tab1.dado2.face;

        } else {
            if (!tab1.casas.get(25).vazio()) {
                ronda.setText("Tem de jogar a casa do meio");
                iniID = 25;
                iniH = tab1.casas.get(iniID).pecas.size() - 1;
                iniX = tab1.casas.get(iniID).pecas.get(iniH).posX;
                iniY = tab1.casas.get(iniID).pecas.get(iniH).posY;
            }
            posID1 = iniID - tab1.dado1.face;
            posID2 = iniID - tab1.dado2.face;

        }

        //correção automatica de foreach feita pelo netbeans (functional operation ?)
        Rects.forEach((f) -> {
            f.setDisable(true);

        });
        //desbloqueio da ultima posicao
        Rects.get(iniID).setFill(Color.RED);
        if (fimjogo) {
            if (tab1.dado1.uso == false && posID1 >= 25 || posID1 <= 0) {
                Rects.get(jog.id).setDisable(false);
                Rects.get(jog.id).setFill(Color.DARKSEAGREEN);
                                System.out.println("id do jog ativado" + jog.id);
            }
            if (tab1.dado2.uso == false && posID2 >= 25 || posID2 <= 0) {
                Rects.get(jog.id).setDisable(false);
                Rects.get(jog.id).setFill(Color.DARKSEAGREEN);
                                System.out.println("id do jog ativado" + jog.id);
            }
        }

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

        if (finID == 0 || finID == 25) {
            //nao é aceite            
            System.out.println("wowoooooooooooooooooooo");

        } else if (finID == 26 || finID == 27) {
            if (finID == jog.id) {
                clickfimjogo();

                movepeca(finX - iniX, finY - iniY);
            }
        } else {
            clicknormal();

            movepeca(finX - iniX, finY - iniY);
        }

        /*  for (Rectangle f : Rects) {
                f.setDisable(true);
            }*/
    }

    public void clickfimjogo() {
        System.out.println("weweweeeeeeeeeeeeeeeeee");
        System.out.println("weweweeeeeeeeeeeeeeeeee");
        //Adicionar peça para acertar a posição da animação final    
        jog.addpecablank();
        //verificar o  H (id da ultima peça dentro da casa)
        finH = jog.pecas.size() - 1;
        finX = jog.pecas.get(finH).posX;
        finY = jog.pecas.get(finH).posY;
        //Remove peça blank
        jog.rempeca();
        if ("jog1".compareTo(jogador) == 0) {
            jog.addpecabranca();
        }
        if ("jog2".compareTo(jogador) == 0) {
            jog.addpecapreta();
        }
        //Remove peça do inicio
        tab1.casas.get(iniID).rempeca();
        Rects.get(finID).setFill(Color.BLUE);
        //verificar que dado escolheu
        
        //caso a pos de destino seja 27 as peças estao a moverse na direcao contraria entao é necessario verificaçao de dados diferente
        if ( finID == 27){
        posID1 = posID1*-1 + 26;
        posID2 = posID2*-1 + 26;        
        }
            if (posID1 <= posID2) {
                if (posID1 + 1 >= finID && tab1.dado1.uso == false) {
                    tab1.dado1.usadado();
                    phase = 2;
                } else if (posID2 + 1 >= finID) {
                    tab1.dado2.usadado();
                    phase = 2;
                }
            } else if (posID1 >= posID2) {
                if (posID2 + 1 >= finID && tab1.dado2.uso == false) {
                    tab1.dado2.usadado();
                    phase = 2;
                } else if (posID1 + 1 >= finID) {
                    tab1.dado1.usadado();
                    phase = 2;
                }
            }
        if (tab1.dado1.uso && tab1.dado2.uso) {
            phase = 4;
        }

        condicaovitoria(jog);

    }

    public void clicknormal() {
        //Adicionar peça para acertar a posição da animação final    
        tab1.casas.get(finID).addpecablank();
        //verificar o  H (id da ultima peça dentro da casa)
        finH = tab1.casas.get(finID).pecas.size() - 1;
        finX = tab1.casas.get(finID).pecas.get(finH).posX;
        finY = tab1.casas.get(finID).pecas.get(finH).posY;
        //Remove peça blank
        tab1.casas.get(finID).rempeca();
        comivel();
        if ("jog1".compareTo(jogador) == 0) {
            tab1.casas.get(finID).addpecabranca();

        }
        if ("jog2".compareTo(jogador) == 0) {
            tab1.casas.get(finID).addpecapreta();
        }

        //Remove peça do inicio
        tab1.casas.get(iniID).rempeca();
        //verifica se o jogo se encontra na fase final (apos estar na fase final nao verifica mais)
        if (fimjogo == false) {
            fimjogo = tab1.fimdejogo(jogador);
        }
        Rects.get(finID).setFill(Color.BLUE);
        //verificar que dado escolheu (a segunda condição é para caso os dois dados sejam iguais)
        if (finID == posID1 && tab1.dado1.uso == false) {
                    tab1.dado1.usadado();
            phase = 2;
        } else if (finID == posID2) {
                    tab1.dado2.usadado();
            phase = 2;
        }

        if (tab1.dado1.uso && tab1.dado2.uso) {
            phase = 4;
        }

    }

    private void cancelarjog() {
        if (phase == 3) {
            imprime();
            phase = 2;
        }
    }
    //---------------------------------server-----------------------------------------

    private void passarjogada() {
        bdados.setDisable(true);
        //envia pecas
        try {
            cliente.enviarPecas(tab1);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        //envia jog      
        try {
            cliente.enviarJog(jog);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        //envia adv        
        try {
            cliente.enviarJog(adv);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }          

        phase = 5;
        ronda.setText("a esperar pelo outro utilizador \n ronda:" + phase);

        receberjogada();
    }

    private void receberjogada() {
        //recebe pecas
        try {
            tab1.casas = cliente.receberpecas();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        //recebe jog (adv)
        try {
            adv = cliente.receberJog();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        //recebe adv (jog) 
        try {
            jog = cliente.receberJog();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        
        pane.getChildren().clear();
        imprime();
        imprimeronda();
        imprimebotao();
        bdados.setDisable(false);
        bdados.setText("rodardados");
        bdados.setOnMouseClicked(event -> animadados());
        phase = 1;
        tab1.dado1.resetdado();
        tab1.dado2.resetdado();
        ronda.setText("jogada recebida \n ronda:" + phase +"\njog"+jog.jogador + "\nadv"+ adv.jogador);

    }
    //---------------------------------server-----------------------------------------

    private void comivel() {
        //Só há uma situação em que a peça pousa numa peça adversária (clicavel) que é quando esta é comivel
        if (!tab1.casas.get(finID).pecas.isEmpty()) {
            if (tab1.casas.get(finID).pecas.get(finH - 1).jogador.compareTo(jogador) != 0) {
                //finH é a posicao da peça em branco que foi removida
                if ("jog1".equals(jogador)) {
                    tab1.casas.get(25).addpecapreta();
                }
                if ("jog2".equals(jogador)) {
                    tab1.casas.get(0).addpecabranca();
                }
                //remove a peça do adversario
                tab1.casas.get(finID).rempeca();
            }
        }
    }

    //verifica se todas as peças brancas se encontram no ultimo quadrante do tabuleiro
    private void condicaovitoria(jogador jog) {
        if (jog.pecas.size() >= 15) {
            ronda.setTextFill(Color.GOLD);
            ronda.setScaleX(5);
            ronda.setScaleY(5);
            ronda.setLayoutX(100);
            ronda.setRotate(0.1);
            ronda.setText(jog + "GANHOU !!!!");
            animavitoria();
            Rects.forEach((f) -> {
                f.setDisable(true);
                System.out.print(f.getId());

            });
            try {
                cliente.CloseServer();
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            }
        }

    }

    private void animavitoria() {
        //Instantiating TranslateTransition class   
        TranslateTransition translate = new TranslateTransition();
        //shifting the X coordinate of the centre of the circle by 400   
        translate.setByX(200);
        //setting the duration for the Translate transition   
        translate.setDuration(Duration.millis(500));
        //setting cycle count for the Translate transition   
        translate.setCycleCount(100);
        //the transition will set to be auto reversed by setting this to true   
        translate.setAutoReverse(true);
        //setting Circle as the node onto which the transition will be applied  
        translate.setNode(ronda);
        //playing the transition   
        translate.play();
        System.out.println("ANIMACAO ACABOU");
    }

}
