package DTO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HoaDon {
    private int maHoaDon;
    private Date ngayTao;

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    private  int soLuongSach;

    public  boolean daThanhToan;

    public boolean isDaThanhToan() {
        return daThanhToan;
    }

    public void setDaThanhToan(boolean daThanhToan) {
        this.daThanhToan = daThanhToan;
    }
    private double thanhTien;
    public  String moTa;

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    public int getDaThanhToan()
    {
        if(daThanhToan)return 1;
        return 0;
    }
    public String getDaThanhToanTable()
    {
        if(daThanhToan)return "Đã thanh toán";
        return "Chưa thanh toán";
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getSoLuongSach() {
        return soLuongSach;
    }

    public void setSoLuongSach(int soLuongSach) {
        this.soLuongSach = soLuongSach;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
    public String getNgayTaoTable()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(ngayTao);
    }
}
