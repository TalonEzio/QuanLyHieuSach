package DAL;

import DTO.HoaDon;
import DTO.KhachHang;
import DTO.TrangThai;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HoaDonDAL {
    private static HoaDonDAL instance;
    public static  HoaDonDAL getInstance()
    {
        if(instance == null)instance = new HoaDonDAL();
        return instance;
    }
    public ResultSet layDanhSachHoaDon(int maKhachHang) {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_LayDanhSachHoaDon(?)}");
            ResultSet rs = DatabaseAccess.getInstance().getData(callableStatement, new Object[]{maKhachHang});
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public TrangThai themHoaDon(int maKhachHang,String moTa) {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_ThemHoaDon(?,?)}");
            int result = DatabaseAccess.getInstance().getRowsAffected(callableStatement,
                    new Object[]{maKhachHang,moTa});
            if (result == 0) return TrangThai.THAT_BAI;
            return TrangThai.THANH_CONG;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public TrangThai suaHoaDon(HoaDon hoaDon) {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_SuaHoaDon(?,?,?,?)}");
            int result = DatabaseAccess.getInstance().getRowsAffected(callableStatement,
                    new Object[]{hoaDon.getNgayTao(),hoaDon.getMoTa(),hoaDon.isDaThanhToan(),hoaDon.getMaHoaDon()});
            if (result == 0) return TrangThai.THAT_BAI;
            return TrangThai.THANH_CONG;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public TrangThai xoaHoaDon(HoaDon hoaDon) {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_XoaHoaDon(?)}");
            int result = DatabaseAccess.getInstance().getRowsAffected(callableStatement,
                    new Object[]{hoaDon.getMaHoaDon()});
            if (result == 0) return TrangThai.THAT_BAI;
            return TrangThai.THANH_CONG;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
