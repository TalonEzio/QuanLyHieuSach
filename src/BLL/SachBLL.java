package BLL;

import DAL.SachDAL;
import DTO.Sach;

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
        ArrayList<Sach> dsSach = new ArrayList<Sach>();
        ResultSet resultSet = SachDAL.getInstance().laySach(maNXB);

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
                    sach.setAnh(new ImageIcon(bufferedImage));
                }

                dsSach.add(sach);
            }

            resultSet.close();
            return dsSach;
        } catch (SQLException | IOException e) {
            return null;
        }
    }

    private ImageIcon getDefaultImage() {
        URL imageURL = SachBLL.class.getResource("SachmacDinh.png");
        ImageIcon defaultImage = new ImageIcon(imageURL);
        Image newImg = defaultImage.getImage();
        newImg = newImg.getScaledInstance(160,160,Image.SCALE_SMOOTH);
        defaultImage = new ImageIcon(newImg);
        return defaultImage;
    }

}
