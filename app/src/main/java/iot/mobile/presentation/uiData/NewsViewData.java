package iot.mobile.presentation.uiData;

public class NewsViewData {
    private String name;
    private String login;
    private String gender;

    private String photoUrl;

    public NewsViewData(String name, String login, String gender, String photoUrl) {
        this.name = name;
        this.login = login;
        this.gender = gender;
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
