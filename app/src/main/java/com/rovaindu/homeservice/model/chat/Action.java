package com.rovaindu.homeservice.model.chat;


import androidx.annotation.Nullable;

import com.rovaindu.homeservice.model.AppEntity;

public class Action extends BaseMessage {
    private static final String TAG = Action.class.getSimpleName();
    private AppEntity actioBy;
    private AppEntity actionFor;
    private AppEntity actionOn;
    private Message message;
    private String rawData;
    private String action;
    private String oldScope;
    private String newScope;

    public Action() {
    }

    public AppEntity getActioBy() {
        return this.actioBy;
    }

    public void setActioBy(AppEntity actioBy) {
        this.actioBy = actioBy;
    }

    public AppEntity getActionFor() {
        return this.actionFor;
    }

    public void setActionFor(AppEntity actionFor) {
        this.actionFor = actionFor;
    }

    public AppEntity getActionOn() {
        return this.actionOn;
    }

    public void setActionOn(AppEntity actionOn) {
        this.actionOn = actionOn;
    }

    public Message getMessage() {
        return this.message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getRawData() {
        return this.rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getOldScope() {
        return this.oldScope;
    }

    public void setOldScope(String oldScope) {
        this.oldScope = oldScope;
    }

    public String getNewScope() {
        return this.newScope;
    }

    public void setNewScope(String newScope) {
        this.newScope = newScope;
    }


    public int hashCode() {
        return this.id;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof Action)) {
            return false;
        } else {
            Action a = (Action)obj;
            return a.getId() == this.id;
        }
    }

    public String toString() {
        return "Action{actioBy=" + this.actioBy + ", actionFor=" + this.actionFor + ", actionOn=" + this.actionOn + ", message='" + this.message + '\'' + ", rawData='" + this.rawData + '\'' + ", action='" + this.action + '\'' + ", oldScope='" + this.oldScope + '\'' + ", newScope='" + this.newScope + '\'' + ", id=" + this.id + ", muid='" + this.muid + '\'' + ", sender=" + this.sender + ", receiverUid='" + this.receiverUid + '\'' + ", type='" + this.type + '\'' + ", receiverType='" + this.receiverType + '\'' + ", category='" + this.category + '\'' + ", sentAt=" + this.sentAt + ", deliveredAt=" + this.deliveredAt + ", readAt=" + this.readAt  + ", readByMeAt=" + this.readByMeAt + ", deliveredToMeAt=" + this.deliveredToMeAt + ", deletedAt=" + this.deletedAt + ", editedAt=" + this.editedAt + ", deletedBy='" + this.deletedBy + '\'' + ", editedBy='" + this.editedBy + '\'' + ", updatedAt=" + this.updatedAt + '}';
    }
}

