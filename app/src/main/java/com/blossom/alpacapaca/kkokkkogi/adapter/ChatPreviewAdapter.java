package com.blossom.alpacapaca.kkokkkogi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blossom.alpacapaca.kkokkkogi.Model.Chat;
import com.blossom.alpacapaca.kkokkkogi.Model.Ward;
import com.blossom.alpacapaca.kkokkkogi.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

// 모든 ChatPreview를 Ward로 바꿔볼게
//public class ChatPreviewAdapter extends RecyclerView.Adapter<ChatPreviewAdapter.ViewHolder>{
public class ChatPreviewAdapter extends RecyclerView.Adapter<ChatPreviewAdapter.ViewHolder>{

    private Context mContext;
    //private ArrayList<ChatPreview> chatPreviewArray = new ArrayList<>();
    private ArrayList<Ward> chatPreviewArray = new ArrayList<>();

    public ChatPreviewAdapter(Context mContext) { this.mContext = mContext; }
    //public ChatPreviewAdapter(Context mContext, ArrayList<ChatPreview> chatPreviewArray) {
    public ChatPreviewAdapter(Context mContext, ArrayList<Ward> chatPreviewArray) {
        this.mContext = mContext;
        this.chatPreviewArray = chatPreviewArray;
    }

    View rootView;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.chat_preview_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 뷰홀더가 재사용 될 때 자동으로 호출됨.
        // 뷰 객체는 기존의 것을 사용하고, 안의 데이터만 바꿔줍니다.
        //ChatPreview ward = chatPreviewArray.get(position);
        Ward ward = chatPreviewArray.get(position);
        //HashMap<String, Chat> hashMap = ward.getChats();

        holder.wardName.setText(ward.getNameForMe());
        if(ward.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.drawable.chicken_small);
        } else {
            Glide.with(mContext).load(ward.getImageURL()).into(holder.profile_image);
        }

        holder.chatContent.setText(ward.lastMessage());
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                        Intent intent = new Intent(mContext, ChatActivity.class);
//                        intent.putExtra("wardId", ward.getId());
//                        intent.putExtra("userId", userId);
//                        intent.putExtra("isWard", MainActivity.getIsWard());
//                        intent.putExtra("loginEmail", MainActivity.getLoginEmail());
//                        intent.putExtra("loginPassword", MainActivity.getLoginPassword());
//
//                        Log.d("WardAdapter", MainActivity.getIsWard());
//                        Log.d("WardAdapter", MainActivity.getLoginEmail());
//                        Log.d("WardAdapter", MainActivity.getLoginPassword());
//
//                        mContext.startActivity(intent);
//                
//            }
//        });

    }

    @Override
    public int getItemCount() {
        if(chatPreviewArray != null){
            return chatPreviewArray.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        public TextView wardName;
        public ImageView profile_image;
        public TextView chatContent;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            wardName = itemView.findViewById(R.id.ward_name);
            profile_image = itemView.findViewById(R.id.imageView2);
            cardView = itemView.findViewById(R.id.chat_preview_box);
            chatContent = itemView.findViewById(R.id.chat_content);
        }
        public void setItem(Ward item) {
            wardName.setText(item.getNameForMe());
        }
    }

//    public void addItem(ChatPreview item) {
//        chatPreviewArray.add(item);
//    }
//    public void setItem(int position, ChatPreview item) {
//        chatPreviewArray.set(position, item);
//    }
//    public void setChatPreviewArray(ArrayList<ChatPreview> chatPreviewArray) {
//        this.chatPreviewArray = chatPreviewArray;
//    }
//    public ChatPreview getItem(int position) {
//        return chatPreviewArray.get(position);
//    }

    public void addItem(Ward item) {
        chatPreviewArray.add(item);
    }
    public void setItem(int position, Ward item) {
        chatPreviewArray.set(position, item);
    }
    public void setChatPreviewArray(ArrayList<Ward> chatPreviewArray) {
        this.chatPreviewArray = chatPreviewArray;
    }
    public Ward getItem(int position) {
        return chatPreviewArray.get(position);
    }
}
