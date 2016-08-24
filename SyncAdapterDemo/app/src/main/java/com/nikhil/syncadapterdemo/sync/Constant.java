package com.nikhil.syncadapterdemo.sync;

import java.io.File;


/**
 * Created by Raj on 08/06/15.
 */
public class Constant {

    public static final String ISERVE_API_URL = "http://iserve.io";
    //    public static final String ISERVE_API_URL = "http://192.168.1.4:3000";
    public static final String ISERVE_LOG_TAG = "ISERVE_LOG";
    public static final String APP_NAME_ISERVE = "iserve";
    public static final String ISERVE_ACCOUNT = "iServe";
    public static final String ACCOUNT_TYPE = "io.iserve.iserveandroid";// String.valueOf(R.string.account_type);
    public static final String AUTHORITY = "io.iserve.iserveandroid.provider";

    public static final String SYNC_ENTITY = "sync_entity";
    /* GCM data sync */
    public static final String GCM_AUTHORITY = "io.iserve.android.sync.provider";
    // Incoming Intent key for extended data
    public static final String KEY_SYNC_REQUEST = "key_sync_request";

    // Important Keys
    public static final String KEY_FLURRY_API = "JFVJGVSR3FRMCGJY5ZDK";
    public static final String ISERVE_GOOGLE_PROJECT_ID = "66836132014";
    public static final String ISERVE_GOOGLE_PROJECT_NAME = "iserve-e50de";

    public static final String AUTH_TYPE_PWD = "password";
    public static final String AUTH_TOKEN_TYPE_BEARER = "bearer";
    public static final String AUTH_GRANT_TYPE_PWD = "password";

    public static final String AUTH_ASSERTION = "assertion";

    public static final String ACCESS_TOKEN = "access_token";


    public static final String AUTH_TOKEN_TYPE = "authTokenType";
    public static final String AUTH_REQUIRED_FEATURES = "requiredFeatures";
    public static final String AUTH_LOGIN_OPTIONS = "loginOptions";
    public static final String STR_ACCOUNT = "account";

    public static final String AUTH_CLIENT_ID = "7dbfafecb75f51f103912b65fc6a1c324785b378251a82502508b9d6fadd59f0";
    public static final String AUTH_CLIENT_SECRET = "a2c157f59d326119e7f70ea9e4c1ae30af393dea3d2e840445f9e9ed3aba09a4";

    public static final String ARG_ACCOUNT_TYPE = "account_type";
    public static final String ARG_AUTH_TYPE = "auth_type";
    public static final String ARG_AUTH_TOKEN_TYPE = "authTokenType";
    public static final String ARG_LOGIN_OPTIONS = "loginOptions";
    public static final String ARG_IS_NEW_ACCOUNT = "is_new_account";
    public static final String ARG_REQUIRED_FEATURES = "requiredFeatures";

    public static final String SERVICE_TYPE_REGISTRATION = "registration";
    public static final String SERVICE_TYPE_GENERAL = "general";

    public static final String CTRL_DATATYPE_STRING = "string";
    public static final String CTRL_DATATYPE_NUMERIC = "numeric";
    public static final String CTRL_DATATYPE_CHECKBOX = "checkbox";
    public static final String CTRL_DATATYPE_RADIO = "radio";
    public static final String CTRL_DATATYPE_DATE = "date";
    public static final String CTRL_DATATYPE_SELECT = "select";
    public static final String CTRL_DATATYPE_EMAIL = "email";
    public static final String CTRL_DATATYPE_PHOTO = "photo";
    public static final String CTRL_DATATYPE_FILE = "file";
    public static final String CTRL_DATATYPE_LOCATION = "location";

    public static class Preference {
        public static final String ISERVE = "io.iserve.android.preference";
        public static final String DATA_VERSION = "data_version";
        public static final String USER_DATA_VERSION = "user_data_version";
        public static final String APP_ACCESS_TOKEN = "app_access_token";
        public static final String GOOGLE_REG_ID = "GOOGLE_REG_ID";
        public static final String CURRENT_TAB_POSITION = "current_tab_position";
    }

    /* Status */
    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_OFFLINE = "offline";
    public static final String STATUS_SEND_TO_SYNC_UP = "send_to_sync_up";
    public static final String STATUS_TBD = "tbd";

