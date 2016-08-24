package com.ormdatabase.DBModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikhil on 21-07-2016.
 */
@Table
public class Part extends SugarRecord {

    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("part_number")
    @Expose
    @Unique
    private String partNumber;
    @SerializedName("part_name")
    @Expose
    private String partName;
    @SerializedName("associated_categories_str")
    @Expose
    private List<String> associatedCategoriesStr = new ArrayList<String>();
    @SerializedName("part_available_quantity")
    @Expose
    private String partAvailableQuantity;
    @SerializedName("part_products")
    @Expose
    private String partProducts;
    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("part_category")
    @Expose
    private String partCategory;


    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public List<String> getAssociatedCategoriesStr() {
        return associatedCategoriesStr;
    }

    public void setAssociatedCategoriesStr(List<String> associatedCategoriesStr) {
        this.associatedCategoriesStr = associatedCategoriesStr;
    }

    public String getPartAvailableQuantity() {
        return partAvailableQuantity;
    }

    public void setPartAvailableQuantity(String partAvailableQuantity) {
        this.partAvailableQuantity = partAvailableQuantity;
    }

    public String getPartProducts() {
        return partProducts;
    }

    public void setPartProducts(String partProducts) {
        this.partProducts = partProducts;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getPartCategory() {
        return partCategory;
    }

    public void setPartCategory(String partCategory) {
        this.partCategory = partCategory;
    }
}
