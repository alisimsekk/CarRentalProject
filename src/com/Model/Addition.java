package com.Model;

import com.Helper.DBConnector;
import com.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Addition {
    private int id;
    private String explanation;
    private int price;
    private int car_id;

    public Addition(int id, String explanation, int price, int car_id) {
        this.id = id;
        this.explanation = explanation;
        this.price = price;
        this.car_id = car_id;
    }

    public Addition() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public static ArrayList<Addition> getListByCarID(int id) {
        ArrayList<Addition> additionList = new ArrayList<>();
        String query = "SELECT * FROM addition  WHERE car_id = ?";
        Addition obj;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new Addition();
                obj.setId(rs.getInt("id"));
                obj.setExplanation(rs.getString("explanation"));
                obj.setPrice(rs.getInt("price"));
                obj.setCar_id(rs.getInt("car_id"));
                additionList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return additionList;
    }

    public static Addition getFetchByID(int id) {
        String query = "SELECT * FROM addition  WHERE id = ?";
        Addition obj = null;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new Addition();
                obj.setId(rs.getInt("id"));
                obj.setExplanation(rs.getString("explanation"));
                obj.setPrice(rs.getInt("price"));
                obj.setCar_id(rs.getInt("car_id"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static boolean add(String explanation, int price, int car_id) {
        String query = "INSERT INTO addition (explanation, price, car_id) VALUES (?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,explanation);
            pr.setInt(2,price);
            pr.setInt(3,car_id);
            int response = pr.executeUpdate();
            if (response == -1){
                Helper.showMsg("error");
            }
            return response != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

}
