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
    int posX, posY;
    
    public peca(String jogador, int posX, int posY) {
        
        this.posX = posX;
        this.posY = posY;
        c = new Circle(posX, posY, 18, Color.CORAL);
        this.jogador = jogador;
        
        if ("jog1".compareTo(jogador) == 0) {
            c.setFill(Color.WHITE);
        }
        if ("jog2".compareTo(jogador) == 0) {
            c.setFill(Color.BLACK);
        }
        
        c.setStroke(Color.BLACK);
    }
    
    public void draw() {
        c.setTranslateX(posX);
        c.setTranslateY(posX);
    }
}
