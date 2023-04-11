package com.rovaindu.homeservice.manager.chat_DB;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.rovaindu.homeservice.model.chat.Conversation;
import com.rovaindu.homeservice.model.chat.Message;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed El Sayed
 */
public class DB_SqLite_Chat extends SQLiteOpenHelper {
    // Database Name
    public static final String DB_name = "msgstore.db";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    public DB_SqLite_Chat(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DB_SqLite_Chat(Context context) {
        super(context, DB_name, null, DATABASE_VERSION);
    }
    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.beginTransaction();
        if (sql.trim().length() > 0) {
            database.execSQL(sql);
        }
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
    }
    // Create table SQL query
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FROM_NAME +" TEXT ,"
                + COLUMN_MESSAGE +" TEXT ,"
                + COLUMN_ITSELF +" VARCHAR,"
                + COLUMN_COLOR +" VARCHAR"
                + ")"
        );
        */
        //sqLiteDatabase.execSQL(Message.CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Message.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    //======================== Conversation ========================
    public long insertDataConv(String TABLE_NAME , String from_name , String from_id , String message , String thumb_image , String channelType , long time_stamp , boolean seen , Boolean check )
    {
        //
        SQLiteDatabase db_check = this.getReadableDatabase();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor res = db_check.rawQuery("select DISTINCT "+ Conversation.COLUMN_FROM_ID+" from " + TABLE_NAME + " where "+ Conversation.COLUMN_FROM_ID +" = '"+from_id+"' " , null);//* = All
        if(res!=null) {
            if(res.getCount() > 0) {
                res.close();

            }
            else
            {

                contentValues.put(Conversation.COLUMN_FROM_ID , from_id);
                contentValues.put(Conversation.COLUMN_FROM_NAME , from_name);
                contentValues.put(Conversation.COLUMN_MESSAGE , message);
                contentValues.put(Conversation.COLUMN_THUMB_IMAGE , thumb_image);
                contentValues.put(Conversation.COLUMN_CHANNEL_TYPE , channelType);
                contentValues.put(Conversation.COLUMN_TIME_STAMP , time_stamp);
                contentValues.put(Conversation.COLUMN_SEEN , seen);
                return db.insert( TABLE_NAME , null , contentValues);
            }
            res.close();
        }
        //
        return db.insert( TABLE_NAME , null , contentValues);
    }
    public List<Conversation> getAllrecordConv(String TABLE_NAME)
    {
        List<Conversation> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " ORDER BY "+Message.COLUMN_TIME_STAMP+" DESC LIMIT 10" , null);//* = All

        if (res.moveToFirst()) {

            while (!res.isAfterLast()) {
                Conversation message = new Conversation(
                        res.getInt(res.getColumnIndex(Conversation.COLUMN_ID)),
                        res.getString(res.getColumnIndex(Conversation.COLUMN_FROM_NAME)),
                        res.getString(res.getColumnIndex(Conversation.COLUMN_FROM_ID)),
                        res.getString(res.getColumnIndex(Conversation.COLUMN_MESSAGE)),
                        res.getString(res.getColumnIndex(Conversation.COLUMN_THUMB_IMAGE)),
                        res.getString(res.getColumnIndex(Conversation.COLUMN_CHANNEL_TYPE)),
                        res.getLong(res.getColumnIndex(Conversation.COLUMN_TIME_STAMP)),
                        res.getInt(res.getColumnIndex(Conversation.COLUMN_SEEN)) > 0
                );
                list.add(message);
                res.moveToNext();
            }
        }
        return list;
    }
    public Conversation getConversation(String TABLE_NAME , long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_NAME ,
                new String[]{Conversation.COLUMN_ID, Conversation.COLUMN_FROM_NAME , Conversation.COLUMN_FROM_ID, Conversation.COLUMN_MESSAGE , Conversation.COLUMN_THUMB_IMAGE , Conversation.COLUMN_CHANNEL_TYPE  , Conversation.COLUMN_TIME_STAMP , Conversation.COLUMN_SEEN},
                Conversation.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        //Cursor cursor = db.rawQuery("select * from " + Conversation.TABLE_NAME + " ORDER BY "+Conversation.COLUMN_TIME_STAMP+" ASC LIMIT 1" , null);//* = All

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Conversation message = new Conversation(
                cursor.getInt(cursor.getColumnIndex(Conversation.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Conversation.COLUMN_FROM_ID)),
                cursor.getString(cursor.getColumnIndex(Conversation.COLUMN_FROM_NAME)),
                cursor.getString(cursor.getColumnIndex(Conversation.COLUMN_MESSAGE)),
                cursor.getString(cursor.getColumnIndex(Conversation.COLUMN_THUMB_IMAGE)),
                cursor.getString(cursor.getColumnIndex(Conversation.COLUMN_CHANNEL_TYPE)) ,
                cursor.getLong(cursor.getColumnIndex(Conversation.COLUMN_TIME_STAMP)),
                cursor.getInt(cursor.getColumnIndex(Conversation.COLUMN_SEEN)) > 0
        );

        // close the db connection
        cursor.close();

        return message;
    }
    public List<Conversation> getAllrecordFromMomeryConv(Activity activity , String TABLE_NAME, final File yourFile) {
        List<Conversation> list = new ArrayList<>();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(yourFile, null);
                Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TABLE_NAME + "'", null);
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.close();
                        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " ORDER BY " + Message.COLUMN_TIME_STAMP + " DESC LIMIT 10", null);//* = All

                        if (res.moveToFirst()) {

                            while (!res.isAfterLast()) {
                                Conversation message = new Conversation(
                                        //int id  , String from_id, String from_name, String message
                                        // , String thumb_image, String channelType, long time_stamp , boolean seen
                                        res.getInt(res.getColumnIndex(Conversation.COLUMN_ID)),
                                        res.getString(res.getColumnIndex(Conversation.COLUMN_FROM_ID)),
                                        res.getString(res.getColumnIndex(Conversation.COLUMN_FROM_NAME)),
                                        res.getString(res.getColumnIndex(Conversation.COLUMN_MESSAGE)),
                                        res.getString(res.getColumnIndex(Conversation.COLUMN_THUMB_IMAGE)),
                                        res.getString(res.getColumnIndex(Conversation.COLUMN_CHANNEL_TYPE)),
                                        res.getLong(res.getColumnIndex(Conversation.COLUMN_TIME_STAMP)),
                                        res.getInt(res.getColumnIndex(Conversation.COLUMN_SEEN)) > 0
                                );
                                list.add(message);
                                res.moveToNext();
                            }
                        }
                    }
                    cursor.close();
                }

            }
        });
        return list;
    }
    //======================== DirectChat ========================
    public long insertData(String TABLE_NAME , String from_name , String message , String postion , String media_path , byte[] image , long time_stamp , boolean seen )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Message.COLUMN_FROM_NAME , from_name);
        contentValues.put(Message.COLUMN_MESSAGE , message);
        contentValues.put(Message.COLUMN_MEDIA_URL , image);
        contentValues.put(Message.COLUMN_POSITION , postion);
        contentValues.put(Message.COLUMN_MEDIA_PATH , media_path);
        contentValues.put(Message.COLUMN_TIME_STAMP , time_stamp);
        contentValues.put(Message.COLUMN_SEEN , seen);

        return db.insert(TABLE_NAME , null , contentValues);
    }
    public Message getMessage(String TABLE_NAME , long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_NAME ,
                new String[]{Message.COLUMN_ID, Message.COLUMN_FROM_NAME, Message.COLUMN_MESSAGE ,  Message.COLUMN_MEDIA_URL , Message.COLUMN_POSITION , Message.COLUMN_MEDIA_PATH  , Message.COLUMN_TIME_STAMP , Message.COLUMN_SEEN},
                Message.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        //Cursor cursor = db.rawQuery("select * from " + Message.TABLE_NAME + " ORDER BY "+Message.COLUMN_TIME_STAMP+" ASC LIMIT 1" , null);//* = All

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Message message = new Message(
                cursor.getInt(cursor.getColumnIndex(Message.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Message.COLUMN_FROM_NAME)),
                cursor.getString(cursor.getColumnIndex(Message.COLUMN_MESSAGE)),
                cursor.getBlob(cursor.getColumnIndex(Message.COLUMN_MEDIA_URL)),
                cursor.getString(cursor.getColumnIndex(Message.COLUMN_POSITION)) ,
                cursor.getString(cursor.getColumnIndex(Message.COLUMN_MEDIA_PATH)),
                cursor.getLong(cursor.getColumnIndex(Message.COLUMN_TIME_STAMP)),
                cursor.getInt(cursor.getColumnIndex(Message.COLUMN_SEEN)) > 0
        );

        // close the db connection
        cursor.close();

        return message;
    }
    public List<Message> getAllrecord(String TABLE_NAME)
    {
        List<Message> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " ORDER BY "+Message.COLUMN_TIME_STAMP+" DESC LIMIT 10" , null);//* = All

        if (res.moveToFirst()) {

            while (!res.isAfterLast()) {
                Message message = new Message(
                        res.getInt(res.getColumnIndex(Message.COLUMN_ID)),
                        res.getString(res.getColumnIndex(Message.COLUMN_FROM_NAME)),
                        res.getString(res.getColumnIndex(Message.COLUMN_MESSAGE)),
                        res.getBlob(res.getColumnIndex(Message.COLUMN_MEDIA_URL)),
                        res.getString(res.getColumnIndex(Message.COLUMN_POSITION)),
                        res.getString(res.getColumnIndex(Message.COLUMN_MEDIA_PATH)),
                        res.getLong(res.getColumnIndex(Message.COLUMN_TIME_STAMP)),
                        res.getInt(res.getColumnIndex(Message.COLUMN_SEEN)) > 0
                );
                list.add(message);
                res.moveToNext();
            }
        }
        return list;
    }
    public List<Message> getMorerecord(String TABLE_NAME , int mCurrentPage )
    {
        List<Message> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " ORDER BY "+Message.COLUMN_TIME_STAMP+" DESC LIMIT "+((mCurrentPage-1)*10)+", 10" , null);//* = All

        if (res.moveToFirst()) {

            while (!res.isAfterLast()) {
                Message message = new Message(
                        res.getInt(res.getColumnIndex(Message.COLUMN_ID)),
                        res.getString(res.getColumnIndex(Message.COLUMN_FROM_NAME)),
                        res.getString(res.getColumnIndex(Message.COLUMN_MESSAGE)),
                        res.getBlob(res.getColumnIndex(Message.COLUMN_MEDIA_URL)),
                        res.getString(res.getColumnIndex(Message.COLUMN_POSITION)),
                        res.getString(res.getColumnIndex(Message.COLUMN_MEDIA_PATH)),
                        res.getLong(res.getColumnIndex(Message.COLUMN_TIME_STAMP)),
                        res.getInt(res.getColumnIndex(Message.COLUMN_SEEN)) > 0
                );
                list.add(message);
                res.moveToNext();
            }
        }
        return list;
    }
    public List<Message> getAllrecordFromMomery(Activity activity , String TABLE_NAME, final File yourFile) {
        List<Message> list = new ArrayList<>();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(yourFile, null);
                Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TABLE_NAME + "'", null);
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.close();
                        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " ORDER BY " + Message.COLUMN_TIME_STAMP + " DESC LIMIT 10", null);//* = All

                        if (res.moveToFirst()) {

                            while (!res.isAfterLast()) {
                                Message message = new Message(
                                        res.getInt(res.getColumnIndex(Message.COLUMN_ID)),
                                        res.getString(res.getColumnIndex(Message.COLUMN_FROM_NAME)),
                                        res.getString(res.getColumnIndex(Message.COLUMN_MESSAGE)),
                                        res.getBlob(res.getColumnIndex(Message.COLUMN_MEDIA_URL)),
                                        res.getString(res.getColumnIndex(Message.COLUMN_POSITION)),
                                        res.getString(res.getColumnIndex(Message.COLUMN_MEDIA_PATH)),
                                        res.getLong(res.getColumnIndex(Message.COLUMN_TIME_STAMP)),
                                        res.getInt(res.getColumnIndex(Message.COLUMN_SEEN)) > 0
                                );
                                list.add(message);
                                res.moveToNext();
                            }
                        }
                    }
                    cursor.close();
                }

            }
        });
        return list;
    }

    //======================== Extras ========================
    public int getMessagesCount(String TABLE_NAME ) {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
    //
    public Cursor getAllItems(String TABLE_NAME)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME ,
                null,
                null,
                null,
                null,
                null,
                null);
    }
    public String getLastMessage(String TABLE_NAME , String user_id) {
        String lastMessage = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("SELECT "+Conversation.COLUMN_MESSAGE+" FROM "+TABLE_NAME+" WHERE "+Conversation.COLUMN_FROM_NAME+"=?", new String[] {user_id + ""} , null);
            if(res.getCount() > 0) {
                res.moveToFirst();
                lastMessage = res.getString(res.getColumnIndex(Conversation.COLUMN_MESSAGE));
            }
            return lastMessage;
        }finally {
            //cursor.close();
        }
    }
    public long getLastMessageT(String TABLE_NAME , String user_id) {
        long lastMessageT = 0;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("SELECT "+Conversation.COLUMN_TIME_STAMP+" FROM "+TABLE_NAME+" WHERE "+Conversation.COLUMN_FROM_NAME+"=?", new String[] {user_id + ""});
            if(res.getCount() > 0) {
                res.moveToFirst();
                lastMessageT = res.getLong(res.getColumnIndex(Conversation.COLUMN_TIME_STAMP));
            }
            return lastMessageT;
        }finally {
            //cursor.close();
        }
    }
    public boolean updateData (String TABLE_NAME , String id , String from_name , String message , Boolean itself , String color )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Message.COLUMN_FROM_NAME , from_name);
        contentValues.put(Message.COLUMN_MESSAGE , message);
        contentValues.put(Message.COLUMN_POSITION , itself);
        contentValues.put(Message.COLUMN_MEDIA_PATH , color);

        db.update(TABLE_NAME , contentValues , Message.COLUMN_ID + "= ?" , new String[]{id});
        return true;
    }
    public Integer Delete (String TABLE_NAME , String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME , Message.COLUMN_ID + "= ?" , new String[]{id});
    }
}
