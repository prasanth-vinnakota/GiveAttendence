package com.prasanth.attendance;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.prasanth.attendance.beans.AttendanceSessionBean;
import com.prasanth.attendance.context.ApplicationContext;

import java.util.ArrayList;

public class ViewLogActivity extends AppCompatActivity {

    ArrayList<String> logList;

    ListView mLogList;

    ArrayAdapter<String> logAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        //initialize ListView
        mLogList = findViewById(R.id.studentList);

        //set visibility for card as gone
        findViewById(R.id.card).setVisibility(View.GONE);

        //initialize ArrayList
        logList = new ArrayList<>();

        //add heading
        logList.add("SID FID DATE    DEPT S SUBJECT  PURPOSE");

        //get AttendanceSessionBean list from database
        final ArrayList<AttendanceSessionBean> attendanceSessionBeans = ((ApplicationContext)ViewLogActivity.this.getApplicationContext()).getAttendanceSessionBeanList();

        //for every AttendanceSessionBean
        for (AttendanceSessionBean attendanceSessionBean : attendanceSessionBeans) {

            //assign values of AttendanceSessionBEan to String
            String s = attendanceSessionBean.getAttendanceSessionId() + "   " +
                    attendanceSessionBean.getAttendanceSessionFacultyId() + "   "  +
                    attendanceSessionBean.getAttendanceSessionDate() + "  " +
                    attendanceSessionBean.getAttendanceSessionDepartment() +
                    "  " + attendanceSessionBean.getAttendanceSessionSection() +
                    " " + attendanceSessionBean.getAttendanceSessionSubject() +
                    " " + attendanceSessionBean.getAttendanceSessionPurpose() +
                    " - " + attendanceSessionBean.getAttendanceSessionEventDate();

            //add string to list
            logList.add(s);
        }

        //initialize Adapter
        logAdapter = new ArrayAdapter<>(this, R.layout.view_student_attendance, R.id.student, logList);

        //set Adapter to ListView
        mLogList.setAdapter(logAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //get selected item id
        if (item.getItemId() == R.id.credits) {//create a AlertDialog.Builder object
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //set builder title
            builder.setTitle("Credits");

            //set builder message
            builder.setMessage("App Credits : Prasanth Vinnakota \nIcon Credits www.flaticon.com");

            //set builder title
            builder.setIcon(R.mipmap.credits);

            //set builder Button
            builder.setPositiveButton("Gotcha", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            //build builder
            AlertDialog dialog = builder.create();

            //show dialog
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate menu from resources
        getMenuInflater().inflate(R.menu.menu_with_search, menu);

        //initialize MenuItem with button id
        MenuItem search = menu.findItem(R.id.search_button);

        //initialize SearchView with action view of MenuItem
        SearchView searchView = (SearchView) search.getActionView();

        //set OnQueryTextListener to SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                //create a ArrayList object
                ArrayList<String> visibleList = new ArrayList<>();

                //for every log row
                for (String visibleItem : logList){
                    //contains search bar string
                    if (visibleItem.toLowerCase().contains(s.toLowerCase())){
                        //add item to list
                        visibleList.add(visibleItem);
                    }
                }
                //initialize Adapter
                logAdapter = new ArrayAdapter<>(ViewLogActivity.this, R.layout.view_student_attendance, R.id.student, visibleList);

                //set Adapter to ListView
                mLogList.setAdapter(logAdapter);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
