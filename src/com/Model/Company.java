package com.Model;


import com.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Company extends User {
    public Company(int id, String name, String uname, String pass, String email, String type, String city) {
        super(id, name, uname, pass, email, type, city);
    }

    public Company() {
    }

    public static Company getFetchByID(int id){
        Company obj = null;
        String query = "SELECT * FROM user WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Company();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setEmail(rs.getString("email"));
                obj.setType(rs.getString("type"));
                obj.setCity(rs.getString("city"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
