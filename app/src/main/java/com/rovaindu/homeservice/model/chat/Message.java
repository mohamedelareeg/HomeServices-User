package com.rovaindu.homeservice.model.chat;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Message extends BaseMessage {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FROM_NAME = "from_name";
    public static final String COLUMN_MESSAGE= "message";
    public static final String COLUMN_MEDIA_PATH= "media_path";
    //public static final String COLUMN_MEDIA_COLLECTION= "media_collection";
    public static final String COLUMN_MEDIA_URL= "media_url";
    public static final String COLUMN_TIME_STAMP= "time_stamp";
    public static final String COLUMN_SEEN= "seen";
    public static final String COLUMN_POSITION= "position";
    public static String CREATE_TABLE(String TABLE_NAME)
    {
        return
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_FROM_NAME +" TEXT ,"
                        + COLUMN_MESSAGE +" TEXT ,"
                        + COLUMN_MEDIA_URL +" BLOB,"
                        + COLUMN_POSITION +" TEXT,"
                        + COLUMN_MEDIA_PATH +" TEXT,"
                        + COLUMN_TIME_STAMP +" INTEGER,"
                        + COLUMN_SEEN +" VARCHAR"
                        + ")";
    }
    public static String CREATE_TABLE_EXIST(String TABLE_NAME)
    {
        return
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_FROM_NAME +" TEXT ,"
                        + COLUMN_MESSAGE +" TEXT ,"
                        + COLUMN_MEDIA_URL +" BLOB,"
                        + COLUMN_POSITION +" TEXT,"
                        + COLUMN_MEDIA_PATH +" TEXT,"
                        + COLUMN_TIME_STAMP +" INTEGER,"
                        + COLUMN_SEEN +" VARCHAR"
                        + ")";
    }


    private String from_name, messagetxt;
    private byte[] media_url;
    private String media_path;
    private HashMap<String , String> media_collection;
    private long time_stamp;
    private boolean seen;
    private String position;
    private String from;
    private List<Message> messages;

    private String text;
    private String subType;
    private JSONObject customData;
    private File file;
    private String caption;
    private Attachment attachment;

    public Message(@NonNull int receiverUid, @NonNull String text, String receiverType) {
        super(receiverUid, "text", receiverType);
        this.text = text;
    }
    public Message(int receiverUid, File file, String messageType, String receiverType , Attachment attachment) {
        super(receiverUid, messageType, receiverType );
        this.attachment = attachment;
        this.file = file;
    }
    public Message(int receiverUid, String receiverType, String customType, @NonNull JSONObject customData) {
        super(receiverUid, customType, receiverType);
        this.setCategory("custom");
        this.customData = customData;
    }
    private Message() {
    }
    //TODO
    public Message(int conversationId, String participantName,
                   List<Message> messages) {
        this.id = conversationId;
        this.from_name = participantName;
        this.messages = messages == null ? Collections.<Message>emptyList() : messages;
        this.time_stamp = System.currentTimeMillis();
    }

    public Message(int id , String from_name, String messagetxt, String position, String media_path , long time_stamp , boolean seen) {
        this.id = id;
        this.from_name = from_name;
        this.messagetxt = messagetxt;
        this.position = position;
        this.media_path=media_path;
        this.time_stamp = time_stamp;
        this.seen = seen;

    }
    public Message(int id , String from_name, String messagetxt , byte[] media_url, String position, String media_path , long time_stamp , boolean seen) {
        this.id = id;
        this.from_name = from_name;
        this.messagetxt = messagetxt;
        this.media_url = media_url;
        this.position = position;
        this.media_path=media_path;
        this.time_stamp = time_stamp;
        this.seen = seen;
    }
    public Message(String from_name, String messagetxt, String position, String media_path , long time_stamp , boolean seen) {
        this.from_name = from_name;
        this.messagetxt = messagetxt;
        this.position = position;
        this.media_path=media_path;
        this.time_stamp = time_stamp;
        this.seen = seen;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getMessagetxt() {
        return messagetxt;
    }

    public void setMessagetxt(String messagetxt) {
        this.messagetxt = messagetxt;
    }

    public byte[] getMedia_url() {
        return media_url;
    }

    public void setMedia_url(byte[] media_url) {
        this.media_url = media_url;
    }

    public String getMedia_path() {
        return media_path;
    }

    public void setMedia_path(String media_path) {
        this.media_path = media_path;
    }

    public HashMap<String, String> getMedia_collection() {
        return media_collection;
    }

    public void setMedia_collection(HashMap<String, String> media_collection) {
        this.media_collection = media_collection;
    }

    public long getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    //

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public JSONObject getCustomData() {
        return customData;
    }

    public void setCustomData(JSONObject customData) {
        this.customData = customData;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public String toString() {
        return "TextMessage{text='" + this.text + '\'' + ", id=" + this.id + ", muid='" + this.muid + '\'' + ", sender=" + this.sender + ", receiverUid='" + this.receiverUid + '\'' + ", type='" + this.type + '\'' + ", receiverType='" + this.receiverType + '\'' + ", category='" + this.category + '\'' + ", sentAt=" + this.sentAt + ", deliveredAt=" + this.deliveredAt + ", readAt=" + this.readAt  + ", readByMeAt=" + this.readByMeAt + ", deliveredToMeAt=" + this.deliveredToMeAt + ", deletedAt=" + this.deletedAt + ", editedAt=" + this.editedAt + ", deletedBy='" + this.deletedBy + '\'' + ", editedBy='" + this.editedBy + '\'' + ", updatedAt=" + this.updatedAt + '}';
    }

    public int hashCode() {
        return this.id;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof Message)) {
            return false;
        } else {
            Message a = (Message)obj;
            return a.getId() == this.id;
        }
    }
}

