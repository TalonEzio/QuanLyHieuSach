package BLL;

import DAL.ChiTietHoaDonDAL;
import DTO.ChiTietHoaDon;
import DTO.TrangThai;
import GUI.Custom.ChiTietHoaDonModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietHoaDonBLL {
    private  static ChiTietHoaDonBLL instance;
    public static ChiTietHoaDonBLL getInstance()
    {
        if (instance == null) instance = new ChiTietHoaDonBLL();
        return  instance;
    }

    public ArrayList<ChiTietHoaDon> layChiTietHoaDon(int maKhachHang, int maHoaDon)
    {
        ArrayList<ChiTietHoaDon> list = new ArrayList<>();
        ResultSet rs = ChiTietHoaDonDAL.getInstance().layChiTietHoaDon(maKhachHang,maHoaDon);

        while (true) {
            try {
                if (!rs.next()) break;
                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                chiTietHoaDon.setMaSach(rs.getInt(1));
                chiTietHoaDon.setTenSach(rs.getString(2));
                chiTietHoaDon.setSoLuong(rs.getInt(3));
                chiTietHoaDon.setDonGia(rs.getDouble(4));
                list.add(chiTietHoaDon);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }
    public TrangThai themSachVaoHoaDon(ChiTietHoaDon cthd, int maHoaDon)
    {
        return ChiTietHoaDonDAL.getInstance().themSachVaoHoaDon(cthd,maHoaDon);
    }
    public TrangThai capNhatChiTietHoaDon(ChiTietHoaDon cthd, int maHoaDon)
    {
        return ChiTietHoaDonDAL.getInstance().capNhatChiTietHoaDon(cthd,maHoaDon);
    }
    public TrangThai xoaSachTrongHoaDon(int maHoaDon, int maSach) {
        return ChiTietHoaDonDAL.getInstance().xoaSachTrongHoaDon(maHoaDon,maSach);
    }
    }
