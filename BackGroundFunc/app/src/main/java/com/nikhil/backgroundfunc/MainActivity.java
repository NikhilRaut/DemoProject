package com.nikhil.backgroundfunc;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {

    //    String downLoadUrl = "http://dl.enjoypur.vc/upload_file/5570/5745/DJ%20Remix%20Bollywood%20Songs%202016%20-%20Singles/Dj%20Remix%20Singles%20-%20Januvary%202016/Sanam%20Teri%20Kasam%20-%20DJ%20Zedi%20Ft.%20Drake%20%5BHip%20Hop%20Remix%5D.mp3";
    String downLoadUrl = "https://www.youtube.com/watch?v=s7YYt9_KfsM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                new DownloadManager(MainActivity.this).execute();
            }
        });
    }


    private class DownloadManager extends AsyncTask<String, Integer, Drawable> {

        private Drawable d;
        private HttpURLConnection conn;
        private InputStream stream; //to read
        private ByteArrayOutputStream out; //to write
        private Context mCtx;

        private double fileSize;
        private double downloaded; // number of bytes downloaded
        private int status = DOWNLOADING; //status of current process

        private ProgressDialog progressDialog;

        private static final int MAX_BUFFER_SIZE = 1024; //1kb
        private static final int DOWNLOADING = 0;
        private static final int COMPLETE = 1;

        public DownloadManager(Context ctx) {
            d = null;
            Log.e("DownloadManager", "WEWEWEEWEWEWEWEWE");
            conn = null;
            fileSize = 0;
            downloaded = 0;
            status = DOWNLOADING;
            mCtx = ctx;
        }

        public boolean isOnline() {
            try {
                Log.e("isOnline", "WEWEWEEWEWEWEWEWE");
                ConnectivityManager cm = (ConnectivityManager) mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
                return cm.getActiveNetworkInfo().isConnectedOrConnecting();
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected Drawable doInBackground(String... url) {
            try {
//                String filename = url[1];

                Log.e("doInBackground", "WEWEWEEWEWEWEWEWE");

//                if (isOnline()) {
                Log.e("DDDDDDDDDDD", "WEWEWEEWEWEWEWEWE");
                conn = (HttpURLConnection) new URL(downLoadUrl).openConnection();
                fileSize = conn.getContentLength();
                Log.e("File Size", " Size" + fileSize);
                out = new ByteArrayOutputStream((int) fileSize);
                conn.connect();

                stream = conn.getInputStream();
                // loop with step
                while (status == DOWNLOADING) {
                    byte buffer[];

                    if (fileSize - downloaded > MAX_BUFFER_SIZE) {
                        buffer = new byte[MAX_BUFFER_SIZE];
                    } else {
                        buffer = new byte[(int) (fileSize - downloaded)];
                    }
                    int read = stream.read(buffer);

                    if (read == -1) {
                        publishProgress(100);
                        break;
                    }
                    // writing to buffer
                    out.write(buffer, 0, read);
                    downloaded += read;
                    // update progress bar
                    publishProgress((int) ((downloaded / fileSize) * 100));
                } // end of while

                if (status == DOWNLOADING) {
                    status = COMPLETE;
                }
                try {
                    File sd = Environment.getExternalStorageDirectory();
                    File backupDB = new File(sd.getPath(), "/youtube.mp4");
                    FileOutputStream fos = new FileOutputStream(backupDB);
                    fos.write(out.toByteArray());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }


                d = Drawable.createFromStream((InputStream) new ByteArrayInputStream(out.toByteArray()), "filename");
                return d;
//                } // end of if isOnline
//                else {
//                    Log.e("Exception", "Else  jjjf");
//
//                    return null;
//
//                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.toString());
                return null;
            }// end of catch
        } // end of class DownloadManager()

        @Override
        protected void onProgressUpdate(Integer... changed) {
            progressDialog.setProgress(changed[0]);
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this); // your activity
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Downloading ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Drawable result) {
            progressDialog.dismiss();
            // do something
        }
    }


}


