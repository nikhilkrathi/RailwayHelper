package com.nikhilkrathi.railwayhelper;
import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nikhilkrathi.railwayhelper.database.helper.DatabaseHelper;
import com.nikhilkrathi.railwayhelper.database.model.CheckAvailabilityTrainDetails;

import java.util.List;

public class CheckAvailabilityAdapter extends RecyclerView.Adapter<CheckAvailabilityAdapter.MyViewHolder>{

    private List<CheckAvailabilityTrainDetails> trainDetailsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
        Button b1, b2, b3;
        DatabaseHelper databaseHelper;


        public MyViewHolder(final View view) {
            super(view);
            tv1 = (TextView) view.findViewById(R.id.ca_trainName);
            tv2 = (TextView) view.findViewById(R.id.ca_trainID);
            tv3 = (TextView) view.findViewById(R.id.ca_sourceStn);
            tv4 = (TextView) view.findViewById(R.id.ca_destinationStn);
            tv5 = (TextView) view.findViewById(R.id.ca_arrivalTime);
            tv6 = (TextView) view.findViewById(R.id.ca_departureTime);
            tv7 = (TextView) view.findViewById(R.id.ca_trainType);
            b1 = (Button) view.findViewById(R.id.ca_bookTicketBtn);
            b2 = (Button) view.findViewById(R.id.ca_checkStatusBtn);
            b3 = (Button) view.findViewById(R.id.ca_getFareBtn);
            databaseHelper = new DatabaseHelper(view.getContext());
        }
    }

    public CheckAvailabilityAdapter(List<CheckAvailabilityTrainDetails> trainDetailsList) {
        this.trainDetailsList = trainDetailsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.check_availability_single_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final CheckAvailabilityTrainDetails checkAvailabilityTrainDetails = trainDetailsList.get(position);
        holder.tv1.setText(checkAvailabilityTrainDetails.getTrainName());
        holder.tv2.setText("   " + "(" + String.valueOf(checkAvailabilityTrainDetails.getTrainID()) + ")");
        holder.tv3.setText(checkAvailabilityTrainDetails.getSourceID());
        holder.tv4.setText(checkAvailabilityTrainDetails.getDestinationID());
        holder.tv5.setText(checkAvailabilityTrainDetails.getArrivalTime());
        holder.tv6.setText(checkAvailabilityTrainDetails.getDepartureTime());
        holder.tv7.setText(checkAvailabilityTrainDetails.getTrainType());

        View.OnClickListener bookTicket = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Open field to enter passenger details " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), PassengerDetailsActivity.class);
                intent.putExtra("TrainName", checkAvailabilityTrainDetails.getTrainName());
                intent.putExtra("TrainNo", checkAvailabilityTrainDetails.getTrainID());
                intent.putExtra("FromID", checkAvailabilityTrainDetails.getSourceID());
                intent.putExtra("ToID", checkAvailabilityTrainDetails.getDestinationID());
                intent.putExtra("Date", checkAvailabilityTrainDetails.getDate());
                v.getContext().startActivity(intent);
            }
        };
        holder.b1.setOnClickListener(bookTicket);

        final int asI = holder.databaseHelper.getAvailableSeats(checkAvailabilityTrainDetails.getTrainID(), checkAvailabilityTrainDetails.getDate(), "I");
        final int asII = holder.databaseHelper.getAvailableSeats(checkAvailabilityTrainDetails.getTrainID(), checkAvailabilityTrainDetails.getDate(), "II");
        final int asIII = holder.databaseHelper.getAvailableSeats(checkAvailabilityTrainDetails.getTrainID(), checkAvailabilityTrainDetails.getDate(), "III");
        final int wsI = holder.databaseHelper.getWaitingSeats(checkAvailabilityTrainDetails.getTrainID(), checkAvailabilityTrainDetails.getDate(), "I");
        final int wsII = holder.databaseHelper.getWaitingSeats(checkAvailabilityTrainDetails.getTrainID(), checkAvailabilityTrainDetails.getDate(), "II");
        final int wsIII = holder.databaseHelper.getWaitingSeats(checkAvailabilityTrainDetails.getTrainID(), checkAvailabilityTrainDetails.getDate(), "III");
        final int bsI = holder.databaseHelper.getBookedSeats(checkAvailabilityTrainDetails.getTrainID(), checkAvailabilityTrainDetails.getDate(), "I");
        final int bsII = holder.databaseHelper.getBookedSeats(checkAvailabilityTrainDetails.getTrainID(), checkAvailabilityTrainDetails.getDate(), "II");
        final int bsIII = holder.databaseHelper.getBookedSeats(checkAvailabilityTrainDetails.getTrainID(), checkAvailabilityTrainDetails.getDate(), "III");
        int tsI = holder.databaseHelper.getTotalSeats(checkAvailabilityTrainDetails.getTrainID(), "I");
        int tsII = holder.databaseHelper.getTotalSeats(checkAvailabilityTrainDetails.getTrainID(), "II");
        int tsIII = holder.databaseHelper.getTotalSeats(checkAvailabilityTrainDetails.getTrainID(), "III");

        View.OnClickListener checkStatus = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Route of train " + position, Toast.LENGTH_SHORT).show();
                String statusI = null;
                if (asI > 0) {
                    statusI = "AVAILABLE " + asI;
                } else if (asI == 0 && wsI <= 3) {
                    statusI = "WAITING " + (wsI + 1);
                } else if (bsI == asI + 3) {
                    statusI = "REGRET";
                }

                String statusII = null;
                if (asII > 0) {
                    statusII = "AVAILABLE " + asII;
                } else if (asII == 0 && wsII <= 3) {
                    statusII = "WAITING " + (wsII + 1);
                } else if (bsII == asII + 3) {
                    statusII = "REGRET";
                }

                String statusIII = null;
                if (asIII > 0) {
                    statusIII = "AVAILABLE " + asIII;
                } else if (asIII == 0 && wsIII <= 3) {
                    statusIII = "WAITING " + (wsIII + 1);
                } else if (bsIII == asIII + 3) {
                    statusIII = "REGRET";
                }

                CheckStatusDialog checkStatusDialog = new CheckStatusDialog(v.getContext(), checkAvailabilityTrainDetails.getTrainID(), checkAvailabilityTrainDetails.getTrainName(), checkAvailabilityTrainDetails.getDate(), statusI, statusII, statusIII);
                checkStatusDialog.setTitle("Availability");
                checkStatusDialog.show();
                Window window = checkStatusDialog.getWindow();
                window.setLayout(ActionBarOverlayLayout.LayoutParams.MATCH_PARENT, ActionBarOverlayLayout.LayoutParams.WRAP_CONTENT);

            }
        };
        holder.b2.setOnClickListener(checkStatus);

        int sourceDist = holder.databaseHelper.getDistance(checkAvailabilityTrainDetails.getSourceID(), checkAvailabilityTrainDetails.getTrainID());
        int destinationDist = holder.databaseHelper.getDistance(checkAvailabilityTrainDetails.getDestinationID(), checkAvailabilityTrainDetails.getTrainID());
        final int distance = destinationDist - sourceDist;
        double rateClass1 = Double.parseDouble(holder.databaseHelper.getFareClass1(checkAvailabilityTrainDetails.getTrainID()));
        double rateClass2 = Double.parseDouble(holder.databaseHelper.getFareClass2(checkAvailabilityTrainDetails.getTrainID()));
        double rateClass3 = Double.parseDouble(holder.databaseHelper.getFareClass3(checkAvailabilityTrainDetails.getTrainID()));
        final double fareClass1 = rateClass1 * distance;
        final double fareClass2 = rateClass2 * distance;
        final double fareClass3 = rateClass3 * distance;

        View.OnClickListener getFare = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Fare of train " + position, Toast.LENGTH_SHORT).show();
                FareDialog dialogFare = new FareDialog(v.getContext(), distance, fareClass1, fareClass2, fareClass3, checkAvailabilityTrainDetails.getTrainName(), checkAvailabilityTrainDetails.getTrainID());
                dialogFare.setTitle("Fare Details");
                dialogFare.show();
                Window window = dialogFare.getWindow();
                window.setLayout(ActionBarOverlayLayout.LayoutParams.MATCH_PARENT, ActionBarOverlayLayout.LayoutParams.WRAP_CONTENT);
            }
        };
        holder.b3.setOnClickListener(getFare);

    }

    @Override
    public int getItemCount() {
        return trainDetailsList.size();
    }

    public void clear() {
        int size = this.trainDetailsList.size();
        this.trainDetailsList.clear();
        notifyItemRangeRemoved(0, size);
    }


}