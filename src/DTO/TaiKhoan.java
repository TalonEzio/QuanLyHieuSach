package DTO;

public class TaiKhoan {
    private  String username;
    private  String hashPassword;

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

    public TaiKhoan(String username, String hashPassword) {
        this.username = username;
        this.hashPassword = hashPassword;
    }

}
