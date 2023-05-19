package DAL;

import DTO.ChiTietHoaDon;
import DTO.TrangThai;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChiTietHoaDonDAL {
    private static ChiTietHoaDonDAL instance;

    public static ChiTietHoaDonDAL getInstance() {
        if (instance == null) instance = new ChiTietHoaDonDAL();
        return instance;
    }

    public ResultSet layChiTietHoaDon(int maKhachHang, int maHoaDon) {
        CallableStatement callableStatement = null;
        try {
            callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_LayChiTietHoaDon(?,?)}");
            ResultSet rs = DatabaseAccess.getInstance().getData(callableStatement, new Object[]{maKhachHang, maHoaDon});
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TrangThai themSachVaoHoaDon(ChiTietHoaDon cthd, int maHoaDon) {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_ThemSachVaoHoaDon(?,?,?,?)}");
            int result = DatabaseAccess.getInstance().getRowsAffected(callableStatement,
                    new Object[]{cthd.getMaSach(), maHoaDon, cthd.getSoLuong(), cthd.getDonGia()});
            if (result == 0) return TrangThai.THAT_BAI;
            return TrangThai.THANH_CONG;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TrangThai capNhatChiTietHoaDon(ChiTietHoaDon cthd, int maHoaDon) {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_CapNhatChiTietHoaDon(?,?,?,?)}");
            int result = DatabaseAccess.getInstance().getRowsAffected(callableStatement,
                    new Object[]{maHoaDon, cthd.getMaSach(), cthd.getSoLuong(), cthd.getDonGia()});
            if (result == 0) return TrangThai.THAT_BAI;
            return TrangThai.THANH_CONG;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TrangThai xoaSachTrongHoaDon(int maHoaDon, int maSach) {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_XoaSachTrongHoaDon(?,?)}");
            int result = DatabaseAccess.getInstance().getRowsAffected(callableStatement,
                    new Object[]{maHoaDon, maSach});
            if (result == 0) return TrangThai.THAT_BAI;
            return TrangThai.THANH_CONG;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
