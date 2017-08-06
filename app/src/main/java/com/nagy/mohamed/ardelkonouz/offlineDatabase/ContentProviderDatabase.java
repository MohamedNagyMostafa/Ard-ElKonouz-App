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
import java.util.ArrayList;

/**
 * Created by mohamednagy on 6/10/2017.
 */
public class ContentProviderDatabase extends ContentProvider {

    private UriMatcher m_uriMatcher = buildUriMatcher();
    private DbHelper m_dbHelper;

    /** Table Uri **/
    private static final int CHILD_TABLE = 0;
    private static final int COURSE_TABLE = 1;
    private static final int INSTRUCTOR_TABLE = 2;
    private static final int EMPLOYEE_TABLE = 3;
    public static final int SECTION_TABLE = 32;

    /** Search Uri **/
    private static final int CHILD_WITH_SEARCH_TABLE = 17;
    private static final int COURSE_WITH_SEARCH_TABLE = 18;
    private static final int INSTRUCTOR_WITH_SEARCH_TABLE = 19;
    private static final int EMPLOYEE_WITH_SEARCH_TABLE = 20;

    private static final int CHILD_SECTION_TABLE = 4;
    private static final int INSTRUCTOR_SECTION_TABLE = 5;

    private static final int CHILD_WITH_ID_TABLE = 6;
    private static final int COURSE_WITH_ID_TABLE = 7;
    private static final int INSTRUCTOR_WITH_ID_TABLE = 8;
    public static final int SECTION_WITH_ID_TABLE = 33;
    private static final int EMPLOYEE_WITH_ID_TABLE = 14;

    private static final int CHILD_SECTION_WITH_CHILD_ID_TABLE = 9;// Child List
    private static final int CHILD_SECTION_WITH_SECTION_ID_TABLE = 10;
    private static final int INSTRUCTOR_SECTION_WITH_INSTRUCTOR_ID_TABLE = 12;
    private static final int INSTRUCTOR_SECTION_WITH_SECTION_ID_TABLE = 13;

    private static final int CHILD_SECTION_WITH_CHILD_ID_SECTION_ID_TABLE = 11;

    private static final int SECTION_WITH_DATE_WITH_COMPLETE_ID_AGE_RANGE_TABLE = 15;
    private static final int SECTION_WITH_ID_WITH_END_DATE_TABLE = 16;



    private static final int SHIFT_WITH_SECTION_ID_TABLE = 21;
    private static final int SHIFT_TABLE = 22;
    private static final int SHIFT_WITH_SECTION_ID_JOIN_TABLE = 23;
    private static final int SECTION_WITH_DAY_SEARCH_TABLE = 24;
    private static final int SECTION_WITH_DAY_TABLE = 25;
    private static final int COURSES_CHOICES_TABLE = 26;
    private static final int COURSES_SELECTION_TABLE = 27;
    private static final int SHIFT_WITH_START_END_DATE_TABLE = 28;
    private static final int SHIFT_WITH_START_DATE_TABLE = 29;
    private static final int SHIFT_WITH_END_DATE_TABLE = 30;
    private static final int SHIFT_WITH_ID_TABLE = 31;
    public static final int COURSE_SECTION_JOIN_TABLE = 34;
    public static final int COURSE_SECTION_JOIN_WITH_COURSE_ID_TABLE = 35;
    public static final int COURSE_SECTION_JOIN_WITH_SECTION_ID_TABLE = 36;
    public static final int SECTION_WITH_COURSE_ID_TABLE = 37; // Course List
    public static final int SECTION_WITH_COURSE_NAME_TABLE = 38;


    private static final String INNER_JOIN = "INNER JOIN";
    private static final String ON = "ON";

    private static final SQLiteQueryBuilder SECTION_INSTRUCTOR_JOIN_WITH_COURSE_QUERY =
            new SQLiteQueryBuilder();
    static{
        SECTION_INSTRUCTOR_JOIN_WITH_COURSE_QUERY.setTables(
            DbContent.SectionTable.TABLE_NAME + DbContent.SPACE + INNER_JOIN +
                    DbContent.SPACE + DbContent.SectionInstructorTable.TABLE_NAME +
                    DbContent.SPACE + ON + DbContent.SPACE + DbContent.SectionTable.TABLE_NAME +
                    "." + DbContent.SectionTable._ID + "=" + DbContent.SectionInstructorTable.TABLE_NAME +
                    "." + DbContent.SectionInstructorTable.SECTION_ID_COLUMN + DbContent.SPACE +
                    INNER_JOIN + DbContent.SPACE + DbContent.CourseTable.TABLE_NAME + DbContent.SPACE +
                    ON + DbContent.SPACE + DbContent.SectionTable.TABLE_NAME + "." +
                    DbContent.SectionTable.SECTION_COURSE_ID_COLUMN + "=" + DbContent.CourseTable.TABLE_NAME +
                    "." + DbContent.CourseTable._ID
        );
    }

    private static final SQLiteQueryBuilder SECTION_INSTRUCTOR_JOIN_SECTION_QUERY =
            new SQLiteQueryBuilder();

    static {
        SECTION_INSTRUCTOR_JOIN_SECTION_QUERY.setTables(
                DbContent.SectionInstructorTable.TABLE_NAME + DbContent.SPACE + INNER_JOIN +
                        DbContent.SPACE + DbContent.SectionTable.TABLE_NAME +
                        DbContent.SPACE + ON + DbContent.SPACE +  DbContent.SectionTable.TABLE_NAME +
                        "." + DbContent.SectionTable._ID +
                        "=" + DbContent.SectionInstructorTable.TABLE_NAME + "." +
                        DbContent.SectionInstructorTable.SECTION_ID_COLUMN
        );
    }


