/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import java.util.ArrayList;

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
        for (int i = 0; i <= 25; i++) {
            if (i > 0 && i <= 6) {
                casas.add(new casa(i, 750 - (i * 50), 20));
            }
            if (i > 6 && i <= 12) {
                casas.add(new casa(i, 750 - (i * 50) - 50, 20));
            }
            if (i > 12 && i <= 18) {
                casas.add(new casa(i, (i-12) * 50, 380));
            }
            if (i > 18 && i <= 24) {
                casas.add(new casa(i, ((i - 12) * 50) + 50, 380));
            }

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
            } else if (jogador == "jog2") {
                casas.get(chegada).addpecapreta();
            }
        }

    }
}
