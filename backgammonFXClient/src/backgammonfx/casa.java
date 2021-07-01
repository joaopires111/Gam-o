/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author User
 */
public class casa implements Serializable {

    ArrayList<peca> pecas;
    int id;
    int posX, posY;
    Rectangle rect;

    public casa(int id, int posX, int posY) {
        pecas = new ArrayList<peca>();
        this.id = id;
        this.posX = posX;
        this.posY = posY;

        rect = new Rectangle(50, 180);
        rect.setLayoutX(posX);
        rect.setLayoutY(posY);

        if (id > 0 && id <= 12) {
            rect.setFill(Color.AQUA);
        } else if (id < 25) {
            rect.setFill(Color.CORAL);
        }

        rect.setStroke(Color.BLACK);
         rect.setOnMousePressed(event -> pressed(event, id));
    }

    public void addpecabranca() {

        pecas.add(new peca("jog1", posX + 25, posY + correcaoposy()));
    }

    public void addpecapreta() {
        pecas.add(new peca("jog2", posX + 25, posY + correcaoposy()));
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
        private void pressed(MouseEvent event, int id) {
        System.out.println(id);
    }

}
