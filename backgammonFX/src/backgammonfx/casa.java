/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author User
 */
public class casa {

    ArrayList<peca> pecas;
    int id;
    int posX, posY;
    Rectangle rect;

    public casa(int id, int posX, int posY) {
        pecas = new ArrayList<peca>();
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        
        
        rect = new Rectangle(40, 180);
        
        if (id <= 12) {
            rect.setFill(Color.BLUE);
        }
        else {
            rect.setFill(Color.ORANGE);
        }
        
        rect.setStroke(Color.BLACK);
        
        

    }

    public void addpecabranca() {
        pecas.add(new peca("jog1", posX, posY));
    }

    public void addpecapreta() {
        pecas.add(new peca("jog2", posX, posY));
    }

    public void rempeca() {
        pecas.remove(pecas.size() - 1);
    }

}
