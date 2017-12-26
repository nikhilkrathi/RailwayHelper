package com.nikhilkrathi.railwayhelper;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nikhilkrathi.railwayhelper.database.helper.DatabaseHelper;
import com.nikhilkrathi.railwayhelper.database.model.SignUp;

public class SignUpActivity extends AppCompatActivity {


    DatabaseHelper databaseHelper;
    SignUp signup;
    EditText et1, et2, et3, et4, et5, et6, et7;
    Button b1, b2;
    RadioGroup rg1;
    String emailId, password, re_enter_password, gender;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et1 = (EditText) findViewById(R.id.et_fullname);
        et2 = (EditText) findViewById(R.id.et_emailID);
        et3 = (EditText) findViewById(R.id.et_password);
        et4 = (EditText) findViewById(R.id.et_re_enter_password);
        et5 = (EditText) findViewById(R.id.et_mobile_no);
        et6 = (EditText) findViewById(R.id.et_age);
        et7 = (EditText) findViewById(R.id.et_city);

        tv1 = (TextView) findViewById(R.id.tv_error);
        b1 = (Button) findViewById(R.id.b_signup);
        b2 = (Button) findViewById(R.id.b_already_acc);
        rg1 = (RadioGroup) findViewById(R.id.rg_gender);
        databaseHelper = new DatabaseHelper(this);

        rg1.clearCheck();
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               RadioButton rb = (RadioButton) group.findViewById(checkedId);
               if (null != rb && checkedId > -1) {
                   Toast.makeText(SignUpActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                   //signup.setGender(rb.getText().toString());
                   gender = rb.getText().toString();
               }
           }
        });

        View.OnClickListener signupbtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et1.getText().toString().isEmpty()){
                    tv1.setText("Enter Fullname");
                    tv1.setVisibility(View.VISIBLE);
                }
                else if(et2.getText().toString().isEmpty()){
                    tv1.setText("Enter EmailID");
                    tv1.setVisibility(View.VISIBLE);
                }
                else if(et3.getText().toString().length() < 8){
                    tv1.setText("Minimum password length is 8");
                    tv1.setVisibility(View.VISIBLE);
                }
                else if(et3.getText().toString().isEmpty()){
                    tv1.setText("Enter Password");
                    tv1.setVisibility(View.VISIBLE);
                }
                else if(et4.getText().toString().isEmpty()){
                    tv1.setText("Re-enter Password");
                    tv1.setVisibility(View.VISIBLE);
                }
                else if(et5.getText().toString().isEmpty()){
                    tv1.setText("Enter Mobile Number");
                    tv1.setVisibility(View.VISIBLE);
                }
                else if(et6.getText().toString().isEmpty()){
                    tv1.setText("Enter Age");
                    tv1.setVisibility(View.VISIBLE);
                }
                else if(et7.getText().toString().isEmpty()){
                    tv1.setText("Enter City");
                    tv1.setVisibility(View.VISIBLE);
                }
                else if(et3.getText().toString().equals(et4.getText().toString()) == false){
                    tv1.setText("Passwords do not match");
                    tv1.setVisibility(View.VISIBLE);
                }
                else if (et5.getText().toString().length() != 10){
                    tv1.setText("Invalid Mobile Number");
                    tv1.setVisibility(View.VISIBLE);
                }
                else if(Integer.parseInt(et6.getText().toString()) < 0 || Integer.parseInt(et6.getText().toString()) > 100){
                    tv1.setText("Age should be between 1 and 100");
                    tv1.setVisibility(View.VISIBLE);
                }
                else if(!et2.getText().toString().contains("@") || !et2.getText().toString().contains(".")){
                    tv1.setText("Invalid EmailID, should contain '@' and '.' ");
                    tv1.setVisibility(View.VISIBLE);
                }

                else if(databaseHelper.CheckIfUserExists(et2.getText().toString())){
                    tv1.setText("Email ID already exists");
                    tv1.setVisibility(View.VISIBLE);
                }

        /*        else if(databaseHelper.checkUser(et2.getText().toString(), et3.getText().toString())){
                    tv1.setText("You already have an account!");
                    tv1.setVisibility(View.VISIBLE);
                }
        */
                else{
                    signup = new SignUp();
                    signup.setFullName(et1.getText().toString());
                    signup.setEmailID(et2.getText().toString());
                    signup.setPassword(et3.getText().toString());
                    signup.setMobile_no(et5.getText().toString());
                    signup.setGender(gender);
                    signup.setAge(Integer.parseInt(et6.getText().toString()));
                    signup.setCity(et7.getText().toString());

                    databaseHelper.InsertSignUpRecord(signup);
                    //databaseHelper.InsertInitialValues();

                    Toast.makeText(SignUpActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent1);
                }
                }
        };
        b1.setOnClickListener(signupbtn);

        View.OnClickListener already_acc = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent2);
            }
        };
        b2.setOnClickListener(already_acc);
    }
}