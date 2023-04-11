package com.rovaindu.homeservice.interfaces;



import com.rovaindu.homeservice.retrofit.models.ServiesAgent;

import java.io.Serializable;

public interface OnTrackerClickListener extends Serializable {
    void onTrackerClicked(ServiesAgent agent, int position, int parentID);
}
