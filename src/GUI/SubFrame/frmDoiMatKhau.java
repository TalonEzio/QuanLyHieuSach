package GUI.SubFrame;

import DTO.TaiKhoan;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;

public class frmDoiMatKhau extends  JFrame implements  IGetMainPanel{
    private JPasswordField txtMKHT;
    private JPasswordField txtMKMoi;
    private JPasswordField txtXacNhan;

    public JPasswordField getTxtMKHT() {
        return txtMKHT;
    }

    public JPasswordField getTxtMKMoi() {
        return txtMKMoi;
    }

    public JPasswordField getTxtXacNhan() {
        return txtXacNhan;
    }

    private JButton btnDoiMatKhau;

    public JButton getBtnDoiMatKhau() {
        return btnDoiMatKhau;
    }

    private JPanel pnlMain;
    private JPanel pnlDetail;
    private JLabel lblImage;

    private  TaiKhoan taiKhoan;

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public frmDoiMatKhau(boolean visible) {
        this.setVisible(visible);
        initComponents();
    }

    private void initComponents() {
        this.setContentPane(pnlMain);
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public JPanel getMainPanel() {
        return (JPanel)pnlMain;
    }
    @Override
    public String getName()
    {
        return "frmDoiMatKhau";
    }

}
