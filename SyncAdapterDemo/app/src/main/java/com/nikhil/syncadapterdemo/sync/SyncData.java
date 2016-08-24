//package com.nikhil.syncadapterdemo.sync;
//
//import android.util.Log;
//
//import com.flurry.android.FlurryAgent;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.yahoo.squidb.data.SquidCursor;
//import com.yahoo.squidb.sql.Query;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import io.iserve.iserveandroid.Constant;
//import io.iserve.iserveandroid.Global;
//import io.iserve.iserveandroid.api.RestAPIClient;
//import io.iserve.iserveandroid.api.iServeAPI;
//import io.iserve.iserveandroid.api.response.AddressResponse;
//import io.iserve.iserveandroid.api.response.CountryResponse;
//import io.iserve.iserveandroid.api.response.OrganizationResponse;
//import io.iserve.iserveandroid.api.response.ProductFaqResponse;
//import io.iserve.iserveandroid.api.response.ProductFeatureResponse;
//import io.iserve.iserveandroid.api.response.ProductFeedbackDefItemResponse;
//import io.iserve.iserveandroid.api.response.ProductFeedbackDefResponse;
//import io.iserve.iserveandroid.api.response.ProductResponse;
//import io.iserve.iserveandroid.api.response.SupportServiceAttributeResponse;
//import io.iserve.iserveandroid.api.response.SupportServiceFeedbackDefItemResponse;
//import io.iserve.iserveandroid.api.response.SupportServiceFeedbackDefResponse;
//import io.iserve.iserveandroid.api.response.SupportServiceResponse;
//import io.iserve.iserveandroid.api.response.SyncResponse;
//import io.iserve.iserveandroid.api.response.UserAttributeResponse;
//import io.iserve.iserveandroid.api.response.UserProductAttributeResponse;
//import io.iserve.iserveandroid.api.response.UserProductFeedbackItemResponse;
//import io.iserve.iserveandroid.api.response.UserProductFeedbackResponse;
//import io.iserve.iserveandroid.api.response.UserRequestAttributeResponse;
//import io.iserve.iserveandroid.api.response.UserRequestFeedbackItemResponse;
//import io.iserve.iserveandroid.api.response.UserRequestFeedbackResponse;
//import io.iserve.iserveandroid.api.response.UserRequestResponse;
//import io.iserve.iserveandroid.api.response.UserResponse;
//import io.iserve.iserveandroid.api.response.UserSyncResponse;
//import io.iserve.iserveandroid.data.db.IServeDatabase;
//import io.iserve.iserveandroid.data.db.models.Address;
//import io.iserve.iserveandroid.data.db.models.Country;
//import io.iserve.iserveandroid.data.db.models.DataManager;
//import io.iserve.iserveandroid.data.db.models.Organization;
//import io.iserve.iserveandroid.data.db.models.Product;
//import io.iserve.iserveandroid.data.db.models.ProductFaq;
//import io.iserve.iserveandroid.data.db.models.ProductFeature;
//import io.iserve.iserveandroid.data.db.models.ProductFeedbackDef;
//import io.iserve.iserveandroid.data.db.models.ProductFeedbackDefItem;
//import io.iserve.iserveandroid.data.db.models.SupportService;
//import io.iserve.iserveandroid.data.db.models.SupportServiceAttribute;
//import io.iserve.iserveandroid.data.db.models.SupportServiceFeedbackDef;
//import io.iserve.iserveandroid.data.db.models.SupportServiceFeedbackDefItem;
//import io.iserve.iserveandroid.data.db.models.User;
//import io.iserve.iserveandroid.data.db.models.UserAttribute;
//import io.iserve.iserveandroid.data.db.models.UserProduct;
//import io.iserve.iserveandroid.data.db.models.UserProductAttribute;
//import io.iserve.iserveandroid.data.db.models.UserProductFeedback;
//import io.iserve.iserveandroid.data.db.models.UserProductFeedbackItem;
//import io.iserve.iserveandroid.data.db.models.UserRequest;
//import io.iserve.iserveandroid.data.db.models.UserRequestAttribute;
//import io.iserve.iserveandroid.data.db.models.UserRequestFeedback;
//import io.iserve.iserveandroid.data.db.models.UserRequestFeedbackItem;
//import io.iserve.iserveandroid.utils.ModelUtils;
//import io.iserve.iserveandroid.utils.PreferenceUtils;
//import retrofit.Callback;
//import retrofit.RetrofitError;
//import retrofit.client.Response;
//
///**
// * Created by Raj on 14/10/15.
// */
//// Call this class from syncadapter and intentservice
//public class SyncData {
//
//    public interface OnSaveListener {
//        void onComplete();
//    }
//
//    protected static String _version;
//    protected static String _user_data_version;
//
//    OnSaveListener _listener;
//
//    public SyncData(OnSaveListener listener) {
//        _listener = listener;
//        _version = PreferenceUtils.get(Global.getContext(), Constant.Preference.DATA_VERSION);
//        _user_data_version = PreferenceUtils.get(Global.getContext(), Constant.Preference.USER_DATA_VERSION);
//    }
//
//    protected void updateVersion() {}
//
//    public void syncUserUp() {
//
//        JsonObject json = getUserJson();
//        if(json == null) return;
//
//        iServeAPI srv = RestAPIClient.getInstance().getApi();
//        srv.updateUser(json, new Callback<JsonObject>() {
//            @Override
//            public void success(JsonObject jsonObject, Response response) {
//
//                updateDataModelStatus(Product.class.getName(), Constant.STATUS_SEND_TO_SYNC_UP, Constant.STATUS_TBD);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//                updateDataModelStatus(Product.class.getName(), Constant.STATUS_SEND_TO_SYNC_UP, Constant.STATUS_OFFLINE);
//            }
//        });
//    }
//
//    public void syncUserProductFollowUp() {
//
//        JsonObject upData = getProductFollowJson(Constant.ACTION_FOLLOW);
//        if(upData == null) return;
//
//        iServeAPI srv = RestAPIClient.getInstance().getApi();
//        srv.followProduct(upData, new Callback<JsonObject>() {
//            @Override
//            public void success(JsonObject jsonObject, Response response) {
//
//                updateDataModelStatus(Product.class.getName(), Constant.STATUS_SEND_TO_SYNC_UP, Constant.STATUS_TBD);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//                updateDataModelStatus(Product.class.getName(), Constant.STATUS_SEND_TO_SYNC_UP, Constant.ACTION_FOLLOW);
//            }
//        });
//    }
//
//    public void syncUserProductUnfollowUp() {
//
//        JsonObject upData = getProductUnfollowJson();
//        if(upData == null) return;
//
//        iServeAPI srv = RestAPIClient.getInstance().getApi();
//        srv.unfollowProduct(upData, new Callback<JsonObject>() {
//            @Override
//            public void success(JsonObject jsonObject, Response response) {
//
//                updateDataModelStatus(Product.class.getName(), Constant.STATUS_SEND_TO_SYNC_UP, Constant.STATUS_TBD);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//                updateDataModelStatus(Product.class.getName(), Constant.STATUS_SEND_TO_SYNC_UP, Constant.ACTION_UNFOLLOW);
//            }
//        });
//    }
//
//    public void syncUserProductFeedbackUp() {
//
//        JsonObject upData = getUserProductFeedbacksJson();
//        if(upData == null) return;
//
//        iServeAPI srv = RestAPIClient.getInstance().getApi();
//        srv.syncProductFeedbacks(upData, new Callback<List<UserProductFeedbackResponse>>() {
//            @Override
//            public void success(List<UserProductFeedbackResponse> userProductFeedbackResponses, Response response) {
//
//                for (UserProductFeedbackResponse upfr : userProductFeedbackResponses) {
//                    if (upfr != null) {
//                        IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//                        UserProductFeedback upf = db.fetch(UserProductFeedback.class, upfr.getLocalId());
//                        upf.merge(upfr);
//                        db.persist(upf);
//
//                        Query qry = Query.select().from(DataManager.TABLE).where(DataManager.OBJECT_ID.eq(upfr.getLocalId())
//                                .and(DataManager.OBJECT_NAME.eq(UserProductFeedback.class.getName()))
//                                .and(DataManager.STATUS.eq(Constant.STATUS_SEND_TO_SYNC_UP)));
//                        DataManager dm = db.fetchByQuery(DataManager.class, qry);
//                        if (dm != null) {
//                            dm.setStatus(Constant.STATUS_TBD);
//                            db.persist(dm);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//                Log.e(Constant.ISERVE_LOG_TAG, error.getLocalizedMessage());
//                FlurryAgent.onError(Constant.ISERVE_LOG_TAG, error.getMessage(), error);
//
//                onFailureRevertDataModel(UserProductFeedback.class.getName());
//            }
//        });
//    }
//
//
//    public void syncUserAddressUp() {
//
//        // New
//        JsonObject upData = getUserAddressesNewJson();
//        if(upData == null) return;
//        RestAPIClient.getInstance().getApi().createAddress(upData, new Callback<List<AddressResponse>>() {
//            @Override
//            public void success(List<AddressResponse> addressResponses, Response response) {
//
//                IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//
//                for (AddressResponse addrRes : addressResponses) {
//                    if (addrRes != null) {
//                        Address addr = db.fetch(Address.class, addrRes.getLocalId());
//                        addr.merge(addrRes);
//                        db.persist(addr);
//
//                        Query qry = Query.select().from(DataManager.TABLE).where(DataManager.OBJECT_ID.eq(addrRes.getLocalId())
//                                                                                .and(DataManager.OBJECT_NAME.eq(Address.class.getName()))
//                                                                                .and(DataManager.STATUS.eq(Constant.STATUS_SEND_TO_SYNC_UP)));
//                        DataManager dm = db.fetchByQuery(DataManager.class, qry);
//                        if (dm != null) {
//
//                            dm.setStatus(Constant.STATUS_TBD);
//                            db.persist(dm);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.e(Constant.ISERVE_LOG_TAG, error.getLocalizedMessage());
//                FlurryAgent.onError(Constant.ISERVE_LOG_TAG, error.getMessage(), error);
//
//                onFailureRevertDataModel(Address.class.getName());
//            }
//        });
//
//        // Update
//        upData = getUserAddressesUpdateJson();
//        if(upData == null) return;
//        RestAPIClient.getInstance().getApi().updateAddress(upData, new Callback<List<AddressResponse>>() {
//            @Override
//            public void success(List<AddressResponse> addressResponses, Response response) {
//
//                IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//
//                for (AddressResponse addrRes : addressResponses) {
//                    if (addrRes != null) {
//                        Address addr = db.fetch(Address.class, addrRes.getLocalId());
//                        addr.merge(addrRes);
//                        db.persist(addr);
//
//                        Query qry = Query.select().from(DataManager.TABLE).where(DataManager.OBJECT_ID.eq(addrRes.getLocalId())
//                                                                            .and(DataManager.OBJECT_NAME.eq(Address.class.getName()))
//                                                                            .and(DataManager.STATUS.eq(Constant.STATUS_SEND_TO_SYNC_UP)));
//                        DataManager dm = db.fetchByQuery(DataManager.class, qry);
//                        if (dm != null) {
//
//                            dm.setStatus(Constant.STATUS_TBD);
//                            db.persist(dm);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.e(Constant.ISERVE_LOG_TAG, error.getLocalizedMessage());
//                FlurryAgent.onError(Constant.ISERVE_LOG_TAG, error.getMessage(), error);
//
//                onFailureRevertDataModel(Address.class.getName());
//            }
//        });
//
//        // Delete
//        upData = getUserAddressesDeleteJson();
//        if(upData == null) return;
//        RestAPIClient.getInstance().getApi().deleteAddress(upData, new Callback<List<AddressResponse>>() {
//            @Override
//            public void success(List<AddressResponse> addressResponses, Response response) {
//
//                IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//
//                for (AddressResponse addrRes : addressResponses) {
//                    if (addrRes != null) {
//                        Query qry = Query.select().from(DataManager.TABLE).where(DataManager.OBJECT_ID.eq(addrRes.getLocalId())
//                                .and(DataManager.OBJECT_NAME.eq(Address.class.getName()))
//                                .and(DataManager.STATUS.eq(Constant.STATUS_SEND_TO_SYNC_UP)));
//                        DataManager dm = db.fetchByQuery(DataManager.class, qry);
//                        if (dm != null) {
//
//                            dm.setStatus(Constant.STATUS_TBD);
//                            db.persist(dm);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.e(Constant.ISERVE_LOG_TAG, error.getLocalizedMessage());
//                FlurryAgent.onError(Constant.ISERVE_LOG_TAG, error.getMessage(), error);
//
//                onFailureRevertDataModel(Address.class.getName());
//            }
//        });
//    }
//
//
//
//    public void syncUserProductAttributeUp() {
//
//        JsonObject upData = getUserProductAttributesJson();
//        if(upData == null) return;
//
//        iServeAPI srv = RestAPIClient.getInstance().getApi();
//        srv.syncUserProductAttribute(upData, new Callback<List<UserProductAttributeResponse>>() {
//
//            @Override
//            public void success(List<UserProductAttributeResponse> userProductAttributeResponses, Response response) {
//
//                for (UserProductAttributeResponse upar : userProductAttributeResponses) {
//                    if (upar != null) {
//                        IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//                        UserProductAttribute upa = db.fetch(UserProductAttribute.class, upar.getLocalId());
//                        upa.merge(upar);
//                        db.persist(upa);
//
//                        Query qry = Query.select().from(DataManager.TABLE).where(DataManager.OBJECT_ID.eq(upar.getLocalId())
//                                .and(DataManager.OBJECT_NAME.eq(UserProductAttribute.class.getName()))
//                                .and(DataManager.STATUS.eq(Constant.STATUS_SEND_TO_SYNC_UP)));
//                        DataManager dm = db.fetchByQuery(DataManager.class, qry);
//                        if (dm != null) {
//
//                            dm.setStatus(Constant.STATUS_TBD);
//                            db.persist(dm);
////                            db.delete(DataManager.class, dm.getId());
//
//                            _listener.onComplete();
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//                Log.e(Constant.ISERVE_LOG_TAG, error.getLocalizedMessage());
//                FlurryAgent.onError(Constant.ISERVE_LOG_TAG, error.getMessage(), error);
//
//                onFailureRevertDataModel(UserProductAttribute.class.getName());
//            }
//        });
//    }
//
//
//    public void syncUserRequestUp(){
//
//        // Read User Request from  the table; if any
//
//        JsonArray retObj= getUserRequestsToSync();
//
//        if(retObj == null) {
//            Log.d(Constant.ISERVE_LOG_TAG, "No UR Sync");
//            return;
//        }
//        JsonObject upData = new JsonObject();
//        upData.add("user_request", retObj);
//
//        // call api to upload
//        iServeAPI srv = RestAPIClient.getInstance().getApi();
//        srv.createNewRequest(upData, new Callback<List<UserRequestResponse>>() {
//            @Override
//            public void success(List<UserRequestResponse> userRequestResponses, Response response) {
//
//                for (UserRequestResponse urr : userRequestResponses) {
//
//                    if (urr != null) {
//                        IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//                        UserRequest ur = db.fetch(UserRequest.class, urr.getLocalId());
//                        ur.merge(urr);
//                        db.persist(ur);
//
//                        Query qry = Query.select().from(DataManager.TABLE).where(DataManager.OBJECT_ID.eq(urr.getLocalId())
//                                .and(DataManager.OBJECT_NAME.eq(UserRequest.class.getName()))
//                                .and(DataManager.STATUS.eq(Constant.STATUS_SEND_TO_SYNC_UP)));
//                        DataManager dm = db.fetchByQuery(DataManager.class, qry);
//                        if (dm != null) {
//
//                            Log.d(Constant.ISERVE_LOG_TAG, "return DM1 ID:" + dm.getId());
//                            dm.setStatus(Constant.STATUS_TBD);
//                            db.persist(dm);
//                        }
//                    }
//                }
//
//                _listener.onComplete();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//                Log.e(Constant.ISERVE_LOG_TAG, error.getLocalizedMessage());
//                FlurryAgent.onError(Constant.ISERVE_LOG_TAG, error.getMessage(), error);
//
//                onFailureRevertDataModel(UserRequest.class.getName());
//            }
//        });
//
//        // Change the uploaded data to done
//        // delete uploaded data from DataManager i.e. status = done
//    }
//
//
//    private JsonArray getUserRequestsToSync() {
//
//        // Read DataManager for any offline data
//        IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//
//        Query qry = Query.select().from(DataManager.TABLE).where(DataManager.OBJECT_NAME.eq(UserRequest.class.getName())
//                .and(DataManager.STATUS.eq(Constant.STATUS_OFFLINE)));
//        SquidCursor<DataManager> dmCur = db.query(DataManager.class, qry);
//
//        if(dmCur.getCount() == 0) return null;
//
//        JsonArray userRequestArr = new JsonArray();
//        while(dmCur.moveToNext()){
//
//            DataManager dm = new DataManager(dmCur);
//            Log.d(Constant.ISERVE_LOG_TAG, "DM ID:" + dm.getId());
//            dm.readPropertiesFromCursor(dmCur);
//
//            if(dm.getObjectId() != 0) {
//                UserRequest uReq = db.fetch(UserRequest.class, dm.getObjectId());
//                Log.d(Constant.ISERVE_LOG_TAG, "UR ID:" + uReq.getId());
//                if(uReq != null)
//                    userRequestArr.add(uReq.getJson());
//
//
//                // Change status
//                dm.setStatus(Constant.STATUS_SEND_TO_SYNC_UP);
//                db.persist(dm);
//            }
//        }
//
//        return userRequestArr;
//    }
//
//
//
//
//    public void syncUserDown(){
//
//        RestAPIClient.getInstance().getApi().syncUser(new Callback<UserSyncResponse>() {
//
//            @Override
//            public void success(UserSyncResponse userSyncResponse, Response response) {
//
//                IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//
//                // User
//                UserResponse uRes = userSyncResponse.getUser();
//                if (uRes != null) {
//                    User usr = User.create(uRes);
//                    db.persist(usr);
//                }
//
//                // User Attributes
//                if (userSyncResponse.getUserAttributes() != null) {
//                    for (UserAttributeResponse uAttrRes : userSyncResponse.getUserAttributes()) {
//                        UserAttribute uAttr = UserAttribute.create(uAttrRes);
//                        db.persist(uAttr);
//                    }
//                }
//
//                // UserRequest
//                if (userSyncResponse.getUserRequests() != null) {
//                    for (UserRequestResponse urRes : userSyncResponse.getUserRequests()) {
//                        UserRequest ur = UserRequest.create(urRes);
//                        db.persist(ur);
//                    }
//                }
//                // UserRequestFeedback
//                if (userSyncResponse.getUserRequestFeedbacks() != null) {
//                    for (UserRequestFeedbackResponse urfRes : userSyncResponse.getUserRequestFeedbacks()) {
//                        UserRequestFeedback urf = UserRequestFeedback.create(urfRes);
//                        db.persist(urf);
//                    }
//                }
//                // UserRequestFeedbackItem
//                if (userSyncResponse.getUserRequestFeedbackItems() != null) {
//                    for (UserRequestFeedbackItemResponse urfItemRes : userSyncResponse.getUserRequestFeedbackItems()) {
//                        UserRequestFeedbackItem urfItem = UserRequestFeedbackItem.create(urfItemRes);
//                        db.persist(urfItem);
//                    }
//                }
//
//                // UserRequestAttribute
//                if (userSyncResponse.getUserRequestAttributes() != null) {
//                    for (UserRequestAttributeResponse uraRes : userSyncResponse.getUserRequestAttributes()) {
//                        UserRequestAttribute ura = UserRequestAttribute.create(uraRes);
//                        db.persist(ura);
//                    }
//                }
//
//                // UserProduct
//                if (userSyncResponse.getUserProducts() != null) {
//                    for (ProductResponse uProdRes : userSyncResponse.getUserProducts()) {
//                        UserProduct ur = UserProduct.create(uProdRes);
//                        db.persist(ur);
//                    }
//                }
//
//                // UserProductFeedback
//                if (userSyncResponse.getUserProductFeedbacks() != null) {
//                    for (UserProductFeedbackResponse uProdFbRes : userSyncResponse.getUserProductFeedbacks()) {
//                        UserProductFeedback uProdFb = UserProductFeedback.create(uProdFbRes);
//                        db.persist(uProdFb);
//                    }
//                }
//
//                // UserProductFeedback
//                if (userSyncResponse.getUserProductFeedbackItems() != null) {
//                    for (UserProductFeedbackItemResponse uProdFbItemRes : userSyncResponse.getUserProductFeedbackItems()) {
//                        UserProductFeedbackItem uProdFbItem = UserProductFeedbackItem.create(uProdFbItemRes);
//                        db.persist(uProdFbItem);
//                    }
//                }
//
//                _user_data_version = userSyncResponse.getDataVersion();
//                PreferenceUtils.set(Global.getContext(), Constant.Preference.USER_DATA_VERSION, _user_data_version);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.e(Constant.ISERVE_LOG_TAG, "syncUser:" + error.getMessage());
//                FlurryAgent.onError("SyncData.syncUser", error.getMessage(), error);
//            }
//        });
//    }
//
//
////    public void syncPartialAll() {
////
////        RestAPIClient.getInstance().getApi().syncPartialAll(_version, new Callback<SyncResponse>() {
////            @Override
////            public void success(SyncResponse syncResponse, Response response) {
////
////                IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
////
////                // 1.
////                if (syncResponse.getOrganizations() != null) {
////                    for (OrganizationResponse orgRes : syncResponse.getOrganizations()) {
////
////                        Organization org = Organization.create(orgRes);
//////                        Log.i(Constant.ISERVE_LOG_TAG, "In to saving...");
//////                        String imgPath = FileUtils.saveImage(Global.getContext(),
//////                                                                            Constant.ISERVE_API_URL + org.getImgUrl(),
//////                                                                            Constant.IMG_DIR_ORG,
//////                                                                            org.getIdSvr().toString());
//////
//////                        Log.i(Constant.ISERVE_LOG_TAG, "Out of saving...");
//////
//////                        org.setLocalProfileImg(imgPath);
////
////                        // save the data to local db
////                        db.persist(org);
////                    }
////                }
////
////                // 2.
////                if (syncResponse.getProducts() != null) {
////                    for (ProductResponse prod : syncResponse.getProducts()) {
////                        // TODO: Save product images...
////                        Product p = Product.create(prod);
////                        db.persist(p);
////                    }
////                }
////
////                if (syncResponse.getProductFeatures() != null) {
////                    for (ProductFeatureResponse featureResponse : syncResponse.getProductFeatures()) {
////                        ProductFeature pf = ProductFeature.create(featureResponse);
////                        db.persist(pf);
////                    }
////                }
////
////                // 2.1.
////                // Save ProductFaq
////                if (syncResponse.getProductFaqs() != null) {
////                    for (ProductFaqResponse pfr : syncResponse.getProductFaqs()) {
////                        ProductFaq pf = ProductFaq.create(pfr);
////                        db.persist(pf);
////                    }
////                }
////
////                // 2.2.
////                // Save ProductFeedbackDef
////                if (syncResponse.getProductFeedbackDefs() != null) {
////                    for (ProductFeedbackDefResponse pfbdr : syncResponse.getProductFeedbackDefs()) {
////                        ProductFeedbackDef pfbd = ProductFeedbackDef.create(pfbdr);
////                        db.persist(pfbd);
////                    }
////                }
////
////                // 2.2.1.
////                // Save ProductFeedbackDefItem
////                if (syncResponse.getProductFeedbackDefItems() != null) {
////                    for (ProductFeedbackDefItemResponse pfbdItemRes : syncResponse.getProductFeedbackDefItems()) {
////                        ProductFeedbackDefItem pfbdItem = ProductFeedbackDefItem.create(pfbdItemRes);
////                        db.persist(pfbdItem);
////                    }
////                }
////
////                // 2.3.
////                // Save SupportService
////                if (syncResponse.getSupportServices() != null) {
////                    for (SupportServiceResponse ssr : syncResponse.getSupportServices()) {
////                        SupportService ss = SupportService.create(ssr);
////                        db.persist(ss);
////                    }
////                }
////
////                // 2.3.1.
////                // Save SupportServiceAttribute
////                if (syncResponse.getSupportServiceAttributes() != null) {
////                    for (SupportServiceAttributeResponse ssaRes : syncResponse.getSupportServiceAttributes()) {
////                        SupportServiceAttribute ssa = SupportServiceAttribute.create(ssaRes);
////                        db.persist(ssa);
////                    }
////                }
////
////                // 2.3.2.
////                // Save SupportServiceFeedbackDef
////                if (syncResponse.getSupportServiceFeedbackDefs() != null) {
////                    for (SupportServiceFeedbackDefResponse ssfbdRes : syncResponse.getSupportServiceFeedbackDefs()) {
////                        SupportServiceFeedbackDef ssfbd = SupportServiceFeedbackDef.create(ssfbdRes);
////                        db.persist(ssfbd);
////                    }
////                }
////
////                // 2.3.2.1.
////                // Save SupportServiceFeedbackDefItem
////                if (syncResponse.getSupportServiceFeedbackDefItems() != null) {
////                    for (SupportServiceFeedbackDefItemResponse ssfbdItemRes : syncResponse.getSupportServiceFeedbackDefItems()) {
////                        SupportServiceFeedbackDefItem ssfbdItem = SupportServiceFeedbackDefItem.create(ssfbdItemRes);
////                        db.persist(ssfbdItem);
////                    }
////                }
////            }
////
////            @Override
////            public void failure(RetrofitError error) {
////                Log.e(Constant.ISERVE_LOG_TAG, error.getMessage());
////                FlurryAgent.onError("SyncData.syncPartialAll", error.getMessage(), error);
////            }
////        });
////    }
//
//    public void syncAll() {
//
//        RestAPIClient.getInstance().getApi().syncAll("", "", new Callback<SyncResponse>() {
//            @Override
//            public void success(SyncResponse syncResponse, Response response) {
//
//                saveOrganizationGraph(syncResponse.getOrganizations());
//                _version = syncResponse.getVersion();
//                PreferenceUtils.set(Global.getContext(), Constant.Preference.DATA_VERSION, _version);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.e(Constant.ISERVE_LOG_TAG, error.getMessage());
//                FlurryAgent.onError("SyncData.syncAll", error.getMessage(), error);
//            }
//        });
//
//        syncStatic();
//    }
//
//    public void syncStatic() {
//        RestAPIClient.getInstance().getApi().getCountries(new Callback<List<CountryResponse>>() {
//
//            @Override
//            public void success(List<CountryResponse> countryResponses, Response response) {
//
//                for (CountryResponse countryResponse : countryResponses) {
//
//                    Country.create(countryResponse).save();
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.e(Constant.ISERVE_LOG_TAG, "SyncData.syncStatic.getCountries:" + error.getMessage());
//                FlurryAgent.onError("SyncData.syncStatic.getCountries", error.getMessage(), error);
//            }
//        });
//    }
//
//
//    public void syncOrganizations() {
//
//        RestAPIClient.getInstance().getApi().getOrganizationList(_version, new Callback<List<OrganizationResponse>>() {
//            @Override
//            public void success(List<OrganizationResponse> orgResponses, Response response) {
//
//                OrganizationResponse[] orgResArray = orgResponses.toArray(new OrganizationResponse[orgResponses.size()]);
//                saveOrganizationGraph(orgResArray);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.e(Constant.ISERVE_LOG_TAG, error.getMessage());
//                FlurryAgent.onError("SyncData.syncOrganizations", error.getMessage(), error);
//            }
//        });
//    }
//
//
//    private void saveOrganizationGraph(OrganizationResponse[] orgResponses) {
//        if (orgResponses == null) return;
//        // 1.
//        for (OrganizationResponse orgRes : orgResponses) {
//
//            Organization org = Organization.create(orgRes);
//
////            // save images
////            String imgPath = FileUtils.getInstance().saveImage(Global.getContext(), Constant.ISERVE_API_URL + org.getImgUrl(), "organizations", org.getIdSvr().toString());
////            org.setLocalProfileImg(imgPath);
//
//            // save the data to local db
//            IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//            db.persist(org);
//
//            // 2.
//            if (orgRes.getProducts() != null) {
//                for (ProductResponse prodRes : orgRes.getProducts()) {
//
//                    // TODO: Save product images...
//                    Product p = Product.create(prodRes);
//                    db.persist(p);
//
//                    // 2.1.
//                    // Save ProductFaq
//                    if (prodRes.getProductFeatures() != null) {
//                        for (ProductFeatureResponse pfRes : prodRes.getProductFeatures()) {
//                            ProductFeature pf = ProductFeature.create(pfRes);
//                            pf.setProductId(prodRes.getId_svr());
//                            db.persist(pf);
//                        }
//                    }
//
//                    // 2.1.
//                    // Save ProductFaq
//                    if (prodRes.getProductFaqs() != null) {
//                        for (ProductFaqResponse pfRes : prodRes.getProductFaqs()) {
//                            ProductFaq pf = ProductFaq.create(pfRes);
//                            db.persist(pf);
//                        }
//                    }
//
//                    // 2.2.
//                    // Save ProductFeedbackDef
//                    if (prodRes.getProductFeedbackDefs() != null) {
//                        for (ProductFeedbackDefResponse pfbdRes : prodRes.getProductFeedbackDefs()) {
//                            ProductFeedbackDef pfbd = ProductFeedbackDef.create(pfbdRes);
//                            db.persist(pfbd);
//
//                            // 2.2.1.
//                            if (pfbdRes.getProductFeedbackDefItems() != null) {
//                                for (ProductFeedbackDefItemResponse pfbdItemRes : pfbdRes.getProductFeedbackDefItems()) {
//                                    ProductFeedbackDefItem pfbdItem = ProductFeedbackDefItem.create(pfbdItemRes);
//                                    db.persist(pfbdItem);
//                                }
//                            }
//                        }
//                    }
//
//                    // 2.3.
//                    // Save SupportService
//                    if (prodRes.getSupportServices() != null) {
//                        for (SupportServiceResponse ssRes : prodRes.getSupportServices()) {
//                            SupportService ss = SupportService.create(ssRes);
//                            db.persist(ss);
//
//                            // 2.3.1.
//                            // Save SupportServiceAttribute
//                            if (ssRes.getSupportServiceAttributes() != null) {
//                                for (SupportServiceAttributeResponse ssaRes : ssRes.getSupportServiceAttributes()) {
//                                    SupportServiceAttribute ssa = SupportServiceAttribute.create(ssaRes);
//                                    db.persist(ssa);
//                                }
//                            }
//
//                            // 2.3.2.
//                            // Save SupportServiceFeedbackDef
//                            if (ssRes.getSupportServiceFeedbackDefs() != null) {
//                                for (SupportServiceFeedbackDefResponse ssfbdRes : ssRes.getSupportServiceFeedbackDefs()) {
//                                    SupportServiceFeedbackDef ssfbd = SupportServiceFeedbackDef.create(ssfbdRes);
//                                    db.persist(ssfbd);
//
//                                    // 2.3.2.1.
//                                    // Save SupportServiceFeedbackDef
//                                    if (ssfbdRes.getSupportServiceFeedbackDefItems() != null) {
//                                        for (SupportServiceFeedbackDefItemResponse ssfbdItemRes : ssfbdRes.getSupportServiceFeedbackDefItems()) {
//                                            SupportServiceFeedbackDefItem ssfbdItem = SupportServiceFeedbackDefItem.create(ssfbdItemRes);
//                                            db.persist(ssfbdItem);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//
//    public static void resetUserDB(){
//        IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//        db.deleteAll(UserProductFeedbackItem.class);
//        db.deleteAll(UserProductFeedback.class);
//        db.deleteAll(UserProduct.class);
//        db.deleteAll(UserRequestFeedbackItem.class);
//        db.deleteAll(UserRequestFeedback.class);
//        db.deleteAll(UserRequestAttribute.class);
//        db.deleteAll(UserRequest.class);
//        db.deleteAll(UserAttribute.class);
//        db.deleteAll(User.class);
//        _user_data_version = "";
//        PreferenceUtils.set(Global.getContext(), Constant.Preference.USER_DATA_VERSION, _user_data_version);
//    }
//
//    public static void resetAll(){
//        IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//        db.deleteAll(SupportServiceFeedbackDefItem.class);
//        db.deleteAll(SupportServiceFeedbackDef.class);
//        db.deleteAll(SupportServiceAttribute.class);
//        db.deleteAll(SupportService.class);
//
//        db.deleteAll(ProductFeedbackDefItem.class);
//        db.deleteAll(ProductFeedbackDef.class);
//        db.deleteAll(ProductFaq.class);
//        db.deleteAll(Product.class);
//
//        db.deleteAll(Address.class);
//        db.deleteAll(Organization.class);
//
//        resetUserDB();
//        _version = "";
//        PreferenceUtils.set(Global.getContext(), Constant.Preference.DATA_VERSION, _version);
//    }
//
//    public void syncOrganization(final String orgID){
//
//        RestAPIClient.getInstance().getApi().getProducts(orgID, _version, new Callback<List<ProductResponse>>() {
//            @Override
//            public void success(List<ProductResponse> productResponse, Response response) {
//
//                // Converting the response to models
//                ArrayList<Product> pList = ModelUtils.getProductList(productResponse);
//                for (Product p : pList) {
//
////                String imgPath = FileUtils.saveImage(Global.getContext(), Constant.ISERVE_API_URL + org.getImgUrl(), "organizations", org.getIdSvr().toString());
////                org.setLocalProfileImg(imgPath);
//                    // save the data to local db
//                    IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//                    db.persist(p);
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });
//    }
//
//    private void onFailureRevertDataModel(String model) {
//
//        updateDataModelStatus(model, Constant.STATUS_SEND_TO_SYNC_UP, Constant.STATUS_OFFLINE);
//    }
//
//    private void updateDataModelStatus(String model, String fromStatus, String toStatus) {
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
//
//    public void getMyProducts() {
//        String version = PreferenceUtils.get(Global.getContext(), Constant.Preference.DATA_VERSION);
//        iServeAPI srv = RestAPIClient.getInstance().getApi();
//        srv.getMyProductList(version, new Callback<List<ProductResponse>>() {
//            @Override
//            public void success(List<ProductResponse> productResponses, Response response) {
//                saveMyProducts(ModelUtils.getProductList(productResponses));
//                _listener.onComplete();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.e(Constant.ISERVE_LOG_TAG, error.getMessage());
//            }
//        });
//    }
//
//    public void saveMyProducts(List<Product> products){
//
//        for (Product p : products) {
//            IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//            db.persist(p.createUserProduct());
//        }
//        Log.e(Constant.ISERVE_LOG_TAG, "Products Saved" + products.size());
//    }
//
//    private JsonObject getUserJson() {
//
//        IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//        DataManager dm = db.fetchByCriterion(DataManager.class, DataManager.OBJECT_NAME.eq(User.class.getName()).and(DataManager.STATUS.eq(Constant.STATUS_OFFLINE)));
//
//        if(dm == null) return null;
//
//        dm.setStatus(Constant.STATUS_SEND_TO_SYNC_UP);
//        db.persist(dm);
//        return Constant.Global.USER.getJson();
//    }
//
//    private JsonObject getProductFollowJson(String followStatus){
//
//        IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//        Query qry = Query.select().from(DataManager.TABLE).where(DataManager.OBJECT_NAME.eq(Product.class.getName()).and(DataManager.STATUS.eq(followStatus)));
//        SquidCursor<DataManager> dmCur = db.query(DataManager.class, qry);
//        if(dmCur.getCount() == 0) return null;
//
//        JsonArray upaArr = new JsonArray();
//        while(dmCur.moveToNext()){
//
//            DataManager dm = new DataManager(dmCur);
//            if(dm.getObjectId() != 0) {
//
//                JsonObject jsonProductId = new JsonObject();
//                jsonProductId.addProperty("id", dm.getObjectId());
//                upaArr.add(jsonProductId);
//
//                dm.setStatus(Constant.STATUS_SEND_TO_SYNC_UP);
//                db.persist(dm);
//            }
//        }
//
//        JsonObject upData = new JsonObject();
//        upData.add("products", upaArr);
//        return upData;
//    }
//
//    private JsonObject getProductUnfollowJson(){
//        return getProductFollowJson(Constant.ACTION_UNFOLLOW);
//    }
//
//    private JsonObject getUserProductFeedbacksJson() {
//
//        SquidCursor<DataManager> dmCur = getDataManagerCursor(UserProductFeedback.class.getName());
//        if(dmCur.getCount() == 0) return null;
//
//        JsonArray upaArr = new JsonArray();
//        IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//        while(dmCur.moveToNext()) {
//
//            DataManager dm = new DataManager(dmCur);
//            if(dm.getObjectId() != 0) {
//
//                UserProductFeedback upf = db.fetch(UserProductFeedback.class, dm.getObjectId());
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
//        upData.add("user_product_feedbacks", upaArr);
//        return upData;
//    }
//
//    private JsonObject getUserAddressesNewJson() {
//
//        return getUserAddressesJson(Constant.ACTION_CREATE);
//    }
//
//    private JsonObject getUserAddressesUpdateJson() {
//        return getUserAddressesJson(Constant.ACTION_UPDATE);
//    }
//
//    private JsonObject getUserAddressesDeleteJson() {
//        return getUserAddressesJson(Constant.ACTION_DELETE);
//    }
//
//    private JsonObject getUserAddressesJson(String action) {
//
//        IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//        Query qry = Query.select().from(DataManager.TABLE).where(DataManager.OBJECT_NAME.eq(Address.class.getName())
//                                                            .and(DataManager.STATUS.eq(Constant.STATUS_OFFLINE))
//                                                            .and(DataManager.ACTION.eq(action)));
//        SquidCursor<DataManager> dmCur = db.query(DataManager.class, qry);
//        if (dmCur.getCount() == 0) return null;
//
//        JsonArray upaArr = new JsonArray();
//        while (dmCur.moveToNext()) {
//
//            DataManager dm = new DataManager(dmCur);
//            Log.d(Constant.ISERVE_LOG_TAG, "DM ID:" + dm.getId());
//
//            if(dm.getObjectId() != 0) {
//                Address address = db.fetch(Address.class, dm.getObjectId());
//
//                if(address != null)
//                    upaArr.add(address.getJson());
//
//                dm.setStatus(Constant.STATUS_SEND_TO_SYNC_UP);
//                db.persist(dm);
//            }
//        }
//
//        JsonObject upData = new JsonObject();
//        upData.add("addresses", upaArr);
//
//        return upData;
//    }
//
//    private JsonObject getUserProductAttributesJson(){
//
//        // Read DataManager for any offline data
//        IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//        Query qry = Query.select().from(DataManager.TABLE).where(DataManager.OBJECT_NAME.eq(UserProductAttribute.class.getName())
//                                                                    .and(DataManager.STATUS.eq(Constant.STATUS_OFFLINE)));
//        SquidCursor<DataManager> dmCur = db.query(DataManager.class, qry);
//
//        if(dmCur.getCount() == 0) return null;
//
//        JsonArray upaArr = new JsonArray();
//        while(dmCur.moveToNext()){
//
//            DataManager dm = new DataManager(dmCur);
//            Log.d(Constant.ISERVE_LOG_TAG, "DM ID:" + dm.getId());
//
//            if(dm.getObjectId() != 0) {
//                UserProductAttribute upa = db.fetch(UserProductAttribute.class, dm.getObjectId());
//                Log.d(Constant.ISERVE_LOG_TAG, "UR ID:" + upa.getId());
//                if(upa != null)
//                    upaArr.add(upa.getJson());
//
//                dm.setStatus(Constant.STATUS_SEND_TO_SYNC_UP);
//                db.persist(dm);
//            }
//        }
//
//        JsonObject upData = new JsonObject();
//        upData.add("user_product_attributes", upaArr);
//
//        return upData;
//    }
//
//
//    private SquidCursor<DataManager> getDataManagerCursor(String model) {
//
//        IServeDatabase db = IServeDatabase.getInstance(Global.getContext());
//        Query qry = Query.select().from(DataManager.TABLE).where(DataManager.OBJECT_NAME.eq(model)
//                .and(DataManager.STATUS.eq(Constant.STATUS_OFFLINE)));
//        return db.query(DataManager.class, qry);
//    }
//}
