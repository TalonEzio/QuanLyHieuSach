package BLL;

import DAL.KhachHangDAL;
import DTO.KhachHang;
import DTO.TrangThai;

import java.sql.ResultSet;
import java.util.ArrayList;

public class KhachHangBLL {
    private static KhachHangBLL instance;

    public static KhachHangBLL getInstance() {
        if (instance == null) instance = new KhachHangBLL();
        return instance;
    }

    public ArrayList<KhachHang> layDanhSachKhachHang() {
        ArrayList<KhachHang> DSKH = new ArrayList<>();
        ResultSet resultSet = KhachHangDAL.getInstance().layDanhSachKhachHang();
        try {
            while (resultSet.next()) {
                KhachHang khachHang = new KhachHang();
                khachHang.setMaKhachHang(resultSet.getInt(1));
                khachHang.setTenKhachHang(resultSet.getString(2));
                khachHang.setNgaySinh(resultSet.getDate(3));
                khachHang.setDiaChi(resultSet.getString(4));
                khachHang.setSoDienThoai(resultSet.getString(5));
                DSKH.add(khachHang);
            }
        } catch (Exception e) {
            return null;
        }
        return DSKH;
    }

    public TrangThai themKhachHang(KhachHang khachHang) {
        return KhachHangDAL.getInstance().themKhachHang(khachHang);
    }

    public TrangThai suaKhachHang(KhachHang khachHang) {
        return KhachHangDAL.getInstance().suaKhachHang(khachHang);
    }

    public TrangThai xoaKhachHang(int maKhachHang) {
        return KhachHangDAL.getInstance().xoaKhachHang(maKhachHang);
    }

    public ArrayList<KhachHang> timKiemKhachHang(String tenKhachHang) {
        ArrayList<KhachHang> DSKH = new ArrayList<>();
        ResultSet resultSet = KhachHangDAL.getInstance().timKiemKhachHang(tenKhachHang);
        try {
            while (resultSet.next()) {
                KhachHang khachHang = new KhachHang();
                khachHang.setMaKhachHang(resultSet.getInt(1));
                khachHang.setTenKhachHang(resultSet.getString(2));
                khachHang.setNgaySinh(resultSet.getDate(3));
                khachHang.setDiaChi(resultSet.getString(4));
                khachHang.setSoDienThoai(resultSet.getString(5));
                DSKH.add(khachHang);
            }
        } catch (Exception e) {
            return null;
        }

        return DSKH;
    }
}
