package com.rovaindu.homeservice.model.chat;


import androidx.annotation.Nullable;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;

public class Attachment {
    private String fileName;
    private String fileExtension;
    private int fileSize;
    private String fileMimeType;
    private String fileUrl;

    public Attachment() {
    }

    public Attachment(String fileName, String fileExtension, int fileSize, String fileMimeType, String fileUrl) {
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.fileSize = fileSize;
        this.fileMimeType = fileMimeType;
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public int getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileMimeType() {
        return this.fileMimeType;
    }

    public void setFileMimeType(String fileMimeType) {
        this.fileMimeType = fileMimeType;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }



    public int hashCode() {
        return Objects.hash(new Object[]{this.fileName, this.fileSize, this.fileExtension, this.fileMimeType, this.fileSize, this.fileUrl});
    }

    public boolean equals(@Nullable Object obj) {
        Attachment a = (Attachment)obj;
        return a.getFileName().equalsIgnoreCase(this.getFileName()) && a.getFileExtension().equalsIgnoreCase(this.getFileExtension()) && a.getFileMimeType().equalsIgnoreCase(this.getFileMimeType()) && a.getFileSize() == this.getFileSize() && a.getFileUrl().equalsIgnoreCase(this.getFileUrl());
    }

    public String toString() {
        return "Attachment{fileName='" + this.fileName + '\'' + ", fileExtension='" + this.fileExtension + '\'' + ", fileSize=" + this.fileSize + ", fileMimeType='" + this.fileMimeType + '\'' + ", fileUrl='" + this.fileUrl + '\'' + '}';
    }
}
