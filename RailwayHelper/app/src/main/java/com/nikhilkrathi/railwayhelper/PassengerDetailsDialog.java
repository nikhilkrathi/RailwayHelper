package com.nikhilkrathi.railwayhelper;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nikhilkrathi.railwayhelper.database.model.CheckAvailabilityTrainDetails;
import com.nikhilkrathi.railwayhelper.database.model.PassengerDetails;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class PassengerDetailsDialog extends Dialog implements View.OnClickListener{

    Button b1, b2;
    EditText et1, et2;
    TextView tv1;
    RadioGroup rg1;
    String gender;
    PassengerDetails passengerDetails;
    SharedPreferences sharedpreferences;

    public PassengerDetailsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_details_dialog_box);

        et1 = (EditText)findViewById(R.id.pddb_et_name);
        et2 = (EditText)findViewById(R.id.pddb_et_age);
        b1 = (Button)findViewById(R.id.pddb_close_btn);
        b2 = (Button)findViewById(R.id.pddb_save_btn);
        rg1 = (RadioGroup)findViewById(R.id.pddb_gender);
        tv1 = (TextView)findViewById(R.id.pddb_tv_error);
        passengerDetails = new PassengerDetails();
        sharedpreferences = getContext().getSharedPreferences("sp1", 0);

        rg1.clearCheck();
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    gender = rb.getText().toString();
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("check", 2);
                editor.commit();
                cancel();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if(et1.getText().toString().isEmpty() || et1.getText().toString() == null){
                    tv1.setText("Invalid name");
                    tv1.setVisibility(View.VISIBLE);
                }
                else if(et2.getText().toString().isEmpty() || et1.getText().toString() == null){
                    tv1.setText("Invalid age");
                    tv1.setVisibility(View.VISIBLE);
                }
                else if(Integer.parseInt(et2.getText().toString()) < 0 || Integer.parseInt(et2.getText().toString()) > 100){
                    tv1.setText("Age should be between 1 to 100");
                    tv1.setVisibility(View.VISIBLE);
                }
                else if (rg1.getCheckedRadioButtonId() == -1){
                    tv1.setText("Invalid gender");
                    tv1.setVisibility(View.VISIBLE);
                }
                else {
                    editor.putInt("check", 1);
                    editor.putString("name", et1.getText().toString());
                    editor.putInt("age", Integer.parseInt(et2.getText().toString()));
                    editor.putString("gender", gender);
                    editor.commit();
                    dismiss();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}