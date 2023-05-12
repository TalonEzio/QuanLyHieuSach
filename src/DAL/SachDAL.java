package DAL;

import DTO.TrangThai;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SachDAL {
    private static SachDAL instance;

    public static SachDAL getInstance() {
        if (instance == null) instance = new SachDAL();
        return instance;
    }
    public ResultSet laySach(int maNXB)
    {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_LaySach(?)}");
            return DatabaseAccess.getInstance().getData(callableStatement, new Object[]{maNXB});
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
