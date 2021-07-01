/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client {

    static ArrayList<casa> casas;  
    Socket s;
    ObjectInputStream din;
    ObjectOutputStream dout;
    packet received;
    static final int PORT = 3192;

    public Client() throws Exception {

        System.out.println("client up and running");

        s = new Socket("localhost", PORT);

        System.out.println("client up and running");

        dout = new ObjectOutputStream(s.getOutputStream());
        din = new ObjectInputStream(s.getInputStream());

        packet sent = new packet("okokokokokokokok");
        dout.writeObject(sent);

        System.out.println("client up and running");
        dout.close();
        s.close();

    }

    public ArrayList<casa> getcasas() throws Exception {
        System.out.println("client up and running");

        s = new Socket("localhost", PORT);

        System.out.println("client up and running");

        dout = new ObjectOutputStream(s.getOutputStream());
        din = new ObjectInputStream(s.getInputStream());

        casas = (ArrayList<casa>) din.readObject();
       if (casas == null){System.out.println("NAO DEU");}
        System.out.println(casas.get(1).id);
         dout.close();
        s.close();       
        return casas;


    }
}
