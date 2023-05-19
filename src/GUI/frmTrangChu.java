package GUI;

import DTO.TaiKhoan;
import GUI.Custom.ChangePasswordListener;
import GUI.Custom.DeleteAccountListener;
import GUI.SubFrame.*;

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
    private JButton btnQLKH;
    private JButton btnDMK;
    private JButton btnThoat;
    private JPanel pnlMenu;
    private JPanel pnlDetail;
    private JButton btnTrangChu;
    private final TaiKhoan taiKhoan;
    private frmDangNhap dangNhap;

    public frmDangNhap getDangNhap() {
        return dangNhap;
    }

    public void setDangNhap(frmDangNhap dangNhap) {
        this.dangNhap = dangNhap;
    }

    Hashtable<String, JFrame> subFrame = new Hashtable<>();

    public frmTrangChu(JFrame loginFrame, TaiKhoan taiKhoan) {
        this.setTitle("Trang chủ");
        this.taiKhoan = taiKhoan;
        initComponents();

        setDangNhap((frmDangNhap) loginFrame);
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
                    WindowEvent event = new WindowEvent(window, WindowEvent.WINDOW_CLOSING);
                    window.dispatchEvent(event);
                }
            }
        });
        btnDMK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScreen("frmThongTinTaiKhoan");
                frmTrangChu.this.setTitle("Thông tin tài khoản");
            }
        });
        btnTrangChu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScreen("frmWelcome");
                frmTrangChu.this.setTitle("Trang chủ");

            }
        });
        btnQLS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScreen("frmQuanLySach");
                frmTrangChu.this.setTitle("Quản lý sách");
            }
        });
        btnQLKH.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScreen("frmKhachHang");
                frmTrangChu.this.setTitle("Quản lý khách hàng");
            }
        });
    }
    void initComponents() {
        this.setContentPane(pnlMain);
        this.setResizable(false);

        loadSubFrame(pnlDetail);
        setMargin(pnlMenu);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setMinimumSize(new Dimension(1200,600));
        this.setLocationRelativeTo(null);
    }

    private void loadSubFrame(JPanel pnlDetail) {
        addSubFrame(pnlDetail, new frmThongTinTaiKhoan(false));
        addSubFrame(pnlDetail,new frmQuanLySach());
        addSubFrame(pnlDetail,new frmKhachHang());
        //add last, show first :>
        addSubFrame(pnlDetail, new frmWelcome(false));

    }

    private void addSubFrame(JPanel pnlDetail, JFrame frame) {

        String name = ((IGetMainPanel) frame).getName();
        if (subFrame.get(name) != null) {
            showScreen(name);
        } else {
            subFrame.put(name, frame);

            if (frame instanceof frmThongTinTaiKhoan) {

                frmThongTinTaiKhoan thongTinTaiKhoan =(frmThongTinTaiKhoan)frame;
                JButton btnDoiMatKhau = ((frmThongTinTaiKhoan) frame).getBtnDoiMatKhau();
                JPasswordField txtMKHT =((frmThongTinTaiKhoan) frame).getTxtMKHT();
                JPasswordField txtMKMoi=((frmThongTinTaiKhoan) frame).getTxtMKMoi();
                JPasswordField txtXacNhan = ((frmThongTinTaiKhoan) frame).getTxtXacNhan();
                //Handle Change Password
                btnDoiMatKhau.addActionListener(new ChangePasswordListener(taiKhoan,txtMKHT,txtMKMoi,txtXacNhan,frmTrangChu.this,thongTinTaiKhoan));
                //Handle Delete Account
                JButton btnXoaTaiKhoan = thongTinTaiKhoan.getBtnXoaTaiKhoan();
                btnXoaTaiKhoan.addActionListener(new DeleteAccountListener(taiKhoan,frmTrangChu.this,thongTinTaiKhoan));
            }

            JPanel pnlContent = ((IGetMainPanel) frame).getMainPanel();
            pnlDetail.add(pnlContent, name);
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
            if (component instanceof JButton button) {
                button.setMargin(new Insets(10, 10, 10, 10));
            }
        }
    }

}
