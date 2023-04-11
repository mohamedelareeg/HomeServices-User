package com.rovaindu.homeservice.interfaces.chat;


public abstract class OnItemClickListener<T> {

    public abstract void OnItemClick(T var, int position);

    public void OnItemLongClick(T var,int position) {

    }
}
