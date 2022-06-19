package com.View;

import com.Helper.Config;
import com.Helper.Helper;
import com.Model.Addition;
import com.Model.Car;
import com.Model.Company;
import com.Model.ReservedCar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CompanyGUI extends JFrame{
    private JPanel wrapper;
    private JPanel pnl_top;
    private JTabbedPane tab_company;
    private JButton btn_logout;
    private JPanel pnl_car_list;
    private JPanel pnl_car_top;
    private JScrollPane scrl_car_list;
    private JTable tbl_car_list;
    private JButton btn_car_add;
    private JPanel pnl_car_left;
    private JScrollPane scrl_car_bottom;
    private JTable tbl_car_addition;
    private JButton btn_adition_add;
    private JTextField fld_car_id;
    private JPanel pnl_car_right;
    private JTextArea txtArea_axplanation;
    private JTextField fld_addition_price;
    private JPanel pnl_reservedcar;
    private JScrollPane scrl_reserved_car;
    private JTable tbl_reserved_car;

    DefaultTableModel mdl_car_list;
    private Object[] row_car_list;

    DefaultTableModel mdl_addition_list;
    private Object[] row_addition_list;

    private int select_car_id;

    DefaultTableModel mdl_reserved_car_list;
    private Object[] row_reserved_car_list;

    private Company company;

    public CompanyGUI(Company company) {
        this.company = company;
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

        Object[] col_car_list = {"id", "Marka", "Model", "Tip", "Sezon Başlangıcı", "Sezon Bitişi", "Fiyat", "Vites", "Yakıt"};
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


//Rezervasyon yapılmış araç tablosu kodları başlangıcı
        mdl_reserved_car_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_reserved_car_list = {"id", "Firma", "Şehir", "Marka", "Model", "Tip", "Vites", "Yakıt", "Kiralama Tarihi", "Teslim Tarihi", "Toplam Tutar (TL)"};
        mdl_reserved_car_list.setColumnIdentifiers(col_reserved_car_list);
        row_reserved_car_list = new Object[col_reserved_car_list.length];
        loadReservedCarModel();
        tbl_reserved_car.setModel(mdl_reserved_car_list);
        tbl_reserved_car.getTableHeader().setReorderingAllowed(false);
        tbl_reserved_car.getColumnModel().getColumn(0).setMaxWidth(75);
//Rezervasyon yapılmış araç tablosu kodları bitişi


//Ek hizmetleri listelemek için araç id sini alma
        tbl_car_list.getSelectionModel().addListSelectionListener(e -> {
            try{
                select_car_id = Integer.parseInt(tbl_car_list.getValueAt(tbl_car_list.getSelectedRow(),0).toString());
            }
            catch (Exception ex){

            }
            fld_car_id.setText(Integer.toString(select_car_id));
            loadAdditionModel(select_car_id);

            //select_car_id = 0;
        });

//araç ekle butonu kodları başlangıcı
        btn_car_add.addActionListener(e -> {
            CarAddGUI carAddGUI = new CarAddGUI(company);
            carAddGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarModel();
                    tbl_car_list.getSelectionModel().clearSelection();
                    super.windowClosed(e);
                }
            });
        });
//araç ekle butonu kodları bitişi

//ek özellik ekle butonu kodları başlangıcı
        btn_adition_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_car_id) || Helper.isAreaEmpty(txtArea_axplanation) || Helper.isFieldEmpty(fld_addition_price)){
                Helper.showMsg("fill");
            }
            else {
                String explanation = txtArea_axplanation.getText();
                int price = Integer.parseInt(fld_addition_price.getText());
                int car_id = Integer.parseInt(fld_car_id.getText());
                if (Addition.add(explanation, price, car_id)){
                    Helper.showMsg("done");
                    loadAdditionModel(select_car_id);
                    txtArea_axplanation.setText(null);
                    fld_addition_price.setText(null);
                }
                else {
                    Helper.showMsg("error");
                }
            }
        });
//ek özellik ekle butonu kodları bitişi


        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI log = new LoginGUI();
        });
    }


    private void loadCarModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_car_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Car obj : Car.getList()){
            i = 0;
            if (obj.getCompany_id() == company.getId() ){
                row_car_list[i++] = obj.getId();
                row_car_list[i++] = obj.getBrand();
                row_car_list[i++] = obj.getModel();
                row_car_list[i++] = obj.getType();
                row_car_list[i++] = obj.getSeason_start();
                row_car_list[i++] = obj.getSeason_end();
                row_car_list[i++] = obj.getPrice();
                row_car_list[i++] = obj.getTransmission();
                row_car_list[i++] = obj.getFuel();
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

    private void loadReservedCarModel() {   //rezervasyon yapılmış araçları tabloya aktaran metod
        DefaultTableModel clearModel = (DefaultTableModel) tbl_reserved_car.getModel();
        clearModel.setRowCount(0);
        int i;
        for (ReservedCar obj : ReservedCar.getListByCompanyID(company.getId())){
            i = 0;
            row_reserved_car_list[i++] = obj.getId();
            row_reserved_car_list[i++] = Company.getFetchByID(Car.getFetch(obj.getCar_id()).getCompany_id()).getName();
            row_reserved_car_list[i++] = Car.getFetch(obj.getCar_id()).getCity();
            row_reserved_car_list[i++] = Car.getFetch(obj.getCar_id()).getBrand();
            row_reserved_car_list[i++] = Car.getFetch(obj.getCar_id()).getModel();
            row_reserved_car_list[i++] = Car.getFetch(obj.getCar_id()).getType();
            row_reserved_car_list[i++] = Car.getFetch(obj.getCar_id()).getFuel();
            row_reserved_car_list[i++] = Car.getFetch(obj.getCar_id()).getTransmission();
            row_reserved_car_list[i++] = obj.getCheck_in_date();
            row_reserved_car_list[i++] = obj.getCheck_out_date();
            row_reserved_car_list[i++] = obj.getTotal_price();
            mdl_reserved_car_list.addRow(row_reserved_car_list);
        }
    }



}
