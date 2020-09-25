package com.prasanth.attendance;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.prasanth.attendance.beans.FacultyBean;
import com.prasanth.attendance.database.DataBaseAdapter;

public class UpdateFacultyActivity extends AppCompatActivity {

    private EditText mFacultyFirstname;
    private EditText mFacultyLastname;
    private EditText mFacultyTel;

    private int faculty_id;

    private DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_facutly);

        //get faculty id from intent
        faculty_id = getIntent().getIntExtra("facultyId",0);

        //get faculty details from database
        FacultyBean facultyBean = dataBaseAdapter.getFacultyDetailsById(faculty_id);

        //initialize EditText
        mFacultyFirstname = findViewById(R.id.facultyFirstname);
        mFacultyFirstname.setText(facultyBean.getFacultyFirstName());
        mFacultyLastname = findViewById(R.id.facultyLastname);
        mFacultyLastname.setText(facultyBean.getFacultyLastname());
        mFacultyTel = findViewById(R.id.facultyTelNo);
        mFacultyTel.setText(facultyBean.getFaultyTel());
        EditText mFacultyUsername = findViewById(R.id.facultyUsername);
        String username = "Username : "+facultyBean.getFacultyUsername();
        mFacultyUsername.setText(username);
        mFacultyUsername.setEnabled(false);

        //initialize TextView
        TextView mFacultyId = findViewById(R.id.facultyId);
        String s = "ID : "+faculty_id;
        mFacultyId.setText(s);

        //initialize Button
        Button mUpdate = findViewById(R.id.updateFaculty);

        //set OnClickListener to Button
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get values from View
                String facultyFirstName = mFacultyFirstname.getText().toString();
                String facultyLastName = mFacultyLastname.getText().toString();
                String facultyTel = mFacultyTel.getText().toString();

                //add values to faculty bean
                FacultyBean facultyBean = new FacultyBean();
                facultyBean.setFacultyId(faculty_id);
                facultyBean.setFacultyFirstName(facultyFirstName);
                facultyBean.setFacultyLastName(facultyLastName);
                facultyBean.setFacultyTel(facultyTel);

                //faculty is updated
                if (dataBaseAdapter.updateFaculty(facultyBean)){

                    //show message
                    Toast.makeText(getApplicationContext(), "Faculty Details Updated", Toast.LENGTH_LONG).show();
                }
                //faculty is not updated
                else {

                    //show message
                    Toast.makeText(getApplicationContext(), "Unable to Update Faculty Details", Toast.LENGTH_LONG).show();
                }
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
