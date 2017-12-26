package com.nikhilkrathi.railwayhelper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikhilkrathi.railwayhelper.database.model.CheckAvailabilityTrainDetails;
import com.nikhilkrathi.railwayhelper.database.model.PassengerDetails;

import java.util.List;

public class PassengerDetailsAdapter extends RecyclerView.Adapter<PassengerDetailsAdapter.MyViewHolder> {

    private List<PassengerDetails> passengerDetailsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2, tv3, tv4;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv1 = (TextView)itemView.findViewById(R.id.pd_id);
            tv2 = (TextView)itemView.findViewById(R.id.pd_name);
            tv3 = (TextView)itemView.findViewById(R.id.pd_age);
            tv4 = (TextView)itemView.findViewById(R.id.pd_gender);
        }
    }

    public PassengerDetailsAdapter(List<PassengerDetails> passengerDetailsList) {
        this.passengerDetailsList = passengerDetailsList;
    }

    @Override
    public PassengerDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.passenger_details_single_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PassengerDetailsAdapter.MyViewHolder holder, int position) {
        final PassengerDetails passengerDetails = passengerDetailsList.get(position);
        holder.tv1.setText(position + 1 + ")");
        holder.tv2.setText(passengerDetails.getName());
        holder.tv3.setText(String.valueOf(passengerDetails.getAge()));
        holder.tv4.setText(passengerDetails.getGender().substring(0,1));
    }

    @Override
    public int getItemCount() {
        return passengerDetailsList.size();
    }

    public void clear() {
        int size = this.passengerDetailsList.size();
        this.passengerDetailsList.clear();
        notifyItemRangeRemoved(0, size);
    }

}