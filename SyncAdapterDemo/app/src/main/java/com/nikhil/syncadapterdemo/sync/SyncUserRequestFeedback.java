//package com.nikhil.syncadapterdemo.sync;
//
//import android.util.Log;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.yahoo.squidb.data.SquidCursor;
//
//import java.util.List;
//
//import io.iserve.iserveandroid.Constant;
//import io.iserve.iserveandroid.Global;
//import io.iserve.iserveandroid.api.RestAPIClient;
//import io.iserve.iserveandroid.api.iServeAPI;
//import io.iserve.iserveandroid.api.response.UserRequestFeedbackResponse;
//import io.iserve.iserveandroid.data.db.IServeDatabase;
//import io.iserve.iserveandroid.data.db.models.DataManager;
//import io.iserve.iserveandroid.data.db.models.UserRequestFeedback;
//import retrofit.Callback;
//import retrofit.RetrofitError;
//import retrofit.client.Response;
//
///**
// * Created by Raj on 11/12/15.
// */
//public class SyncUserRequestFeedback extends SyncBase {
//
//    public SyncUserRequestFeedback() {}
//
//    public void syncUp(){
//
//        JsonObject upData = getUserRequestFeedbacksCreateJson();
//        if(upData == null) {
//            Log.d(Constant.ISERVE_LOG_TAG, "No URF Sync create");
//            return;
//        }
//
//        iServeAPI srv = RestAPIClient.getInstance().getApi();
//        srv.syncRequestFeedbacksForCreate(upData, new Callback<List<UserRequestFeedbackResponse>>() {
//            @Override
//            public void success(List<UserRequestFeedbackResponse> userRequestFeedbackResponses, Response response) {
//
//                afterSuccess(userRequestFeedbackResponses);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//                onFailureRevertDataModel(UserRequestFeedback.class.getName());
//            }
//        });
//
//
//        upData = getUserRequestFeedbacksUpdateJson();
//        if(upData == null) {
//            Log.d(Constant.ISERVE_LOG_TAG, "No URF Sync update");
//            return;
//        }
//
//        srv.syncRequestFeedbacksForUpdate(upData, new Callback<List<UserRequestFeedbackResponse>>() {
//            @Override
//            public void success(List<UserRequestFeedbackResponse> userRequestFeedbackResponses, Response response) {
//
//                afterSuccess(userRequestFeedbackResponses);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });
//    }
//
//    private void afterSuccess(List<UserRequestFeedbackResponse> userRequestFeedbackResponses) {
//        for (UserRequestFeedbackResponse urfr : userRequestFeedbackResponses) {
//            if (urfr != null) {
//                IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//                UserRequestFeedback urf = db.fetch(UserRequestFeedback.class, urfr.getLocalId());
//                urf.merge(urfr);
//                db.persist(urf);
//
//                updateDataModelStatus(UserRequestFeedback.class.getName(), Constant.STATUS_SEND_TO_SYNC_UP, Constant.STATUS_TBD);
//            }
//        }
//    }
//
//    private JsonObject getUserRequestFeedbacksCreateJson() {
//
//        return getUserRequestFeedbacksJson(Constant.ACTION_CREATE);
//    }
//
//    private JsonObject getUserRequestFeedbacksUpdateJson() {
//
//        return getUserRequestFeedbacksJson(Constant.ACTION_UPDATE);
//    }
//
//    private JsonObject getUserRequestFeedbacksJson(String action) {
//
//        // For Create
//        SquidCursor<DataManager> dmCur = getDataManagerCursor(UserRequestFeedback.class.getName(), action);
//        if(dmCur.getCount() == 0) return null;
//
//        JsonArray upaArr = new JsonArray();
//        IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//        while(dmCur.moveToNext()) {
//
//            DataManager dm = new DataManager(dmCur);
//            if(dm.getObjectId() != 0) {
//
//                UserRequestFeedback upf = db.fetch(UserRequestFeedback.class, dm.getObjectId());
//                Log.d(Constant.ISERVE_LOG_TAG, "UPF ID:" + upf.getId());
//                if(upf != null)
//                    upaArr.add(upf.getJson());
//
//                dm.setStatus(Constant.STATUS_SEND_TO_SYNC_UP);
//                db.persist(dm);
//            }
//        }
//
//        JsonObject upData = new JsonObject();
//        upData.add("user_request_feedbacks", upaArr);
//        return upData;
//    }
//}
