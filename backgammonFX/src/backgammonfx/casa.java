/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class casa implements Serializable {

    ArrayList<peca> pecas;
    final int id;
    int posX, posY;
    String cor;
    boolean jogavel;

    public casa(int id, int posX, int posY, String cor) {
        pecas = new ArrayList<>();
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.cor = cor;
        jogavel = true;

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

    public boolean vazio() {
        return pecas.isEmpty();
    }

    public void setjogavel(String jogador, boolean jogavel) {
        if (jogador == "jog1") {
            this.jogavel = jogavel;

        }
        if (jogador == "jog2") {
            this.jogavel = !jogavel;

        }

    }
}
