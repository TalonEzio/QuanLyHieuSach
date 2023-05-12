package DAL;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NhaXuatBanDAL {
    private static NhaXuatBanDAL instance;

    public static NhaXuatBanDAL getInstance() {
        if (instance == null) instance = new NhaXuatBanDAL();
        return instance;
    }
    public ResultSet layNXB()
    {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_LayNXB}");
            return DatabaseAccess.getInstance().getData(callableStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
