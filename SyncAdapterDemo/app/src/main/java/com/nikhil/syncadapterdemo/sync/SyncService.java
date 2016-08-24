package com.nikhil.syncadapterdemo.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


/**
 * Created by Raj on 15/06/15.
 */
public class SyncService extends Service {

    // Storage for an instance of the sync adapter
    private static IServeSyncAdapter sSyncAdapter = null;
    private static final Object lockSyncAdapter = new Object();

    public SyncService() {
        super();
        Log.i(Constant.ISERVE_LOG_TAG, "In SyncService : SyncService()");
    }

    /*
     * Instantiate the sync adapter object.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(Constant.ISERVE_LOG_TAG, "In SyncService : OnCreate");
        /*
         * Create the sync adapter as a singleton.
         * Set the sync adapter as syncable
         * Disallow parallel syncs
         */
        if (sSyncAdapter == null) {
            synchronized (lockSyncAdapter) {
                Log.i(Constant.ISERVE_LOG_TAG, "In SyncService : OnCreate1");
                if (sSyncAdapter == null) {
                    Log.i(Constant.ISERVE_LOG_TAG, "In SyncService : OnCreate2");
                    sSyncAdapter = new IServeSyncAdapter(getApplicationContext(), true);
                }
            }
        }
        Log.i(Constant.ISERVE_LOG_TAG, "In SyncService : OnCreate3");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(Constant.ISERVE_LOG_TAG, "In SyncService : onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        Log.i(Constant.ISERVE_LOG_TAG, "In SyncService : onBind");

       /*
         * Get the object that allows external processes
         * to call onPerformSync(). The object is created
         * in the base class code when the SyncAdapter
         * constructors call super()
         */
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
