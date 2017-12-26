package com.nikhilkrathi.railwayhelper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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

import java.util.ArrayList;
import java.util.List;

public class BookedHistory extends Fragment {

    String[] allPNR;
    String[] allName;
    String[] allAge;
    String[] allSeatNumber;
    String[] allSeatStatus;
    String[] allGender;
    String classType = "";
    String passengerDetails = null;
    TextView tv1;
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    BookedHistoryAdapter bookedHistoryAdapter;
    BookedTicketDetails bookedTicketDetails;
    private List<BookedTicketDetails> ticketDetailsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_booking_history, container, false);
        tv1 = (TextView)view.findViewById(R.id.bh_noItem);
        databaseHelper = new DatabaseHelper(getActivity());
        recyclerView = (RecyclerView)view.findViewById(R.id.bh_recView);
        bookedTicketDetails = new BookedTicketDetails();
        bookedHistoryAdapter = new BookedHistoryAdapter(ticketDetailsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(bookedHistoryAdapter);

        //initialize count from databasehelper
        bookedHistoryAdapter.clear();
        SharedPreferences sharedPreferences2 = getContext().getSharedPreferences("sp2", 0);
        final String bookedBy = sharedPreferences2.getString("emailID", "--");

        allPNR = new String[databaseHelper.getAllPNR(bookedBy).size()];
        allPNR = databaseHelper.getAllPNR(bookedBy).toArray(allPNR);
        int count = allPNR.length;
        if(allPNR[0] == "0"){
            tv1.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else {
            tv1.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            int flag = 0;

            while (flag != count) {
                passengerDetails = "";
                String pnr = allPNR[flag];
                String[] allName = new String[databaseHelper.getAllNameFromPNR(pnr).size()];
                allName = databaseHelper.getAllNameFromPNR(pnr).toArray(allName);

                String[] allAge = new String[databaseHelper.getAllAgeFromPNR(pnr).size()];
                allAge = databaseHelper.getAllAgeFromPNR(pnr).toArray(allAge);

                String[] allGender = new String[databaseHelper.getAllGenderFromPNR(pnr).size()];
                allGender = databaseHelper.getAllGenderFromPNR(pnr).toArray(allGender);

                String[] allSeatNumber = new String[databaseHelper.getAllSeatNoFromPNR(pnr).size()];
                allSeatNumber = databaseHelper.getAllSeatNoFromPNR(pnr).toArray(allSeatNumber);

                String classType = databaseHelper.getClassTypeFromPNR(pnr);

                int count2 = allName.length;
                int flag2 = 0;
                while (flag2 != count2) {
                    //Toast.makeText(getActivity(), String.valueOf(allName.length) + " Flag: " + String.valueOf(flag), Toast.LENGTH_SHORT).show();
                    if (Integer.parseInt(allSeatNumber[flag2]) > 0) {
                        passengerDetails = passengerDetails + "\n " + allName[flag2] + "    " + allAge[flag2] + "   " + allGender[flag2] + "   " + classType + " - " + allSeatNumber[flag2] + "\n";
                    } else {
                        passengerDetails = passengerDetails + "\n " + allName[flag2] + "    " + allAge[flag2] + "   " + allGender[flag2] + "   " + classType + " - " + "W/L " + allSeatNumber[flag2] + "\n";
                    }
                    flag2++;
                }
                //Toast.makeText(getActivity(), String.valueOf(databaseHelper.getPassengerNoOnTicket(allPNR[flag])), Toast.LENGTH_SHORT).show();
                prepareTicketItems(allPNR[flag], databaseHelper.getDateOfJourneyFromPNR(allPNR[flag]), databaseHelper.getTrainIDFromPNR(allPNR[flag]), bookedBy, passengerDetails);
                flag++;
            }
        }

        return view;
    }

    private void prepareTicketItems(String pnr, String date, int trainID, String emailID, String passengerDetails){
        BookedTicketDetails bookedTicketDetails = new BookedTicketDetails(pnr, date, trainID, emailID, passengerDetails);
        ticketDetailsList.add(bookedTicketDetails);
        bookedHistoryAdapter.notifyDataSetChanged();
    }
}