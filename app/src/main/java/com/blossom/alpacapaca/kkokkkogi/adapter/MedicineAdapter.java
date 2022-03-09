package com.blossom.alpacapaca.kkokkkogi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blossom.alpacapaca.kkokkkogi.Model.Medicine;
import com.blossom.alpacapaca.kkokkkogi.R;

import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<Medicine> medicines = new ArrayList<>();

    public MedicineAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public MedicineAdapter(Context mContext, ArrayList<Medicine> medicines) {
        this.mContext = mContext;
        this.medicines = medicines;
    }

    @NonNull
    @Override
    public MedicineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.medicine_item, parent, false);
        return new MedicineAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineAdapter.ViewHolder holder, int position) {
        Medicine medicine = this.medicines.get(position);
        holder.medicine_name.setText(medicine.getName());
        holder.setItem(medicine);
        //holder.medicine_name.setback
    }

    @Override
    public int getItemCount() {
        if (medicines != null) {
            return medicines.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView medicine_name;

        public ViewHolder(View itemView) {
            super(itemView);
            medicine_name = itemView.findViewById(R.id.medicine_item_name_box);
            //profile_image = itemView.findViewById(R.id.imageView2);
        }

        public void setItem(Medicine medicine) {
            medicine_name.setText(medicine.getName());
        }
    }

    public void addItem(Medicine medicine) {
        this.medicines.add(medicine);
    }
    public void setMedicines(ArrayList<Medicine> medicine) {
        this.medicines = medicine;
    }
    public Medicine getItem(int position) {
        return medicines.get(position);
    }
    public void setItem(int position, Medicine medicine) {
        this.medicines.set(position, medicine);
    }
}