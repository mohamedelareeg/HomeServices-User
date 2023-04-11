package com.rovaindu.homeservice.model.chat;



import java.io.Serializable;
import java.util.Map;

/**
 * Created by Mohamed El Sayed
 */
public class Conversation implements Serializable {

    //public static final String TABLE_NAME = "conversations";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FROM_NAME = "from_name";
    public static final String COLUMN_FROM_ID = "from_id";
    public static final String COLUMN_MESSAGE= "message";
    public static final String COLUMN_THUMB_IMAGE= "thumb_image";
    public static final String COLUMN_TIME_STAMP= "time_stamp";
    public static final String COLUMN_SEEN= "seen";
    public static final String COLUMN_CHANNEL_TYPE= "channelType";
    public static String CREATE_TABLE(String TABLE_NAME)
    {
        return "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FROM_ID +" TEXT ,"
                + COLUMN_FROM_NAME +" TEXT ,"
                + COLUMN_MESSAGE +" TEXT ,"
                + COLUMN_THUMB_IMAGE +" TEXT ,"
                + COLUMN_CHANNEL_TYPE +" TEXT,"
                + COLUMN_TIME_STAMP +" INTEGER,"
                + COLUMN_SEEN +" VARCHAR"
                + ")";
    }
    public static String CREATE_TABLE_EXIST(String TABLE_NAME)
    {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FROM_ID +" TEXT ,"
                + COLUMN_FROM_NAME +" TEXT ,"
                + COLUMN_MESSAGE +" TEXT ,"
                + COLUMN_THUMB_IMAGE +" TEXT ,"
                + COLUMN_CHANNEL_TYPE +" TEXT,"
                + COLUMN_TIME_STAMP +" INTEGER,"
                + COLUMN_SEEN +" VARCHAR"
                + ")";
    }


    private int id;
    public String user_id;
    private String from_name, message;
    private long time_stamp;
    private boolean seen;
    private String from_id;
    private String channelType;
    private Map<String, Object> extras;
    private String thumb_image;
    public Conversation() {
    }

    public Conversation(String from_id, String from_name, String message , String thumb_image, String channelType, long time_stamp , boolean seen) {
        this.from_id = from_id;
        this.from_name = from_name;
        this.message = message;
        this.thumb_image = thumb_image;
        this.channelType = channelType;
        this.time_stamp = time_stamp;
        this.seen = seen;

    }
    public Conversation(int id  , String from_id, String from_name, String message , String thumb_image, String channelType, long time_stamp , boolean seen) {
        this.id = id;
        this.from_id = from_id;
        this.from_name = from_name;
        this.message = message;
        this.thumb_image = thumb_image;
        this.channelType = channelType;
        this.time_stamp = time_stamp;
        this.seen = seen;

    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
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

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Map<String, Object> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
    }

}
