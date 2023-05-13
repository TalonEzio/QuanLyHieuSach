package GUI.Custom;

import BLL.TaiKhoanBLL;
import DTO.TaiKhoan;
import DTO.TrangThai;
import GUI.SubFrame.frmThongTinTaiKhoan;
import GUI.frmTrangChu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteAccountListener implements ActionListener {
    TaiKhoan taiKhoan;
    frmTrangChu trangChu;
    frmThongTinTaiKhoan thongTinTaiKhoan;
    public DeleteAccountListener(TaiKhoan taiKhoan, frmTrangChu trangChu, frmThongTinTaiKhoan thongTinTaiKhoan) {
        this.taiKhoan = taiKhoan;
        this.trangChu = trangChu;
        this.thongTinTaiKhoan = thongTinTaiKhoan;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int result= JOptionPane.showConfirmDialog(null,"Bạn có muốn xóa tài khoản không?","Xóa tài khoản",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.NO_OPTION) return;
        TrangThai trangThai = TaiKhoanBLL.getInstance().XoaTaiKhoan(taiKhoan.getUsername());
        if (trangThai == TrangThai.THANH_CONG) {
            JOptionPane.showMessageDialog(null, "Xóa tài khoản thành công, vui lòng đăng nhập lại!", "Trạng thái", JOptionPane.INFORMATION_MESSAGE);
            thongTinTaiKhoan.dispose();
            trangChu.dispose();
            trangChu.getDangNhap().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Xóa tài khoản thất bại, vui lòng thử lại!", "Trạng thái", JOptionPane.ERROR_MESSAGE);
        }
    }
}
