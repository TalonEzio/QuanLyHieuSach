package DTO;

public class TaiKhoan {
    private int taiKhoanId;
    private String username;
    private String hashPassword;
    private  int getTaiKhoanId()
    {
       return  taiKhoanId;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public TaiKhoan(int taiKhoanId, String username, String hashPassword) {
        this.taiKhoanId = taiKhoanId;
        this.username = username;
        this.hashPassword = hashPassword;
    }

}
