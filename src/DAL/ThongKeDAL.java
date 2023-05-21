package DAL;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ThongKeDAL {
    private static ThongKeDAL instance;
    public  static ThongKeDAL getInstance()
    {
        if(instance == null) instance = new ThongKeDAL();
        return instance;
    }
    public ResultSet thongKeTheoNgay(Date ngayBatDau, Date ngayKetThuc)
    {
        try
        {
            CallableStatement cstmt = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_ThongKeTheoNgay(?,?)}");
            ResultSet rs = DatabaseAccess.getInstance().getData(cstmt,new Object[]{ngayBatDau,ngayKetThuc});
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet thongKePhanTramSachTheoNXB(int maNXB,int nam)
    {
        try
        {
            CallableStatement cstmt = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_ThongKePhanTramSachTheoNXB(?,?)}");
            ResultSet rs = DatabaseAccess.getInstance().getData(cstmt,new Object[]{maNXB,nam});
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
