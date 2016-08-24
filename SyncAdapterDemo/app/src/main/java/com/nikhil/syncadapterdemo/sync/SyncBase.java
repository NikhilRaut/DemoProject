//package com.nikhil.syncadapterdemo.sync;
//
//import android.text.TextUtils;
//
//import com.yahoo.squidb.data.SquidCursor;
//import com.yahoo.squidb.sql.Query;
//
//import io.iserve.iserveandroid.Constant;
//import io.iserve.iserveandroid.Global;
//import io.iserve.iserveandroid.data.db.IServeDatabase;
//import io.iserve.iserveandroid.data.db.models.DataManager;
//import io.iserve.iserveandroid.utils.PreferenceUtils;
//
///**
// * Created by Raj on 11/12/15.
// */
//public abstract class SyncBase {
//
//    protected static String _version;
//    protected static String _user_data_version;
//
//    protected SyncBase() {
//        _version = PreferenceUtils.get(Global.getContext(), Constant.Preference.DATA_VERSION);
//        _user_data_version = PreferenceUtils.get(Global.getContext(), Constant.Preference.USER_DATA_VERSION);
//    }
//
//    protected SquidCursor<DataManager> getDataManagerCursor(String model, String action) {
//
//        IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//        Query qry = Query.select().from(DataManager.TABLE).where(DataManager.OBJECT_NAME.eq(model)
//                                                            .and(DataManager.STATUS.eq(Constant.STATUS_OFFLINE)));
//        if(!TextUtils.isEmpty(action)) {
//            qry = Query.select().from(DataManager.TABLE).where(DataManager.OBJECT_NAME.eq(model)
//                                                        .and(DataManager.STATUS.eq(Constant.STATUS_OFFLINE))
//                                                        .and(DataManager.ACTION.eq(action)));
//        }
//
//        return db.query(DataManager.class, qry);
//    }
//
//    protected void onFailureRevertDataModel(String model) {
//
//        updateDataModelStatus(model, Constant.STATUS_SEND_TO_SYNC_UP, Constant.STATUS_OFFLINE);
//    }
//
//    protected void updateDataModelStatus(Long objectID, String model, String fromStatus, String toStatus) {
//
//        Query qry = Query.select().from(DataManager.TABLE).where(DataManager.OBJECT_ID.eq(objectID).and(DataManager.OBJECT_NAME.eq(model)).and(DataManager.STATUS.eq(fromStatus)));
//        IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//        SquidCursor<DataManager> dmCur = db.query(DataManager.class, qry);
//        while(dmCur.moveToNext()) {
//
//            DataManager dm = new DataManager(dmCur);
//            dm.setStatus(toStatus);
//            db.persist(dm);
//        }
//    }
//
//    protected void updateDataModelStatus(String model, String fromStatus, String toStatus) {
//
//        Query qry = Query.select().from(DataManager.TABLE).where(DataManager.OBJECT_NAME.eq(model).and(DataManager.STATUS.eq(fromStatus)));
//        IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//        SquidCursor<DataManager> dmCur = db.query(DataManager.class, qry);
//        while(dmCur.moveToNext()) {
//
//            DataManager dm = new DataManager(dmCur);
//            dm.setStatus(toStatus);
//            db.persist(dm);
//        }
//    }
//}
