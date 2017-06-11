package com.nagy.mohamed.ardelkonouz.offlineDatabase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by mohamednagy on 6/10/2017.
 */
public class ContentProviderDatabase extends ContentProvider {

    private UriMatcher m_uriMatcher = buildUriMatcher();
    private DbHelper m_dbHelper;

    private static final int CHILD_TABLE = 0;
    private static final int COURSE_TABLE = 1;
    private static final int INSTRUCTOR_TABLE = 10;
    private static final int EMPLOYEE_TABLE = 111;
    private static final int CHILD_COURSE_TABLE = 11;
    private static final int INSTRUCTOR_COURSE_TABLE = 110;
    private static final int CHILD_WITH_ID_TABLE = 1010;
    private static final int COURSE_WITH_ID_TABLE = 1110;
    private static final int INSTRUCTOR_WITH_ID_TABLE = 1111;
    private static final int CHILD_COURSE_WITH_CHILD_ID_TABLE = 11110;
    private static final int CHILD_COURSE_WITH_COURSE_ID_TABLE = 11111;
    private static final int INSTRUCTOR_COURSE_WITH_INSTRUCTOR_ID_TABLE = 10101;
    private static final int INSTRUCTOR_COURSE_WITH_COURSE_ID_TABLE = 10011;
    private static final int EMPLOYEE_WITH_ID_TABLE = 10111;


    private static final String INNER_JOIN = "INNER JOINT";
    private static final String ON = "ON";

    private static final SQLiteQueryBuilder COURSE_INSTRUCTOR_QUERY =
            new SQLiteQueryBuilder();

    static {
        COURSE_INSTRUCTOR_QUERY.setTables(
                DbContent.CourseInstructorTable.TABLE_NAME + DbContent.SPACE + INNER_JOIN +
                        DbContent.SPACE + DbContent.InstructorTable.TABLE_NAME +
                        DbContent.SPACE + ON + DbContent.SPACE +  DbContent.InstructorTable.TABLE_NAME +
                        "." + DbContent.InstructorTable._ID +
                        "=" + DbContent.CourseInstructorTable.TABLE_NAME + "." +
                        DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN
        );
    }

    private static final SQLiteQueryBuilder COURSE_CHILD_QUERY =
            new SQLiteQueryBuilder();

    static {
        COURSE_CHILD_QUERY.setTables(
                DbContent.ChildCourseTable.TABLE_NAME + DbContent.SPACE + INNER_JOIN +
                        DbContent.SPACE + DbContent.ChildTable.TABLE_NAME +
                        DbContent.SPACE + ON + DbContent.SPACE + DbContent.ChildTable.TABLE_NAME +
                        "." + DbContent.ChildTable._ID +
                        "=" + DbContent.ChildCourseTable.TABLE_NAME + "." +
                        DbContent.ChildCourseTable.CHILD_ID_COLUMN
        );
    }


