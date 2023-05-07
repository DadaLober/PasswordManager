package PM;

public class PasswordModel {


    private int id;
    private String webname;
    private String appMail;
    private String appPass;
    private String website;
    private String notes;
    private String iconpath;
    private int favorites;

    public PasswordModel(int id, String webname, String appMail, String appPass, String notes, String website, String iconpath, int favorites) {
        this.id = id;
        this.webname = webname;
        this.appMail = appMail;
        this.appPass = appPass;
        this.notes = notes;
        this.website = website;
        this.iconpath = iconpath;
        this.favorites = favorites;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAppName() {
        return webname;
    }
    public void setAppName(String name) {
        this.webname = name;
    }
    public String getAppEmail() {
        return appMail;
    }
    public void setAppMail(String mail) {
        this.appMail = mail;
    }
    public String getPass() {
        return appPass;
    }
    public void setAppPass(String pass) {
        this.appPass = pass;
    }
    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getAppIcon() {
        return iconpath;
    }
    public void setAppIcon(String iconpath) {
        this.iconpath = iconpath;
    }
    public int getFavorites() {
        return favorites;
    }
    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

}