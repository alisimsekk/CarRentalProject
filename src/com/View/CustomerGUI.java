package com.View;

import com.Helper.Config;
import com.Helper.Helper;
import com.Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
    private JTextField fld_price;
    private JComboBox cmb_city;
    private JComboBox cmb_car_type;
    private JTextField fld_start_date;
    private JTextField fld_end_date;

    DefaultTableModel mdl_car_list;
    private Object[] row_car_list;

    DefaultTableModel mdl_addition_list;
    private Object[] row_addition_list;

    private int select_car_id;

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

        Object[] col_car_list = {"id", "Marka", "Model", "Tip", "Fiyat", "Vites", "Yakıt", "Şehir"};
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
            row_car_list[i++] = Company.getFetchByID(obj.getCompany_id()).getCity();
            mdl_car_list.addRow(row_car_list);
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




}
