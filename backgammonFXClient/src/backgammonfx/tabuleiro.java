/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 *
 * @author User
 */
public class tabuleiro {

    ArrayList<casa> casas;
    dado dado1;
    dado dado2;

    public tabuleiro() {
        casas = new ArrayList<>();
        dado1 = new dado();
        dado2 = new dado();
    }

    public void jogada(String jogador, int casapart, int dado) {
        int chegada = casapart + dado;

        if (casas.get(chegada).pecas.isEmpty()) {
            casas.get(casapart).rempeca();
            if (jogador == "jog1") {
                casas.get(chegada).addpecabranca();
            } else if (jogador == "jog2") {
                casas.get(chegada).addpecapreta();
            }
        }

    }
}
