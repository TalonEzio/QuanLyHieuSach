package DAL;

import DTO.KhachHang;
import DTO.TrangThai;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KhachHangDAL {
    private static KhachHangDAL instance;

    public static KhachHangDAL getInstance() {
        if (instance == null) instance = new KhachHangDAL();
        return instance;
    }

    public ResultSet layDanhSachKhachHang() {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_LayKhachHang}");
            ResultSet rs = DatabaseAccess.getInstance().getData(callableStatement);
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TrangThai themKhachHang(KhachHang khachHang) {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_ThemKhachHang(?,?,?,?)}");
            int result = DatabaseAccess.getInstance().getRowsAffected(callableStatement,
                    new Object[]{
                            khachHang.getTenKhachHang(),
                            khachHang.getNgaySinh(),
                            khachHang.getDiaChi(),
                            khachHang.getSoDienThoai()
                    });
            if (result == 0) return TrangThai.THAT_BAI;
            return TrangThai.THANH_CONG;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TrangThai suaKhachHang(KhachHang khachHang) {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_SuaKhachHang(?,?,?,?,?)}");
            int result = DatabaseAccess.getInstance().getRowsAffected(callableStatement,
                    new Object[]{
                            khachHang.getMaKhachHang(),
                            khachHang.getTenKhachHang(),
                            khachHang.getNgaySinh(),
                            khachHang.getDiaChi(),
                            khachHang.getSoDienThoai()
                    });
            if (result == 0) return TrangThai.THAT_BAI;
            return TrangThai.THANH_CONG;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TrangThai xoaKhachHang(int maKhachHang) {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_XoaKhachHang(?)}");
            int result = DatabaseAccess.getInstance().getRowsAffected(callableStatement,
                    new Object[]{maKhachHang
                    });
            if (result == 0) return TrangThai.THAT_BAI;
            return TrangThai.THANH_CONG;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet timKiemKhachHang(String tenKhachHang) {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_TimKiemKhachHang(?)}");
            ResultSet rs = DatabaseAccess.getInstance().getData(callableStatement,new Object[]{tenKhachHang});
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
