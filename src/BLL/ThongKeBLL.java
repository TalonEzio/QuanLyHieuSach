package BLL;

import DAL.ThongKeDAL;

import java.sql.ResultSet;
import java.util.Date;

public class ThongKeBLL {
    private static ThongKeBLL instance;

    public static ThongKeBLL getInstance() {
        if (instance == null) instance = new ThongKeBLL();
        return instance;
    }

    public ResultSet thongKeTheoNgay(Date ngayBatDau, Date ngayKetThuc) {
        return ThongKeDAL.getInstance().thongKeTheoNgay(ngayBatDau, ngayKetThuc);
    }
    public ResultSet thongKePhanTramSachTheoNXB(int maNXB,int nam)
    {
        return ThongKeDAL.getInstance().thongKePhanTramSachTheoNXB(maNXB,nam);
    }
}
