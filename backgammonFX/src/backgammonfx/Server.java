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
import java.io.*;
import java.net.*;

public class Server {

    ServerSocket ss;
    Socket s;
    ObjectInputStream din;
    ObjectOutputStream dout;
    packet received;
    static final int PORT = 3192;

    public Server() throws Exception {
        

        System.out.println("server up and running");
        ss = new ServerSocket(PORT);
        s = ss.accept();//establishes connection  

        dout = new ObjectOutputStream(s.getOutputStream());
        din = new ObjectInputStream(s.getInputStream());

        received = (packet) din.readObject();
        System.out.println(received.message);
        
        ss.close();

    }
    
    public void servertab(tabuleiro h) throws IOException{
                System.out.println("server up and running");
        ss = new ServerSocket(PORT);
        s = ss.accept();//establishes connection  

        dout = new ObjectOutputStream(s.getOutputStream());
        din = new ObjectInputStream(s.getInputStream());
        
            System.out.println(h.casas.get(5).id);
        dout.writeObject(h.casas);
        
        System.out.println("server up and running");
        ss.close();
    
    
    }

}
