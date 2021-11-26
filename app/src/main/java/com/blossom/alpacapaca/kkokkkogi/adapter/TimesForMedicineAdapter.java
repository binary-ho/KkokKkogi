package com.blossom.alpacapaca.kkokkkogi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blossom.alpacapaca.kkokkkogi.Model.Medicine;
import com.blossom.alpacapaca.kkokkkogi.Model.TimeForMedicines;
import com.blossom.alpacapaca.kkokkkogi.R;

import java.util.ArrayList;

public class TimesForMedicineAdapter extends RecyclerView.Adapter<TimesForMedicineAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<TimeForMedicines> times = new ArrayList<>();

    public TimesForMedicineAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public TimesForMedicineAdapter(Context mContext, ArrayList<TimeForMedicines> times) {
        this.mContext = mContext;
        this.times = times;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.time_for_medicine_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeForMedicines timeForMedicines = this.times.get(position);
        holder.time.setText(timeForMedicines.getTime());
        holder.setItem(timeForMedicines);

        ArrayList<Medicine> medicines = new ArrayList<>();
        medicines.clear();
        medicines.add(new Medicine("타이레놀"));
    }

    @Override
    public int getItemCount() {
        if (times != null) {
            return times.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView time;


        public RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.time_for_medicine_text);
            //profile_image = itemView.findViewById(R.id.imageView2);
            recyclerView = itemView.findViewById(R.id.medicine_recycler_view);
        }

        public void setItem(TimeForMedicines timeForMedicines) {
            time.setText(timeForMedicines.getTime());
        }
    }

    public void addItem(TimeForMedicines timeForMedicines) {
        this.times.add(timeForMedicines);
    }
    public void setMedicines(ArrayList<TimeForMedicines> times) {
        this.times = times;
    }
    public TimeForMedicines getItem(int position) {
        return times.get(position);
    }
    public void setItem(int position, TimeForMedicines timeForMedicines) {
        this.times.set(position, timeForMedicines);
    }
}
