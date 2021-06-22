/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

/**
 *
 * @author User
 */
public class dado {
    int face;
    
    public dado(){
    face = 1;
    }
    
    public int rodadado(){
    face = (int) Math.floor(Math.random() * 6) + 1;
        return face;
    
    }
    
}
