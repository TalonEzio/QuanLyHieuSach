package DAL;

import DTO.TaiKhoan;
import DTO.TrangThai;
import org.mindrot.jbcrypt.BCrypt;

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

    public TaiKhoan DangNhap(String username, String password) {
        TaiKhoan taiKhoan =null;
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_DangNhap(?)}");

            ResultSet resultSet = DatabaseAccess.getInstance().getData(callableStatement, new Object[]{username});
            if (resultSet.next()) {
                taiKhoan = new TaiKhoan(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                if (BCrypt.checkpw(password, taiKhoan.getHashPassword()))
                {
                    return taiKhoan;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public TrangThai DoiMatKhau(String username, String newPassword) {
        String hashPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(iteratorSalt));
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_DoiMatKhau(?,?)}");
            int rowsAffected = DatabaseAccess.getInstance().getRowsAffected(callableStatement, new Object[]{username, hashPassword});

            if (rowsAffected > 0) return TrangThai.THANH_CONG;

            return TrangThai.THAT_BAI;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
