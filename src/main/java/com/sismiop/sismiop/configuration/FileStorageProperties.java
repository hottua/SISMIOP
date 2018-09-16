package com.sismiop.sismiop.configuration;


public class FileStorageProperties {
    private String uploadDir = "./uploads/";

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir += uploadDir;
    }
}
