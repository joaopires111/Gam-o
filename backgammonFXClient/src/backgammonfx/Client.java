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

    Socket s;
    ObjectInputStream din;
    ObjectOutputStream dout;
    packet p;

    ArrayList<casa> casas;
    static final int PORT = 3192;

    public Client() throws Exception {
        StartServer();
        enviar();
    }
    //-----------------------START SERVIDOR--------------------------
    public void StartServer() throws ClassNotFoundException {
        try {

            s = new Socket("localhost", PORT);
            dout = new ObjectOutputStream(s.getOutputStream());
            din = new ObjectInputStream(s.getInputStream());
            System.out.println("CLIENT up and running");

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    //-----------------------CLOSE SERVIDOR--------------------------
    public void CloseServer() throws ClassNotFoundException {
        try {
            s.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    //-------------------------ENVIAR TESTE-------------------------------------
    public void enviar() throws IOException, ClassNotFoundException {

        System.out.println("A ENVIAR");
        p = new packet("A bueno ADIOS MASTER :c");
        dout.writeObject(p);
        System.out.println("mensage enviada");

    }
    //-------------------------RECEBER CASAS-------------------------------------
    public ArrayList<casa> receberpecas() throws IOException, ClassNotFoundException {


        System.out.println("A RECEBER casas");
        casas = (ArrayList<casa>) din.readObject();
        System.out.println("mensage recebida:");
        return casas;
    }
}
