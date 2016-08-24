package com.ormdatabase.DBModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;


/**
 * Created by Nikhil on 21-07-2016.
 */
@Table
public class Retailer extends SugarRecord {

//    @SerializedName("id")
//    @Expose
//    private Long id;

    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("retailer_name")
    @Expose
    private String retailerName;
    @SerializedName("retailer_mobile")
    @Expose
    private String retailerMobile;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("retailer_email")
    @Expose
    private String retailerEmail;
    @SerializedName("locality_id")
    @Expose
    private String localityId;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("retailer_address")
    @Expose
    private String retailerAddress;
    @SerializedName("retailer_Id")
    @Expose
    @Unique
    private String retailerId;

    // Default constructor for database
    public Retailer() {
    }


//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getRetailerMobile() {
        return retailerMobile;
    }

    public void setRetailerMobile(String retailerMobile) {
        this.retailerMobile = retailerMobile;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getRetailerEmail() {
        return retailerEmail;
    }

    public void setRetailerEmail(String retailerEmail) {
        this.retailerEmail = retailerEmail;
    }

    public String getLocalityId() {
        return localityId;
    }

    public void setLocalityId(String localityId) {
        this.localityId = localityId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getRetailerAddress() {
        return retailerAddress;
    }

    public void setRetailerAddress(String retailerAddress) {
        this.retailerAddress = retailerAddress;
    }

    public String getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(String retailerId) {
        this.retailerId = retailerId;
    }


}
