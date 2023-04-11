package com.rovaindu.homeservice.controller.chat.fragments;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.rovaindu.homeservice.MyApplication;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.adapter.chat.MessageAdapter;
import com.rovaindu.homeservice.controller.ProfileActivity;
import com.rovaindu.homeservice.controller.chat.ChatActivity;
import com.rovaindu.homeservice.controller.orders.TrackActivity;
import com.rovaindu.homeservice.interfaces.chat.ComposeActionListener;
import com.rovaindu.homeservice.interfaces.chat.MessageActionCloseListener;
import com.rovaindu.homeservice.interfaces.chat.OnItemClickListener;
import com.rovaindu.homeservice.interfaces.chat.OnMessageLongClick;
import com.rovaindu.homeservice.interfaces.chat.StickyHeaderDecoration;
import com.rovaindu.homeservice.manager.ServiesSharedPrefManager;
import com.rovaindu.homeservice.manager.chat_DB.DB_SqLite_Chat;
import com.rovaindu.homeservice.manager.chat_DB.DB_Utils;
import com.rovaindu.homeservice.model.AgentAvaliability;
import com.rovaindu.homeservice.model.chat.Action;
import com.rovaindu.homeservice.model.chat.Attachment;
import com.rovaindu.homeservice.model.chat.BaseMessage;
import com.rovaindu.homeservice.model.chat.Conversation;
import com.rovaindu.homeservice.model.chat.Message;
import com.rovaindu.homeservice.model.chat.MessageReceipt;
import com.rovaindu.homeservice.model.chat.Smartreply;
import com.rovaindu.homeservice.model.chat.TypingIndicator;
import com.rovaindu.homeservice.retrofit.models.ServiesAgent;
import com.rovaindu.homeservice.retrofit.models.ServiesOrder;
import com.rovaindu.homeservice.retrofit.models.ServiesUser;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.FontUtils;
import com.rovaindu.homeservice.utils.KeyBoardUtils;
import com.rovaindu.homeservice.utils.MediaUtils;
import com.rovaindu.homeservice.utils.views.chat.Avatar;
import com.rovaindu.homeservice.utils.views.chat.ChatConstants;
import com.rovaindu.homeservice.utils.views.chat.ComposeBox;
import com.rovaindu.homeservice.utils.views.chat.SmartReplyList;
import com.rovaindu.homeservice.utils.views.chat.StringContract;
import com.rovaindu.homeservice.utils.views.chat.Utils;
import com.rovaindu.homeservice.utils.views.shimmer.ShimmerFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.GONE;
import static com.rovaindu.homeservice.MyApplication.endTyping;
import static com.rovaindu.homeservice.MyApplication.startTyping;


