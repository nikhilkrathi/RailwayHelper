package com.nikhilkrathi.railwayhelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nikhilkrathi.railwayhelper.database.helper.DatabaseHelper;

import static android.R.id.empty;

public class PNRStatus extends Fragment {

    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8;
    EditText et1;
    Button b1;
    DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pnr_status, container, false);
        tv1 = (TextView) view.findViewById(R.id.tv_heading);
        tv2 = (TextView) view.findViewById(R.id.tv_train_number);
        tv3 = (TextView) view.findViewById(R.id.tv_from);
        tv4 = (TextView) view.findViewById(R.id.tv_to);
        tv5 = (TextView) view.findViewById(R.id.tv_date);
        tv6 = (TextView) view.findViewById(R.id.tv_class);
        tv7 = (TextView) view.findViewById(R.id.tv_status);
        tv8 = (TextView) view.findViewById(R.id.pn_tv_error);
        et1 = (EditText) view.findViewById(R.id.et_pnr_number);
        b1 = (Button) view.findViewById(R.id.b_find_details);
        databaseHelper = new DatabaseHelper(getActivity());

        View.OnClickListener pnrDetails = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et1.getText().toString() == null || et1.getText().toString().isEmpty()){
                    tv8.setText("PNR number field cannot be empty!");
                    tv1.setVisibility(View.GONE);
                    tv2.setVisibility(View.GONE);
                    tv3.setVisibility(View.GONE);
                    tv4.setVisibility(View.GONE);
                    tv5.setVisibility(View.GONE);
                    tv6.setVisibility(View.GONE);
                    tv7.setVisibility(View.GONE);
                    tv8.setVisibility(View.VISIBLE);
                }

                else if (et1.getText().toString().length() != 5){
                    tv8.setText("PNR number should be a 5-digit number");
                    tv1.setVisibility(View.GONE);
                    tv2.setVisibility(View.GONE);
                    tv3.setVisibility(View.GONE);
                    tv4.setVisibility(View.GONE);
                    tv5.setVisibility(View.GONE);
                    tv6.setVisibility(View.GONE);
                    tv7.setVisibility(View.GONE);
                    tv8.setVisibility(View.VISIBLE);
                }

                else {
                    String pnr = et1.getText().toString();
                    int trainID = databaseHelper.getTrainIDFromPNR(pnr);
                    if(trainID == 0){
                        tv8.setText("No such PNR found!");
                        tv1.setVisibility(View.GONE);
                        tv2.setVisibility(View.GONE);
                        tv3.setVisibility(View.GONE);
                        tv4.setVisibility(View.GONE);
                        tv5.setVisibility(View.GONE);
                        tv6.setVisibility(View.GONE);
                        tv7.setVisibility(View.GONE);
                        tv8.setVisibility(View.VISIBLE);
                    }
                    else{
                        String passengerDetails = null ;
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
                        while (flag2 != count2){
                            //Toast.makeText(getActivity(), String.valueOf(allName.length) + " Flag: " + String.valueOf(flag), Toast.LENGTH_SHORT).show();
                            if(Integer.parseInt(allSeatNumber[flag2]) > 0){
                                passengerDetails = passengerDetails + "\n "+ allName[flag2] + "    " + allAge[flag2] + "   " + allGender[flag2] + "   " + classType + " - " + allSeatNumber[flag2] + "\n";
                            }
                            else {
                                passengerDetails = passengerDetails + "\n " + allName[flag2] + "    " + allAge[flag2] + "   " + allGender[flag2] + "   " + classType + " - " + "W/L " + allSeatNumber[flag2] + "\n";
                            }
                            flag2++;
                        }
                        tv2.setText("Train Number: " + String.valueOf(trainID));
                        tv3.setText("From: " + databaseHelper.getSourceIDFromPNR(pnr));
                        tv4.setText("To: " + databaseHelper.getDestinationIDFromPNR(pnr));
                        tv5.setText("Date: " + databaseHelper.getDateOfJourneyFromPNR(pnr));
                        tv6.setText("Class: " + databaseHelper.getClassTypeFromPNR(pnr));
                        tv7.setText("Status: " + passengerDetails.substring(4));

                        tv1.setVisibility(View.VISIBLE);
                        tv2.setVisibility(View.VISIBLE);
                        tv3.setVisibility(View.VISIBLE);
                        tv4.setVisibility(View.VISIBLE);
                        tv5.setVisibility(View.VISIBLE);
                        tv6.setVisibility(View.VISIBLE);
                        tv7.setVisibility(View.VISIBLE);
                        tv8.setVisibility(View.GONE);

                    }



                }
            }
        };
        b1.setOnClickListener(pnrDetails);

        return view;
    }
}