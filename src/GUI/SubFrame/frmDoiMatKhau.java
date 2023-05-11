package GUI.SubFrame;

import javax.swing.*;
import java.awt.*;

public class frmDoiMatKhau extends  JFrame implements  IGetMainPanel{
    private JPasswordField txtMKHT;
    private JPasswordField txtMKMoi;
    private JPasswordField txtXacNhan;
    private JButton btnDoiMatKhau;
    private JPanel pnlMain;
    private JPanel pnlDetail;
    private JLabel lblImage;

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
        return pnlMain;
    }
    @Override
    public String getName()
    {
        return "frmDoiMatKhau";
    }
}
