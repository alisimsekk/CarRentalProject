package com.View;

import com.Helper.Config;
import com.Helper.Helper;
import com.Model.Customer;
import com.Model.Company;
import com.Model.User;

import javax.swing.*;

public class LoginGUI extends JFrame {
    private JPanel wrapper;
    private JPanel wbottom;
    private JPanel wtop;
    private JTextField fld_user_email;
    private JPasswordField fld_user_pass;
    private JButton btn_login;
    private JButton btn_signup;

    public LoginGUI(){
        add(wrapper);
        setSize(500,400);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

//login ekranı giriş yap butonu metodu
        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_email) || Helper.isFieldEmpty(fld_user_pass)){
                Helper.showMsg("fill");
            }
            else{
                User u = User.getFetch(fld_user_email.getText(), fld_user_pass.getText());
                if (u==null){
                    Helper.showMsg("Kullanıcı bulunamadı. Kullanıcı adı veya şifre hatalı.");
                }
                else{
                    switch (u.getType()){
                        case "company":
                            CompanyGUI compGUI = new CompanyGUI((Company) u);
                            break;
                        case "customer":
                            CustomerGUI custmGUI = new CustomerGUI((Customer) u);
                            break;
                    }
                    dispose();
                }
            }
        });

//kayıt ol butonu kodları
        btn_signup.addActionListener(e -> {
            SignUpGUI signGUI = new SignUpGUI();
        });

    }

    public static void main(String[] args){
        Helper.setLayout();
        LoginGUI login = new LoginGUI();
    }
}
