package com.nagy.mohamed.ardelkonouz.offlineDatabase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.Utility;

import java.net.URISyntaxException;

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
    private static final int CHILD_COURSE_WITH_CHILD_ID_COURSE_ID_TABLE = 3;
    private static final int INSTRUCTOR_COURSE_WITH_INSTRUCTOR_ID_TABLE = 10101;
    private static final int INSTRUCTOR_COURSE_WITH_COURSE_ID_TABLE = 10011;
    private static final int EMPLOYEE_WITH_ID_TABLE = 10111;
    private static final int COURSE_WITH_DATE_WITH_COMPLETE_ID_AGE_RANGE_TABLE = 2;
    private static final int COURSE_WITH_ID_WITH_END_DATE_TABLE = 4;
    private static final int CHILD_WITH_SEARCH_TABLE = 5;
    private static final int COURSE_WITH_SEARCH_TABLE = 6;
    private static final int INSTRUCTOR_WITH_SEARCH_TABLE = 7;
    private static final int EMPLOYEE_WITH_SEARCH_TABLE = 8;
    private static final int SHIFT_WITH_COURSE_ID_TABLE = 9;
    private static final int SHIFT_TABLE = 14;
    private static final int SHIFT_WITH_COURSE_ID_JOIN_TABLE = 12;
    private static final int COURSE_WITH_DAY_SEARCH_TABLE = 13;

    private static final String INNER_JOIN = "INNER JOIN";
    private static final String ON = "ON";

    private static final SQLiteQueryBuilder COURSE_INSTRUCTOR_JOIN_COURSE_QUERY =
            new SQLiteQueryBuilder();

    static {
        COURSE_INSTRUCTOR_JOIN_COURSE_QUERY.setTables(
                DbContent.CourseInstructorTable.TABLE_NAME + DbContent.SPACE + INNER_JOIN +
                        DbContent.SPACE + DbContent.CourseTable.TABLE_NAME +
                        DbContent.SPACE + ON + DbContent.SPACE +  DbContent.CourseTable.TABLE_NAME +
                        "." + DbContent.CourseTable._ID +
                        "=" + DbContent.CourseInstructorTable.TABLE_NAME + "." +
                        DbContent.CourseInstructorTable.COURSE_ID_COLUMN
        );
    }

    private static final SQLiteQueryBuilder COURSE_INSTRUCTOR_QUERY =
            new SQLiteQueryBuilder();

    static {
        COURSE_INSTRUCTOR_QUERY.setTables(
                DbContent.CourseInstructorTable.TABLE_NAME + DbContent.SPACE + INNER_JOIN +
                        DbContent.SPACE + DbContent.InstructorTable.TABLE_NAME +
                        DbContent.SPACE + ON + DbContent.SPACE +  DbContent.InstructorTable.TABLE_NAME +
                        "." + DbContent.InstructorTable._ID +
                        "=" + DbContent.CourseInstructorTable.TABLE_NAME + "." +
                        DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN + DbContent.SPACE + INNER_JOIN +
                        DbContent.SPACE + DbContent.CourseTable.TABLE_NAME +
                        DbContent.SPACE + ON + DbContent.SPACE +  DbContent.CourseTable.TABLE_NAME +
                        "." + DbContent.CourseTable._ID +
                        "=" + DbContent.CourseInstructorTable.TABLE_NAME + "." +
                        DbContent.CourseInstructorTable.COURSE_ID_COLUMN
        );
    }

    private static final SQLiteQueryBuilder SHIFT_WITH_COURSE_QUERY =
            new SQLiteQueryBuilder();

    static {
        SHIFT_WITH_COURSE_QUERY.setTables(
                DbContent.ShiftDaysTable.TABLE_NAME + DbContent.SPACE + INNER_JOIN +
                        DbContent.SPACE + DbContent.CourseTable.TABLE_NAME +
                        DbContent.SPACE + ON + DbContent.SPACE +  DbContent.CourseTable.TABLE_NAME +
                        "." + DbContent.CourseTable._ID +
                        "=" + DbContent.ShiftDaysTable.TABLE_NAME + "." +
                        DbContent.ShiftDaysTable.COURSE_ID_COLUMN
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
                        DbContent.ChildCourseTable.CHILD_ID_COLUMN + DbContent.SPACE + INNER_JOIN +
                        DbContent.SPACE + DbContent.CourseTable.TABLE_NAME +
                        DbContent.SPACE + ON + DbContent.SPACE +  DbContent.CourseTable.TABLE_NAME +
                        "." + DbContent.CourseTable._ID +
                        "=" + DbContent.ChildCourseTable.TABLE_NAME + "." +
                        DbContent.ChildCourseTable.COURSE_ID_COLUMN
        );
    }


    @Override
    public boolean onCreate() {
        m_dbHelper = new DbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int match = m_uriMatcher.match(uri);
        Log.e("query",uri.toString());
        Log.e("match",String.valueOf(match));
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

            case COURSE_WITH_DATE_WITH_COMPLETE_ID_AGE_RANGE_TABLE:
                return getCourseWithEndDateWithCompleteAndAgeRangeTable(uri, projection, sortOrder);

            case CHILD_COURSE_WITH_CHILD_ID_COURSE_ID_TABLE:
                try {
                    return getCourseChildWithChildIdCourseId(uri, projection, sortOrder);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            case COURSE_WITH_ID_WITH_END_DATE_TABLE:
                return getCourseWithIdWithEndDateId(uri, projection, sortOrder);

            case CHILD_WITH_SEARCH_TABLE:
                return getChildWithSearch(uri, projection, sortOrder);

            case COURSE_WITH_SEARCH_TABLE:
                return getCourseWithSearch(uri, projection, sortOrder);

            case INSTRUCTOR_WITH_SEARCH_TABLE:
                return getInstructorWithSearch(uri, projection, sortOrder);

            case EMPLOYEE_WITH_SEARCH_TABLE:
                return getEmployeeWithSearch(uri, projection, sortOrder);

            case SHIFT_WITH_COURSE_ID_TABLE:
                return getShiftWithCourseId(uri, projection, sortOrder);

            case SHIFT_WITH_COURSE_ID_JOIN_TABLE:
                return getShiftWithCourseIdJoin(uri, projection, sortOrder);

            case COURSE_WITH_DAY_SEARCH_TABLE:
                return getCourseWithDaySearch(uri, projection, sortOrder);

            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }
    }

    @Override
    public String getType(@NonNull Uri uri) {

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

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        int match = m_uriMatcher.match(uri);
        Long insertResult = null;

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

            case SHIFT_TABLE:
                insertResult = m_dbHelper.getWritableDatabase().insert(
                        DbContent.ShiftDaysTable.TABLE_NAME,
                        null,
                        contentValues
                );

            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }


        return ContentUris.withAppendedId(uri, insertResult);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
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

            case SHIFT_WITH_COURSE_ID_TABLE:
                return deleteRowWithId(DbContent.ShiftDaysTable.TABLE_NAME,
                        uri,
                        DbContent.ShiftDaysTable.COURSE_ID_COLUMN);

            case EMPLOYEE_WITH_ID_TABLE:
                return deleteRowWithId(DbContent.EmployeeTable.TABLE_NAME,
                        uri,
                        DbContent.EmployeeTable._ID);

            case COURSE_WITH_ID_TABLE:
                return deleteRowWithId(DbContent.CourseTable.TABLE_NAME,
                        uri,
                        DbContent.CourseTable._ID);

            case INSTRUCTOR_WITH_ID_TABLE:
                return deleteRowWithId(DbContent.InstructorTable.TABLE_NAME,
                        uri,
                        DbContent.InstructorTable._ID);

            case CHILD_WITH_ID_TABLE:
                return deleteRowWithId(DbContent.ChildTable.TABLE_NAME,
                        uri,
                        DbContent.ChildTable._ID);

            case CHILD_COURSE_WITH_CHILD_ID_TABLE:
                return deleteRowWithId(DbContent.ChildCourseTable.TABLE_NAME,
                        uri,
                        DbContent.ChildCourseTable.CHILD_ID_COLUMN);

            case CHILD_COURSE_WITH_COURSE_ID_TABLE:
                return deleteRowWithId(DbContent.ChildCourseTable.TABLE_NAME,
                        uri,
                        DbContent.ChildCourseTable.COURSE_ID_COLUMN);

            case INSTRUCTOR_COURSE_WITH_COURSE_ID_TABLE:
                return deleteRowWithId(DbContent.CourseInstructorTable.TABLE_NAME,
                        uri,
                        DbContent.CourseInstructorTable.COURSE_ID_COLUMN);

            case INSTRUCTOR_COURSE_WITH_INSTRUCTOR_ID_TABLE:
                return deleteRowWithId(DbContent.CourseInstructorTable.TABLE_NAME,
                        uri,
                        DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN);

        }

        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
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

            case SHIFT_WITH_COURSE_ID_TABLE:
                return updateRowWithId(
                        uri,
                        contentValues,
                        DbContent.ShiftDaysTable.TABLE_NAME,
                        DbContent.ShiftDaysTable.COURSE_ID_COLUMN
                );

        }
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        int match = m_uriMatcher.match(uri);
        int counter = 0;

        switch (match){
            case INSTRUCTOR_TABLE:
                for(ContentValues contentValues : values) {
                    counter++;
                     m_dbHelper.getWritableDatabase().insert(
                            DbContent.InstructorTable.TABLE_NAME,
                            null,
                            contentValues
                    );
                }
                break;

            case CHILD_TABLE:
                for(ContentValues contentValues : values) {
                    counter++;
                    m_dbHelper.getWritableDatabase().insert(
                            DbContent.ChildTable.TABLE_NAME,
                            null,
                            contentValues
                    );
                }
                break;

            case COURSE_TABLE:
                for(ContentValues contentValues : values) {
                    counter++;
                    m_dbHelper.getWritableDatabase().insert(
                            DbContent.CourseTable.TABLE_NAME,
                            null,
                            contentValues
                    );
                }
                break;

            case EMPLOYEE_TABLE:
                for(ContentValues contentValues : values) {
                    counter++;
                    m_dbHelper.getWritableDatabase().insert(
                            DbContent.EmployeeTable.TABLE_NAME,
                            null,
                            contentValues
                    );
                }
                break;

            case CHILD_COURSE_TABLE:
                for(ContentValues contentValues : values) {
                    counter++;
                    m_dbHelper.getWritableDatabase().insert(
                            DbContent.ChildCourseTable.TABLE_NAME,
                            null,
                            contentValues
                    );
                }
                break;

            case INSTRUCTOR_COURSE_TABLE:
                for(ContentValues contentValues : values) {
                    counter++;
                    m_dbHelper.getWritableDatabase().insert(
                            DbContent.CourseInstructorTable.TABLE_NAME,
                            null,
                            contentValues
                    );
                }
                break;


            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }


        return counter;
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
        final String COURSE_WITH_DATE_WITH_COMPLETE_ID_AGE_RANGE_PATH = DbContent.CourseTable.TABLE_NAME + "/" +
                DbContent.CourseTable.COURSE_END_DATE_COLUMN + "/" +
                DbContent.CourseTable.COURSE_AVAILABLE_POSITIONS_COLUMN+ "/#" + "/#";
        final String INSTRUCTOR_WITH_ID_PATH = DbContent.InstructorTable.TABLE_NAME + "/#";
        final String CHILD_COURSE_WITH_CHILD_ID_PATH = DbContent.ChildCourseTable.TABLE_NAME + "/" +
                DbContent.ChildTable.TABLE_NAME + "/#";
        final String CHILD_COURSE_WITH_COURSE_ID_PATH = DbContent.ChildCourseTable.TABLE_NAME + "/" +
                DbContent.CourseTable.TABLE_NAME + "/#";
        final String CHILD_COURSE_WITH_CHILD_ID_COURSE_ID_TABLE_PATH = DbContent.ChildCourseTable.TABLE_NAME + "/" +
                DbContent.ChildTable.TABLE_NAME + "/" + DbContent.CourseTable.TABLE_NAME + "/#" + "/#";
        final String INSTRUCTOR_COURSE_WITH_INSTRUCTOR_ID_PATH = DbContent.CourseInstructorTable.TABLE_NAME + "/" +
                DbContent.InstructorTable.TABLE_NAME +"/#";
        final String INSTRUCTOR_COURSE_WITH_COURSE_ID_PATH = DbContent.CourseInstructorTable.TABLE_NAME + "/" +
                DbContent.CourseTable.TABLE_NAME +"/#";
        final String COURSE_WITH_ID_WITH_END_DATE_ID_PATH = DbContent.CourseTable.TABLE_NAME + "/" +
                DbContent.CourseTable.COURSE_END_DATE_COLUMN + "/#/#";

        final String CHILD_WITH_SEARCH_PATH = DbContent.ChildTable.TABLE_NAME + "/" +
                DbContent.ChildTable.CHILD_NAME_COLUMN + "/*";
        final String CHILD_WITH_SEARCH_NULL_PATH = DbContent.ChildTable.TABLE_NAME + "/"
                + DbContent.ChildTable.CHILD_NAME_COLUMN +"/";

        final String INSTRUCTOR_WITH_SEARCH_PATH = DbContent.InstructorTable.TABLE_NAME + "/"
                + DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN + "/*";
        final String INSTRUCTOR_WITH_SEARCH_NULL_PATH = DbContent.InstructorTable.TABLE_NAME + "/"
                + DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN + "/";

        final String COURSE_WITH_SEARCH_NULL_PATH = DbContent.CourseTable.TABLE_NAME + "/"
                + DbContent.CourseTable.COURSE_NAME_COLUMN + "/";
        final String COURSE_WITH_SEARCH_PATH = DbContent.CourseTable.TABLE_NAME + "/"
                + DbContent.CourseTable.COURSE_NAME_COLUMN + "/*";

        final String EMPLOYEE_WITH_SEARCH_NULL_PATH = DbContent.EmployeeTable.TABLE_NAME + "/"
                + DbContent.EmployeeTable.EMPLOYEE_NAME_COLUMN + "/";
        final String EMPLOYEE_WITH_SEARCH_PATH = DbContent.EmployeeTable.TABLE_NAME + "/"
                + DbContent.EmployeeTable.EMPLOYEE_NAME_COLUMN + "/*";

        final String COURSE_WITH_DAY_SEARCH_PATH  = DbContent.CourseTable.TABLE_NAME + "/day/";
        final String SHIFT_PATH = DbContent.ShiftDaysTable.TABLE_NAME;
        final String SHIFT_WITH_COURSE_ID_PATH = DbContent.ShiftDaysTable.TABLE_NAME + "/#";
        final String SHIFT_WITH_COURSE_ID_JOIN_PATH = DbContent.ShiftDaysTable.TABLE_NAME + "/" +
                DbContent.ShiftDaysTable.COURSE_ID_COLUMN + "/#";

        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, COURSE_WITH_DAY_SEARCH_PATH, COURSE_WITH_DAY_SEARCH_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SHIFT_PATH, SHIFT_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SHIFT_WITH_COURSE_ID_PATH, SHIFT_WITH_COURSE_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SHIFT_WITH_COURSE_ID_JOIN_PATH, SHIFT_WITH_COURSE_ID_JOIN_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, CHILD_WITH_SEARCH_PATH, CHILD_WITH_SEARCH_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, CHILD_WITH_SEARCH_NULL_PATH, CHILD_WITH_SEARCH_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, INSTRUCTOR_WITH_SEARCH_PATH, INSTRUCTOR_WITH_SEARCH_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, INSTRUCTOR_WITH_SEARCH_NULL_PATH, INSTRUCTOR_WITH_SEARCH_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, COURSE_WITH_SEARCH_PATH, COURSE_WITH_SEARCH_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, COURSE_WITH_SEARCH_NULL_PATH, COURSE_WITH_SEARCH_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, EMPLOYEE_WITH_SEARCH_PATH, EMPLOYEE_WITH_SEARCH_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, EMPLOYEE_WITH_SEARCH_NULL_PATH, EMPLOYEE_WITH_SEARCH_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, CHILD_PATH, CHILD_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, COURSE_PATH, COURSE_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, INSTRUCTOR_PATH, INSTRUCTOR_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, CHILD_COURSE_PATH, CHILD_COURSE_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, INSTRUCTOR_COURSE_PATH, INSTRUCTOR_COURSE_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, CHILD_WITH_ID_PATH, CHILD_WITH_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, COURSE_WITH_ID_PATH, COURSE_WITH_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, COURSE_WITH_ID_WITH_END_DATE_ID_PATH, COURSE_WITH_ID_WITH_END_DATE_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, COURSE_WITH_DATE_WITH_COMPLETE_ID_AGE_RANGE_PATH, COURSE_WITH_DATE_WITH_COMPLETE_ID_AGE_RANGE_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, EMPLOYEE_PATH, EMPLOYEE_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, EMPLOYEE_WITH_ID_PATH, EMPLOYEE_WITH_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, INSTRUCTOR_WITH_ID_PATH, INSTRUCTOR_WITH_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, CHILD_COURSE_WITH_CHILD_ID_PATH, CHILD_COURSE_WITH_CHILD_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, CHILD_COURSE_WITH_CHILD_ID_COURSE_ID_TABLE_PATH, CHILD_COURSE_WITH_CHILD_ID_COURSE_ID_TABLE);
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
        Log.e("id ", String.valueOf(columnId));
        String selection = column + "=?";
        String[] selectionArgs = {String.valueOf(columnId)};

        return m_dbHelper.getWritableDatabase().update(
                tableName,
                contentValues,
                selection,
                selectionArgs
        );
    }

    private Cursor getCourseWithEndDateWithCompleteAndAgeRangeTable(Uri uri, String[] projection, String sortOrder){
        long age = ContentUris.parseId(uri);
        String newUriString = uri.toString().substring(0, uri.toString().lastIndexOf("/"));
        long date = ContentUris.parseId(Uri.parse(newUriString));

        String selection =
                DbContent.CourseTable.COURSE_AVAILABLE_POSITIONS_COLUMN + "=?" + " AND " +
                DbContent.CourseTable.COURSE_START_AGE_COLUMN + " <=?" + " AND " +
                DbContent.CourseTable.COURSE_END_AGE_COLUMN + " >=?" + " AND " +
                 DbContent.CourseTable.COURSE_END_DATE_COLUMN + " > ?";

        String selectionArgs[] = {
                String.valueOf(Constants.COURSE_INCOMPLETE),
                String.valueOf(age),
                String.valueOf(age),
                String.valueOf(date)
        };

        Log.e("query done", "done");
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

    private Cursor getCourseChildWithChildIdCourseId(Uri uri, String[] projection, String sortOrder) throws URISyntaxException {
        long courseId = ContentUris.parseId(uri);
        String newUriString = uri.toString().substring(0, uri.toString().lastIndexOf("/"));
        Uri childUri = Uri.parse(newUriString);
        long childId = ContentUris.parseId(childUri);

        String selection = DbContent.ChildCourseTable.CHILD_ID_COLUMN + "=?" + " AND " +
                DbContent.ChildCourseTable.COURSE_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(childId), String.valueOf(courseId)};

        Log.e("child id", String.valueOf(childId));
        Log.e("course id", String.valueOf(courseId));

        return m_dbHelper.getReadableDatabase().query(
                DbContent.ChildCourseTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getCourseWithIdWithEndDateId(Uri uri, String[] projection, String sortType){
        long date = ContentUris.parseId(uri);
        String newUriString = uri.toString().substring(0, uri.toString().lastIndexOf("/"));
        Uri newUri = Uri.parse(newUriString);
        long instructorId = ContentUris.parseId(newUri);

        String selection = DbContent.CourseTable.COURSE_END_DATE_COLUMN + ">?" + " AND (" +
                DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN + " =?" + " OR " +
                DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN + "=? )" ;
        String[] selectionArgs = {String.valueOf(date),
                String.valueOf(Constants.NO_INSTRUCTOR),
                String.valueOf(instructorId)};

        return COURSE_INSTRUCTOR_JOIN_COURSE_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortType
        );
    }

    private Cursor getChildWithSearch(Uri uri, String[] projection, String sortType){
        String searchChars = uri.toString().substring(uri.toString().lastIndexOf("/") + 1 , uri.toString().length())
                + "%";
        Log.e("search is", searchChars);

        String selection = DbContent.ChildTable.CHILD_NAME_COLUMN + " LIKE ?";
        String[] selectionArgs = {searchChars};

        return m_dbHelper.getReadableDatabase().query(
                DbContent.ChildTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortType
        );
    }

    private Cursor getCourseWithSearch(Uri uri, String[] projection, String sortType){
        String searchChars = uri.toString().substring(uri.toString().lastIndexOf("/") + 1 , uri.toString().length())
                + "%";
        Log.e("search is", searchChars);

        String selection = DbContent.CourseTable.COURSE_NAME_COLUMN + " LIKE ?";
        String[] selectionArgs = {searchChars};

        return m_dbHelper.getReadableDatabase().query(
                DbContent.CourseTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortType
        );
    }

    private Cursor getInstructorWithSearch(Uri uri, String[] projection, String sortType){
        String searchChars = uri.toString().substring(uri.toString().lastIndexOf("/") + 1 , uri.toString().length())
                + "%";
        Log.e("search is", searchChars);

        String selection = DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN + " LIKE ?";
        String[] selectionArgs = {searchChars};

        return m_dbHelper.getReadableDatabase().query(
                DbContent.InstructorTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortType
        );
    }

    private Cursor getEmployeeWithSearch(Uri uri, String[] projection, String sortType){
        String searchChars = uri.toString().substring(uri.toString().lastIndexOf("/") + 1 , uri.toString().length())
                + "%";
        Log.e("search is", searchChars);

        String selection = DbContent.EmployeeTable.EMPLOYEE_NAME_COLUMN + " LIKE ?";
        String[] selectionArgs = {searchChars};

        return m_dbHelper.getReadableDatabase().query(
                DbContent.EmployeeTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortType
        );
    }

    private Cursor getShiftWithCourseId(Uri uri, String[] projection, String sortOrder){
        long id = ContentUris.parseId(uri);
        String selection = DbContent.ShiftDaysTable.COURSE_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        return m_dbHelper.getReadableDatabase().query(
                DbContent.ShiftDaysTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getShiftWithCourseIdJoin(Uri uri, String[] projection, String sortOrder){
        long id = ContentUris.parseId(uri);
        String selection = DbContent.ShiftDaysTable.COURSE_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        return SHIFT_WITH_COURSE_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

    }

    private Cursor getCourseWithDaySearch(Uri uri, String[] projection, String sortOrder){
        long dayIndex = ContentUris.parseId(uri);
        /// TODO ... index validation.
        String newUri = uri.toString().substring(0,uri.toString().lastIndexOf('/'));
        String searchWord = newUri.substring(newUri.lastIndexOf('/') + 1,newUri.length());
        String encodeWord = searchWord + "%";

        String selection = "SUBSTR(" + DbContent.CourseTable.COURSE_DAYS_COLUMN + "," +
                String.valueOf(dayIndex+1) + "," + String.valueOf(1) + ") LIKE ?" + " AND " +
                DbContent.CourseTable.COURSE_NAME_COLUMN + " LIKE ?" + " AND " +
                DbContent.CourseTable.COURSE_END_DATE_COLUMN + " >= ?";

        String[] selectionArgs = {
                String.valueOf(Constants.SELECTED),
                encodeWord,
                String.valueOf(Utility.getCurrentDateAsMills())
        };

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
}
