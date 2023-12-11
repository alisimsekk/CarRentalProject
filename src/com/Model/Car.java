package com.Model;

import com.helper.DBConnector;
import com.helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Car {
    private int id;
    private String brand;
    private String model;
    private int year;
    private String type;
    private String season_start;
    private String season_end;
    private int price;
    private String transmission;
    private String fuel;
    private int company_id;
    private String city;

    public Car(int id, String brand, String model, int year, String type, String season_start, String season_end, int price, String transmission, String fuel, int company_id, String city) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.type = type;
        this.season_start = season_start;
        this.season_end = season_end;
        this.price = price;
        this.transmission = transmission;
        this.fuel = fuel;
        this.company_id = company_id;
        this.city = city;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
                obj.setYear(rs.getInt("year"));
                obj.setType(rs.getString("type"));
                obj.setSeason_start(rs.getString("season_start"));
                obj.setSeason_end(rs.getString("season_end"));
                obj.setPrice(rs.getInt("price"));
                obj.setTransmission(rs.getString("transmission"));
                obj.setFuel(rs.getString("fuel"));
                obj.setCompany_id(rs.getInt("company_id"));
                obj.setCity(rs.getString("city"));
                carList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carList;
    }

    public static boolean add(String brand, String model, int year, String type, String season_start, String season_end, int price, String transmission, String fuel, int company_id, String city) {
        String query = "INSERT INTO car (brand, model, year, type, season_start, season_end, price, transmission, fuel, company_id, city) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,brand);
            pr.setString(2,model);
            pr.setInt(3,year);
            pr.setString(4,type);
            pr.setString(5,season_start);
            pr.setString(6,season_end);
            pr.setInt(7,price);
            pr.setString(8,transmission);
            pr.setString(9,fuel);
            pr.setInt(10,company_id);
            pr.setString(11,city);
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

    // girilen anahtar kelimeye göre search metodu
    public static ArrayList<Car> searchCarList(String query){
        ArrayList<Car> carList = new ArrayList<>();
        Car obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new Car();
                obj.setId(rs.getInt("id"));
                obj.setBrand(rs.getString("brand"));
                obj.setModel(rs.getString("model"));
                obj.setYear(rs.getInt("year"));
                obj.setType(rs.getString("type"));
                obj.setSeason_start(rs.getString("season_start"));
                obj.setSeason_end(rs.getString("season_end"));
                obj.setPrice(rs.getInt("price"));
                obj.setTransmission(rs.getString("transmission"));
                obj.setFuel(rs.getString("fuel"));
                obj.setCompany_id(rs.getInt("company_id"));
                obj.setCity(rs.getString("city"));
                carList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carList;
    }

    public static Car getFetch(int id) {
        Car obj = null;
        String query = "SELECT * FROM car WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Car(rs.getInt("id"), rs.getString("brand"), rs.getString("model"), rs.getInt("year"), rs.getString("type"), rs.getString("season_start"),
                        rs.getString("season_end"), rs.getInt("price"), rs.getString("transmission"),
                        rs.getString("fuel"), rs.getInt("company_id"), rs.getString("city"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
