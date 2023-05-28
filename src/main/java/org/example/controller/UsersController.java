package org.example.controller;

import org.example.server.DatabaseManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class UsersController {
    private DatabaseManager databaseManager;

    public UsersController(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }
    public JSONArray getUser(){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM users";


        try(Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql)){
                while (resultSet.next()){
                    JSONObject jsonUser = new JSONObject();
                    jsonUser.put("id",resultSet.getInt("id"));
                    jsonUser.put("firstName",resultSet.getString("first_name"));
                    jsonUser.put("lastName",resultSet.getString("last_name"));
                    jsonUser.put("email",resultSet.getString("email"));
                    jsonUser.put("phoneNumber",resultSet.getString("phone_number"));
                    jsonUser.put("type",resultSet.getString("type"));
                    jsonArray.put(jsonUser);
                }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }
    public JSONArray getUser(int userId){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM users WHERE id=" + userId;

        try(Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql)){
                while (resultSet.next()){
                    JSONObject jsonUser = new JSONObject();
                    jsonUser.put("id",resultSet.getInt("id"));
                    jsonUser.put("firstName",resultSet.getString("first_name"));
                    jsonUser.put("lastName",resultSet.getString("last_name"));
                    jsonUser.put("email",resultSet.getString("email"));
                    jsonUser.put("phoneNumber",resultSet.getString("phone_number"));
                    jsonUser.put("type",resultSet.getString("type"));
                    jsonArray.put(jsonUser);
                }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }
    public JSONArray getUser(String type){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM users WHERE type='" + type + "'";

        try(Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql)){
            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("firstName",resultSet.getString("first_name"));
                jsonUser.put("lastName",resultSet.getString("last_name"));
                jsonUser.put("email",resultSet.getString("email"));
                jsonUser.put("phoneNumber",resultSet.getString("phone_number"));
                jsonUser.put("type",resultSet.getString("type"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    public boolean deleteUser(int idUser){
//        String querySql = "DELETE FROM users WHERE id=?";
//        try(Connection connection = databaseManager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(querySql)){
//            statement.setInt(1,idUser);
//            int rowDeleted = statement.executeUpdate();
//            return rowDeleted > 0;
//        }catch (SQLException e){
//            e.printStackTrace();
//        }

        int rowDeleted = 0;
        PreparedStatement statement = null;
        String querySql = "DELETE FROM users WHERE id="+idUser;
        try {
            statement = this.databaseManager
                    .getConnection()
                    .prepareStatement(querySql);
//            statement.execute();
            rowDeleted = statement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }

        return rowDeleted > 0;
    }

    public boolean addUser(JSONObject requestBodyJson){
        String firstName = requestBodyJson.optString("firstName");
        String lastName = requestBodyJson.optString("lastName");
        String email = requestBodyJson.optString("email");
        String phoneNumber = requestBodyJson.optString("phoneNumber");
        String type = requestBodyJson.optString("type");
        PreparedStatement statement = null;
        int insertedRow = 0;

        String querySql = "INSERT INTO users(first_name,last_name,email,phone_number,type) VALUES(?,?,?,?,?)";
        try {
            statement = databaseManager
                    .getConnection()
                    .prepareStatement(querySql);
            statement.setString(1,firstName);
            statement.setString(2,lastName);
            statement.setString(3,email);
            statement.setString(4,phoneNumber);
            statement.setString(5,type);

            insertedRow = statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return insertedRow > 0;
    }
    public boolean updateUser(int idUser, JSONObject requestBodyJson){
        String firstName = requestBodyJson.optString("firstName");
        String lastName = requestBodyJson.optString("lastName");
        String email = requestBodyJson.optString("email");
        String phoneNumber = requestBodyJson.optString("phoneNumber");
        String type = requestBodyJson.optString("type");
        PreparedStatement statement = null;
        int insertedRow = 0;
        String querySql = "UPDATE users SET first_name=?, last_name=?, email=?, phone_number=?, type=? WHERE id=?";
        try {
            statement = databaseManager
                    .getConnection()
                    .prepareStatement(querySql);
            statement.setString(1,firstName);
            statement.setString(2,lastName);
            statement.setString(3,email);
            statement.setString(4,phoneNumber);
            statement.setString(5,type);
            statement.setInt(6,idUser);

            insertedRow = statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }


        return insertedRow > 0;
    }

}
