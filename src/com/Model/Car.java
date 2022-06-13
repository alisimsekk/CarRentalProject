package com.Model;

import com.Helper.DBConnector;
import com.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Car {
    private int id;
    private String brand;
    private String model;
    private String type;
    private String season_start;
    private String season_end;
    private int price;
    private String transmission;
    private String fuel;
    private int company_id;

    public Car(int id, String brand, String model, String type, String season_start, String season_end, int price, String transmission, String fuel, int company_id) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.season_start = season_start;
        this.season_end = season_end;
        this.price = price;
        this.transmission = transmission;
        this.fuel = fuel;
        this.company_id = company_id;
    }

    public Car() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeason_start() {
        return season_start;
    }

    public void setSeason_start(String season_start) {
        this.season_start = season_start;
    }

    public String getSeason_end() {
        return season_end;
    }

    public void setSeason_end(String season_end) {
        this.season_end = season_end;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public static ArrayList<Car> getList() {
        ArrayList<Car> carList = new ArrayList<>();
        String query = "SELECT * FROM car";
        Car obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new Car();
                obj.setId(rs.getInt("id"));
                obj.setBrand(rs.getString("brand"));
                obj.setModel(rs.getString("model"));
                obj.setType(rs.getString("type"));
                obj.setSeason_start(rs.getString("season_start"));
                obj.setSeason_end(rs.getString("season_end"));
                obj.setPrice(rs.getInt("price"));
                obj.setTransmission(rs.getString("transmission"));
                obj.setFuel(rs.getString("fuel"));
                obj.setCompany_id(rs.getInt("company_id"));
                carList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carList;
    }

    public static boolean add(String brand, String model, String type, String season_start, String season_end, int price, String transmission, String fuel, int company_id) {
        String query = "INSERT INTO car (brand, model, type, season_start, season_end, price, transmission, fuel, company_id) VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,brand);
            pr.setString(2,model);
            pr.setString(3,type);
            pr.setString(4,season_start);
            pr.setString(5,season_end);
            pr.setInt(6,price);
            pr.setString(7,transmission);
            pr.setString(8,fuel);
            pr.setInt(9,company_id);
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
