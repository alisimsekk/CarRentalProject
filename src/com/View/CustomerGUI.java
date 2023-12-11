package com.View;

import com.helper.Config;
import com.helper.Helper;
import com.helper.Item;
import com.Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private JScrollPane scrl_car_left;
    private JTable tbl_car_addition;
    private JButton btn_reservation;
    private JTextField fld_car_id;
    private JComboBox cmb_city;
    private JComboBox cmb_car_type;
    private JTextField fld_start_date;
    private JTextField fld_end_date;
    private JPanel pnl_reserved_car;
    private JScrollPane scrl_reserved_car;
    private JTable tbl_reserved_car;
    private JPanel pnl_reservation;
    private JPanel pnl_reserved_car_top;
    private JTextField fld_reserved_car_id;
    private JButton btn_reserved_car_cancel;
    private JLabel jlbl_welcome;
    private JScrollPane scrl_car_right;
    private JTable tbl_car_season;
    private JTable tbl_reserved_addition;

    DefaultTableModel mdl_car_list;
    private Object[] row_car_list;

    DefaultTableModel mdl_addition_list;
    private Object[] row_addition_list;

    private int select_car_id;
    private String check_in;
    private String check_out;

    DefaultTableModel mdl_reserved_car_list;
    private Object[] row_reserved_car_list;

    DefaultTableModel mdl_reserved_addition_list;
    private Object[] row_reserved_addition_list;

    DefaultTableModel mdl_car_season_list;
    private Object[] row_car_season_list;

    private int reserved_car_id;
    ReservedCar reservedCar;

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

        jlbl_welcome.setText("Hoş geldin " + customer.getName());

//Araç tablosu kodları başlangıcı
        mdl_car_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_car_list = {"id", "Marka", "Model", "Model Yılı", "Tip", "Fiyat", "Vites", "Yakıt", "Firma", "Şehir"};
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
        tbl_car_addition.getColumnModel().getColumn(1).setMaxWidth(220);
        tbl_car_addition.getColumnModel().getColumn(2).setMaxWidth(75);
        tbl_car_addition.getColumnModel().getColumn(3).setMaxWidth(75);
//ek hizmet tablosu kodları bitişi

//kiralama dönemi tablosu kodları başlangıcı
        mdl_car_season_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_car_season_list = {"Başlangıç Tarihi", "Bitiş Tarihi"};
        mdl_car_season_list.setColumnIdentifiers(col_car_season_list);
        row_car_season_list = new Object[col_car_season_list.length];
        loadSeasonModel(select_car_id);
        tbl_car_season.setModel(mdl_car_season_list);
        tbl_car_season.getTableHeader().setReorderingAllowed(false);
       // tbl_car_season.getColumnModel().getColumn(0).setMaxWidth(100);
       // tbl_car_season.getColumnModel().getColumn(1).setMaxWidth(100);
//kiralama dönemi tablosu kodları bitişi

//Rezervasyon yapılmış araç tablosu kodları başlangıcı
        mdl_reserved_car_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_reserved_car_list = {"id", "Firma", "Şehir", "Marka", "Model", "Model Yılı", "Tip", "Vites", "Yakıt", "Kiralama Tarihi", "Teslim Tarihi", "Toptam Tutar (TL)"};
        mdl_reserved_car_list.setColumnIdentifiers(col_reserved_car_list);
        row_reserved_car_list = new Object[col_reserved_car_list.length];
        loadReservedCarModel();
        tbl_reserved_car.setModel(mdl_reserved_car_list);
        tbl_reserved_car.getTableHeader().setReorderingAllowed(false);
        tbl_reserved_car.getColumnModel().getColumn(0).setMaxWidth(75);
//Rezervasyon yapılmış araç tablosu kodları bitişi

//Rezervasyon iptali için araç id sini alma
        tbl_reserved_car.getSelectionModel().addListSelectionListener(e -> {
            try{
                reserved_car_id = Integer.parseInt(tbl_reserved_car.getValueAt(tbl_reserved_car.getSelectedRow(),0).toString());
                reservedCar = ReservedCar.getFetch(reserved_car_id);
            }
            catch (Exception ex){

            }
            fld_reserved_car_id.setText(Integer.toString(reserved_car_id));
        });

