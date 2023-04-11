package com.rovaindu.homeservice.controller.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.adapter.chat.MessageAdapter;
import com.rovaindu.homeservice.base.BaseActivity;
import com.rovaindu.homeservice.controller.chat.fragments.ChatFragment;
import com.rovaindu.homeservice.interfaces.chat.MessageActionCloseListener;
import com.rovaindu.homeservice.interfaces.chat.OnMessageLongClick;
import com.rovaindu.homeservice.model.chat.BaseMessage;
import com.rovaindu.homeservice.retrofit.models.ServiesOrder;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.views.chat.ChatConstants;
import com.rovaindu.homeservice.utils.views.chat.StringContract;

import java.util.List;

public class ChatActivity extends BaseActivity implements  MessageAdapter.OnMessageLongClick {

    private static final String TAG = "ChatActivity" ;
    private OnMessageLongClick messageLongClick;

    Fragment fragment = new ChatFragment();

    private String avatar;

    private String name;

    private String uid;

    private String messageType;

    private String message;

    private String messagefileName;

    private String mediaUrl;

    private String mediaExtension;

    private int messageId;

    private int mediaSize;

    private String mediaMime;

    private String type;

    private String Id;

    private long sentAt;

    private int replyCount;

    private String conversationName;

    private ServiesOrder order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        permissons();
        EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);

        if (getIntent()!=null) {
            Bundle bundle = new Bundle();

            if (getIntent().hasExtra(Constants.BUNDLE_ORDER_LIST))
                order = (ServiesOrder) getIntent().getExtras().getSerializable(Constants.BUNDLE_ORDER_LIST);

            if (getIntent().hasExtra(StringContract.IntentStrings.CONVERSATION_NAME))
                conversationName = getIntent().getStringExtra(StringContract.IntentStrings.CONVERSATION_NAME);
            if (getIntent().hasExtra(StringContract.IntentStrings.PARENT_ID))
                messageId = getIntent().getIntExtra(StringContract.IntentStrings.PARENT_ID,0);

            if (getIntent().hasExtra(StringContract.IntentStrings.AVATAR))
                avatar = getIntent().getStringExtra(StringContract.IntentStrings.AVATAR);
            if (getIntent().hasExtra(StringContract.IntentStrings.NAME))
                name = getIntent().getStringExtra(StringContract.IntentStrings.NAME);
            if (getIntent().hasExtra(StringContract.IntentStrings.MESSAGE_TYPE))
                messageType = getIntent().getStringExtra(StringContract.IntentStrings.MESSAGE_TYPE);
            if (getIntent().hasExtra(StringContract.IntentStrings.UID))
                uid = getIntent().getStringExtra(StringContract.IntentStrings.UID);
            if (getIntent().hasExtra(StringContract.IntentStrings.SENTAT))
                sentAt = getIntent().getLongExtra(StringContract.IntentStrings.SENTAT,0);
            if (getIntent().hasExtra(StringContract.IntentStrings.TEXTMESSAGE))
                message = getIntent().getStringExtra(StringContract.IntentStrings.TEXTMESSAGE);
            if (getIntent().hasExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_NAME))
                messagefileName = getIntent().getStringExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_NAME);
            if (getIntent().hasExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_SIZE))
                mediaSize = getIntent().getIntExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_SIZE,0);
            if (getIntent().hasExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_URL))
                mediaUrl = getIntent().getStringExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_URL);
            if (getIntent().hasExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_EXTENSION))
                mediaExtension = getIntent().getStringExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_EXTENSION);
            if (getIntent().hasExtra(StringContract.IntentStrings.TYPE))
                type = getIntent().getStringExtra(StringContract.IntentStrings.TYPE);
            if (getIntent().hasExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_MIME_TYPE))
                mediaMime = getIntent().getStringExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_MIME_TYPE);

            if (type.equals(ChatConstants.RECEIVER_TYPE_GROUP)) {
                if (getIntent().hasExtra(StringContract.IntentStrings.GUID))
                    Id = getIntent().getStringExtra(StringContract.IntentStrings.GUID);
            } else{
                if (getIntent().hasExtra(StringContract.IntentStrings.UID))
                    Id = getIntent().getStringExtra(StringContract.IntentStrings.UID);
            }
            bundle.putSerializable(Constants.BUNDLE_ORDER_LIST , order);
            bundle.putString(StringContract.IntentStrings.ID,Id);
            bundle.putString(StringContract.IntentStrings.CONVERSATION_NAME,conversationName);
            bundle.putString(StringContract.IntentStrings.TYPE,type);
            bundle.putString(StringContract.IntentStrings.AVATAR, avatar);
            bundle.putString(StringContract.IntentStrings.NAME, name);
            bundle.putInt(StringContract.IntentStrings.PARENT_ID,messageId);

            bundle.putString(StringContract.IntentStrings.MESSAGE_TYPE,messageType);
            bundle.putString(StringContract.IntentStrings.UID, uid);
            bundle.putLong(StringContract.IntentStrings.SENTAT, sentAt);

            if (messageType.equals(ChatConstants.MESSAGE_TYPE_TEXT))
                bundle.putString(StringContract.IntentStrings.TEXTMESSAGE,message);
            else {
                bundle.putString(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_URL,mediaUrl);
                bundle.putString(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_NAME,messagefileName);
                bundle.putString(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_EXTENSION,mediaExtension);
                bundle.putInt(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_SIZE,mediaSize);
                bundle.putString(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_MIME_TYPE,mediaMime);
            }

            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.ChatFragment, fragment).commit();
        }

    }
    private void permissons() {
        String [] permissions = {Manifest.permission.RECORD_AUDIO , Manifest.permission.WRITE_EXTERNAL_STORAGE , Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.CAMERA    };
        int REQUEST_RECORD_AUDIO_PERMISSION = 200;
        int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 201;
        int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 202;
        int REQUEST_CAMERA_PERMISSION = 203;
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_READ_EXTERNAL_STORAGE_PERMISSION);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CAMERA_PERMISSION);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        Log.d(TAG, "onActivityResult: ");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setLongMessageClick(List<BaseMessage> baseMessage) {
        if (fragment!=null)
            ((OnMessageLongClick)fragment).setLongMessageClick(baseMessage);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void handleDialogClose(DialogInterface dialog) {
        ((MessageActionCloseListener)fragment).handleDialogClose(dialog);
    }
}