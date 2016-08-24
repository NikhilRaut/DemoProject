package com.nikhil.syncadapterdemo.sync;

import android.accounts.Account;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


/**
 * Created by Raj on 16/06/15.
 */
public class AuthenticatorService extends Service {

    private Authenticator authenticator;
//    public static final String ACCOUNT_NAME = "iserve";
//    private static final String ACCOUNT_TYPE = String.valueOf(R.string.sync_account_type);

    /**
     * Obtain a handle to the {@link android.accounts.Account} used for sync in this application.
     *
     * @return Handle to application's account (not guaranteed to resolve unless CreateSyncAccount()
     *         has been called)
     */
    public static Account GetAccount() {
        // Note: Normally the account name is set to the user's identity (username or email
        // address). However, since we aren't actually using any user accounts, it makes more sense
        // to use a generic string in this case.
        //
        // This string should *not* be localized. If the user switches locale, we would not be
        // able to locate the old account, and may erroneously register multiple accounts.
//        final String accountName = ACCOUNT_NAME;
        return new Account(Constant.ISERVE_ACCOUNT, Constant.ACCOUNT_TYPE);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(Constant.ISERVE_LOG_TAG, "In AuthenticatorService : OnCreate");

        authenticator = new Authenticator(this);
    }

    /*
    * When the system binds to this Service to make the RPC call
    * return the authenticator’s IBinder.
    */
    @Override
    public IBinder onBind(Intent intent) {

        return authenticator.getIBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(Constant.ISERVE_LOG_TAG, "*** AuthenticatorService.Service destroyed");
    }

    public class AuthenticatorBinder extends Binder {

        public AuthenticatorService getService() {

            Log.i(Constant.ISERVE_LOG_TAG, "In AuthenticatorService : getService");

            return AuthenticatorService.this;
        }
    }
}