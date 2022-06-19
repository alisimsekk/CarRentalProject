package com.Model;

import com.Helper.DBConnector;
import com.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservedCar {
    private int id;
    private int customer_id;
    private String check_in_date;
    private String check_out_date;
    private int car_id;
    private int company_id;

    public ReservedCar(int id, int customer_id, String check_in_date, String check_out_date, int car_id, int company_id) {
        this.id = id;
        this.customer_id = customer_id;
        this.check_in_date = check_in_date;
        this.check_out_date = check_out_date;
        this.car_id = car_id;
        this.company_id = company_id;
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

    public static boolean add(int customer_id, String check_in_date, String check_out_date, int car_id, int company_id) {
        String query = "INSERT INTO reserved_car (customer_id, check_in_date, check_out_date, car_id, company_id ) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,customer_id);
            pr.setString(2,check_in_date);
            pr.setString(3,check_out_date);
            pr.setInt(4,car_id);
            pr.setInt(5,company_id);
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
