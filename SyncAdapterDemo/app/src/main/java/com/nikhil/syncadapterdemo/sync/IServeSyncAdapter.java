package com.nikhil.syncadapterdemo.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;


import java.text.SimpleDateFormat;
import java.util.Calendar;

//import io.iserve.iserveandroid.api.deserializers.OrganizationListDeserializer;


/**
 * Created by Raj on 15/06/15.
 */
public class IServeSyncAdapter extends AbstractThreadedSyncAdapter {

    /**
     * Network connection timeout, in milliseconds.
     */
    private static final int NET_CONNECT_TIMEOUT_MILLIS = 15000;  // 15 seconds
    /**
     * Network read timeout, in milliseconds.
     */
    private static final int NET_READ_TIMEOUT_MILLIS = 10000;  // 10 seconds
    //    public static final int SYNC_INTERVAL =  60 * 60 * 24 * 7;
    public static final int SYNC_INTERVAL = 60 * 60 * 24; // Per day
    //    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
    public static final int SYNC_FLEXTIME = 1000;

    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";

    private final ContentResolver mContentResolver;
    private final AccountManager mAccountManager;

    public IServeSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
        mAccountManager = AccountManager.get(context);

        Log.e(Constant.ISERVE_LOG_TAG, "*** IServeSyncAdapter Constructor 1 *** ");
    }

    public IServeSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
        mAccountManager = AccountManager.get(context);

        Log.e(Constant.ISERVE_LOG_TAG, "*** IServeSyncAdapter Constructor 2 *** ");
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        Log.e(Constant.ISERVE_LOG_TAG, "*** Beginning network synchronization *** ");
        Log.e("SFA", "IServeSyncAdapter -- onPerformSync");
        Log.e("SFA", "IServeSyncAdapter -- onPerformSync");
        Log.e("SFA", "IServeSyncAdapter -- onPerformSync");

