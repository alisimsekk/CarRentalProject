package com.View;

import com.Helper.Config;
import com.Helper.Helper;
import com.Model.Addition;
import com.Model.Car;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

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

    DefaultTableModel mdl_addition_list;
    private Object[] row_addition_list;

    public ReservationGUI(Car c, long number_of_days, String check_in, String check_out) {
        add(wrapper);
        setSize(1000, 400);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        fld_rez_brand.setText(c.getBrand());
        fld_rez_model.setText(c.getModel());
        fld_rez_type.setText(c.getType());
        fld_rez_transmission.setText(c.getTransmission());
        fld_rez_fuel.setText(c.getFuel());
        fld_check_in.setText(String.valueOf(check_in));
        fld_check_out.setText(String.valueOf(check_out));
        fld_number_of_day.setText(String.valueOf(number_of_days));



//ek hizmet tablosu kodları başlangıcı
        mdl_addition_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_addition_list = {"Açıklama", "Fiyat (TL)"};
        mdl_addition_list.setColumnIdentifiers(col_addition_list);
        row_addition_list = new Object[col_addition_list.length];
        loadAdditionModel(c.getId());
        tbl_addition_left.setModel(mdl_addition_list);
        tbl_addition_left.getTableHeader().setReorderingAllowed(false);
//ek hizmet tablosu kodları bitişi

/// burdan devam, ek hismet eklenmesi tabloya aktarması


    }



    private void loadAdditionModel(int car_id) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_addition_left.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Addition obj : Addition.getListByCarID(car_id)){
            i = 0;
            row_addition_list[i++] = obj.getExplanation();
            row_addition_list[i++] = obj.getPrice();
            mdl_addition_list.addRow(row_addition_list);

        }
    }



}
