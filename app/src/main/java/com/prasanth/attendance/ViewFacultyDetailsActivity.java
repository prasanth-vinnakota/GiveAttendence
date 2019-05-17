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

import com.prasanth.attendance.beans.FacultyBean;
import com.prasanth.attendance.database.DataBaseAdapter;

import java.util.ArrayList;


public class ViewFacultyDetailsActivity extends AppCompatActivity {

    private ArrayList<FacultyBean> facultyBeans;

    final private ArrayList<String> facultyList = new ArrayList<>();

    private ArrayAdapter<String> facultyAdapter;

    private DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        //initialize Views
        ListView mFacultyList = findViewById(R.id.studentList);

        //set visibility for card as gone
        findViewById(R.id.card).setVisibility(View.GONE);

        //get all faculty details from database
        facultyBeans = dataBaseAdapter.getAllFacultiesDetails();

        //check for null
        if (facultyBeans == null){

            //show message
            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

            //finish current activity
            finish();

            return;
        }

        //no faculty
        if (facultyBeans.size() == 0) {

            //show message
            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

            //finish current activity
            finish();

            return;
        }

        //for every FacultyBean
        for (FacultyBean facultyBean : facultyBeans) {

            //get name
            String string = facultyBean.getFacultyFirstName() + " " + facultyBean.getFacultyLastname();

            //add to list
            facultyList.add(string);
        }

        //initialize Adapter
        facultyAdapter = new ArrayAdapter<>(this, R.layout.view_student_attendance, R.id.student, facultyList);

        //set Adapter to List
        mFacultyList.setAdapter(facultyAdapter);

        //set OnItemLongClick to ListView
        mFacultyList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                //create a AlertBuilder to Show Message
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewFacultyDetailsActivity.this);

                //set Builder Title
                builder.setTitle("Delete " + facultyBeans.get(position).getFacultyFirstName() + " " + facultyBeans.get(position).getFacultyLastname() + "?");

                //set icon for builder
                builder.setIcon(R.mipmap.warning);

                //set Button for Builder
                builder.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //exit dialog
                        dialog.cancel();
                    }
                });
                //set Button for Builder
                builder.setPositiveButton("Yup", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //remove selected faculty from database
                        dataBaseAdapter.deleteFaculty(facultyBeans.get(position).getFacultyId());

                        //show message
                        Toast.makeText(ViewFacultyDetailsActivity.this, facultyList.get(position) + " is removed from database", Toast.LENGTH_SHORT).show();

                        //remove faculty from list
                        facultyList.remove(position);

                        //notify Adapter
                        facultyAdapter.notifyDataSetChanged();
                        facultyAdapter.notifyDataSetInvalidated();

                        //get all faculty details
                        facultyBeans = dataBaseAdapter.getAllFacultiesDetails();

                        //for every faculty
                        for (FacultyBean facultyBean : facultyBeans) {

                            //get name
                            String string = facultyBean.getFacultyFirstName() + " " + facultyBean.getFacultyLastname();

                            //add to list
                            facultyList.add(string);
                        }
                    }
                });

                //build and show builder
                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
            }
        });

        //add OnItemClickListener
        mFacultyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //create a intent object to UpdateFacultyActivity
                Intent intent = new Intent(ViewFacultyDetailsActivity.this, UpdateFacultyActivity.class);

                //get the FacultyBean object to selected Student
                FacultyBean facultyBean = facultyBeans.get(position);

                //send student information to intent activity
                intent.putExtra("facultyId", facultyBean.getFacultyId());

                //start UpdateStudentActivity
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
