package com.aem.demo.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class)
public class PdfDetails {
    @Inject
    @Named("jcr:content/metadata/dc:title")
    @Optional
    private String fileName;
    private String filePath;
    @Inject
    @Named("jcr:content/metadata/dam:size")
    private Long fileSize;
    @Inject
    @Named("jcr:content/metadata/cq:tags")
    @Optional
    private List<String> tags;

    private String downloadUrl;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize / 1024;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public List<String> getTags() {
        List<String> pdfTags = new ArrayList<>();
        for (String tag : tags) {
            pdfTags.add(tag.split(":")[1]);
        }
        return pdfTags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
