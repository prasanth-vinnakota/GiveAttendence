package com.prasanth.attendance;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prasanth.attendance.beans.FacultyBean;
import com.prasanth.attendance.database.DataBaseAdapter;


public class AddFacultyActivity extends AppCompatActivity {

    private EditText mFacultyFirstName;
    private EditText mFacultyLastName;
    private EditText mFacultyTel;
    private EditText mFacultyUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        //initialize EditText
        mFacultyFirstName = findViewById(R.id.facultyFirstName);
        mFacultyLastName = findViewById(R.id.facultyLastName);
        mFacultyTel = findViewById(R.id.facultyTel);
        mFacultyUsername = findViewById(R.id.facultyUserName);

        //initialize Button
        Button mRegisterFaculty = findViewById(R.id.registerFaculty);

        //set OnClickListener to Button
        mRegisterFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get Values from Edit Text
                String facultyFirstName = mFacultyFirstName.getText().toString();
                String facultyLastName = mFacultyLastName.getText().toString();
                String facultyTel = mFacultyTel.getText().toString();
                String facultyUsername = mFacultyUsername.getText().toString();

                //check for empty EditText
                if (facultyFirstName.equals("")){
                    //set error message
                    mFacultyFirstName.setError("Required Field");
                    return;
                }

                //check for empty EditText
                if (facultyLastName.equals("")){
                    //set error message
                    mFacultyLastName.setError("Required Field");
                    return;
                }

                //check for empty EditText
                if (facultyTel.equals("")){
                    //set error message
                    mFacultyTel.setError("Required Field");
                    return;
                }

                //check for empty EditText
                if (facultyUsername.equals("")){
                    //set error message
                    mFacultyUsername.setError("Required Field");
                    return;
                }

                //create a FacultyBean object
                FacultyBean facultyBean = new FacultyBean();

                //set Values of FacultyBean object
                facultyBean.setFacultyFirstName(facultyFirstName);
                facultyBean.setFacultyLastName(facultyLastName);
                facultyBean.setFacultyTel(facultyTel);
                facultyBean.setFacultyUsername(facultyUsername);
                facultyBean.setFacultyPassword("");

                //create a DatabaseAdapter object
                DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(AddFacultyActivity.this);

                //add faculty to database
                if (dataBaseAdapter.addFaculty(facultyBean)){

                    //show success message
                    Toast.makeText(getApplicationContext(), "Faculty Registered Successfully", Toast.LENGTH_SHORT).show();

                    //start AdminActivity
                    startActivity(new Intent(AddFacultyActivity.this, AdminActivity.class));

                    //finish Current Activity
                    finish();
                }
                else {
                    //show error message
                    Toast.makeText(getApplicationContext(), "Username Already Exists", Toast.LENGTH_LONG).show();
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
