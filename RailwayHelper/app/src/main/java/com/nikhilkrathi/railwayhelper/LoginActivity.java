package com.nikhilkrathi.railwayhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nikhilkrathi.railwayhelper.database.helper.DatabaseHelper;
import com.nikhilkrathi.railwayhelper.database.model.SignUp;

import java.nio.channels.Pipe;

public class LoginActivity extends AppCompatActivity {

    EditText et1, et2;
    TextView tv1, tv2;
    Button b1;
    long l;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv1=(TextView)findViewById(R.id.tv_signup);
        tv2=(TextView)findViewById(R.id.tv_loginError);
        et1=(EditText)findViewById(R.id.et_username);
        et2=(EditText)findViewById(R.id.et_password);
        b1=(Button)findViewById(R.id.b_login);
        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = this.getSharedPreferences("sp2", 0);
        //databaseHelper.InsertInitialValues();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean previouslyStarted = prefs.getBoolean("isFirstTime", false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean("isFirstTime", Boolean.TRUE);
            //Toast.makeText(this, "Opened first time", Toast.LENGTH_SHORT).show();
            edit.commit();
            databaseHelper.InsertInitialValues();
        }

        sqLiteDatabase = new DatabaseHelper(getApplicationContext()).getWritableDatabase();
        //l = databaseHelper.getRowCount();
        //Toast.makeText(this, String.valueOf(l), Toast.LENGTH_SHORT).show();

        View.OnClickListener login = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et1.getText().toString().isEmpty()){
                    tv2.setText("Username field cannot be empty!");
                    tv2.setVisibility(View.VISIBLE);
                }

                else if(et2.getText().toString().isEmpty()){
                    tv2.setText("Password field cannot be empty!");
                    tv2.setVisibility(View.VISIBLE);
                }

         /*       else if (databaseHelper.checkUser(et1.getText().toString(), et2.getText().toString())) {
                    Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent1);
                }
         */
                else if(databaseHelper.LoginAuthentication(et1.getText().toString(), et2.getText().toString())){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("emailID", et1.getText().toString());
                    editor.putString("password", et2.getText().toString());
                    editor.commit();
                    Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                    intent1.putExtra("emailID", et1.getText().toString());
                    intent1.putExtra("password", et2.getText().toString());
                    startActivity(intent1);
                }

                else{
                    tv2.setText("Invalid login credentials");
                    tv2.setVisibility(View.VISIBLE);
                }

                //Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                //startActivity(intent1);
            }
        };
        b1.setOnClickListener(login);

        View.OnClickListener signup = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent2);
            }
        };
        tv1.setOnClickListener(signup);

    }

}