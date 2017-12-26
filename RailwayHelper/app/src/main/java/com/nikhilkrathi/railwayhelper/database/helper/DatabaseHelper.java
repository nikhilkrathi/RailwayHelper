package com.nikhilkrathi.railwayhelper.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.nikhilkrathi.railwayhelper.database.model.SignUp;

import java.util.ArrayList;
import java.util.Random;

import static android.R.attr.name;
import static android.R.attr.password;
import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;
import static android.graphics.PorterDuff.Mode.SRC;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.os.Build.VERSION_CODES.M;
import static android.webkit.WebSettings.PluginState.ON;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DBName = "railwayHelper.db";
    private static final int DBVersion = 1;
    private Context context;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;

    public static final String train_class_table = "train_class";
    public static final String days_available_table = "days_available";
    public static final String station_table = "station";
    public static final String route_has_station_table = "route_has_station";
    public static final String user_table = "user_table";
    public static final String train_table = "train";
    public static final String passenger_table = "passenger";
    public static final String passenger_ticket_table = "passenger_ticket";
    public static final String train_status_table = "train_status";
    public static final String reservation_table = "reservation";
    public static final String route_table = "route";

    public static final String column_train_ID = "train_ID";
    public static final String column_fare_class1 = "fare_class1";
    public static final String column_seat_class1 = "seat_class1";
    public static final String column_fare_class2 = "fare_class2";
    public static final String column_seat_class2 = "seat_class2";
    public static final String column_fare_class3 = "fare_class3";
    public static final String column_seat_class3 = "seat_class3";
    public static final String column_available_days = "available_days";
    public static final String column_station_ID = "station_ID";
    public static final String column_station_name = "station_name";
    public static final String column_stop_number = "stop_number";
    public static final String column_ID = "ID";
    public static final String column_email_ID = "email_ID";
    public static final String column_password = "password";
    public static final String column_re_enter_password = "re_enter_password";
    public static final String column_fullname = "fullname";
    public static final String column_gender = "gender";
    public static final String column_age = "age";
    public static final String column_mobile = "mobile";
    public static final String column_city = "city";
    public static final String column_train_name = "train_name";
    public static final String column_train_type = "train_type";
    public static final String column_source_stn = "source_stn";
    public static final String column_destination_stn = "destination_stn";
    public static final String column_source_ID = "source_ID";
    public static final String column_destination_ID = "destination_ID";
    public static final String column_pnr = "pnr";
    public static final String column_seat_number = "seat_number";
    public static final String column_passenger_name = "passenger_name";
    public static final String column_booked_by = "booked_by";
    public static final String column_class_type = "class_type";
    public static final String column_reservation_status = "reservation_status";
    public static final String column_available_date = "available_date";
    public static final String column_booked_seats1 = "booked_seats1";
    public static final String column_waiting_seats1 = "waiting_seats1";
    public static final String column_available_seats1 = "available_seats1";
    public static final String column_booked_seats2 = "booked_seats2";
    public static final String column_waiting_seats2 = "waiting_seats2";
    public static final String column_available_seats2 = "available_seats2";
    public static final String column_booked_seats3 = "booked_seats3";
    public static final String column_waiting_seats3 = "waiting_seats3";
    public static final String column_available_seats3 = "available_seats3";
    public static final String column_reservation_date = "reservation_date";
    public static final String column_arrival_time = "arrival_time";
    public static final String column_departure_time = "departure_time";
    public static final String column_source_distance = "source_distance";
    public static final String column_seat_status = "seat_status";


    public DatabaseHelper(Context context) {
        super(context, DBName, null, DBVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTrainClassTable = "CREATE TABLE " + train_class_table +
                " (" + column_train_ID + " INT, "
                + column_fare_class1 + " VARCHAR(8), "
                + column_seat_class1 + " INT, "
                + column_fare_class2 + " VARCHAR(8), "
                + column_seat_class2 + " INT, "
                + column_fare_class3 + " VARCHAR(8), "
                + column_seat_class3 + " INT, "
                + "PRIMARY KEY (" + column_train_ID + "));";

        String createDaysAvailableTable = "CREATE TABLE " + days_available_table +
                " (" + column_train_ID + " INT, "
                + column_available_days + " VARCHAR(20), "
                + "PRIMARY KEY (" + column_train_ID + "));";

        String createStationTable = "CREATE TABLE " + station_table +
                " (" + column_station_ID + " VARCHAR(8), "
                + column_station_name + " VARCHAR(25), "
                + "PRIMARY KEY (" + column_station_ID + "));";

        String createRouteHasStationTable = "CREATE TABLE " + route_has_station_table +
                " (" + column_train_ID + " INT, "
                + column_station_ID + " VARCHAR(20), "
                + column_stop_number + " INT, "
                + "PRIMARY KEY (" + column_train_ID + ", " + column_station_ID + "));";

        String createUserTable = "CREATE TABLE " + user_table +
                //"(" + column_ID + "INTEGER AUTOINCREMENT, "
                " (" + column_email_ID + " VARCHAR(30), "
                + column_password + " VARCHAR(15), "
                + column_re_enter_password + " VARCHAR(15), "
                + column_fullname + " VARCHAR(30), "
                + column_gender + " VARCHAR(8), "
                + column_age + " INT, "
                + column_mobile + " VARCHAR(14), "
                + column_city + " VARCHAR(20), "
                + "PRIMARY KEY (" + column_email_ID + ", " + column_password + "));";
        //+ "PRIMARY KEY (" + column_ID + "));";

        String createTrainTable = "CREATE TABLE " + train_table +
                " (" + column_train_ID + " INT, "
                + column_train_name + " VARCHAR(50), "
                + column_train_type + " VARCHAR(50), "
                + column_source_stn + " VARCHAR(30), "
                + column_destination_stn + " VARCHAR(30), "
                + column_source_ID + " VARCHAR(8), "
                + column_destination_ID + " VARCHAR(8), "
                + "PRIMARY KEY (" + column_train_ID + ")"
                + "FOREIGN KEY (" + column_source_ID + ") REFERENCES "
                + station_table + "(" + column_station_ID + ") "
                + "ON UPDATE CASCADE ON DELETE CASCADE, "
                + "FOREIGN KEY (" + column_destination_ID + ") REFERENCES "
                + station_table + "(" + column_station_ID + ") "
                + "ON UPDATE CASCADE ON DELETE CASCADE);";

        String createPassengerTable = "CREATE TABLE " + passenger_table +
                " (" + column_train_ID + " INT, "
                + column_pnr + " VARCHAR(25), "
                + column_seat_number + " INT, "
                + column_seat_status + " INT, "
                + column_passenger_name + " VARCHAR(30), "
                + column_age + " INT, "
                + column_gender + " VARCHAR(8), "
                + "PRIMARY KEY (" + column_pnr + ", " + column_seat_number + ")"
                + "FOREIGN KEY (" + column_train_ID + ") REFERENCES "
                + train_table + "(" + column_train_ID + ") "
                + "ON UPDATE CASCADE ON DELETE CASCADE);";

        String createPassengerTicketTable = "CREATE TABLE " + passenger_ticket_table +
                " (" + column_train_ID + " INT, "
                + column_pnr + " VARCHAR(25), "
                + column_source_ID + " VARCHAR(8), "
                + column_destination_ID + " VARCHAR(8), "
                + column_class_type + " VARCHAR(50), "
                + column_reservation_date + " VARCHAR(25), "
                + column_booked_by + " VARCHAR(30), "
                + "PRIMARY KEY (" + column_pnr + ")"
                + "FOREIGN KEY (" + column_train_ID + ") REFERENCES "
                + train_table + "(" + column_train_ID + ") "
                + "ON UPDATE CASCADE ON DELETE CASCADE);";

        String createTrainStatusTable = "CREATE TABLE " + train_status_table +
                " (" + column_train_ID + " INT, "
                + column_available_date + " VARCHAR(20), "
                + column_booked_seats1 + " INT, "
                + column_waiting_seats1 + " INT, "
                + column_available_seats1 + " INT, "
                + column_booked_seats2 + " INT, "
                + column_waiting_seats2 + " INT, "
                + column_available_seats2 + " INT, "
                + column_booked_seats3 + " INT, "
                + column_waiting_seats3 + " INT, "
                + column_available_seats3 + " INT, "
                + "PRIMARY KEY (" + column_train_ID + ", " + column_available_date + ")"
                + "FOREIGN KEY (" + column_train_ID + ") REFERENCES "
                + train_table + "(" + column_train_ID + ") "
                + "ON UPDATE CASCADE ON DELETE CASCADE);";

        String createReservationTable = "CREATE TABLE " + reservation_table +
                " (" + column_train_ID + " INT, "
                + column_available_date + " VARCHAR(20), "
                + column_email_ID + " VARCHAR(30), "
                + column_pnr + " VARCHAR(20), "
                + column_reservation_date + " VARCHAR(12), "
                + column_reservation_status + " VARCHAR(20), "
                + "PRIMARY KEY (" + column_train_ID + ", " + column_available_date + ", " + column_email_ID + ", " + column_pnr + ")"
                + "FOREIGN KEY (" + column_train_ID + ", " + column_available_date + ") REFERENCES "
                + train_status_table + "(" + column_train_ID + ", " + column_available_date + ") "
                + "ON UPDATE CASCADE ON DELETE CASCADE "
                + "FOREIGN KEY (" + column_email_ID + ") REFERENCES "
                + user_table + "(" + column_email_ID + ") "
                + "ON UPDATE CASCADE ON DELETE CASCADE);";

        String createRouteTable = "CREATE TABLE " + route_table +
                " (" + column_train_ID + " INT, "
                + column_stop_number + " INT, "
                + column_station_ID + " VARCHAR(8), "
                + column_arrival_time + " VARCHAR(8), "
                + column_departure_time + " VARCHAR(8), "
                + column_source_distance + " INT, "
                + "PRIMARY KEY (" + column_train_ID + ", " + column_stop_number + ")"
                + "FOREIGN KEY (" + column_train_ID + ") REFERENCES "
                + train_table + "(" + column_train_ID + ") "
                + "ON UPDATE CASCADE ON DELETE CASCADE);";


        try {
            //db = openOrCreateDatabase("railwayHelper.db", Context.MODE_PRIVATE, null);
            db.execSQL(createTrainClassTable);
            db.execSQL(createDaysAvailableTable);
            db.execSQL(createStationTable);
            db.execSQL(createRouteHasStationTable);
            db.execSQL(createUserTable);
            db.execSQL(createTrainTable);
            db.execSQL(createPassengerTable);
            db.execSQL(createPassengerTicketTable);
            db.execSQL(createTrainStatusTable);
            db.execSQL(createReservationTable);
            db.execSQL(createRouteTable);
            Log.d("Database", "Tables Created");
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean previouslyStarted = prefs.getBoolean("isFirstTime", false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean("isFirstTime", Boolean.TRUE);
            edit.commit();
            Insert();
        }
*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS '" + train_status_table + "'";
        String sq2 = "DROP TABLE IF EXISTS '" + days_available_table + "'";
        String sq3 = "DROP TABLE IF EXISTS '" + station_table + "'";
        String sq4 = "DROP TABLE IF EXISTS '" + route_has_station_table + "'";
        String sq5 = "DROP TABLE IF EXISTS '" + user_table + "'";
        String sq6 = "DROP TABLE IF EXISTS '" + train_table + "'";
        String sq7 = "DROP TABLE IF EXISTS '" + passenger_table + "'";
        String sq8 = "DROP TABLE IF EXISTS '" + passenger_ticket_table + "'";
        String sq9 = "DROP TABLE IF EXISTS '" + train_status_table + "'";
        String sq10 = "DROP TABLE IF EXISTS '" + reservation_table + "'";
        String sq11 = "DROP TABLE IF EXISTS '" + route_table + "'";
        db.execSQL(sql);
        db.execSQL(sq2);
        db.execSQL(sq3);
        db.execSQL(sq4);
        db.execSQL(sq5);
        db.execSQL(sq6);
        db.execSQL(sq7);
        db.execSQL(sq8);
        db.execSQL(sq9);
        db.execSQL(sq10);
        db.execSQL(sq11);
        onCreate(db);
    }

    public void InsertSignUpRecord(SignUp signUp) {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(column_ID, signUp.getID());
        values.put(column_fullname, signUp.getFullName());
        values.put(column_email_ID, signUp.getEmailID());
        values.put(column_password, signUp.getPassword());
        values.put(column_re_enter_password, signUp.getReEnterPassword());
        values.put(column_mobile, signUp.getMobile_no());
        values.put(column_gender, signUp.getGender());
        values.put(column_age, signUp.getAge());
        values.put(column_city, signUp.getCity());

        sqLiteDatabase.insert(user_table, null, values);
        sqLiteDatabase.close();
    }

    public void InsertInitialValues() {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        //train_class table values
        ContentValues values6 = new ContentValues();
        int[] trainID_tc = new int[]{11045, 12821, 22845, 12221, 22123, 12135, 12113, 11039, 12779, 12629, 11077, 11013};
        String[] fareClass1_tc = new String[]{"1.05", "1.01", "1.01", "1.02", "1.11", "1.25", "0.85", "1.15", "1.10", "1.14", "1.15", "1.50"};
        int[] seatClass1_tc = new int[]{5,5,4,4,5,5,3,3,5,5,4,4};
        String[] fareClass2_tc = new String[]{"0.51", "0.50", "0.50", "0.51", "0.55", "0.62", "0.42", "0.58", "0.55", "0.57", "0.56", "0.75"};
        int[] seatClass2_tc = new int[]{6,6,5,5,4,4,6,6,5,5,4,4};
        String[] fareClass3_tc = new String[]{"0.17", "0.20", "0.21", "0.25", "0.27", "0.31", "0.21", "0.30", "0.27", "0.28", "0.28", "0.35"};
        int[] seatClass3_tc = new int[]{8,8,10,10,8,8,10,10,8,8,10,10};
        int trainID_tc_length = trainID_tc.length;
        int count6 = 0;
        while (trainID_tc_length != 0){
            values6.put(column_train_ID, trainID_tc[count6]);
            values6.put(column_fare_class1, fareClass1_tc[count6]);
            values6.put(column_seat_class1, seatClass1_tc[count6]);
            values6.put(column_fare_class2, fareClass2_tc[count6]);
            values6.put(column_seat_class2, seatClass2_tc[count6]);
            values6.put(column_fare_class3, fareClass3_tc[count6]);
            values6.put(column_seat_class3, seatClass3_tc[count6]);
            sqLiteDatabase.insert(train_class_table, null, values6);
            trainID_tc_length--;
            count6++;
        }


        //days_available table values
        ContentValues values1 = new ContentValues();
        int[] trainID_da = new int[]{11045, 12821, 22845, 12221, 22123, 12135, 12113, 11039, 12779, 12629, 11077, 11013};
        String[] availableDays_da = new String[]{"6", "2", "14", "27", "6", "135", "247",
                "1234567", "1234567", "35", "1234567", "1234567"};
        int trainIDLength = trainID_da.length;
        int count1 = 0;
        while (trainIDLength != 0){
            values1.put(column_train_ID, trainID_da[count1]);
            values1.put(column_available_days, availableDays_da[count1]);
            sqLiteDatabase.insert(days_available_table, null, values1);
            trainIDLength--;
            count1++;
        }

        //station_table values
        ContentValues values2 = new ContentValues();
        String[] stationID = new String[]{"DNH", "KOP", "PUNE", "SRC", "HTE", "HWH", "AJNI",
                "NGP", "G", "VSG", "NZM", "JAT", "LTT", "CBE", "YPR", "KYN", "WADI", "SBC",
                "GTL", "SA", "DD", "MMR", "BSL", "ET", "BPL", "AGC", "NDLS", "JRC", "UBL",
                "MRJ", "JHS", "MAO", "CLR", "STR", "KPG", "KNW", "GWL", "MTJ", "AK", "BD",
                "ANG", "WR", "MKU", "BSP", "DURG", "R", "ROU", "PNVL", "IGP", "TATA", "KGP",
                "AWB", "NED", "KTE", "BSB", "GAYA"};

        String[] stationName = new String[]{"Dhanbad", "Kolhapur", "Pune", "Santragachi", "Hatia",
                "Howrah", "Ajni", "Nagpur", "Gondia", "Vasco da Gama", "Hazrat Nizamuddin",
                "Jammu Tawi", "Lokmanya Tilak Terminus", "Coimbatore", "Yesvantpur", "Kalyan",
                "Wadi", "Bengaluru City", "Guntakal", "Salem", "Daund", "Manmad", "Bhusaval",
                "Itarsi", "Bhopal", "Agra Cantt", "New Delhi", "Jalandhar Cantt", "Hubballi",
                "Miraj", "Jhansi", "Madgaon", "Castle Rock", "Satara", "Kopargaon", "Khandwa",
                "Gwalior", "Mathura", "Akola", "Badnera", "Ahmadnagar", "Wardha", "Malkapur",
                "Bilaspur", "Durg", "Raipur", "Rourkela", "Panvel", "Igatpuri", "Tatanagar",
                "Kharagpur", "Aurangabad", "Nanded", "Katni", "Varanasi", "Gaya"};
        int stationIDLength = stationID.length;
        int count2 = 0;
        while (stationIDLength != 0) {
            values2.put(column_station_ID, stationID[count2]);
            values2.put(column_station_name, stationName[count2]);
            sqLiteDatabase.insert(station_table, null, values2);
            stationIDLength--;
            count2++;
        }

        //route_has_station table values
        ContentValues values3 = new ContentValues();
        int[] trainID_rhs = new int[]{11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013,
                11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039,
                11045, 11045, 11045, 11045, 11045, 11045, 11045, 11045,
                11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077,
                12113, 12113, 12113, 12113, 12113, 12113, 12113, 12113, 12113,
                12135, 12135, 12135, 12135, 12135, 12135,
                12221, 12221, 12221, 12221, 12221, 12221, 12221, 12221,
                12629, 12629, 12629, 12629, 12629, 12629, 12629, 12629,
                12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779,
                12821, 12821, 12821, 12821, 12821, 12821, 12821, 12821, 12821, 12821,
                22123, 22123, 22123, 22123, 22123, 22123, 22123,
                22845, 22845, 22845, 22845, 22845, 22845};

        String[] stationID_rhs = new String[]{ "CBE", "GTL", "KYN", "LTT", "PUNE", "SA", "SBC", "WADI",
                "AK", "BD", "G", "KOP", "KPG", "MRJ", "NGP", "PUNE",
                "BSB", "DNH", "GAYA", "KOP", "MMR", "NED", "NGP", "PUNE",
                "AGC", "DD", "ET", "GW", "JAT", "JRC", "KNW", "KPG", "NDLS", "PUNE",
                "AK", "ANG", "BD", "BSL", "DD", "MMR", "NGP", "PUNE", "WR",
                "ANG", "BD", "DD", "MMR", "NGP", "PUNE",
                "BSL", "BSP", "DD", "HWH", "MMR", "NGP", "PUNE", "TATA",
                "BPL", "BSL", "DD", "JHS", "MRJ", "NZM", "UBL", "YPR",
                "AGC", "CLR", "ET", "MAO", "MTJ", "NZM", "PUNE", "STR", "VSG",
                "BSL", "BSP", "IGP", "KGP", "NGP", "PNVL", "PUNE", "R", "SRC", "TATA",
                "AJNI", "AK", "BD", "BSL", "KPG", "PUNE", "WR",
                "AK", "DD", "DURG", "HTE", "PUNE", "ROU"};

        int[] stopNumber_rhs = new int[]{8,5,2,1,3,7,6,4,
                5,6,8,1,4,2,7,3,
                6,8,7,1,3,4,5,2,
                7,2,5,6,10,9,4,3,8,1,
                6,3,7,5,2,4,9,1,8,
                3,5,2,4,6,1,
                4,6,2,8,3,5,1,7,
                6,5,4,7,3,8,2,1,
                7,3,6,2,8,9,5,4,1,
                4,7,3,9,5,2,1,6,10,8,
                7,4,5,3,2,1,6,
                3,2,4,6,1,5};

        int trainID_rhs_length = trainID_rhs.length;
        int count3 = 0;
        while (trainID_rhs_length != 0){
            values3.put(column_train_ID, trainID_rhs[count3]);
            values3.put(column_station_ID, stationID_rhs[count3]);
            values3.put(column_stop_number, stopNumber_rhs[count3]);
            sqLiteDatabase.insert(route_has_station_table, null, values3);
            trainID_rhs_length--;
            count3++;
        }

        //train table values
        ContentValues values4 = new ContentValues();
        int[] trainID_t = new int[]{22123, 12221, 22845, 12821, 11045, 12135,
                12113, 11039, 12779, 12629, 11077, 11013,};

        String[] trainName_t = new String[]{"PUNE AJNI EXP",
                "HWH DURONTO EXP",
                "PUNE HTE EXP",
                "PUNE SRC",
                "DEEKSHABHUMI",
                "PUNE NGP EXP",
                "PUNE NGP GARIBRATH",
                "MAHARASHTRA EXP",
                "GOA EXP",
                "KTK SMP KRN EXP",
                "JHELUM EXP",
                "MUM LTT CBE EXP"};
        String[] traintype = new String[]{"AC Super Fast", "Duronto", "Super Fast", "Super Fast", "Express",
                "Super Fast", "Garibrath", "Express", "Super Fast", "Sampark Kranti", "Express", "Express"};
        String[] sourceStn = new String[]{"Pune", "Pune", "Pune", "Pune", "Kolhapur", "Pune", "Pune",
                "Kolhapur", "Vasco da Gama", "Yesvantpur", "Pune", "Lokmanya Tilak Terminus"};
        String[] destinationStn = new String[]{"Ajni", "Howrah", "Hatia", "Santragachi", "Dhanbad",
                "Nagpur", "Nagpur", "Gondia", "Hazrat Nizamuddin", "Hazrat Nizamuddin", "Jammu Tawi",
                "Coimbatore"};
        String[] sourceID = new String[]{"PUNE", "PUNE", "PUNE", "PUNE", "KOP", "PUNE", "PUNE",
                "KOP", "VSG", "YPR", "PUNE", "LTT"};
        String[] destinationID = new String[]{"AJNI", "HWH", "HTE",
                "SRC", "DNH", "NGP", "NGP", "G", "NZM", "NZM", "JAT", "CBE"};

        int trainID_t_length = trainID_t.length;
        int count4 = 0;
        while (trainID_t_length != 0){
            values4.put(column_train_ID, trainID_t[count4]);
            values4.put(column_train_name, trainName_t[count4]);
            values4.put(column_train_type, traintype[count4]);
            values4.put(column_source_stn, sourceStn[count4]);
            values4.put(column_destination_stn, destinationStn[count4]);
            values4.put(column_source_ID, sourceID[count4]);
            values4.put(column_destination_ID, destinationID[count4]);
            sqLiteDatabase.insert(train_table, null, values4);
            trainID_t_length--;
            count4++;
        }
        //train_status table values
        ContentValues values7 = new ContentValues();
        int[] trainID_ts = new int[]{11045, 11045, 11045, 11045,//4
                12821, 12821, 12821, 12821,//4
                22845, 22845, 22845, 22845, 22845, 22845, 22845, 22845,//8
                12221, 12221, 12221, 12221, 12221, 12221, 12221,//7
                22123, 22123, 22123, 22123,//4
                12135, 12135, 12135, 12135, 12135, 12135, 12135, 12135, 12135, 12135, 12135, 12135,//12
                12113, 12113, 12113, 12113, 12113, 12113, 12113, 12113, 12113, 12113, 12113, 12113, 12113,//13
                11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039,//31
                12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779,//31
                12629, 12629, 12629, 12629, 12629, 12629, 12629, 12629, 12629, 12629,//10
                11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077,//31
                11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013};//31
        String[] available_date_ts = new String[]{"10/11/2017", "17/11/2017", "24/11/2017", "1/12/2017",
                "13/11/2017", "20/11/2017", "27/11/2017", "4/11/2017",
                "12/11/2017", "15/11/2017", "19/11/2017", "22/11/2017", "26/11/2017", "29/11/2017", "3/12/2017", "6/12/2017",
                "13/11/2017", "18/11/2017", "20/11/2017", "25/11/2017", "27/11/2017", "2/12/2017", "4/12/2017",
                "10/11/2017", "17/11/2017", "24/11/2017", "1/11/2017",
                "12/11/2017", "14/11/2017", "16/11/2017", "19/11/2017", "21/11/2017", "23/11/2017", "26/11/2017", "28/11/2017", "30/11/2017", "3/12/2017", "5/12/2017", "7/12/2017",
                "8/11/2017", "11/11/2017", "13/11/2017", "15/11/2017", "18/11/2017", "20/11/2017", "22/11/2017", "25/11/2017", "27/11/2017", "29/11/2017", "2/12/2017", "4/12/2017", "6/12/2017",
                "7/11/2017", "8/11/2017", "9/11/2017", "10/11/2017", "11/11/2017", "12/11/2017", "13/11/2017", "14/11/2017", "15/11/2017", "16/11/2017", "17/11/2017", "18/11/2017", "19/11/2017", "20/11/2017", "21/11/2017", "22/11/2017", "23/11/2017", "24/11/2017", "25/11/2017", "26/11/2017", "27/11/2017", "28/11/2017", "29/11/2017", "30/11/2017", "1/12/2017", "2/12/2017", "3/12/2017", "4/12/2017", "5/12/2017", "6/12/2017", "7/12/2017",
                "7/11/2017", "8/11/2017", "9/11/2017", "10/11/2017", "11/11/2017", "12/11/2017", "13/11/2017", "14/11/2017", "15/11/2017", "16/11/2017", "17/11/2017", "18/11/2017", "19/11/2017", "20/11/2017", "21/11/2017", "22/11/2017", "23/11/2017", "24/11/2017", "25/11/2017", "26/11/2017", "27/11/2017", "28/11/2017", "29/11/2017", "30/11/2017", "1/12/2017", "2/12/2017", "3/12/2017", "4/12/2017", "5/12/2017", "6/12/2017", "7/12/2017",
                "7/11/2017", "9/11/2017", "14/11/2017", "16/11/2017", "21/11/2017", "23/11/2017", "28/11/2017", "30/11/2017", "5/12/2017", "7/12/2017",
                "7/11/2017", "8/11/2017", "9/11/2017", "10/11/2017", "11/11/2017", "12/11/2017", "13/11/2017", "14/11/2017", "15/11/2017", "16/11/2017", "17/11/2017", "18/11/2017", "19/11/2017", "20/11/2017", "21/11/2017", "22/11/2017", "23/11/2017", "24/11/2017", "25/11/2017", "26/11/2017", "27/11/2017", "28/11/2017", "29/11/2017", "30/11/2017", "1/12/2017", "2/12/2017", "3/12/2017", "4/12/2017", "5/12/2017", "6/12/2017", "7/12/2017",
                "7/11/2017", "8/11/2017", "9/11/2017", "10/11/2017", "11/11/2017", "12/11/2017", "13/11/2017", "14/11/2017", "15/11/2017", "16/11/2017", "17/11/2017", "18/11/2017", "19/11/2017", "20/11/2017", "21/11/2017", "22/11/2017", "23/11/2017", "24/11/2017", "25/11/2017", "26/11/2017", "27/11/2017", "28/11/2017", "29/11/2017", "30/11/2017", "1/12/2017", "2/12/2017", "3/12/2017", "4/12/2017", "5/12/2017", "6/12/2017", "7/12/2017"};

        int[] booked_seats1_ts = new int[]{0,0,0,0,
                0,0,0,0,
                0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,
                0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,};
        int[] waiting_seats1_ts = new int[]{0,0,0,0,
                0,0,0,0,
                0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,
                0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,};
        int[] available_seats1_ts = new int[]{5,5,5,5,
                5,5,5,5,
                4,4,4,4,4,4,4,4,
                4,4,4,4,4,4,4,
                5,5,5,5,
                5,5,5,5,5,5,5,5,5,5,5,5,
                3,3,3,3,3,3,3,3,3,3,3,3,3,
                3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,
                5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,
                5,5,5,5,5,5,5,5,5,5,
                4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,
                4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4};
        int[] booked_seats2_ts = new int[]{0,0,0,0,
                0,0,0,0,
                0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,
                0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,};
        int[] waiting_seats2_ts = new int[]{0,0,0,0,
                0,0,0,0,
                0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,
                0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,};
        int[] available_seats2_ts = new int[]{6,6,6,6,
                6,6,6,6,
                5,5,5,5,5,5,5,5,
                5,5,5,5,5,5,5,
                4,4,4,4,
                4,4,4,4,4,4,4,4,4,4,4,4,
                6,6,6,6,6,6,6,6,6,6,6,6,6,
                6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,
                5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,
                5,5,5,5,5,5,5,5,5,5,
                4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,
                4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4};
        int[] booked_seats3_ts = new int[]{0,0,0,0,
                0,0,0,0,
                0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,
                0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,};
        int[] waiting_seats3_ts = new int[]{0,0,0,0,
                0,0,0,0,
                0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,
                0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,};
        int[] available_seats3_ts = new int[]{8,8,8,8,
                8,8,8,8,
                10,10,10,10,10,10,10,10,
                10,10,10,10,10,10,10,
                8,8,8,8,
                8,8,8,8,8,8,8,8,8,8,8,8,
                10,10,10,10,10,10,10,10,10,10,10,10,10,
                10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
                8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,
                8,8,8,8,8,8,8,8,8,8,
                10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
                10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,};

        int trainID_ts_length = trainID_ts.length;
        int count7 = 0;
        while (trainID_ts_length != 0){
            values7.put(column_train_ID, trainID_ts[count7]);
            values7.put(column_available_date, available_date_ts[count7]);
            values7.put(column_booked_seats1, booked_seats1_ts[count7]);
            values7.put(column_waiting_seats1, waiting_seats1_ts[count7]);
            values7.put(column_available_seats1, available_seats1_ts[count7]);
            values7.put(column_booked_seats2, booked_seats2_ts[count7]);
            values7.put(column_waiting_seats2, waiting_seats2_ts[count7]);
            values7.put(column_available_seats2, available_seats2_ts[count7]);
            values7.put(column_booked_seats3, booked_seats3_ts[count7]);
            values7.put(column_waiting_seats3, waiting_seats3_ts[count7]);
            values7.put(column_available_seats3, available_seats3_ts[count7]);
            sqLiteDatabase.insert(train_status_table, null, values7);
            trainID_ts_length--;
            count7++;
        }



    //route table values
        ContentValues values5 = new ContentValues();
        int[] trainID_rt = new int[]{11013, 11013, 11013, 11013, 11013, 11013, 11013, 11013,
                11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077, 11077,
                12629, 12629, 12629, 12629, 12629, 12629, 12629, 12629,
                12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779, 12779,
                11039, 11039, 11039, 11039, 11039, 11039, 11039, 11039,
                12113, 12113, 12113, 12113, 12113, 12113, 12113, 12113, 12113,
                12135, 12135, 12135, 12135, 12135, 12135,
                22123, 22123, 22123, 22123, 22123, 22123, 22123,
                12221, 12221, 12221, 12221, 12221, 12221, 12221, 12221,
                22845, 22845, 22845, 22845, 22845, 22845,
                12821, 12821, 12821, 12821, 12821, 12821, 12821, 12821, 12821, 12821,
                11045, 11045, 11045, 11045, 11045, 11045, 11045, 11045};

        String[] stop_number_rt = new String[]{"1", "2", "3", "4", "5", "6", "7", "8",
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "1", "2", "3", "4", "5", "6", "7", "8",
                "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "1", "2", "3", "4", "5", "6", "7", "8",
                "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "1", "2", "3", "4", "5", "6",
                "1", "2", "3", "4", "5", "6", "7",
                "1", "2", "3", "4", "5", "6", "7", "8",
                "1", "2", "3", "4", "5", "6",
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "1", "2", "3", "4", "5", "6", "7", "8"};

        String[] station_ID_rt = new String[]{"LTT", "KYN", "PUNE", "WADI", "GTL", "SBC", "SA", "CBE",
                "PUNE", "DD", "KPG", "KNW", "ET", "GWL", "AGC", "NDLS", "JRC", "JAT",
                "YPR", "UBL", "MRJ", "DD", "BSL", "BPL", "JHS", "NZM",
                "VSG", "MAO", "CLR", "STR", "PUNE", "ET", "AGC", "MTJ", "NZM",
                "KOP", "MRJ", "PUNE", "KPG", "AK", "BD", "NGP", "G",
                "PUNE", "DD", "ANG", "MMR", "BSL", "AK", "BD", "WR", "NGP",
                "PUNE", "DD", "ANG", "MMR", "BD", "NGP",
                "PUNE", "KPG", "BSL", "AK", "BD", "WR", "AJNI",
                "PUNE", "DD", "MMR", "BSL", "NGP", "BSP", "TATA", "HWH",
                "PUNE", "DD", "AK", "DURG", "ROU", "HTE",
                "PUNE", "PNVL", "IGP", "BSL", "NGP", "R", "BSP", "TATA", "KGP", "SRC",
                "KOP", "PUNE", "MMR", "NED", "NGP", "BSB", "GAYA", "DNH"};

        String[] arrival_time_rt = new String[]{"--", "23:13", "01:50", "09:55", "13:50", "21:50", "03:35", "07:00",
                "--", "18:35", "22:14", "04:25", "07:20", "15:15", "17:07", "21:00", "04:45", "10:05",
                "--", "21:40", "03:15", "10:15", "17:50", "00:35", "06:00", "13:00",
                "--", "15:45", "17:35", "00:35", "03:55", "10:30", "12:25", "16:00", "18:20",
                "--", "16:35", "23:05", "04:35", "11:15", "13:00", "17:20", "20:15",
                "--", "18:55", "20:50", "23:55", "02:20", "04:30", "06:20", "08:05", "09:25",
                "--", "18:55", "20:50", "23:55", "06:20", "09:25",
                "--", "19:33", "22:40", "00:50", "02:25", "03:40", "05:15",
                "--", "16:20", "20:30", "22:40", "04:10", "09:55", "15:45", "19:40",
                "--", "12:05", "21:50", "06:15", "14:30", "18:20",
                "--", "13:00", "15:40", "19:30", "01:20", "05:25", "07:20", "14:15", "16:00", "18:15",
                "--", "07:25", "14:20", "21:55", "07:35", "14:45", "18:40", "23:00"};

        String[] departure_time_rt = new String[]{"22:35", "23:18", "01:55", "10:00", "14:00", "22:10", "03:40", "--",
                "17:20", "18:50", "22:15", "04:30", "07:15", "15:20", "17:12", "21:20", "04:50", "--",
                "13:45", "21:50", "03:20", "10:50", "18:00", "00:45", "06:10", "--",
                "15:10", "15:50", "17:40", "00:40", "04:10", "10:35", "12:30", "16:10", "--",
                "15:30", "16:40", "23:20", "04:40", "11:20", "13:05", "17:30", "--",
                "17:40", "19:00", "21:00", "23:59", "02:30", "04:35", "06:25", "08:10", "--",
                "17:40", "19:00", "21:00", "23:59", "06:25", "--",
                "15:15", "19:35", "22:45", "00:55", "02:30", "03:45", "--",
                "16:25", "20:35", "22:50", "04:15", "10:00", "15:55", "--",
                "10:45", "12:20", "21:55", "06:20", "14:40", "--",
                "10:30", "13:05", "15:45", "19:40", "01:25", "05:30", "07:22", "14:20", "16:05", "--",
                "23:45", "07:30", "14:25", "22:00", "07:40", "14:50", "18:50", "--"};

        int[] source_distance_rt = new int[]{0, 34, 172, 584, 814, 1134, 1353, 1531,
                0, 75, 271, 621, 804, 1285, 1404, 1600, 1964, 2176,
                0, 464, 743, 1022, 1516, 1915, 2207, 2610,
                0, 25, 85, 430, 576, 1380, 1980, 2030, 2170,
                0, 47, 325, 600, 962, 1040, 1215, 1300,
                0, 75, 159, 313, 500, 636, 715, 810, 880,
                0, 75, 159, 313, 715, 880,
                0, 270, 500, 636, 715, 810, 880,
                0, 75, 313, 500, 890, 1300, 1767, 2016,
                0, 75, 636, 1150, 1612, 1777,
                0, 119, 154, 543, 934, 1240, 1350, 1820, 1900, 2060,
                0, 325, 640, 987, 1433, 2470, 2690, 2890};

        int train_id_rt_length = departure_time_rt.length;
        int count5 = 0;
        while (train_id_rt_length != 0){
            values5.put(column_train_ID, trainID_rt[count5]);
            values5.put(column_stop_number, stop_number_rt[count5]);
            values5.put(column_station_ID, station_ID_rt[count5]);
            values5.put(column_arrival_time, arrival_time_rt[count5]);
            values5.put(column_departure_time, departure_time_rt[count5]);
            values5.put(column_source_distance, source_distance_rt[count5]);
            sqLiteDatabase.insert(route_table, null, values5);
            train_id_rt_length--;
            count5++;
        }

        sqLiteDatabase.close();
        //Toast.makeText(context, "Insert function call!", Toast.LENGTH_SHORT).show();

    }

    public boolean LoginAuthentication(String username, String password) throws SQLException {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        Cursor mCursor = mDb.rawQuery("SELECT * FROM " + user_table + " WHERE email_ID=? AND password=?;", new String[]{username, password});
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                mDb.close();
                return true;
            }
        }
        mDb.close();
        return false;
    }

    public String getFullname(String username, String password) {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        Cursor mCursor = mDb.rawQuery("SELECT " + column_fullname + " FROM " + user_table + " WHERE email_ID=? AND password=?;", new String[]{username, password});
        String itemname = null;
        if (mCursor.moveToFirst()) {
            do {
                itemname = mCursor.getString(mCursor.getColumnIndex(column_fullname));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return itemname;
    }

    public long getRowCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long cnt = DatabaseUtils.queryNumEntries(db, passenger_ticket_table);
        db.close();
        return cnt;
    }

    public ArrayList<String> getAllStationValues() {
        ArrayList<String> yourStringValues = new ArrayList<String>();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String query = "SELECT " + column_station_name + " FROM " + station_table + " ORDER BY " + column_station_name + " ASC";
        //Cursor mCursor = mDb.query(true, station_table, new String[]{column_station_name}, null, null, null, null, null, null);
        Cursor mCursor = mDb.rawQuery(query, null);
        if (mCursor.moveToFirst()) {
            do {
                yourStringValues.add(mCursor.getString(mCursor
                        .getColumnIndex(column_station_name)));
            } while (mCursor.moveToNext());
        } else {
            return null;
        }
        mDb.close();
        return yourStringValues;
    }

    public String getStnIDFromName (String stationName){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        Cursor mCursor = mDb.rawQuery("SELECT " + column_station_ID + " FROM " + station_table + " WHERE " + column_station_name + "=? ;", new String[]{stationName});
        String stnID = null;
        if (mCursor.moveToFirst()) {
            do {
                stnID = mCursor.getString(mCursor.getColumnIndex(column_station_ID));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return stnID;
    }

    public ArrayList<String> getTrainIDUserQuery(String fromStnID, String toStnID){
        ArrayList<String> yourStringValues = new ArrayList<String>();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        //String query = "SELECT " + column_train_ID + " FROM " + route_table + " WHERE " + column_station_ID + " =? "
        //        + "AND " + column_train_ID + " IN ( SELECT " + column_train_ID + " FROM " + route_table + " WHERE "
        //        + column_station_ID + " =?);";

        String query = "SELECT A." + column_train_ID + " FROM " + route_table + " A, " + route_table + " B WHERE (A." + column_stop_number + "< B." + column_stop_number + ") AND (A." + column_train_ID + " = B." + column_train_ID + ") AND (A." + column_station_ID + " =? AND B." + column_station_ID + " =?);";
        Cursor mCursor = mDb.rawQuery(query, new String[]{fromStnID, toStnID});
        if (mCursor.moveToFirst()){
            do {
                yourStringValues.add(String.valueOf(mCursor.getInt(mCursor.getColumnIndex(column_train_ID))));
            }while (mCursor.moveToNext());
        }
        else {
            yourStringValues.add("0");
        }
        mDb.close();
        return yourStringValues;
    }

    public String getRunsOnDays(int trainID){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String days = null;
        String query = "SELECT " + column_available_days + " FROM " + days_available_table + " WHERE " + column_train_ID + " =?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID)});
        if (mCursor.moveToFirst()) {
            do {
                days = mCursor.getString(mCursor.getColumnIndex(column_available_days));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return days;
    }

    public String getTrainName(int trainID){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String trainName = null;
        String query = "SELECT " + column_train_name + " FROM " + train_table + " WHERE " + column_train_ID + " =?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID)});
        if (mCursor.moveToFirst()) {
            do {
                trainName = mCursor.getString(mCursor.getColumnIndex(column_train_name));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return trainName;
    }

    public String getTrainType(int trainID){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String trainType = null;
        String query = "SELECT " + column_train_type + " FROM " + train_table + " WHERE " + column_train_ID + " =?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID)});
        if (mCursor.moveToFirst()) {
            do {
                trainType = mCursor.getString(mCursor.getColumnIndex(column_train_type));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return trainType;
    }

    public String getArrivalTime(String stationID, int train_ID){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String arrivalTime = null;
        String query = "SELECT " + column_arrival_time + " FROM " + route_table + " WHERE " + column_station_ID + " =? AND " + column_train_ID + " =?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{stationID, String.valueOf(train_ID)});
        if (mCursor.moveToFirst()) {
            do {
                arrivalTime = mCursor.getString(mCursor.getColumnIndex(column_arrival_time));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return arrivalTime;
    }

    public String getDepartureTime(String stationID, int train_ID){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String departureTime = null;
        String query = "SELECT " + column_departure_time + " FROM " + route_table + " WHERE " + column_station_ID + " =? AND " + column_train_ID + " =?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{stationID, String.valueOf(train_ID)});
        if (mCursor.moveToFirst()) {
            do {
                departureTime = mCursor.getString(mCursor.getColumnIndex(column_departure_time));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return departureTime;
    }

    public int getDistance(String stationID, int train_ID){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        int distance = 0;
        String query = "SELECT " + column_source_distance + " FROM " + route_table + " WHERE " + column_station_ID + " =? AND " + column_train_ID + " =?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{stationID, String.valueOf(train_ID)});
        if (mCursor.moveToFirst()) {
            do {
                distance = mCursor.getInt(mCursor.getColumnIndex(column_source_distance));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return distance;
    }

    public String getFareClass1(int train_ID){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String fareClass1 = null;
        String query = "SELECT " + column_fare_class1 + " FROM " + train_class_table + " WHERE " + column_train_ID + " =?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(train_ID)});
        if (mCursor.moveToFirst()) {
            do {
                fareClass1 = mCursor.getString(mCursor.getColumnIndex(column_fare_class1));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return fareClass1;

    }

    public String getFareClass2(int train_ID){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String fareClass2 = null;
        String query = "SELECT " + column_fare_class2 + " FROM " + train_class_table + " WHERE " + column_train_ID + " =?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(train_ID)});
        if (mCursor.moveToFirst()) {
            do {
                fareClass2 = mCursor.getString(mCursor.getColumnIndex(column_fare_class2));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return fareClass2;

    }

    public String getFareClass3(int train_ID){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String fareClass3 = null;
        String query = "SELECT " + column_fare_class3 + " FROM " + train_class_table + " WHERE " + column_train_ID + " =?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(train_ID)});
        if (mCursor.moveToFirst()) {
            do {
                fareClass3 = mCursor.getString(mCursor.getColumnIndex(column_fare_class3));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return fareClass3;

    }

    public int getAvailableSeats(int trainID, String date, String classType){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        int availableSeats = 0;
        String query = null;
        if(classType.equals("I")){
            query = "SELECT " + column_available_seats1 + " FROM " + train_status_table + " WHERE " + column_train_ID + " =? AND " + column_available_date + " =?;";
            Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID), date});
            if (mCursor.moveToFirst()) {
                do {
                    availableSeats = mCursor.getInt(mCursor.getColumnIndex(column_available_seats1));

                } while (mCursor.moveToNext());
            }
            mDb.close();
            return availableSeats;
        }
        else if (classType.equals("II")){
            query = "SELECT " + column_available_seats2 + " FROM " + train_status_table + " WHERE " + column_train_ID + " =? AND " + column_available_date + " =?;";
            Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID), date});
            if (mCursor.moveToFirst()) {
                do {
                    availableSeats = mCursor.getInt(mCursor.getColumnIndex(column_available_seats2));

                } while (mCursor.moveToNext());
            }
            mDb.close();
            return availableSeats;
        }
        else if (classType.equals("III")){
            query = "SELECT " + column_available_seats3 + " FROM " + train_status_table + " WHERE " + column_train_ID + " =? AND " + column_available_date + " =?;";
            Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID), date});
            if (mCursor.moveToFirst()) {
                do {
                    availableSeats = mCursor.getInt(mCursor.getColumnIndex(column_available_seats3));

                } while (mCursor.moveToNext());
            }
            mDb.close();
            return availableSeats;
        }

        return 0;
    }

    public int getWaitingSeats(int trainID, String date, String classType){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        int waitingSeats = 0;
        String query = null;
        if(classType.equals("I")){
            query = "SELECT " + column_waiting_seats1 + " FROM " + train_status_table + " WHERE " + column_train_ID + " =? AND " + column_available_date + " =?;";
            Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID), date});
            if (mCursor.moveToFirst()) {
                do {
                    waitingSeats = mCursor.getInt(mCursor.getColumnIndex(column_waiting_seats1));

                } while (mCursor.moveToNext());
            }
            mDb.close();
            return waitingSeats;
        }
        else if (classType.equals("II")){
            query = "SELECT " + column_waiting_seats2 + " FROM " + train_status_table + " WHERE " + column_train_ID + " =? AND " + column_available_date + " =?;";
            Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID), date});
            if (mCursor.moveToFirst()) {
                do {
                    waitingSeats = mCursor.getInt(mCursor.getColumnIndex(column_waiting_seats2));

                } while (mCursor.moveToNext());
            }
            mDb.close();
            return waitingSeats;
        }
        else if (classType.equals("III")){
            query = "SELECT " + column_waiting_seats3 + " FROM " + train_status_table + " WHERE " + column_train_ID + " =? AND " + column_available_date + " =?;";
            Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID), date});
            if (mCursor.moveToFirst()) {
                do {
                    waitingSeats = mCursor.getInt(mCursor.getColumnIndex(column_waiting_seats3));

                } while (mCursor.moveToNext());
            }
            mDb.close();
            return waitingSeats;
        }
        return 0;
    }

    public int getBookedSeats(int trainID, String date, String classType){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        int bookedSeats = 0;
        String query = null;
        if(classType.equals("I")){
            query = "SELECT " + column_booked_seats1 + " FROM " + train_status_table + " WHERE " + column_train_ID + " =? AND " + column_available_date + " =?;";
            Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID), date});
            if (mCursor.moveToFirst()) {
                do {
                    bookedSeats = mCursor.getInt(mCursor.getColumnIndex(column_booked_seats1));

                } while (mCursor.moveToNext());
            }
            mDb.close();
            return bookedSeats;
        }
        else if (classType.equals("II")){
            query = "SELECT " + column_booked_seats2 + " FROM " + train_status_table + " WHERE " + column_train_ID + " =? AND " + column_available_date + " =?;";
            Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID), date});
            if (mCursor.moveToFirst()) {
                do {
                    bookedSeats = mCursor.getInt(mCursor.getColumnIndex(column_booked_seats2));

                } while (mCursor.moveToNext());
            }
            mDb.close();
            return bookedSeats;
        }
        else if (classType.equals("III")){
            query = "SELECT " + column_booked_seats3 + " FROM " + train_status_table + " WHERE " + column_train_ID + " =? AND " + column_available_date + " =?;";
            Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID), date});
            if (mCursor.moveToFirst()) {
                do {
                    bookedSeats = mCursor.getInt(mCursor.getColumnIndex(column_booked_seats3));

                } while (mCursor.moveToNext());
            }
            mDb.close();
            return bookedSeats;
        }
        return 0;
    }

    public int getTotalSeats(int trainID, String classType){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        int totalSeats = 0;
        String query = null;
        if(classType.equals("I")){
            query = "SELECT " + column_seat_class1 + " FROM " + train_class_table + " WHERE " + column_train_ID + " =?;";
            Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID)});
            if (mCursor.moveToFirst()) {
                do {
                    totalSeats = mCursor.getInt(mCursor.getColumnIndex(column_seat_class1));

                } while (mCursor.moveToNext());
            }
            mDb.close();
            return totalSeats;
        }
        else if (classType.equals("II")){
            query = "SELECT " + column_seat_class2 + " FROM " + train_class_table + " WHERE " + column_train_ID + " =?;";
            Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID)});
            if (mCursor.moveToFirst()) {
                do {
                    totalSeats = mCursor.getInt(mCursor.getColumnIndex(column_seat_class2));

                } while (mCursor.moveToNext());
            }
            mDb.close();
            return totalSeats;
        }
        else if (classType.equals("III")){
            query = "SELECT " + column_seat_class3 + " FROM " + train_class_table + " WHERE " + column_train_ID + " =?;";
            Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID)});
            if (mCursor.moveToFirst()) {
                do {
                    totalSeats = mCursor.getInt(mCursor.getColumnIndex(column_seat_class3));

                } while (mCursor.moveToNext());
            }
            mDb.close();
            return totalSeats;
        }
        return 0;
    }

    public void addPassengers(int trainID, String pnr, int seatStatus, int seatNumber, String name, int age, String gender){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        int seatNo = 0;
        values.put(column_train_ID, trainID);
        values.put(column_pnr, pnr);
        values.put(column_seat_number, seatNumber);
        values.put(column_seat_status, seatStatus);
        values.put(column_passenger_name, name);
        values.put(column_age, age);
        values.put(column_gender, gender);
        sqLiteDatabase.insert(passenger_table, null, values);
        sqLiteDatabase.close();
    }

    public void makeTicket(int trainID, String pnr, String sourceID, String destinationID, String classType, String date, String bookedBy){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(column_train_ID, trainID);
        values.put(column_pnr, pnr);
        values.put(column_source_ID, sourceID);
        values.put(column_destination_ID, destinationID);
        values.put(column_class_type, classType);
        values.put(column_reservation_date, date);
        values.put(column_booked_by, bookedBy);
        sqLiteDatabase.insert(passenger_ticket_table, null, values);
        sqLiteDatabase.close();

    }

    public void updateAvailableSeats(int trainID, String date, String classType, int newValue){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] args = new String[]{String.valueOf(trainID), date};
        if(classType.equals("I")) {
            values.put(column_available_seats1, newValue);

        }
        else if(classType.equals("II")){
            values.put(column_available_seats2, newValue);
        }
        else if(classType.equals("III")){
            values.put(column_available_seats3, newValue);
        }
        sqLiteDatabase.update(train_status_table, values, column_train_ID + " =? AND " + column_available_date + " =?", args);
        sqLiteDatabase.close();
    }

    public void updateBookedSeats(int trainID, String date, String classType, int newValue){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] args = new String[]{String.valueOf(trainID), date};
        if(classType.equals("I")) {
            values.put(column_booked_seats1, newValue);
        }
        else if(classType.equals("II")){
            values.put(column_booked_seats2, newValue);
        }
        else if(classType.equals("III")){
            values.put(column_booked_seats3, newValue);
        }
        sqLiteDatabase.update(train_status_table, values, column_train_ID + " =? AND " + column_available_date + " =?", args);
        sqLiteDatabase.close();
    }

    public void updateWaitingSeats(int trainID, String date, String classType, int newValue){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] args = new String[]{String.valueOf(trainID), date};
        if(classType.equals("I")) {
            values.put(column_waiting_seats1, newValue);
        }
        else if(classType.equals("II")){
            values.put(column_waiting_seats2, newValue);
        }
        else if(classType.equals("III")){
            values.put(column_waiting_seats3, newValue);
        }
        sqLiteDatabase.update(train_status_table, values, column_train_ID + " =? AND " + column_available_date + " =?", args);
        sqLiteDatabase.close();
    }

    public ArrayList<String> getAllPNR(String emailID){
        ArrayList<String> yourStringValues = new ArrayList<String>();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String query = "SELECT " + column_pnr + " FROM " + passenger_ticket_table + " WHERE " + column_booked_by + "=?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{emailID});
        if (mCursor.moveToFirst()){
            do {
                yourStringValues.add(String.valueOf(mCursor.getInt(mCursor.getColumnIndex(column_pnr))));
            }while (mCursor.moveToNext());
        }
        else {
            yourStringValues.add("0");
        }
        mDb.close();
        return yourStringValues;
    }

    public ArrayList<String> getAllNameFromPNR(String pnr){
        ArrayList<String> yourStringValues = new ArrayList<String>();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String query = "SELECT " + column_passenger_name + " FROM " + passenger_table + " WHERE " + column_pnr + "=?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{pnr});
        if (mCursor.moveToFirst()){
            do {
                //yourStringValues.add(String.valueOf(mCursor.getInt(mCursor.getColumnIndex(column_passenger_name))));
                yourStringValues.add(mCursor.getString(mCursor.getColumnIndex(column_passenger_name)));
            }while (mCursor.moveToNext());
        }
        else {
            yourStringValues.add("0");
        }
        mDb.close();
        return yourStringValues;
    }

    public ArrayList<String> getAllAgeFromPNR(String pnr){
        ArrayList<String> yourStringValues = new ArrayList<String>();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String query = "SELECT " + column_age + " FROM " + passenger_table + " WHERE " + column_pnr + "=?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{pnr});
        if (mCursor.moveToFirst()){
            do {
                yourStringValues.add(String.valueOf(mCursor.getInt(mCursor.getColumnIndex(column_age))));
            }while (mCursor.moveToNext());
        }
        else {
            yourStringValues.add("0");
        }
        mDb.close();
        return yourStringValues;
    }

    public ArrayList<String> getAllGenderFromPNR(String pnr){
        ArrayList<String> yourStringValues = new ArrayList<String>();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String query = "SELECT " + column_gender + " FROM " + passenger_table + " WHERE " + column_pnr + "=?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{pnr});
        if (mCursor.moveToFirst()){
            do {
                //yourStringValues.add(String.valueOf(mCursor.getInt(mCursor.getColumnIndex(column_gender))));
                yourStringValues.add(mCursor.getString(mCursor.getColumnIndex(column_gender)));
            }while (mCursor.moveToNext());
        }
        else {
            yourStringValues.add("0");
        }
        mDb.close();
        return yourStringValues;
    }

    public ArrayList<String> getAllSeatNoFromPNR(String pnr){
        ArrayList<String> yourStringValues = new ArrayList<String>();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String query = "SELECT " + column_seat_number + " FROM " + passenger_table + " WHERE " + column_pnr + "=?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{pnr});
        if (mCursor.moveToFirst()){
            do {
                yourStringValues.add(String.valueOf(mCursor.getInt(mCursor.getColumnIndex(column_seat_number))));
            }while (mCursor.moveToNext());
        }
        else {
            yourStringValues.add("0");
        }
        mDb.close();
        return yourStringValues;
    }

    public ArrayList<String> getAllSeatStatusFromPNR(String pnr){
        ArrayList<String> yourStringValues = new ArrayList<String>();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String query = "SELECT " + column_seat_status + " FROM " + passenger_table + " WHERE " + column_pnr + "=?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{pnr});
        if (mCursor.moveToFirst()){
            do {
                yourStringValues.add(String.valueOf(mCursor.getInt(mCursor.getColumnIndex(column_seat_status))));
            }while (mCursor.moveToNext());
        }
        else {
            yourStringValues.add("0");
        }
        mDb.close();
        return yourStringValues;
    }

    public String getClassTypeFromPNR(String pnr){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String classType = null;
        String query = "SELECT " + column_class_type + " FROM " + passenger_ticket_table + " WHERE " + column_pnr + " =?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{pnr});
        if (mCursor.moveToFirst()) {
            do {
                classType = mCursor.getString(mCursor.getColumnIndex(column_class_type));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return classType;
    }

    public int getTrainIDFromPNR(String pnr){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        int trainID = 0;
        String query = "SELECT " + column_train_ID + " FROM " + passenger_ticket_table + " WHERE " + column_pnr + " =?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{pnr});
        if (mCursor.moveToFirst()) {
            do {
                trainID = mCursor.getInt(mCursor.getColumnIndex(column_train_ID));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return trainID;
    }

    public String getDateOfJourneyFromPNR(String pnr){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String dateOfJourney = null;
        String query = "SELECT " + column_reservation_date + " FROM " + passenger_ticket_table + " WHERE " + column_pnr + " =?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{pnr});
        if (mCursor.moveToFirst()) {
            do {
                dateOfJourney = mCursor.getString(mCursor.getColumnIndex(column_reservation_date));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return dateOfJourney;
    }

    public String getSourceIDFromPNR(String pnr){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String sourceID = null;
        String query = "SELECT " + column_source_ID + " FROM " + passenger_ticket_table + " WHERE " + column_pnr + " =?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{pnr});
        if (mCursor.moveToFirst()) {
            do {
                sourceID = mCursor.getString(mCursor.getColumnIndex(column_source_ID));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return sourceID;
    }

    public String getDestinationIDFromPNR(String pnr){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String destinationID = null;
        String query = "SELECT " + column_destination_ID + " FROM " + passenger_ticket_table + " WHERE " + column_pnr + " =?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{pnr});
        if (mCursor.moveToFirst()) {
            do {
                destinationID = mCursor.getString(mCursor.getColumnIndex(column_destination_ID));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return destinationID;
    }

    public int getRowCountOfTrainAtdate(int trainID, String date, String classType){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String query = "SELECT COUNT(" + passenger_table + "." + column_passenger_name + ") AS COUNT FROM " + passenger_table + " INNER JOIN " + passenger_ticket_table + " ON " + passenger_table + "." + column_pnr + " = " + passenger_ticket_table + "." + column_pnr + " WHERE " + passenger_table + "." + column_train_ID + " =? AND " + passenger_ticket_table + "." + column_reservation_date + " =? AND " + column_class_type + " =? GROUP BY " + passenger_table + "." + column_train_ID + ";";
        int count = 0;
        Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID), date, classType});
        if (mCursor.moveToFirst()) {
            do {
                count = mCursor.getInt(mCursor.getColumnIndex("COUNT"));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return count;
    }

    public int getMinAvailableSeatNo(int trainID, String date, String classType){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String query = "SELECT MIN(" + passenger_table + "." + column_seat_number + ") AS MIN FROM " + passenger_table + " INNER JOIN " + passenger_ticket_table + " ON " + passenger_table + "." + column_train_ID + " = " + passenger_ticket_table + "." + column_train_ID + " WHERE " + passenger_table + "." + column_train_ID + " =? AND " + passenger_ticket_table + "." + column_reservation_date + " =? AND " + column_class_type + " =? AND " + passenger_table + "." + column_seat_status + " =? AND " + passenger_table + "." + column_seat_number + " > 0 GROUP BY " + passenger_table + "." + column_train_ID + ";";
        int seatNo = 0;
        Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID), date, classType, String.valueOf(0)});
        if (mCursor.moveToFirst()) {
            do {
                seatNo = mCursor.getInt(mCursor.getColumnIndex("MIN"));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return seatNo;
    }

    public int getPassengerNoOnTicket(String pnr){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        int count = 0;
        String query = "SELECT COUNT( " + column_passenger_name + ") AS COUNT FROM " + passenger_table + " WHERE " + column_pnr  + " =? GROUP BY " + column_pnr + ";";
        Cursor mCursor = mDb.rawQuery(query, new String[]{pnr});
        if (mCursor.moveToFirst()) {
            do {
                count = mCursor.getInt(mCursor.getColumnIndex("COUNT"));

            } while (mCursor.moveToNext());
        }
        mDb.close();
        return count;
    }

    public void updateSeatStatus(String pnr, int seatNo, int newStatus){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getWritableDatabase();
        ContentValues values8 = new ContentValues();
        String[] args = new String[]{pnr, String.valueOf(seatNo)};
        values8.put(column_seat_status, newStatus);
        mDb.update(passenger_table, values8, column_pnr + " =? AND " + column_seat_number + " =?", args);
        mDb.close();
    }

    public void updateSeatNumber(String pnr, int oldSeatNo, int newSeatNo){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getWritableDatabase();
        ContentValues values8 = new ContentValues();
        String[] args = new String[]{pnr, String.valueOf(oldSeatNo)};
        values8.put(column_seat_number, newSeatNo);
        mDb.update(passenger_table, values8, column_pnr + " =? AND " + column_seat_number + " =?", args);
        mDb.close();
    }

    public void deletePassengerTicket(String pnr){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getWritableDatabase();
        mDb.delete(passenger_ticket_table, column_pnr + " =?", new String[]{pnr});
        mDb.close();
    }

    public void deletePassenger(String pnr, int seatno){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getWritableDatabase();
        mDb.delete(passenger_table, column_pnr + " =? AND " + column_seat_number + " =?", new String[]{pnr, String.valueOf(seatno)});
        mDb.close();
    }

    public ArrayList<String> getAllPNRFromInfo(int trainID, String date, String classType){
        ArrayList<String> yourStringValues = new ArrayList<String>();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String query = "SELECT " + column_pnr + " FROM " + passenger_ticket_table + " WHERE " + column_train_ID + "=? AND " + column_reservation_date + " =? AND " + column_class_type + " =?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(trainID), date, classType});
        if (mCursor.moveToFirst()){
            do {
                yourStringValues.add(String.valueOf(mCursor.getInt(mCursor.getColumnIndex(column_pnr))));
            }while (mCursor.moveToNext());
        }
        else {
            yourStringValues.add("0");
        }
        mDb.close();
        return yourStringValues;
    }

    public ArrayList<String> getAllPNRFromInfo2(int seatno){
        ArrayList<String> yourStringValues = new ArrayList<String>();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String query = "SELECT " + column_pnr + " FROM " + passenger_table + " WHERE " + column_seat_number + "=? AND " + column_seat_status + " =?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(seatno), String.valueOf(0)});
        if (mCursor.moveToFirst()){
            do {
                yourStringValues.add(String.valueOf(mCursor.getInt(mCursor.getColumnIndex(column_pnr))));
            }while (mCursor.moveToNext());
        }
        else {
            yourStringValues.add("0");
        }
        mDb.close();
        return yourStringValues;
    }

    public ArrayList<String> getAllPNRFromInfo3(int seatno){
        ArrayList<String> yourStringValues = new ArrayList<String>();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String query = "SELECT " + column_pnr + " FROM " + passenger_table + " WHERE " + column_seat_number + " =?;";
        Cursor mCursor = mDb.rawQuery(query, new String[]{String.valueOf(seatno)});
        if (mCursor.moveToFirst()){
            do {
                yourStringValues.add(String.valueOf(mCursor.getInt(mCursor.getColumnIndex(column_pnr))));
            }while (mCursor.moveToNext());
        }
        else {
            yourStringValues.add("0");
        }
        mDb.close();
        return yourStringValues;
    }

    public boolean CheckIfUserExists(String emailID) {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase mDb = helper.getReadableDatabase();
        String query = "SELECT " + column_email_ID +  " FROM " + user_table + " WHERE " + column_email_ID + " =?;";
        Cursor cursor = mDb.rawQuery(query, new String[]{emailID});
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

}