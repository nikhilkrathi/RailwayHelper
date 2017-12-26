package com.nikhilkrathi.railwayhelper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FareDialog extends Dialog implements View.OnClickListener {

    TextView tv1, tv2, tv3, tv4, tv5;
    Button b1;
    int dist, trainID;
    String trainName;
    double fc1, fc2, fc3;

    public FareDialog(@NonNull Context context, int distance, double fareClass1, double fareClass2, double fareClass3, String trainName, int trainID) {
        super(context);
        this.dist = distance;
        this.fc1 = fareClass1;
        this.fc2 = fareClass2;
        this.fc3 = fareClass3;
        this.trainName = trainName;
        this.trainID = trainID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_fare_dialog_box);
        tv1 = (TextView) findViewById(R.id.gfdb_tv_trainName);
        tv2 = (TextView) findViewById(R.id.gfdb_tv_distance);
        tv3 = (TextView) findViewById(R.id.gfdb_tv_fareClass1);
        tv4 = (TextView) findViewById(R.id.gfdb_tv_fareClass2);
        tv5 = (TextView) findViewById(R.id.gfdb_tv_fareClass3);
        b1 = (Button)findViewById(R.id.gfdb_close_btn);

        fc1 = Math.round(fc1*100.0)/100.0;
        fc2 = Math.round(fc2*100.0)/100.0;
        fc3 = Math.round(fc3*100.0)/100.0;

        tv1.setText(trainName + " (" + String.valueOf(trainID) + ")");
        tv2.setText("Distance = " + String.valueOf(dist) + " kms");
        tv3.setText("Face Class I = Rs. " + String.valueOf(fc1));
        tv4.setText("Face Class II = Rs. " + String.valueOf(fc2));
        tv5.setText("Face Class III = Rs. " + String.valueOf(fc3));

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