package com.aem.demo.core.models;

public class DownloadPdfRequest {
    private String path;
    private String[] tags;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
