package com.nikhilkrathi.railwayhelper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nikhilkrathi.railwayhelper.database.helper.DatabaseHelper;
import com.nikhilkrathi.railwayhelper.database.model.CheckAvailabilityTrainDetails;
import com.nikhilkrathi.railwayhelper.database.model.PassengerDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.R.attr.name;

public class PassengerDetailsActivity extends AppCompatActivity {

    int flag = 0, pnr = 0;
    Button b1, b2, b3;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
    RecyclerView recyclerView;
    RadioGroup rg1;
    String classType;
    PassengerDetailsAdapter passengerDetailsAdapter;
    PassengerDetails passengerDetails;
    private List<PassengerDetails> passengerDetailsList = new ArrayList<>();
    PassengerDetailsAdapter.MyViewHolder myViewHolder;
    String[] name = new String[3];
    int[] age = new int[3];
    String[] gender = new String[3];
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_details);

        final Intent intent = getIntent();

        b1 = (Button)findViewById(R.id.apd_b_addPassenger);
        b2 = (Button)findViewById(R.id.apd_b_confirm);
        b3 = (Button)findViewById(R.id.apd_b_done);
        tv1 = (TextView)findViewById(R.id.apd_trainNameNo);
        tv2 =(TextView)findViewById(R.id.apd_FromStation);
        tv3 = (TextView)findViewById(R.id.apd_ToStation);
        tv4 = (TextView)findViewById(R.id.apd_doj);
        tv5 = (TextView)findViewById(R.id.apd_tv_error);
        tv6 = (TextView)findViewById(R.id.apd_tv_confirm);
        tv7 = (TextView)findViewById(R.id.apd_tv_pnr);
        recyclerView = (RecyclerView)findViewById(R.id.apd_recView);
        rg1 = (RadioGroup)findViewById(R.id.apd_class);
        databaseHelper = new DatabaseHelper(this);

        rg1.clearCheck();
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    classType = rb.getText().toString();
                }
            }
        });

        passengerDetails = new PassengerDetails();
        passengerDetailsAdapter = new PassengerDetailsAdapter(passengerDetailsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(passengerDetailsAdapter);

        tv1.setText(intent.getStringExtra("TrainName") + " (" + intent.getIntExtra("TrainNo", 0) + ")");
        tv2.setText("From: " + intent.getStringExtra("FromID"));
        tv3.setText("To: " + intent.getStringExtra("ToID"));
        tv4.setText("Date of journey: " + intent.getStringExtra("Date"));
        passengerDetailsAdapter.clear();

        if(passengerDetailsAdapter.getItemCount() == 0){
            b2.setVisibility(View.GONE);
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassengerDetailsDialog passengerDetailsDialog = new PassengerDetailsDialog(v.getContext());
                passengerDetailsDialog.setTitle("Enter passenger's details");
               /* if(passengerDetailsAdapter.getItemCount() != 0){
                    b2.setVisibility(View.VISIBLE);
                }
               */ if(passengerDetailsAdapter.getItemCount() < 3) {
                    passengerDetailsDialog.show();
                    Window window = passengerDetailsDialog.getWindow();
                    window.setLayout(ActionBarOverlayLayout.LayoutParams.MATCH_PARENT, ActionBarOverlayLayout.LayoutParams.WRAP_CONTENT);
                }
                else{
                    Toast.makeText(PassengerDetailsActivity.this, "Maximum no. of passengers per ticket are 3", Toast.LENGTH_SHORT).show();
                }

                passengerDetailsDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //Toast.makeText(PassengerDetailsActivity.this, "Cancelled!", Toast.LENGTH_SHORT).show();
                        int tag = -1;
                    }
                });
                passengerDetailsDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        //Toast.makeText(PassengerDetailsActivity.this, String.valueOf(passengerDetailsAdapter.getItemCount()), Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences1 = getSharedPreferences("sp1", 0);
                        if(sharedPreferences1.getInt("check", 0) == 1) {
                            preparePassengerItems(sharedPreferences1.getString("name", "--"), sharedPreferences1.getInt("age", 0), sharedPreferences1.getString("gender", "-"));
                            b2.setVisibility(View.VISIBLE);
                            name[passengerDetailsAdapter.getItemCount() - 1] = sharedPreferences1.getString("name", "--");
                            age[passengerDetailsAdapter.getItemCount() - 1] = sharedPreferences1.getInt("age", 0);
                            gender[passengerDetailsAdapter.getItemCount() - 1] = sharedPreferences1.getString("gender","-");
                            SharedPreferences.Editor editor = sharedPreferences1.edit();
                            editor.putInt("check", 2);
                            editor.commit();
                        }
                    }
                });

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rg1.getCheckedRadioButtonId() == -1){
                   tv5.setVisibility(View.VISIBLE);
                }
                else {
                    //Toast.makeText(PassengerDetailsActivity.this, "Confirm and Book!", Toast.LENGTH_SHORT).show();
                    //Add ticket into passenger _ticket table
                    pnr = generatePNR();
                    for(int i = 0; i<passengerDetailsAdapter.getItemCount(); i++){
                        //Toast.makeText(PassengerDetailsActivity.this, name[i] + " " + age[i] + " " + gender[i], Toast.LENGTH_SHORT).show();
                        //Add individual passenger into passenger table
                        int as = databaseHelper.getAvailableSeats(intent.getIntExtra("TrainNo", 0), intent.getStringExtra("Date"), classType);
                        int ws = databaseHelper.getWaitingSeats(intent.getIntExtra("TrainNo", 0), intent.getStringExtra("Date"), classType);
                        int bs = databaseHelper.getBookedSeats(intent.getIntExtra("TrainNo", 0), intent.getStringExtra("Date"), classType);
                        int ts = databaseHelper.getTotalSeats(intent.getIntExtra("TrainNo", 0), classType);
                        if(bs + passengerDetailsAdapter.getItemCount() - i > ts + 3){
                            flag = 0;
                            Toast.makeText(PassengerDetailsActivity.this, "No. of passenger exceeds total available seats", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //Toast.makeText(PassengerDetailsActivity.this, "Booked seats:" + String.valueOf(bs), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            //pnr = generatePNR();
                            //int row_count = databaseHelper.getRowCountOfTrainAtdate(intent.getIntExtra("TrainNo", 0), intent.getStringExtra("Date"), classType) + i;
                            //Toast.makeText(PassengerDetailsActivity.this, "Run: " + String.valueOf(i) + "," + String.valueOf(p), Toast.LENGTH_SHORT).show();
                            if (as > 0) {
                                int seatno = databaseHelper.getMinAvailableSeatNo(intent.getIntExtra("TrainNo", 0), intent.getStringExtra("Date"), classType);
                                //Toast.makeText(PassengerDetailsActivity.this, "Min Seat no = " + String.valueOf(seatno), Toast.LENGTH_SHORT).show();
                                /*if(row_count < ts){
                                    seatno = bs + 1;
                                }
                               */
                                if(seatno == 0){
                                    int newseatno = bs + 1;
                                    databaseHelper.addPassengers(intent.getIntExtra("TrainNo", 0), String.valueOf(pnr), 1, newseatno, name[i], age[i], gender[i]);
                                }
                                else {
                                    seatno = databaseHelper.getMinAvailableSeatNo(intent.getIntExtra("TrainNo", 0), intent.getStringExtra("Date"), classType);
                                    if(seatno != 0) {
                                        String[] pnrs = new String[databaseHelper.getAllPNRFromInfo(intent.getIntExtra("TrainNo", 0), intent.getStringExtra("Date"), classType).size()];
                                        pnrs = databaseHelper.getAllPNRFromInfo(intent.getIntExtra("TrainNo", 0), intent.getStringExtra("Date"), classType).toArray(pnrs);

                                        String[] pnrs2 = new String[databaseHelper.getAllPNRFromInfo2(seatno).size()];
                                        pnrs2 = databaseHelper.getAllPNRFromInfo2(seatno).toArray(pnrs2);

                                        databaseHelper.deletePassenger(getCommon(pnrs, pnrs2), seatno);
                                    }
                                    //Toast.makeText(PassengerDetailsActivity.this, "Run: " + String.valueOf(i) + "," +String.valueOf(databaseHelper.getMinAvailableSeatNo(intent.getIntExtra("TrainNo", 0), intent.getStringExtra("Date"), classType)), Toast.LENGTH_SHORT).show();
                                    databaseHelper.addPassengers(intent.getIntExtra("TrainNo", 0), String.valueOf(pnr), 1, seatno, name[i], age[i], gender[i]);
                                }
                                //book seat of passsenger
                                //Toast.makeText(PassengerDetailsActivity.this, String.valueOf(pnr) + " - " +  name[i] + " " + age[i] + " " + gender[i] + " ", Toast.LENGTH_SHORT).show();
                                //update remaining available seats
                                as = as - 1;
                                databaseHelper.updateAvailableSeats(intent.getIntExtra("TrainNo", 0), intent.getStringExtra("Date"), classType, as);
                                //update booked seats
                                bs = bs + 1;
                                databaseHelper.updateBookedSeats(intent.getIntExtra("TrainNo", 0), intent.getStringExtra("Date"), classType, bs);

                            } else if (as == 0 && ws < 3) {
                                //book seat of passenger
                                int seatno = as - ws - 1;
                                databaseHelper.addPassengers(intent.getIntExtra("TrainNo", 0), String.valueOf(pnr), 1, seatno, name[i], age[i], gender[i]);
                                //Toast.makeText(PassengerDetailsActivity.this, String.valueOf(pnr) + " - " +  name[i] + " " + age[i] + " " + gender[i] + " ", Toast.LENGTH_SHORT).show();
                                //update waiting seats
                                ws = ws + 1;
                                databaseHelper.updateWaitingSeats(intent.getIntExtra("TrainNo", 0), intent.getStringExtra("Date"), classType, ws);
                                //update booked seats
                                bs = bs + 1;
                                databaseHelper.updateBookedSeats(intent.getIntExtra("TrainNo", 0), intent.getStringExtra("Date"), classType, bs);
                            }
                        }

                    }
                }
                if(flag == 1) {
                    SharedPreferences sharedPreferences2 = getSharedPreferences("sp2", 0);
                    final String bookedBy = sharedPreferences2.getString("emailID", "--");
                    final String password = sharedPreferences2.getString("password", "--");
                    databaseHelper.makeTicket(intent.getIntExtra("TrainNo", 0), String.valueOf(pnr), intent.getStringExtra("FromID"), intent.getStringExtra("ToID"), classType, intent.getStringExtra("Date"), bookedBy );
                    b1.setVisibility(View.GONE);
                    b2.setVisibility(View.GONE);
                    tv6.setVisibility(View.VISIBLE);
                    tv7.setText("PNR: " + String.valueOf(pnr));
                    tv7.setVisibility(View.VISIBLE);
                    b3.setVisibility(View.VISIBLE);
                    b3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent1 = new Intent(PassengerDetailsActivity.this, MainActivity.class);
                            intent1.putExtra("emailID", bookedBy);
                            intent1.putExtra("password", password);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent1);
                        }
                    });

                }
            }
        });

    }

    private void preparePassengerItems(String name, int age, String gender){
        PassengerDetails passengerDetails = new PassengerDetails(name, age, gender);
        passengerDetailsList.add(passengerDetails);
        passengerDetailsAdapter.notifyDataSetChanged();
    }

    public int generatePNR() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(8)) * 10000 + r.nextInt(10000));
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