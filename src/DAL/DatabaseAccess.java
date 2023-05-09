package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.ResultSet;

public class DatabaseAccess {
    private static DatabaseAccess instance;
    public static DatabaseAccess getInstance()
    {
        if(instance == null) instance = new DatabaseAccess();
        return instance;
    }
    private  Connection connection  = createConnection();
    public Connection createConnection() {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("TalonEzio\\SqlExpress");//sửa vào
        ds.setDatabaseName("QuanLyNguoiDung");//sửa vào
        ds.setPortNumber(1433);
        ds.setUser("sa");//sửa vào
        ds.setPassword("manhngu123");//sửa vào
        ds.setEncrypt("false");
        try {
            Connection conn = ds.getConnection();
            System.out.println("Kết nối thành công");
            return conn;
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        }
    }
}
