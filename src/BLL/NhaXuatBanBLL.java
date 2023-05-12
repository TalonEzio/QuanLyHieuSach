package BLL;


import DAL.NhaXuatBanDAL;
import DTO.NhaXuatBan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NhaXuatBanBLL {
    private static NhaXuatBanBLL instance;

    public static NhaXuatBanBLL getInstance() {
        if (instance == null) instance = new NhaXuatBanBLL();
        return instance;
    }

    public ArrayList<NhaXuatBan> layNXB() {
        ArrayList<NhaXuatBan> DSNXB = new ArrayList<NhaXuatBan>();
        ResultSet rs = NhaXuatBanDAL.getInstance().layNXB();
        try {
            while (rs.next()) {
                DSNXB.add(
                    new NhaXuatBan
                    (
                        rs.getInt(1),
                        rs.getString(2)
                    )
                );
            }
            return DSNXB;
        } catch (SQLException ex) {
            return null;
        }
    }
}
