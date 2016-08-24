package com.nikhil.readsqldb;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ((Button) findViewById(R.id.btnImp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ((Button) findViewById(R.id.btnImp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void importDB() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            String currentDBPath = "/data/" + this.getApplicationInfo().packageName + "/databases/";
            File file = new File(data.getPath() + currentDBPath + "/prrlite.db");


            if (!file.exists()) {
                if (sd.canWrite()) {

                    DBHandler_ProductList productList = new DBHandler_ProductList(this);
//                    file.mkdir();
                    File currentDB = new File(data.getPath() + currentDBPath, "parts");
                    Log.e("importDB", "before read from assate");
                    InputStream in = getAssets().open("prrlite.db");
                    Log.e("importDB", "after read from assate");
                    OutputStream out = new FileOutputStream(data.getPath() + currentDBPath + "/prrlite.db");
                    System.out.println("IMAGE @ COPY TO --------- " + data.getPath() + currentDBPath + "/parts");
                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                    System.out.println("End of  @ COPY TO --------- " + data.getPath() + currentDBPath + "/parts");
                }
            } else {
//                Toast.makeText(getBaseContext(), "Already exists", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("Exception", "Exception -- " + e.toString());
        }
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
}
