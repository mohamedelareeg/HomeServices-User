package com.rovaindu.homeservice.model.chat;


import androidx.annotation.NonNull;

import com.rovaindu.homeservice.model.AppEntity;


public class Call extends BaseMessage {
    private String sessionId;
    private String callStatus;
    private String action;
    private String rawData;
    private long initiatedAt;
    private long joinedAt;
    private AppEntity callInitiator;
    private AppEntity callReceiver;

    public Call(@NonNull int receiverId, String receiverType, String callType) {
        this.receiverUid = receiverId;
        this.receiverType = receiverType;
        this.type = callType;
    }

    private Call() {
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCallStatus() {
        return this.callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRawData() {
        return this.rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public long getInitiatedAt() {
        return this.initiatedAt;
    }

    public void setInitiatedAt(long initiatedAt) {
        this.initiatedAt = initiatedAt;
    }

    public AppEntity getCallInitiator() {
        return this.callInitiator;
    }

    public void setCallInitiator(AppEntity callInitiator) {
        this.callInitiator = callInitiator;
    }

    public AppEntity getCallReceiver() {
        return this.callReceiver;
    }

    public void setCallReceiver(AppEntity callReceiver) {
        this.callReceiver = callReceiver;
    }

    public long getJoinedAt() {
        return this.joinedAt;
    }

    public void setJoinedAt(long joinedAt) {
        this.joinedAt = joinedAt;
    }



    public String toString() {
        return "Call{sessionId='" + this.sessionId + '\'' + ", callStatus='" + this.callStatus + '\'' + ", action='" + this.action + '\'' + ", rawData='" + this.rawData + '\'' + ", initiatedAt=" + this.initiatedAt + ", joinedAt=" + this.joinedAt + ", callInitiator=" + this.callInitiator + ", callReceiver=" + this.callReceiver + ", id=" + this.id + ", muid='" + this.muid + '\'' + ", sender=" + this.sender + ", receiverUid='" + this.receiverUid + '\'' + ", type='" + this.type + '\'' + ", receiverType='" + this.receiverType + '\'' + ", category='" + this.category + '\'' + ", sentAt=" + this.sentAt + ", deliveredAt=" + this.deliveredAt + ", readAt=" + this.readAt  + ", readByMeAt=" + this.readByMeAt + ", deliveredToMeAt=" + this.deliveredToMeAt + ", deletedAt=" + this.deletedAt + ", editedAt=" + this.editedAt + ", deletedBy='" + this.deletedBy + '\'' + ", editedBy='" + this.editedBy + '\'' + ", updatedAt=" + this.updatedAt + '}';
    }
}
