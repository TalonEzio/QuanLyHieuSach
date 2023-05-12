package DTO;

import javax.swing.*;
import java.awt.*;

public class Sach {
    private  int maSach;
    private String tenSach;
    private String tacGia;
    private ImageIcon anh;
    private int soTrang;

    private int maNXB;

    public ImageIcon getAnh() {
        return anh;
    }

    public void setAnh(ImageIcon anh) {
        this.anh = anh;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return  tenSach;
    }


    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public int getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(int maNXB) {
        this.maNXB = maNXB;
    }

}
