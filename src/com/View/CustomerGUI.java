package com.View;

import com.Helper.Config;
import com.Helper.Helper;
import com.Helper.Item;
import com.Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomerGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JTabbedPane tab_company;
    private JPanel pnl_car_list;
    private JPanel pnl_car_top;
    private JButton btn_car_search;
    private JScrollPane scrl_car_list;
    private JTable tbl_car_list;
    private JPanel pnl_car_right;
    private JPanel pnl_car_left;
    private JScrollPane scrl_car_left;
    private JTable tbl_car_addition;
    private JButton btn_reservation;
    private JTextField fld_car_id;
    private JComboBox cmb_city;
    private JComboBox cmb_car_type;
    private JTextField fld_start_date;
    private JTextField fld_end_date;

    DefaultTableModel mdl_car_list;
    private Object[] row_car_list;

    DefaultTableModel mdl_addition_list;
    private Object[] row_addition_list;

    private int select_car_id;
    private String check_in;
    private String check_out;

    private Customer customer;

    public CustomerGUI(Customer customer) {
        this.customer = customer;
        add(wrapper);
        setSize(1200,600);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

//Araç tablosu kodları başlangıcı
        mdl_car_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_car_list = {"id", "Marka", "Model", "Tip", "Fiyat", "Vites", "Yakıt", "Firma", "Şehir"};
        mdl_car_list.setColumnIdentifiers(col_car_list);
        row_car_list = new Object[col_car_list.length];
        loadCarModel();
        tbl_car_list.setModel(mdl_car_list);
        tbl_car_list.getTableHeader().setReorderingAllowed(false);
        tbl_car_list.getColumnModel().getColumn(0).setMaxWidth(75);
//Araç tablosu kodları bitişi

//ek hizmet tablosu kodları başlangıcı
        mdl_addition_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_addition_list = {"id", "Açıklama", "Fiyat", "Araç id"};
        mdl_addition_list.setColumnIdentifiers(col_addition_list);
        row_addition_list = new Object[col_addition_list.length];
        loadAdditionModel(select_car_id);
        tbl_car_addition.setModel(mdl_addition_list);
        tbl_car_addition.getTableHeader().setReorderingAllowed(false);
        tbl_car_addition.getColumnModel().getColumn(0).setMaxWidth(75);
//ek hizmet tablosu kodları bitişi


//Ek hizmetleri listelemek için araç id sini alma
        tbl_car_list.getSelectionModel().addListSelectionListener(e -> {
            try{
                select_car_id = Integer.parseInt(tbl_car_list.getValueAt(tbl_car_list.getSelectedRow(),0).toString());
            }
            catch (Exception ex){

            }
            fld_car_id.setText(Integer.toString(select_car_id));
            loadAdditionModel(select_car_id);
        });

        loadCityCombo();
        loadCarTypeCombo();

 //araç filtreleme butonu
        btn_car_search.addActionListener(e -> {

            String city = cmb_city.getSelectedItem().toString();
            String carType = cmb_car_type.getSelectedItem().toString();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            check_in = fld_start_date.getText().trim();
            check_out = fld_end_date.getText().trim();
            Date check_in_date = null;
            Date check_out_date = null;
            try {
                check_in_date = formatter.parse(check_in);
                check_out_date = formatter.parse(check_out);
            } catch (ParseException ex) {

            }
            String query = "SELECT * FROM car WHERE city LIKE '%{{city}}%' AND type LIKE '%{{type}}%'";
            if (city == null){
                city = "";
            }
            if (carType == null){
                carType = "";
            }
            query = query.replace("{{city}}", city);
            query = query.replace("{{type}}", carType);
            ArrayList<Car> searchingCar = Car.searchCarList(query);
            ArrayList<Car> searchingCarWithDate = new ArrayList<>();

            if (Helper.isFieldEmpty(fld_start_date) && Helper.isFieldEmpty(fld_end_date)){
                if (searchingCar.size() == 0){
                    Helper.showMsg("Aradığınız kriterlere uygun araç bulunamadı");
                    DefaultTableModel clearModel = (DefaultTableModel) tbl_car_list.getModel();
                    clearModel.setRowCount(0);
                }
                else {
                    loadCarModel(searchingCar);
                }
            }
            else if (Helper.isFieldEmpty(fld_start_date) || Helper.isFieldEmpty(fld_end_date)){
                Helper.showMsg("Tarih bilgisi eksik");
            }
            else {
                for (Car c : searchingCar){
                    String season_start = c.getSeason_start();
                    String season_end = c.getSeason_end();
                    Date season_start_date = null;
                    Date season_end_date = null;
                    try {
                        season_start_date = formatter.parse(season_start);
                        season_end_date = formatter.parse(season_end);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    if (season_start_date.before(check_in_date) && season_end_date.after(check_out_date)){
                        searchingCarWithDate.add(c);
                    }
                }
                if (searchingCarWithDate.size() == 0){
                    Helper.showMsg("Aradığınız kriterlere uygun araç bulunamadı");
                    DefaultTableModel clearModel = (DefaultTableModel) tbl_car_list.getModel();
                    clearModel.setRowCount(0);
                }
                else {
                    loadCarModel(searchingCarWithDate);
                }
            }
            DefaultTableModel clearAdditionModel = (DefaultTableModel) tbl_car_addition.getModel();
            clearAdditionModel.setRowCount(0);
        });

//rezervasyon butonu
        btn_reservation.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_car_id) || Helper.isFieldEmpty(fld_start_date) || Helper.isFieldEmpty(fld_end_date)){
                Helper.showMsg("Araç seçimi ve/veya tarih bilgisi eksik");
            }
            else {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                check_in = fld_start_date.getText().trim();
                check_out = fld_end_date.getText().trim();
                Date check_in_date = null;
                Date check_out_date = null;
                try {
                    check_in_date = formatter.parse(check_in);
                    check_out_date = formatter.parse(check_out);
                } catch (ParseException ex) {

                }
                Car c = Car.getFetch(select_car_id);
                String season_start = c.getSeason_start();
                String season_end = c.getSeason_end();
                Date season_start_date = null;
                Date season_end_date = null;
                try {
                    season_start_date = formatter.parse(season_start);
                    season_end_date = formatter.parse(season_end);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                if (check_in_date.after(check_out_date)){
                    Helper.showMsg("Araç alım tarihi, teslim tarihinden önce olamaz");
                }
                else{
                    if (season_start_date.before(check_in_date) && season_end_date.after(check_out_date)){
                        long diff = check_out_date.getTime() - check_in_date.getTime();
                        long seconds = diff / 1000;
                        long minutes = seconds / 60;
                        long hours = minutes / 60;
                        long number_of_days = hours / 24;
                        ReservationGUI resGUI = new ReservationGUI (customer, c, number_of_days, check_in,  check_out);
                    }
                    else {
                        Helper.showMsg("Seçili araç girilen tarihlerde uygun değil. Araç Filtrelemeden uygun araçları bulabilirsiniz");
                    }
                }
            }
        });
    }

    private void loadCarModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_car_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Car obj : Car.getList()){
            i = 0;
            row_car_list[i++] = obj.getId();
            row_car_list[i++] = obj.getBrand();
            row_car_list[i++] = obj.getModel();
            row_car_list[i++] = obj.getType();
            row_car_list[i++] = obj.getPrice();
            row_car_list[i++] = obj.getTransmission();
            row_car_list[i++] = obj.getFuel();
            row_car_list[i++] = Company.getFetchByID(obj.getCompany_id()).getName();
            row_car_list[i++] = obj.getCity();
            mdl_car_list.addRow(row_car_list);
        }
    }

    private void loadCarModel(ArrayList<Car> carList) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_car_list.getModel();
        clearModel.setRowCount(0);
        int i;
        if (carList == null){
            Helper.showMsg("Aradığınız kriterlere uygun oda bulunamadı");
        }
        else {
            for (Car obj : carList) {
                i = 0;
                row_car_list[i++] = obj.getId();
                row_car_list[i++] = obj.getBrand();
                row_car_list[i++] = obj.getModel();
                row_car_list[i++] = obj.getType();
                row_car_list[i++] = obj.getPrice();
                row_car_list[i++] = obj.getTransmission();
                row_car_list[i++] = obj.getFuel();
                row_car_list[i++] = Company.getFetchByID(obj.getCompany_id()).getName();
                row_car_list[i++] = obj.getCity();
                mdl_car_list.addRow(row_car_list);
            }
        }
    }



    private void loadAdditionModel(int car_id) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_car_addition.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Addition obj : Addition.getListByCarID(car_id)){
            i = 0;
            row_addition_list[i++] = obj.getId();
            row_addition_list[i++] = obj.getExplanation();
            row_addition_list[i++] = obj.getPrice();
            row_addition_list[i++] = obj.getCar_id();

            mdl_addition_list.addRow(row_addition_list);

        }
    }

//Şehir isimlerini combo box a aktaran metod
    public void loadCityCombo(){
        cmb_city.removeAllItems();
        cmb_city.addItem(new Item(0,null));
        ArrayList<String> cityName = new ArrayList<>();
        for (Car obj : Car.getList()){
            if (!cityName.contains(obj.getCity())){
                cmb_city.addItem(new Item(obj.getId(), obj.getCity()));
            }
            cityName.add(obj.getCity());
        }
    }

//Kasa tipleri isimlerini combo box a aktaran metod
    public void loadCarTypeCombo(){
        cmb_car_type.removeAllItems();
        cmb_car_type.addItem(new Item(0,null));
        ArrayList<String> carType = new ArrayList<>();
        for (Car obj : Car.getList()){
            if (!carType.contains(obj.getType())){
                cmb_car_type.addItem(new Item(obj.getId(), obj.getType()));
            }
            carType.add(obj.getType());
        }
    }




}
