package org.example.server;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{
    public static void main(String[]args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress("localhost",8067), 0);

        httpServer.createContext("/",null);
        httpServer.setExecutor(Executors.newSingleThreadExecutor());
        httpServer.start();

    }
    public class Handler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())){

            }
        }
    }
}
