/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 */
public class dado {

    int face;
    boolean uso;
    Rectangle rect;
    Label num;

    public dado() {
        resetdado();
    }

    public void rodadado(Pane pane, int pos) {
                uso = false;
        face = (int) Math.floor(Math.random() * 6) + 1;
        rect = new Rectangle(30, 30);   
        rect.setFill(Color.WHITESMOKE);
        rect.setStroke(Color.BLACK);
        num = new Label(""+face);
        rect.setLayoutX(100 +40*pos);
        rect.setLayoutY(185);
        num.setLayoutX(100 + 40*pos + 10);
        num.setLayoutY(185 + 6);
        num.setScaleX(3);
        num.setScaleY(3);
        pane.getChildren().add(rect);
        pane.getChildren().add(num);

    }
    public void redraw(Pane pane) {

        pane.getChildren().remove(rect);
        pane.getChildren().remove(num);        
        pane.getChildren().add(rect);
        pane.getChildren().add(num);
    }
    
        public void usadado(){
        uso = true;
        rect.setFill(Color.GREY);
    }
            public void resetdado(){
                face = 0;
    rect = null;
    num = null;
    uso = false;
    }

}
