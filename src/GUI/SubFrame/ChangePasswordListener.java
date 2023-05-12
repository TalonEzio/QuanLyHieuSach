package GUI.SubFrame;

import BLL.TaiKhoanBLL;
import DTO.TaiKhoan;
import DTO.TrangThai;
import GUI.frmTrangChu;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePasswordListener implements ActionListener {
    private TaiKhoan taiKhoan;
    JPasswordField txtMKHT, txtMKMoi, txtMKXN;
    private frmTrangChu trangChu;
    private frmThongTinTaiKhoan doiMatKhau;
    @Override
    public void actionPerformed(ActionEvent e) {
        if(validateBeforeChange(taiKhoan) == false)return;
        TrangThai trangThai = TaiKhoanBLL.getInstance().DoiMatKhau(taiKhoan.getUsername(),txtMKMoi.getText());
        if (trangThai == TrangThai.THANH_CONG) {
            JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công, vui lòng đăng nhập lại!", "Trạng thái", JOptionPane.INFORMATION_MESSAGE);
            doiMatKhau.dispose();
            trangChu.dispose();
            trangChu.getDangNhap().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Đổi mật khẩu thất bại, vui lòng thử lại!", "Trạng thái", JOptionPane.ERROR_MESSAGE);
        }
    }
    boolean validateBeforeChange(TaiKhoan taiKhoan) {

        String matKhauHienTai = txtMKHT.getText();
        String matKhauMoi= txtMKMoi.getText();
        String matKhauXacNhan = txtMKXN.getText();
        if(BCrypt.checkpw(matKhauHienTai,taiKhoan.getHashPassword()) == false)
        {
            JOptionPane.showMessageDialog(null,"Mật khẩu hiện tại không chính xác!","Thông báo",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (matKhauMoi.length() < 6) {
            JOptionPane.showMessageDialog(null, "Mật khẩu mới phải dài hơn 6 ký tự!", "Trạng thái", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (matKhauXacNhan.length() < 6) {
            JOptionPane.showMessageDialog(null, "Mật khẩu xác nhận phải dài hơn 6 ký tự!", "Trạng thái", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (matKhauMoi.indexOf(' ') >= 0) {
            JOptionPane.showMessageDialog(null, "Mật khẩu mới không được chứa khoảng trằng!", "Trạng thái", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (matKhauXacNhan.indexOf(' ') >= 0) {
            JOptionPane.showMessageDialog(null, "Mật khẩu xác nhận không được chứa khoảng trằng!", "Trạng thái", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(matKhauMoi.equals(matKhauXacNhan) == false)
        {
            JOptionPane.showMessageDialog(null, "Mật khẩu xác nhận không hợp lệ!", "Trạng thái", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    public ChangePasswordListener(TaiKhoan taiKhoan, JPasswordField txtMKHT, JPasswordField txtMKMoi, JPasswordField txtMKXN, frmTrangChu trangChu, frmThongTinTaiKhoan doiMatKhau)
    {
        this.taiKhoan = taiKhoan;
        this.txtMKHT = txtMKHT;
        this.txtMKMoi= txtMKMoi;
        this.txtMKXN = txtMKXN;
        this.trangChu = trangChu;
        this.doiMatKhau = doiMatKhau;

    }
}
