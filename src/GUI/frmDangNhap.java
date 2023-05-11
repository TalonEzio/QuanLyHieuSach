package GUI;

import BLL.TaiKhoanBLL;
import DAL.TaiKhoanDAL;
import DTO.TaiKhoan;
import DTO.TrangThai;

import javax.swing.*;
import java.awt.event.*;

public class frmDangNhap extends JFrame {
    private JPanel pnlLeft;
    private JPanel pnlRight;
    private JPanel pnlMain;
    private JTextField txtUsername;
    private JButton btnDangNhap;
    private JPasswordField txtPassword;
    private JButton btnDangKy;

    public frmDangNhap() {
        this.setTitle("Đăng nhập");

        String password = "password";

        initComponents();
        addEvents();
    }

    private void initComponents() {
        ChangeLookAndFeel();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setContentPane(pnlMain);
        this.setSize(900, 450);
        this.setLocationRelativeTo(null);
        this.getRootPane().setDefaultButton(btnDangNhap);
    }
    private void ChangeLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
    private void addEvents() {
        btnDangNhap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ValidateBefore() == false) return;

                TaiKhoan taiKhoan = TaiKhoanBLL.getInstance().DangNhap(txtUsername.getText(), txtPassword.getText());
                if (taiKhoan != null) {
                    JOptionPane.showMessageDialog(null, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    new frmTrangChu(frmDangNhap.this,taiKhoan).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Đăng nhập thất bại, vui lòng thử lại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frmDangNhap.this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát không?", "Thoát chương trình", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    frmDangNhap.this.dispose();
                }
            }
        });


        btnDangKy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ValidateBefore() == false) return;
                TrangThai trangThai = TaiKhoanBLL.getInstance().DangKy(txtUsername.getText(), txtPassword.getText());
                if (trangThai == TrangThai.THANH_CONG) {
                    JOptionPane.showMessageDialog(null, "Đăng ký thành công!, vui lòng đăng nhập!", "Trạng thái", JOptionPane.INFORMATION_MESSAGE);
                } else if (trangThai == TrangThai.DA_TON_TAI) {
                    JOptionPane.showMessageDialog(null, "Tài khoản đã tồn tại, vui lòng thử lại!", "Trạng thái", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Đăng ký thất bại, vui lòng thử lại!", "Trạng thái", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean ValidateBefore() {
        if (txtUsername.getText().length() < 6) {
            JOptionPane.showMessageDialog(null, "Tài khoản phải dài hơn 6 ký tự", "Trạng thái", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtPassword.getText().length() < 6) {
            JOptionPane.showMessageDialog(null, "Mật khẩu phải dài hơn 6 ký tự", "Trạng thái", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtUsername.getText().indexOf(' ') >= 0) {
            JOptionPane.showMessageDialog(null, "Tài khoản không được chứa khoảng trằng", "Trạng thái", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtPassword.getText().indexOf(' ') >= 0) {
            JOptionPane.showMessageDialog(null, "Mật khẩu không được chứa khoảng trằng", "Trạng thái", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
