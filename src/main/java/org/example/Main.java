package org.example;


import org.example.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        try{
            server.httpConnection();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}