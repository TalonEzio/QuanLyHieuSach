package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;

public class DatabaseAccess {
    private static DatabaseAccess instance;

    public static DatabaseAccess getInstance() {
        if (instance == null)
        {
            instance = new DatabaseAccess();
            sqlServerDataSource = getSqlServerDataSource();
            connection = createConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private static Connection connection;
    private static SQLServerDataSource sqlServerDataSource;

    private static SQLServerDataSource getSqlServerDataSource() {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("TalonEzio\\SqlExpress");
        ds.setDatabaseName("QuanLyHieuSach");
        ds.setPortNumber(1433);
        ds.setUser("sa");
        ds.setPassword("manhngu123");
        ds.setEncrypt("false");
        return ds;
    }

    private static Connection createConnection() {
        try {
            connection = sqlServerDataSource.getConnection();
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        }
        return connection;
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
            return resultSet;


        } catch (SQLException ex) {
            throw ex;
        }
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
            return result;
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public int getRowsAffected(CallableStatement cstmt) throws SQLException {
        return getRowsAffected(cstmt, null);
    }

}
