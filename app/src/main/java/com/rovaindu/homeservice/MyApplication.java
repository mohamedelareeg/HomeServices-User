package com.rovaindu.homeservice;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

import com.rovaindu.homeservice.manager.ServiesSharedPrefManager;
import com.rovaindu.homeservice.model.chat.BaseMessage;
import com.rovaindu.homeservice.model.chat.Message;
import com.rovaindu.homeservice.model.chat.MessageReceipt;
import com.rovaindu.homeservice.model.chat.TypingIndicator;
import com.rovaindu.homeservice.retrofit.models.ServiesUser;

import java.util.concurrent.ConcurrentHashMap;


public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();

    private static ConcurrentHashMap<Integer, TypingIndicator> startTypingMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<Integer, TypingIndicator> endTypingMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, UserListener> userListeners = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, MessageListener> messageListeners = new ConcurrentHashMap();

    private static Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);}
    @Override
    public void onCreate() {
        super.onCreate();
    }
    public static int getLastDeliveredMessageId() {
        return ServiesSharedPrefManager.getLastDeliveredMessageId();
    }

    public static void startTyping(@NonNull TypingIndicator typingIndicator) {
        String methodName = (new Throwable()).getStackTrace()[0].getMethodName();

        try {
            if (typingIndicator != null) {
                if (startTypingMap.containsKey(typingIndicator.getReceiverId())) {
                    if (System.currentTimeMillis() - ((TypingIndicator)startTypingMap.get(typingIndicator.getReceiverId())).getLastTimestamp() >= 5000L) {
                        typingIndicator.setLastTimestamp(System.currentTimeMillis());
                        startTypingMap.put(typingIndicator.getReceiverId(), typingIndicator);
                        endTypingMap.remove(typingIndicator.getReceiverId());
                        //XMPPConnectionService.getInstance().startTyping(typingIndicator.getReceiverId(), typingIndicator.getReceiverType(), typingIndicator.getMetadata());
                    }
                } else {
                    typingIndicator.setLastTimestamp(System.currentTimeMillis());
                    startTypingMap.put(typingIndicator.getReceiverId(), typingIndicator);
                    endTypingMap.remove(typingIndicator.getReceiverId());
                    //XMPPConnectionService.getInstance().startTyping(typingIndicator.getReceiverId(), typingIndicator.getReceiverType(), typingIndicator.getMetadata());
                }
            }
        } catch (Exception var5) {
            Log.d(TAG, "startTyping: " + var5.getLocalizedMessage());

        }

    }

    public static void endTyping(@NonNull TypingIndicator typingIndicator) {
        String methodName = (new Throwable()).getStackTrace()[0].getMethodName();

        try {
            if (typingIndicator != null && startTypingMap.containsKey(typingIndicator.getReceiverId())) {
                if (endTypingMap.containsKey(typingIndicator.getReceiverId())) {
                    if (System.currentTimeMillis() - ((TypingIndicator)endTypingMap.get(typingIndicator.getReceiverId())).getLastTimestamp() >= 5000L) {
                        typingIndicator.setLastTimestamp(System.currentTimeMillis());
                        endTypingMap.put(typingIndicator.getReceiverId(), typingIndicator);
                        startTypingMap.remove(typingIndicator.getReceiverId());
                        //XMPPConnectionService.getInstance().endTyping(typingIndicator.getReceiverId(), typingIndicator.getReceiverType(), typingIndicator.getMetadata());
                    }
                } else {
                    typingIndicator.setLastTimestamp(System.currentTimeMillis());
                    endTypingMap.put(typingIndicator.getReceiverId(), typingIndicator);
                    startTypingMap.remove(typingIndicator.getReceiverId());
                    //XMPPConnectionService.getInstance().endTyping(typingIndicator.getReceiverId(), typingIndicator.getReceiverType(), typingIndicator.getMetadata());
                }
            }
        } catch (Exception var5) {
            Log.d(TAG, "typingIndicator: " + var5.getLocalizedMessage());

        }

    }

    public static void markAsRead(int messageId, @NonNull int receiverId, String receiverType) {
        String methodName = (new Throwable()).getStackTrace()[0].getMethodName();
        /*
        try {
            XMPPConnectionService.getInstance().markAsRead(messageId, receiverId, receiverType);
        } catch (Exception var7) {
            new CometChatException("ERROR_UNHANDLED_EXCEPTION", var7.getMessage());
            HashMap<String, String> detailsMap = new HashMap();
            detailsMap.put("messageId", String.valueOf(messageId));
            detailsMap.put("receiverId", receiverId);
            detailsMap.put("receiverType", receiverType);
            handleException(methodName, var7, detailsMap);
        }

         */

    }

    public static void markAsDelivered(int messageId, @NonNull int receiverId, String receiverType) {
        String methodName = (new Throwable()).getStackTrace()[0].getMethodName();
        /*
        try {
            XMPPConnectionService.getInstance().markAsDelivered(messageId, receiverId, receiverType);
        } catch (Exception var7) {
            new CometChatException("ERROR_UNHANDLED_EXCEPTION", var7.getMessage());
            HashMap<String, String> detailsMap = new HashMap();
            detailsMap.put("messageId", String.valueOf(messageId));
            detailsMap.put("receiverId", receiverId);
            detailsMap.put("receiverType", receiverType);
            handleException(methodName, var7, detailsMap);
        }

         */

    }

    public abstract static class UserListener {
        public UserListener() {
        }

        public void onUserOnline(ServiesUser user) {
        }

        public void onUserOffline(ServiesUser user) {
        }
    }
    public abstract static class MessageListener {
        public MessageListener() {
        }

        public void onTextMessageReceived(Message message) {
        }

        public void onMediaMessageReceived(Message message) {
        }

        public void onCustomMessageReceived(Message message) {
        }

        public void onTypingStarted(TypingIndicator typingIndicator) {
        }

        public void onTypingEnded(TypingIndicator typingIndicator) {
        }

        public void onMessagesDelivered(MessageReceipt messageReceipt) {
        }

        public void onMessagesRead(MessageReceipt messageReceipt) {
        }

        public void onMessageEdited(BaseMessage message) {
        }

        public void onMessageDeleted(BaseMessage message) {
        }
    }
    public static void addMessageListener(@NonNull String listenerID, @NonNull MessageListener listener) {
        if (!TextUtils.isEmpty(listenerID)) {
            messageListeners.put(listenerID, listener);
        }

    }

    public static void removeMessageListener(@NonNull String listenerID) {
        if (listenerID != null && !TextUtils.isEmpty(listenerID)) {
            messageListeners.remove(listenerID);
        }

    }

    public static void addUserListener(@NonNull String listenerID, @NonNull UserListener listener) {
        if (listenerID != null && !TextUtils.isEmpty(listenerID) && listener != null) {
            userListeners.put(listenerID, listener);
        }

    }

    public static void removeUserListener(@NonNull String listenerID) {
        if (!TextUtils.isEmpty(listenerID)) {
            userListeners.remove(listenerID);
        }

    }

}
