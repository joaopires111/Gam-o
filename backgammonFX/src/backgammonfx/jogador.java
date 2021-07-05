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
public class jogador {

    ArrayList<peca> pecas;
    int posX =  300;
    int posY =  100;
    int id;
    String jogador;
    public jogador(int id, String jogador) {
        pecas = new ArrayList<>();
        this.id = id;
        this.jogador = jogador;
    }  
        public void addpecabranca() {

        pecas.add(new peca("jog1", posX + 25, posY + correcaoposy()));
    }

    public void addpecapreta() {
        pecas.add(new peca("jog2", posX + 25, posY + correcaoposy()));
    }

    public void addpecablank() {

        pecas.add(new peca("", posX + 25, posY + correcaoposy()));
    }

    public int correcaoposy() {
        int posycorr = pecas.size() * 10;
        if (id > 12) {
            posycorr *= -1;
            posycorr += 160;
        } else {
            posycorr += 20;
        }
        return posycorr;
    }

    public void rempeca() {
        pecas.remove(pecas.size() - 1);
    }
}
