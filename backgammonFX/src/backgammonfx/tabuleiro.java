/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

/**
 *
 * @author User
 */
public class tabuleiro {

    ArrayList<casa> casas;
    dado dado1;
    dado dado2;
    public tabuleiro() {
        casas = new ArrayList<casa>();
        dado1 = new dado();
        dado2 = new dado();
        iniciapecas();

        
    }

    public void iniciapecas() {
        for (int i = 0; i <= 24; i++) {
            casas.add(new casa(i));
        }
        //pecas brancas
        for (int i = 0; i < 2; i++) {
            casas.get(24).addpecabranca();
        }
        for (int i = 0; i < 5; i++) {
            casas.get(13).addpecabranca();
        }
        for (int i = 0; i < 3; i++) {
            casas.get(8).addpecabranca();
        }
        for (int i = 0; i < 5; i++) {
            casas.get(6).addpecabranca();
        }

        //pecas pretas
        for (int i = 0; i < 2; i++) {
            casas.get(1).addpecapreta();
        }
        for (int i = 0; i < 5; i++) {
            casas.get(12).addpecapreta();
        }
        for (int i = 0; i < 3; i++) {
            casas.get(17).addpecapreta();
        }
        for (int i = 0; i < 5; i++) {
            casas.get(19).addpecapreta();
        }

    }

    public void jogada(String jogador, int casapart, int dado) {
        int chegada = casapart + dado;

        if (casas.get(chegada).pecas.isEmpty()) {
            casas.get(casapart).rempeca();
            if (jogador == "jog1") {
                casas.get(chegada).addpecabranca();
            }
            else if (jogador == "jog2") {
                casas.get(chegada).addpecapreta();
            }
        }
        

    }
}
