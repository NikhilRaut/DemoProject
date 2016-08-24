package com.nikhil.hankshakedection;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class MyService extends Service implements AccelerometerListener {
    Context context;
    RunBackground runBackground;
    Thread thread, delayThread;
    Camera cam;
    float currentLightValue;
    boolean isReady = true, isFlashOn = false;

    public MyService(Context context) {
        this.context = context;
    }

    public MyService() {
//        super("");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (AccelerometerManager.isSupported(this)) {

            //Start Accelerometer Listening
            AccelerometerManager.startListening(this);
        }
        runBackground = new RunBackground();

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (lightSensor == null) {
            Toast.makeText(this,
                    "No Light Sensor! quit-",
                    Toast.LENGTH_LONG).show();
        } else {
            float max = lightSensor.getMaximumRange();


            sensorManager.registerListener(lightSensorEventListener,
                    lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        }

        delayThread = new Thread();
        thread = new Thread();
        return super.onStartCommand(intent, flags, startId);

    }


    SensorEventListener lightSensorEventListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                final float currentReading = event.values[0];

            }
        }

    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (AccelerometerManager.isListening()) {
            //Start Accelerometer Listening
            AccelerometerManager.stopListening();

        }

    }

    @Override
    public void onAccelerationChanged(float x, float y, float z) {

    }

    @Override
    public void onShake(float force) {

        if (isReady) {
            Log.e("onShake", "is Ready");
            if (isFlashOn) {
                Log.e("onShake", "is isFlashOn");
                if (cam != null) {
                    cam.stopPreview();
                    cam.release();
                    isFlashOn = false;
                }
            } else {
                Log.e("onShake", "not isFlashOn");
                cam = Camera.open();
                Camera.Parameters p = cam.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                cam.setParameters(p);
                cam.startPreview();
                isFlashOn = true;

            }
            checkForOverLoad();

        }


//        if (AsyncTask.Status.RUNNING == runBackground.getStatus()) {
//            return;
//        } else {
//            runBackground.execute();
//        }

        Toast.makeText(getBaseContext(), "OnShake  Started", Toast.LENGTH_SHORT).show();
    }

    private void checkForOverLoad() {
        if (delayThread != null) {
            if (delayThread.isAlive())
                return;
        } else {
            delayThread = new Thread();
        }
        delayThread = new Thread() {
            @Override
            public void run() {
                try {
                    isReady = false;
                    delayThread.sleep(3000);
                    isReady = true;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        delayThread.start();
    }

    private void takePhoto() {
        if (thread != null) {
            if (thread.isAlive())
                return;
        } else {
            thread = new Thread();
        }

        Toast.makeText(getBaseContext(), "Action start", Toast.LENGTH_SHORT).show();
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    thread.sleep(2000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        thread.start();
    }


    private class RunBackground extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Log.e("doInBackground", "start");
                Thread.sleep(1000);
                Log.e("doInBackground", "stop");
            } catch (InterruptedException e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Before", "Demo test");
            takePhoto(getBaseContext());
            Log.e("After", "Demo test");
            Toast.makeText(getBaseContext(), "Photo captured :)", Toast.LENGTH_SHORT).show();
//            new RunBackground().execute();
        }
    }


    public void takePictureNoPreview(Context context) {
        // open back facing camera by default
        Camera myCamera = Camera.open();

        if (myCamera != null) {
            try {
                //set camera parameters if you want to
                //...
                // here, the unused surface view and holder
                SurfaceView dummy = new SurfaceView(context);
                myCamera.setPreviewDisplay(dummy.getHolder());
                myCamera.startPreview();

                myCamera.takePicture(null, null, getJpegCallback());
            } catch (IOException e) {
                Log.e("IOException", "" + e.toString());
            } finally {
                myCamera.release();
            }

        } else {
            //booo, failed!
        }
    }


    private Camera.PictureCallback getJpegCallback() {
        Camera.PictureCallback jpeg = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream fos;
                Long tsLong = System.currentTimeMillis() / 1000;
                String fileName = tsLong.toString();
                try {
                    File sd = Environment.getExternalStorageDirectory();
                    fos = new FileOutputStream(sd.getPath() + "/Img_" + fileName + ".jpeg");
                    fos.write(data);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    //do something about it
                }
            }
        };
        return jpeg;
    }


    @SuppressWarnings("deprecation")
    private static void takePhoto(final Context context) {

        ((Vibrator) context.getSystemService(VIBRATOR_SERVICE)).vibrate(800);
        final SurfaceView preview = new SurfaceView(context);
        SurfaceHolder holder = preview.getHolder();
        // deprecated setting, but required on Android versions prior to 3.0
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            //The preview must happen at or after this point or takePicture fails
            public void surfaceCreated(SurfaceHolder holder) {
                showMessage("Surface created");

                Camera camera = null;

                try {
                    camera = Camera.open();
                    showMessage("Opened camera");

                    try {
                        camera.setPreviewDisplay(holder);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    camera.startPreview();
                    showMessage("Started preview");

                    camera.takePicture(null, null, new Camera.PictureCallback() {

                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            showMessage("Took picture");
                            FileOutputStream fos;
                            Long tsLong = System.currentTimeMillis() / 1000;
                            String fileName = tsLong.toString();
                            try {
                                File sd = Environment.getExternalStorageDirectory();
                                fos = new FileOutputStream(sd.getPath() + "/Img_" + fileName + ".jpeg");
                                fos.write(data);
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                //do something about it
                            }
                            camera.release();
                        }
                    });
                } catch (Exception e) {
                    if (camera != null)
                        camera.release();
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                1, 1, //Must be at least 1x1
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                0,
                //Don't know if this is a safe default
                PixelFormat.UNKNOWN);

        //Don't set the preview visibility to GONE or INVISIBLE
        wm.addView(preview, params);
    }

    private static void showMessage(String message) {
        Log.i("Camera", message);
    }
}



