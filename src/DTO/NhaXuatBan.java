package DTO;

public class NhaXuatBan {
    private int maNXB;
    private String tenNXB;

    public int getMaNXB() {
        return maNXB;
    }

    public NhaXuatBan(int maNXB, String tenNXB) {
        this.maNXB = maNXB;
        this.tenNXB = tenNXB;
    }

    public void setMaNXB(int maNXB) {
        this.maNXB = maNXB;
    }

    public String getTenNXB() {
        return tenNXB;
    }

    public void setTenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
    }

    @Override
    public String toString() {
        return getTenNXB();
    }
}
