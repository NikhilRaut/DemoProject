package com.ormdatabase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.orm.SugarContext;
import com.ormdatabase.API.RetrofitAPI;
import com.ormdatabase.DBModel.Part;
import com.ormdatabase.DBModel.Retailer;
import com.ormdatabase.DBModel.TimeTableResponce;
import com.ormdatabase.DBModel.Timetable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SugarContext.init(this);

        textView = (TextView) findViewById(R.id.text);

        ((Button) findViewById(R.id.btnSave)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        ((Button) findViewById(R.id.btnGet)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                login();
            }
        });
    }

    private void getData() {
        List<Retailer> retailerList = Retailer.listAll(Retailer.class);
        textView.setText("Total Numbet : " + retailerList.size());
    }

    private void saveData() {

        RetrofitAPI.getInstance(this).getApi().getRetailers(new Callback<List<Retailer>>() {
            @Override
            public void success(List<Retailer> dashboardResponse, Response response) {
                if (dashboardResponse == null)
                    return;

                Retailer.saveInTx(dashboardResponse);
//                for (int i = 0; i < dashboardResponse.size(); i++) {
//                    Retailer.save(dashboardResponse.get(i));
//                }

            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(getApplicationContext(), "Server is not responding, Try after some time.", Toast.LENGTH_SHORT).show();

            }
        });


        RetrofitAPI.getInstance(this).getApi().getParts(new Callback<List<Part>>() {
            @Override
            public void success(List<Part> dashboardResponse, Response response) {
                if (dashboardResponse == null)
                    return;

                Part.saveInTx(dashboardResponse);

            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(getApplicationContext(), "Server is not responding, Try after some time.", Toast.LENGTH_SHORT).show();

            }
        });


        RetrofitAPI.getInstance(this).getApi().getTimeTable(new Callback<TimeTableResponce>() {
            @Override
            public void success(TimeTableResponce timeTableResponce, Response response) {
                Timetable.saveInTx(timeTableResponce.getTimetable());
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Server is not responding, Try after some time.", Toast.LENGTH_SHORT).show();
            }
        });
//        Log.e("Save Data", "Before Save ");
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//        gsonBuilder.serializeNulls();
//        Gson gson = gsonBuilder.create();
//        List<Retailer> retailerList = Arrays.asList(gson.fromJson(strRetailer, Retailer[].class));
//        Log.e("Save Data", "Reer" + retailerList.toString());
//        for (int i = 0; i < retailerList.size(); i++) {
//            Retailer.save(retailerList.get(i));
//        }
//
//        Log.e("Save Data", "After Save ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void login() {
        JsonObject obj = new JsonObject();
        obj.addProperty("username", "premkumar239@gmail.com");
        obj.addProperty("password", "P?123");
        RetrofitAPI.getInstance(this).getApi().signIn(obj, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject login, Response response) {

                Log.e("retrofit", "retrofilt login success " + login);
            }

            @Override
            public void failure(RetrofitError error) {

                Log.e("retrofit", "retrofilt login fail " + error);
            }
        });
    }
}