//        try {
//            // String authToken = mAccountManager.blockingGetAuthToken(account, AUTHTOKEN_TYPE_FULL_ACCESS, true);
////            new SyncData(new SyncData.OnSaveListener() {
////                @Override
////                public void onComplete() {
////                    Log.e(Constant.ISERVE_LOG_TAG, "*** Sync Completed *** ");
////                }
////            }).syncPartialAll();
//
//            if (extras.containsKey(Constant.SYNC_ENTITY)) {
//
//                SyncEntity se = SyncEntity.values()[extras.getInt(Constant.SYNC_ENTITY)];
//
//
//                /************** Upload Sync ***************/
//                SyncData sd = new SyncData(this);
//
//                switch (se) {
//                    case USER:
//                        Log.e(Constant.ISERVE_LOG_TAG, "Sync::USER");
//                        sd.syncUserUp();
//                        break;
//                    case USER_DETAILS_ADDRESS:
//                        Log.e(Constant.ISERVE_LOG_TAG, "Sync::USER_DETAILS_ADDRESS");
//                        sd.syncUserAddressUp();
//                        break;
//                    case USER_REQUEST:
//                        Log.e(Constant.ISERVE_LOG_TAG, "Sync::USER_REQUEST");
//                        sd.syncUserRequestUp();
//                        break;
//                    case USER_REQUEST_FEEDBACK:
//                        Log.e(Constant.ISERVE_LOG_TAG, "Sync::USER_REQUEST_FEEDBACK");
//                        SyncUserRequestFeedback obj = new SyncUserRequestFeedback();
//                        obj.syncUp();
//                        break;
//                    case USER_PRODUCT_DETAILS:
//                        Log.e(Constant.ISERVE_LOG_TAG, "Sync::USER_PRODUCT_DETAILS");
//                        sd.syncUserProductAttributeUp();
//                        break;
//                    case USER_PRODUCT_FOLLOW:
//                        Log.e(Constant.ISERVE_LOG_TAG, "Sync::USER_PRODUCT_FOLLOW");
//                        sd.syncUserProductFollowUp();
//                        break;
//                    case USER_PRODUCT_UNFOLLOW:
//                        Log.e(Constant.ISERVE_LOG_TAG, "Sync::USER_PRODUCT_UNFOLLOW");
//                        sd.syncUserProductUnfollowUp();
//                        break;
//                    case USER_PRODUCT_FEEDBACK:
//                        Log.e(Constant.ISERVE_LOG_TAG, "Sync::USER_PRODUCT_FEEDBACK");
//                        sd.syncUserProductFeedbackUp();
//                        break;
//                    // *********** TODO **************
//                    case USER_ATTRIBUTES:
//                        Log.e(Constant.ISERVE_LOG_TAG, "Sync::USER_ATTRIBUTES");
//                        break;
//                    case USER_DETAILS_CONTACT:
//                        Log.e(Constant.ISERVE_LOG_TAG, "Sync::USER_DETAILS_CONTACT");
//                        break;
//                    case USER_DETAILS_OTHER:
//                        Log.e(Constant.ISERVE_LOG_TAG, "Sync::USER_DETAILS_OTHER");
//                        break;
//                    // *********** END:TODO **************
//                }
//            }
//        } catch (Exception e) {
//
//            Log.e(Constant.ISERVE_LOG_TAG, "Failed to connect.", e);
//            syncResult.stats.numParseExceptions++;
//            return;
//        }

        Log.i(Constant.ISERVE_LOG_TAG, "Network synchronization complete");
    }

    public static void initializeSyncAdapter(Context context) {
//        getSyncAccount(context);

        Log.e("SFA", "IServeSyncAdapter -- initializeSyncAdapter");
        Account account = IServeSyncAdapter.getSyncAccount(context);
        ContentResolver.setIsSyncable(account, Constant.AUTHORITY, 1);
        /*
         * Since we've created an account
         */
        configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(account, Constant.AUTHORITY, true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {

        Log.e(Constant.ISERVE_LOG_TAG, "*** getSyncAccount: Start *** ");

        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account("SFA", Constant.ACCOUNT_TYPE);

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

//            onAccountCreated(newAccount, context);
        }

        Log.e(Constant.ISERVE_LOG_TAG, "*** getSyncAccount: END *** ");

        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, Constant.AUTHORITY, true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

//    private static void notifyChange(){
//        List<String> pathSegments = uri.getPathSegments();
//        String mySetID = pathSegments.get(2);
//        Uri mySetUri = Uri.parse("content://" + AUTHORITY + "/" + TABLE_PATH + "/Set/" + mySetID);
//        getContext().getContentResolver().notifyChange(mySetUri, null);
//    }

    /**
     * Helper method to have the sync adapter sync immediately
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {

        Log.e(Constant.ISERVE_LOG_TAG, "*** Beginning network synchronization *** ");

        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        Account account = IServeSyncAdapter.getSyncAccount(context);
        ContentResolver.setIsSyncable(account, Constant.AUTHORITY, 1);
        ContentResolver.requestSync(account, Constant.AUTHORITY, bundle);
    }

    public static void syncUserImmediately(Context context, SyncEntity syncEntity) {

        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putInt(Constant.SYNC_ENTITY, syncEntity.ordinal());

        Account account = IServeSyncAdapter.getSyncAccount(context);
        ContentResolver.setIsSyncable(account, Constant.AUTHORITY, 1);
        ContentResolver.requestSync(account, Constant.AUTHORITY, bundle);
    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Log.e(Constant.ISERVE_LOG_TAG, "*** configurePeriodicSync: Beginning network synchronization *** ");

        Account account = getSyncAccount(context);
        String authority = Constant.AUTHORITY; // context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account, authority, new Bundle(), syncInterval);
        }
    }

//    @Override
//    public void onComplete() {
//
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss a");
//
////        PreferenceUtils.set(Global.getContext(), Constant.SYNC_LASTTIME, sdf.format(c.getTime()));
////        Log.d(Constant.ISERVE_LOG_TAG, "***** Sync Completed Successfully *****");
////        FlurryAgent.logEvent("Sync Completed Successfully");
//    }
}
