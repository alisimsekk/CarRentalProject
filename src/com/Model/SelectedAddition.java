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
    private String check_in;
    private String check_out;
    private int car_id;
    private int customer_id;

    public SelectedAddition(int id, String explanation, int price,String check_in, String check_out,  int car_id, int customer_id) {
        this.id = id;
        this.explanation = explanation;
        this.price = price;
        this.check_in = check_in;
        this.check_out = check_out;
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

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
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

    public static ArrayList<SelectedAddition> getListByCarIDandCheckin(int car_id, String check_in){
        ArrayList<SelectedAddition> selectedAdditionList = new ArrayList<>();
        String query = "SELECT * FROM selected_addition  WHERE car_id = ? AND check_in = ?";
        SelectedAddition obj;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, car_id);
            pr.setString(2,check_in);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new SelectedAddition();
                obj.setId(rs.getInt("id"));
                obj.setExplanation(rs.getString("explanation"));
                obj.setPrice(rs.getInt("price"));
                obj.setCheck_in(rs.getString("check_in"));
                obj.setCheck_out(rs.getString("check_out"));
                obj.setCar_id(rs.getInt("car_id"));
                obj.setCustomer_id(rs.getInt("customer_id"));
                selectedAdditionList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectedAdditionList;
    }

    public static boolean add(String explanation, int price,String check_in, String check_out, int car_id, int customer_id) {
        SelectedAddition obj = SelectedAddition.getFetchByExplanationAndCheckInAndCustomerID(explanation, check_in, customer_id);

        if (obj != null) {
            Helper.showMsg("Ek özellik zaten seçili");
            return false;
        } else {
            String query = "INSERT INTO selected_addition (explanation, price, check_in, check_out, car_id, customer_id) VALUES (?,?,?,?,?,?)";
            try {
                PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
                pr.setString(1, explanation);
                pr.setInt(2, price);
                pr.setString(3,check_in);
                pr.setString(4,check_out);
                pr.setInt(5, car_id);
                pr.setInt(6, customer_id);
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

    public static boolean remove(int id) {
        String query = "DELETE FROM selected_addition WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean remove(int customer_id, String check_in, int car_id) {
        String query = "DELETE FROM selected_addition WHERE customer_id = ? AND check_in = ? AND car_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,customer_id);
            pr.setString(2, check_in);
            pr.setInt(3,car_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static SelectedAddition getFetchByExplanationAndCheckInAndCustomerID(String explanation, String check_in, int customer_id) {
        SelectedAddition obj = null;
        String query = "SELECT * FROM selected_addition  WHERE explanation = ? AND check_in = ? AND customer_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, explanation);
            pr.setString(2, check_in);
            pr.setInt(3, customer_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new SelectedAddition();
                obj.setId(rs.getInt("id"));
                obj.setExplanation(rs.getString("explanation"));
                obj.setPrice(rs.getInt("price"));
                obj.setCheck_in(rs.getString("check_in"));
                obj.setCheck_out(rs.getString("check_out"));
                obj.setCar_id(rs.getInt("car_id"));
                obj.setCustomer_id(rs.getInt("customer_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static SelectedAddition getFetchByExplanation(String selected_addition_explanation) {
        SelectedAddition obj = null;
        String query = "SELECT * FROM selected_addition  WHERE explanation = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, selected_addition_explanation);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new SelectedAddition();
                obj.setId(rs.getInt("id"));
                obj.setExplanation(rs.getString("explanation"));
                obj.setPrice(rs.getInt("price"));
                obj.setCheck_in(rs.getString("check_in"));
                obj.setCheck_out(rs.getString("check_out"));
                obj.setCar_id(rs.getInt("car_id"));
                obj.setCustomer_id(rs.getInt("customer_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
