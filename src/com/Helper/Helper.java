package com.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static int screenCenterPoint(String axis, Dimension size){
        int point;
        switch (axis) {
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
                break;
            default:
                point = 0;
        }
        return point;
    }

    public static void setLayout(){
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if ("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static void showMsg(String str){
        optionPaneTR();
        String msg;
        String title;
        switch (str){
            case "fill":
                msg = "Lütfen tüm alanları doldurunuz!";
                title = "Hata";
                break;
            case "done":
                msg = "İşlem başarılı!";
                title = "Sonuç";
                break;
            case "error":
                msg = "Bir hata oluştu!";
                title = "Hata";

            default:
                msg = str;
                title = "Mesaj";
        }
        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }

    public static void optionPaneTR(){
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");
    }

    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }
    public static boolean isAreaEmpty(JTextArea area){
        return area.getText().trim().isEmpty();
    }

// user search için dinamik query oluşturma
    public static String searchQuery (String name, String uname, String type) {
        String query = "SELECT * FROM user WHERE name LIKE '%{{name}}%' AND uname LIKE '%{{uname}}%' AND type LIKE '%{{type}}%'";
        query = query.replace("{{name}}", name);
        query = query.replace("{{uname}}", uname);
        query = query.replace("{{type}}",type);

        return query;
    }

//silme işlemlerinde emin misin ekranına ait metod
    public static boolean confirm(String str) {
        optionPaneTR();
        String msg;
        switch (str){
            case "sure":
                msg = "Silme işlemi yapılacak, emin misiniz?";
                break;
            default:
                msg = str;
        }
        return JOptionPane.showConfirmDialog(null,msg,"Son Kararın Mı?",JOptionPane.YES_NO_OPTION) ==0;
    }

}