    private static final SQLiteQueryBuilder COURSE_SECTION_JOIN_QUERY =
            new SQLiteQueryBuilder();

    static {
        COURSE_SECTION_JOIN_QUERY.setTables(
                DbContent.SectionTable.TABLE_NAME + DbContent.SPACE + INNER_JOIN +
                        DbContent.SPACE + DbContent.CourseTable.TABLE_NAME +
                        DbContent.SPACE + ON + DbContent.SPACE +  DbContent.SectionTable.TABLE_NAME +
                        "." + DbContent.SectionTable.SECTION_COURSE_ID_COLUMN +
                        "=" + DbContent.CourseTable.TABLE_NAME + "." +
                        DbContent.CourseTable._ID
        );
    }

    private static final SQLiteQueryBuilder SECTION_INSTRUCTOR_QUERY =
            new SQLiteQueryBuilder();

    static {
        SECTION_INSTRUCTOR_QUERY.setTables(
                DbContent.SectionInstructorTable.TABLE_NAME + DbContent.SPACE + INNER_JOIN +
                        DbContent.SPACE + DbContent.InstructorTable.TABLE_NAME +
                        DbContent.SPACE + ON + DbContent.SPACE +  DbContent.InstructorTable.TABLE_NAME +
                        "." + DbContent.InstructorTable._ID +
                        "=" + DbContent.SectionInstructorTable.TABLE_NAME + "." +
                        DbContent.SectionInstructorTable.INSTRUCTOR_ID_COLUMN + DbContent.SPACE + INNER_JOIN +
                        DbContent.SPACE + DbContent.SectionTable.TABLE_NAME +
                        DbContent.SPACE + ON + DbContent.SPACE +  DbContent.SectionTable.TABLE_NAME +
                        "." + DbContent.SectionTable._ID +
                        "=" + DbContent.SectionInstructorTable.TABLE_NAME + "." +
                        DbContent.SectionInstructorTable.SECTION_ID_COLUMN
        );
    }

    private static final SQLiteQueryBuilder SHIFT_WITH_SECTION_QUERY =
            new SQLiteQueryBuilder();

    static {
        SHIFT_WITH_SECTION_QUERY.setTables(
                DbContent.ShiftDaysTable.TABLE_NAME + DbContent.SPACE + INNER_JOIN +
                        DbContent.SPACE + DbContent.SectionTable.TABLE_NAME +
                        DbContent.SPACE + ON + DbContent.SPACE +  DbContent.SectionTable.TABLE_NAME +
                        "." + DbContent.SectionTable._ID +
                        "=" + DbContent.ShiftDaysTable.TABLE_NAME + "." +
                        DbContent.ShiftDaysTable.SECTION_ID_COLUMN
        );
    }

    private static final SQLiteQueryBuilder SECTION_CHILD_QUERY =
            new SQLiteQueryBuilder();

