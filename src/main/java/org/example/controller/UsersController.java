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

    public void deleteUser(int idUser){
//        String querySql = "DELETE FROM users WHERE id=?";
//        try(Connection connection = databaseManager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(querySql)){
//            statement.setInt(1,idUser);
//            int rowDeleted = statement.executeUpdate();
//            return rowDeleted > 0;
//        }catch (SQLException e){
//            e.printStackTrace();
//        }


        PreparedStatement statement = null;
        String querySql = "DELETE FROM users WHERE id="+idUser;
        try {
            statement = this.databaseManager
                    .getConnection()
                    .prepareStatement(querySql);
            statement.execute();
//            int rowDeleted = statement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }

//        return true;
    }

}
