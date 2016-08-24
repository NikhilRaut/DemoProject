package com.ormdatabase.API;

import com.google.gson.JsonObject;
import com.ormdatabase.DBModel.Part;
import com.ormdatabase.DBModel.Retailer;
import com.ormdatabase.DBModel.TimeTableResponce;

import org.json.JSONObject;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by Nikhil on 04-06-2016.
 */
public interface RetrofitAPIInterface {


    @POST("/gm-users/login/")
    @Headers({
            "Accept: application/json"
    })
    public void signIn(@Body JsonObject loginDetails, Callback<JsonObject> status);

//
//    //    // Get Dashboard Details From Server
//    @GET("/show_routes")
//    public void getDashboardDetails(Callback<DashboardResponse> dashboard);
//
//    //
//    // Get attendance Details From Server
//    @POST("/trip_status")
//    public void updateTripStatus(@Body JSONObject date, Callback<JsonObject> json);
//
//    //    @POST("/mark_attendance")
////    public void markAttendance(@Body JsonObject date, Callback<JSONObject> attendance);
////
//    @GET("/registrations/sign_out")
//    public void logout(Callback<JSONObject> logout);
//
//    @GET("/driver_profile")
//    public void getProfile(Callback<ProfileResponse> profileResponse);

    @GET("/get_retailers/dsr_id/500003/")
    public void getRetailers(Callback<List<Retailer>> retailers);

    @GET("/get_parts/")
    public void getParts(Callback<List<Part>> retailers);

    @GET("YOUR URL")
    public void getTimeTable(Callback<TimeTableResponce> callback);

//
//    @GET("/leave_application")
//    public void leaveApplication(Callback<LeaveResponse> leaveResponse);
//
//    @POST("/apply_leave")
//    public void applyLeave(@Body JsonObject jsonObject, Callback<JsonObject> leaveJson);
//
//    @GET("/student_leave_application")
//    public void studentLeaveApplication(Callback<StudentLeaveResponse> leaveResponse);
//
//    @POST("/teacher_approved_leave_application")
//    public void teacherApprovedLeaveApplication(@Body JsonObject jsonObject, Callback<JsonObject> leaveJson);

//    // Get Search result https://api.myjson.com/bins/1kb7w
//    @GET("/bins/1kb7w")
//    public void getSearchResult(Callback<List<Product>> productList);
}
