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
        casas = new ArrayList<>();
        dado1 = new dado();
        dado2 = new dado();
    }

}
