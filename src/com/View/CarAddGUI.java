package com.View;

import com.helper.Config;
import com.helper.Helper;
import com.Model.Car;
import com.Model.Company;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class CarAddGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_car_add_top;
    private JPanel pnl_car_add_bottom;
    private JTextField fld_brand;
    private JTextField fld_model;
    private JComboBox cmb_type;
    private JTextField fld_season_start;
    private JTextField fld_season_end;
    private JTextField fld_price;
    private JComboBox cmb_transmission;
    private JComboBox cmb_fuel;
    private JButton btn_car_add;
    private JTextField fld_year;

    private final Company company;

    public CarAddGUI(Company company) {
        this.company = company;
        add(wrapper);
        setSize(500, 420);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

//Araç ekleme butonu kodları başlangıcı
        btn_car_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_brand) || Helper.isFieldEmpty(fld_model) || cmb_type.getSelectedItem().toString().equals("")
                    || Helper.isFieldEmpty(fld_season_start) || Helper.isFieldEmpty(fld_season_end) || Helper.isFieldEmpty(fld_price)
                    || cmb_transmission.getSelectedItem().toString().equals("") || cmb_fuel.getSelectedItem().toString().equals("")){
                Helper.showMsg("fill");
            }
            else{
                ZoneId defaultZoneId = ZoneId.systemDefault();
                //creating the instance of LocalDate using the day, month, year info
                LocalDate today = LocalDate.now();
                //local date + atStartOfDay() + default time zone + toInstant() = Date
                Date today_date = Date.from(today.atStartOfDay(defaultZoneId).toInstant());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String season_start =  fld_season_start.getText();
                Date season_start_date = null;
                try {
                    season_start_date = formatter.parse(season_start);
                } catch (ParseException ex) {

                }
                long diff = season_start_date.getTime() - today_date.getTime();
                long seconds = diff / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long difference_date = hours / 24;

                String brand = fld_brand.getText();
                String model = fld_model.getText();
                int year = Integer.parseInt(fld_year.getText());
                String type = cmb_type.getSelectedItem().toString();
                String season_end = fld_season_end.getText();
                int price = Integer.parseInt(fld_price.getText());
                String transmission = cmb_transmission.getSelectedItem().toString();
                String fuel = cmb_fuel.getSelectedItem().toString();
                int company_id = company.getId();
                String city = company.getCity();


                if (difference_date<=0){
                    Helper.showMsg("Kiralama dönemi başlangıcı bugünden daha önce bir tarih olamaz");
                }

                else {
                    if (Car.add(brand, model, year, type, season_start, season_end, price, transmission, fuel, company_id, city)){
                        Helper.showMsg("Araç başarı ile eklendi.");
                        fld_brand.setText(null);
                        fld_model.setText(null);
                        fld_year.setText(null);
                        cmb_type.setSelectedIndex(0);
                        fld_season_start.setText(null);
                        fld_season_end.setText(null);
                        fld_price.setText(null);
                        cmb_transmission.setSelectedIndex(0);
                        cmb_fuel.setSelectedIndex(0);
                    }
                    else {
                        Helper.showMsg("error");
                    }
                }
            }
        });
//Araç ekleme butonu kodları başlangıcı

    }
}
