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
public class casa {

    ArrayList<peca> pecas;
    int id;
    public casa(int id) {
        pecas = new ArrayList<peca>();
        this.id = id;
        
    }

    public void addpecabranca() {
        pecas.add(new peca("jog1"));
    }

    public void addpecapreta() {
        pecas.add(new peca("jog2"));
    }

    public void rempeca() {
        pecas.remove(pecas.size() - 1);
    }

}
