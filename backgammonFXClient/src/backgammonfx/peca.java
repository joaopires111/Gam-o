/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import java.io.Serializable;

/**
 *
 * @author User
 */
public class peca implements Serializable{

    String jogador;
    int posX, posY;
    public peca(String jogador, int posX, int posY) {
        
        this.posX = posX;
        this.posY = posY;
        this.jogador = jogador;

    }
}
