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
import android.widget.Toast;

import com.prasanth.attendance.context.ApplicationContext;
import com.prasanth.attendance.database.DataBaseAdapter;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class StudentAttendanceDates extends AppCompatActivity {

    ListView mDateList;

   ArrayList<String> dateList;

    ArrayAdapter<String> dateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        //initialize ListView
        mDateList = findViewById(R.id.studentList);

        //set visibility for card as gone
        findViewById(R.id.card).setVisibility(View.GONE);

        //get intent and initialize it to StringTokenizer object
        StringTokenizer stringTokenizer = new StringTokenizer(getIntent().getStringExtra("studentId")," ");

        //get student id from StringTokenizer object
        String studentId = stringTokenizer.nextToken();

        //set title for Activity
        setTitle("Attendance of "+studentId);

        //create ArrayList object
        dateList = new DataBaseAdapter(this).getStudentAttendanceDates(ApplicationContext.getInstance().getAttendanceSessionBean(),studentId);

        //check for null
        if (dateList == null){

            //show message
            Toast.makeText(getApplicationContext(),"No Data Found", Toast.LENGTH_LONG).show();

            //finish current activity
            finish();

            return;
        }

        //list size is 0
        if (dateList.size() == 0){

            //show message
            Toast.makeText(getApplicationContext(),"No Data Found", Toast.LENGTH_LONG).show();

            //finish current activity
            finish();

            return;
        }

        //create ArrayAdapter object
        dateAdapter = new ArrayAdapter<>(this, R.layout.view_student_attendance, R.id.student, dateList);

        //set adapter to ListView
        mDateList.setAdapter(dateAdapter);
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
                for (String visibleItem : dateList){
                    //contains search bar string
                    if (visibleItem.toLowerCase().contains(s.toLowerCase())){
                        //add item to list
                        visibleList.add(visibleItem);
                    }
                }
                //initialize Adapter
                dateAdapter = new ArrayAdapter<>(StudentAttendanceDates.this, R.layout.view_student_attendance, R.id.student, visibleList);

                //set Adapter to ListView
                mDateList.setAdapter(dateAdapter);

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
