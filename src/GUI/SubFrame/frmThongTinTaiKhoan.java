package GUI.SubFrame;

import DTO.TaiKhoan;

import javax.swing.*;
import java.awt.*;

public class frmThongTinTaiKhoan extends  JFrame implements  IGetMainPanel{
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
    private JLabel lblImage;
    private JButton btnXoaTaiKhoan;

    public JButton getBtnXoaTaiKhoan() {
        return btnXoaTaiKhoan;
    }
    private  TaiKhoan taiKhoan;

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public frmThongTinTaiKhoan(boolean visible) {
        this.setVisible(visible);
        initComponents();
    }

    private void initComponents() {
        this.setContentPane(pnlMain);
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMargin(pnlMain);
    }
    private void setMargin(JPanel panel) {

        Component[] listComponent = panel.getComponents();
        for (Component component : listComponent) {
            if (component instanceof JButton button) {
                button.setMargin(new Insets(10, 10, 10, 10));
            }
        }
    }

    @Override
    public JPanel getMainPanel() {
        return (JPanel)pnlMain;
    }
    @Override
    public String getName()
    {
        return "frmThongTinTaiKhoan";
    }

}
