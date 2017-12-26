package com.nikhilkrathi.railwayhelper;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheckStatusDialog extends Dialog implements View.OnClickListener{

    TextView tv1, tv2, tv3, tv4, tv5;
    int trainID;
    String trainName, date, statusI, statusII, statusIII;
    Button b1;

    public CheckStatusDialog(@NonNull Context context,int trainID, String trainName, String date, String statusI, String statusII, String statusIII) {
        super(context);
        this.trainID = trainID;
        this.trainName = trainName;
        this.date = date;
        this.statusI = statusI;
        this.statusII = statusII;
        this.statusIII = statusIII;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_status_dialog_box);
        tv1 = (TextView) findViewById(R.id.csdb_tv_trainName);
        tv2 = (TextView) findViewById(R.id.csdb_tv_date);
        tv3 = (TextView) findViewById(R.id.csdb_tv_statusClassI);
        tv4 = (TextView) findViewById(R.id.csdb_tv_statusClassII);
        tv5 = (TextView) findViewById(R.id.csdb_tv_statusClassIII);
        b1 = (Button) findViewById(R.id.csdb_close_btn);

        tv1.setText(trainName + " (" + String.valueOf(trainID) + ")");
        tv2.setText("Date: " + date);
        if(statusI.equals("WAITING 4")){
            tv3.setText("Status Class I: " + statusI + "/REGRET");
        }else {
            tv3.setText("Status Class I: " + statusI);
        }
        if(statusII.equals("WAITING 4")){
            tv4.setText("Status Class II: " + statusII + "/REGRET");
        }else {
            tv4.setText("Status Class II: " + statusII);
        }

        if(statusIII.equals("WAITING 4")){
            tv5.setText("Status Class III: " + statusIII + "/REGRET");
        }else {
            tv5.setText("Status Class III: " + statusIII);
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}