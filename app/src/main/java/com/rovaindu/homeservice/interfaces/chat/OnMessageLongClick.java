package com.rovaindu.homeservice.interfaces.chat;



import com.rovaindu.homeservice.model.chat.BaseMessage;

import java.util.List;

public interface OnMessageLongClick
{
    void setLongMessageClick(List<BaseMessage> baseMessage);
}
