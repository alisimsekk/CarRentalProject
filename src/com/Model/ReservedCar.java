package com.Model;

import com.Helper.DBConnector;
import com.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;



public class ReservedCar {
    private int id;
    private int customer_id;
    private String check_in_date;
    private String check_out_date;
    private int total_price;
    private int car_id;
    private int company_id;


    public ReservedCar(int id, int customer_id, String check_in_date, String check_out_date, int total_price, int car_id, int company_id) {
        this.id = id;
        this.customer_id = customer_id;
        this.check_in_date = check_in_date;
        this.check_out_date = check_out_date;
        this.total_price = total_price;
        this.car_id = car_id;
        this.company_id = company_id;
    }

    public ReservedCar() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCheck_in_date() {
        return check_in_date;
    }

    public void setCheck_in_date(String check_in_date) {
        this.check_in_date = check_in_date;
    }

    public String getCheck_out_date() {
        return check_out_date;
    }

    public void setCheck_out_date(String check_out_date) {
        this.check_out_date = check_out_date;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public static ArrayList<ReservedCar> getListByCustomerID(int customer_id) {
        ArrayList<ReservedCar> reservedCarList = new ArrayList<>();
        String query = "SELECT * FROM reserved_car WHERE customer_id = ?";
        ReservedCar obj;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, customer_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new ReservedCar();
                obj.setId(rs.getInt("id"));
                obj.setCustomer_id(rs.getInt("customer_id"));
                obj.setCheck_in_date(rs.getString("check_in_date"));
                obj.setCheck_out_date(rs.getString("check_out_date"));
                obj.setTotal_price(rs.getInt("total_price"));
                obj.setCar_id(rs.getInt("car_id"));
                obj.setCompany_id(rs.getInt("company_id"));
                reservedCarList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservedCarList;
    }

    public static ArrayList<ReservedCar> getListByCompanyID(int company_id) {
        ArrayList<ReservedCar> reservedCarList = new ArrayList<>();
        String query = "SELECT * FROM reserved_car WHERE company_id = ?";
        ReservedCar obj;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, company_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new ReservedCar();
                obj.setId(rs.getInt("id"));
                obj.setCustomer_id(rs.getInt("customer_id"));
                obj.setCheck_in_date(rs.getString("check_in_date"));
                obj.setCheck_out_date(rs.getString("check_out_date"));
                obj.setTotal_price(rs.getInt("total_price"));
                obj.setCar_id(rs.getInt("car_id"));
                obj.setCompany_id(rs.getInt("company_id"));
                reservedCarList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservedCarList;
    }

    public static ReservedCar getFetch(int id) {
        String query = "SELECT * FROM reserved_car WHERE id = ?";
        ReservedCar obj = null;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new ReservedCar();
                obj.setId(rs.getInt("id"));
                obj.setCustomer_id(rs.getInt("customer_id"));
                obj.setCheck_in_date(rs.getString("check_in_date"));
                obj.setCheck_out_date(rs.getString("check_out_date"));
                obj.setTotal_price(rs.getInt("total_price"));
                obj.setCar_id(rs.getInt("car_id"));
                obj.setCompany_id(rs.getInt("company_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static boolean add(int customer_id, String check_in_date, String check_out_date, int total_price, int car_id, int company_id) {
        String query = "INSERT INTO reserved_car (customer_id, check_in_date, check_out_date, total_price, car_id, company_id ) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,customer_id);
            pr.setString(2,check_in_date);
            pr.setString(3,check_out_date);
            pr.setInt(4,total_price);
            pr.setInt(5,car_id);
            pr.setInt(6,company_id);
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

    public static boolean remove(int id) {
        ReservedCar reservedCar = ReservedCar.getFetch(id);
        //default time zone
        ZoneId defaultZoneId = ZoneId.systemDefault();
        //creating the instance of LocalDate using the day, month, year info
        LocalDate today = LocalDate.now();
        //local date + atStartOfDay() + default time zone + toInstant() = Date
        Date today_date = Date.from(today.atStartOfDay(defaultZoneId).toInstant());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String check_in =  reservedCar.getCheck_in_date();
        Date check_in_date = null;
        try {
            check_in_date = formatter.parse(check_in);
        } catch (ParseException ex) {

        }
        long diff = check_in_date.getTime() - today_date.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long difference_date = hours / 24;
        if (difference_date>=1){
            String query = "DELETE FROM reserved_car WHERE id = ?";
            try {
                PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
                pr.setInt(1,id);
                return pr.executeUpdate() != -1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
        else {
            return false;
        }
    }
}
