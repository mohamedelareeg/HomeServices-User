package com.rovaindu.homeservice.model.chat;


import androidx.annotation.Nullable;

import com.rovaindu.homeservice.model.AppEntity;
import com.rovaindu.homeservice.retrofit.models.ServiesUser;

import java.io.Serializable;

public class BaseMessage extends AppEntity implements Serializable {
    public static final String TABLE_CONVERSATIONS = "Conversations";
    protected int id;
    protected String muid;
    protected ServiesUser sender;
    protected int receiverUid;
    protected String type;
    protected String receiverType;
    protected String category;
    protected long sentAt;
    protected long deliveredAt;
    protected long readAt;
    protected long readByMeAt;
    protected long deliveredToMeAt;
    protected long deletedAt;
    protected long editedAt;
    protected String deletedBy;
    protected String editedBy;
    protected long updatedAt;
    protected String conversationId;
    protected int parentMessageId;
    protected Message message;

    public BaseMessage(int receiverUid, String type, String receiverType) {
        this.receiverUid = receiverUid;
        this.type = type;
        this.receiverType = receiverType;
    }

    public BaseMessage() {
    }

    public BaseMessage(int id, String muid, ServiesUser sender, int receiverUid, String type, String receiverType, String category, long sentAt, long deliveredAt, long readAt, long readByMeAt, long deliveredToMeAt, long deletedAt, long editedAt, String deletedBy, String editedBy, long updatedAt, String conversationId, int parentMessageId , Message message) {
        this.id = id;
        this.muid = muid;
        this.sender = sender;

        this.receiverUid = receiverUid;
        this.type = type;
        this.receiverType = receiverType;
        this.category = category;
        this.sentAt = sentAt;
        this.deliveredAt = deliveredAt;
        this.readAt = readAt;

        this.readByMeAt = readByMeAt;
        this.deliveredToMeAt = deliveredToMeAt;
        this.deletedAt = deletedAt;
        this.editedAt = editedAt;
        this.deletedBy = deletedBy;
        this.editedBy = editedBy;
        this.updatedAt = updatedAt;
        this.conversationId = conversationId;
        this.parentMessageId = parentMessageId;

        this.message = message;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMuid() {
        return this.muid;
    }

    public void setMuid(String muid) {
        this.muid = muid;
    }

    public ServiesUser getSender() {
        return this.sender;
    }

    public void setSender(ServiesUser sender) {
        this.sender = sender;
    }

    public int getReceiverUid() {
        return this.receiverUid;
    }

    public void setReceiverUid(int receiverUid) {
        this.receiverUid = receiverUid;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReceiverType() {
        return this.receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public long getSentAt() {
        return this.sentAt;
    }

    public void setSentAt(long sentAt) {
        this.sentAt = sentAt;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getDeliveredAt() {
        return this.deliveredAt;
    }

    public void setDeliveredAt(long deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public long getReadAt() {
        return this.readAt;
    }

    public void setReadAt(long readAt) {
        this.readAt = readAt;
    }



    public long getReadByMeAt() {
        return this.readByMeAt;
    }

    public void setReadByMeAt(long readByMeAt) {
        this.readByMeAt = readByMeAt;
    }

    public long getDeliveredToMeAt() {
        return this.deliveredToMeAt;
    }

    public void setDeliveredToMeAt(long deliveredToMeAt) {
        this.deliveredToMeAt = deliveredToMeAt;
    }

    public long getDeletedAt() {
        return this.deletedAt;
    }

    public void setDeletedAt(long deletedAt) {
        this.deletedAt = deletedAt;
    }

    public long getEditedAt() {
        return this.editedAt;
    }

    public void setEditedAt(long editedAt) {
        this.editedAt = editedAt;
    }

    public String getDeletedBy() {
        return this.deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getEditedBy() {
        return this.editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public long getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getConversationId() {
        return this.conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }



    public int getParentMessageId() {
        return this.parentMessageId;
    }

    public void setParentMessageId(int parentMessageId) {
        this.parentMessageId = parentMessageId;
    }



    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String toString() {
        return "BaseMessage{id=" + this.id + ", muid='" + this.muid + '\'' + ", sender=" + this.sender  + ", receiverUid='" + this.receiverUid + '\'' + ", type='" + this.type + '\'' + ", receiverType='" + this.receiverType + '\'' + ", category='" + this.category + '\'' + ", sentAt=" + this.sentAt + ", deliveredAt=" + this.deliveredAt + ", readAt=" + this.readAt  + ", readByMeAt=" + this.readByMeAt + ", deliveredToMeAt=" + this.deliveredToMeAt + ", deletedAt=" + this.deletedAt + ", editedAt=" + this.editedAt + ", deletedBy='" + this.deletedBy + '\'' + ", editedBy='" + this.editedBy + '\'' + ", updatedAt=" + this.updatedAt + ", conversationId='" + this.conversationId + '\'' + ", parentMessageId=" + this.parentMessageId + '}';
    }

    public int hashCode() {
        return this.id;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof BaseMessage)) {
            return false;
        } else {
            BaseMessage a = (BaseMessage)obj;
            return a.getId() == this.id;
        }
    }
}
