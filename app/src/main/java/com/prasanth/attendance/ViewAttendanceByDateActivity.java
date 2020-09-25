package com.prasanth.attendance;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.prasanth.attendance.beans.AttendanceBean;
import com.prasanth.attendance.beans.AttendanceSessionBean;
import com.prasanth.attendance.context.ApplicationContext;
import com.prasanth.attendance.database.DataBaseAdapter;

import java.util.ArrayList;

public class ViewAttendanceByDateActivity extends AppCompatActivity {

    ListView mAttendanceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        //set activity title
        setTitle("Attendance on " + getIntent().getStringExtra("date"));

        //initialize ListView
        mAttendanceList = findViewById(R.id.studentList);

        //set visibility for card as gone
        findViewById(R.id.card).setVisibility(View.GONE);

        //get attendance bean list
        final ArrayList<AttendanceBean> attendanceBeans = ApplicationContext.getInstance().getAttendanceBeanList();

        //create a ArrayList object
        ArrayList<String> attendanceList = new ArrayList<>();

        //for every attendance bean
        for (AttendanceBean attendanceBean : attendanceBeans) {

            //get id and status from attendance bean
            String s = attendanceBean.getAttendanceStudentId() + " - " + attendanceBean.getTime() +" - "+ attendanceBean.getStatus();

            //add String object to list
            attendanceList.add(s);
        }

        //initialize ArrayAdapter object
        ArrayAdapter<String> attendanceAdapter = new ArrayAdapter<>(this, R.layout.view_student_attendance, R.id.student, attendanceList);

        //set adapter to list view
        mAttendanceList.setAdapter(attendanceAdapter);

        //set OnItemClickListener to ListView object
        mAttendanceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                //create AlertDialog.Builder object
                final AlertDialog.Builder builder = new AlertDialog.Builder(ViewAttendanceByDateActivity.this);

                //set title for builder
                builder.setTitle("Invert Attendance?");

                //set icon for builder
                builder.setIcon(R.mipmap.invert);

                //set button
                builder.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                //set button
                builder.setPositiveButton("Yup", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //create DataBaseAdapter object
                        DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(ViewAttendanceByDateActivity.this);

                        //get attendance session bean
                        AttendanceSessionBean attendanceSessionBean = ApplicationContext.getInstance().getAttendanceSessionBean();

                        //attendance is inverted
                        if (dataBaseAdapter.invertAttendance(attendanceBeans.get(position).getAttendanceStudentId(), attendanceSessionBean)) {

                            //show message
                            Toast.makeText(getApplicationContext(), "Attendance Inverted", Toast.LENGTH_LONG).show();

                            //finish current activity
                            finish();
                        }
                        //attendance is not inverted
                        else {

                            //show message
                            Toast.makeText(getApplicationContext(), "There is a Problem in Inverting\nTry Add / Update Attendance option", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                //build and show builder
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //create a AlertDialog.Builder object
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate menu from resources
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
