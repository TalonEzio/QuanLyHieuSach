package DAL;

import DTO.Sach;
import DTO.TrangThai;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    public ResultSet timKiemSach(int maNXB,String input)
    {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_TimKiemSach(?,?)}");
            return DatabaseAccess.getInstance().getData(callableStatement, new Object[]{maNXB,input});
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public TrangThai themSachMoi(Sach sach)
    {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_ThemSachMoi(?,?,?,?,?)}");
            int result = DatabaseAccess.getInstance().getRowsAffected(
                    callableStatement,
                    new Object[] {sach.getTenSach(),sach.getTacGia(),sach.getMaNXB(),sach.getSoTrang(),getInputImage(sach.getAnh().getImage())});
            if(result == 0)return TrangThai.THAT_BAI;
            return TrangThai.THANH_CONG;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public TrangThai capNhatSach(Sach sach)
    {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_CapNhatSach(?,?,?,?,?,?)}");
            int result = DatabaseAccess.getInstance().getRowsAffected(
                    callableStatement,
                    new Object[] {sach.getTenSach(),sach.getTacGia(),sach.getMaNXB(),sach.getSoTrang(),getInputImage(sach.getAnh().getImage()),sach.getMaSach()});
            if(result == 0)return TrangThai.THAT_BAI;
            return TrangThai.THANH_CONG;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TrangThai xoaSach(Sach sach)
    {
        try {
            CallableStatement callableStatement = DatabaseAccess.getInstance().getConnection().prepareCall("{call usp_XoaSach(?)}");
            int result = DatabaseAccess.getInstance().getRowsAffected(
                    callableStatement,
                    new Object[] {sach.getMaSach()});
            if(result == 0)return TrangThai.THAT_BAI;
            return TrangThai.THANH_CONG;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    byte[] getInputImage(Image image)
    {
                BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            return imageBytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
