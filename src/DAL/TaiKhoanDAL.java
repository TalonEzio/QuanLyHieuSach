package DAL;

import DTO.TaiKhoan;
import DTO.TrangThai;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoanDAL {
    private static TaiKhoanDAL instance;
    private final int iteratorSalt = 11;

    public static TaiKhoanDAL getInstance() {
        if (instance == null) instance = new TaiKhoanDAL();
        return instance;
    }

    public TrangThai DangKy(String username, String password) {
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt(iteratorSalt));
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_KiemTraTonTai(?)}");
            ResultSet resultSet = DatabaseAccess.getInstance().getData(callableStatement, new Object[]{username});
            if (resultSet.next()) {
                return TrangThai.DA_TON_TAI;
            }
            callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_DangKy(?,?)}");
            int rowsAffected = DatabaseAccess.getInstance().getRowsAffected(callableStatement, new Object[]{username, hashPassword});

            if (rowsAffected > 0) return TrangThai.THANH_CONG;

            return TrangThai.THAT_BAI;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TrangThai DangNhap(String username, String password) {
        TrangThai trangThai = TrangThai.THAT_BAI;
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_DangNhap(?)}");

            ResultSet resultSet = DatabaseAccess.getInstance().getData(callableStatement, new Object[]{username});
            if (resultSet.next()) {
                TaiKhoan taiKhoan = new TaiKhoan(resultSet.getString(1), resultSet.getString(2));
                if (BCrypt.checkpw(password, taiKhoan.getHashPassword())) ;
                {
                    trangThai =  TrangThai.THANH_CONG;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trangThai;
    }

}
