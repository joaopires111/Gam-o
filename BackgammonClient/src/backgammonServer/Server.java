/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonServer;

import java.io.*;
import java.net.*;

public class Server {

    ServerSocket ss;
    Socket s;
    DataInputStream din;
    DataOutputStream dout;
    BufferedReader br;
    public Server() {
        try {
            ss = new ServerSocket(6666);
            s = ss.accept();//establishes connection   
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(System.in));

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void getinput() throws IOException {

        int h = (int) din.readInt();
        System.out.println("message= " + h);
    }
}
