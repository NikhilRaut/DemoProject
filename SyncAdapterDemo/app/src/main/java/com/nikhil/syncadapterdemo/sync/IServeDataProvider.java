package com.nikhil.syncadapterdemo.sync;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class IServeDataProvider extends ContentProvider {
    @Override
    public boolean onCreate() {

        Log.i(Constant.ISERVE_LOG_TAG, "Provider on Create");
//        dbDao = new DatabaseDao(new IServeDatabase(getContext()));
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    //    @Override
//    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        Query query;
//        switch (URI_MATCHER.match(uri)) {
//            case ORGANIZATION:
//                query = buildOrganizationQuery(projection, selection, selectionArgs, sortOrder);
//                break;
//            case ORGANIZATION_ID:
//                query = buildOrganizationQuery(projection, selection, selectionArgs, sortOrder);
//                query.where(Organization.ID.eq(ContentUris.parseId(uri)));
//                break;
//            case ORGANIZATION_ID_PRODUCTS:
//                query = buildProductQuery(projection, selection, selectionArgs, sortOrder);
//                query.where(Organization.ID.eq(getIdPathSegment(uri, 1)));
//                break;
//            default:
//                throw new UnsupportedOperationException("Unsupported uri: " + uri);
//        }
//
//            query.limit(getLimit(uri));
//            SquidCursor<AbstractModel> cursor = dbDao.query(AbstractModel.class, query);
//            return cursor;
//        }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
//package io.iserve.iserveandroid.data.db;
//
//import android.content.ContentProvider;
//import android.content.ContentUris;
//import android.content.ContentValues;
//import android.content.UriMatcher;
//import android.database.Cursor;
//import android.net.Uri;
//import android.provider.BaseColumns;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.yahoo.squidb.data.AbstractModel;
//import com.yahoo.squidb.data.DatabaseDao;
//import com.yahoo.squidb.data.SquidCursor;
//import com.yahoo.squidb.data.TableModel;
//import com.yahoo.squidb.sql.Criterion;
//import com.yahoo.squidb.sql.Function;
//import com.yahoo.squidb.sql.Query;
//import com.yahoo.squidb.utility.ContentProviderQueryBuilder;
//import com.yahoo.squidb.utility.ProjectionMap;
//
//import java.util.List;
//
//import io.iserve.iserveandroid.Constant;
//import io.iserve.iserveandroid.data.db.IServeContract.Organizations;
//import io.iserve.iserveandroid.models.Organization;
//import io.iserve.iserveandroid.models.Product;
//
///**
// * Created by Raj on 15/06/15.
// */
//public class IServeDataProvider extends ContentProvider {
//
//    private static final int ORGANIZATION = 100;
//    private static final int ORGANIZATION_ID = 101;
//    private static final int ORGANIZATION_ID_PRODUCTS = 200;
//    private static final int ORGANIZATION_ID_PRODUCT_ID = 201;
//
//    private static final UriMatcher URI_MATCHER = buildUriMatcher();
//    private DatabaseDao dbDao;
//
//    private static final ProjectionMap ORGANIZATION_PROJECTION_MAP = new ProjectionMap();
//    private static final ProjectionMap PRODUCT_PROJECTION_MAP = new ProjectionMap();
//    private static final ProjectionMap COUNT_PROJECTION_MAP = new ProjectionMap();
//    static {
//        ORGANIZATION_PROJECTION_MAP.put(Organizations._ID, Organization.ID);
//        ORGANIZATION_PROJECTION_MAP.put(Organizations.ID_SVR, Organization.ID_SVR);
//        ORGANIZATION_PROJECTION_MAP.put(Organizations.NAME, Organization.NAME);
//        ORGANIZATION_PROJECTION_MAP.put(Organizations.POPULAR_NAME, Organization.POPULAR_NAME);
//        ORGANIZATION_PROJECTION_MAP.put(Organizations.CONTACT_NUMBER1, Organization.CONTACT_NUMBER_1);
//        ORGANIZATION_PROJECTION_MAP.put(Organizations.DESCRIPTION, Organization.DESCRIPTION);
//        ORGANIZATION_PROJECTION_MAP.put(Organizations.CONTACT_NUMBER2, Organization.CONTACT_NUMBER_2);
//        ORGANIZATION_PROJECTION_MAP.put(Organizations.CONTACT_FAX, Organization.CONTACT_FAX);
//        ORGANIZATION_PROJECTION_MAP.put(Organizations.CONTACT_EMAIL, Organization.CONTACT_EMAIL);
//        ORGANIZATION_PROJECTION_MAP.put(Organizations.WEBSITE, Organization.WEBSITE);
//        ORGANIZATION_PROJECTION_MAP.put(Organizations.STATUS, Organization.STATUS);
//        ORGANIZATION_PROJECTION_MAP.put(Organizations.IMG_URL, Organization.IMG_URL);
//        ORGANIZATION_PROJECTION_MAP.put(Organizations.PROFILE_IMG, Organization.PROFILE_IMG);
//        ORGANIZATION_PROJECTION_MAP.put(Organizations.LOCAL_PROFILE_IMG, Organization.LOCAL_PROFILE_IMG);
//
//        PRODUCT_PROJECTION_MAP.put(Organizations.Products._ID, Product.ID);
//        PRODUCT_PROJECTION_MAP.put(Organizations.Products.ORGANIZATION_ID, Product.ORGANIZATION_ID);
//        PRODUCT_PROJECTION_MAP.put(Organizations.Products.ID_SVR, Product.ID_SVR);
//        PRODUCT_PROJECTION_MAP.put(Organizations.Products.NAME, Product.NAME);
//        PRODUCT_PROJECTION_MAP.put(Organizations.Products.CODE, Product.CODE);
//        PRODUCT_PROJECTION_MAP.put(Organizations.Products.CLIENT_PRODUCT_ID, Product.CLIENT_PRODUCT_ID);
//        PRODUCT_PROJECTION_MAP.put(Organizations.Products.DESCRIPTION, Product.DESCRIPTION);
//        PRODUCT_PROJECTION_MAP.put(Organizations.Products.OVERVIEW, Product.OVERVIEW);
//        PRODUCT_PROJECTION_MAP.put(Organizations.Products.TECH_SPECIFICATION, Product.TECH_SPECIFICATION);
//        PRODUCT_PROJECTION_MAP.put(Organizations.Products.PRODUCT_TYPE, Product.PRODUCT_TYPE);
//        PRODUCT_PROJECTION_MAP.put(Organizations.Products.FAMILY, Product.FAMILY);
//        PRODUCT_PROJECTION_MAP.put(Organizations.Products.STATUS, Product.STATUS);
//        PRODUCT_PROJECTION_MAP.put(Organizations.Products.IMG_URL, Product.IMG_URL);
//        PRODUCT_PROJECTION_MAP.put(Organizations.Products.PROFILE_IMG, Product.PROFILE_IMG);
//        PRODUCT_PROJECTION_MAP.put(Organizations.Products.LOCAL_PROFILE_IMG, Product.LOCAL_PROFILE_IMG);
//
//        COUNT_PROJECTION_MAP.put(BaseColumns._COUNT, Function.count());
//    }
//
//
//    private static UriMatcher buildUriMatcher() {
//        final String authority = IServeContract.AUTHORITY;
//        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
//
//        matcher.addURI(authority, "organizations", ORGANIZATION);
//        matcher.addURI(authority, "organizations/#", ORGANIZATION_ID);
//        matcher.addURI(authority, "organizations/#/PRODUCTS", ORGANIZATION_ID_PRODUCTS);
//        matcher.addURI(authority, "organizations/#/PRODUCTS/#", ORGANIZATION_ID_PRODUCT_ID);
//
////        matcher.addURI(authority, "students", STUDENTS);
////        matcher.addURI(authority, "students/#", STUDENTS_ID);
////        matcher.addURI(authority, "students/#/courses", STUDENTS_ID_COURSES);
////
////        matcher.addURI(authority, "courses", COURSES);
////        matcher.addURI(authority, "courses/#", COURSES_ID);
////        matcher.addURI(authority, "courses/#/faculty", COURSES_ID_FACULTY);
////        matcher.addURI(authority, "courses/#/students", COURSES_ID_STUDENTS);
//        return matcher;
//    }
//
//    @Override
//    public boolean onCreate() {
//        Log.i(Constant.ISERVE_LOG_TAG, "Provider on Create");
//        dbDao = new DatabaseDao(new IServeDatabase(getContext()));
//        return true;
//    }
//
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        Query query;
//        switch (URI_MATCHER.match(uri)) {
//            case ORGANIZATION:
//                query = buildOrganizationQuery(projection, selection, selectionArgs, sortOrder);
//                break;
//            case ORGANIZATION_ID:
//                query = buildOrganizationQuery(projection, selection, selectionArgs, sortOrder);
//                query.where(Organization.ID.eq(ContentUris.parseId(uri)));
//                break;
//            case ORGANIZATION_ID_PRODUCTS:
//                query = buildProductQuery(projection, selection, selectionArgs, sortOrder);
//                query.where(Organization.ID.eq(getIdPathSegment(uri, 1)));
//                break;
//            default:
//                throw new UnsupportedOperationException("Unsupported uri: " + uri);
//        }
//
//            query.limit(getLimit(uri));
//            SquidCursor<AbstractModel> cursor = dbDao.query(AbstractModel.class, query);
//            return cursor;
//        }
//
//    @Override
//    public String getType(Uri uri) {
//        switch (URI_MATCHER.match(uri)) {
//            case ORGANIZATION:
//                return Organizations.CONTENT_TYPE;
//            case ORGANIZATION_ID:
//                return Organizations.CONTENT_ITEM_TYPE;
//            case ORGANIZATION_ID_PRODUCTS:
//                return Organizations.Products.CONTENT_TYPE;
//            case ORGANIZATION_ID_PRODUCT_ID:
//                return Organizations.Products.CONTENT_ITEM_TYPE;
//        }
//        throw new UnsupportedOperationException("Unknown uri: " + uri);
//    }
//
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        TableModel model;
//        switch (URI_MATCHER.match(uri)) {
//            case ORGANIZATION:
//                model = organizationFromValues(values);
//                break;
//            case ORGANIZATION_ID_PRODUCTS:
//                model = productFromValues(values);
//                break;
//            default:
//                throw new UnsupportedOperationException("Unsupported uri: " + uri);
//        }
//
//        if (dbDao.createNew(model)) {
//            Uri newUri = ContentUris.withAppendedId(uri, model.getId());
//            getContext().getContentResolver().notifyChange(newUri, null);
//            return newUri;
//        }
//
//        return null;
//    }
//
//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        int numDeleted;
//        switch (URI_MATCHER.match(uri)) {
//            case ORGANIZATION: {
//                numDeleted = dbDao.deleteWhere(Organization.class,
//                                Criterion.fromRawSelection(selection, selectionArgs));
//                break;
//            }
//            case ORGANIZATION_ID: {
//                long id = ContentUris.parseId(uri);
//                numDeleted = dbDao.delete(Organization.class, id) ? 1 : 0;
//                break;
//            }
//            case ORGANIZATION_ID_PRODUCTS: {
//                numDeleted = dbDao.deleteWhere(Product.class,
//                        Criterion.fromRawSelection(selection, selectionArgs));
//                break;
//            }
//            case ORGANIZATION_ID_PRODUCT_ID: {
//                long id = ContentUris.parseId(uri);
//                numDeleted = dbDao.delete(Product.class, id) ? 1 : 0;
//                break;
//            }
//            default:
//                throw new UnsupportedOperationException("Unsupported uri: " + uri);
//        }
//
//        if (numDeleted > 0) {
//            getContext().getContentResolver().notifyChange(uri, null);
//        }
//        return numDeleted;
//    }
//
//    @Override
//    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//
//        values.remove(BaseColumns._ID);
//        int numUpdated = 0;
//        switch (URI_MATCHER.match(uri)) {
//            case ORGANIZATION: {
//                Organization org= organizationFromValues(values);
//                numUpdated = dbDao.update(Criterion.fromRawSelection(selection, selectionArgs), org);
//                break;
//            }
//            case ORGANIZATION_ID: {
//                Organization org= organizationFromValues(values);
//                org.setId(ContentUris.parseId(uri));
//                numUpdated = dbDao.saveExisting(org) ? 1 : 0;
//                break;
//            }
//            case ORGANIZATION_ID_PRODUCTS: {
//                Product product = productFromValues(values);
//                numUpdated = dbDao.update(Criterion.fromRawSelection(selection, selectionArgs), product);
//                break;
//            }
//            case ORGANIZATION_ID_PRODUCT_ID: {
//                Product product = productFromValues(values);
//                product.setId(ContentUris.parseId(uri));
//                numUpdated = dbDao.saveExisting(product) ? 1 : 0;
//                break;
//            }
//            default:
//                throw new UnsupportedOperationException("Unsupported uri: " + uri);
//        }
//
//        if (numUpdated > 0) {
//            getContext().getContentResolver().notifyChange(uri, null);
//        }
//        return numUpdated;
//    }
//
//    private Organization organizationFromValues(ContentValues values) {
//
//        Organization org = new Organization();
//        org.setId(values.getAsLong(String.valueOf(Organization.ID)));
//        org.setName(values.getAsString(String.valueOf(Organization.NAME)));
//        org.setIdSvr(values.getAsString(String.valueOf(Organization.ID_SVR)));
//        org.setPopularName(values.getAsString(String.valueOf(Organization.POPULAR_NAME)));
//        org.setDescription(values.getAsString(String.valueOf(Organization.DESCRIPTION)));
//        org.setContactNumber1(values.getAsString(String.valueOf(Organization.CONTACT_NUMBER_1)));
//        org.setContactNumber2(values.getAsString(String.valueOf(Organization.CONTACT_NUMBER_2)));
//        org.setContactFax(values.getAsString(String.valueOf(Organization.CONTACT_FAX)));
//        org.setContactEmail(values.getAsString(String.valueOf(Organization.CONTACT_EMAIL)));
//        org.setWebsite(values.getAsString(String.valueOf(Organization.WEBSITE)));
//        org.setStatus(values.getAsString(String.valueOf(Organization.STATUS)));
//        org.setImgUrl(values.getAsString(String.valueOf(Organization.IMG_URL)));
//        org.setProfileImg(values.getAsString(String.valueOf(Organization.PROFILE_IMG)));
//        org.setLocalProfileImg(values.getAsString(String.valueOf(Organization.LOCAL_PROFILE_IMG)));
//
//        return org;
//    }
//
//    private Product productFromValues(ContentValues values) {
//
//        Product product = new Product();
//        product.setId(values.getAsLong(String.valueOf(Product.ID)));
//        product.setOrganizationId(values.getAsLong(String.valueOf(Product.ORGANIZATION_ID)));
//        product.setIdSvr(values.getAsString(String.valueOf(Product.ID_SVR)));
//        product.setName(values.getAsString(String.valueOf(Product.NAME)));
//        product.setCode(values.getAsString(String.valueOf(Product.CODE)));
//        product.setClientProductId(values.getAsString(String.valueOf(Product.CLIENT_PRODUCT_ID)));
//        product.setDescription(values.getAsString(String.valueOf(Product.DESCRIPTION)));
//        product.setOverview(values.getAsString(String.valueOf(Product.OVERVIEW)));
//        product.setTechSpecification(values.getAsString(String.valueOf(Product.TECH_SPECIFICATION)));
//        product.setProductType(values.getAsString(String.valueOf(Product.PRODUCT_TYPE)));
//        product.setFamily(values.getAsString(String.valueOf(Product.FAMILY)));
//        product.setStatus(values.getAsString(String.valueOf(Product.STATUS)));
//        product.setImgUrl(values.getAsString(String.valueOf(Product.IMG_URL)));
//        product.setProfileImg(values.getAsString(String.valueOf(Product.PROFILE_IMG)));
//        product.setLocalProfileImg(values.getAsString(String.valueOf(Product.LOCAL_PROFILE_IMG)));
//
//        return product;
//    }
//
//    private long getIdPathSegment(Uri uri, int pathIndex) {
//        if (pathIndex < 0) {
//            throw new IllegalArgumentException("pathIndex can't be less than zero");
//        }
//        List<String> pathSegments = uri.getPathSegments();
//        if (pathSegments.size() <= pathIndex) {
//            throw new IllegalArgumentException("Uri has too few path segments");
//        }
//        return Long.parseLong(pathSegments.get(pathIndex));
//    }
//
//    private int getLimit(Uri uri) {
//        int limit = Query.NO_LIMIT;
//        String paramString = uri.getQueryParameter(IServeContract.QUERY_PARAM_LIMIT);
//        if (!TextUtils.isEmpty(paramString)) {
//            try {
//                limit = Integer.parseInt(paramString);
//            } catch (NumberFormatException e) {
//                // ignore and return default
//            }
//        }
//        return limit;
//    }
//
//    private boolean isCountProjection(String[] projection) {
//        return projection != null && projection.length == 1 && BaseColumns._COUNT.equals(projection[0]);
//    }
//
//    private Query buildOrganizationQuery(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        ProjectionMap map = isCountProjection(projection) ? COUNT_PROJECTION_MAP : ORGANIZATION_PROJECTION_MAP;
//        ContentProviderQueryBuilder builder = new ContentProviderQueryBuilder()
//                                                    .setStrict(true)
//                                                    .setProjectionMap(map)
//                                                    .setDataSource(Organization.TABLE)
//                                                    .setDefaultOrder(Organization.NAME.asc());
//        return builder.build(projection, selection, selectionArgs, sortOrder);
//    }
//
//    private Query buildProductQuery(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        ProjectionMap map = isCountProjection(projection) ? COUNT_PROJECTION_MAP : PRODUCT_PROJECTION_MAP;
//        ContentProviderQueryBuilder builder = new ContentProviderQueryBuilder()
//                                                    .setStrict(true)
//                                                    .setProjectionMap(map)
//                                                    .setDataSource(Product.TABLE)
//                                                    .setDefaultOrder(Product.NAME.asc());
//        return builder.build(projection, selection, selectionArgs, sortOrder);
//    }
//}
