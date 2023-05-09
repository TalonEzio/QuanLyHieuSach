package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseAccess {
    private static DatabaseAccess instance;

    public static DatabaseAccess getInstance() {
        if (instance == null) instance = new DatabaseAccess();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private final Connection connection = createConnection();

    private Connection createConnection() {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("TalonEzio\\SqlExpress");
        ds.setDatabaseName("QuanLyHieuSach");
        ds.setPortNumber(1433);
        ds.setUser("sa");
        ds.setPassword("manhngu123");
        ds.setEncrypt("false");
        try {
            Connection conn = ds.getConnection();
            return conn;
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getData(CallableStatement cstmt, Object[] parameterList) throws SQLException {
        ResultSet resultSet = null;
        int parameterCount;
        if (parameterList == null) parameterCount = 0;
        else {
            parameterCount = parameterList.length;
        }
        try {
            for (int i = 0; i < parameterCount; i++) {
                cstmt.setObject(i + 1, parameterList[i]);
            }
            resultSet = cstmt.executeQuery();
        } catch (SQLException ex) {
            throw ex;
        }
        return resultSet;
    }

    public ResultSet getData(CallableStatement cstmt) throws SQLException {
        return this.getData(cstmt, null);
    }

    public int getRowsAffected(CallableStatement cstmt, Object[] parameterList) throws SQLException {
        int result = 0;
        int parameterCount;
        if (parameterList == null) parameterCount = 0;
        else {
            parameterCount = parameterList.length;
        }
        try {
            for (int i = 0; i < parameterCount; i++) {
                cstmt.setObject(i + 1, parameterList[i]);
            }
            result = cstmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }

    public int getRowsAffected(CallableStatement cstmt) throws SQLException
    {
        return getRowsAffected(cstmt,null);
    }

}
