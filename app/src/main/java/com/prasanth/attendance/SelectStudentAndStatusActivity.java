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
import android.widget.Button;
import android.widget.Spinner;

import com.prasanth.attendance.beans.AttendanceSessionBean;
import com.prasanth.attendance.beans.StudentBean;
import com.prasanth.attendance.context.ApplicationContext;
import com.prasanth.attendance.database.DataBaseAdapter;

import java.util.ArrayList;

public class SelectStudentAndStatusActivity extends AppCompatActivity {

    private String studentId, status = "P";

    private String[] statuses = new String[]{"P" , "A"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_student_and_status);

        //initialize Spinner
        Spinner mStudentsId = findViewById(R.id.selectStudentID);
        Spinner mStatus = findViewById(R.id.selectStudentStatus);

        //initialize Button
        Button mSubmit = findViewById(R.id.submitStudentIdAndStatus);

        //create ArrayAdapter object
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);

        //set view for adapter
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //set adapter for list view
        mStatus.setAdapter(statusAdapter);

        //get attendance bean to get department and section
        final AttendanceSessionBean attendanceSessionBean = ApplicationContext.getInstance().getAttendanceSessionBean();

        //create a DataBaseAdapter object
        final DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(this);

        //create ArrayList object to store students list
        final ArrayList<StudentBean> studentBeans = ApplicationContext.getInstance().getStudentBeanList();

        //create a ArrayLIst object
        final ArrayList<String> studentIds = new ArrayList<>();

        //for every student
        for (StudentBean studentBean : studentBeans){

            //add student id to list
            studentIds.add(studentBean.getStudentId());
        }

        //initialize String object with 1st value of student list
        studentId = studentBeans.get(0).getStudentId();

        //set OnItemSelectListener to Spinner
        mStudentsId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //ass the selected student to String object
                studentId = studentBeans.get(position).getStudentId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //set OnItemSelectListener to Spinner
        mStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //selected item position is o
                if (position == 0){

                    //assign string object to 'P'
                    status = "P";
                }
                else {

                    //assign string object to 'P'
                    status = "A";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //create ArrayAdapter object
        ArrayAdapter<String> studentIdAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, studentIds);

        //set view for adapter
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //set adapter for list view
        mStudentsId.setAdapter(studentIdAdapter);

        //set OnClickListener to Adapter
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //add or update attendance
                dataBaseAdapter.addOrUpdateAttendance(studentId, attendanceSessionBean, status);
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