//Ek hizmetleri listelemek için araç id sini alma
        tbl_car_list.getSelectionModel().addListSelectionListener(e -> {
            try{
                select_car_id = Integer.parseInt(tbl_car_list.getValueAt(tbl_car_list.getSelectedRow(),0).toString());
            }
            catch (Exception ex){

            }
            fld_car_id.setText(Integer.toString(select_car_id));
            loadAdditionModel(select_car_id);
            loadSeasonModel(select_car_id);
        });

        loadCityCombo();
        loadCarTypeCombo();


 //araç filtreleme butonu
        /*int comparison=d.compareTo(d1);
        It returns the value 0 if d1 is equal to d.  return 0
        It returns a value less than 0 if this d is before the d1.   return -1
        It returns a value greater than 0 if d is after d1. return 1
         */
        btn_car_search.addActionListener(e -> {
            Car avaibleCar = null;
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
                    if (season_start_date.compareTo(check_in_date) != 1 && season_end_date.compareTo(check_out_date) != -1){
                        ArrayList<ReservedCar> reservedCarList = new ArrayList<>();
                        reservedCarList = ReservedCar.getListByCarID(c.getId());
                        if (reservedCarList.size() != 0){
                            for (ReservedCar reservedCar : reservedCarList){
                                    String reservedCar_check_in = reservedCar.getCheck_in();
                                    String reservedCar_check_out = reservedCar.getCheck_out();
                                    Date reservedCar_check_in_date = null;
                                    Date reservedCar_check_out_date = null;
                                    try {
                                        reservedCar_check_in_date = formatter.parse(reservedCar_check_in);
                                        reservedCar_check_out_date = formatter.parse(reservedCar_check_out);
                                    } catch (ParseException ex) {
                                        ex.printStackTrace();
                                    }
                                    if ( check_in_date.compareTo(reservedCar_check_in_date) != 1 && reservedCar_check_in_date.compareTo(check_out_date) != 1
                                            && check_out_date.compareTo(reservedCar_check_out_date) != 1){
                                        //araç uygun değil, filtrelemeye alma.
                                        avaibleCar = null;
                                        break;
                                    }
                                    else if (reservedCar_check_in_date.compareTo(check_in_date) != 1 && check_out_date.compareTo(reservedCar_check_out_date) != 1){
                                        //araç uygun değil, filtrelemeye alma.
                                        avaibleCar = null;
                                        break;
                                    }
                                    else if (reservedCar_check_in_date.compareTo(check_in_date) != 1 && check_in_date.compareTo(reservedCar_check_out_date) != 1 && reservedCar_check_out_date.compareTo(check_out_date) != 1){
                                        //araç uygun değil, filtrelemeye alma.
                                        avaibleCar = null;
                                        break;
                                    }
                                    else if (check_in_date.compareTo(reservedCar_check_in_date) != 1 && reservedCar_check_out_date.compareTo(check_out_date) != 1){
                                        //araç uygun değil, filtrelemeye alma.
                                        avaibleCar = null;
                                        break;
                                    }
                                    else {
                                        avaibleCar = c;
                                    }
                            }
                            if ( avaibleCar != null){
                                searchingCarWithDate.add(avaibleCar);
                            }
                        }
                        else{
                            searchingCarWithDate.add(c);
                        }
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

            DefaultTableModel clearCarSeasonModel = (DefaultTableModel) tbl_car_season.getModel();
            clearCarSeasonModel.setRowCount(0);
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
                        resGUI.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                loadReservedCarModel();
                                loadCarModel();
                                DefaultTableModel clearModel = (DefaultTableModel) tbl_car_addition.getModel();
                                clearModel.setRowCount(0);
                                DefaultTableModel clearCarSeasonModel = (DefaultTableModel) tbl_car_season.getModel();
                                clearCarSeasonModel.setRowCount(0);
                                fld_car_id.setText(null);
                                fld_start_date.setText(null);
                                fld_end_date.setText(null);
                            }
                        });
                    }
                    else {
                        Helper.showMsg("Seçili araç girilen tarihlerde uygun değil. Araç Filtrelemeden uygun araçları bulabilirsiniz");
                    }
                }
            }
        });

        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI log = new LoginGUI();
        });

