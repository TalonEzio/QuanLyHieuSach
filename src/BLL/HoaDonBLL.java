package BLL;

import DAL.HoaDonDAL;
import DAL.KhachHangDAL;
import DTO.HoaDon;
import DTO.KhachHang;
import DTO.TrangThai;

import java.sql.ResultSet;
import java.util.ArrayList;

public class HoaDonBLL {
    private static HoaDonBLL instance;

    public static HoaDonBLL getInstance() {
        if (instance == null) instance = new HoaDonBLL();
        return instance;
    }

    public ArrayList<HoaDon> layDanhSachHoaDon(int maKhachHang) {
        ArrayList<HoaDon> DSHD = new ArrayList<>();
        ResultSet resultSet = HoaDonDAL.getInstance().layDanhSachHoaDon(maKhachHang);
        try {
            while (resultSet.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(resultSet.getInt(1));
                hoaDon.setNgayTao(resultSet.getDate(2));
                hoaDon.setSoLuongSach(resultSet.getInt(3));
                hoaDon.setThanhTien(resultSet.getDouble(4));
                hoaDon.setMoTa(resultSet.getString(5));
                hoaDon.setDaThanhToan(resultSet.getBoolean(6));
                DSHD.add(hoaDon);
            }
        } catch (Exception e) {
            return null;
        }
        return DSHD;
    }
    public TrangThai themHoaDon(int maKhachHang, String moTa) {
        return HoaDonDAL.getInstance().themHoaDon(maKhachHang, moTa);
    }
    public TrangThai suaHoaDon(HoaDon hoaDon) {
        return HoaDonDAL.getInstance().suaHoaDon(hoaDon);
    }
    public TrangThai xoaHoaDon(HoaDon hoaDon) {
        return  HoaDonDAL.getInstance().xoaHoaDon(hoaDon);
    }
    }
