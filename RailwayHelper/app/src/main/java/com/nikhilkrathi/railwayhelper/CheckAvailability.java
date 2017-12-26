package com.nikhilkrathi.railwayhelper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nikhilkrathi.railwayhelper.database.helper.DatabaseHelper;
import com.nikhilkrathi.railwayhelper.database.model.CheckAvailabilityTrainDetails;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.attr.maxDate;
import static android.R.attr.minDate;
import static android.R.attr.transcriptMode;
import static android.R.attr.y;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class CheckAvailability extends Fragment {

    TextView tv1, tv2;
    Button b1;
    EditText et1;
    Spinner s1, s2;
    DatabaseHelper databaseHelper;
    private int mYear, mMonth, mDay;
    private String[] arrayFrom;
    private  String[] arrayTo;
    String[] trainList;
    String dayOfWeek, SelectedDate;
    RecyclerView recyclerView;
    CheckAvailabilityAdapter checkAvailabilityAdapter;
    CheckAvailabilityTrainDetails checkAvailabilityTrainDetails;
    private List<CheckAvailabilityTrainDetails> trainDetailsList = new ArrayList<>();
    CheckAvailabilityAdapter.MyViewHolder myViewHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_check_availability, container, false);
        tv1 = (TextView) view.findViewById(R.id.tv_trains);
        tv2 = (TextView) view.findViewById(R.id.tv_ca_date);
        b1 = (Button) view.findViewById(R.id.b_search_trains);
        et1 = (EditText) view.findViewById(R.id.et_ca_date);
        s1 = (Spinner) view.findViewById(R.id.spinnerFrom);
        s2 = (Spinner) view.findViewById(R.id.spinnerTo);

        databaseHelper = new DatabaseHelper(getActivity());

        arrayFrom = new String[databaseHelper.getAllStationValues().size()];
        arrayFrom = databaseHelper.getAllStationValues().toArray(arrayFrom);
        arrayTo = new String[databaseHelper.getAllStationValues().size()];
        arrayTo = databaseHelper.getAllStationValues().toArray(arrayTo);

        recyclerView = (RecyclerView) view.findViewById(R.id.ca_recView);
        checkAvailabilityTrainDetails = new CheckAvailabilityTrainDetails();

        checkAvailabilityAdapter = new CheckAvailabilityAdapter(trainDetailsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(checkAvailabilityAdapter);


        ArrayAdapter<String> adapterFrom = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, arrayFrom);
        s1.setAdapter(adapterFrom);

        ArrayAdapter<String> adapterTo = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, arrayTo);
        s2.setAdapter(adapterTo);

        et1.setShowSoftInputOnFocus(false);
        et1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                SimpleDateFormat df = new SimpleDateFormat("EEEE");
                                Date date = new Date(year, monthOfYear, dayOfMonth - 1);
                                dayOfWeek = df.format(date);
                                //Toast.makeText(getActivity(), dayOfWeek, Toast.LENGTH_SHORT).show();
                                SelectedDate = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year);
                                et1.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                c.add(Calendar.MONTH, 1);
                datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        View.OnClickListener searchTrains = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tv1.setVisibility(View.VISIBLE);
                //prepareTrainItems();
                String fromStn = s1.getSelectedItem().toString();
                String toStn = s2.getSelectedItem().toString();

                String fromStnID = databaseHelper.getStnIDFromName(fromStn);
                String toStnID = databaseHelper.getStnIDFromName(toStn);

                trainList = new String[databaseHelper.getTrainIDUserQuery(fromStnID,toStnID).size()];
                trainList = databaseHelper.getTrainIDUserQuery(fromStnID, toStnID).toArray(trainList);
                if(trainList[0] == "0"){
                    tv1.setText("Sorry, no trains found!");
                    tv1.setVisibility(View.VISIBLE);
                }
                else if(et1.getText().toString() == "" || et1.getText().toString().isEmpty()){
                    tv1.setText("Date field cannot be empty.");
                    tv1.setVisibility(View.VISIBLE);
                }
                else if (fromStn.equals(toStn)){
                    tv1.setText("Source and destination have to be different.");
                    tv1.setVisibility(View.VISIBLE);
                }
                else {
                    tv1.setVisibility(View.GONE);

                    int length = trainList.length;
                    int count = 0;

                    checkAvailabilityAdapter.clear();
                    while (count < length) {
                        int trainID = Integer.parseInt(trainList[count]);
                        if (trainID == 0) {
                            tv1.setVisibility(View.VISIBLE);
                            break;
                        }
                        String days = databaseHelper.getRunsOnDays(trainID);
                        String arrivalTime = databaseHelper.getArrivalTime(toStnID, trainID);
                        String departureTime = databaseHelper.getDepartureTime(fromStnID, trainID);
                        if (runsOrNot(dayOfWeek, days)) {
                            //Toast.makeText(getActivity(), trainList[count] , Toast.LENGTH_LONG).show();
                            if (fromStnID.equals(toStnID)) {
                                if (arrivalTime.equals("--")) {
                                    prepareTrainItems(databaseHelper.getTrainName(trainID), trainID, fromStnID, toStnID, departureTime, departureTime, databaseHelper.getTrainType(trainID), SelectedDate);
                                } else if (departureTime.equals("--")) {
                                    prepareTrainItems(databaseHelper.getTrainName(trainID), trainID, fromStnID, toStnID, arrivalTime, arrivalTime, databaseHelper.getTrainType(trainID), SelectedDate);
                                }
                            } else {
                                prepareTrainItems(databaseHelper.getTrainName(trainID), trainID, fromStnID, toStnID, departureTime, arrivalTime, databaseHelper.getTrainType(trainID), SelectedDate);
                            }
                        } else {
                            //Toast.makeText(getActivity(), "No trains available", Toast.LENGTH_SHORT).show();
                        }
                        count++;
                    }
                }

            }
        };
        b1.setOnClickListener(searchTrains);

        return view;
    }

    private void prepareTrainItems(String trainName, int trainID, String sourceID, String destinationID, String arrivalTime, String departureTime, String trainType, String date){
        CheckAvailabilityTrainDetails checkAvailabilityTrainDetails = new CheckAvailabilityTrainDetails(trainName, trainID, sourceID, destinationID, arrivalTime, departureTime, trainType, date);
        trainDetailsList.add(checkAvailabilityTrainDetails);
        checkAvailabilityAdapter.notifyDataSetChanged();
    }

    private Boolean runsOrNot(String selectedDate, String trainRunsOn){
        int length = trainRunsOn.length();
        int arr[] = new int[length];
        int count = 0, i=0;
        //String s1 = null;
        while (count < length){
            //s1 = trainRunsOn.substring(count, count + 1);
            arr[count] = Integer.parseInt(trainRunsOn.substring(count, count + 1));
            count++;
        }

        count = trainRunsOn.length();
        switch (selectedDate) {
            case "Sunday":
                for(i=0; i<count; i++){
                    if (arr[i] == 1)
                        return true;
                }
                break;
            case "Monday":
                count = trainRunsOn.length();
                for(i=0; i<count; i++){
                    if (arr[i] == 2)
                        return true;
                }
                break;
            case "Tuesday":
                count = trainRunsOn.length();
                for(i=0; i<count; i++){
                    if (arr[i] == 3)
                        return true;
                }
                break;
            case "Wednesday":
                count = trainRunsOn.length();
                for(i=0; i<count; i++){
                    if (arr[i] == 4)
                        return true;
                }
                break;
            case "Thursday":
                count = trainRunsOn.length();
                for(i=0; i<count; i++){
                    if (arr[i] == 5)
                        return true;
                }
                break;
            case "Friday":
                count = trainRunsOn.length();
                for(i=0; i<count; i++){
                    if (arr[i] == 6)
                        return true;
                }
                break;
            case "Saturday":
                count = trainRunsOn.length();
                for(i=0; i<count; i++){
                    if (arr[i] == 7)
                        return true;
                }
                break;

        }

        return false;
    }

}
