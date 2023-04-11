package com.rovaindu.homeservice.utils.views.chat;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.RecyclerView;

import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.adapter.chat.SmartReplyViewModel;
import com.rovaindu.homeservice.interfaces.chat.ClickListener;
import com.rovaindu.homeservice.interfaces.chat.OnItemClickListener;
import com.rovaindu.homeservice.interfaces.chat.RecyclerTouchListener;

import java.util.List;





public class SmartReplyList extends RecyclerView {

    private Context context;

    private SmartReplyViewModel smartReplyViewModel;

    public SmartReplyList(@NonNull Context context) {
        super(context);
        setContext(context);
    }

    public SmartReplyList(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setContext(context);
        getAttributes(attrs);
        setSmartReplyViewModel();
    }

    public SmartReplyList(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setContext(context);
        getAttributes(attrs);
        setSmartReplyViewModel();
    }

    private void getAttributes(AttributeSet attributeSet) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.SmartReplyList, 0, 0);
    }


    private void setContext(Context context){
        this.context=context;
    }

    private void setSmartReplyViewModel(){
        if (smartReplyViewModel==null){
            smartReplyViewModel=new SmartReplyViewModel(context,this);
        }
    }

    /**
     * This method is used to set list of replies in SmartReplyComponent.
     * @param replyList is object of List<String> . It is list of smart replies.
     */
    public void setSmartReplyList(List<String> replyList){
        if (smartReplyViewModel!=null){
            smartReplyViewModel.setSmartReplyList(replyList);
        }
    }

    /**
     * This method is used to give events on click of item in given smart reply list.
     * @param itemClickListener
     */
    public void setItemClickListener(final OnItemClickListener<String> itemClickListener){

        this.addOnItemTouchListener(new RecyclerTouchListener(context, this, new ClickListener() {
            @Override
            public void onClick(View var1, int var2) {
                String reply=(String) var1.getTag(R.string.replyTxt);
                if (itemClickListener!=null)
                    itemClickListener.OnItemClick(reply,var2);
                else
                    throw new NullPointerException(getResources().getString(R.string.smart_reply_itemclick_error) );
            }

            @Override
            public void onLongClick(View var1, int var2) {
                String reply=(String) var1.getTag(R.string.replyTxt);
                 if (itemClickListener!=null)
                    itemClickListener.OnItemLongClick(reply,var2);
                 else
                    throw new NullPointerException(getResources().getString(R.string.smart_reply_itemclick_error) );
            }
        }));
    }

}
