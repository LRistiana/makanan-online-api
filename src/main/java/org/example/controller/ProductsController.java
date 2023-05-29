package org.example.controller;

import org.example.server.DatabaseManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class ProductsController {

    private DatabaseManager databaseManager;

    public ProductsController(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }


    public JSONArray getProducts(){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM products";

        try(Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql)){
            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("seller",resultSet.getString("seller"));
                jsonUser.put("title",resultSet.getString("title"));
                jsonUser.put("description",resultSet.getString("description"));
                jsonUser.put("price",resultSet.getString("price"));
                jsonUser.put("stock",resultSet.getString("stock"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONArray getProducts(int idProduct){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM products WHERE id=" + idProduct;

        try(Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql)){
            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("seller",resultSet.getString("seller"));
                jsonUser.put("title",resultSet.getString("title"));
                jsonUser.put("description",resultSet.getString("description"));
                jsonUser.put("price",resultSet.getString("price"));
                jsonUser.put("stock",resultSet.getString("stock"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    public boolean updateProduct(int idProduct, JSONObject requestBodyJson){
        int seller = requestBodyJson.optInt("seller");
        String title = requestBodyJson.optString("title");
        String description = requestBodyJson.optString("description");
        String price = requestBodyJson.optString("price");
        int stock = requestBodyJson.optInt("stock");
        PreparedStatement statement = null;
        int insertedRow = 0;
        String querySql = "UPDATE products SET seller=?, title=?, description=?, price=?, stock=? WHERE id=?";
        try {
            statement = databaseManager
                    .getConnection()
                    .prepareStatement(querySql);
            statement.setInt(1,seller);
            statement.setString(2,title);
            statement.setString(3,description);
            statement.setString(4,price);
            statement.setInt(5,stock);
            statement.setInt(6,idProduct);

            insertedRow = statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }


        return insertedRow > 0;
    }

    public boolean addProduct(JSONObject requestBodyJson){
        int seller = requestBodyJson.optInt("seller");
        String title = requestBodyJson.optString("title");
        String description = requestBodyJson.optString("description");
        String price = requestBodyJson.optString("price");
        int stock = requestBodyJson.optInt("stock");
        PreparedStatement statement = null;
        int insertedRow = 0;

        String querySql = "INSERT INTO products(seller,title,description,price,stock) VALUES(?,?,?,?,?)";
        try {
            statement = databaseManager
                    .getConnection()
                    .prepareStatement(querySql);
            statement.setInt(1,seller);
            statement.setString(2,title);
            statement.setString(3,description);
            statement.setString(4,price);
            statement.setInt(5,stock);

            insertedRow = statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return insertedRow > 0;
    }

    public boolean deleteProducts(int idProduct){

        int rowDeleted = 0;
        PreparedStatement statement = null;
        String querySql = "DELETE FROM products WHERE id="+idProduct;
        try {
            statement = this.databaseManager
                    .getConnection()
                    .prepareStatement(querySql);
            rowDeleted = statement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }

        return rowDeleted > 0;
    }
}
