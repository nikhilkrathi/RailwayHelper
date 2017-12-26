package com.nikhilkrathi.railwayhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nikhilkrathi.railwayhelper.database.helper.DatabaseHelper;
import com.nikhilkrathi.railwayhelper.database.model.BookedTicketDetails;
import com.nikhilkrathi.railwayhelper.database.model.CheckAvailabilityTrainDetails;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BookedHistoryAdapter extends RecyclerView.Adapter<BookedHistoryAdapter.MyViewHolder>{

    private List<BookedTicketDetails> ticketDetailsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv1, tv2, tv3, tv4, tv5, tv6;
        Button b1;
        CardView cv1;
        DatabaseHelper databaseHelper;

        public MyViewHolder(final View view) {
            super(view);
            tv1 = (TextView)view.findViewById(R.id.tv_bh_pnr);
            tv2 = (TextView)view.findViewById(R.id.tv_bh_date);
            tv3 = (TextView)view.findViewById(R.id.tv_bh_train_no);
            tv4 = (TextView)view.findViewById(R.id.tv_bh_emailID);
            tv5 = (TextView)view.findViewById(R.id.tv_bh_passengers);
            tv6 = (TextView)view.findViewById(R.id.tv_bh_passengerDetails);
            b1 = (Button)view.findViewById(R.id.b_bh_cancel_ticket);
            cv1 = (CardView)view.findViewById(R.id.cv_bh);
            databaseHelper = new DatabaseHelper(view.getContext());
        }
    }

    public BookedHistoryAdapter(List<BookedTicketDetails> ticketDetailsList) {
        this.ticketDetailsList = ticketDetailsList;
    }

    @Override
    public BookedHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_history_single_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BookedHistoryAdapter.MyViewHolder holder, final int position) {
        final BookedTicketDetails bookedTicketDetails = ticketDetailsList.get(position);
        holder.tv1.setText("PNR: " + bookedTicketDetails.getPNR());
        holder.tv2.setText("Date of journey: " + bookedTicketDetails.getDate());
        holder.tv3.setText("Train Number: " + String.valueOf(bookedTicketDetails.getTrainNo()));
        holder.tv4.setText("Email ID: " + bookedTicketDetails.getEmailID());
        holder.tv5.setText(bookedTicketDetails.getPassengerDetails());

        holder.cv1.setOnClickListener(new View.OnClickListener() {
            int flag = 0;
            @Override
            public void onClick(View v) {
                if(flag == 0){
                    holder.tv3.setVisibility(View.VISIBLE);
                    holder.tv4.setVisibility(View.VISIBLE);
                    holder.tv5.setVisibility(View.VISIBLE);
                    holder.tv6.setVisibility(View.VISIBLE);
                    holder.b1.setVisibility(View.VISIBLE);
                    flag = 1;
                }
                else if(flag == 1){
                    holder.tv3.setVisibility(View.GONE);
                    holder.tv4.setVisibility(View.GONE);
                    holder.tv5.setVisibility(View.GONE);
                    holder.tv6.setVisibility(View.GONE);
                    holder.b1.setVisibility(View.GONE);
                    flag = 0;
                }
            }
        });

        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Toast.makeText(v.getContext(), "Cancel ticket " + position, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                alertDialog.setTitle("Confirm Cancellation");
                alertDialog.setMessage("Are you sure you want to cancel this ticket?");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        // Toast.makeText(v.getContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                        int count = holder.databaseHelper.getPassengerNoOnTicket(bookedTicketDetails.getPNR());
                        int ws = holder.databaseHelper.getWaitingSeats(bookedTicketDetails.getTrainNo(), bookedTicketDetails.getDate(), holder.databaseHelper.getClassTypeFromPNR(bookedTicketDetails.getPNR()));
                        int bs = holder.databaseHelper.getBookedSeats(bookedTicketDetails.getTrainNo(), bookedTicketDetails.getDate(), holder.databaseHelper.getClassTypeFromPNR(bookedTicketDetails.getPNR()));
                        int as = holder.databaseHelper.getAvailableSeats(bookedTicketDetails.getTrainNo(), bookedTicketDetails.getDate(), holder.databaseHelper.getClassTypeFromPNR(bookedTicketDetails.getPNR()));

                        String arr[] = new String[holder.databaseHelper.getAllSeatNoFromPNR(bookedTicketDetails.getPNR()).size()];
                        arr = holder.databaseHelper.getAllSeatNoFromPNR(bookedTicketDetails.getPNR()).toArray(arr);

                        int flag = 0;
                        while(flag != arr.length){
                            //Toast.makeText(v.getContext(), "Seat Number: " + arr[flag], Toast.LENGTH_SHORT).show();
                            flag++;
                        }
                        flag = 0;
                        while(flag != count) {
                            //get int array of all cancelled seat no
                            //String arr[] = new String[holder.databaseHelper.getAllSeatNoFromPNR(bookedTicketDetails.getPNR()).size()];
                            //0arr = holder.databaseHelper.getAllSeatNoFromPNR(bookedTicketDetails.getPNR()).toArray(arr);
                            //Collections.reverse(Arrays.asList(arr));
                            String pnrs[] = new String[holder.databaseHelper.getAllPNRFromInfo(bookedTicketDetails.getTrainNo(), bookedTicketDetails.getDate(), holder.databaseHelper.getClassTypeFromPNR(bookedTicketDetails.getPNR())).size()];
                            pnrs = holder.databaseHelper.getAllPNRFromInfo(bookedTicketDetails.getTrainNo(), bookedTicketDetails.getDate(), holder.databaseHelper.getClassTypeFromPNR(bookedTicketDetails.getPNR())).toArray(pnrs);

                            int cancelled_seat_no = Integer.parseInt(arr[flag]);

                            if (cancelled_seat_no > 0) {
                                if (ws > 0) {
                                    //seat_status of seat_no -1 = 0
                                    //holder.databaseHelper.updateSeatStatus(bookedTicketDetails.getPNR(), -1, 0);
                                    //passenger with seat_no = -1, gets cancelled_seat_number
                                    holder.databaseHelper.deletePassenger(bookedTicketDetails.getPNR(), cancelled_seat_no);
                                    //holder.databaseHelper.updateSeatNumber(bookedTicketDetails.getPNR(), -1, cancelled_seat_no);
                                    String pnrs2[] = new String[holder.databaseHelper.getAllPNRFromInfo3(-1).size()];
                                    pnrs2 = holder.databaseHelper.getAllPNRFromInfo3(-1).toArray(pnrs2);
                                    //Toast.makeText(v.getContext(), "here 1, waiting seats = " + String.valueOf(ws), Toast.LENGTH_SHORT).show();
                                    holder.databaseHelper.updateSeatNumber(getCommon(pnrs, pnrs2), -1, cancelled_seat_no);

                                    if (ws == 1) {
                                        //do nothing xD
                                    } else if (ws == 2) {
                                        //status of seat_no -2 = 0
                                        //holder.databaseHelper.updateSeatStatus(bookedTicketDetails.getPNR(), -2, 0);
                                        //passeneger with seat_no -2, gets seat_no -1
                                        String pnrs3[] = new String[holder.databaseHelper.getAllPNRFromInfo3(-2).size()];
                                        pnrs3 = holder.databaseHelper.getAllPNRFromInfo3(-2).toArray(pnrs3);
                                        holder.databaseHelper.updateSeatNumber(getCommon(pnrs, pnrs3), -2, -1);
                                    } else if (ws == 3) {
                                        //status of seat_no -3 = 0
                                        //holder.databaseHelper.updateSeatStatus(bookedTicketDetails.getPNR(), -3, 0);
                                        //passeneger with seat_no -2, gets seat_no -1
                                        String pnrs4[] = new String[holder.databaseHelper.getAllPNRFromInfo3(-2).size()];
                                        pnrs4 = holder.databaseHelper.getAllPNRFromInfo3(-2).toArray(pnrs4);
                                        holder.databaseHelper.updateSeatNumber(getCommon(pnrs, pnrs4), -2, -1);
                                        //passeneger with seat_no -3, gets seat_no -2
                                        String pnrs5[] = new String[holder.databaseHelper.getAllPNRFromInfo3(-3).size()];
                                        pnrs5 = holder.databaseHelper.getAllPNRFromInfo3(-3).toArray(pnrs5);
                                        holder.databaseHelper.updateSeatNumber(getCommon(pnrs, pnrs5), -3, -2);
                                    }
                                    ws = ws - 1;
                                    bs = bs - 1;
                                    //update waiting seats
                                    holder.databaseHelper.updateWaitingSeats(bookedTicketDetails.getTrainNo(), bookedTicketDetails.getDate(), holder.databaseHelper.getClassTypeFromPNR(bookedTicketDetails.getPNR()), ws);
                                    //update booked seats
                                    holder.databaseHelper.updateBookedSeats(bookedTicketDetails.getTrainNo(), bookedTicketDetails.getDate(), holder.databaseHelper.getClassTypeFromPNR(bookedTicketDetails.getPNR()), bs);

                                }
                                else {
                                    //seat_status of cancelled seat = 0
                                    holder.databaseHelper.updateSeatStatus(bookedTicketDetails.getPNR(), cancelled_seat_no, 0);
                                    as = as + 1;
                                    //update available seats
                                    holder.databaseHelper.updateAvailableSeats(bookedTicketDetails.getTrainNo(), bookedTicketDetails.getDate(), holder.databaseHelper.getClassTypeFromPNR(bookedTicketDetails.getPNR()), as);
                                    bs = bs - 1;
                                    holder.databaseHelper.updateBookedSeats(bookedTicketDetails.getTrainNo(), bookedTicketDetails.getDate(), holder.databaseHelper.getClassTypeFromPNR(bookedTicketDetails.getPNR()), bs);
                                    //holder.databaseHelper.deletePassenger(bookedTicketDetails.getPNR(), cancelled_seat_no);
                                }
                            }
                            else {
                                //Toast.makeText(v.getContext(), "here 2, waiting seats = " + String.valueOf(ws), Toast.LENGTH_SHORT).show();
                                holder.databaseHelper.deletePassenger(bookedTicketDetails.getPNR(), cancelled_seat_no);
                                if (ws == 1) {
                                    //do nothing xD
                                } else if (ws == 2) {
                                    //status of seat_no -2 = 0
                                    //holder.databaseHelper.updateSeatStatus(bookedTicketDetails.getPNR(), -2, 0);
                                    //passeneger with seat_no -2, gets seat_no -1
                                    String pnrs3[] = new String[holder.databaseHelper.getAllPNRFromInfo3(-2).size()];
                                    pnrs3 = holder.databaseHelper.getAllPNRFromInfo3(-2).toArray(pnrs3);
                                    holder.databaseHelper.updateSeatNumber(getCommon(pnrs, pnrs3), -2, -1);
                                } else if (ws == 3) {
                                    //status of seat_no -3 = 0
                                    //holder.databaseHelper.updateSeatStatus(bookedTicketDetails.getPNR(), -3, 0);
                                    //passeneger with seat_no -2, gets seat_no -1
                                    String pnrs4[] = new String[holder.databaseHelper.getAllPNRFromInfo3(-2).size()];
                                    pnrs4 = holder.databaseHelper.getAllPNRFromInfo3(-2).toArray(pnrs4);
                                    holder.databaseHelper.updateSeatNumber(getCommon(pnrs, pnrs4), -2, -1);
                                    //passeneger with seat_no -3, gets seat_no -2
                                    String pnrs5[] = new String[holder.databaseHelper.getAllPNRFromInfo3(-3).size()];
                                    pnrs5 = holder.databaseHelper.getAllPNRFromInfo3(-3).toArray(pnrs5);
                                    holder.databaseHelper.updateSeatNumber(getCommon(pnrs, pnrs5), -3, -2);
                                }
                                ws = ws - 1;
                                bs = bs - 1;
                                //update waiting seats
                                holder.databaseHelper.updateWaitingSeats(bookedTicketDetails.getTrainNo(), bookedTicketDetails.getDate(), holder.databaseHelper.getClassTypeFromPNR(bookedTicketDetails.getPNR()), ws);
                                //update booked seats
                                holder.databaseHelper.updateBookedSeats(bookedTicketDetails.getTrainNo(), bookedTicketDetails.getDate(), holder.databaseHelper.getClassTypeFromPNR(bookedTicketDetails.getPNR()), bs);
                            }
                            //count--;
                            flag++;
                        }
                        // Delete card
                        holder.databaseHelper.deletePassengerTicket(bookedTicketDetails.getPNR());
                        String s = bookedTicketDetails.getPNR();
                        removeAt(position);
                        //Delete all details of that PNR from passenger and passenger_ticket table
                        Toast.makeText(v.getContext(), "Ticket with PNR = " + s + " cancelled successfully", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences2 = v.getContext().getSharedPreferences("sp2", 0);
                        String bookedBy = sharedPreferences2.getString("emailID", "--");
                        String password = sharedPreferences2.getString("password", "--");
                        Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                        intent1.putExtra("emailID", bookedBy);
                        intent1.putExtra("password", password);
                        v.getContext().startActivity(intent1);
                    }
                });

                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(v.getContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return ticketDetailsList.size();
    }

    public void clear() {
        int size = this.ticketDetailsList.size();
        this.ticketDetailsList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void removeAt(int position) {
        ticketDetailsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, ticketDetailsList.size());
    }

    public String getCommon(String[] arr1, String[] arr2){
        for(int k = 0; k < arr1.length; k++){
            for(int j = 0; j < arr2.length; j++){
                if(arr2[j].equals(arr1[k]));
                return arr2[j];
            }

        }
        return null;
    }
}