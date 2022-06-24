package com.View;

import com.Helper.Config;
import com.Helper.Helper;
import com.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_sign_form;
    private JTextField fld_sign_name;
    private JTextField fld_sign_email;
    private JTextField fld_sign_pass;
    private JButton btn_sign;
    private JButton btn_back;
    private JComboBox cmb_type;
    private JTextField fld_phone;
    private JTextField fld_city;

    public SignUpGUI() {
        add(wrapper);
        setSize(400, 600);
        setLocation(Helper.screenCenterPoint("x" , getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);




        btn_sign.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_sign_name) || Helper.isFieldEmpty(fld_sign_email) || Helper.isFieldEmpty(fld_sign_pass)
            || cmb_type.getSelectedItem() == null || Helper.isFieldEmpty(fld_phone) ||Helper.isFieldEmpty(fld_city) ){
                Helper.showMsg("fill");
            }
            else{
                String name = fld_sign_name.getText();
                String email = fld_sign_email.getText();
                String pass = fld_sign_pass.getText();
                String type = cmb_type.getSelectedItem().toString();
                String phone = fld_phone.getText();
                String city = fld_city.getText();
                if (User.add(name, pass, email, phone, type, city)){
                    Helper.showMsg("done");
                    fld_sign_name.setText(null);
                    fld_sign_pass.setText(null);
                    fld_sign_email.setText(null);
                    fld_phone.setText(null);
                    cmb_type.setSelectedIndex(0);
                    fld_city.setText(null);
                }
            }
        });

        btn_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


    }
}
