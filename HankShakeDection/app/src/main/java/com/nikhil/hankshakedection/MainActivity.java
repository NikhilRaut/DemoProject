package com.nikhil.hankshakedection;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AccelerometerListener {

    boolean flag = true;
    Camera cam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        cam = Camera.open();
        Camera.Parameters p = cam.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        cam.setParameters(p);
        cam.startPreview();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                startService(new Intent(new MyService(MainActivity.this)))

                Intent dialogIntent = new Intent(MainActivity.this, MyService.class);
                startService(dialogIntent);
            }
        });
//        new RunBackground().execute();

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


    @Override
    public void onAccelerationChanged(float x, float y, float z) {
//        Toast.makeText(this, "Onshaker Yaaahoooooooooooo...?", Toast.LENGTH_LONG).show();
        Log.e("onAccelerationChanged", "X - " + x + "  Y - " + y + "  Z -- " + z);
    }

    @Override
    public void onShake(float force) {
        Toast.makeText(this, "Onshaker Yaaahoooooooooooo...?", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
//        Toast.makeText(getBaseContext(), "onResume Accelerometer Started",
//                Toast.LENGTH_SHORT).show();

        //Check device supported Accelerometer senssor or not
//        if (AccelerometerManager.isSupported(this)) {
//
//            //Start Accelerometer Listening
//            AccelerometerManager.startListening(this);
//        }

    }

    @Override
    public void onStop() {
        super.onStop();

        //Check device supported Accelerometer senssor or not
//        if (AccelerometerManager.isListening()) {
//
//            //Start Accelerometer Listening
//            AccelerometerManager.stopListening();
//
//            Toast.makeText(getBaseContext(), "onStop Accelerometer Stoped",
//                    Toast.LENGTH_SHORT).show();
//        }

    }

    private class RunBackground extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Log.e("doInBackground", "Start");
                Thread.sleep(3000);
                Log.e("doInBackground", "stop");
            } catch (InterruptedException e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            new RunBackground().execute();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Sensor", "Service  distroy");

        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {

            //Start Accelerometer Listening
            AccelerometerManager.stopListening();

            Toast.makeText(getBaseContext(), "onDestroy Accelerometer Stoped",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
