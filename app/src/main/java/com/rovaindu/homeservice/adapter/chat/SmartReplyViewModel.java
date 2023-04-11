package com.rovaindu.homeservice.adapter.chat;

import android.content.Context;


import com.rovaindu.homeservice.utils.views.chat.SmartReplyList;

import java.util.List;



public class SmartReplyViewModel {

    private static final String TAG = "SmartReplyViewModel";

    private Context context;

    private SmartReplyListAdapter smartReplyListAdapter;

    private SmartReplyList smartReplyList;

    public SmartReplyViewModel(Context context, SmartReplyList smartReplyList) {
        this.context = context;
        this.smartReplyList = smartReplyList;
         setSmartReplyAdapter(smartReplyList);
    }

    private void setSmartReplyAdapter(SmartReplyList smartReplyList) {
        smartReplyListAdapter=new SmartReplyListAdapter(context);
        smartReplyList.setAdapter(smartReplyListAdapter);
    }

    private SmartReplyListAdapter getAdapter(){
        if (smartReplyListAdapter==null){
            smartReplyListAdapter=new SmartReplyListAdapter(context);
        }
        return smartReplyListAdapter;
    }

    public void setSmartReplyList(List<String> replyList){
        getAdapter().updateList(replyList);
    }


}