    static {
        SECTION_CHILD_QUERY.setTables(
                DbContent.ChildSectionTable.TABLE_NAME + DbContent.SPACE + INNER_JOIN +
                        DbContent.SPACE + DbContent.ChildTable.TABLE_NAME +
                        DbContent.SPACE + ON + DbContent.SPACE + DbContent.ChildTable.TABLE_NAME +
                        "." + DbContent.ChildTable._ID +
                        "=" + DbContent.ChildSectionTable.TABLE_NAME + "." +
                        DbContent.ChildSectionTable.CHILD_ID_COLUMN + DbContent.SPACE + INNER_JOIN +
                        DbContent.SPACE + DbContent.SectionTable.TABLE_NAME +
                        DbContent.SPACE + ON + DbContent.SPACE +  DbContent.SectionTable.TABLE_NAME +
                        "." + DbContent.SectionTable._ID +
                        "=" + DbContent.ChildSectionTable.TABLE_NAME + "." +
                        DbContent.ChildSectionTable.SECTION_ID_COLUMN
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

            case INSTRUCTOR_SECTION_TABLE:
                return SECTION_INSTRUCTOR_QUERY.query(
                        m_dbHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

            case CHILD_SECTION_TABLE:
                return SECTION_CHILD_QUERY.query(
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

            case SECTION_TABLE:
                return m_dbHelper.getReadableDatabase().query(
                        DbContent.SectionTable.TABLE_NAME,
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

            case SHIFT_WITH_ID_TABLE:
                return getShiftWithId(uri, projection, sortOrder);

            case SECTION_WITH_ID_TABLE:
                return getSectionWithId(uri, projection, sortOrder);

            case INSTRUCTOR_SECTION_WITH_SECTION_ID_TABLE:
                return getSectionInstructorWithSectionId(uri, projection, sortOrder);

            case INSTRUCTOR_SECTION_WITH_INSTRUCTOR_ID_TABLE:
                return getSectionInstructorWithInstructorId(uri, projection, sortOrder);

            case CHILD_SECTION_WITH_CHILD_ID_TABLE:
                return getSectionChildWithChildId(uri, projection, sortOrder);

            case CHILD_SECTION_WITH_SECTION_ID_TABLE:
                return getSectionChildWithSectionId(uri, projection, sortOrder);

            case SECTION_WITH_DATE_WITH_COMPLETE_ID_AGE_RANGE_TABLE:
                return getCourseWithEndDateWithCompleteAndAgeRangeTable(uri, projection, sortOrder);

            case CHILD_SECTION_WITH_CHILD_ID_SECTION_ID_TABLE:
                try {
                    return getSectionChildWithSectionIdCourseId(uri, projection, sortOrder);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            case SECTION_WITH_ID_WITH_END_DATE_TABLE:
                return getSectionWithIdWithEndDateId(uri, projection, sortOrder);

            case CHILD_WITH_SEARCH_TABLE:
                return getChildWithSearch(uri, projection, sortOrder);

            case COURSE_WITH_SEARCH_TABLE:
                return getCourseWithSearch(uri, projection, sortOrder);

            case INSTRUCTOR_WITH_SEARCH_TABLE:
                return getInstructorWithSearch(uri, projection, sortOrder);

            case EMPLOYEE_WITH_SEARCH_TABLE:
                return getEmployeeWithSearch(uri, projection, sortOrder);

            case SHIFT_WITH_SECTION_ID_TABLE:
                return getShiftWithSectionId(uri, projection);

            case SHIFT_WITH_SECTION_ID_JOIN_TABLE:
                return getShiftWithSectionIdJoin(uri, projection);

            case SECTION_WITH_DAY_SEARCH_TABLE:
                return getSectionWithDaySearch(uri, projection, sortOrder);

            case SECTION_WITH_DAY_TABLE:
                return getSectionWithDay(uri, projection, sortOrder);

            case COURSES_CHOICES_TABLE:
                return getCoursesChoices(uri, projection, sortOrder);

            case COURSES_SELECTION_TABLE:
                return getCoursesSelection(uri, projection, sortOrder);

            case SHIFT_WITH_START_END_DATE_TABLE:
                return getShiftWithStartEndDate(uri, projection, sortOrder);

            case SHIFT_WITH_START_DATE_TABLE:
                return getShiftWithStartDate(uri, projection, sortOrder);

            case SHIFT_WITH_END_DATE_TABLE:
                return getShiftWithEndDate(uri, projection, sortOrder);

            case SECTION_WITH_COURSE_NAME_TABLE:
                return getSectionWithCourseName(uri, projection, sortOrder);

            case COURSE_SECTION_JOIN_TABLE:
                return COURSE_SECTION_JOIN_QUERY.query(
                        m_dbHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

            case COURSE_SECTION_JOIN_WITH_COURSE_ID_TABLE:
                return  getCourseSectionJoinWithCourseId(uri, projection);

            case COURSE_SECTION_JOIN_WITH_SECTION_ID_TABLE:
                return  getCourseSectionJoinWithSectionId(uri, projection, sortOrder);

            case SECTION_WITH_COURSE_ID_TABLE: // Course List
                return getSectionWithCourseId(uri, projection);

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

            case CHILD_SECTION_TABLE:
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

            case CHILD_SECTION_TABLE:
                insertResult = m_dbHelper.getWritableDatabase().insert(
                        DbContent.ChildSectionTable.TABLE_NAME,
                        null,
                        contentValues
                );
                break;

            case INSTRUCTOR_SECTION_TABLE:
                insertResult = m_dbHelper.getWritableDatabase().insert(
                        DbContent.SectionInstructorTable.TABLE_NAME,
                        null,
                        contentValues
                );
                break;

            case SECTION_TABLE:
                insertResult = m_dbHelper.getWritableDatabase().insert(
                        DbContent.SectionTable.TABLE_NAME,
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
        Log.e("match", String.valueOf(match));
        Log.e("uri",uri.toString());
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

            case INSTRUCTOR_SECTION_TABLE:
                return m_dbHelper.getWritableDatabase().delete(
                        DbContent.SectionInstructorTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );

            case CHILD_SECTION_TABLE:
                return m_dbHelper.getWritableDatabase().delete(
                        DbContent.ChildSectionTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );

            case SECTION_TABLE:
                return m_dbHelper.getWritableDatabase().delete(
                        DbContent.SectionTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );

            case SHIFT_WITH_ID_TABLE:
                return deleteRowWithId(DbContent.ShiftDaysTable.TABLE_NAME, uri, DbContent.ShiftDaysTable._ID);

            case SHIFT_WITH_SECTION_ID_TABLE:
                return deleteRowWithId(DbContent.ShiftDaysTable.TABLE_NAME,
                        uri,
                        DbContent.ShiftDaysTable.SECTION_ID_COLUMN);

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

            case SECTION_WITH_ID_TABLE:
                return deleteRowWithId(DbContent.SectionTable.TABLE_NAME,
                        uri,
                        DbContent.SectionTable._ID);

            case CHILD_WITH_ID_TABLE:
                return deleteRowWithId(DbContent.ChildTable.TABLE_NAME,
                        uri,
                        DbContent.ChildTable._ID);

            case CHILD_SECTION_WITH_CHILD_ID_TABLE:
                return deleteRowWithId(DbContent.ChildSectionTable.TABLE_NAME,
                        uri,
                        DbContent.ChildSectionTable.CHILD_ID_COLUMN);

            case CHILD_SECTION_WITH_SECTION_ID_TABLE:
                return deleteRowWithId(DbContent.ChildSectionTable.TABLE_NAME,
                        uri,
                        DbContent.ChildSectionTable.SECTION_ID_COLUMN);

            case INSTRUCTOR_SECTION_WITH_SECTION_ID_TABLE:
                return deleteRowWithId(DbContent.SectionInstructorTable.TABLE_NAME,
                        uri,
                        DbContent.SectionInstructorTable.SECTION_ID_COLUMN);

            case INSTRUCTOR_SECTION_WITH_INSTRUCTOR_ID_TABLE:
                return deleteRowWithId(DbContent.SectionInstructorTable.TABLE_NAME,
                        uri,
                        DbContent.SectionInstructorTable.INSTRUCTOR_ID_COLUMN);

            case SHIFT_WITH_START_END_DATE_TABLE:
                return deleteShiftWithStartEndDate(uri);

            case SECTION_WITH_COURSE_ID_TABLE: // Course List
                return deleteRowWithId(DbContent.SectionTable.TABLE_NAME,
                        uri,
                        DbContent.SectionTable.SECTION_COURSE_ID_COLUMN);
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

            case CHILD_SECTION_TABLE:
                return m_dbHelper.getWritableDatabase().update(
                        DbContent.ChildSectionTable.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs
                );

            case INSTRUCTOR_SECTION_TABLE:
                return m_dbHelper.getWritableDatabase().update(
                        DbContent.SectionInstructorTable.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs
                );

            case SECTION_TABLE:
                return m_dbHelper.getWritableDatabase().update(
                        DbContent.SectionTable.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs
                );

            case SECTION_WITH_ID_TABLE:
                return updateRowWithId(
                        uri,
                        contentValues,
                        DbContent.SectionTable.TABLE_NAME,
                        DbContent.SectionTable._ID
                );

            case SECTION_WITH_COURSE_ID_TABLE:
                return updateRowWithId(
                        uri,
                        contentValues,
                        DbContent.SectionTable.TABLE_NAME,
                        DbContent.SectionTable.SECTION_COURSE_ID_COLUMN
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


            case INSTRUCTOR_SECTION_WITH_SECTION_ID_TABLE:
                return updateRowWithId(
                        uri,
                        contentValues,
                        DbContent.SectionInstructorTable.TABLE_NAME,
                        DbContent.SectionInstructorTable.SECTION_ID_COLUMN
                );

            case INSTRUCTOR_SECTION_WITH_INSTRUCTOR_ID_TABLE:
                return updateRowWithId(
                        uri,
                        contentValues,
                        DbContent.SectionInstructorTable.TABLE_NAME,
                        DbContent.SectionInstructorTable.INSTRUCTOR_ID_COLUMN
                );

            case CHILD_SECTION_WITH_CHILD_ID_TABLE:
                return updateRowWithId(
                        uri,
                        contentValues,
                        DbContent.ChildSectionTable.TABLE_NAME,
                        DbContent.ChildSectionTable.CHILD_ID_COLUMN
                );

            case CHILD_SECTION_WITH_SECTION_ID_TABLE:
                return updateRowWithId(
                        uri,
                        contentValues,
                        DbContent.ChildSectionTable.TABLE_NAME,
                        DbContent.ChildSectionTable.SECTION_ID_COLUMN
                );

            case SHIFT_WITH_SECTION_ID_TABLE:
                return updateRowWithId(
                        uri,
                        contentValues,
                        DbContent.ShiftDaysTable.TABLE_NAME,
                        DbContent.ShiftDaysTable.SECTION_ID_COLUMN
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

            case CHILD_SECTION_TABLE:
                for(ContentValues contentValues : values) {
                    counter++;
                    m_dbHelper.getWritableDatabase().insert(
                            DbContent.ChildSectionTable.TABLE_NAME,
                            null,
                            contentValues
                    );
                }
                break;

            case INSTRUCTOR_SECTION_TABLE:
                for(ContentValues contentValues : values) {
                    counter++;
                    m_dbHelper.getWritableDatabase().insert(
                            DbContent.SectionInstructorTable.TABLE_NAME,
                            null,
                            contentValues
                    );
                }
                break;

            case SHIFT_TABLE:
                for(ContentValues contentValues : values) {
                    counter++;
                    m_dbHelper.getWritableDatabase().insert(
                            DbContent.ShiftDaysTable.TABLE_NAME,
                            null,
                            contentValues
                    );
                }
                break;

            case SECTION_TABLE:
                for(ContentValues contentValues : values) {
                    counter++;
                    m_dbHelper.getWritableDatabase().insert(
                            DbContent.SectionTable.TABLE_NAME,
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
        final String CHILD_SECTION_PATH = DbContent.ChildSectionTable.TABLE_NAME;
        final String INSTRUCTOR_SECTION_PATH = DbContent.SectionInstructorTable.TABLE_NAME;

        final String CHILD_WITH_ID_PATH = CHILD_PATH + "/#";
        final String EMPLOYEE_WITH_ID_PATH = EMPLOYEE_PATH + "/#";
        final String COURSE_WITH_ID_PATH = COURSE_PATH + "/#";
        // Child course connector ..
        final String SECTION_WITH_DATE_WITH_COMPLETE_ID_AGE_RANGE_PATH = DbContent.SectionTable.TABLE_NAME + "/" +
                DbContent.SectionTable.SECTION_END_DATE_COLUMN + "/" +
                DbContent.SectionTable.SECTION_AVAILABLE_POSITIONS_COLUMN+ "/#" + "/#";
        final String INSTRUCTOR_WITH_ID_PATH = DbContent.InstructorTable.TABLE_NAME + "/#";
        /// Child List
        final String CHILD_SECTION_WITH_CHILD_ID_PATH = CHILD_SECTION_PATH + "/" +
                DbContent.ChildTable.TABLE_NAME + "/#";

        final String CHILD_SECTION_WITH_SECTION_ID_PATH = CHILD_SECTION_PATH + "/" +
                DbContent.SectionTable.TABLE_NAME + "/#";
        // Child course connector ..
        final String CHILD_SECTION_WITH_CHILD_ID_SECTION_ID_TABLE_PATH = CHILD_SECTION_PATH + "/" +
                DbContent.ChildTable.TABLE_NAME + "/" + DbContent.SectionTable.TABLE_NAME + "/#" + "/#";

        final String INSTRUCTOR_SECTION_WITH_INSTRUCTOR_ID_PATH = DbContent.SectionInstructorTable.TABLE_NAME + "/" +
                DbContent.InstructorTable.TABLE_NAME +"/#";
        final String INSTRUCTOR_SECTION_WITH_SECTION_ID_PATH = DbContent.SectionInstructorTable.TABLE_NAME + "/" +
                DbContent.SectionTable.TABLE_NAME +"/#";
        final String SECTION_WITH_ID_WITH_END_DATE_ID_PATH = DbContent.SectionTable.TABLE_NAME + "/" +
                DbContent.SectionTable.SECTION_END_DATE_COLUMN + "/#/#";

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
        // Shift List
        final String SECTION_WITH_DAY_SEARCH_PATH  = DbContent.SectionTable.TABLE_NAME + "/day/#/*";
        final String SECTION_WITH_DAY_PATH  = DbContent.SectionTable.TABLE_NAME + "/day/#";

        final String SHIFT_PATH = DbContent.ShiftDaysTable.TABLE_NAME;
        final String SHIFT_WITH_ID_PATH = DbContent.ShiftDaysTable.TABLE_NAME + "/" +
                DbContent.ShiftDaysTable._ID + "/#";
        final String SHIFT_WITH_SECTION_ID_PATH = DbContent.ShiftDaysTable.TABLE_NAME + "/#";
        final String SHIFT_WITH_SECTION_ID_JOIN_PATH = DbContent.ShiftDaysTable.TABLE_NAME + "/" +
                DbContent.ShiftDaysTable.SECTION_ID_COLUMN + "/#";

        final String COURSES_CHOICES_PATH = DbContent.SectionTable.TABLE_NAME + "/"
                + DbContent.CourseTable.COURSE_NAME_COLUMN + "/*/*";
        final String COURSES_SELECTION_PATH = DbContent.SectionTable.TABLE_NAME + "/"
                + DbContent.CourseTable._ID + "/*";

        final String SECTION_WITH_COURSE_NAME_PATH = DbContent.SectionTable.TABLE_NAME + "/"
                + DbContent.CourseTable.COURSE_NAME_COLUMN + "/*";

        final String SHIFT_WITH_START_END_DATE_PATH = DbContent.ShiftDaysTable.TABLE_NAME + "/" +
                DbContent.ShiftDaysTable.START_DATE_COLUMN + "/" + DbContent.ShiftDaysTable.END_DATE_COLUMN +
                "/#/#/#" ;
        final String SHIFT_WITH_START_DATE_PATH = DbContent.ShiftDaysTable.TABLE_NAME + "/" +
                DbContent.ShiftDaysTable.START_DATE_COLUMN + "/#/#/#" ;
        final String SHIFT_WITH_END_DATE_PATH = DbContent.ShiftDaysTable.TABLE_NAME + "/" +
                DbContent.ShiftDaysTable.END_DATE_COLUMN + "/#/#/#" ;

        final String SECTION_PATH = DbContent.SectionTable.TABLE_NAME;
        final String SECTION_WITH_ID_PATH = SECTION_PATH + "/#";
        // Course List
        final String SECTION_WITH_COURSE_ID_PATH = SECTION_PATH + "/" +
                DbContent.SectionTable.SECTION_COURSE_ID_COLUMN + "/#";

        final String COURSE_SECTION_JOIN_PATH = SECTION_PATH + "/" + COURSE_PATH;
        final String COURSE_SECTION_JOIN_WITH_COURSE_ID_PATH = SECTION_PATH + "/" + COURSE_PATH + "/" +
                DbContent.SectionTable.SECTION_COURSE_ID_COLUMN + "/#";
        final String COURSE_SECTION_JOIN_WITH_SECTION_ID_PATH = SECTION_PATH + "/" + COURSE_PATH + "/" +
                DbContent.SectionTable._ID + "/#";

        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, COURSE_SECTION_JOIN_PATH, COURSE_SECTION_JOIN_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SECTION_WITH_COURSE_NAME_PATH, SECTION_WITH_COURSE_NAME_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, COURSE_SECTION_JOIN_WITH_COURSE_ID_PATH, COURSE_SECTION_JOIN_WITH_COURSE_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, COURSE_SECTION_JOIN_WITH_SECTION_ID_PATH, COURSE_SECTION_JOIN_WITH_SECTION_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SECTION_PATH, SECTION_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SECTION_WITH_COURSE_ID_PATH, SECTION_WITH_COURSE_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SECTION_WITH_ID_PATH, SECTION_WITH_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SHIFT_WITH_ID_PATH, SHIFT_WITH_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SHIFT_WITH_START_END_DATE_PATH, SHIFT_WITH_START_END_DATE_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SHIFT_WITH_START_DATE_PATH, SHIFT_WITH_START_DATE_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SHIFT_WITH_END_DATE_PATH, SHIFT_WITH_END_DATE_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, COURSES_SELECTION_PATH, COURSES_SELECTION_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, COURSES_CHOICES_PATH, COURSES_CHOICES_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SECTION_WITH_DAY_SEARCH_PATH, SECTION_WITH_DAY_SEARCH_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SECTION_WITH_DAY_PATH, SECTION_WITH_DAY_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SHIFT_PATH, SHIFT_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SHIFT_WITH_SECTION_ID_PATH, SHIFT_WITH_SECTION_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SHIFT_WITH_SECTION_ID_JOIN_PATH, SHIFT_WITH_SECTION_ID_JOIN_TABLE);
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
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, CHILD_SECTION_PATH, CHILD_SECTION_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, INSTRUCTOR_SECTION_PATH, INSTRUCTOR_SECTION_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, CHILD_WITH_ID_PATH, CHILD_WITH_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, COURSE_WITH_ID_PATH, COURSE_WITH_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SECTION_WITH_ID_WITH_END_DATE_ID_PATH, SECTION_WITH_ID_WITH_END_DATE_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, SECTION_WITH_DATE_WITH_COMPLETE_ID_AGE_RANGE_PATH, SECTION_WITH_DATE_WITH_COMPLETE_ID_AGE_RANGE_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, EMPLOYEE_PATH, EMPLOYEE_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, EMPLOYEE_WITH_ID_PATH, EMPLOYEE_WITH_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, INSTRUCTOR_WITH_ID_PATH, INSTRUCTOR_WITH_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, CHILD_SECTION_WITH_CHILD_ID_PATH, CHILD_SECTION_WITH_CHILD_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, CHILD_SECTION_WITH_CHILD_ID_SECTION_ID_TABLE_PATH, CHILD_SECTION_WITH_CHILD_ID_SECTION_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, CHILD_SECTION_WITH_SECTION_ID_PATH, CHILD_SECTION_WITH_SECTION_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, INSTRUCTOR_SECTION_WITH_INSTRUCTOR_ID_PATH, INSTRUCTOR_SECTION_WITH_INSTRUCTOR_ID_TABLE);
        uriMatcher.addURI(DbContent.CONTENT_AUTHORITY, INSTRUCTOR_SECTION_WITH_SECTION_ID_PATH, INSTRUCTOR_SECTION_WITH_SECTION_ID_TABLE);

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

    private Cursor getSectionInstructorWithInstructorId(Uri uri, String[] projection, String sortOrder){
        long columnId = ContentUris.parseId(uri);

        String selection = DbContent.SectionInstructorTable.INSTRUCTOR_ID_COLUMN +"=?";
        String[] selectionArgs = {String.valueOf(columnId)};

        return SECTION_INSTRUCTOR_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getSectionInstructorWithSectionId(Uri uri, String[] projection, String sortOrder){
        long columnId = ContentUris.parseId(uri);

        String selection = DbContent.SectionInstructorTable.SECTION_ID_COLUMN +"=?";
        String[] selectionArgs = {String.valueOf(columnId)};

        return SECTION_INSTRUCTOR_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getSectionChildWithSectionId(Uri uri, String[] projection, String sortOrder){
        long columnId = ContentUris.parseId(uri);

        String selection = DbContent.ChildSectionTable.SECTION_ID_COLUMN +"=?";
        String[] selectionArgs = {String.valueOf(columnId)};

        return SECTION_CHILD_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getSectionChildWithChildId(Uri uri, String[] projection, String sortOrder){
        long columnId = ContentUris.parseId(uri);

        String selection = DbContent.ChildSectionTable.CHILD_ID_COLUMN +"=?";
        String[] selectionArgs = {String.valueOf(columnId)};

        return SECTION_CHILD_QUERY.query(
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
                DbContent.SectionTable.SECTION_AVAILABLE_POSITIONS_COLUMN + "=?" + " AND " +
                DbContent.CourseTable.COURSE_START_AGE_COLUMN + " <=?" + " AND " +
                DbContent.CourseTable.COURSE_END_AGE_COLUMN + " >=?" + " AND " +
                 DbContent.SectionTable.SECTION_END_DATE_COLUMN + " > ?";

        String selectionArgs[] = {
                String.valueOf(Constants.COURSE_INCOMPLETE),
                String.valueOf(age),
                String.valueOf(age),
                String.valueOf(date)
        };

        Log.e("query done", "done");
        return COURSE_SECTION_JOIN_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getSectionChildWithSectionIdCourseId(Uri uri, String[] projection, String sortOrder) throws URISyntaxException {
        long sectionId = ContentUris.parseId(uri);
        String newUriString = uri.toString().substring(0, uri.toString().lastIndexOf("/"));
        Uri childUri = Uri.parse(newUriString);
        long childId = ContentUris.parseId(childUri);

        String selection = DbContent.ChildSectionTable.CHILD_ID_COLUMN + "=?" + " AND " +
                DbContent.ChildSectionTable.SECTION_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(childId), String.valueOf(sectionId)};

        Log.e("child id", String.valueOf(childId));
        Log.e("course id", String.valueOf(sectionId));

        return m_dbHelper.getReadableDatabase().query(
                DbContent.ChildSectionTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getSectionWithIdWithEndDateId(Uri uri, String[] projection, String sortType){
        long date = ContentUris.parseId(uri);
        String newUriString = uri.toString().substring(0, uri.toString().lastIndexOf("/"));
        Uri newUri = Uri.parse(newUriString);
        long instructorId = ContentUris.parseId(newUri);

        String selection = DbContent.SectionTable.SECTION_END_DATE_COLUMN + ">?" + " AND (" +
                DbContent.SectionInstructorTable.INSTRUCTOR_ID_COLUMN + " =?" + " OR " +
                DbContent.SectionInstructorTable.INSTRUCTOR_ID_COLUMN + "=? )" ;
        String[] selectionArgs = {String.valueOf(date),
                String.valueOf(Constants.NO_INSTRUCTOR),
                String.valueOf(instructorId)};

        return SECTION_INSTRUCTOR_JOIN_WITH_COURSE_QUERY.query(
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

        return  m_dbHelper.getReadableDatabase().query(
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

    private Cursor getShiftWithSectionId(Uri uri, String[] projection){
        long id = ContentUris.parseId(uri);
        String selection = DbContent.ShiftDaysTable.SECTION_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        String sortOrder = DbContent.ShiftDaysTable.START_DATE_COLUMN + " ASC";

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

    private Cursor getShiftWithSectionIdJoin(Uri uri, String[] projection){
        long id = ContentUris.parseId(uri);
        String selection = DbContent.ShiftDaysTable.SECTION_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(id)};
        String sortOrder = DbContent.ShiftDaysTable.START_DATE_COLUMN + " ASC";

        return SHIFT_WITH_SECTION_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getSectionWithDay(Uri uri, String[] projection, String sortOrder){
        long dayIndex = ContentUris.parseId(uri);
        String selection = "(SUBSTR(" + DbContent.SectionTable.SECTION_DAYS_COLUMN + "," +
                String.valueOf(dayIndex+1) + "," + String.valueOf(1) + ") LIKE ? )" + " AND " +
                "(" + DbContent.SectionTable.SECTION_END_DATE_COLUMN + " >= ?" + ")";

        Log.e("index",String.valueOf(dayIndex));
        String[] selectionArgs = {
                String.valueOf(Constants.SELECTED),
                String.valueOf(Utility.getCurrentDateAsMills())
        };

        return COURSE_SECTION_JOIN_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getSectionWithDaySearch(Uri uri, String[] projection, String sortOrder){
        String searchWord = uri.toString().substring(uri.toString().lastIndexOf('/') + 1, uri.toString().length());
        String encodeWord = searchWord + "%";
        String newUri = uri.toString().substring(0, uri.toString().lastIndexOf('/'));
        long dayIndex = ContentUris.parseId(Uri.parse(newUri));

        /// TODO ... Check start Date...

        String selection = "(SUBSTR(" + DbContent.SectionTable.SECTION_DAYS_COLUMN + "," +
                String.valueOf(dayIndex+1) + "," + String.valueOf(1) + ") LIKE ? )" + " AND " +
                "(" + DbContent.SectionTable.SECTION_END_DATE_COLUMN + " >= ?" + ")" + " AND " +
                "(" + DbContent.CourseTable.COURSE_NAME_COLUMN + " LIKE ?" + ")";

        Log.e("index",String.valueOf(dayIndex));
        String[] selectionArgs = {
                String.valueOf(Constants.SELECTED),
                String.valueOf(Utility.getCurrentDateAsMills()),
                encodeWord
        };

        return COURSE_SECTION_JOIN_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getCoursesChoices(Uri uri, String[] projection, String sortOrder){
        String searchWord = uri.toString().substring(uri.toString().lastIndexOf('/') + 1, uri.toString().length());
        String encodeWord = searchWord + "%";
        String newUri = uri.toString().substring(0, uri.toString().lastIndexOf('/'));
        String idUri = newUri.substring(newUri.lastIndexOf('/'), newUri.length());

        ArrayList<String> selectionArgs = new ArrayList<>();
        selectionArgs.add(encodeWord);
        StringBuilder selection = new StringBuilder("");
        selection.append(DbContent.CourseTable.COURSE_NAME_COLUMN).append(" LIKE ?");

        do{
            if(idUri.contains("k")) {
                String courseId = idUri.substring(idUri.lastIndexOf('k') + 1, idUri.length());
                idUri = idUri.substring(0, idUri.lastIndexOf('k'));
                selectionArgs.add(courseId);
                selection.append(" AND ").append(DbContent.SectionTable.TABLE_NAME).append(".")
                        .append(DbContent.SectionTable._ID).append(" != ?");
            }else{
                String courseId = idUri.substring(idUri.lastIndexOf('/') + 1, idUri.length());
                idUri = idUri.substring(0, idUri.lastIndexOf('/'));
                selectionArgs.add(courseId);
                selection.append(" AND ").append(DbContent.SectionTable.TABLE_NAME).append(".")
                        .append(DbContent.SectionTable._ID).append(" != ?");
            }
        }while (idUri.length() > 1);

        String[] selectionArgsArray = new String[selectionArgs.size()];
        selectionArgs.toArray(selectionArgsArray);

        Log.e("selection",selection.toString());
        for(String selectionAr : selectionArgs){
            Log.e("selectionArgs", selectionAr);
        }

        return  COURSE_SECTION_JOIN_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection.toString(),
                selectionArgsArray,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getCoursesSelection (Uri uri, String[] projection, String sortOrder){
        String idUri = uri.toString().substring(uri.toString().lastIndexOf('/'), uri.toString().length());

        ArrayList<String> selectionArgs = new ArrayList<>();
        StringBuilder selection = new StringBuilder("");

        do{
            if(idUri.contains("k")) {
                String courseId = idUri.substring(idUri.lastIndexOf('k') + 1, idUri.length());
                idUri = idUri.substring(0, idUri.lastIndexOf('k'));
                selectionArgs.add(courseId);

                if(selection.length() > 1) {
                    selection.append(" OR ").append(DbContent.SectionTable.TABLE_NAME).append(".")
                            .append(DbContent.SectionTable._ID).append(" =?");
                }else{
                    selection.append(DbContent.SectionTable.TABLE_NAME).append(".")
                            .append(DbContent.SectionTable._ID).append(" =?");
                }

            }else{
                String courseId = idUri.substring(idUri.lastIndexOf('/') + 1, idUri.length());
                idUri = idUri.substring(0, idUri.lastIndexOf('/'));
                selectionArgs.add(courseId);

                if(selection.length() > 1) {
                    selection.append(" OR ").append(DbContent.SectionTable.TABLE_NAME).append(".")
                            .append(DbContent.SectionTable._ID).append(" =?");
                }else{
                    selection.append(DbContent.SectionTable.TABLE_NAME).append(".")
                            .append(DbContent.SectionTable._ID).append(" =?");
                }
            }
        }while (idUri.length() > 1);

        String[] selectionArgsArray = new String[selectionArgs.size()];
        selectionArgs.toArray(selectionArgsArray);

        Log.e("selection", selection.toString());
        for(String id : selectionArgsArray){
            Log.e("selectionArg", id);
        }
        return  COURSE_SECTION_JOIN_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection.toString(),
                selectionArgsArray,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getShiftWithStartEndDate(Uri uri, String[] projection, String sortOrder){
        Long courseId = ContentUris.parseId(uri);
        String endDateURi = uri.toString().substring(0, uri.toString().lastIndexOf('/'));
        Long endDate = ContentUris.parseId( Uri.parse(endDateURi));
        String startDateUri = endDateURi.substring(0, endDateURi.lastIndexOf('/'));
        Long startDate = ContentUris.parseId(Uri.parse(startDateUri));

        String selection = DbContent.ShiftDaysTable.START_DATE_COLUMN + " <=?" + " AND " +
                DbContent.ShiftDaysTable.END_DATE_COLUMN + " >=?" + " AND " +
                DbContent.ShiftDaysTable.SECTION_ID_COLUMN + " =?";

        String[] selectionArgs = {
                String.valueOf(startDate),
                String.valueOf(endDate),
                String.valueOf(courseId)
        };

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

    private Cursor getShiftWithEndDate(Uri uri, String[] projection, String sortOrder){
        Long courseId = ContentUris.parseId(uri);
        String endDateURi = uri.toString().substring(0, uri.toString().lastIndexOf('/'));
        Long endDate = ContentUris.parseId( Uri.parse(endDateURi));
        String startDateUri = endDateURi.substring(0, endDateURi.lastIndexOf('/'));
        Long startDate = ContentUris.parseId(Uri.parse(startDateUri));

        String selection = DbContent.ShiftDaysTable.END_DATE_COLUMN + " >?" + " AND " +
                DbContent.ShiftDaysTable.END_DATE_COLUMN + " >=?" + " AND " +
                DbContent.ShiftDaysTable.SECTION_ID_COLUMN + " =?";

        String[] selectionArgs = {
                String.valueOf(endDate),
                String.valueOf(startDate),
                String.valueOf(courseId)
        };

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

    private Cursor getShiftWithStartDate(Uri uri, String[] projection, String sortOrder){
        Long courseId = ContentUris.parseId(uri);
        String endDateURi = uri.toString().substring(0, uri.toString().lastIndexOf('/'));
        Long endDate = ContentUris.parseId( Uri.parse(endDateURi));
        String startDateUri = endDateURi.substring(0, endDateURi.lastIndexOf('/'));
        Long startDate = ContentUris.parseId(Uri.parse(startDateUri));

        String selection = DbContent.ShiftDaysTable.START_DATE_COLUMN + " <?" + " AND " +
                DbContent.ShiftDaysTable.START_DATE_COLUMN + " <=?" + " AND " +
                DbContent.ShiftDaysTable.SECTION_ID_COLUMN + " =?";

        String[] selectionArgs = {
                String.valueOf(startDate),
                String.valueOf(endDate),
                String.valueOf(courseId)
        };

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

    private Cursor getShiftWithId(Uri uri, String[] projection, String sortOrder){
        Long id = ContentUris.parseId(uri);
        String selection = DbContent.ShiftDaysTable._ID + " =?";
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

    private int deleteShiftWithStartEndDate(Uri uri){
        Long courseId = ContentUris.parseId(uri);
        String endDateURi = uri.toString().substring(0, uri.toString().lastIndexOf('/'));
        Long endDate = ContentUris.parseId( Uri.parse(endDateURi));
        String startDateUri = endDateURi.substring(0, endDateURi.lastIndexOf('/'));
        Long startDate = ContentUris.parseId(Uri.parse(startDateUri));

        String selection = DbContent.ShiftDaysTable.START_DATE_COLUMN + " >=?" + " AND " +
                DbContent.ShiftDaysTable.END_DATE_COLUMN + " <=?" + " AND " +
                DbContent.ShiftDaysTable.SECTION_ID_COLUMN + " =?";

        String[] selectionArgs = {
                String.valueOf(startDate),
                String.valueOf(endDate),
                String.valueOf(courseId)
        };

        return m_dbHelper.getReadableDatabase().delete(
                DbContent.ShiftDaysTable.TABLE_NAME,
                selection,
                selectionArgs
        );
    }

    private Cursor getSectionWithId(Uri uri, String[] projection, String sortOrder){
        final Long SECTION_ID = ContentUris.parseId(uri);
        String selection = DbContent.SectionTable._ID + " =?";
        String[] selectionArgs = {String.valueOf(SECTION_ID)};

        return m_dbHelper.getReadableDatabase().query(
                DbContent.SectionTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getCourseSectionJoinWithCourseId(Uri uri, String[] projection) {

        final Long COURSE_ID = ContentUris.parseId(uri);
        String selection = DbContent.CourseTable.TABLE_NAME +"."+ DbContent.CourseTable._ID + " =?";
        String[] selectionArgs = {String.valueOf(COURSE_ID)};
        String sortOrder = DbContent.SectionTable.SECTION_NAME_COLUMN +" ASC";

        return COURSE_SECTION_JOIN_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getCourseSectionJoinWithSectionId(Uri uri, String[] projection, String sortOrder) {

        final Long SECTION_ID = ContentUris.parseId(uri);
        String selection = DbContent.SectionTable.TABLE_NAME+"."+DbContent.SectionTable._ID + " =?";
        String[] selectionArgs = {String.valueOf(SECTION_ID)};

        return COURSE_SECTION_JOIN_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getSectionWithCourseId(Uri uri, String[] projection){

        final Long COURSE_ID = ContentUris.parseId(uri);
        String selection = DbContent.SectionTable.SECTION_COURSE_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(COURSE_ID)};
        String sortOrder = DbContent.SectionTable.SECTION_NAME_COLUMN + " ASC";
        return m_dbHelper.getReadableDatabase().query(
                DbContent.SectionTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getSectionWithCourseName(Uri uri, String[] projection, String sortType){
        String searchChars = uri.toString().substring(uri.toString().lastIndexOf("/") + 1 , uri.toString().length())
                + "%";
        Log.e("search is", searchChars);

        String selection = DbContent.CourseTable.COURSE_NAME_COLUMN + " LIKE ?";
        String[] selectionArgs = {searchChars};

        return  COURSE_SECTION_JOIN_QUERY.query(
                m_dbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortType
        );
    }

}