public class ChatFragment  extends Fragment implements View.OnClickListener,
        OnMessageLongClick, MessageActionCloseListener {

    private static final String TAG = "ChatMessageScreen";
    private static final int LIMIT = 30;

    private RelativeLayout bottomLayout;

    private String name = "";

    private String status = "";

    private BaseMessage messagesRequest;    //Used to fetch messages.


    private ComposeBox composeBox;

    private RecyclerView rvChatListView;    //Used to display list of messages.

    private MessageAdapter messageAdapter;

    private LinearLayoutManager linearLayoutManager;

    private SmartReplyList rvSmartReply;

    private ShimmerFrameLayout messageShimmer;

    /**
     * <b>Avatar</b> is a UI Kit Component which is used to display user and group avatars.
     */
    private Avatar userAvatar;

    private TextView tvName;

    private TextView tvStatus;

    private int Id;

    private Context context;

    private LinearLayout blockUserLayout;

    private TextView blockedUserName;

    private StickyHeaderDecoration stickyHeaderDecoration;

    private String avatarUrl;

    private Toolbar toolbar;

    private String type;

    private String groupType;

    private boolean isBlockedByMe;

    private String loggedInUserScope;

    private RelativeLayout editMessageLayout;

    private TextView tvMessageTitle;

    private TextView tvMessageSubTitle;

    private RelativeLayout replyMessageLayout;

    private TextView replyTitle;

    private TextView replyMessage;

    private ImageView replyMedia;

    private ImageView replyClose;

    private BaseMessage baseMessage;

    private List<BaseMessage> baseMessages = new ArrayList<>();

    private List<BaseMessage> messageList = new ArrayList<>();

    private boolean isEdit;

    private boolean isReply;

    private String groupOwnerId;

    private int memberCount;

    private String memberNames;

    private String groupDesc;

    private String groupPassword;

    private Timer timer = new Timer();

    private Timer typingTimer = new Timer();

    private View view;

    private boolean isNoMoreMessages;

    private FontUtils fontUtils;

    private ServiesUser loggedInUser = ServiesSharedPrefManager.getInstance(getContext()).getUser();

    String[] CAMERA_PERMISSION = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private boolean isInProgress;

    private boolean isSmartReplyClicked;

    private RelativeLayout onGoingCallView;

    private TextView onGoingCallTxt;

    private ImageView onGoingCallClose;

    private RelativeLayout order_view;

    private TextView orderInfoTXT;

    public int count = 0;

    private MessageActionFragment messageActionFragment;

    private String mChatUser;
    private DB_Utils DBUtils;
    private String dbName;
    private File yourFile;
    public static DB_SqLite_Chat db_chat;

    //TODO POJO SMART REPLY
    private List<Smartreply> smartreplyList;
    private ServiesOrder selectedOrder;
    ServiesAgent selectedAgent;
    /*
    private ArrayList<AgentServies> agentServiesList;
    private ArrayList<AgentAvaliability> agentAvaliabilityList;
    private ArrayList<AgentAvaliability.DayAvaliabilty> dayAvaliabilties;

     */
    private int parentQuestion = -1 , subQuestion = -1;
    Timestamp timestamp;
    //

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleArguments();
        if (getActivity() != null)
            fontUtils = FontUtils.getInstance(getActivity());

    }
    /**
     * This method is used to handle arguments passed to this fragment.
     */
    private void handleArguments() {
        if (getArguments() != null) {
            selectedOrder = (ServiesOrder) getArguments().getSerializable(Constants.BUNDLE_ORDER_LIST);
            Log.d(TAG, "handleArguments: " + selectedOrder.getDay());
            Id = getArguments().getInt(StringContract.IntentStrings.UID);
            avatarUrl = getArguments().getString(StringContract.IntentStrings.AVATAR);
            status = getArguments().getString(StringContract.IntentStrings.STATUS);
            name = getArguments().getString(StringContract.IntentStrings.NAME);
            type = getArguments().getString(StringContract.IntentStrings.TYPE);
            if (type != null && type.equals(ChatConstants.RECEIVER_TYPE_GROUP)) {
                Id = getArguments().getInt(StringContract.IntentStrings.GUID);
                memberCount = getArguments().getInt(StringContract.IntentStrings.MEMBER_COUNT);
                groupDesc = getArguments().getString(StringContract.IntentStrings.GROUP_DESC);
                groupPassword = getArguments().getString(StringContract.IntentStrings.GROUP_PASSWORD);
                groupType = getArguments().getString(StringContract.IntentStrings.GROUP_TYPE);
            }
        }
    }
    ServiesUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        // =========================== SQlite ===========================
        timestamp = new Timestamp(System.currentTimeMillis());
        pojoAgentInfo();
        pojoAgent();
        pojoSmartReply();

        user = ServiesSharedPrefManager.getInstance(getContext()).getUser();
        mChatUser = usernameFromEmail(user.getEmail());
        Database();
        // =========================== Methods ===========================
        initViewComponent(view);
        return view;
    }

    private void pojoAgent() {
        selectedAgent = selectedOrder.getAgent();
        /*
        dayAvaliabilties = new ArrayList<>();
        agentAvaliabilityList = new ArrayList<>();
        agentServiesList = new ArrayList<>();

        UserAddress addressData = new UserAddress(27.097767097646255 , 31.16760905832052 , "Assiut Governorate, Egypt");

        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "6:30م - 7:30م" , 1));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "7:30م - 8:30م" , 1));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "8:30م - 9:30م" , 1));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "9:30م - 10:30م" , 2));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "10:30م - 11:30م" , 2));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "11:30م - 12:30ص" , 2));

        agentAvaliabilityList.add(new AgentAvaliability( 1 , "16/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 2 ,"17/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 3 , "18/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 4 , "19/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 5 , "20/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 6 ,"21/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 7 ,"22/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 8 ,"23/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 9 ,"24/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 10 ,"25/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 11,"26/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 12 ,"27/9",dayAvaliabilties));



        agentServiesList.add(new AgentServies( 1,"السباكة" ,"هناك حقبة مثبتة منذ زمن طويل " ,
                R.drawable.pojo_agent_servies, 500 , 250 , 50));
        agentServiesList.add(new AgentServies( 2,"الكهرباء" ,"هناك حقبة مثبتة منذ زمن طويل ",
                R.drawable.pojo_agent_servies, 1500 , 500 , 150));
        agentServiesList.add(new AgentServies( 3,"البناء" ,"هناك حقبة مثبتة منذ زمن طويل ",
                R.drawable.pojo_agent_servies, 2500 , 1000 , 1500));
        agentServiesList.add(new AgentServies( 4,"الحدادة" ,"هناك حقبة مثبتة منذ زمن طويل ",
                R.drawable.pojo_agent_servies, 250 , 50 , 10));

        selectedAgent = new Agent( 1 , "ياسر محمود القحطانى" , "مهندس كهرباء" , "السعودية, الرياض",
                5,554848448484L,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل" ,
                R.drawable.pojo_agent_1 ,1 ,1 ,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل",
                500 , 150 , 1500 ,50 , agentAvaliabilityList , addressData  , agentServiesList);

         */
    }

    private void pojoSmartReply() {
        smartreplyList = new ArrayList<>();
        List<Smartreply.BotReplys> botReplys2Child1 =new ArrayList<>();
        for (int i =0 ; i < agentAvaliabilityList.get(0).getDayAvaliabilties().size() ; i++)
        {
            if(agentAvaliabilityList.get(0).getDayAvaliabilties().get(i).getStatue() == 1) {
                botReplys2Child1.add(new Smartreply.BotReplys(agentAvaliabilityList.get(0).getDayAvaliabilties().get(i).getDate(), 1, null));
            }
        }

        List<Smartreply.BotReplys> botReplys3Child1 =new ArrayList<>();
        for (int i =0 ; i < selectedAgent.getServices().size() ; i++)
        {
            botReplys3Child1.add(new Smartreply.BotReplys(selectedAgent.getServices().get(i).getName() , 1 ,null));
        }

        List<Smartreply.BotReplys> botReplys3Child2 =new ArrayList<>();
        botReplys3Child2.add(new Smartreply.BotReplys("لا يوجد خدمات سابقة", 1 ,null));

        List<Smartreply.BotReplys> botReplys3Child3 =new ArrayList<>();
        botReplys3Child3.add(new Smartreply.BotReplys("يمكنك ترك رسالة بالمحتوى الذى تريد السؤال عنة وسيرد عليك احد ممثلى خدمة العملاء الخاصة بنا فى اقرب وقت", 1 ,null));

        List<Smartreply.BotReplys> botReplys1 =new ArrayList<>();
        botReplys1.add(new Smartreply.BotReplys("وعليكم السلام ورحمة الله وبركاتة", 1 ,null));
        List<Smartreply.BotReplys> botReplys2 =new ArrayList<>();
        botReplys2.add(new Smartreply.BotReplys("برجاء اختيار اليوم المناسب لك", 1 ,null));
        for (int i =0 ; i < agentAvaliabilityList.size() ; i++)
        {
            botReplys2.add(new Smartreply.BotReplys(agentAvaliabilityList.get(i).getDay(), 2 ,botReplys2Child1));
        }
        List<Smartreply.BotReplys> botReplys3 =new ArrayList<>();
        botReplys3.add(new Smartreply.BotReplys("ما الذى تريد الاستفسار عنة ؟", 1 ,null));
        botReplys3.add(new Smartreply.BotReplys("الخدمات التى نقدمها", 2 ,botReplys3Child1));
        botReplys3.add(new Smartreply.BotReplys("خدمة سابقة", 2 ,botReplys3Child2));
        botReplys3.add(new Smartreply.BotReplys("اخرى", 2 ,botReplys3Child3));

        List<Smartreply.BotReplys> botReplys4 =new ArrayList<>();
        botReplys4.add(new Smartreply.BotReplys("للتواصل مع خدمة العملاء برجاء الاتصال على رقم : "  + selectedAgent.getPhone(), 1 ,null));


        smartreplyList.add(new Smartreply(1 , 2 , "السلام عليكم" ,botReplys1));
        smartreplyList.add(new Smartreply(2 , 2 , "مواعيد العمل المتاحة" ,botReplys2));
        smartreplyList.add(new Smartreply(3 , 2 , "اريد ان اسال سؤالا ؟" ,botReplys3));
        smartreplyList.add(new Smartreply(4 , 2 , "اريد تقديم شكوى" ,botReplys4));
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }
    private void Database() {
        if (Utils.hasPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE})) {

            dbName = "msgstore.db";
            DBUtils = new DB_Utils(getContext());
            yourFile = new File(getContext().getExternalFilesDir(null).getAbsolutePath()
                    + File.separator + "Databases"
                    + File.separator + dbName);
            db_chat = new DB_SqLite_Chat(getContext(), dbName, null, 1);
            db_chat.queryData(Conversation.CREATE_TABLE_EXIST("conversations"));
            db_chat.queryData(Message.CREATE_TABLE_EXIST("RovaIndu"));
            //SQLite_Conv(userName , mChatUser);
            Log.d(TAG, "getChatFromDatabaseMomery: " + db_chat.getAllrecordConv("conversations").size());
        }

    }
    /**
     * This is a main method which is used to initialize the view for this fragment.
     *
     * @param view
     */
    private void initViewComponent(View view) {

        cFolders();
        setHasOptionsMenu(true);

        orderInfoTXT = view.findViewById(R.id.orderInfoTXT);
        order_view = view.findViewById(R.id.order_view);
        bottomLayout = view.findViewById(R.id.bottom_layout);
        composeBox = view.findViewById(R.id.message_box);
        messageShimmer = view.findViewById(R.id.shimmer_layout);
        composeBox = view.findViewById(R.id.message_box);

        setComposeBoxListener();

        rvSmartReply = view.findViewById(R.id.rv_smartReply);

        editMessageLayout = view.findViewById(R.id.editMessageLayout);
        tvMessageTitle = view.findViewById(R.id.tv_message_layout_title);
        tvMessageSubTitle = view.findViewById(R.id.tv_message_layout_subtitle);
        ImageView ivMessageClose = view.findViewById(R.id.iv_message_close);
        ivMessageClose.setOnClickListener(this);

        replyMessageLayout = view.findViewById(R.id.replyMessageLayout);
        replyTitle = view.findViewById(R.id.tv_reply_layout_title);
        replyMessage = view.findViewById(R.id.tv_reply_layout_subtitle);
        replyMedia = view.findViewById(R.id.iv_reply_media);
        replyClose = view.findViewById(R.id.iv_reply_close);
        replyClose.setOnClickListener(this);

        rvChatListView = view.findViewById(R.id.rv_message_list);
        MaterialButton unblockUserBtn = view.findViewById(R.id.btn_Unblock_user);
        unblockUserBtn.setOnClickListener(this);
        blockedUserName = view.findViewById(R.id.tv_Blocked_user_name);
        blockUserLayout = view.findViewById(R.id.blocked_User_layout);
        tvName = view.findViewById(R.id.tv_name);
        tvStatus = view.findViewById(R.id.tv_status);
        userAvatar = view.findViewById(R.id.iv_chat_avatar);
        toolbar = view.findViewById(R.id.chatList_toolbar);
        toolbar.setOnClickListener(this);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        tvName.setTypeface(fontUtils.getTypeFace(FontUtils.robotoMedium));
        tvName.setText(name);
        setAvatar();

        rvChatListView.setLayoutManager(linearLayoutManager);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Utils.isDarkMode(context)) {
            bottomLayout.setBackgroundColor(getResources().getColor(R.color.darkModeBackground));
            toolbar.setBackgroundColor(getResources().getColor(R.color.grey));
            editMessageLayout.setBackground(getResources().getDrawable(R.drawable.left_border_dark));
            replyMessageLayout.setBackground(getResources().getDrawable(R.drawable.left_border_dark));
            composeBox.setBackgroundColor(getResources().getColor(R.color.darkModeBackground));
            rvChatListView.setBackgroundColor(getResources().getColor(R.color.darkModeBackground));
            tvName.setTextColor(getResources().getColor(R.color.textColorWhite));
        } else {
            bottomLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.textColorWhite)));
            toolbar.setBackgroundColor(getResources().getColor(R.color.textColorWhite));
            editMessageLayout.setBackground(getResources().getDrawable(R.drawable.left_border));
            replyMessageLayout.setBackground(getResources().getDrawable(R.drawable.left_border));
            composeBox.setBackgroundColor(getResources().getColor(R.color.textColorWhite));
            rvChatListView.setBackgroundColor(getResources().getColor(R.color.textColorWhite));
            tvName.setTextColor(getResources().getColor(R.color.primaryTextColor));
        }

        KeyBoardUtils.setKeyboardVisibilityListener(getActivity(), (View) rvChatListView.getParent(), keyboardVisible -> {
            if (keyboardVisible) {
                scrollToBottom();
                composeBox.ivMic.setVisibility(GONE);
                composeBox.ivSend.setVisibility(View.VISIBLE);
            } else {
                if (isEdit) {
                    composeBox.ivMic.setVisibility(GONE);
                    composeBox.ivSend.setVisibility(View.VISIBLE);
                }else {
                    composeBox.ivMic.setVisibility(View.VISIBLE);
                    composeBox.ivSend.setVisibility(GONE);;
                }
            }
        });


        // Uses to fetch next list of messages if rvChatListView (RecyclerView) is scrolled in downward direction.
        rvChatListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                //for toolbar elevation animation i.e stateListAnimator
                toolbar.setSelected(rvChatListView.canScrollVertically(-1));
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if (!isNoMoreMessages && !isInProgress) {
                    if (linearLayoutManager.findFirstVisibleItemPosition() == 10 || !rvChatListView.canScrollVertically(-1)) {
                        isInProgress = true;
                        fetchMessage();
                    }
                }
            }

        });
        rvSmartReply.setItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void OnItemClick(String var, int position) {
                if (!isSmartReplyClicked) {
                    isSmartReplyClicked = true;
                    //rvSmartReply.setVisibility(GONE); TODO
                    sendSmartMessage(var);
                }
            }
        });

        //Check Ongoing Call
        onGoingCallView = view.findViewById(R.id.ongoing_call_view);
        onGoingCallClose = view.findViewById(R.id.close_ongoing_view);
        onGoingCallTxt = view.findViewById(R.id.ongoing_call);
       // checkOnGoingCall();

        orderInfoTXT.setText(selectedOrder.getServices().get(0).getName());//TODo
        order_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TrackActivity.class);
                i.putExtra(Constants.BUNDLE_ORDER_LIST, (Serializable) selectedOrder);
                context.startActivity(i);
            }
        });
    }



    private void setComposeBoxListener() {

        composeBox.setComposeBoxListener(new ComposeActionListener() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>0) {
                    sendTypingIndicator(false);
                } else {
                    sendTypingIndicator(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (typingTimer == null) {
                    typingTimer = new Timer();
                }
                endTypingTimer();
            }

            @Override
            public void onAudioActionClicked(ImageView audioIcon) {
                if (Utils.hasPermissions(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    startActivityForResult(MediaUtils.openAudio(getActivity()),StringContract.RequestCode.AUDIO);
                } else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},StringContract.RequestCode.AUDIO);
                }
            }

            @Override
            public void onCameraActionClicked(ImageView cameraIcon) {
                if (Utils.hasPermissions(getContext(), CAMERA_PERMISSION)) {
                    startActivityForResult(MediaUtils.openCamera(getContext()), StringContract.RequestCode.CAMERA);
                } else {
                    requestPermissions(CAMERA_PERMISSION, StringContract.RequestCode.CAMERA);
                }
            }


            @Override
            public void onGalleryActionClicked(ImageView galleryIcon) {
                if (Utils.hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    startActivityForResult(MediaUtils.openGallery(getActivity()), StringContract.RequestCode.GALLERY);
                } else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, StringContract.RequestCode.GALLERY);
                }
            }

            @Override
            public void onFileActionClicked(ImageView fileIcon) {
                if (Utils.hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    startActivityForResult(MediaUtils.getFileIntent(StringContract.IntentStrings.EXTRA_MIME_DOC), StringContract.RequestCode.FILE);
                } else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, StringContract.RequestCode.FILE);
                }
            }

            @Override
            public void onSendActionClicked(EditText editText) {
                String message = editText.getText().toString().trim();
                editText.setText("");
                editText.setHint(getString(R.string.message));
                if (isEdit) {
                    editMessage(baseMessage, message);
                    editMessageLayout.setVisibility(GONE);
                } else if(isReply){
                    replyMessage(baseMessage,message);
                    replyMessageLayout.setVisibility(GONE);
                } else if (!message.isEmpty())
                    sendMessage(message);
            }

            @Override
            public void onVoiceNoteComplete(String string) {
                if (string != null) {
                    File audioFile = new File(string);
                    sendMediaMessage(audioFile, ChatConstants.MESSAGE_TYPE_AUDIO);

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.d(TAG, "onRequestPermissionsResult: ");
        switch (requestCode) {

            case StringContract.RequestCode.CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)

                    startActivityForResult(MediaUtils.openCamera(getActivity()), StringContract.RequestCode.CAMERA);
                else
                    showSnackBar(view.findViewById(R.id.message_box), getResources().getString(R.string.grant_camera_permission));
                break;
            case StringContract.RequestCode.GALLERY:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    startActivityForResult(MediaUtils.openGallery(getActivity()), StringContract.RequestCode.GALLERY);
                else
                    showSnackBar(view.findViewById(R.id.message_box), getResources().getString(R.string.grant_storage_permission));
                break;
            case StringContract.RequestCode.FILE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    startActivityForResult(MediaUtils.getFileIntent(StringContract.IntentStrings.EXTRA_MIME_DOC), StringContract.RequestCode.FILE);
                else
                    showSnackBar(view.findViewById(R.id.message_box), getResources().getString(R.string.grant_storage_permission));
                break;
        }
    }

    private void showSnackBar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     * Incase if user is blocked already, then this method is used to unblock the user .
     *

     */
    private void unblockUser() {
        ArrayList<Integer> uids = new ArrayList<>();
        uids.add(Id);
        //TODO
        Snackbar.make(rvChatListView,String.format(getResources().getString(R.string.user_unblocked),name),Snackbar.LENGTH_LONG).show();
        blockUserLayout.setVisibility(GONE);
        isBlockedByMe = false;
        messagesRequest=null;
        //
    }

    /**
     * This method is used to set GroupMember names as subtitle in toolbar.
     *
     * @param users
     */
    private void setSubTitle(String... users) {
        if (users != null && users.length != 0) {
            StringBuilder stringBuilder = new StringBuilder();

            for (String user : users) {
                stringBuilder.append(user).append(",");
            }

            memberNames = stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();

            tvStatus.setText(memberNames);
        }

    }

    private static String mFileName = null;
    private static String mFileImgName = null;
    private void cFolders() {
        File folder = new File(getContext().getExternalFilesDir(null).getAbsolutePath() ,
                File.separator + "Rova" +
                File.separator + "Media"
        );
        if(!folder.exists()){
            folder.mkdirs();
        }

    }
    /**
     * This method is used to fetch message of users & groups. For user it fetches previous 100 messages at
     * a time and for groups it fetches previous 30 messages. You can change limit of messages by modifying
     * number in <code>setLimit()</code>
     * This method also mark last message as read using markMessageAsRead() present in this class.
     * So all the above messages get marked as read.
     *

     */
    private void fetchMessage() {

        /* TODO MESSAGE REQUEST
        if (messagesRequest == null) {
            if (type != null) {
                if (type.equals(ChatConstants.RECEIVER_TYPE_USER))
                    messagesRequest = new MessagesRequest.MessagesRequestBuilder().setLimit(LIMIT).hideReplies(true).setUID(Id).build();
                else
                    messagesRequest = new MessagesRequest.MessagesRequestBuilder().setLimit(LIMIT).hideReplies(true).setGUID(Id).hideMessagesFromBlockedUsers(true).build();
            }
        }

         */
        String fileName = "";
        mFileImgName = Environment.getExternalStorageDirectory().getPath()
                + "/FutureAcademy"
                + "/Media"
                + "/FutureRecords";

        mFileImgName += "/" + fileName + ".3gp";
/*
        UserAddress addressData = new UserAddress(37.42199845544925, -122.0839998498559, "Mountain View, CA 94043");
        ServiesUser user = new ServiesUser(2, "محمد السيد", "mohaa.coder@yahoo.com",
                "https://www.mantruckandbus.com/fileadmin/media/bilder/02_19/219_05_busbusiness_interviewHeader_1485x1254.jpg", "رجل"
                , "المملكة العربية السعودية", "الرياض", "1277637646", "", addressData);


 */
        Message textMessage = new Message(1, "بسم الله الرحمن الرحيم", ChatConstants.RECEIVER_TYPE_USER);
        long currentTime = timestamp.getTime();
        //int id, String muid, User sender, AppEntity receiver, int receiverUid, String type, String receiverType, String category
        // , long sentAt, long deliveredAt, long readAt, JSONObject metadata, long readByMeAt, long deliveredToMeAt, long deletedAt
        // , long editedAt, String deletedBy, String editedBy, long updatedAt, String conversationId, int parentMessageId, int replyCount
        baseMessages.add(new BaseMessage(1 , "t" , user  , 1 , ChatConstants.MESSAGE_TYPE_TEXT , ChatConstants.RECEIVER_TYPE_USER ,
                ChatConstants.CATEGORY_MESSAGE , currentTime , 0 , 0  , currentTime , currentTime , 0 , 0 , null ,"Mohaa"  , 0 ,"Mohaa",
                1  , textMessage));
        baseMessages.add(new BaseMessage(2 , "t" , user  , 1 , ChatConstants.MESSAGE_TYPE_TEXT , ChatConstants.RECEIVER_TYPE_USER ,
                ChatConstants.CATEGORY_MESSAGE , currentTime , 0 , 0  , currentTime , currentTime , 0 , 0 , null ,"Mohaa" , 0 ,"Mohaa",
                1  , textMessage));
        baseMessages.add(new BaseMessage(3 , "t" , user  , 1 , ChatConstants.MESSAGE_TYPE_TEXT , ChatConstants.RECEIVER_TYPE_USER ,
                ChatConstants.CATEGORY_MESSAGE , currentTime , 0 , 0  , currentTime , currentTime , 0 , 0 , null ,"Mohaa", 0  ,"Mohaa",
                1   , textMessage));
        baseMessages.add(new BaseMessage(4 , "t" , user  , 1 , ChatConstants.MESSAGE_TYPE_TEXT , ChatConstants.RECEIVER_TYPE_USER ,
                ChatConstants.CATEGORY_MESSAGE , currentTime , 0 , 0  , currentTime , currentTime , 0 , 0 , null ,"Mohaa" , 0 ,"Mohaa",
                1  , textMessage));
        baseMessages.add(new BaseMessage(5 , "t" , user  , 1 , ChatConstants.MESSAGE_TYPE_TEXT , ChatConstants.RECEIVER_TYPE_USER ,
                ChatConstants.CATEGORY_MESSAGE , currentTime , 0 , 0  , currentTime , currentTime , 0 , 0 , null ,"Mohaa", 0  ,"Mohaa",
                1  , textMessage));
        baseMessages.add(new BaseMessage(6 , "t" , user  , 1 , ChatConstants.MESSAGE_TYPE_TEXT , ChatConstants.RECEIVER_TYPE_USER ,
                ChatConstants.CATEGORY_MESSAGE , currentTime , 0 , 0  , currentTime , currentTime , 0 , 0 , null ,"Mohaa", 0  ,"Mohaa",
                1  , textMessage));
        isInProgress = false;
        List<BaseMessage> filteredMessageList = filterBaseMessages(baseMessages);
        initMessageAdapter(filteredMessageList);
        if (baseMessages.size() != 0) {
            stopHideShimmer();
            BaseMessage baseMessage = baseMessages.get(baseMessages.size() - 1);
            markMessageAsRead(baseMessage);
        }

        if (baseMessages.size() == 0) {
            stopHideShimmer();
            isNoMoreMessages = true;
        }
    }

    private void stopHideShimmer() {
        messageShimmer.stopShimmer();
        messageShimmer.setVisibility(GONE);
    }


    private List<BaseMessage> filterBaseMessages(List<BaseMessage> baseMessages) {
        List<BaseMessage> tempList = new ArrayList<>();
        for(BaseMessage baseMessage : baseMessages)
        {
            Log.e(TAG, "filterBaseMessages: "+baseMessage.toString());
            if (baseMessage.getCategory().equals(ChatConstants.CATEGORY_ACTION)) {
                Action action = ((Action)baseMessage);
                if (action.getAction().equals(ChatConstants.ActionKeys.ACTION_MESSAGE_DELETED) ||
                        action.getAction().equals(ChatConstants.ActionKeys.ACTION_MESSAGE_EDITED)) {
                }
                else {
                    tempList.add(baseMessage);
                }
            }

            else {
                tempList.add(baseMessage);
            }
        }
        return tempList;
    }

    private void getSmartReplyList(BaseMessage baseMessage) {

        //
        rvSmartReply.setVisibility(View.VISIBLE);
        List<String> replyList = new ArrayList<>();
        try {
            Log.d(TAG, "checkSmartReply: "  + baseMessage.getMessage().toString());
            for (int i = 0; i < smartreplyList.size() ; i++)
            {
                replyList.add(smartreplyList.get(i).getQuestion());
            }
        } catch (Exception e) {
            Log.e(TAG, "onSuccess: " + e.getMessage());
        }
        setSmartReplyAdapter(replyList);
        //

        /*
        HashMap<String, JSONObject> extensionList = Extensions.extensionCheck(baseMessage);
        if (extensionList != null && extensionList.containsKey("smartReply")) {//TODO

            JSONObject replyObject = extensionList.get("smartReply");

        } else {
            rvSmartReply.setVisibility(GONE);
        }

         */
    }

    private void setSmartReplyAdapter(List<String> replyList) {
        rvSmartReply.setSmartReplyList(replyList);
        scrollToBottom();
    }


    /**
     * This method is used to initialize the message adapter if it is empty else it helps
     * to update the messagelist in adapter.
     *
     * @param messageList is a list of messages which will be added.
     */
    private void initMessageAdapter(List<BaseMessage> messageList) {
        if (messageAdapter == null) {
            messageAdapter = new MessageAdapter(getActivity(), messageList, ChatFragment.class.getName());
            rvChatListView.setAdapter(messageAdapter);
            stickyHeaderDecoration = new StickyHeaderDecoration(messageAdapter);
            rvChatListView.addItemDecoration(stickyHeaderDecoration, 0);
            scrollToBottom();
            messageAdapter.notifyDataSetChanged();
        } else {
            messageAdapter.updateList(messageList);

        }
       // if (!isBlockedByMe && rvSmartReply.getAdapter().getItemCount()==0 && rvSmartReply.getVisibility() == GONE) {
        if (rvSmartReply.getAdapter().getItemCount()==0 ) {
            BaseMessage lastMessage = messageAdapter.getLastMessage();
            checkSmartReply(lastMessage);
        }
    }

    /**
     * This method is used to send typing indicator to other users and groups.
     *
     * @param isEnd is boolean which is used to differentiate between startTyping & endTyping Indicators.

     */
    private void sendTypingIndicator(boolean isEnd) {
        if (isEnd) {
            if (type.equals(ChatConstants.RECEIVER_TYPE_USER)) {
               endTyping(new TypingIndicator(Id, ChatConstants.RECEIVER_TYPE_USER));
            } else {
                endTyping(new TypingIndicator(Id, ChatConstants.RECEIVER_TYPE_GROUP));
            }
        } else {
            if (type.equals(ChatConstants.RECEIVER_TYPE_USER)) {
                startTyping(new TypingIndicator(Id, ChatConstants.RECEIVER_TYPE_USER));
            } else {
                startTyping(new TypingIndicator(Id, ChatConstants.RECEIVER_TYPE_GROUP));
            }
        }
    }

    private void endTypingTimer() {
        if (typingTimer!=null) {
            typingTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    sendTypingIndicator(true);
                }
            }, 2000);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");

        switch (requestCode) {
            case StringContract.RequestCode.AUDIO:
                if (data!=null) {
                    Log.d(TAG, "onActivityResult: record " );

                    File file = MediaUtils.getRealPath(getContext(),data.getData());
                    ContentResolver cr = getActivity().getContentResolver();
                    sendMediaMessage(file,ChatConstants.MESSAGE_TYPE_AUDIO);

                }
                break;
            case StringContract.RequestCode.GALLERY:
                if (data != null) {

                    File file = MediaUtils.getRealPath(getContext(), data.getData());

                    ContentResolver cr = getActivity().getContentResolver();
                    String mimeType = cr.getType(data.getData());
                    if (mimeType!=null && mimeType.contains("image")) {
                        if (file.exists()) {
                            Log.d(TAG, "onActivityResult: " + file.toString());
                            sendMediaMessage(file, ChatConstants.MESSAGE_TYPE_IMAGE);
                        }
                        else
                            Snackbar.make(rvChatListView, R.string.file_not_exist, Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        if (file.exists())
                            sendMediaMessage(file, ChatConstants.MESSAGE_TYPE_VIDEO);
                        else
                            Snackbar.make(rvChatListView, R.string.file_not_exist, Snackbar.LENGTH_LONG).show();
                    }
                }

                break;
            case StringContract.RequestCode.CAMERA:
                File file;
                if (Build.VERSION.SDK_INT >= 29) {
                    file = MediaUtils.getRealPath(getContext(), MediaUtils.uri);
                } else {
                    file = new File(MediaUtils.pictureImagePath);
                }
                if (file.exists())
                    sendMediaMessage(file, ChatConstants.MESSAGE_TYPE_IMAGE);
                else
                    Snackbar.make(rvChatListView,R.string.file_not_exist,Snackbar.LENGTH_LONG).show();

                break;
            case StringContract.RequestCode.FILE:
                if (data != null)
                    sendMediaMessage(MediaUtils.getRealPath(getActivity(), data.getData()), ChatConstants.MESSAGE_TYPE_FILE);
                break;
            case StringContract.RequestCode.BLOCK_USER:
                name = data.getStringExtra("");
                break;
        }

    }


    /**
     * This method is used to send media messages to other users and group.
     *
     * @param file     is an object of File which is been sent within the message.
     * @param filetype is a string which indicate a type of file been sent within the message.

     * @see Message
     */
    private void sendMediaMessage(File file, String filetype) {
        long CurrentTime = timestamp.getTime();
        Message mediaMessage;
        Attachment attachment = new Attachment("" , "jpg" , 0 , "image" , file.getAbsolutePath());
        if (type.equalsIgnoreCase(ChatConstants.RECEIVER_TYPE_USER)) {
            Log.d(TAG, "sendMediaMessage: " +filetype + file.getAbsolutePath());
            mediaMessage = new Message(Id, file, filetype, ChatConstants.RECEIVER_TYPE_USER , attachment);
        }
        else
            mediaMessage = new Message(Id, file, filetype, ChatConstants.RECEIVER_TYPE_GROUP , attachment);



        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("path", file.getAbsolutePath());
        } catch (JSONException e) {
            e.printStackTrace();
        }
       // mediaMessage.setMetadata(jsonObject);TODO
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        switch (filetype)
        {
            case  ChatConstants.MESSAGE_TYPE_AUDIO:
            {
                long insert_id = db_chat.insertData(mChatUser ,user.getName(), "Audio", "6", file.getAbsolutePath(), null ,timestamp.getTime() , false);
                break;
            }
            case  ChatConstants.MESSAGE_TYPE_IMAGE:
            {
                //long insert_id = db_chat.insertData(mChatUser ,user.getName(), "", "2", file.getAbsolutePath(), null ,timestamp.getTime() , false);
                break;
            }
        }

        //int id, String muid, User sender, AppEntity receiver, int receiverUid, String type, String receiverType, String category
        // , long sentAt, long deliveredAt, long readAt, JSONObject metadata, long readByMeAt, long deliveredToMeAt, long deletedAt
        // , long editedAt, String deletedBy, String editedBy, long updatedAt, String conversationId, int parentMessageId, int replyCount
        BaseMessage baseMessage = new BaseMessage(baseMessages.size() +1 , "t" , user  , 1 , filetype , ChatConstants.RECEIVER_TYPE_USER ,
                ChatConstants.CATEGORY_MESSAGE , CurrentTime , 0 , 0  , CurrentTime , CurrentTime , 0 , 0 , null ,"Mohaa" ,0 ,"Mohaa",
                1  , mediaMessage);

        //TODO
        if (messageAdapter != null) {
            messageAdapter.addMessage(baseMessage);
            scrollToBottom();
            Log.d(TAG, "sendMediaMessage: " + baseMessage.getMessage().getAttachment().getFileUrl());
            //BotResponse();
        }
        //
    }

    /**
     * This method is used to get details of reciever.
     *

     */


    private void setAvatar() {
        if (avatarUrl != null && !avatarUrl.isEmpty())
            userAvatar.setAvatar(avatarUrl);
        else {
            userAvatar.setInitials(name);
        }
    }



    /**
     * This method is used to send Text Message to other users and groups.
     *
     * @param message is a String which is been sent as message.
     * @see Message

     */
    private void sendMessage(String message) {
        long CurrentTime = timestamp.getTime();
        Message textMessage;
        if (type.equalsIgnoreCase(ChatConstants.RECEIVER_TYPE_USER))
            textMessage = new Message(Id, message, ChatConstants.RECEIVER_TYPE_USER);
        else
            textMessage = new Message(Id, message, ChatConstants.RECEIVER_TYPE_GROUP);

        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        sendTypingIndicator(true);
        long insert_id = db_chat.insertData(mChatUser , user.getName(), message, "0", "", null ,timestamp.getTime() , false);

        //int id, String muid, User sender, AppEntity receiver, int receiverUid, String type, String receiverType, String category
        // , long sentAt, long deliveredAt, long readAt, JSONObject metadata, long readByMeAt, long deliveredToMeAt, long deletedAt
        // , long editedAt, String deletedBy, String editedBy, long updatedAt, String conversationId, int parentMessageId, int replyCount
        BaseMessage baseMessage = new BaseMessage(baseMessages.size() +1 , "t" , user  , 1 , ChatConstants.MESSAGE_TYPE_TEXT , ChatConstants.RECEIVER_TYPE_USER ,
                ChatConstants.CATEGORY_MESSAGE , CurrentTime , 0 , 0  , CurrentTime , CurrentTime , 0 , 0 , null ,"Mohaa" ,0 ,"Mohaa",
                1  , textMessage);
        //TODO
        isSmartReplyClicked=false;
        if (messageAdapter != null) {
            MediaUtils.playSendSound(context,R.raw.outgoing_message);
            messageAdapter.addMessage(baseMessage);
            scrollToBottom();
            //BotResponse();
        }
        //


    }
    private void sendSmartMessage(String message) {
        long CurrentTime = timestamp.getTime();
        Message textMessage;
        if (type.equalsIgnoreCase(ChatConstants.RECEIVER_TYPE_USER))
            textMessage = new Message(Id, message, ChatConstants.RECEIVER_TYPE_USER);
        else
            textMessage = new Message(Id, message, ChatConstants.RECEIVER_TYPE_GROUP);


        sendTypingIndicator(true);
        long insert_id = db_chat.insertData(mChatUser , user.getName(), message, "0", "", null ,CurrentTime , false);

        //int id, String muid, User sender, AppEntity receiver, int receiverUid, String type, String receiverType, String category
        // , long sentAt, long deliveredAt, long readAt, JSONObject metadata, long readByMeAt, long deliveredToMeAt, long deletedAt
        // , long editedAt, String deletedBy, String editedBy, long updatedAt, String conversationId, int parentMessageId, int replyCount
        BaseMessage baseMessage = new BaseMessage(baseMessages.size() +1 , "t" , user  , 1 , ChatConstants.MESSAGE_TYPE_TEXT , ChatConstants.RECEIVER_TYPE_USER ,
                ChatConstants.CATEGORY_MESSAGE , CurrentTime , CurrentTime , CurrentTime  , CurrentTime , CurrentTime , 0 , 0 , null ,"Mohaa" ,0 ,"Mohaa",
                1  , textMessage);
        //TODO
        isSmartReplyClicked=false;
        if (messageAdapter != null) {
            MediaUtils.playSendSound(context,R.raw.outgoing_message);
            messageAdapter.addMessage(baseMessage);
            scrollToBottom();
            BotResponse(message);
        }
        //


    }
    public void BotResponse(String message)
    {
        long CurrentTime = timestamp.getTime();



        //TODO
        String botMessage = "";
        if(parentQuestion != -1)
        {
            for (int i = 0; i < smartreplyList.get(parentQuestion).getBotReplys().size(); i++) {
                if (smartreplyList.get(parentQuestion).getBotReplys().get(i).getReply().equals(message)) {
                    subQuestion = i;
                        Log.d(TAG, "BotResponse: " + smartreplyList.get(parentQuestion).getBotReplys().get(subQuestion).getReply());
                        for (int z = 0; z < smartreplyList.get(parentQuestion).getBotReplys().get(subQuestion).getBotReplys().size(); z++) {
                            if (smartreplyList.get(parentQuestion).getBotReplys().get(subQuestion).getBotReplys().get(z).getType() == 1) {

                                botMessage += smartreplyList.get(parentQuestion).getBotReplys().get(subQuestion).getBotReplys().get(z).getReply() + "\n";

                            }
                        }
                }
            }
        }
        else {
            for (int i = 0; i < smartreplyList.size(); i++) {
                if (smartreplyList.get(i).getQuestion().equals(message)) {
                    Log.d(TAG, "BotResponse: ");
                    parentQuestion = i;
                    List<String> replyList = new ArrayList<>();
                    try {
                        Log.d(TAG, "checkSmartReply: " + message);
                        for (int z = 0; z < smartreplyList.get(parentQuestion).getBotReplys().size(); z++) {
                            if (smartreplyList.get(parentQuestion).getBotReplys().get(z).getType() == 2) {
                                replyList.add(smartreplyList.get(parentQuestion).getBotReplys().get(z).getReply());

                            }
                            else if (smartreplyList.get(parentQuestion).getBotReplys().get(z).getType() == 1) {
                                botMessage += smartreplyList.get(parentQuestion).getBotReplys().get(z).getReply() + "\n";


                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onSuccess: " + e.getMessage());
                    }
                    setSmartReplyAdapter(replyList);


                }
            }
        }
        Message textMessage = new Message(1, botMessage, ChatConstants.RECEIVER_TYPE_USER);
        BaseMessage baseMessage = new BaseMessage(baseMessages.size() +1 , "t" , user  , 1 , ChatConstants.MESSAGE_TYPE_TEXT , ChatConstants.RECEIVER_TYPE_USER ,
                ChatConstants.CATEGORY_MESSAGE , CurrentTime , CurrentTime , CurrentTime  , CurrentTime , CurrentTime , 0 , 0 , null ,"Mohaa" ,0 ,"Mohaa",
                1  , textMessage);
        isSmartReplyClicked=false;
        if (messageAdapter != null) {
            //MediaUtils.playSendSound(context,R.raw.incoming_message); TODO
            messageAdapter.addMessage(baseMessage);
            scrollToBottom();
        }
    }
    /**
     * This method is used to delete the message.
     *
     * @param baseMessage is an object of BaseMessage which is being used to delete the message.
     * @see BaseMessage
     */
    private void deleteMessage(BaseMessage baseMessage) {
       //TODO
        if (messageAdapter != null)
            messageAdapter.setUpdatedMessage(baseMessage);
        //
    }

    /**
     * This method is used to edit the message. This methods takes old message and change text of old
     * message with new message i.e String and update it.
     *
     * @param baseMessage is an object of BaseMessage, It is a old message which is going to be edited.
     * @param message     is String, It is a new message which will be replaced with text of old message.
     * @see Message
     * @see BaseMessage
     */
    private void editMessage(BaseMessage baseMessage, String message) {

        isEdit = false;

        Message textMessage;
        if (baseMessage.getReceiverType().equalsIgnoreCase(ChatConstants.RECEIVER_TYPE_USER))
            textMessage = new Message(baseMessage.getReceiverUid(), message, ChatConstants.RECEIVER_TYPE_USER);
        else
            textMessage = new Message(baseMessage.getReceiverUid(), message, ChatConstants.RECEIVER_TYPE_GROUP);
        sendTypingIndicator(true);
        textMessage.setId(baseMessage.getId());
        //TODO
        if (messageAdapter != null) {
            Log.e(TAG, "onSuccess: " + baseMessage.getMessage().toString());
            messageAdapter.setUpdatedMessage(baseMessage.getMessage());
        }
        //

    }

    /**
     * This method is used to send reply message by link previous message with new message.
     * @param baseMessage is a linked message
     * @param message is a String. It will be new message sent as reply.
     */
    private void replyMessage(BaseMessage baseMessage, String message) {
        isReply = false;
        try {
            Message textMessage;
            if (type.equalsIgnoreCase(ChatConstants.RECEIVER_TYPE_USER))
                textMessage = new Message(Id, message, ChatConstants.RECEIVER_TYPE_USER);
            else
                textMessage = new Message(Id, message, ChatConstants.RECEIVER_TYPE_GROUP);
            JSONObject jsonObject = new JSONObject();
            JSONObject replyObject = new JSONObject();
            if (baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_TEXT)) {
                replyObject.put("type",ChatConstants.MESSAGE_TYPE_TEXT);
                replyObject.put("message", baseMessage.getMessage().getText());
            } else if (baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_IMAGE)) {
                replyObject.put("type",ChatConstants.MESSAGE_TYPE_IMAGE);
                replyObject.put("message", "image");
            } else if (baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_VIDEO)) {
                replyObject.put("type",ChatConstants.MESSAGE_TYPE_VIDEO);
                replyObject.put("message", "video");
            } else if (baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_FILE)) {
                replyObject.put("type",ChatConstants.MESSAGE_TYPE_FILE);
                replyObject.put("message", "file");
            } else if (baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_AUDIO)) {
                replyObject.put("type", ChatConstants.MESSAGE_TYPE_AUDIO);
                replyObject.put("message", "audio");
            }
            replyObject.put("name",baseMessage.getSender().getName());
            replyObject.put("avatar",baseMessage.getSender().getImage());
            jsonObject.put("reply",replyObject);
            //textMessage.setMetadata(jsonObject); TODO
            sendTypingIndicator(true);
            //TODO
            if (messageAdapter != null) {
                MediaUtils.playSendSound(context,R.raw.outgoing_message);
                messageAdapter.addMessage(textMessage);
                scrollToBottom();
            }
            //
        }catch (Exception e) {
            Log.e(TAG, "replyMessage: "+e.getMessage());
        }
    }

    private void scrollToBottom() {
        if (messageAdapter != null && messageAdapter.getItemCount() > 0) {
            rvChatListView.scrollToPosition(messageAdapter.getItemCount() - 1);

        }
    }



    /**
     * This method is used to mark users & group message as read.
     *
     * @param baseMessage is object of BaseMessage.class. It is message which is been marked as read.
     */
    private void markMessageAsRead(BaseMessage baseMessage) {
        if (type.equals(ChatConstants.RECEIVER_TYPE_USER))
            MyApplication.markAsRead(baseMessage.getId(), baseMessage.getSender().getId(), baseMessage.getReceiverType());
        else
            MyApplication.markAsRead(baseMessage.getId(), baseMessage.getReceiverUid(), baseMessage.getReceiverType());
    }


    /**
     * This method is used to add message listener to recieve real time messages between users &
     * groups. It also give real time events for typing indicators, edit message, delete message,
     * message being read & delivered.
     *

     */
    private void addMessageListener() {

        MyApplication.addMessageListener(TAG, new MyApplication.MessageListener() {
            @Override
            public void onTextMessageReceived(Message message) {
                Log.d(TAG, "onTextMessageReceived: " + message.toString());
                onMessageReceived(message);
            }

            @Override
            public void onMediaMessageReceived(Message message) {
                Log.d(TAG, "onMediaMessageReceived: " + message.toString());
                onMessageReceived(message);
            }

            @Override
            public void onTypingStarted(TypingIndicator typingIndicator) {
                Log.e(TAG, "onTypingStarted: " + typingIndicator);
                setTypingIndicator(typingIndicator,true);
            }

            @Override
            public void onTypingEnded(TypingIndicator typingIndicator) {
                Log.d(TAG, "onTypingEnded: " + typingIndicator.toString());
                setTypingIndicator(typingIndicator,false);
            }

            @Override
            public void onMessagesDelivered(MessageReceipt messageReceipt) {
                Log.d(TAG, "onMessagesDelivered: " + messageReceipt.toString());
                setMessageReciept(messageReceipt);

            }

            @Override
            public void onMessagesRead(MessageReceipt messageReceipt) {
                Log.e(TAG, "onMessagesRead: " + messageReceipt.toString());
                setMessageReciept(messageReceipt);
            }

            @Override
            public void onMessageEdited(BaseMessage message) {
                Log.d(TAG, "onMessageEdited: " + message.toString());
                updateMessage(message);
            }

            @Override
            public void onMessageDeleted(BaseMessage message) {
                Log.d(TAG, "onMessageDeleted: ");
                updateMessage(message);
            }
        });
    }

    private void setMessageReciept(MessageReceipt messageReceipt) {
        if (messageAdapter != null) {
            if (messageReceipt.getReceivertype().equals(ChatConstants.RECEIVER_TYPE_USER)) {
                if (Id!=0 ) {
                    if (messageReceipt.getReceiptType().equals(MessageReceipt.RECEIPT_TYPE_DELIVERED))
                        messageAdapter.setDeliveryReceipts(messageReceipt);
                    else
                        messageAdapter.setReadReceipts(messageReceipt);
                }
            }
        }
    }

    private void setTypingIndicator(TypingIndicator typingIndicator,boolean isShow) {
        if (typingIndicator.getReceiverType().equalsIgnoreCase(ChatConstants.RECEIVER_TYPE_USER)) {
            Log.e(TAG, "onTypingStarted: " + typingIndicator);
            if (Id != 0) {
                if (typingIndicator.getMetadata() == null)
                    typingIndicator(typingIndicator, isShow);
            }
        } else {
            if (Id != 0)
                typingIndicator(typingIndicator, isShow);
        }
    }

    private void onMessageReceived(BaseMessage message) {
        MediaUtils.playSendSound(context,R.raw.incoming_message);
        if (message.getReceiverType().equals(ChatConstants.RECEIVER_TYPE_USER)) {
            if (Id != 0 ) {
                setMessage(message);
            } else if(Id != 0) {
                setMessage(message);
            }
        } else {
            if (Id != 0) {
                setMessage(message);
            }
        }
    }

    /**
     * This method is used to update edited message by calling <code>setEditMessage()</code> of adapter
     *
     * @param message is an object of BaseMessage and it will replace with old message.
     * @see BaseMessage
     */
    private void updateMessage(BaseMessage message) {
        messageAdapter.setUpdatedMessage(message);
    }


    /**
     * This method is used to mark message as read before adding them to list. This method helps to
     * add real time message in list.
     *
     * @param message is an object of BaseMessage, It is recieved from message listener.
     * @see BaseMessage
     */
    private void setMessage(BaseMessage message) {
        if (message.getParentMessageId() == 0) {
            if (messageAdapter != null) {
                messageAdapter.addMessage(message);
                checkSmartReply(message);
                markMessageAsRead(message);
                if ((messageAdapter.getItemCount() - 1) - ((LinearLayoutManager) rvChatListView.getLayoutManager()).findLastVisibleItemPosition() < 5)
                    scrollToBottom();
            } else {
                messageList.add(message);
                initMessageAdapter(messageList);
            }
        }
    }

    private void checkSmartReply(BaseMessage lastMessage) {

        if (lastMessage != null && lastMessage.getSender().getId() != (loggedInUser.getId())) {
            getSmartReplyList(lastMessage);
        }
    }

    /**
     * This method is used to display typing status to user.
     *
     * @param show is boolean, If it is true then <b>is Typing</b> will be shown to user
     *             If it is false then it will show user status i.e online or offline.
     */
    private void typingIndicator(TypingIndicator typingIndicator, boolean show) {
        if (messageAdapter != null) {
            if (show) {
                if (typingIndicator.getReceiverType().equals(ChatConstants.RECEIVER_TYPE_USER))
                    tvStatus.setText("is Typing...");
                else
                    tvStatus.setText(typingIndicator.getSender().getName() + " is Typing...");
            } else {
                if (typingIndicator.getReceiverType().equals(ChatConstants.RECEIVER_TYPE_USER))
                    tvStatus.setText(status);
                else
                    tvStatus.setText(memberNames);
            }

        }
    }

    /**
     * This method is used to remove message listener
     *
     * @see MyApplication#removeMessageListener(String)
     */
    private void removeMessageListener() {
        MyApplication.removeMessageListener(TAG);
    }

    /**
     * This method is used to remove user presence listener
     *
     * @see MyApplication#removeUserListener(String)
     */

    /**
     * This method is used to get real time user status i.e user is online or offline.
     *

     */
    private void addUserListener() {
        if (type.equals(ChatConstants.RECEIVER_TYPE_USER)) {
            MyApplication.addUserListener(TAG, new MyApplication.UserListener() {
                @Override
                public void onUserOnline(ServiesUser user) {
                    Log.d(TAG, "onUserOnline: " + user.toString());
                    if (user.getId() == (Id)) {
                        status = ChatConstants.USER_STATUS_ONLINE;
                        tvStatus.setText(user.getCountry().getName());
                        tvStatus.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                }

                @Override
                public void onUserOffline(ServiesUser user) {
                    Log.d(TAG, "onUserOffline: " + user.toString());
                    if (user.getId() == (Id)) {
                        if (Utils.isDarkMode(getContext()))
                            tvStatus.setTextColor(getResources().getColor(R.color.textColorWhite));
                        else
                            tvStatus.setTextColor(getResources().getColor(android.R.color.black));
                        tvStatus.setText(user.getCountry().getName());
                        status = ChatConstants.USER_STATUS_OFFLINE;
                    }
                }
            });
        }
    }
    /**
     * This method is used to remove user presence listener
     *

     */
    private void removeUserListener() {
        MyApplication.removeUserListener(TAG);
    }

    /**
     * This method is used to get details of reciever.
     *

     */
    private void getUser() {

       //TODO POJO USER

        /*
        if (getActivity() != null) {
            if (user.isBlockedByMe()) {
                isBlockedByMe = true;
                rvSmartReply.setVisibility(GONE);
                toolbar.setSelected(false);
                blockedUserName.setText("You've blocked " + user.getName());
                blockUserLayout.setVisibility(View.VISIBLE);
            } else {
                isBlockedByMe = false;
                blockUserLayout.setVisibility(GONE);
                avatarUrl = user.getThumb_image();
                if (user.getStatus().equals(ChatConstants.USER_STATUS_ONLINE)) {
                    tvStatus.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                }
                status = user.getStatus().toString();
                setAvatar();
                tvStatus.setText(status);

            }
            name = user.getName();
            tvName.setText(name);
            Log.d(TAG, "onSuccess: " + user.toString());
        }

         */
        //
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
        if (messageAdapter!=null)
            messageAdapter.stopPlayingAudio();
        removeMessageListener();
        removeUserListener();
        //removeGroupListener();TODO
        sendTypingIndicator(true);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        rvChatListView.removeItemDecoration(stickyHeaderDecoration);
        messageAdapter = null;
        messagesRequest = null;
        //checkOnGoingCall(); TODO
        fetchMessage();
        addMessageListener();

        if (messageActionFragment!=null)
            messageActionFragment.dismiss();

        if (type != null) {
            if (type.equals(ChatConstants.RECEIVER_TYPE_USER)) {
                addUserListener();
                tvStatus.setText(status);
                new Thread(this::getUser).start();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.iv_message_close) {
            if (messageAdapter != null) {
                messageAdapter.clearLongClickSelectedItem();
                messageAdapter.notifyDataSetChanged();
            }
            isEdit = false;
            baseMessage = null;
            editMessageLayout.setVisibility(GONE);
        }
        else if (id == R.id.iv_reply_close) {
            if (messageAdapter!=null) {
                messageAdapter.clearLongClickSelectedItem();
                messageAdapter.notifyDataSetChanged();
            }
            isReply = false;
            baseMessage = null;
            replyMessageLayout.setVisibility(GONE);
        }
        else if (id == R.id.btn_Unblock_user) {
            unblockUser();
        }
        else if (id == R.id.chatList_toolbar) {
            if (type.equals(ChatConstants.RECEIVER_TYPE_USER)) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra(StringContract.IntentStrings.UID, Id);
                intent.putExtra(StringContract.IntentStrings.NAME, name);
                intent.putExtra(StringContract.IntentStrings.AVATAR, avatarUrl);
                intent.putExtra(StringContract.IntentStrings.IS_BLOCKED_BY_ME, isBlockedByMe);
                intent.putExtra(StringContract.IntentStrings.STATUS, status);
                intent.putExtra(StringContract.IntentStrings.TYPE, type);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra(StringContract.IntentStrings.GUID, Id);
                intent.putExtra(StringContract.IntentStrings.NAME, name);
                intent.putExtra(StringContract.IntentStrings.AVATAR, avatarUrl);
                intent.putExtra(StringContract.IntentStrings.TYPE, type);
                intent.putExtra(StringContract.IntentStrings.GROUP_TYPE,groupType);
                intent.putExtra(StringContract.IntentStrings.MEMBER_SCOPE, loggedInUserScope);
                intent.putExtra(StringContract.IntentStrings.GROUP_OWNER, groupOwnerId);
                intent.putExtra(StringContract.IntentStrings.MEMBER_COUNT,memberCount);
                intent.putExtra(StringContract.IntentStrings.GROUP_DESC,groupDesc);
                intent.putExtra(StringContract.IntentStrings.GROUP_PASSWORD,groupPassword);
                startActivity(intent);
            }
        }
    }

    private void startForwardMessageActivity() {
        Intent intent = new Intent(getContext(), ProfileActivity.class);//ToDO FORWARD ACTIVIRT
        if (baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_TEXT)){
            intent.putExtra(ChatConstants.MESSAGE_TYPE_TEXT, baseMessage.getMessage().getText());
            intent.putExtra(StringContract.IntentStrings.TYPE, ChatConstants.MESSAGE_TYPE_TEXT);
        } else if(baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_IMAGE) ||
                baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_AUDIO) ||
                baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_VIDEO) ||
                baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_FILE)) {
            intent.putExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_NAME, baseMessage.getMessage().getAttachment().getFileName());
            intent.putExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_URL, baseMessage.getMessage().getAttachment().getFileUrl());
            intent.putExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_MIME_TYPE, baseMessage.getMessage().getAttachment().getFileMimeType());
            intent.putExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_EXTENSION, baseMessage.getMessage().getAttachment().getFileExtension());
            intent.putExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_SIZE, baseMessage.getMessage().getAttachment().getFileSize());
            intent.putExtra(StringContract.IntentStrings.TYPE,baseMessage.getType());
        }
        startActivity(intent);
    }

    private void shareMessage() {
        if (baseMessage!=null && baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_TEXT)) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TITLE,getResources().getString(R.string.app_name));
            shareIntent.putExtra(Intent.EXTRA_TEXT, baseMessage.getMessage().getText());
            shareIntent.setType("text/plain");
            Intent intent = Intent.createChooser(shareIntent, getResources().getString(R.string.share_message));
            startActivity(intent);
        } else if (baseMessage!=null && baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_IMAGE)) {
            String mediaName = baseMessage.getMessage().getAttachment().getFileName();
            Glide.with(context).asBitmap().load(baseMessage.getMessage().getAttachment().getFileUrl()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), resource, mediaName, null);
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
                    shareIntent.setType(baseMessage.getMessage().getAttachment().getFileMimeType());
                    Intent intent = Intent.createChooser(shareIntent, getResources().getString(R.string.share_message));
                    startActivity(intent);
                }
            });
        }
    }
    private void startThreadActivity() {
        Intent intent = new Intent(getContext(), ChatActivity.class);
        intent.putExtra(StringContract.IntentStrings.CONVERSATION_NAME,name);
        intent.putExtra(StringContract.IntentStrings.NAME,baseMessage.getSender().getName());
        intent.putExtra(StringContract.IntentStrings.UID,baseMessage.getSender().getName());
        intent.putExtra(StringContract.IntentStrings.AVATAR,baseMessage.getSender().getImage());
        intent.putExtra(StringContract.IntentStrings.PARENT_ID,baseMessage.getId());
        intent.putExtra(StringContract.IntentStrings.MESSAGE_TYPE,baseMessage.getType());

        intent.putExtra(StringContract.IntentStrings.SENTAT,baseMessage.getSentAt());
        if (baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_TEXT))
            intent.putExtra(StringContract.IntentStrings.TEXTMESSAGE,baseMessage.getMessage().getText());
        else {
            intent.putExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_NAME,baseMessage.getMessage().getAttachment().getFileName());
            intent.putExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_EXTENSION,baseMessage.getMessage().getAttachment().getFileExtension());
            intent.putExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_URL,baseMessage.getMessage().getAttachment().getFileUrl());
            intent.putExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_SIZE,baseMessage.getMessage().getAttachment().getFileSize());
            intent.putExtra(StringContract.IntentStrings.MESSAGE_TYPE_IMAGE_MIME_TYPE,baseMessage.getMessage().getAttachment().getFileMimeType());
        }
        intent.putExtra(StringContract.IntentStrings.TYPE,type);
        if (type.equals(ChatConstants.CONVERSATION_TYPE_GROUP)) {
            intent.putExtra(StringContract.IntentStrings.GUID,Id);
        }
        else {
            intent.putExtra(StringContract.IntentStrings.UID,Id);
        }
        startActivity(intent);
    }

    @Override
    public void setLongMessageClick(List<BaseMessage> baseMessagesList) {
        Log.e(TAG, "setLongMessageClick: " + baseMessagesList);
        isReply = false;
        isEdit = false;
        messageActionFragment = new MessageActionFragment();
        replyMessageLayout.setVisibility(GONE);
        editMessageLayout.setVisibility(GONE);
        boolean shareVisible = true;
        boolean copyVisible = true;
        boolean threadVisible = true;
        boolean replyVisible = true;
        boolean editVisible = true;
        boolean deleteVisible = true;
        boolean forwardVisible = true;
        List<BaseMessage> textMessageList = new ArrayList<>();
        List<BaseMessage> mediaMessageList = new ArrayList<>();
        for (BaseMessage baseMessage : baseMessagesList) {
            if (baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_TEXT)) {
                textMessageList.add(baseMessage);
            } else {
                mediaMessageList.add(baseMessage);
            }
        }
        if (textMessageList.size() == 1) {
            BaseMessage basemessage = textMessageList.get(0);
            if (basemessage != null && basemessage.getSender() != null) {
                if (!(basemessage instanceof Action) && basemessage.getDeletedAt() == 0) {
                    baseMessage = basemessage;
                    threadVisible = true;//TODO
                    if (basemessage.getSender().getId() == ServiesSharedPrefManager.getInstance(getContext()).getUser().getId()) {
                        deleteVisible = true;
                        editVisible = true;
                        forwardVisible = true;
                    } else {
                        editVisible = false;
                        forwardVisible = true;
                        if (loggedInUserScope!=null && (loggedInUserScope.equals(ChatConstants.SCOPE_ADMIN) || loggedInUserScope.equals(ChatConstants.SCOPE_MODERATOR))) {
                            deleteVisible = true;
                        } else {
                            deleteVisible = false;
                        }
                    }
                }
            }
        }

        if (mediaMessageList.size() == 1) {
            BaseMessage basemessage = mediaMessageList.get(0);
            if (basemessage != null && basemessage.getSender() != null) {
                if (!(basemessage instanceof Action) && basemessage.getDeletedAt() == 0) {
                    baseMessage = basemessage;
                    threadVisible = true;//TODO
                    copyVisible = false;
                    if (basemessage.getSender().getId() == ServiesSharedPrefManager.getInstance(getContext()).getUser().getId()) {
                        deleteVisible = true;
                        editVisible = false;
                        forwardVisible = true;
                    } else {
                        if (loggedInUserScope!=null && (loggedInUserScope.equals(ChatConstants.SCOPE_ADMIN) || loggedInUserScope.equals(ChatConstants.SCOPE_MODERATOR))){
                            deleteVisible = true;
                        } else {
                            deleteVisible = false;
                        }
                        forwardVisible = true;
                        editVisible = false;
                    }
                }
            }
        }
        baseMessages = baseMessagesList;
        Bundle bundle = new Bundle();
        bundle.putBoolean("copyVisible",copyVisible);
        bundle.putBoolean("threadVisible",threadVisible);
        bundle.putBoolean("shareVisible",shareVisible);
        bundle.putBoolean("editVisible",editVisible);
        bundle.putBoolean("deleteVisible",deleteVisible);
        bundle.putBoolean("replyVisible",replyVisible);
        bundle.putBoolean("forwardVisible",forwardVisible);
        bundle.putString("type", ChatActivity.class.getName());
        messageActionFragment.setArguments(bundle);
        messageActionFragment.show(getFragmentManager(),messageActionFragment.getTag());
        messageActionFragment.setMessageActionListener(new MessageActionFragment.MessageActionListener() {
            @Override
            public void onThreadMessageClick() {
                startThreadActivity();
            }

            @Override
            public void onEditMessageClick() {
                if (baseMessage!=null&&baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_TEXT)) {
                    isEdit = true;
                    isReply = false;
                    tvMessageTitle.setText(getResources().getString(R.string.edit_message));
                    tvMessageSubTitle.setText(baseMessage.getMessage().getText());
                    composeBox.ivMic.setVisibility(GONE);
                    composeBox.ivSend.setVisibility(View.VISIBLE);
                    editMessageLayout.setVisibility(View.VISIBLE);
                    composeBox.etComposeBox.setText(baseMessage.getMessage().getText());
                    if (messageAdapter != null) {
                        messageAdapter.setSelectedMessage(baseMessage.getId());
                        messageAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onReplyMessageClick() {
                replyMessage();
            }

            @Override
            public void onForwardMessageClick() {
                startForwardMessageActivity();
            }

            @Override
            public void onDeleteMessageClick() {
                deleteMessage(baseMessage);
                if (messageAdapter != null) {
                    messageAdapter.clearLongClickSelectedItem();
                    messageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCopyMessageClick() {
                String message = "";
                for (BaseMessage bMessage : baseMessages) {
                    if (bMessage.getDeletedAt() == 0 && bMessage instanceof Message) {
                        message = message + "[" + Utils.getLastMessageDate(bMessage.getSentAt()) + "] " + bMessage.getSender().getName() + ": " + bMessage.getMessage().getText();
                    }
                }
                Log.e(TAG, "onCopy: " + message);
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("MessageAdapter", message);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(context, getResources().getString(R.string.text_copied_clipboard), Toast.LENGTH_LONG).show();
                if (messageAdapter != null) {
                    messageAdapter.clearLongClickSelectedItem();
                    messageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onShareMessageClick() { shareMessage(); }
        });
    }


    private void replyMessage() {
        if (baseMessage != null) {
            isReply = true;
            replyTitle.setText(baseMessage.getSender().getName());
            replyMedia.setVisibility(View.VISIBLE);
            if (baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_TEXT)) {
                replyMessage.setText((baseMessage.getMessage().getText()));//TODO TEXTMESSAGE
                replyMedia.setVisibility(GONE);
            } else if (baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_IMAGE)) {
                replyMessage.setText(getResources().getString(R.string.shared_a_image));
                Glide.with(context).load(baseMessage.getMessage().getAttachment().getFileUrl()).into(replyMedia);
            } else if (baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_AUDIO)) {
                String messageStr = String.format(getResources().getString(R.string.shared_a_audio),
                        Utils.getFileSize(baseMessage.getMessage().getAttachment().getFileSize()));
                replyMessage.setText(messageStr);
                replyMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_library_music_24dp, 0, 0, 0);
            } else if (baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_VIDEO)) {
                replyMessage.setText(getResources().getString(R.string.shared_a_video));
                Glide.with(context).load(baseMessage.getMessage().getAttachment().getFileUrl()).into(replyMedia);
            } else if (baseMessage.getType().equals(ChatConstants.MESSAGE_TYPE_FILE)) {
                String messageStr = String.format(getResources().getString(R.string.shared_a_file),
                        Utils.getFileSize(baseMessage.getMessage().getAttachment().getFileSize()));
                replyMessage.setText(messageStr);
                replyMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_insert_drive_file_black_24dp, 0, 0, 0);
            }
            composeBox.ivMic.setVisibility(GONE);
            composeBox.ivSend.setVisibility(View.VISIBLE);
            replyMessageLayout.setVisibility(View.VISIBLE);
            if (messageAdapter != null) {
                messageAdapter.setSelectedMessage(baseMessage.getId());
                messageAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        if (messageAdapter!=null)
            messageAdapter.clearLongClickSelectedItem();
        dialog.dismiss();
    }
    private ArrayList<AgentAvaliability> agentAvaliabilityList;
    private ArrayList<AgentAvaliability.DayAvaliabilty> dayAvaliabilties;
    private void pojoAgentInfo() {
        agentAvaliabilityList = new ArrayList<>();
        dayAvaliabilties = new ArrayList<>();
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "6:30م - 7:30م"  , "18:30:00", 1));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "7:30م - 8:30م" , "19:30:00",1));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "8:30م - 9:30م" ,"20:30:00", 1));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "9:30م - 10:30م" ,"21:30:00", 2));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "10:30م - 11:30م" ,"22:30:00", 2));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "11:30م - 12:30ص" , "23:30:00",2));

        agentAvaliabilityList.add(new AgentAvaliability( 1 , "16-9-2020" , "لسبا",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 2 ,"17-9-2020","لسبا",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 3 , "18-9-2020","لسبا",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 4 , "19-9-2020","لسبا",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 5 , "20-9-2020","لسبا",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 6 ,"21-9-2020","لسبا",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 7 ,"22-9-2020","لسبا",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 8 ,"23-9-2020","لسبا",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 9 ,"24-9-2020","لسبا",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 10 ,"25-9-2020","لسبا",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 11,"26-9-2020","لسبا",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 12 ,"27-9-2020","لسبا",dayAvaliabilties));
    }
}