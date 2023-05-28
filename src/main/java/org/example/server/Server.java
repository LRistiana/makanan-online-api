package org.example.server;

import java.io.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.controller.ProductsController;
import org.example.controller.UsersController;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;


public class Server{
    private static final int PORT = 8067;
    private UsersController usersController;
    private ProductsController productsController;

    public Server(){
        DatabaseManager databaseManager = new DatabaseManager("database/db_makanan_online.db");
        usersController = new UsersController(databaseManager);
        productsController = new ProductsController(databaseManager);
    }
    public void httpConnection() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress("localhost",PORT), 0);
        httpServer.createContext("/",new Handler());
        httpServer.setExecutor(Executors.newSingleThreadExecutor());
        httpServer.start();
    }
    private class Handler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String response = "";


            if (path.equals("/users") && method.equals("GET")){
                String type = exchange
                        .getRequestURI()
                        .getQuery();
                type = type.substring(type.lastIndexOf('=')+1);

                if (type != null && (type.equals("Buyer") || type.equals("Seller"))){
                    response = usersController.getUser(type)
                            .toString();
                }else {
                    JSONArray jsonArray = usersController.getUser();
                    response = jsonArray.toString();
//                    response=type;
                }
            }
            else if (path.matches("/users/\\d+") && method.equals("GET")){
                int userId = Integer.parseInt(path.substring(path.lastIndexOf('/')+1));
                JSONArray jsonArray = usersController.getUser(userId);
                if ( jsonArray != null){
                    response = jsonArray.toString();
                }else {
                    response = " User Not Found";
                }

            }
            else if (path.matches("/users/\\d+") && method.equals("DELETE")) {
                int userId = Integer.parseInt(path.substring(path.lastIndexOf('/')+1));
//                boolean done = usersController.deleteUser(userId);
                boolean done = usersController.deleteUser(userId);
                if (done){
                    response = "User id =" + userId + " deleted";
                }else {
                    response = "User Not Found";
                }
            }
            else if(path.equals("/users") && method.equals("POST")){
                JSONObject requestBodyJson = parseRequestBody(exchange.getRequestBody());
                if (usersController.addUser(requestBodyJson)){
                    response = "User Added";
                }else {
                    response = "Invalid Data";
                }
            }
            else if (path.matches("/users/\\d+")&& method.equals("PUT")) {
                JSONObject requestBodyJson = parseRequestBody(exchange.getRequestBody());
                int idUser = Integer.parseInt(path.substring(path.lastIndexOf('/')+1));
//
                if (usersController.updateUser(idUser,requestBodyJson)){
                    response = "Data User Updated";
                }else {
                    response = "Invalid Data";
                }
            }
            else if (path.equals("/users") && method.equals("GET")) {
                String type = exchange.getRequestURI().getQuery();
                if (type != null && (type.equals("Buyer") || type.equals("Seller"))){
                    response = usersController.getUser(type)
                            .toString();

                }else {
                    response = "Invalid Type";
                }
            }


            OutputStream outputStream = exchange.getResponseBody();
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            outputStream.write(response.getBytes());
            outputStream.flush();
            outputStream.close();
        }
        private JSONObject parseRequestBody(InputStream requestBody) throws IOException {
            byte[] requestBodyBytes = requestBody.readAllBytes();
            String requestBodyString = new String(requestBodyBytes);
            return new JSONObject(requestBodyString);
        }
    }
}
