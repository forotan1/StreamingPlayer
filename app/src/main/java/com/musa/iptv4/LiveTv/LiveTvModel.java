package com.musa.iptv4.LiveTv;


public class LiveTvModel {
    private String id;
    private String title;
    private String liveUrl;
    private String tvicon;
    private String aboutTv;

    public LiveTvModel(String id, String title, String liveUrl, String tvicon, String aboutTv) {

        this.id =id;
        this.title = title;
        this.liveUrl = liveUrl;
        this.tvicon = tvicon;
        this.aboutTv = aboutTv;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLiveUrl() {
        return liveUrl;
    }

    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }


    public String getTvicon() {
        return tvicon;
    }

    public void setTvicon(String tvicon) {
        this.tvicon = tvicon;
    }

    public String getAboutTv() {
        return aboutTv;
    }

    public void setAboutTv(String aboutTv) {
        this.aboutTv = aboutTv;
    }
}
