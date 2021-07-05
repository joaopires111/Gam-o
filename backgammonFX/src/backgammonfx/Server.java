/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    Socket s;
    ServerSocket ss;
    ObjectInputStream din;
    ObjectOutputStream dout;
    packet p;
    static final int PORT = 3192;
    ArrayList<casa> casas;
    public Server() throws Exception {
        StartServer();
        receber();
    }
    //-----------------------START SERVIDOR--------------------------
    public void StartServer() throws ClassNotFoundException {
        try {

            ss = new ServerSocket(PORT);
            s = ss.accept();//establishes connection  

            dout = new ObjectOutputStream(s.getOutputStream());
            din = new ObjectInputStream(s.getInputStream());
            System.out.println("server up and running");

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

    //---------------------------------RECEBER TESTE-----------------------------
    public void receber() throws IOException, ClassNotFoundException {

        System.out.println("A RECEBER");
        p = (packet) din.readObject();
        System.out.println("mensage recebida:" + p.message);

    }
    //------------------------ENVIAR CASAS---------------------------------------
    public void enviarPecas(tabuleiro tab1) throws IOException, ClassNotFoundException {

        System.out.println("A ENVIAR CASAS");
        dout.writeObject(tab1.casas);
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
