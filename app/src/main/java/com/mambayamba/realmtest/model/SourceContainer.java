package com.mambayamba.realmtest.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by макс on 19.07.2017.
 */

public class SourceContainer{
    private String status;
    private List<Source> sources;

    public SourceContainer(String status, List<Source> sources) {
        this.status = status;
        this.sources = sources;
    }

    public SourceContainer() {
    }

    public String getStatus() {
        return status;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}
