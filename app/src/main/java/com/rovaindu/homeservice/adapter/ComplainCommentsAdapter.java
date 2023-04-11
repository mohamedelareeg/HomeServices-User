package com.rovaindu.homeservice.adapter;


import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.model.ComplainComments;
import com.rovaindu.homeservice.retrofit.models.Complain;
import com.rovaindu.homeservice.utils.ExpandableTextView;
import com.rovaindu.homeservice.utils.FormatterUtil;
import com.rovaindu.homeservice.utils.GetTimeAgo;
import com.rovaindu.homeservice.utils.views.TextViewAr;


import java.util.List;



/**
 * Created by Mohamed El Sayed
 */
public class ComplainCommentsAdapter extends RecyclerView.Adapter<ComplainCommentsAdapter.ViewHolder> {
    public List<ComplainComments> commentsList;
    public Complain complain;
    public Context context;



    public ComplainCommentsAdapter(Context context , Complain complain , List<ComplainComments> commentsList  ){
        this.context = context;
        this.complain = complain;
        this.commentsList = commentsList;


    }

    @Override
    public ComplainCommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
        context = parent.getContext();

        return new ComplainCommentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ComplainCommentsAdapter.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        final int user_id = commentsList.get(position).getId();
        String userName = commentsList.get(position).getName();
        String userImage = commentsList.get(position).getThumb_image();
        holder.setUserData(userImage);
        long time = commentsList.get(position).getCreated_at();
        holder.setTime(time);
        holder.comment_like_count.setText(  "500 " + context.getResources().getString(R.string.likes));
        holder.comment_reply_count.setText("2500 " + context.getResources().getString(R.string.comment));

        String commentMessage = commentsList.get(position).getContent();
        holder.setComment_message(commentMessage);

        ComplainComments c = commentsList.get(position);
        holder.fillComment(userName, c, holder.commentTextView, holder.comment_time_stamp);
//        holder.ratingBar.setRating(commentsList.get(position).getRate());






    }


    @Override
    public int getItemCount() {

        if(commentsList != null) {

            return commentsList.size();

        } else {

            return 0;

        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextViewAr comment_like_count , comment_reply_count;
        private TextViewAr comment_message;
        private ImageView owner_image;
        private TextViewAr blogUserName;
        //private CircleImageView blogUserImage;
        private TextViewAr comment_time_stamp;
        private final ExpandableTextView commentTextView;
        private TextView show_reply;
        private TextView image_like;

        // reply
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            commentTextView = (ExpandableTextView) itemView.findViewById(R.id.commentText);
            image_like = mView.findViewById(R.id.comment_like);
            comment_like_count = itemView.findViewById(R.id.comment_like_count);
            comment_reply_count = itemView.findViewById(R.id.comment_reply_count);
            owner_image = itemView.findViewById(R.id.ImgLogo);
            //blogUserImage = mView.findViewById(R.id.Comments_image);
            blogUserName = mView.findViewById(R.id.Comments_username);
            comment_message = mView.findViewById(R.id.Comments_message);
            comment_time_stamp = mView.findViewById(R.id.comment_time_stamp);
            show_reply = mView.findViewById(R.id.comment_reply);

            //

        }
        private void fillComment(String userName, ComplainComments comment, ExpandableTextView commentTextView, TextView dateTextView) {
            Spannable contentString = new SpannableStringBuilder(userName + "   " + comment.getContent());
            contentString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.highlight_text)),
                    0, userName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            commentTextView.setText(contentString );

            CharSequence date = FormatterUtil.getRelativeTimeSpanString(context, comment.getCreated_at());
            dateTextView.setText(date);
        }

        public void setComment_message(String message){


            comment_message.setText(message);

        }

        public void setUserData(String name, String image){


            blogUserName.setText(name);

            if(image != null) {
                Glide.with(context).load(image).into(owner_image);
            }

        }

        public void setUserData(String image){
            if(image != null) {
                Glide.with(context).load(image).into(owner_image);
            }

        }
        public void setTime(long time) {

            GetTimeAgo getTimeAgo = new GetTimeAgo();

            long lastTime = time;

            String lastSeenTime = GetTimeAgo.getTimeAgo(lastTime, context);

            comment_time_stamp.setText(lastSeenTime);

        }





    }

}
