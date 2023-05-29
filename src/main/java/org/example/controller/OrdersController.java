package org.example.controller;

import org.example.server.DatabaseManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class OrdersController {

    private DatabaseManager databaseManager;

    public OrdersController(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }


    public JSONArray getOrders(){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM orders";

        try(Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql)){
            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("buyer",resultSet.getInt("buyer"));
                jsonUser.put("note",resultSet.getInt("note"));
                jsonUser.put("total",resultSet.getInt("total"));
                jsonUser.put("discount",resultSet.getInt("discount"));
                jsonUser.put("isPaid",resultSet.getInt("is_paid"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }
    public JSONArray getOrders(int idOrder){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM orders WHERE id=" + idOrder;

        try(Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql)){
            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("buyer",resultSet.getInt("buyer"));
                jsonUser.put("note",resultSet.getInt("note"));
                jsonUser.put("total",resultSet.getInt("total"));
                jsonUser.put("discount",resultSet.getInt("discount"));
                jsonUser.put("isPaid",resultSet.getInt("is_paid"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }



    public boolean updateOrder(int idOrder, JSONObject requestBodyJson){
        int buyer = requestBodyJson.optInt("buyer");
        int note = requestBodyJson.optInt("note");
        int total = requestBodyJson.optInt("total");
        int discount = requestBodyJson.optInt("discount");
        int is_paid = requestBodyJson.optInt("isPaid");
        PreparedStatement statement = null;
        int insertedRow = 0;
        String querySql = "UPDATE orders SET buyer=?, note=?, total=?, discount=?, is_paid=? WHERE id=?";
        try {
            statement = databaseManager
                    .getConnection()
                    .prepareStatement(querySql);
            statement.setInt(1,buyer);
            statement.setInt(2,note);
            statement.setInt(3,total);
            statement.setInt(4,discount);
            statement.setInt(5,is_paid);
            statement.setInt(6,idOrder);

            insertedRow = statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return insertedRow > 0;
    }

    public boolean addOrder(JSONObject requestBodyJson){
        int buyer = requestBodyJson.optInt("buyer");
        int note = requestBodyJson.optInt("note");
        int total = requestBodyJson.optInt("total");
        int discount = requestBodyJson.optInt("discount");
        int is_paid = requestBodyJson.optInt("isPaid");
        PreparedStatement statement = null;
        int insertedRow = 0;

        String querySql = "INSERT INTO orders(buyer,note,total,discount,is_paid) VALUES(?,?,?,?,?)";
        try {
            statement = databaseManager
                    .getConnection()
                    .prepareStatement(querySql);
            statement.setInt(1,buyer);
            statement.setInt(2,note);
            statement.setInt(3,total);
            statement.setInt(4,discount);
            statement.setInt(5,is_paid);

            insertedRow = statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return insertedRow > 0;
    }

    public boolean deleteOrder(int idOrder){

        int rowDeleted = 0;
        PreparedStatement statement = null;
        String querySql = "DELETE FROM orders WHERE id="+idOrder;
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
