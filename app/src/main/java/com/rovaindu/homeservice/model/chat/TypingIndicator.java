package com.rovaindu.homeservice.model.chat;


import androidx.annotation.NonNull;

import com.rovaindu.homeservice.retrofit.models.ServiesUser;

import org.json.JSONObject;

public class TypingIndicator {
    private ServiesUser sender;
    private int receiverId;
    private String receiverType;
    private JSONObject metadata;
    private long lastTimestamp;

    public TypingIndicator(@NonNull int receiverId, String receiverType) {
        this.receiverId = receiverId;
        this.receiverType = receiverType;
    }

    public TypingIndicator(@NonNull int receiverId, String receiverType, @NonNull JSONObject metadata) {
        this.receiverId = receiverId;
        this.receiverType = receiverType;
        this.metadata = metadata;
    }

    public ServiesUser getSender() {
        return this.sender;
    }

    public void setSender(ServiesUser sender) {
        this.sender = sender;
    }

    public long getLastTimestamp() {
        return this.lastTimestamp;
    }

    public void setLastTimestamp(long lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public int getReceiverId() {
        return this.receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverType() {
        return this.receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public JSONObject getMetadata() {
        return this.metadata;
    }

    public void setMetadata(JSONObject metadata) {
        this.metadata = metadata;
    }

    public String toString() {
        return "TypingIndicator{sender=" + this.sender + ", receiverId='" + this.receiverId + '\'' + ", receiverType='" + this.receiverType + '\'' + ", metadata=" + this.metadata + '}';
    }
}
