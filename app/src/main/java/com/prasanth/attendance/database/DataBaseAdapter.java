package com.prasanth.attendance.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.prasanth.attendance.beans.AttendanceBean;
import com.prasanth.attendance.beans.AttendanceSessionBean;
import com.prasanth.attendance.beans.FacultyBean;
import com.prasanth.attendance.beans.StudentBean;

import java.util.ArrayList;

public class DataBaseAdapter extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Attendance";

    private static final int DATABASE_VERSION = 1;

    //table names
    private static final String FACULTY_INFO_TABLE = "faculty_table";
    private static final String STUDENT_INFO_TABLE = "student_table";
    private static final String ATTENDANCE_SESSION_TABLE = "attendance_session_table";
    private static final String ATTENDANCE_TABLE = "attendance_table";

    //column names for faculty table
    private static final String FACULTY_ID = "faculty_id";
    private static final String FACULTY_FIRSTNAME = "faculty_firstname";
    private static final String FACULTY_LASTNAME = "faculty_lastname";
    private static final String FACULTY_TEL = "faculty_tel";
    private static final String FACULTY_USERNAME = "faculty_username";
    private static final String FACULTY_PASSWORD = "faculty_password";

    //column names for student table
    private static final String STUDENT_ID = "student_id";
    private static final String STUDENT_FIRSTNAME = "student_firstname";
    private static final String STUDENT_LASTNAME = "student_lastname";
    private static final String STUDENT_TEl = "student_tel";
    private static final String STUDENT_DEPARTMENT = "student_department";
    private static final String STUDENT_SECTION = "student_section";

    //columns for attendance session table
    private static final String ATTENDANCE_SESSION_ID = "attendance_session_id";
    private static final String ATTENDANCE_SESSION_FACULTY_ID = "attendance_session_faculty_id";
    private static final String ATTENDANCE_SESSION_DEPARTMENT = "attendance_session_department";
    private static final String ATTENDANCE_SESSION_SECTION = "attendance_session_section";
    private static final String ATTENDANCE_SESSION_DATE = "attendance_session_date";
    private static final String ATTENDANCE_SESSION_SUBJECT = "attendance_session_subject";
    private static final String ATTENDANCE_SESSION_PURPOSE = "attendance_session_purpose";
    private static final String ATTENDANCE_SESSION_EVENT_DATE = "attendance_session_event_date";

    //column for attendance table
    private static final String SESSION_ID = "attendance_session_id";
    private static final String ATTENDANCE_STUDENT_ID = "attendance_student_id";
    private static final String ATTENDANCE_STATUS = "attendance_status";

    private Context context;

    public DataBaseAdapter(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public boolean clearDatabaseWithStudentDetails() {

        //create faculty table
        String facultyQuery = "create table " + FACULTY_INFO_TABLE + "(" + FACULTY_ID + " integer primary key autoincrement," +
                FACULTY_FIRSTNAME + " text," +
                FACULTY_LASTNAME + " text, " +
                FACULTY_TEL + " text," +
                FACULTY_USERNAME + " text unique," +
                FACULTY_PASSWORD + " text" + ")";

        //create student table
        String studentQuery = "create table " + STUDENT_INFO_TABLE + " (" +
                STUDENT_ID + " text primary key, " +
                STUDENT_FIRSTNAME + " text, " +
                STUDENT_LASTNAME + " text, " +
                STUDENT_TEl + " text, " +
                STUDENT_DEPARTMENT + " text," +
                STUDENT_SECTION + " integer " + ")";

        //create attendance session table
        String attendanceSessionQuery = "create table " + ATTENDANCE_SESSION_TABLE + " (" +
                ATTENDANCE_SESSION_ID + " integer primary key autoincrement, " +
                ATTENDANCE_SESSION_FACULTY_ID + " integer, " +
                ATTENDANCE_SESSION_DEPARTMENT + " text, " +
                ATTENDANCE_SESSION_SECTION + " integer, " +
                ATTENDANCE_SESSION_DATE + " date," +
                ATTENDANCE_SESSION_SUBJECT + " text," +
                ATTENDANCE_SESSION_PURPOSE + " text, " +
                ATTENDANCE_SESSION_EVENT_DATE + " datetime )";

        //create attendance table
        String attendanceQuery = "create table " + ATTENDANCE_TABLE + " (" +
                SESSION_ID + " integer, " +
                ATTENDANCE_STUDENT_ID + " text, " +
                ATTENDANCE_STATUS + " text " + ")";

        try {

            //get writable database
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();

            //remove tables
            sqLiteDatabase.execSQL("drop table if exists " + ATTENDANCE_SESSION_TABLE);
            sqLiteDatabase.execSQL("drop table if exists " + ATTENDANCE_TABLE);
            sqLiteDatabase.execSQL("drop table if exists " + FACULTY_INFO_TABLE);
            sqLiteDatabase.execSQL("drop table if exists " + STUDENT_INFO_TABLE);

            //create tables
            sqLiteDatabase.execSQL(facultyQuery);
            sqLiteDatabase.execSQL(studentQuery);
            sqLiteDatabase.execSQL(attendanceSessionQuery);
            sqLiteDatabase.execSQL(attendanceQuery);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clearDatabaseWithoutStudentDetails() {

        //create faculty table
        String facultyQuery = "create table " + FACULTY_INFO_TABLE + "(" + FACULTY_ID + " integer primary key autoincrement," +
                FACULTY_FIRSTNAME + " text," +
                FACULTY_LASTNAME + " text, " +
                FACULTY_TEL + " text," +
                FACULTY_USERNAME + " text unique," +
                FACULTY_PASSWORD + " text" + ")";

        //create attendance session table
        String attendanceSessionQuery = "create table " + ATTENDANCE_SESSION_TABLE + " (" +
                ATTENDANCE_SESSION_ID + " integer primary key autoincrement, " +
                ATTENDANCE_SESSION_FACULTY_ID + " integer, " +
                ATTENDANCE_SESSION_DEPARTMENT + " text, " +
                ATTENDANCE_SESSION_SECTION + " integer, " +
                ATTENDANCE_SESSION_DATE + " date," +
                ATTENDANCE_SESSION_SUBJECT + " text," +
                ATTENDANCE_SESSION_PURPOSE + " text, " +
                ATTENDANCE_SESSION_EVENT_DATE + " datetime )";

        //create attendance table
        String attendanceQuery = "create table " + ATTENDANCE_TABLE + " (" +
                SESSION_ID + " integer, " +
                ATTENDANCE_STUDENT_ID + " text, " +
                ATTENDANCE_STATUS + " text " + ")";

        try {

            //get writable database
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();

            //remove tables
            sqLiteDatabase.execSQL("drop table if exists " + ATTENDANCE_SESSION_TABLE);
            sqLiteDatabase.execSQL("drop table if exists " + ATTENDANCE_TABLE);
            sqLiteDatabase.execSQL("drop table if exists " + FACULTY_INFO_TABLE);

            //create tables
            sqLiteDatabase.execSQL(facultyQuery);
            sqLiteDatabase.execSQL(attendanceSessionQuery);
            sqLiteDatabase.execSQL(attendanceQuery);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create faculty table
        String facultyQuery = "create table " + FACULTY_INFO_TABLE + "(" + FACULTY_ID + " integer primary key autoincrement," +
                FACULTY_FIRSTNAME + " text," +
                FACULTY_LASTNAME + " text, " +
                FACULTY_TEL + " text," +
                FACULTY_USERNAME + " text unique," +
                FACULTY_PASSWORD + " text" + ")";

        //create student table
        String studentQuery = "create table " + STUDENT_INFO_TABLE + " (" +
                STUDENT_ID + " text primary key, " +
                STUDENT_FIRSTNAME + " text, " +
                STUDENT_LASTNAME + " text, " +
                STUDENT_TEl + " text, " +
                STUDENT_DEPARTMENT + " text," +
                STUDENT_SECTION + " integer " + ")";


        //create attendance session table
        String attendanceSessionQuery = "create table " + ATTENDANCE_SESSION_TABLE + " (" +
                ATTENDANCE_SESSION_ID + " integer primary key autoincrement, " +
                ATTENDANCE_SESSION_FACULTY_ID + " integer, " +
                ATTENDANCE_SESSION_DEPARTMENT + " text, " +
                ATTENDANCE_SESSION_SECTION + " integer, " +
                ATTENDANCE_SESSION_DATE + " date," +
                ATTENDANCE_SESSION_SUBJECT + " text," +
                ATTENDANCE_SESSION_PURPOSE + " text, " +
                ATTENDANCE_SESSION_EVENT_DATE + " datetime )";

        //create attendance table
        String attendanceQuery = "create table " + ATTENDANCE_TABLE + " (" +
                SESSION_ID + " integer, " +
                ATTENDANCE_STUDENT_ID + " text, " +
                ATTENDANCE_STATUS + " text " + ")";

        try {
            //create tables
            db.execSQL(facultyQuery);
            db.execSQL(studentQuery);
            db.execSQL(attendanceSessionQuery);
            db.execSQL(attendanceQuery);
        } catch (Exception e) {
            //show message
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //create faculty table
        String facultyQuery = "create table " + FACULTY_INFO_TABLE + "(" + FACULTY_ID + " integer primary key autoincrement," +
                FACULTY_FIRSTNAME + " text," +
                FACULTY_LASTNAME + " text, " +
                FACULTY_TEL + " text," +
                FACULTY_USERNAME + " text unique," +
                FACULTY_PASSWORD + " text" + ")";

        //create student table
        String studentQuery = "create table " + STUDENT_INFO_TABLE + " (" +
                STUDENT_ID + " text primary key, " +
                STUDENT_FIRSTNAME + " text, " +
                STUDENT_LASTNAME + " text, " +
                STUDENT_TEl + " text, " +
                STUDENT_DEPARTMENT + " text," +
                STUDENT_SECTION + " text " + ")";


        //create attendance session table
        String attendanceSessionQuery = "create table " + ATTENDANCE_SESSION_TABLE + " (" +
                ATTENDANCE_SESSION_ID + " integer primary key autoincrement, " +
                ATTENDANCE_SESSION_FACULTY_ID + " integer, " +
                ATTENDANCE_SESSION_DEPARTMENT + " text, " +
                ATTENDANCE_SESSION_SECTION + " integer, " +
                ATTENDANCE_SESSION_DATE + " date," +
                ATTENDANCE_SESSION_SUBJECT + " text, " +
                ATTENDANCE_SESSION_PURPOSE + " text,  " +
                ATTENDANCE_SESSION_EVENT_DATE + " datetime )";

        //create attendance table
        String attendanceQuery = "create table " + ATTENDANCE_TABLE + " (" +
                SESSION_ID + " integer, " +
                ATTENDANCE_STUDENT_ID + " text, " +
                ATTENDANCE_STATUS + " text " + ")";

        try {

            //execute SQL queries
            db.execSQL(facultyQuery);
            db.execSQL(studentQuery);
            db.execSQL(attendanceSessionQuery);
            db.execSQL(attendanceQuery);
        } catch (Exception e) {
            //show message
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public boolean addFaculty(FacultyBean facultyBean) {

        //get a WritableDatabase Object
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //query to add data to faculty table
        String addFacultyQuery = "insert into " + FACULTY_INFO_TABLE + " (" + FACULTY_FIRSTNAME + "," + FACULTY_LASTNAME + "," + FACULTY_TEL + "," + FACULTY_USERNAME + "," + FACULTY_PASSWORD + ") values ('" +
                facultyBean.getFacultyFirstName() + "', '" +
                facultyBean.getFacultyLastname() + "', '" +
                facultyBean.getFaultyTel() + "', '" +
                facultyBean.getFacultyUsername() + "', '" +
                facultyBean.getFacultyPassword() + "' )";

        try {

            //execute query
            sqLiteDatabase.execSQL(addFacultyQuery);

            //close database
            sqLiteDatabase.close();

            return true;
        } catch (Exception e) {

            //close database
            sqLiteDatabase.close();

            return false;
        }
    }

    public FacultyBean validateFaculty(String username, String password) {

        //get a WritableDatabase Object
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        //query to get faculty details
        String query = "select * from " + FACULTY_INFO_TABLE + " where " + FACULTY_USERNAME + "='" + username + "' and " + FACULTY_PASSWORD + "='" + password + "'";

        //create a cursor to query
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        //move cursor to starting position of the table
        if (cursor.moveToFirst()) {

            //create FacultyBean object
            FacultyBean facultyBean = new FacultyBean();

            //set attributes of FacultyBean
            facultyBean.setFacultyId(Integer.parseInt(cursor.getString(0)));
            facultyBean.setFacultyFirstName(cursor.getString(1));
            facultyBean.setFacultyLastName(cursor.getString(2));
            facultyBean.setFacultyTel(cursor.getString(3));
            facultyBean.setFacultyUsername(cursor.getString(4));
            facultyBean.setFacultyPassword(cursor.getString(5));

            //close Cursor
            cursor.close();

            //close database
            sqLiteDatabase.close();

            //return FacultyBean object
            return facultyBean;
        }

        //close database
        sqLiteDatabase.close();

        //return null
        return null;
    }

    public ArrayList<FacultyBean> getAllFacultiesDetails() {

        //create a ArrayList object
        ArrayList<FacultyBean> arrayList = new ArrayList<>();

        //get a WritableDatabase Object
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        //query to get all faculties details
        String query = "select * from " + FACULTY_INFO_TABLE + " order by " + FACULTY_FIRSTNAME;

        Cursor cursor;

        try {
            //create a cursor to query
            cursor = sqLiteDatabase.rawQuery(query, null);
        } catch (Exception e) {
            return null;
        }

        //move cursor to starting position of the table
        if (cursor.moveToFirst()) {

            do {

                //create FacultyBean object
                FacultyBean facultyBean = new FacultyBean();

                //set attributes of FacultyBean
                facultyBean.setFacultyId(Integer.parseInt(cursor.getString(0)));
                facultyBean.setFacultyFirstName(cursor.getString(1));
                facultyBean.setFacultyLastName(cursor.getString(2));
                facultyBean.setFacultyTel(cursor.getString(3));
                facultyBean.setFacultyUsername(cursor.getString(4));
                facultyBean.setFacultyPassword(cursor.getString(5));

                //add faculty details to ArrayList object
                arrayList.add(facultyBean);
            }
            //next row exist
            while (cursor.moveToNext());
        }

        //close cursor
        cursor.close();

        //return ArrayList object
        return arrayList;
    }

    public void deleteFaculty(int faculty_id) {

        //get a WritableDatabase Object
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //query to get all faculties details
        String query = "delete from " + FACULTY_INFO_TABLE + " where " + FACULTY_ID + " = " + faculty_id;

        //execute query
        sqLiteDatabase.execSQL(query);

        //close database
        sqLiteDatabase.close();
    }

    public boolean updateFaculty(FacultyBean facultyBean) {

        //create a WritableDatabase object
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //create a ContentValue Object
        ContentValues contentValues = new ContentValues();

        //put values to ContentValue
        contentValues.put(FACULTY_FIRSTNAME, facultyBean.getFacultyFirstName());
        contentValues.put(FACULTY_LASTNAME, facultyBean.getFacultyLastname());
        contentValues.put(FACULTY_TEL, facultyBean.getFaultyTel());

        try {
            //update database
            sqLiteDatabase.update(FACULTY_INFO_TABLE, contentValues, FACULTY_ID + " = " + facultyBean.getFacultyId(), null);

            //close database
            sqLiteDatabase.close();

            return true;
        } catch (Exception e) {

            //close database
            sqLiteDatabase.close();

            return false;
        }
    }

    public void changeFacultyPassword(int faculty_id, String old_password, String new_password) {

        //create a Readable Database object
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //write query to get current password of faculty
        String queryToGetCurrentFacultyPassword = "select " + FACULTY_PASSWORD + " from " + FACULTY_INFO_TABLE + " where " + FACULTY_ID + " = " + faculty_id;

        //execute query
        Cursor oldPassword = sqLiteDatabase.rawQuery(queryToGetCurrentFacultyPassword, null);

        //if old password exists
        if (oldPassword.moveToFirst()) {

            //entered password is incorrect
            if (!(oldPassword.getString(0).equals(old_password))) {

                //close cursor
                oldPassword.close();

                //show message
                Toast.makeText(context, "Password is incorrect", Toast.LENGTH_SHORT).show();

                return;
            }
        }

        //query to change password
        String queryToChangePassword = "update " + FACULTY_INFO_TABLE + " set " + FACULTY_PASSWORD + " = '" + new_password + "' where " + FACULTY_ID + " = " + faculty_id;

        //execute query
        sqLiteDatabase.execSQL(queryToChangePassword);

        //show message
        Toast.makeText(context, "Password Updated Successfully", Toast.LENGTH_SHORT).show();

        //close cursor
        oldPassword.close();

        //close database
        sqLiteDatabase.close();
    }

    public FacultyBean getFacultyDetailsById(int facultyId) {

        //create a Readable Database object
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        //write a query to get data from faculty_table
        String queryToGetFacultyDetails = "select * from " + FACULTY_INFO_TABLE + " where " + FACULTY_ID + " = " + facultyId;

        //create a cursor object
        Cursor cursor = sqLiteDatabase.rawQuery(queryToGetFacultyDetails, null);

        //create a FacultyBean Object
        FacultyBean facultyBean = new FacultyBean();

        //move cursor to starting position
        if (cursor.moveToFirst()) {

            //add values to FacultyBean object
            facultyBean.setFacultyId(cursor.getInt(0));
            facultyBean.setFacultyFirstName(cursor.getString(1));
            facultyBean.setFacultyLastName(cursor.getString(2));
            facultyBean.setFacultyTel(cursor.getString(3));
            facultyBean.setFacultyUsername(cursor.getString(4));
            facultyBean.setFacultyPassword(cursor.getString(5));
        }

        //close cursor
        cursor.close();

        //close database
        sqLiteDatabase.close();

        //return FacultyBean object
        return facultyBean;
    }

    public boolean addStudent(StudentBean studentBean) {

        //get a WritableDatabase Object
        SQLiteDatabase db = getWritableDatabase();

        //query to add data to student table
        String addStudentQuery = "insert into " + STUDENT_INFO_TABLE + "(" + STUDENT_ID + "," + STUDENT_FIRSTNAME + "," + STUDENT_LASTNAME + "," + STUDENT_TEl + "," + STUDENT_DEPARTMENT + "," + STUDENT_SECTION + ") values ('" +
                studentBean.getStudentId() + "', '" +
                studentBean.getStudentFirstname() + "', '" +
                studentBean.getStudentLastname() + "','" +
                studentBean.getStudentTel() + "', '" +
                studentBean.getStudentDepartment() + "', '" +
                studentBean.getStudentSection() + "' )";

        try {
            //add student to database
            db.execSQL(addStudentQuery);
        } catch (SQLiteException e) {
            return false;
        }

        //close database
        db.close();

        return true;
    }

    public boolean updateStudent(StudentBean studentBean) {

        //create a WritableDatabase object
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //create a ContentValue Object
        ContentValues contentValues = new ContentValues();

        //put values to ContentValue
        contentValues.put(STUDENT_FIRSTNAME, studentBean.getStudentFirstname());
        contentValues.put(STUDENT_LASTNAME, studentBean.getStudentLastname());
        contentValues.put(STUDENT_TEl, studentBean.getStudentTel());

        try {
            //update database
            sqLiteDatabase.update(STUDENT_INFO_TABLE, contentValues, STUDENT_ID + " = '" + studentBean.getStudentId() + "'", null);

            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public ArrayList<StudentBean> getAllStudentsDetails() {

        //create a ArrayList object
        ArrayList<StudentBean> arrayList = new ArrayList<>();

        //create a WritableDatabase object
        SQLiteDatabase db = this.getWritableDatabase();


        //query to get all students details
        String query = "select * from " + STUDENT_INFO_TABLE + " order by " + STUDENT_ID;

        Cursor cursor;

        try {
            //create a cursor to query
            cursor = db.rawQuery(query, null);

        } catch (Exception e) {

            return null;
        }

        //move cursor to starting position of the table
        if (cursor.moveToFirst()) {
            do {

                //create a StudentBean object
                StudentBean studentBean = new StudentBean();

                //set values of StudentBean object
                studentBean.setStudentId(cursor.getString(0));
                studentBean.setStudentFirstname(cursor.getString(1));
                studentBean.setStudentLastname(cursor.getString(2));
                studentBean.setStudentTel(cursor.getString(3));
                studentBean.setStudentDepartment(cursor.getString(4));
                studentBean.setStudentSection(Integer.parseInt(cursor.getString(5)));

                //add StudentBean object to list
                arrayList.add(studentBean);

            } while (cursor.moveToNext());
        }

        //close cursor
        cursor.close();

        //return ArrayList object
        return arrayList;
    }

    public ArrayList<StudentBean> getAllStudentByDepartmentAndSection(String department, int section) {

        //create ArrayList object
        ArrayList<StudentBean> arrayList = new ArrayList<>();

        //create a object for WritableDatabase
        SQLiteDatabase db = this.getWritableDatabase();

        //query to get Student details
        String query = "select * from " + STUDENT_INFO_TABLE + " where " + STUDENT_DEPARTMENT + "='" + department + "' and " + STUDENT_SECTION + "='" + section + "' order by " + STUDENT_ID;

        Cursor cursor;

        try {
            //create cursor to query
            cursor = db.rawQuery(query, null);
        } catch (Exception e) {

            return null;
        }

        //move to starting position of the table
        if (cursor.moveToFirst()) {
            do {

                //create a StudentBean object
                StudentBean studentBean = new StudentBean();

                studentBean.setStudentId(cursor.getString(0));
                studentBean.setStudentFirstname(cursor.getString(1));
                studentBean.setStudentLastname(cursor.getString(2));
                studentBean.setStudentTel(cursor.getString(3));
                studentBean.setStudentDepartment(cursor.getString(4));
                studentBean.setStudentSection(Integer.parseInt(cursor.getString(5)));

                //add StudentBean object to list
                arrayList.add(studentBean);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return arrayList;
    }

    public StudentBean getStudentById(String studentId) {
        //create a StudentBean object
        StudentBean studentBean = new StudentBean();

        //create a object for WritableDatabase
        SQLiteDatabase db = this.getWritableDatabase();

        //query to get student details
        String query = "select * from " + STUDENT_INFO_TABLE + " where " + STUDENT_ID + "= '" + studentId + "'";

        //create a cursor for query
        Cursor cursor = db.rawQuery(query, null);

        //moving cursor to starting position of table
        if (cursor.moveToFirst()) {

            //set values of StudentBean object
            studentBean.setStudentId(cursor.getString(0));
            studentBean.setStudentFirstname(cursor.getString(1));
            studentBean.setStudentLastname(cursor.getString(2));
            studentBean.setStudentTel(cursor.getString(3));
            studentBean.setStudentDepartment(cursor.getString(4));
            studentBean.setStudentSection(Integer.parseInt(cursor.getString(5)));
        }

        //close Cursor object
        cursor.close();

        //return studentBean object
        return studentBean;
    }

    public void deleteStudent(String studentId) {

        //create a object for WritableDatabase
        SQLiteDatabase db = this.getWritableDatabase();

        //create query to delete student from database
        String query = "delete from " + STUDENT_INFO_TABLE + " where " + STUDENT_ID + " = '" + studentId + "'";

        //execute query
        db.execSQL(query);

        //close database
        db.close();

    }

    public int addAttendanceSession(AttendanceSessionBean attendanceSessionBean) {

        //create a object for WritableDatabase
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //create query to add attendance
        String addAttendanceSessionQuery = "insert into " + ATTENDANCE_SESSION_TABLE + "(" + ATTENDANCE_SESSION_FACULTY_ID + "," + ATTENDANCE_SESSION_DEPARTMENT + "," + ATTENDANCE_SESSION_SECTION + "," + ATTENDANCE_SESSION_DATE + "," + ATTENDANCE_SESSION_SUBJECT + "," + ATTENDANCE_SESSION_PURPOSE + "," + ATTENDANCE_SESSION_EVENT_DATE + ") values (" +
                attendanceSessionBean.getAttendanceSessionFacultyId() + " , '" +
                attendanceSessionBean.getAttendanceSessionDepartment() + "'," +
                attendanceSessionBean.getAttendanceSessionSection() + " , '" +
                attendanceSessionBean.getAttendanceSessionDate() + "', '" +
                attendanceSessionBean.getAttendanceSessionSubject() + "','" +
                attendanceSessionBean.getAttendanceSessionPurpose() + "','" +
                attendanceSessionBean.getAttendanceSessionEventDate() + "' )";

        //execute query
        sqLiteDatabase.execSQL(addAttendanceSessionQuery);

        //create a cursor to get session id
        Cursor cursor = sqLiteDatabase.rawQuery("select max (" + ATTENDANCE_SESSION_ID + ") from " + ATTENDANCE_SESSION_TABLE, null);

        if (cursor.moveToFirst()) {

            //return session id
            return Integer.parseInt(cursor.getString(0));
        }

        //close database
        sqLiteDatabase.close();

        //close cursor
        cursor.close();

        //return zero
        return 0;
    }

    public ArrayList<AttendanceSessionBean> getAllAttendanceSessions() {

        //create a ArrayList object
        ArrayList<AttendanceSessionBean> attendanceSessionBeans = new ArrayList<>();

        //create a object for WritableDatabase
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //create query to get all attendance sessions
        String query = "select * from " + ATTENDANCE_SESSION_TABLE;

        Cursor cursor;

        try {

            //create a Cursor to query
            cursor = sqLiteDatabase.rawQuery(query, null);
        } catch (Exception e) {

            //show message
            Toast.makeText(context, "No Log Available", Toast.LENGTH_SHORT).show();

            return null;
        }

        //move cursor to starting position of table
        if (cursor.moveToFirst()) {

            do {
                //create a AttendanceSessionBean object
                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();

                //set values of AttendanceBean object
                attendanceSessionBean.setAttendanceSessionId(Integer.parseInt(cursor.getString(0)));
                attendanceSessionBean.setAttendanceSessionFacultyId(Integer.parseInt(cursor.getString(1)));
                attendanceSessionBean.setAttendanceSessionDepartment(cursor.getString(2));
                attendanceSessionBean.setAttendanceSessionSection(Integer.parseInt(cursor.getString(3)));
                attendanceSessionBean.setAttendanceSessionDate(cursor.getString(4));
                attendanceSessionBean.setAttendanceSessionSubject(cursor.getString(5));
                attendanceSessionBean.setAttendanceSessionPurpose(cursor.getString(6));
                attendanceSessionBean.setAttendanceSessionEventDate(cursor.getString(7));

                //add AttendanceSessionBean object to ArrayList
                attendanceSessionBeans.add(attendanceSessionBean);
            }
            while (cursor.moveToNext());
        }

        //close cursor
        cursor.close();

        //close database
        sqLiteDatabase.close();

        //return ArrayList object
        return attendanceSessionBeans;
    }

    public ArrayList<AttendanceSessionBean> getAttendanceSessionsByFacultyId(int faculty_id) {

        //create a ArrayList object
        ArrayList<AttendanceSessionBean> attendanceSessionBeans = new ArrayList<>();

        //create a object for WritableDatabase
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //create query to get all attendance sessions
        String query = "select * from " + ATTENDANCE_SESSION_TABLE + " where " + ATTENDANCE_SESSION_FACULTY_ID + " = " + faculty_id;

        Cursor cursor;

        try {
            //create a Cursor to query
            cursor = sqLiteDatabase.rawQuery(query, null);

        } catch (Exception e) {

            return null;
        }

        //move cursor to starting position of table
        if (cursor.moveToFirst()) {

            do {
                //create a AttendanceSessionBean object
                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();

                attendanceSessionBean.setAttendanceSessionId(Integer.parseInt(cursor.getString(0)));
                attendanceSessionBean.setAttendanceSessionFacultyId(Integer.parseInt(cursor.getString(1)));
                attendanceSessionBean.setAttendanceSessionDepartment(cursor.getString(2));
                attendanceSessionBean.setAttendanceSessionSection(Integer.parseInt(cursor.getString(3)));
                attendanceSessionBean.setAttendanceSessionDate(cursor.getString(4));
                attendanceSessionBean.setAttendanceSessionSubject(cursor.getString(5));
                attendanceSessionBean.setAttendanceSessionPurpose(cursor.getString(6));
                attendanceSessionBean.setAttendanceSessionEventDate(cursor.getString(7));

                //add AttendanceSessionBean object to ArrayList
                attendanceSessionBeans.add(attendanceSessionBean);

            }
            while (cursor.moveToNext());
        }

        //close cursor
        cursor.close();

        //return ArrayList object
        return attendanceSessionBeans;
    }

    public boolean addAttendance(AttendanceBean attendanceBean) {

        //create a WritableDatabase
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //query to add attendance
        String addAttendanceQuery = "insert into " + ATTENDANCE_TABLE + " ( " + SESSION_ID + "," + ATTENDANCE_STUDENT_ID + "," + ATTENDANCE_STATUS + ") values ('" +
                attendanceBean.getAttendanceSessionId() + "', '" +
                attendanceBean.getAttendanceStudentId() + "', '" +
                attendanceBean.getStatus() + "' )";

        try {

            //execute query
            sqLiteDatabase.execSQL(addAttendanceQuery);

            //close database
            sqLiteDatabase.close();

            return true;
        } catch (Exception e) {

            //show message
            Toast.makeText(context, "Unable to Add Attendance", Toast.LENGTH_SHORT).show();

            //close database
            sqLiteDatabase.close();

            return false;
        }
    }

    public ArrayList<AttendanceBean> getAttendance(AttendanceSessionBean attendanceSessionBean) {

        int attendanceSessionId;

        //create a ArrayList object
        ArrayList<AttendanceBean> attendanceBeans = new ArrayList<>();

        //create object for Writable Database
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        //create a query to get data from database
        String query = "select " + ATTENDANCE_SESSION_ID + " from " + ATTENDANCE_SESSION_TABLE + " where " + ATTENDANCE_SESSION_FACULTY_ID + " = " + attendanceSessionBean.getAttendanceSessionFacultyId() +
                " and " + ATTENDANCE_SESSION_DEPARTMENT + " = '" + attendanceSessionBean.getAttendanceSessionDepartment() +
                "' and " + ATTENDANCE_SESSION_SECTION + " = " + attendanceSessionBean.getAttendanceSessionSection() +
                " and " + ATTENDANCE_SESSION_DATE + " = '" + attendanceSessionBean.getAttendanceSessionDate() +
                "' and " + ATTENDANCE_SESSION_SUBJECT + " = '" + attendanceSessionBean.getAttendanceSessionSubject() +
                "' and " + ATTENDANCE_SESSION_PURPOSE + " = 'ADD ATTENDANCE'";
        Cursor cursor;

        try {

            //create cursor to query
            cursor = sqLiteDatabase.rawQuery(query, null);

        } catch (Exception e) {

            return null;
        }

        //move cursor to starting position of table
        if (cursor.moveToFirst()) {
            do {

                //get session id from database
                attendanceSessionId = cursor.getInt(0);

                //create a query to get data from database
                String getAttendanceBySessionIdQuery = "select * from " + ATTENDANCE_TABLE + " where " + SESSION_ID + " = " + attendanceSessionId + " order by " + ATTENDANCE_STUDENT_ID;

                //create cursor to query
                Cursor cursor1 = sqLiteDatabase.rawQuery(getAttendanceBySessionIdQuery, null);

                //move cursor to starting position of table
                if (cursor1.moveToFirst()) {

                    do {
                        //create AttendanceBean object
                        AttendanceBean attendanceBean = new AttendanceBean();

                        attendanceBean.setAttendanceSessionId(Integer.parseInt(cursor1.getString(0)));
                        attendanceBean.setAttendanceStudentId(cursor1.getString(1));
                        attendanceBean.setStatus(cursor1.getString(2));

                        //add AttendanceBean object to list
                        attendanceBeans.add(attendanceBean);
                    }
                    while (cursor1.moveToNext());

                    //close cursor
                    cursor1.close();
                }

            }
            while (cursor.moveToNext());

            return attendanceBeans;
        }

        //close cursor
        cursor.close();

        return null;
    }

    public ArrayList<AttendanceBean> getTotalAttendance(AttendanceSessionBean attendanceSessionBean) {

        int attendanceSessionId;

        //create a ArrayList object
        ArrayList<AttendanceBean> attendanceBeans = new ArrayList<>();

        //create object for Writable Database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //create a query to get data from database
        String query = "select * from " + ATTENDANCE_SESSION_TABLE + " where " + ATTENDANCE_SESSION_FACULTY_ID + " = '" + attendanceSessionBean.getAttendanceSessionFacultyId() +
                "' and " + ATTENDANCE_SESSION_DEPARTMENT + " = '" + attendanceSessionBean.getAttendanceSessionDepartment() +
                "' and " + ATTENDANCE_SESSION_SECTION + " = '" + attendanceSessionBean.getAttendanceSessionSection() +
                "' and " + ATTENDANCE_SESSION_SUBJECT + " = '" + attendanceSessionBean.getAttendanceSessionSubject() +
                "' and " + ATTENDANCE_SESSION_PURPOSE + " = 'ADD ATTENDANCE'";

        //create cursor to query
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        //move cursor to starting position
        if (cursor.moveToFirst()) {
            do {

                //create AttendanceBean object
                AttendanceBean dateAttendanceBean = new AttendanceBean();

                dateAttendanceBean.setAttendanceSessionId(0);
                dateAttendanceBean.setAttendanceStudentId("Date : ");
                dateAttendanceBean.setStatus(cursor.getString(4));

                //add AttendanceBean object to list
                attendanceBeans.add(dateAttendanceBean);

                //get session id from database
                attendanceSessionId = Integer.parseInt(cursor.getString(0));

                //create a query to get data from database
                String getAttendanceBySessionIdQuery = "select * from " + ATTENDANCE_TABLE + " where " + SESSION_ID + "=" + attendanceSessionId + " order by " + ATTENDANCE_STUDENT_ID;

                //create a cursor to database
                Cursor cursor1 = sqLiteDatabase.rawQuery(getAttendanceBySessionIdQuery, null);

                //move cursor to starting position
                if (cursor1.moveToFirst()) {

                    do {

                        //create AttendanceBean object
                        AttendanceBean attendanceBean = new AttendanceBean();

                        attendanceBean.setAttendanceSessionId(Integer.parseInt(cursor1.getString(0)));
                        attendanceBean.setAttendanceStudentId(cursor1.getString(1));
                        attendanceBean.setStatus(cursor1.getString(2));

                        //add AttendanceBean object to list
                        attendanceBeans.add(attendanceBean);
                    }
                    while (cursor1.moveToNext());

                    //close cursor
                    cursor1.close();
                }
            }
            while (cursor.moveToNext());

            //close cursor
            cursor.close();

            return attendanceBeans;
        }

        return null;
    }

    public ArrayList<String> getClassesDates(AttendanceSessionBean attendanceSessionBean) {

        //initialize ArrayList
        ArrayList<String> returnString = new ArrayList<>();

        //add title to list
        returnString.add("DATE");

        //create a SQLiteDatabase object
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        //create a query
        String queryToGetDates = "select " + ATTENDANCE_SESSION_DATE + " from " + ATTENDANCE_SESSION_TABLE +
                " where " + ATTENDANCE_SESSION_FACULTY_ID + " = " + attendanceSessionBean.getAttendanceSessionFacultyId() +
                " and " + ATTENDANCE_SESSION_PURPOSE + " = 'ADD ATTENDANCE' " +
                " and " + ATTENDANCE_SESSION_DEPARTMENT + " = '" + attendanceSessionBean.getAttendanceSessionDepartment() +
                "' and " + ATTENDANCE_SESSION_SECTION + " = " + attendanceSessionBean.getAttendanceSessionSection() +
                " and " + ATTENDANCE_SESSION_SUBJECT + " = '" + attendanceSessionBean.getAttendanceSessionSubject() + "'";

        //execute query using cursor
        Cursor date = sqLiteDatabase.rawQuery(queryToGetDates, null);

        //move cursor to starting position
        if (date.moveToFirst()) {

            do {

                //add string to return string
                returnString.add(date.getString(0));
            }
            while (date.moveToNext());

            //close cursor
            date.close();

            //close database
            sqLiteDatabase.close();

            return returnString;
        }

        //close cursor
        date.close();

        //close database
        sqLiteDatabase.close();

        return null;
    }

    public boolean invertAttendance(String student_id, AttendanceSessionBean attendanceSessionBean) {

        //chow message
        Toast.makeText(context, student_id, Toast.LENGTH_SHORT).show();

        //create a Writable database object
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //create a query
        String queryToGetAttendanceSessionId = "select " + ATTENDANCE_SESSION_ID + " from " + ATTENDANCE_SESSION_TABLE +
                " where " + ATTENDANCE_SESSION_FACULTY_ID + " = " + attendanceSessionBean.getAttendanceSessionFacultyId() +
                " and " + ATTENDANCE_SESSION_PURPOSE + " = '" + attendanceSessionBean.getAttendanceSessionPurpose() +
                "' and " + ATTENDANCE_SESSION_DEPARTMENT + " = '" + attendanceSessionBean.getAttendanceSessionDepartment() +
                "' and " + ATTENDANCE_SESSION_SECTION + " = " + attendanceSessionBean.getAttendanceSessionSection() +
                " and " + ATTENDANCE_SESSION_DATE + " = '" + attendanceSessionBean.getAttendanceSessionDate() +
                "' and " + ATTENDANCE_SESSION_SUBJECT + " = '" + attendanceSessionBean.getAttendanceSessionSubject() + "'";

        Cursor sessionId;
        try {

            //execute query using cursor
            sessionId = sqLiteDatabase.rawQuery(queryToGetAttendanceSessionId, null);
        } catch (Exception e) {

            //close database
            sqLiteDatabase.close();
            return false;
        }

        //move cursor to starting position of table
        if (sessionId.moveToFirst()) {

            //create a query
            String queryToGetStatusOfStudent = "select " + ATTENDANCE_STATUS + " from " + ATTENDANCE_TABLE + " where " + SESSION_ID + " = " + sessionId.getInt(0) + " and " + ATTENDANCE_STUDENT_ID + " = '" + student_id + "'";

            Cursor status;
            try {

                //execute query using cursor
                status = sqLiteDatabase.rawQuery(queryToGetStatusOfStudent, null);
            } catch (Exception e) {

                //close database
                sqLiteDatabase.close();

                return false;
            }

            //move cursor to starting position of table
            if (status.moveToFirst()) {

                //show message
                Toast.makeText(context, status.getString(0), Toast.LENGTH_SHORT).show();

                //get status of student
                if (status.getString(0).equals("P")) {

                    try {

                        //execute query
                        sqLiteDatabase.execSQL("update " + ATTENDANCE_TABLE + " set " + ATTENDANCE_STATUS + " = 'A' where " + SESSION_ID + " = " + sessionId.getInt(0) + " and " + ATTENDANCE_STUDENT_ID + " = '" + student_id + "'");
                    } catch (Exception e) {

                        //close database
                        sqLiteDatabase.close();

                        return false;
                    }

                    //close cursor
                    status.close();

                    //close database
                    sqLiteDatabase.close();

                    return true;
                } else {

                    try {

                        //execute query
                        sqLiteDatabase.execSQL("update " + ATTENDANCE_TABLE + " set " + ATTENDANCE_STATUS + " = 'P' where " + SESSION_ID + " = " + sessionId.getInt(0) + " and " + ATTENDANCE_STUDENT_ID + " = '" + student_id + "'");
                    } catch (Exception e) {

                        //close database
                        sqLiteDatabase.close();

                        return false;
                    }

                    //close cursor
                    status.close();

                    //close database
                    sqLiteDatabase.close();

                    return true;
                }
            }

            //close cursor
            status.close();

            //close cursor
            sessionId.close();

            //close database
            sqLiteDatabase.close();
        }
        return false;
    }

    public void addOrUpdateAttendance(String student_id, AttendanceSessionBean attendanceSessionBean, String status) {

        //create object for Writable database
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //create a query
        String queryToGetSessionId = "select " + ATTENDANCE_SESSION_ID + " from " + ATTENDANCE_SESSION_TABLE + " where " + ATTENDANCE_SESSION_FACULTY_ID + " = " + attendanceSessionBean.getAttendanceSessionFacultyId() +
                " and " + ATTENDANCE_SESSION_DEPARTMENT + " = '" + attendanceSessionBean.getAttendanceSessionDepartment() +
                "' and " + ATTENDANCE_SESSION_SECTION + " = " + attendanceSessionBean.getAttendanceSessionSection() +
                " and " + ATTENDANCE_SESSION_SUBJECT + " = '" + attendanceSessionBean.getAttendanceSessionSubject() +
                "' and " + ATTENDANCE_SESSION_DATE + " = '" + attendanceSessionBean.getAttendanceSessionDate() +
                "' and " + ATTENDANCE_SESSION_PURPOSE + " = 'ADD ATTENDANCE'";

        Cursor cursor;
        try {

            //execute query using cursor
            cursor = sqLiteDatabase.rawQuery(queryToGetSessionId, null);
        } catch (Exception e) {

            //close database
            sqLiteDatabase.close();

            return;
        }

        //move cursor to starting position of table
        if (cursor.moveToFirst()) {

            do {

                //create a query
                String queryToCheckStudentId = "select " + ATTENDANCE_STUDENT_ID + " from " + ATTENDANCE_TABLE + " where " + SESSION_ID + " = " + cursor.getInt(0) + " and " + ATTENDANCE_STUDENT_ID + " = '" + student_id + "'";

                Cursor cursor1;
                try {

                    //execute query using cursor
                    cursor1 = sqLiteDatabase.rawQuery(queryToCheckStudentId, null);
                } catch (Exception e) {

                    //close database
                    sqLiteDatabase.close();

                    return;
                }

                //move cursor to starting position of table
                if (cursor1.moveToFirst()) {

                    try {

                        //execute query
                        sqLiteDatabase.execSQL("update " + ATTENDANCE_TABLE + " set " + ATTENDANCE_STATUS + " = '" + status + "' where " + SESSION_ID + " = " + cursor.getInt(0) + " and " + ATTENDANCE_STUDENT_ID + " = '" + student_id + "'");

                        //show message
                        Toast.makeText(context, "Attendance Updated Successfully", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                        //show message
                        Toast.makeText(context, "Error Occurred While Updating Attendance", Toast.LENGTH_SHORT).show();

                        //close database
                        sqLiteDatabase.close();

                        return;
                    }
                } else {

                    try {

                        //execute query
                        sqLiteDatabase.execSQL("insert into " + ATTENDANCE_TABLE + "(" + SESSION_ID + "," + ATTENDANCE_STUDENT_ID + "," + ATTENDANCE_STATUS + ") values (" + cursor.getInt(0) + ",'" + student_id + "','" + status + "')");

                        //show message
                        Toast.makeText(context, "Attendance Added Successfully", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                        //show message
                        Toast.makeText(context, "Error Occurred While Adding Attendance", Toast.LENGTH_SHORT).show();

                        //close database
                        sqLiteDatabase.close();

                        return;
                    }

                    //close cursor
                    cursor1.close();
                }
            } while (cursor.moveToNext());
        } else {

            //show message
            Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
        }

        //close cursor
        cursor.close();

        //close database
        sqLiteDatabase.close();
    }

    public ArrayList<String> getStudentAttendanceDates(AttendanceSessionBean attendanceSessionBean, String student_id) {

        //create a ArrayList object
        ArrayList<String> returnString = new ArrayList<>();

        //create a object for ReadableDatabase
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        //create a query
        String query = "select " + ATTENDANCE_SESSION_ID + " , " + ATTENDANCE_SESSION_DATE + " from " + ATTENDANCE_SESSION_TABLE + " where " + ATTENDANCE_SESSION_FACULTY_ID + " = " + attendanceSessionBean.getAttendanceSessionFacultyId() +
                " and " + ATTENDANCE_SESSION_DEPARTMENT + " = '" + attendanceSessionBean.getAttendanceSessionDepartment() +
                "' and " + ATTENDANCE_SESSION_SUBJECT + " = '" + attendanceSessionBean.getAttendanceSessionSubject() +
                "' and " + ATTENDANCE_SESSION_SECTION + " = " + attendanceSessionBean.getAttendanceSessionSection() +
                " and " + ATTENDANCE_SESSION_PURPOSE + " = 'ADD ATTENDANCE'";

        Cursor cursor;
        try {

            //execute query using cursor
            cursor = sqLiteDatabase.rawQuery(query, null);
        } catch (Exception e) {

            //close database
            sqLiteDatabase.close();

            return null;
        }

        //move cursor to table starting position
        if (cursor.moveToFirst()) {

            do {

                //create query
                String queryToGetStatus = "select " + ATTENDANCE_STATUS + " from " + ATTENDANCE_TABLE + " where " + SESSION_ID + " = " + cursor.getInt(0) + " and " + ATTENDANCE_STUDENT_ID + " = '" + student_id + "'";

                Cursor cursor1;
                try {

                    //execute query using cursor
                    cursor1 = sqLiteDatabase.rawQuery(queryToGetStatus, null);
                } catch (Exception e) {

                    //close database
                    sqLiteDatabase.close();

                    return null;
                }

                //move cursor to table starting position
                if (cursor1.moveToFirst()) {

                    //add string to list
                    returnString.add(cursor.getString(1) + " - " + cursor1.getString(0));

                }

                //close cursor
                cursor1.close();

            } while (cursor.moveToNext());
        }

        //close database
        sqLiteDatabase.close();

        //close cursor
        cursor.close();

        return returnString;
    }

    public ArrayList<String> getStudentAttendancePercentage(AttendanceSessionBean attendanceSessionBean, ArrayList<StudentBean> studentBeans) {

        //create a string array list
        ArrayList<String> returnString = new ArrayList<>();

        //set title to the list
        returnString.add("STUDENT ID   P   T   %");

        //create a readable database
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        //query to get session id from attendance_table
        String query = "select " + ATTENDANCE_SESSION_ID + " from " + ATTENDANCE_SESSION_TABLE + " where " + ATTENDANCE_SESSION_FACULTY_ID + " = " + attendanceSessionBean.getAttendanceSessionFacultyId() +
                " and " + ATTENDANCE_SESSION_DEPARTMENT + " = '" + attendanceSessionBean.getAttendanceSessionDepartment() +
                "' and " + ATTENDANCE_SESSION_SUBJECT + " = '" + attendanceSessionBean.getAttendanceSessionSubject() +
                "' and " + ATTENDANCE_SESSION_SECTION + " = " + attendanceSessionBean.getAttendanceSessionSection() +
                " and " + ATTENDANCE_SESSION_PURPOSE + " = 'ADD ATTENDANCE'";

        Cursor cursor;

        try {

            //execute query using cursor
            cursor = sqLiteDatabase.rawQuery(query, null);
        } catch (Exception e) {

            //close database
            sqLiteDatabase.close();

            return null;
        }

        //for every student bean
        for (StudentBean studentBean : studentBeans) {

            float present = 0, total = 0;

            //move to first position of table
            if (cursor.moveToFirst()) {

                do {

                    //execute query using cursor
                    Cursor cursor1 = sqLiteDatabase.rawQuery("select " + ATTENDANCE_STATUS + " from " + ATTENDANCE_TABLE + " where " + SESSION_ID + " = " + cursor.getInt(0) + " and " + ATTENDANCE_STUDENT_ID + " = '" + studentBean.getStudentId() + "'", null);

                    //move to first position of table
                    if (cursor1.moveToFirst()) {

                        //if status is equal to 'P'
                        if (cursor1.getString(0).equals("P"))

                            //increase present value by 1
                            present += 1;

                        //increase total value by 1
                        total += 1;
                    }

                    //close cursor
                    cursor1.close();
                }
                while (cursor.moveToNext());

                try {

                    //add string to return string
                    returnString.add(studentBean.getStudentId() + "  " + Math.round(present) + "  " + Math.round(total) + "  " + Math.round((present / total) * 100) + "%");
                } catch (ArithmeticException e) {

                    //close cursor
                    cursor.close();

                    //close database
                    sqLiteDatabase.close();

                    return null;
                }
            }
        }

        //close cursor
        cursor.close();

        //close database
        sqLiteDatabase.close();

        return returnString;
    }

    public ArrayList<String> getDetainedStudentsList(String subject) {

        //create a string array list
        ArrayList<String> returnString = new ArrayList<>();

        //set title to the list
        returnString.add("STUDENT ID   P   T   %");

        //create a readable database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        //query to get session id from attendance_table
        String query = "select " + ATTENDANCE_SESSION_ID + " from " + ATTENDANCE_SESSION_TABLE + " where " + ATTENDANCE_SESSION_SUBJECT + " = '" + subject + "'";

        Cursor cursor;

        try {

            //execute query using cursor
            cursor = sqLiteDatabase.rawQuery(query, null);
        } catch (Exception e) {

            //close database
            sqLiteDatabase.close();

            //show message
            Toast.makeText(context, "Nothing to Show", Toast.LENGTH_SHORT).show();

            return null;
        }

        //create a string list for student ids
        ArrayList<String> studentList = new ArrayList<>();

        //move to first position of table
        if (cursor.moveToFirst()) {

            //query to get student ids
            String queryToGetStudentIds = "select " + ATTENDANCE_STUDENT_ID + " from " + ATTENDANCE_TABLE + " where " + SESSION_ID + " = " + cursor.getString(0);

            //execute query using cursor
            Cursor studentListCursor = sqLiteDatabase.rawQuery(queryToGetStudentIds, null);

            //move to starting position
            if (studentListCursor.moveToFirst()) {

                do {
                    //add student id to list
                    studentList.add(studentListCursor.getString(0));
                }
                while (studentListCursor.moveToNext());

                //close cursor
                studentListCursor.close();
            }
        }

        //for every student
        for (String student : studentList) {

            float present = 0, total = 0;

            //move to starting position of table
            if (cursor.moveToFirst()) {

                do {

                    //execute query with cursor to get status
                    Cursor cursor1 = sqLiteDatabase.rawQuery("select " + ATTENDANCE_STATUS + " from " + ATTENDANCE_TABLE + " where " + SESSION_ID + " = " + cursor.getString(0) + " and " + ATTENDANCE_STUDENT_ID + " = '" + student + "'", null);

                    //move cursor to staring position of table
                    if (cursor1.moveToFirst()) {

                        //if student is present
                        if (cursor1.getString(0).equals("P"))
                            //increase present by 1
                            present += 1;

                        //increase total by 1
                        total += 1;
                    }

                    //close cursor
                    cursor1.close();
                }
                while (cursor.moveToNext());
            }

            try {
                //attendance percentage is lest than 75 and greater that 64
                if (Math.round((present / total) * 100) < 65)
                    //add student data
                    returnString.add(student + "  " + Math.round(present) + "  " + Math.round(total) + "  " + Math.round((present / total) * 100) + "%");
            } catch (ArithmeticException e) {

                return null;
            }

        }

        //close cursor
        cursor.close();

        //close database
        sqLiteDatabase.close();

        return returnString;
    }

    public ArrayList<String> getCondonationStudentsList(String subject) {

        //create a string array list
        ArrayList<String> returnString = new ArrayList<>();

        //set title to the list
        returnString.add("STUDENT ID   P   T   %");

        //create a readable database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        //query to get session id from attendance_table
        String query = "select " + ATTENDANCE_SESSION_ID + " from " + ATTENDANCE_SESSION_TABLE + " where " + ATTENDANCE_SESSION_SUBJECT + " = '" + subject + "'";

        Cursor cursor;

        try {

            //execute query using cursor
            cursor = sqLiteDatabase.rawQuery(query, null);

        } catch (Exception e) {

            //close database
            sqLiteDatabase.close();

            //show message
            Toast.makeText(context, "Nothing to Show", Toast.LENGTH_SHORT).show();

            return null;
        }

        //create a string list for student ids
        ArrayList<String> studentList = new ArrayList<>();

        //move to last position of table
        if (cursor.moveToFirst()) {

            //query to get student ids
            String queryToGetStudentIds = "select " + ATTENDANCE_STUDENT_ID + " from " + ATTENDANCE_TABLE + " where " + SESSION_ID + " = " + cursor.getString(0);

            //execute query using cursor
            Cursor studentListCursor = sqLiteDatabase.rawQuery(queryToGetStudentIds, null);

            //move to starting position
            if (studentListCursor.moveToFirst()) {

                do {
                    //add student id to list
                    studentList.add(studentListCursor.getString(0));
                }
                while (studentListCursor.moveToNext());

                //close cursor
                studentListCursor.close();
            }
        }

        //for every student
        for (String student : studentList) {

            float present = 0, total = 0;

            //move to starting position of table
            if (cursor.moveToFirst()) {

                do {

                    //execute query with cursor to get status
                    Cursor cursor1 = sqLiteDatabase.rawQuery("select " + ATTENDANCE_STATUS + " from " + ATTENDANCE_TABLE + " where " + SESSION_ID + " = " + cursor.getString(0) + " and " + ATTENDANCE_STUDENT_ID + " = '" + student + "'", null);

                    //move cursor to staring position of table
                    if (cursor1.moveToFirst()) {

                        //if student is present
                        if (cursor1.getString(0).equals("P"))
                            //increase present by 1
                            present += 1;

                        //increase total by 1
                        total += 1;
                    }

                    //close cursor
                    cursor1.close();
                }
                while (cursor.moveToNext());
            }

            try {
                //attendance percentage is lest than 75 and greater that 64
                if (Math.round((present / total) * 100) > 64 && Math.round((present / total) * 100) < 75)
                    //add student data
                    returnString.add(student + "  " + Math.round(present) + "  " + Math.round(total) + "  " + Math.round((present / total) * 100) + "%");
            } catch (ArithmeticException e) {

                return null;
            }

        }

        //close cursor
        cursor.close();

        //close database
        sqLiteDatabase.close();

        return returnString;
    }

}