package GUI;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;

public class Program {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch ( UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frmDangNhap dangNhap = new frmDangNhap();
        dangNhap.setVisible(true);
    }
}
