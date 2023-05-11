package GUI;

import GUI.SubFrame.IGetMainPanel;
import GUI.SubFrame.frmDoiMatKhau;
import GUI.SubFrame.frmWelcome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

public class frmTrangChu extends JFrame {

    private JPanel pnlMain;
    private JButton btnQLS;
    private JButton btnQLBH;
    private JButton btnQLNV;
    private JButton btnQLHD;
    private JButton btnDMK;
    private JButton btnThoat;
    private JPanel pnlMenu;
    private JPanel pnlDetail;
    private JButton btnTrangChu;

    Hashtable<String,JPanel> subFrame = new Hashtable<>();
    public frmTrangChu(JFrame loginFrame) {
        this.setTitle("Trang chủ");
        initComponents();
        addEvents(this, loginFrame);
    }

    private void addEvents(JFrame mainFrame, JFrame loginFrame) {
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                loginFrame.setVisible(true);
            }
        });
        btnThoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window window = SwingUtilities.getWindowAncestor(btnThoat);
                if (window instanceof JFrame) {
                    WindowEvent event = new WindowEvent((JFrame) window, WindowEvent.WINDOW_CLOSING);
                    ((JFrame) window).dispatchEvent(event);
                }
            }
        });
        btnDMK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    showScreen("frmDoiMatKhau");
            }
        });
        btnTrangChu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScreen("frmWelcome");
            }
        });
    }

    void initComponents() {
        this.setContentPane(pnlMain);

        ChangeLookAndFeel();
        loadSubFrame(pnlDetail);
        setMargin(pnlMenu);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(1200, 600);
        this.setLocationRelativeTo(null);
    }

    private void loadSubFrame(JPanel pnlDetail) {
        AddSubFrame(pnlDetail,new frmDoiMatKhau(false));
        //add last, show first :>
        AddSubFrame(pnlDetail,new frmWelcome(false));

    }

    private void AddSubFrame(JPanel pnlDetail,JFrame frame) {
        JPanel pnlContent =  ((IGetMainPanel)frame).getMainPanel();
        String name = ((IGetMainPanel)frame).getName();

        if(subFrame.get(name) != null)
        {
            showScreen(name);
        }
        else
        {
            pnlDetail.add(pnlContent, name);
            subFrame.put(name,pnlContent);
            showScreen(name);
        }
    }
    public void showScreen(String name) {
        CardLayout cardLayout = (CardLayout) pnlDetail.getLayout();
        cardLayout.show(pnlDetail, name);
    }

    private void setMargin(JPanel panel) {

        Component[] listComponent = panel.getComponents();
        for (Component component : listComponent) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setMargin(new Insets(10,10,10,10));
            }
        }
    }
    private void ChangeLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

}
