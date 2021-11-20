package com.blossom.alpacapaca.kkokkkogi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.blossom.alpacapaca.kkokkkogi.Model.Chat;
import com.blossom.alpacapaca.kkokkkogi.R;

import java.util.ArrayList;

// 일단 공용 말고 보호자용으로 생각하고 만들겠다.
// 보호자용 확정. 나중에 수정해주자
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MESSAGE_TYPE_RECEIVER = 0;
    public static final int MESSAGE_TYPE_SENDER = 1;

    private String senderId;    // 보호자
    private String receiverId;  // 피보호자

    String userid;
    String wardid;
    String isWard;

    private Context mContext;
    private ArrayList<Chat> chats = new ArrayList<>();
    private String imageUrl;

    Intent intent;

    //public MessageAdapter(Context mContext) {this.mContext = mContext;}
    public MessageAdapter(Context mContext, ArrayList<Chat> chats, String imageUrl) {
        this.mContext = mContext;
        this.chats = chats;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_TYPE_SENDER) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.chat_box_sender, parent, false);
            return new ViewHolder(itemView);
        }
        else {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.chat_box_receiver, parent, false);
            return new ViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chat chat = chats.get(position);
        holder.show_message.setText(chat.getMessage());
        if(chat.getIsSeen()) {
            holder.text_seen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if(chats != null){
            return chats.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message;
        public ImageView profile_image;
        public TextView text_seen;

        public ViewHolder(View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            text_seen = itemView.findViewById(R.id.text_seen);
        }
    }



    @Override
    public int getItemViewType (int position) {
        String idNowLogin = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (chats.get(position).getSender().equals(idNowLogin)) {
            return MESSAGE_TYPE_SENDER;
        } else {
            return MESSAGE_TYPE_RECEIVER;
        }

//        if(isWard == "false") {
//            if (chats.get(position).getSender().equals(userid)) {
//                return MESSAGE_TYPE_SENDER;
//            } else {
//                return MESSAGE_TYPE_RECEIVER;
//            }
//        } else {
//            if (chats.get(position).getSender().equals(wardid)) {
//                return MESSAGE_TYPE_SENDER;
//            } else {
//                return MESSAGE_TYPE_RECEIVER;
//            }
//        }

//        Log.d("MessageAdapter", userid);
//        if(chats.get(position).getSender() == null){
//            return 0;
//        }
//        else {
//            Log.d("MessageAdapter", "1. " + chats.toString());
//            Log.d("MessageAdapter", "2. " + chats.get(position).toString());
//            //Log.d("MessageAdapter", "3. " + chats.get(position).getSender().toString());
//
//            if (chats.get(position).getSender().equals(userid)) {
//                return MESSAGE_TYPE_SENDER;
//            } else {
//                return MESSAGE_TYPE_RECEIVER;
//            }
//        }
    }

    public void addItem(Chat item) {
        chats.add(item);
    }
    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }
    public Chat getItem(int position) {
        return chats.get(position);
    }
    public void setItem(int position, Chat item) {
        chats.set(position, item);
    }
}
