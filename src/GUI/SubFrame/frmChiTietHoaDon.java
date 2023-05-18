package GUI.SubFrame;

import BLL.HoaDonBLL;
import DTO.HoaDon;
import DTO.KhachHang;

import javax.swing.*;

public class frmChiTietHoaDon  extends  JDialog{
    private JPanel pnlMain;
    private HoaDon hoaDon;
    private KhachHang khachHang;
    public  frmChiTietHoaDon(KhachHang khachHang, HoaDon hoaDon)
    {
        this.khachHang = khachHang;
        this.hoaDon = hoaDon;
        initComponents();
    }
    private void initComponents() {
        this.setContentPane(pnlMain);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(700, 500);
        this.setLocationRelativeTo(null);
        this.setModal(true);
    }
}
