package com.blossom.alpacapaca.kkokkkogi.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.blossom.alpacapaca.kkokkkogi.ChatActivity;
import com.blossom.alpacapaca.kkokkkogi.useractivity.MainActivity;
import com.blossom.alpacapaca.kkokkkogi.Model.Medicine;
import com.blossom.alpacapaca.kkokkkogi.Model.Ward;
import com.blossom.alpacapaca.kkokkkogi.R;

import java.util.ArrayList;

public class WardAdapter extends RecyclerView.Adapter<WardAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<Ward> wards = new ArrayList<>();

    String userId;

    public WardAdapter (Context mContext) {this.mContext = mContext;userId = MainActivity.getLoginUserId();}
    public WardAdapter (Context mContext, ArrayList<Ward> wards) {
        this.mContext = mContext;
        this.wards = wards;
        userId = MainActivity.getLoginUserId();
    }

    // 약을 관리하기 위한 부분
    // 싹 갈아 엎어야 할 수도? 얘 자체가 받는 부분이 될 수는 없는걸까
//    private LinearLayoutManager layoutManager;
//    private RecyclerView recyclerView;
    private MedicineAdapter medicineAdapter;
    private ArrayList<Medicine> mMedicine;

    View rootView;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View itemView = inflater.inflate(R.layout.ward_item, parent, false);

        //View itemView = LayoutInflater.from(mContext).inflate(R.layout.ward_item, parent, false);
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.ward_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 뷰홀더가 재사용 될 때 자동으로 호출됨.
        // 뷰 객체는 기존의 것을 사용하고, 안의 데이터만 바꿔줍니다.
        Ward ward = wards.get(position);
        // 프사 설정 넣어줄게
        // holder.setItem(ward); 이거 뺴도 되는거야?
        holder.wardName.setText(ward.getNameForMe());
        if(ward.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.drawable.chicken_small);
        } else {
            Glide.with(mContext).load(ward.getImageURL()).into(holder.profile_image);
        }
        holder.chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 위에거 안 먹히면
                //  MainActivity mainActivity = (MainActivity) mContext;
                //  String userId = mainActivity.getLoginUserId();
                //  메인 엑티비티 가서 static 빼주고, 위에거 적용
                //Intent intent2 = new Intent( , ChatActivity.class);
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("wardId", ward.getId());
                intent.putExtra("userId", userId);
                intent.putExtra("isWard", MainActivity.getIsWard());
                intent.putExtra("loginEmail", MainActivity.getLoginEmail());
                intent.putExtra("loginPassword", MainActivity.getLoginPassword());

                Log.d("WardAdapter", MainActivity.getIsWard());
                Log.d("WardAdapter", MainActivity.getLoginEmail());
                Log.d("WardAdapter", MainActivity.getLoginPassword());
//                Log.d("getLoginUserIdTest", "1. " + ward.getId());
//                Log.d("getLoginUserIdTest", "2. " + userId);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                // FLAG_ACTIVITY_REORDER_TO_FRONT: 어딘가에 있으면 재사용
                // FLAG_ACTIVITY_SINGLE_TOP: 최상위에 이미 같은거 있으면 재사용
                mContext.startActivity(intent);
            }
        });

        //리사이클 안에 리사이클
        ArrayList<Medicine> ma = ward.getMedicineArray();
        ma.clear();
        ward.addMedicine("1. 타이레놀");
        ward.addMedicine("2. 감기약");
        ward.addMedicine("3. 소화제");
        Log.d("WardAdapter", ma.toString());
        holder.recyclerView.setAdapter(new MedicineAdapter(mContext, ma));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        //

        holder.setItem(ward);
    }

    @Override
    public int getItemCount() {
        if(wards != null){
        return wards.size();}
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        public TextView wardName;
        public ImageView profile_image;

        // 약 화면용 - 중요
        public RecyclerView recyclerView;
        //public TextView medicineName;     // 이거 필요한거야?
        public Button add_medicine_button;
        public Button chatButton;
        // 리사이클러 뷰도 추가해야겠네

        public ViewHolder(View itemView) {
            super(itemView);

            wardName = itemView.findViewById(R.id.ward_name);
            profile_image = itemView.findViewById(R.id.imageView2);

            recyclerView = itemView.findViewById(R.id.wardManagementRecyclerView);
            //medicineName = itemView.findViewById(R.id.)

            add_medicine_button = itemView.findViewById(R.id.add_medicine_button);

            chatButton = itemView.findViewById(R.id.chat_button);
        }
        public void setItem(Ward item) {
            wardName.setText(item.getNameForMe());
        }
    }

    public void addItem(Ward item) {
        wards.add(item);
    }
    public void setWards(ArrayList<Ward> wards) {
        this.wards = wards;
    }
    // 오 신기..
    public Ward getItem(int position) {
        return wards.get(position);
    }
    public void setItem(int position, Ward item) {
        wards.set(position, item);
    }
}
