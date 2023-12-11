package com.View;

import com.helper.Config;
import com.helper.Helper;
import com.Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReservationGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_left;
    private JPanel pnl_right;
    private JTextField fld_rez_brand;
    private JTextField fld_rez_model;
    private JTextField fld_rez_type;
    private JTextField fld_rez_transmission;
    private JTextField fld_rez_fuel;
    private JTextField fld_check_in;
    private JTextField fld_check_out;
    private JPanel pnl_addition_left;
    private JScrollPane scrl_addition_left;
    private JPanel pnl_addition_right;
    private JScrollPane scrl_addition_right;
    private JTable tbl_addition_left;
    private JTable tbl_addition_right;
    private JButton btn_addition_add;
    private JButton btn_addition_remove;
    private JTextField fld_number_of_day;
    private JTextField fld_price;
    private JButton btn_reservation;
    private JTextField fld_year;

    DefaultTableModel mdl_addition_list;
    private Object[] row_addition_list;

    int select_addition_id;     //soldaki tabloda seçilen ek hizmet id si
    Addition select_addition;

    String selected_addition_explanation;   //sağdaki tabloda eklenmiş ek hizmet açıklaması
    SelectedAddition selected_addition;

    DefaultTableModel mdl_selectedAddition_list;
    private Object[] row_selectedAddition_list;

    public ReservationGUI(Customer customer, Car car, long number_of_days, String check_in, String check_out) {
        add(wrapper);
        setSize(1000, 600);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(true);
        setVisible(true);

        fld_rez_brand.setText(car.getBrand());
        fld_rez_model.setText(car.getModel());
        fld_year.setText(String.valueOf(car.getYear()));
        fld_rez_type.setText(car.getType());
        fld_rez_transmission.setText(car.getTransmission());
        fld_rez_fuel.setText(car.getFuel());
        fld_check_in.setText(String.valueOf(check_in));
        fld_check_out.setText(String.valueOf(check_out));
        fld_number_of_day.setText(String.valueOf(number_of_days));
        int total_price = (int) (car.getPrice() * number_of_days);
        fld_price.setText(String.valueOf(total_price));

//soldaki ek hizmet tablosu kodları başlangıcı
        mdl_addition_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_addition_list = {"id", "Açıklama", "Fiyat (TL)"};
        mdl_addition_list.setColumnIdentifiers(col_addition_list);
        row_addition_list = new Object[col_addition_list.length];
        loadAdditionModel(car.getId());
        tbl_addition_left.setModel(mdl_addition_list);
        tbl_addition_left.getTableHeader().setReorderingAllowed(false);
//soldaki ek hizmet tablosu kodları bitişi

//sağdaki ek hizmet tablosu kodları başlangıcı
        mdl_selectedAddition_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_selectedAddition_list = {"Açıklama", "Fiyat (TL)"};
        mdl_selectedAddition_list.setColumnIdentifiers(col_selectedAddition_list);
        row_selectedAddition_list = new Object[col_selectedAddition_list.length];
        loadSelectedAdditionModel(car.getId(), check_in, customer.getId());
        tbl_addition_right.setModel(mdl_selectedAddition_list);
        tbl_addition_right.getTableHeader().setReorderingAllowed(false);
//sağdaki ek hizmet tablosu kodları bitişi

//seçilen ek hizmetin bilgilerini alma kodları
        tbl_addition_left.getSelectionModel().addListSelectionListener(e -> {
            try{
                select_addition_id = Integer.parseInt(tbl_addition_left.getValueAt(tbl_addition_left.getSelectedRow(),0).toString());
            }
            catch (Exception ex){

            }
            select_addition = Addition.getFetchByID(select_addition_id);
        });

//seçilen ek hizmeti sağdaki tabloya ekleme butonu
        btn_addition_add.addActionListener(e -> {
            String explanation = select_addition.getExplanation();
            int price = select_addition.getPrice();
            int car_id = select_addition.getCar_id();
            if (SelectedAddition.add(explanation, price,check_in, check_out, car_id, customer.getId())){
                Helper.showMsg("done");
            }
            loadSelectedAdditionModel(car.getId(), check_in, customer.getId());
            calculatePrice(car, (int) number_of_days, total_price, customer);
        });

//eklenen ek hizmetin bilgilerini alma kodları (sağdaki tablo)
        tbl_addition_right.getSelectionModel().addListSelectionListener(e -> {
            try{
                selected_addition_explanation = tbl_addition_right.getValueAt(tbl_addition_right.getSelectedRow(),0).toString();
            }
            catch (Exception ex){

            }
            selected_addition = SelectedAddition.getFetchByExplanation(selected_addition_explanation);
        });

//Eklenen ek hizmeti çıkarmaya yarıyan kodlar(sağdaki tablo için)
        btn_addition_remove.addActionListener(e -> {
            int selected_addition_id = selected_addition.getId();
            if (SelectedAddition.remove(selected_addition_id)){
                Helper.showMsg("done");
            }
            loadSelectedAdditionModel(car.getId(), check_in, customer.getId());
            calculatePrice(car, (int) number_of_days, total_price, customer);
        });

//müşterinin aracı rezerve etmesini sağlayan butona ait kodlar
        btn_reservation.addActionListener(e -> {
            int customer_id = customer.getId();
            String check_in_date = check_in;
            String check_out_date = check_out;
            int price = Integer.parseInt(fld_price.getText());
            int car_id = car.getId();
            int company_id = car.getCompany_id();

            if (ReservedCar.add(customer_id, check_in_date, check_out_date, price, car_id, company_id )){
                Helper.showMsg("done");
            }
            else {
                Helper.showMsg("error");
            }
        });
    }

//araca tanımlı ek özellikleri soldaki tabloda gösteren metod
    private void loadAdditionModel(int car_id) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_addition_left.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Addition obj : Addition.getListByCarID(car_id)){
            i = 0;
            row_addition_list[i++] = obj.getId();
            row_addition_list[i++] = obj.getExplanation();
            row_addition_list[i++] = obj.getPrice();
            mdl_addition_list.addRow(row_addition_list);

        }
    }

//satın almak üzere seçilmiş ek hizmetleri sağdaki tabloda gösteren metod
    private void loadSelectedAdditionModel(int car_id, String check_in, int customer_id) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_addition_right.getModel();
        clearModel.setRowCount(0);
        int i;
        for (SelectedAddition obj : SelectedAddition.getListByCarIDandCheckin(car_id, check_in, customer_id)){
            i = 0;
            row_selectedAddition_list[i++] = obj.getExplanation();
            row_selectedAddition_list[i++] = obj.getPrice();
            mdl_selectedAddition_list.addRow(row_selectedAddition_list);
        }
    }

//sağdaki tabloya eklenen ek hizmetleri toplam tutara ekleyen metod
    private void calculatePrice(Car c, int number_of_days, int price, Customer customer){
        int total_price1= price;
        for (SelectedAddition selAdd : SelectedAddition.getListByCarIDandCheckin(c.getId(), fld_check_in.getText(), customer.getId())){
            total_price1 += selAdd.getPrice();
        }
        fld_price.setText(String.valueOf(total_price1));
    }

}
