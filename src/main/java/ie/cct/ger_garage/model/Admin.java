package ie.cct.ger_garage.model;

public class Admin {

    private int idAdmin;
    private String adminLogin;
    private String adminPw;

    public Admin(int idAdmin, String adminLogin, String adminPw) {
        super();
        this.idAdmin = idAdmin;
        this.adminLogin = adminLogin;
        this.adminPw = adminPw;
    }

    public Admin() {
        super();
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getAdminLogin() {
        return adminLogin;
    }

    public void setAdminLogin(String adminLogin) {
        this.adminLogin = adminLogin;
    }

    public String getAdminPw() {
        return adminPw;
    }

    public void setAdminPw(String adminPw) {
        this.adminPw = adminPw;
    }

    @Override
    public String toString() {
        return "Admin [idAdmin=" + idAdmin + ", adminLogin=" + adminLogin + ", adminPw=" + adminPw + "]";
    }


}
