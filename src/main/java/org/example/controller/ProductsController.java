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
//                jsonUser.put("id",resultSet.getInt("id"));
//                jsonUser.put("seller",resultSet.getString("first_name"));
//                jsonUser.put("lastName",resultSet.getString("last_name"));
//                jsonUser.put("email",resultSet.getString("email"));
//                jsonUser.put("phoneNumber",resultSet.getString("phone_number"));
//                jsonUser.put("type",resultSet.getString("type"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }
}
