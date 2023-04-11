package com.rovaindu.homeservice.model.chat;



import com.rovaindu.homeservice.retrofit.models.ServiesUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class MessageReceipt {
    public static final String RECEIPT_TYPE_DELIVERED = "delivered";
    public static final String RECEIPT_TYPE_READ = "read";
    private int messageId;
    private ServiesUser sender;
    private String receivertype;
    private String receiverId;
    private long timestamp;
    private String receiptType;
    private long deliveredAt;
    private long readAt;

    public MessageReceipt() {
    }

    public int getMessageId() {
        return this.messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public ServiesUser getSender() {
        return this.sender;
    }

    public void setSender(ServiesUser sender) {
        this.sender = sender;
    }

    public String getReceivertype() {
        return this.receivertype;
    }

    public void setReceivertype(String receivertype) {
        this.receivertype = receivertype;
    }

    public String getReceiverId() {
        return this.receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getReceiptType() {
        return this.receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
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


    public String toString() {
        return "MessageReceipt{messageId=" + this.messageId + ", sender=" + this.sender + ", receivertype='" + this.receivertype + '\'' + ", receiverId='" + this.receiverId + '\'' + ", timestamp=" + this.timestamp + ", receiptType='" + this.receiptType + '\'' + ", deliveredAt=" + this.deliveredAt + ", readAt=" + this.readAt + '}';
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ReceiptType {
    }
}
