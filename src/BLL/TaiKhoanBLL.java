package BLL;

import DAL.TaiKhoanDAL;
import DTO.TrangThai;

public class TaiKhoanBLL {
    private  static TaiKhoanBLL instance;

    public static TaiKhoanBLL getInstance() {
        if(instance == null) instance = new TaiKhoanBLL();
        return  instance;
    }
    public TrangThai DangKy(String username, String password)
    {
        return TaiKhoanDAL.getInstance().DangKy(username,password);
    }
    public TrangThai DangNhap(String username, String password)
    {
        return TaiKhoanDAL.getInstance().DangNhap(username,password);
    }
}
