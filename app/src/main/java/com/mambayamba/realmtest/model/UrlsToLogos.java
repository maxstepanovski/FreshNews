package com.mambayamba.realmtest.model;

import io.realm.RealmObject;

/**
 * Created by макс on 19.07.2017.
 */

public class UrlsToLogos extends RealmObject{
    private String small;
    private String medium;
    private String large;

    public UrlsToLogos(String small, String medium, String large) {
        this.small = small;
        this.medium = medium;
        this.large = large;
    }

    public UrlsToLogos() {
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getSmall() {
        return small;
    }

    public String getMedium() {
        return medium;
    }

    public String getLarge() {
        return large;
    }
}
