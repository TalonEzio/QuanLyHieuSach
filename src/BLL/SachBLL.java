package BLL;

import DAL.SachDAL;
import DTO.Sach;
import DTO.TrangThai;
import Resources.ResourceClass;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SachBLL {
    private static SachBLL instance;
    private final int iteratorSalt = 11;

    public static SachBLL getInstance() {
        if (instance == null) instance = new SachBLL();
        return instance;
    }

    public ArrayList<Sach> laySach(int maNXB) {
        ResultSet resultSet = SachDAL.getInstance().laySach(maNXB);
        return getListFromResultSet(resultSet);

    }
    public  ArrayList<Sach> timKiemSach(int maNXB,String input)
    {
        ResultSet resultSet = SachDAL.getInstance().timKiemSach(maNXB,input);
        return getListFromResultSet(resultSet);
    }
    public TrangThai themSachMoi(Sach sach)
    {
        if(sach.getAnh() == null)sach.setAnh(getDefaultImage());
        return SachDAL.getInstance().themSachMoi(sach);
    }
    public TrangThai capNhatSach(Sach sach)
    {
        if(sach.getAnh() == null)sach.setAnh(getDefaultImage());
        return SachDAL.getInstance().capNhatSach(sach);
    }
    public TrangThai xoaSach(Sach sach)
    {
        return SachDAL.getInstance().xoaSach(sach);
    }
    ArrayList<Sach> getListFromResultSet(ResultSet resultSet)
    {
        ArrayList<Sach> dsSach = new ArrayList<Sach>();

        ImageIcon defaultImage = getDefaultImage();
        try {
            while (resultSet.next()) {
                Sach sach = new Sach();
                sach.setMaSach(resultSet.getInt(1));
                sach.setTenSach(resultSet.getString(2));
                sach.setTacGia(resultSet.getString(3));
                sach.setMaNXB(resultSet.getInt(4));
                sach.setSoTrang(resultSet.getInt(5));
                byte[] imageData = resultSet.getBytes(6);

                if (imageData == null) {
                    sach.setAnh(defaultImage);
                }
                else
                {
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
                    BufferedImage bufferedImage = ImageIO.read(inputStream);
                    int width = bufferedImage.getWidth();
                    int height = bufferedImage.getHeight();
                    if (width > 160 || height > 200) {
                        Image scaledImage = bufferedImage.getScaledInstance(160, 200, Image.SCALE_SMOOTH);
                        sach.setAnh(new ImageIcon(scaledImage));
                    } else {
                        sach.setAnh(new ImageIcon(bufferedImage));
                    }
                }

                dsSach.add(sach);
            }
            resultSet.close();
            return dsSach;
        } catch (SQLException | IOException e) {
            return null;
        }
    }
    public Sach laySachTheoMa(int maSach)
    {
        ResultSet resultSet = SachDAL.getInstance().laySachTheoMa(maSach);
        ImageIcon defaultImage = getDefaultImage();
        Sach sach = null;
        try {
            while (resultSet.next()) {
                sach = new Sach();
                sach.setMaSach(resultSet.getInt(1));
                sach.setTenSach(resultSet.getString(2));
                sach.setTacGia(resultSet.getString(3));
                sach.setMaNXB(resultSet.getInt(4));
                sach.setSoTrang(resultSet.getInt(5));
                byte[] imageData = resultSet.getBytes(6);

                if (imageData == null) {
                    sach.setAnh(defaultImage);
                }
                else
                {
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
                    BufferedImage bufferedImage = ImageIO.read(inputStream);
                    int width = bufferedImage.getWidth();
                    int height = bufferedImage.getHeight();
                    if (width > 160 || height > 200) {
                        Image scaledImage = bufferedImage.getScaledInstance(160, 200, Image.SCALE_SMOOTH);
                        sach.setAnh(new ImageIcon(scaledImage));
                    } else {
                        sach.setAnh(new ImageIcon(bufferedImage));
                    }
                }

            }
            resultSet.close();
            return sach;
        } catch (SQLException | IOException e) {
            return null;
        }
    }
    private ImageIcon getDefaultImage()
    {
        URL imageURL = ResourceClass.class.getResource("SachmacDinh.jpg");
        ImageIcon defaultImage = new ImageIcon(imageURL);
        Image newImg = defaultImage.getImage();
        newImg = newImg.getScaledInstance(160,200,Image.SCALE_SMOOTH);
        defaultImage = new ImageIcon(newImg);
        return defaultImage;
    }
}
