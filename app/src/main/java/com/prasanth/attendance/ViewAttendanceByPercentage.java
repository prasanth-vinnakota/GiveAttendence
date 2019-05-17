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

import com.prasanth.attendance.context.ApplicationContext;

import java.util.ArrayList;

public class ViewAttendanceByPercentage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        //initialize ListView
        ListView mListView = findViewById(R.id.studentList);

        //set visibility for card as gone
        findViewById(R.id.card).setVisibility(View.GONE);

        //get student list from database
        final ArrayList<String> studentList = ApplicationContext.getInstance().getStringArrayList();

        //create a ArrayAdapter object
        ArrayAdapter<String> studentAdapter = new ArrayAdapter<>(ViewAttendanceByPercentage.this, R.layout.view_student_attendance, R.id.student, studentList);

        //set adapter to ListView
        mListView.setAdapter(studentAdapter);

        //set OnItemClickListener to ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //if title is selected
                if (position == 0)
                    return;

                //create a intent to StudentAttendanceDate activity
                Intent intent = new Intent(ViewAttendanceByPercentage.this, StudentAttendanceDates.class);

                //set String object to intent
                intent.putExtra("studentId", studentList.get(position));

                //start StudentAttendanceDates activity
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
