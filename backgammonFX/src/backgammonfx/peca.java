/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author User
 */
public class peca {

    String jogador;
    Circle c;

    public peca(String jogador) {

        c = new Circle(0, 0, 18, Color.CORAL);
        this.jogador = jogador;
        
        
        if ("jog1".compareTo(jogador) == 0) {
            c.setFill(Color.WHITE);
        }
        if ("jog2".compareTo(jogador) == 0) {
            c.setFill(Color.BLACK);
        }
        c.setStroke(Color.BLACK);
        

    }

}
