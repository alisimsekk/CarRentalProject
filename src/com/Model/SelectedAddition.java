package com.Model;

import com.Helper.DBConnector;
import com.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SelectedAddition {
    private int id;
    private String explanation;
    private int price;
    private int car_id;
    private int customer_id;

    public SelectedAddition(int id, String explanation, int price, int car_id, int customer_id) {
        this.id = id;
        this.explanation = explanation;
        this.price = price;
        this.car_id = car_id;
        this.customer_id = customer_id;
    }

    public SelectedAddition() {

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

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public static ArrayList<SelectedAddition> getListByCarID(int id){
        ArrayList<SelectedAddition> selectedAdditionList = new ArrayList<>();
        String query = "SELECT * FROM selected_addition  WHERE car_id = ?";
        SelectedAddition obj;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new SelectedAddition();
                obj.setId(rs.getInt("id"));
                obj.setExplanation(rs.getString("explanation"));
                obj.setPrice(rs.getInt("price"));
                obj.setCar_id(rs.getInt("car_id"));
                obj.setCustomer_id(rs.getInt("customer_id"));
                selectedAdditionList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectedAdditionList;
    }

    public static boolean add(String explanation, int price, int car_id, int customer_id) {
        SelectedAddition obj = SelectedAddition.getFetchByExplanation(explanation);

        if (obj != null) {
            Helper.showMsg("Ek özellik zaten seçili");
            return false;
        } else {
            String query = "INSERT INTO selected_addition (explanation, price, car_id, customer_id) VALUES (?,?,?,?)";
            try {
                PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
                pr.setString(1, explanation);
                pr.setInt(2, price);
                pr.setInt(3, car_id);
                pr.setInt(4, customer_id);
                int response = pr.executeUpdate();
                if (response == -1) {
                    Helper.showMsg("error");
                }
                return response != -1;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return true;
        }
    }

    private static SelectedAddition getFetchByExplanation(String explanation) {
        SelectedAddition obj = null;
        String query = "SELECT * FROM selected_addition  WHERE explanation = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, explanation);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new SelectedAddition();
                obj.setId(rs.getInt("id"));
                obj.setExplanation(rs.getString("explanation"));
                obj.setPrice(rs.getInt("price"));
                obj.setCar_id(rs.getInt("car_id"));
                obj.setCustomer_id(rs.getInt("customer_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
