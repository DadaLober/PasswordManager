package PM;

import javafx.beans.property. SimpleStringProperty;

public class PasswordModel {

    private SimpleStringProperty appName;
    private SimpleStringProperty appMail;
    private SimpleStringProperty appIcon;
    private SimpleStringProperty website;
    private SimpleStringProperty notes;

    public PasswordModel(String appName, String appMail, String appIcon, String website, String notes){
        this.appName = new SimpleStringProperty(appName);
        this.appMail = new SimpleStringProperty(appMail);
        this.appIcon = new SimpleStringProperty(appIcon);
        this.website = new SimpleStringProperty(website);
        this.notes = new SimpleStringProperty(notes);
    }

    public String getAppName() {
        return appName.get();
    }
    public String getAppEmail() {
        return appMail.get();
    }
    public String getAppIcon() {
        return appIcon.get();
    }
    public String getWebsite() {
        return website.get();
    }
    public String getNotes() {
        return notes.get();
    }
}
