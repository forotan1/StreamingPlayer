package com.musa.iptv4.Iptv;

public class iModel {

    private long id;
    private String iTitle;
    private String iUrl;
    private String iAbout;
    private String image;

    public iModel() {
    }

    public iModel(String iTitle, String iUrl, String iAbout, String image) {
        this.iTitle = iTitle;
        this.iUrl = iUrl;
        this.iAbout = iAbout;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getiTitle() {
        return iTitle;
    }

    public void setiTitle(String iTitle) {
        this.iTitle = iTitle;
    }

    public String getiUrl() {
        return iUrl;
    }

    public void setiUrl(String iUrl) {
        this.iUrl = iUrl;
    }

    public String getiAbout() {
        return iAbout;
    }

    public void setiAbout(String iAbout) {
        this.iAbout = iAbout;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
