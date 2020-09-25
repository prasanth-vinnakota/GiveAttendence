package com.prasanth.attendance;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.prasanth.attendance.beans.AttendanceBean;
import com.prasanth.attendance.context.ApplicationContext;

import java.util.ArrayList;

public class ViewAttendanceByFacultyActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        //initialize ListView
        ListView mStudentsList = findViewById(R.id.studentList);

        //declare a ArrayList object
        final ArrayList<String> attendanceList = new ArrayList<>();

        //set visibility for card as gone
        findViewById(R.id.card).setVisibility(View.GONE);

        //add title to list
        attendanceList.add("STUDENT ID  DATE TIME STATUS");

        //get attendance from Context
        final ArrayList<AttendanceBean> attendanceBeans = ApplicationContext.getInstance().getAttendanceBeanList();

        //for every AttendanceBean object
        for (AttendanceBean attendanceBean : attendanceBeans){

            String string;

            //check for null
            if(attendanceBean != null){

                //assign string
                string = attendanceBean.getAttendanceStudentId()+"  "+attendanceBean.getDate() +"  "+attendanceBean.getTime()+"  "+attendanceBean.getStatus();

                //add String to list
                attendanceList.add(string);
            }
        }

        //set Adapter view
        ArrayAdapter<String> studentAdapter = new ArrayAdapter<>(ViewAttendanceByFacultyActivity.this, R.layout.view_student_attendance, R.id.student, attendanceList);

        //add Adapter to List
        mStudentsList.setAdapter(studentAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //create a AlertDialog.Builder object
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //set builder title
        builder.setTitle("Credits");

        //set builder message
        builder.setMessage("App Credits : Prasanth Vinnakota and Nihara Katala\nIcon Credits www.flaticon.com");

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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate menu from resources
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
