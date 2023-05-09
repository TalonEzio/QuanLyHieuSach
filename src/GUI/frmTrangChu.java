package GUI;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class frmTrangChu extends  JFrame {

    private JPanel pnlMain;

    public frmTrangChu(JFrame loginFrame)
    {
        this.setTitle("Trang chủ");
        initComponents();
        addEvents(this,loginFrame);
    }

    private void addEvents(JFrame mainFrame,JFrame loginFrame) {
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                loginFrame.setVisible(true);
            }
        });
    }

    void initComponents() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(pnlMain);
        this.setSize(780,430);
        this.setLocationRelativeTo(null);
    }
}
