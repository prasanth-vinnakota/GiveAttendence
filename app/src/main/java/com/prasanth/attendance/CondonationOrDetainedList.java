package com.prasanth.attendance;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.prasanth.attendance.context.ApplicationContext;

import java.util.ArrayList;

public class CondonationOrDetainedList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        //set Title
        setTitle(getIntent().getStringExtra("title"));

        //initialize List View
        ListView mList = findViewById(R.id.studentList);

        //set visibility for card as gone
        findViewById(R.id.card).setVisibility(View.GONE);

        //get list from Application Context
         ArrayList<String> list = ((ApplicationContext)this.getApplicationContext()).getStringArrayList();

         //create ArrayAdapter
         ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.view_student_attendance, R.id.student, list);

         //set Adapter to ListView
         mList.setAdapter(adapter);

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