    /* action */
    public static final String ACTION_CREATE = "create";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_DONE = "done";
    public static final String ACTION_FOLLOW = "follow";
    public static final String ACTION_UNFOLLOW = "unfollow";

    public static final String ACTIVE_TAB = "active_tab";

    public static final String SYNC_LASTTIME = "lastsync";

    public static final String ARG_ATTR_OBJECT = "attr_object";

    public static final String STR_ATTR_TYPE_IMAGE = "image";
    public static final String STR_ATTR_TYPE_TEXT = "text";
    public static final String STR_ATTR_TYPE_DATE = "date";
    public static final String STR_USER_PRODUCT_ATTRIBUTE = "user_product_attribute";
    public static final String STR_PRODUCT = "product";

    public static final String ORG_ID = "OrgID";
    public static final String ORG_NAME = "OrgName";
    public static final String ADDRESS_ID = "AddressID";
    public static final String BUNDLE_ATTR_PRODUCT_ID = "ProductID";
    public static final String BUNDLE_ATTR_SUPPORT_SERVICE_ID = "SupportServiceID";
    public static final String BUNDLE_ATTR_SUPPORT_SERVICE_NAME = "SupportServiceName";
    public static final String BUNDLE_ATTR_USER_REQUEST = "UserRequest";
    public static final String BUNDLE_ATTR_USER_REQUEST_ATTRIBUTE = "UserRequestAttribute";
    public static final String BUNDLE_LOCAL_REQUEST_ID = "local_request_id";
    public static final String BUNDLE_REQUEST_ID_SVR = "request_id_svr";
    public static final String BUNDLE_OBJECT_USER_REQUEST_VIEW = "user_request_view";


    public static final String LANDING_TAB_MY_REQUESTS = "Requests";
    public static final String LANDING_TAB_ORGANIZATIONS = "Organizations";
    public static final String LANDING_TAB_MY_PRODUCTS = "Products";
    public static final String SET_ACTIVE_TAB = "set_active_tab";
    public static final int PRODUCT_DETAILS_MY_DETAILS = 1;

    public static final int CHOOSE_PHOTO = 1;
    public static final int TAKE_PHOTO = 2;

    public static final String PROVIDER_FACEBOOK = "facebook";
    public static final String PROVIDER_ISERVE = "iServe";
    public static final String CONTENT_PROVIDER_AUTHORITY = "io.iserve.iserveandroid.provider";

//    public static final String IMG_DIR_ISERVE = "iserve";
//    public static final String IMG_DIR_IMAGE = "images";
    /***
     * /iserve/images/
     ***/
//    public static final String IMG_DIR_ROOT = File.separator + IMG_DIR_ISERVE + File.separator + IMG_DIR_IMAGE + File.separator;
//    public static final String IMG_DIR_ORG = "organizations";

    public static final String MODE_ENABLE = "enable";
    public static final String KEY_DATE = "date";
    public static final String KEY_TEXT_KEY = "text_key";
    public static final String KEY_TEXT_VALUE = "text_value";

    public static final String IMG_DIR_ISERVE = "iserve";
    public static final String IMG_DIR_IMAGE = "images";
    /***
     * /iserve/images/
     ***/
    public static final String IMG_DIR_ROOT = File.separator + IMG_DIR_ISERVE + File.separator + IMG_DIR_IMAGE + File.separator;
    public static final String IMG_DIR_ORG = "organizations";

    public static final String ACTION = "action";

    public static final int TAB_ORG = 0;
    public static final int TAB_PRODUCT = 1;
    public static final int TAB_REQUEST = 2;

    public static class FragmentTag {
        public static final String FRAGMENT_PROFILE = "FRAGMENT_PROFILE";
        public static final String FRAGMENT_RESET_PASSWORD = "FRAGMENT_RESET_PASSWORD";
        public static final String FRAGMENT_ADDRESS = "FRAGMENT_ADDRESS";
//        public static final String FRAGMENT_ADDRESS = "FRAGMENT_ADDRESS";

    }

    public static class Action {
        public static final int NONE = 0;
        public static final int UPDATE = 1;
        public static final int CREATE = 2;

    }

}