    @Override
    public boolean onCreate() {
        m_dbHelper = new DbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int match = m_uriMatcher.match(uri);

        switch(match){
            case CHILD_TABLE:
                return m_dbHelper.getReadableDatabase().query(
                        DbContent.ChildTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
            case COURSE_TABLE:
                return m_dbHelper.getReadableDatabase().query(
                        DbContent.CourseTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
            case INSTRUCTOR_TABLE:
                return m_dbHelper.getReadableDatabase().query(
                        DbContent.InstructorTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

            case INSTRUCTOR_COURSE_TABLE:
                return COURSE_INSTRUCTOR_QUERY.query(
                        m_dbHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

            case CHILD_COURSE_TABLE:
                return COURSE_CHILD_QUERY.query(
                        m_dbHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

            case EMPLOYEE_TABLE:
                return m_dbHelper.getReadableDatabase().query(
                        DbContent.EmployeeTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

            case CHILD_WITH_ID_TABLE:
                return getChildWithId(uri, projection, sortOrder);

            case INSTRUCTOR_WITH_ID_TABLE:
                return getInstructorWithId(uri, projection, sortOrder);

            case COURSE_WITH_ID_TABLE:
                return getCourseWithId(uri, projection, sortOrder);

            case EMPLOYEE_WITH_ID_TABLE:
                return getEmployeeWithId(uri, projection, sortOrder);

            case INSTRUCTOR_COURSE_WITH_COURSE_ID_TABLE:
                return getCourseInstructorWithCourseId(uri, projection, sortOrder);

            case INSTRUCTOR_COURSE_WITH_INSTRUCTOR_ID_TABLE:
                return getCourseInstructorWithInstructorId(uri, projection, sortOrder);

            case CHILD_COURSE_WITH_CHILD_ID_TABLE:
                return getCourseChildWithChildId(uri, projection, sortOrder);

            case CHILD_COURSE_WITH_COURSE_ID_TABLE:
                return getCourseChildWithCourseId(uri, projection, sortOrder);

            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        int match = m_uriMatcher.match(uri);

        switch (match){

            case INSTRUCTOR_TABLE:
                return DbContent.InstructorTable.CONTENT_TYPE;

            case CHILD_COURSE_TABLE:
                return DbContent.ChildTable.CONTENT_TYPE;

            case COURSE_TABLE:
                return DbContent.CourseTable.CONTENT_TYPE;

            case EMPLOYEE_TABLE:
                return DbContent.EmployeeTable.CONTENT_TYPE;

            case EMPLOYEE_WITH_ID_TABLE:
                return DbContent.EmployeeTable.CONTENT_ITEM_TYPE;

            case INSTRUCTOR_WITH_ID_TABLE:
                return DbContent.InstructorTable.CONTENT_ITEM_TYPE;

            case COURSE_WITH_ID_TABLE:
                return DbContent.CourseTable.CONTENT_ITEM_TYPE;

            case CHILD_WITH_ID_TABLE:
                return DbContent.ChildTable.CONTENT_ITEM_TYPE;

        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int match = m_uriMatcher.match(uri);
        Long insertResult = null;
        Log.e("vvvvvvvvvvvvvvvvvvv",uri.toString() + "vvvvvvvvvvvvvvvvvvv");

        switch (match){
            case INSTRUCTOR_TABLE:
                insertResult = m_dbHelper.getWritableDatabase().insert(
                        DbContent.InstructorTable.TABLE_NAME,
                        null,
                        contentValues
                );
                break;

            case CHILD_TABLE:
                insertResult = m_dbHelper.getWritableDatabase().insert(
                        DbContent.ChildTable.TABLE_NAME,
                        null,
                        contentValues
                );
                break;

            case COURSE_TABLE:
                insertResult = m_dbHelper.getWritableDatabase().insert(
                        DbContent.CourseTable.TABLE_NAME,
                        null,
                        contentValues
                );
                break;

            case EMPLOYEE_TABLE:
                insertResult = m_dbHelper.getWritableDatabase().insert(
                        DbContent.EmployeeTable.TABLE_NAME,
                        null,
                        contentValues
                );
                break;

            case CHILD_COURSE_TABLE:
                insertResult = m_dbHelper.getWritableDatabase().insert(
                        DbContent.ChildCourseTable.TABLE_NAME,
                        null,
                        contentValues
                );
                break;

            case INSTRUCTOR_COURSE_TABLE:
                insertResult = m_dbHelper.getWritableDatabase().insert(
                        DbContent.CourseInstructorTable.TABLE_NAME,
                        null,
                        contentValues
                );
                break;


            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }


        return ContentUris.withAppendedId(uri, insertResult);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = m_uriMatcher.match(uri);

        switch (match){

            case CHILD_TABLE:
                return m_dbHelper.getWritableDatabase().delete(
                        DbContent.ChildTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );

            case INSTRUCTOR_TABLE:
                return m_dbHelper.getWritableDatabase().delete(
                        DbContent.InstructorTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );

            case COURSE_TABLE:
                return m_dbHelper.getWritableDatabase().delete(
                        DbContent.CourseTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );

            case EMPLOYEE_TABLE:
                return m_dbHelper.getWritableDatabase().delete(
                        DbContent.EmployeeTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );

            case INSTRUCTOR_COURSE_TABLE:
                return m_dbHelper.getWritableDatabase().delete(
                        DbContent.CourseInstructorTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );

            case CHILD_COURSE_TABLE:
                return m_dbHelper.getWritableDatabase().delete(
                        DbContent.ChildCourseTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );

            case EMPLOYEE_WITH_ID_TABLE:
                return deleteRowWithId(DbContent.EmployeeTable.TABLE_NAME, uri, DbContent.EmployeeTable._ID);

            case COURSE_WITH_ID_TABLE:
                return deleteRowWithId(DbContent.CourseTable.TABLE_NAME, uri, DbContent.CourseTable._ID);

            case INSTRUCTOR_WITH_ID_TABLE:
                return deleteRowWithId(DbContent.InstructorTable.TABLE_NAME, uri, DbContent.InstructorTable._ID);

            case CHILD_WITH_ID_TABLE:
                return deleteRowWithId(DbContent.ChildTable.TABLE_NAME, uri, DbContent.ChildTable._ID);

            case CHILD_COURSE_WITH_CHILD_ID_TABLE:
                return deleteRowWithId(DbContent.ChildCourseTable.TABLE_NAME, uri,
                        DbContent.ChildCourseTable.CHILD_ID_COLUMN);

            case CHILD_COURSE_WITH_COURSE_ID_TABLE:
                return deleteRowWithId(DbContent.ChildCourseTable.TABLE_NAME, uri,
                        DbContent.ChildCourseTable.COURSE_ID_COLUMN);

            case INSTRUCTOR_COURSE_WITH_COURSE_ID_TABLE:
                return deleteRowWithId(DbContent.CourseInstructorTable.TABLE_NAME, uri,
                        DbContent.CourseInstructorTable.COURSE_ID_COLUMN);

            case INSTRUCTOR_COURSE_WITH_INSTRUCTOR_ID_TABLE:
                return deleteRowWithId(DbContent.CourseInstructorTable.TABLE_NAME, uri,
                        DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN);

        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        int match = m_uriMatcher.match(uri);

        switch (match){
            case CHILD_TABLE:
                return m_dbHelper.getWritableDatabase().update(
                        DbContent.ChildTable.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs
                );

            case INSTRUCTOR_TABLE:
                return m_dbHelper.getWritableDatabase().update(
                        DbContent.InstructorTable.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs
                );

            case COURSE_TABLE:
                return m_dbHelper.getWritableDatabase().update(
                        DbContent.CourseTable.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs
                );

            case EMPLOYEE_TABLE:
                return m_dbHelper.getWritableDatabase().update(
                        DbContent.EmployeeTable.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs
                );

            case CHILD_COURSE_TABLE:
                return m_dbHelper.getWritableDatabase().update(
                        DbContent.ChildCourseTable.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs
                );

            case INSTRUCTOR_COURSE_TABLE:
                return m_dbHelper.getWritableDatabase().update(
                        DbContent.CourseInstructorTable.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs
                );

            case CHILD_WITH_ID_TABLE:
                return updateRowWithId(
                        uri,
                        contentValues,
                        DbContent.ChildTable.TABLE_NAME,
                        DbContent.ChildTable._ID
                );

            case INSTRUCTOR_WITH_ID_TABLE:
                return updateRowWithId(
                        uri,
                        contentValues,
                        DbContent.InstructorTable.TABLE_NAME,
                        DbContent.InstructorTable._ID
                );

            case COURSE_WITH_ID_TABLE:
                return updateRowWithId(
                        uri,
                        contentValues,
                        DbContent.CourseTable.TABLE_NAME,
                        DbContent.CourseTable._ID
                );

            case EMPLOYEE_WITH_ID_TABLE:
                return updateRowWithId(
                        uri,
                        contentValues,
                        DbContent.EmployeeTable.TABLE_NAME,
                        DbContent.EmployeeTable._ID
                );


            case INSTRUCTOR_COURSE_WITH_COURSE_ID_TABLE:
                return updateRowWithId(
                        uri,
                        contentValues,
                        DbContent.CourseInstructorTable.TABLE_NAME,
                        DbContent.CourseInstructorTable.COURSE_ID_COLUMN
                );

            case INSTRUCTOR_COURSE_WITH_INSTRUCTOR_ID_TABLE:
                return updateRowWithId(
                        uri,
                        contentValues,
                        DbContent.CourseInstructorTable.TABLE_NAME,
                        DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN
                );

            case CHILD_COURSE_WITH_CHILD_ID_TABLE:
                return updateRowWithId(
                        uri,
                        contentValues,
                        DbContent.ChildCourseTable.TABLE_NAME,
                        DbContent.ChildCourseTable.CHILD_ID_COLUMN
                );

            case CHILD_COURSE_WITH_COURSE_ID_TABLE:
                return updateRowWithId(
                        uri,
                        contentValues,
                        DbContent.ChildCourseTable.TABLE_NAME,
                        DbContent.ChildCourseTable.COURSE_ID_COLUMN
                );

        }
        return 0;
    }

    private UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String CHILD_PATH = DbContent.ChildTable.TABLE_NAME;
        final String COURSE_PATH = DbContent.CourseTable.TABLE_NAME;
        final String INSTRUCTOR_PATH = DbContent.InstructorTable.TABLE_NAME;
        final String EMPLOYEE_PATH = DbContent.EmployeeTable.TABLE_NAME;
        final String CHILD_COURSE_PATH = DbContent.ChildCourseTable.TABLE_NAME;
        final String INSTRUCTOR_COURSE_PATH = DbContent.CourseInstructorTable.TABLE_NAME;
        final String CHILD_WITH_ID_PATH = DbContent.ChildTable.TABLE_NAME + "/#";
        final String EMPLOYEE_WITH_ID_PATH = DbContent.EmployeeTable.TABLE_NAME + "/#";
        final String COURSE_WITH_ID_PATH = DbContent.CourseTable.TABLE_NAME + "/#";
        final String INSTRUCTOR_WITH_ID_PATH = DbContent.InstructorTable.TABLE_NAME + "/#";
        final String CHILD_COURSE_WITH_CHILD_ID_PATH = DbContent.ChildCourseTable.TABLE_NAME + "/" +
                DbContent.ChildTable.TABLE_NAME + "/#";
        final String CHILD_COURSE_WITH_COURSE_ID_PATH = DbContent.ChildCourseTable.TABLE_NAME + "/" +
                DbContent.CourseTable.TABLE_NAME + "/#";
        final String INSTRUCTOR_COURSE_WITH_INSTRUCTOR_ID_PATH = DbContent.CourseInstructorTable.TABLE_NAME + "/" +
                DbContent.InstructorTable.TABLE_NAME +"/#";
        final String INSTRUCTOR_COURSE_WITH_COURSE_ID_PATH = DbContent.CourseInstructorTable.TABLE_NAME + "/" +
                DbContent.CourseTable.TABLE_NAME +"/#";


        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, CHILD_PATH, CHILD_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, COURSE_PATH, COURSE_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, INSTRUCTOR_PATH, INSTRUCTOR_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, CHILD_COURSE_PATH, CHILD_COURSE_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, INSTRUCTOR_COURSE_PATH, INSTRUCTOR_COURSE_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, CHILD_WITH_ID_PATH, CHILD_WITH_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, COURSE_WITH_ID_PATH, COURSE_WITH_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, EMPLOYEE_PATH, EMPLOYEE_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, EMPLOYEE_WITH_ID_PATH, EMPLOYEE_WITH_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, INSTRUCTOR_WITH_ID_PATH, INSTRUCTOR_WITH_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, CHILD_COURSE_WITH_CHILD_ID_PATH, CHILD_COURSE_WITH_CHILD_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, CHILD_COURSE_WITH_COURSE_ID_PATH, CHILD_COURSE_WITH_COURSE_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, INSTRUCTOR_COURSE_WITH_INSTRUCTOR_ID_PATH, INSTRUCTOR_COURSE_WITH_INSTRUCTOR_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, INSTRUCTOR_COURSE_WITH_COURSE_ID_PATH, INSTRUCTOR_COURSE_WITH_COURSE_ID_TABLE);

        return uriMatcher;
    }

    private Cursor getChildWithId(Uri uri, String[] projection, String sortOrder){
        long columnId = ContentUris.parseId(uri);

        String selection = DbContent.ChildTable._ID +"=?";
        String[] selectionArgs = {String.valueOf(columnId)};

        return m_dbHelper.getReadableDatabase().query(
                DbContent.ChildTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getCourseWithId(Uri uri, String[] projection, String sortOrder){
        long columnId = ContentUris.parseId(uri);

        String selection = DbContent.CourseTable._ID +"=?";
        String[] selectionArgs = {String.valueOf(columnId)};

        return m_dbHelper.getReadableDatabase().query(
                DbContent.CourseTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getEmployeeWithId(Uri uri, String[] projection, String sortOrder){
        long columnId = ContentUris.parseId(uri);

        String selection = DbContent.EmployeeTable._ID +"=?";
        String[] selectionArgs = {String.valueOf(columnId)};

        return m_dbHelper.getReadableDatabase().query(
                DbContent.EmployeeTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getInstructorWithId(Uri uri, String[] projection, String sortOrder){
        long columnId = ContentUris.parseId(uri);

        String selection = DbContent.CourseTable._ID +"=?";
        String[] selectionArgs = {String.valueOf(columnId)};

        return m_dbHelper.getReadableDatabase().query(
                DbContent.InstructorTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getCourseInstructorWithInstructorId(Uri uri, String[] projection, String sortOrder){
        long columnId = ContentUris.parseId(uri);

        String selection = DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN +"=?";
        String[] selectionArgs = {String.valueOf(columnId)};

        return COURSE_INSTRUCTOR_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getCourseInstructorWithCourseId(Uri uri, String[] projection, String sortOrder){
        long columnId = ContentUris.parseId(uri);

        String selection = DbContent.CourseInstructorTable.COURSE_ID_COLUMN +"=?";
        String[] selectionArgs = {String.valueOf(columnId)};

        return COURSE_INSTRUCTOR_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getCourseChildWithCourseId(Uri uri, String[] projection, String sortOrder){
        long columnId = ContentUris.parseId(uri);

        String selection = DbContent.ChildCourseTable.COURSE_ID_COLUMN +"=?";
        String[] selectionArgs = {String.valueOf(columnId)};

        return COURSE_CHILD_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getCourseChildWithChildId(Uri uri, String[] projection, String sortOrder){
        long columnId = ContentUris.parseId(uri);

        String selection = DbContent.ChildCourseTable.CHILD_ID_COLUMN +"=?";
        String[] selectionArgs = {String.valueOf(columnId)};

        return COURSE_CHILD_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private int deleteRowWithId(String tableName, Uri uri, String column){
        long columnId = ContentUris.parseId(uri);

        String selection = column + "=?";
        String[] selectionArgs = {String.valueOf(columnId)};

        return m_dbHelper.getWritableDatabase().delete(
                tableName,
                selection,
                selectionArgs
        );
    }

    private int updateRowWithId(Uri uri, ContentValues contentValues, String tableName, String column){
        long columnId = ContentUris.parseId(uri);

        String selection = column + "=?";
        String[] selectionArgs = {String.valueOf(columnId)};

        return m_dbHelper.getWritableDatabase().update(
                tableName,
                contentValues,
                selection,
                selectionArgs
        );
    }

}
