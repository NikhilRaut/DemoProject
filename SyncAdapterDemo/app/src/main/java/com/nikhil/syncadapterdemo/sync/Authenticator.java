package com.nikhil.syncadapterdemo.sync;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.nikhil.syncadapterdemo.MainActivity;


/**
 * Created by Raj on 16/06/15.
 */
public class Authenticator extends AbstractAccountAuthenticator {

    Context mContext = null;

    public Authenticator(Context context) {
        super(context);

        Log.d(Constant.ISERVE_LOG_TAG, "*** Authenticator Constructor *** ");
        this.mContext = context;

//        mLoginAPI = new RestClient().getLoginApi(); //.getLoginAPI(AccessToken.class, new AccessTokenDeserializer());
    }

    /*
        Important method

     */

//    public static final String KEY_AUTH_TOKEN_TYPE = "authTokenType";
//    public static final String KEY_REQUIRED_FEATURES = "requiredFeatures";
//    public static final String KEY_LOGIN_OPTIONS = "loginOptions";
//    public static final String KEY_ACCOUNT = "account";

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response,
                             String accountType,
                             String authTokenType,
                             String[] requiredFeatures,
                             Bundle options) throws NetworkErrorException {

        Log.d(Constant.ISERVE_LOG_TAG, "*** Authenticator.addAccount *** ");

        final Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra(Constant.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(Constant.ARG_AUTH_TOKEN_TYPE, authTokenType);
        intent.putExtra(Constant.ARG_REQUIRED_FEATURES, requiredFeatures);
        intent.putExtra(Constant.ARG_LOGIN_OPTIONS, options);
        intent.putExtra(Constant.ARG_IS_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }


    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {

        Log.d(Constant.ISERVE_LOG_TAG, "*** Authenticator.getAuthToken ***");

        // Extract the username and password from the Account Manager, and ask
        // the server for an appropriate AuthToken.
        final AccountManager am = AccountManager.get(mContext);

        String authToken = am.peekAuthToken(account, authTokenType);

        // Lets give another try to authenticate the user
        if (TextUtils.isEmpty(authToken)) {
            final String password = am.getPassword(account);
            if (password != null) {

//                LoginAPI lgn = new RestClient().getLoginApi(); // .getLoginAPI(AccessToken.class, new AccessTokenDeserializer());
//                byte[] credentials = (Constant.AUTH_CLIENT_ID + ":" + Constant.AUTH_CLIENT_SECRET).getBytes();
//                String basicAuth = "Basic " + Base64.encodeToString(credentials, Base64.DEFAULT);
//
//                AccessToken at = lgn.signIn(Constant.AUTH_GRANT_TYPE_PWD, account.name, password, basicAuth);
//                authToken = at.getAccessToken();
            }
        }

        // If we get an authToken - we return it
        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity.
        final Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(Constant.ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(Constant.ARG_AUTH_TYPE, authTokenType);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }


    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {

        Log.d(Constant.ISERVE_LOG_TAG, "*** Authenticator.getAuthToken ***");

        if (options != null && options.containsKey(AccountManager.KEY_PASSWORD)) {
            final String password = options.getString(AccountManager.KEY_PASSWORD);
        }

        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(Constant.STR_ACCOUNT, account.name);
        intent.putExtra(Constant.ARG_LOGIN_OPTIONS, options);

        setIntentFlags(intent);

        Bundle resultBundle = new Bundle();
        resultBundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return resultBundle;
    }

    private void setIntentFlags(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {

        final Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(Constant.STR_ACCOUNT, account.name);
        intent.putExtra(Constant.ARG_LOGIN_OPTIONS, options);
        intent.putExtra(Constant.ARG_AUTH_TOKEN_TYPE, authTokenType);
        setIntentFlags(intent);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        final Bundle result = new Bundle();
        result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, true);
        return result;
    }
}
