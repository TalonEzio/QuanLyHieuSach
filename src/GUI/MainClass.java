package GUI;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;

public class MainClass extends frmTrangChu {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch ( UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frmDangNhap dangNhap = new frmDangNhap();
        dangNhap.setVisible(true);
    }
}
