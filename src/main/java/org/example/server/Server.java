package org.example.server;

import java.io.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.controller.OrdersController;
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
    private OrdersController ordersController;

    public Server(){
        DatabaseManager databaseManager = new DatabaseManager("database/db_makanan_online.db");
        usersController = new UsersController(databaseManager);
        productsController = new ProductsController(databaseManager);
        ordersController = new OrdersController(databaseManager);
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
//            String path = exchange.getRequestURI().getPath();
            String response = "";
            int statusCode = 404;
            JSONObject requestBodyJson;

            String[] path = exchange.getRequestURI()
                    .getPath()
                    .split("/");
            String query = exchange.getRequestURI()
                    .getQuery();


            if (method.equals("GET")){
                GetHandler getHandler = new GetHandler(path,query);
                getHandler.main();

                response = getHandler.getResponse();
                statusCode = getHandler.getStatusCode();
            } else if (method.equals("POST")) {
                requestBodyJson = parseRequestBody(exchange.getRequestBody());
                PostHandler postHandler = new PostHandler(path,query,requestBodyJson);
                postHandler.main();

                response = postHandler.getResponse();
                statusCode = postHandler.getStatusCode();
            } else if (method.equals("PUT")) {
                requestBodyJson = parseRequestBody(exchange.getRequestBody());
                PutHandler putHandler = new PutHandler(path,query,requestBodyJson);
                putHandler.main();

                response = putHandler.getResponse();
                statusCode = putHandler.getStatusCode();
            } else if (method.equals("DELETE")) {
                DeleteHandler deleteHandler = new DeleteHandler(path,query);
                deleteHandler.main();

                response = deleteHandler.getResponse();
                statusCode = deleteHandler.getStatusCode();
            }else {
                response = "Request Method Invalid";
            }

//
//            if (path.equals("/users") && method.equals("GET")){
//                String type = exchange
//                        .getRequestURI()
//                        .getQuery();
//                type = type.substring(type.lastIndexOf('=')+1);
//
//                if (type != null && (type.equals("Buyer") || type.equals("Seller"))){
//                    response = usersController.getUser(type)
//                            .toString();
//                }else {
//                    JSONArray jsonArray = usersController.getUser();
//                    response = jsonArray.toString();
////                    response=type;
//                }
//            }
//            else if (path.matches("/users/\\d+") && method.equals("GET")){
//                int userId = Integer.parseInt(path.substring(path.lastIndexOf('/')+1));
//                JSONArray jsonArray = usersController.getUser(userId);
//                if ( jsonArray != null){
//                    response = jsonArray.toString();
//                }else {
//                    response = " User Not Found";
//                }
//
//            }
//            else if (path.matches("/users/\\d+") && method.equals("DELETE")) {
//                int userId = Integer.parseInt(path.substring(path.lastIndexOf('/')+1));
////                boolean done = usersController.deleteUser(userId);
//                boolean done = usersController.deleteUser(userId);
//                if (done){
//                    response = "User id =" + userId + " deleted";
//                }else {
//                    response = "User Not Found";
//                }
//            }
//            else if(path.equals("/users") && method.equals("POST")){
//                JSONObject requestBodyJson = parseRequestBody(exchange.getRequestBody());
//                if (usersController.addUser(requestBodyJson)){
//                    response = "User Added";
//                }else {
//                    response = "Invalid Data";
//                }
//            }
//            else if (path.matches("/users/\\d+")&& method.equals("PUT")) {
//                JSONObject requestBodyJson = parseRequestBody(exchange.getRequestBody());
//                int idUser = Integer.parseInt(path.substring(path.lastIndexOf('/')+1));
////
//                if (usersController.updateUser(idUser,requestBodyJson)){
//                    response = "Data User Updated";
//                }else {
//                    response = "Invalid Data";
//                }
//            }
//            else if (path.equals("/users") && method.equals("GET")) {
//                String type = exchange.getRequestURI().getQuery();
//                if (type != null && (type.equals("Buyer") || type.equals("Seller"))){
//                    response = usersController.getUser(type)
//                            .toString();
//
//                }else {
//                    response = "Invalid Type";
//                }
//            }


            OutputStream outputStream = exchange.getResponseBody();
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(statusCode, response.length());
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
    public class GetHandler {
        private String[] path;
        private String query;
        private String response;
        private int statusCode;
        GetHandler(String[] path,String query){
            this.path = path;
            this.query = query;
        }


        public void main() {
            if (this.path[1].equals("users")){
                usersHandle();
            } else if (this.path[1].equals("products")) {
                productsHandle();
            } else if (this.path[1].equals("orders")) {
                ordersHandle();
            }
        }
        private void usersHandle(){

            if (this.path.length == 4){
                //path : /users/id/something
                if (path[3].equals("products")){
                    this.response = usersController
                            .getUserProduct(Integer.parseInt(path[2]))
                            .toString();
                } else if (path[3].equals("orders")) {
                    this.response = usersController
                            .getUserOrder(Integer.parseInt(path[2]))
                            .toString();
                } else if (path[3].equals("reviews")) {
                    this.response = usersController
                            .getUserReview(Integer.parseInt(path[2]))
                            .toString();
                }
            } else if (this.path.length == 3) {
                // path : /users/id
                this.response = usersController
                        .getUser(Integer.parseInt(path[2]))
                        .toString();

            } else  if(this.path.length == 2){
                // path : /users
                if (query == null){
                    this.response = usersController
                            .getUser()
                            .toString();
                }else if (query.contains("type")){
                    query = query.substring(query.lastIndexOf('=')+1);
                    response = usersController.getUser(query)
                            .toString();
                } else if (query.contains("field")) {
                    
                }
            }

            if (this.response.equals("[]")){
                this.response = "Data Null";
                this.statusCode = 400;
            }else {
                this.statusCode = 200;
            }
        }
        private void productsHandle(){
            if (this.path.length == 3){
                // path  : /products/id
                this.response = productsController
                        .getProducts(Integer.parseInt(path[2]))
                        .toString();
                this.statusCode = 200;

            }else if(this.path.length == 2){
                // path : /products
                this.response = productsController
                        .getProducts()
                        .toString();
                this.statusCode = 200;
            }
        }
        private void ordersHandle(){
            if (this.path.length == 3){
                // path  : /orders/id
                this.response = ordersController
                        .getOrders(Integer.parseInt(path[2]))
                        .toString();
                this.statusCode = 200;

            }else if(this.path.length == 2){
                // path : /orders
                this.response = ordersController
                        .getOrders()
                        .toString();
                this.statusCode = 200;
            }
        }


        public String getResponse(){
            return this.response;
        }
        public int getStatusCode(){return this.statusCode;}

        //======== GET HANDLER END==============//
    }

    public class PutHandler{
        private String[] path;
        private String query;
        private String response;
        private int statusCode;

        private JSONObject requestBodyJson;
        PutHandler(String[] path,String query, JSONObject requestBodyJson){
            this.path = path;
            this.query = query;
            this.requestBodyJson = requestBodyJson;
        }
        public void main() {
            if (this.path[1].equals("users")){
                usersHandle();
            } else if (this.path[1].equals("products")) {
                productsHandle();
            } else if (this.path[1].equals("orders")) {
                ordersHandle();
            }
        }
        private void usersHandle(){
            // path : /user/id
            boolean done = usersController.updateUser(Integer.parseInt(path[2]),requestBodyJson);
            if (done){
                this.response = "Data Updated";
                this.statusCode = 200;
            }else {
                this.response = "Data Invalid";
                this.statusCode = 400;
            }
        }

        private void productsHandle(){
            // path : /products/id
            boolean done = productsController.updateProduct(Integer.parseInt(path[2]),requestBodyJson);
            if (done){
                this.response = "Data Updated";
                this.statusCode = 200;
            }else {
                this.response = "Data Invalid";
                this.statusCode = 400;
            }
        }
        private void ordersHandle(){
            // path : /orders/id
            boolean done = ordersController.updateOrder(Integer.parseInt(path[2]),requestBodyJson);
            if (done){
                this.response = "Data Updated";
                this.statusCode = 200;
            }else {
                this.response = "Data Invalid";
                this.statusCode = 400;
            }
        }

        public String getResponse(){
            return this.response;
        }
        public int getStatusCode(){return this.statusCode;}
        // ========== PUT HANDLER END =========== //
    }

    public class PostHandler{
        private String[] path;
        private String query;
        private String response;
        private int statusCode;

        private JSONObject requestBodyJson;
        PostHandler(String[] path,String query, JSONObject requestBodyJson){
            this.path = path;
            this.query = query;
            this.requestBodyJson = requestBodyJson;
        }
        public void main() {
            if (this.path[1].equals("users")){
                usersHandle();
            } else if (this.path[1].equals("products")) {
                productsHandle();
            } else if (this.path[1].equals("orders")) {
                ordersHandle();
            }
        }
        private void usersHandle(){
            // path : /user
            boolean done = usersController.addUser(requestBodyJson);
            if (done){
                this.response = "Data Added";
                this.statusCode = 200;
            }else {
                this.response = "Data Invalid";
                this.statusCode = 400;
            }
        }

        private void productsHandle(){
            // path : /products
            boolean done = productsController.addProduct(requestBodyJson);
            if (done){
                this.response = "Data Added";
                this.statusCode = 200;
            }else {
                this.response = "Data Invalid";
                this.statusCode = 400;
            }
        }
        private void ordersHandle(){
            // path : /orders
            boolean done = ordersController.addOrder(requestBodyJson);
            if (done){
                this.response = "Data Added";
                this.statusCode = 200;
            }else {
                this.response = "Data Invalid";
                this.statusCode = 400;
            }
        }

        public String getResponse(){
            return this.response;
        }
        public int getStatusCode(){return this.statusCode;}
        // ========== POST HANDLER END =========== //
    }

    public class DeleteHandler{
        private String[] path;
        private String query;
        private String response;
        private int statusCode;

        DeleteHandler(String[] path,String query){
            this.path = path;
            this.query = query;
        }
        public void main() {
            if (this.path[1].equals("users")){
                usersHandle();
            } else if (this.path[1].equals("products")) {
                productsHandle();
            } else if (this.path[1].equals("orders")) {
                ordersHandle();
            }
        }
        private void usersHandle(){
            // path : /user/id
            boolean done = usersController.deleteUser(Integer.parseInt(path[2]));
            if (done){
                this.response = "Data Deleted";
                this.statusCode = 200;
            }else {
                this.response = "Data Not Found";
                this.statusCode = 400;
            }
        }

        private void productsHandle(){
            // path : /products/id
            boolean done = productsController.deleteProducts(Integer.parseInt(path[2]));
            if (done){
                this.response = "Data Deleted";
                this.statusCode = 200;
            }else {
                this.response = "Data Not Found";
                this.statusCode = 400;
            }
        }
        private void ordersHandle(){
            // path : /products/id
            boolean done = ordersController.deleteOrder(Integer.parseInt(path[2]));
            if (done){
                this.response = "Data Deleted";
                this.statusCode = 200;
            }else {
                this.response = "Data Not Found";
                this.statusCode = 400;
            }
        }

        public String getResponse(){
            return this.response;
        }
        public int getStatusCode(){return this.statusCode;}
        // ========== DELETE HANDLER END =========== //
    }
}
