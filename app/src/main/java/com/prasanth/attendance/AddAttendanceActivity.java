package com.prasanth.attendance;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.prasanth.attendance.beans.AttendanceBean;
import com.prasanth.attendance.beans.StudentBean;
import com.prasanth.attendance.context.ApplicationContext;
import com.prasanth.attendance.database.DataBaseAdapter;

import java.util.ArrayList;

public class AddAttendanceActivity extends AppCompatActivity {

    private ArrayList<StudentBean> studentBeans;

    private int sessionId;
    private String date,time;

    //create a DataBaseAdapter object
    private DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        //get session id from intent
        sessionId = getIntent().getIntExtra("sessionId", 0);

        //get date from intent
        date = getIntent().getStringExtra("date");

        //get time from intent
        time = getIntent().getStringExtra("time");

        //initialize ListView object
        ListView mStudentList = findViewById(R.id.studentList);

        //initialize Button
        Button mSubmitAttendance = findViewById(R.id.addStudentAttendance);

        //initialize a ArrayList object
        final ArrayList<String> studentList = new ArrayList<>();

        //get Contexts studentBean
        studentBeans = ApplicationContext.getInstance().getStudentBeanList();

        //for every student
        for (StudentBean studentBean : studentBeans) {
            //set status to P
            studentBean.setStatus("P");

            //assign student name to ArrayList object
            studentList.add(studentBean.getStudentId() + " - " + studentBean.getStatus());

        }

        //assign ArrayList to ArrayAdapter
        final ArrayAdapter<String> studentAdapter = new ArrayAdapter<>(this, R.layout.view_student_attendance, R.id.student, studentList);

        //set Adapter to ListView
        mStudentList.setAdapter(studentAdapter);

        //set OnItemClickListener to ListView
        mStudentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //get StudentBean
                final StudentBean studentBean = studentBeans.get(position);

                //status = 'P'
                if (studentBean.getStatus().equals("P")) {

                    //change status to 'A' in database and list
                    studentBeans.remove(studentBean);
                    StudentBean updatedStudentBean = new StudentBean();
                    updatedStudentBean.setStudentId(studentBean.getStudentId());
                    updatedStudentBean.setStatus("A");
                    studentBeans.add(updatedStudentBean);

                    //remove student from the list
                    studentList.remove(position);
                    studentList.add(updatedStudentBean.getStudentId() + " - " + updatedStudentBean.getStatus());
                    studentAdapter.notifyDataSetChanged();
                }
            }
        });

        //set OnClickListener to Button
        mSubmitAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //session is not registered
                if (sessionId == 0) {

                    //show message
                    Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();

                    return;
                }

                    //variable to check attendance is added or not
                    boolean done = false;

                    //for every student
                    for (StudentBean studentBean : studentBeans) {

                        //create AttendanceBean object
                        AttendanceBean attendanceBean = new AttendanceBean();

                        //set variables of AttendanceBean object
                        attendanceBean.setAttendanceSessionId(sessionId);
                        attendanceBean.setAttendanceStudentId(studentBean.getStudentId());
                        attendanceBean.setStatus(studentBean.getStatus());
                        attendanceBean.setDate(date);
                        attendanceBean.setTime(time);

                        //add attendance to database
                        done = dataBaseAdapter.addAttendance(attendanceBean);
                    }

                    //attendance added successfully
                    if (done) {

                        //show message
                        Toast.makeText(getApplicationContext(), "Attendance Added Successfully", Toast.LENGTH_LONG).show();

                        //start AddAttendanceSession Activity
                        startActivity(new Intent(AddAttendanceActivity.this, AddAttendanceSessionActivity.class));

                        //finish current Activity
                        finish();
                    } else

                        //show message
                        Toast.makeText(getApplicationContext(), "Some Error Occurred While Adding Attendance", Toast.LENGTH_LONG).show();
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
