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
public class packet implements Serializable {
    String message;
    public packet(String message){
    this.message = message;
    }
    
}