//rezervasyon iptal butonu kodları
        btn_reserved_car_cancel.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_reserved_car_id)){
                Helper.showMsg("fill");
            }
            else {
                if (ReservedCar.remove(reserved_car_id)){
                    if (SelectedAddition.remove(customer.getId(), reservedCar.getCheck_in(), reservedCar.getCar_id())){
                        Helper.showMsg("done");
                        loadReservedCarModel();
                        fld_reserved_car_id.setText(null);
                    }
                }
                else {
                    Helper.showMsg("Kiralama gününe 1 günden az kaldığı için iptal gerçekleştirilemedi.");
                }
            }
        });
    }

    private void loadReservedCarModel() {   //rezervasyon yapılmış araçları tabloya aktaran metod
        DefaultTableModel clearModel = (DefaultTableModel) tbl_reserved_car.getModel();
        clearModel.setRowCount(0);
        int i;
        for (ReservedCar obj : ReservedCar.getListByCustomerID(customer.getId())){
            i = 0;
            row_reserved_car_list[i++] = obj.getId();
            row_reserved_car_list[i++] = Company.getFetchByID(Car.getFetch(obj.getCar_id()).getCompany_id()).getName();
            row_reserved_car_list[i++] = Car.getFetch(obj.getCar_id()).getCity();
            row_reserved_car_list[i++] = Car.getFetch(obj.getCar_id()).getBrand();
            row_reserved_car_list[i++] = Car.getFetch(obj.getCar_id()).getModel();
            row_reserved_car_list[i++] = Car.getFetch(obj.getCar_id()).getYear();
            row_reserved_car_list[i++] = Car.getFetch(obj.getCar_id()).getType();
            row_reserved_car_list[i++] = Car.getFetch(obj.getCar_id()).getFuel();
            row_reserved_car_list[i++] = Car.getFetch(obj.getCar_id()).getTransmission();
            row_reserved_car_list[i++] = obj.getCheck_in();
            row_reserved_car_list[i++] = obj.getCheck_out();
            row_reserved_car_list[i++] = obj.getTotal_price();
            mdl_reserved_car_list.addRow(row_reserved_car_list);
        }
    }

    private void loadCarModel() {       //sisteme ekli araçları tabloya aktaran metod
        DefaultTableModel clearModel = (DefaultTableModel) tbl_car_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Car obj : Car.getList()){
            i = 0;
            row_car_list[i++] = obj.getId();
            row_car_list[i++] = obj.getBrand();
            row_car_list[i++] = obj.getModel();
            row_car_list[i++] = obj.getYear();
            row_car_list[i++] = obj.getType();
            row_car_list[i++] = obj.getPrice();
            row_car_list[i++] = obj.getTransmission();
            row_car_list[i++] = obj.getFuel();
            row_car_list[i++] = Company.getFetchByID(obj.getCompany_id()).getName();
            row_car_list[i++] = obj.getCity();
            mdl_car_list.addRow(row_car_list);
        }
    }

    private void loadCarModel(ArrayList<Car> carList) {     //filtrelenmiş araçları tabloya aktaran metod
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
                row_car_list[i++] = obj.getYear();
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

    private void loadSeasonModel(int id) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_car_season.getModel();
        clearModel.setRowCount(0);
        Car obj = Car.getFetch(id);
        if (obj != null){
            row_car_season_list[0] = obj.getSeason_start();
            row_car_season_list[1] = obj.getSeason_end();
            mdl_car_season_list.addRow(row_car_season_list);
        }
    }

//Şehir isimlerini combo box a aktaran metod
    public void loadCityCombo(){
        cmb_city.removeAllItems();
        cmb_city.addItem(new Item(0,null));
        ArrayList<String> cityName = new ArrayList<>();
        for (Car obj : Car.getList()){
            String city = obj.getCity().substring(0,1).toUpperCase() + obj.getCity().substring(1).toLowerCase();
            if (!cityName.contains(city)){
                cmb_city.addItem(new Item(obj.getId(), city));
            }
            cityName.add(city);
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
