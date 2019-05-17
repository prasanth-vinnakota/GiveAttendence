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
import android.widget.Spinner;
import android.widget.Toast;

import com.prasanth.attendance.beans.StudentBean;
import com.prasanth.attendance.context.ApplicationContext;
import com.prasanth.attendance.database.DataBaseAdapter;

import java.util.ArrayList;

public class ViewStudentDetailsActivity extends AppCompatActivity {

    private Spinner mSelectStudentDepartment;
    private Spinner mSelectStudentSection;

    private String selectedStudentDepartment = "IT";

    private int selectedStudentSection = 1;

    private Integer[] sections = new Integer[]{1, 2, 3, 4};

    private String[] departments = new String[]{"IT", "CSE", "CIVIL", "MEC", "CHEM", "EEE", "ECE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_details);

        //initialize Button
        Button mSubmitStudentDepartmentAndSection = findViewById(R.id.submitStudentDepartmentAndSection);
        Button mViewAllStudent = findViewById(R.id.viewAllStudents);

        //initialize Spinners
        mSelectStudentDepartment =findViewById(R.id.selectStudentDepartment);
        mSelectStudentSection =findViewById(R.id.selectStudentSection);

        //create a ArrayAdapter object
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departments);

        //set layout for ArrayAdapter object
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //set adapter to Spinner object
        mSelectStudentDepartment.setAdapter(departmentAdapter);

        //set OnItemSelectListener to Spinner
        mSelectStudentDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //get selected department
                selectedStudentDepartment = mSelectStudentDepartment.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //create a ArrayAdapter object
        ArrayAdapter<Integer> sectionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sections);

        //set layout for ArrayAdapter object
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //set adapter to Spinner object
        mSelectStudentSection.setAdapter(sectionAdapter);

        //set OnItemSelectedListener to Spinner
        mSelectStudentSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //get section
                selectedStudentSection = Integer.parseInt(mSelectStudentSection.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //set OnClickListener to Button
        mSubmitStudentDepartmentAndSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create a DataBaseAdapter object
                DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(ViewStudentDetailsActivity.this);

                //get list of students of selected department and section
                ArrayList<StudentBean> studentBeans = dataBaseAdapter.getAllStudentByDepartmentAndSection(selectedStudentDepartment, selectedStudentSection);

                //checking for null
                if (studentBeans == null){

                    //show message
                    Toast.makeText(ViewStudentDetailsActivity.this,"No Data Found", Toast.LENGTH_SHORT).show();

                    return;
                }

                //set list to application context
                ApplicationContext.getInstance().setStudentBeanList(studentBeans);

                //start ViewStudentByDepartmentAndSection Activity
                startActivity(new Intent(ViewStudentDetailsActivity.this, ViewStudentActivity.class));

            }
        });

        mViewAllStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create a DataBaseAdapter object
                DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(ViewStudentDetailsActivity.this);

                //get list of students of selected department and section
                ArrayList<StudentBean> studentBeans = dataBaseAdapter.getAllStudentsDetails();

                //checking for null
                if (studentBeans == null){

                    //show message
                    Toast.makeText(ViewStudentDetailsActivity.this,"No Data Found", Toast.LENGTH_SHORT).show();

                    return;
                }

                //checking for empty list
                if (studentBeans.size() == 0){

                    //show message
                    Toast.makeText(ViewStudentDetailsActivity.this,"No Data Found", Toast.LENGTH_SHORT).show();

                    return;
                }

                //get list of students of selected department and section
                ApplicationContext.getInstance().setStudentBeanList(studentBeans);

                //start ViewStudentByDepartmentAndSection Activity
                startActivity(new Intent(ViewStudentDetailsActivity.this, ViewStudentActivity.class));

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
