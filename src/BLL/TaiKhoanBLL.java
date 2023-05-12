package BLL;

import DAL.TaiKhoanDAL;
import DTO.TaiKhoan;
import DTO.TrangThai;

public class TaiKhoanBLL {
    private static TaiKhoanBLL instance;

    public static TaiKhoanBLL getInstance() {
        if (instance == null) instance = new TaiKhoanBLL();
        return instance;
    }

    public TrangThai DangKy(String username, String password) {
        return TaiKhoanDAL.getInstance().DangKy(username, password);
    }

    public TaiKhoan DangNhap(String username, String password) {
        return TaiKhoanDAL.getInstance().DangNhap(username, password);
    }

    public TrangThai DoiMatKhau(String username, String newPassword) {
        return TaiKhoanDAL.getInstance().DoiMatKhau(username, newPassword);
    }

    public TrangThai XoaTaiKhoan(String username) {
        return TaiKhoanDAL.getInstance().XoaTaiKhoan(username);
    }
}