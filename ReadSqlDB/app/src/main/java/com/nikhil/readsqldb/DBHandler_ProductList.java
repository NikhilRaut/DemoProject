package com.nikhil.readsqldb;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Ranjan on 23-09-2015.
 */
public class DBHandler_ProductList extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "prrlite";

    // Product table name

    public static final String TABLE_PARTS_LIST = "categoryParts";


    public static final String TABLE_PARTS_CATALOG = "catalogParts";
    public static final String TABLE_PARTS_STOCK = "catalogStock";

    // Product Table Columns names

    private static final String KEY_P_ID = "Id";
    private static final String KEY_P_NAME = "proName";
    private static final String KEY_P_MODELS = "proModel";
    private static final String KEY_P_SUB_CATEGORY = "proSubCategory";
    private static final String KEY_P_CATEGORY = "proCategory";
    private static final String KEY_P_UNIT_PRICE = "proUnitPrice";
    private static final String KEY_P_SUB_TOTAL = "product_sub_total";
    private static final String KEY_P_QUANTITY_AVAIL = "product_quantity_avail";
    private static final String KEY_P_QUANTITY = "product_quantity";
    private static final String KEY_P_MOQ = "product_moq";
    private static final String KEY_P_INDEX = "product_index";


    private static final String KEY_P_CATALOG_MODEL = "catalog_model";
    private static final String KEY_P_CATALOG_PLATES = "catalog_plates";
    private static final String KEY_P_CATALOG_PLATES_ID = "catalog_plates_id";
    SQLiteDatabase db;


    public DBHandler_ProductList(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
/*

        String CREATE_ADD_TABLE_ITEMS = "CREATE TABLE " + TABLE_PARTS_LIST + "("
                + KEY_P_ID + "  TEXT unique ,"
                + KEY_P_NAME + " TEXT,"
                + KEY_P_MODELS + " TEXT, "
                + KEY_P_QUANTITY_AVAIL + " TEXT, "
                + KEY_P_SUB_CATEGORY + " TEXT,"
                + KEY_P_CATEGORY + " TEXT, "
                + KEY_P_UNIT_PRICE + " TEXT, "
                + KEY_P_SUB_TOTAL + " TEXT,"
                + KEY_P_MOQ + " TEXT,"
                + KEY_P_INDEX + " INTEGER,"
                + KEY_P_QUANTITY + " TEXT " + ");";
        db.execSQL(CREATE_ADD_TABLE_ITEMS);
//        db.execSQL("CREATE INDEX parts ON " + TABLE_PARTS_LIST + ";");


        String CREATE_ADD_PARTS_CATALOG = "CREATE TABLE " + TABLE_PARTS_CATALOG + "("
                + KEY_P_ID + "  TEXT ,"
                + KEY_P_NAME + " TEXT,"
                + KEY_P_CATALOG_MODEL + " TEXT, "
                + KEY_P_QUANTITY_AVAIL + " TEXT, "
                + KEY_P_SUB_CATEGORY + " TEXT,"
                + KEY_P_CATALOG_PLATES + " TEXT, "
                + KEY_P_UNIT_PRICE + " TEXT, "
                + KEY_P_SUB_TOTAL + " TEXT,"
                + KEY_P_MOQ + " TEXT,"
                + KEY_P_CATALOG_PLATES_ID + " TEXT,"
                + KEY_P_INDEX + " INTEGER,"
                + KEY_P_QUANTITY + " TEXT " + ");";
        db.execSQL(CREATE_ADD_PARTS_CATALOG);


        String CREATE_ADD_PARTS_STOCK = "CREATE TABLE " + TABLE_PARTS_STOCK + "("
                + KEY_P_ID + "  TEXT,"
                + KEY_P_QUANTITY_AVAIL + " TEXT, "
                + KEY_P_UNIT_PRICE + " TEXT " + ");";
        db.execSQL(CREATE_ADD_PARTS_STOCK);
//        db.execSQL("CREATE INDEX part_catalog ON " + TABLE_PARTS_CATALOG + ";");
*/

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


//        String CREATE_ADD_PARTS_STOCK = "CREATE TABLE " + TABLE_PARTS_STOCK + "("
//                + KEY_P_ID + "  TEXT,"
//                + KEY_P_QUANTITY_AVAIL + " TEXT, "
//                + KEY_P_UNIT_PRICE + " TEXT " + ");";
//        db.execSQL(CREATE_ADD_PARTS_STOCK);
        // Drop older table if existed
//        if (newVersion == 1)
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTS_LIST);
//
//        // Create tables again
//        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations For Restaurant
     */

    public int getProductCount() {
        String selectQuery = "SELECT count(*) FROM " + TABLE_PARTS_LIST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.e("Count", "Count :: " + cursor.getCount());
        return cursor.getCount();
    }

    public boolean checkForExists(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PARTS_LIST + " where " + KEY_P_ID + " = '" + id + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteAllProduct() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PARTS_LIST, null, null);
    }
}