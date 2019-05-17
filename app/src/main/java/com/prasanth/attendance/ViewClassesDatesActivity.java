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
import android.widget.ListView;
import android.widget.Toast;

import com.prasanth.attendance.beans.AttendanceBean;
import com.prasanth.attendance.beans.AttendanceSessionBean;
import com.prasanth.attendance.context.ApplicationContext;
import com.prasanth.attendance.database.DataBaseAdapter;

import java.util.ArrayList;


public class ViewClassesDatesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        //initialize ListView
        ListView mDateList = findViewById(R.id.studentList);

        //set visibility for card as gone
        findViewById(R.id.card).setVisibility(View.GONE);

        //create a object for DataBaseAdapter
        final DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(this);

        //get attendance session bean from ApplicationContext
        final AttendanceSessionBean attendanceSessionBean = ApplicationContext.getInstance().getAttendanceSessionBean();

        //get date list from ApplicationContext
        final ArrayList<String> dateList = ApplicationContext.getInstance().getStringArrayList();

        //create ArrayAdapter object
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(this, R.layout.view_student_attendance, R.id.student, dateList);

        //set adapter to ListView
        mDateList.setAdapter(dateAdapter);

        //if list is empty
        if (dateList.size() == 0) {

            //show message
            Toast.makeText(getApplicationContext(), "No Classes Taken Yet", Toast.LENGTH_LONG).show();
        }

        //set OnItemClickListener to ListView
        mDateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //if title is selected
                if (position == 0)
                    return;

                //create AttendanceSessionBean object
                AttendanceSessionBean attendanceSessionBean1 = new AttendanceSessionBean();

                //set AttendanceSessionBean object variables
                attendanceSessionBean1.setAttendanceSessionFacultyId(attendanceSessionBean.getAttendanceSessionFacultyId());
                attendanceSessionBean1.setAttendanceSessionDepartment(attendanceSessionBean.getAttendanceSessionDepartment());
                attendanceSessionBean1.setAttendanceSessionSection(attendanceSessionBean.getAttendanceSessionSection());
                attendanceSessionBean1.setAttendanceSessionDate(dateList.get(position));
                attendanceSessionBean1.setAttendanceSessionSubject(attendanceSessionBean.getAttendanceSessionSubject());
                attendanceSessionBean1.setAttendanceSessionPurpose("ADD ATTENDANCE");

                //get attendance from database
                ArrayList<AttendanceBean> attendanceBeans = dataBaseAdapter.getAttendance(attendanceSessionBean1);

                //check for AttendanceBean list size
                if (attendanceBeans.size() == 0){

                    //show message
                    Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();

                    return;
                }

                //set AttendanceSessionBean to ApplicationContext
                ApplicationContext.getInstance().setAttendanceSessionBean(attendanceSessionBean1);

                //set AttendanceBean to ApplicationContext
                ApplicationContext.getInstance().setAttendanceBeanList(attendanceBeans);

                //create Intent object to ViewAttendanceByDateActivity
                Intent intent = new Intent(ViewClassesDatesActivity.this, ViewAttendanceByDateActivity.class);

                //send date to intent
                intent.putExtra("date", dateList.get(position));

                //start ViewAttendanceByDateActivity
                startActivity(intent);
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
