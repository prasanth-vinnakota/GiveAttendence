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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.prasanth.attendance.beans.StudentBean;
import com.prasanth.attendance.database.DataBaseAdapter;

public class AddStudentActivity extends AppCompatActivity {

    private EditText mStudentId;
    private EditText mStudentFirstName;
    private EditText mStudentLastName;
    private EditText mStudentTel;

    private Spinner mStudentDepartment;
    private Spinner mStudentSection;

    private Integer[] sections = new Integer[]{1, 2, 3, 4};

    private String[] departments = new String[]{"IT", "CSE", "CIVIL", "MEC", "CHEM", "EEE", "ECE"};

    private String studentDepartment = "IT";

    private int studentSection = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        //initialize EditText
        mStudentId = findViewById(R.id.studentID);
        mStudentFirstName = findViewById(R.id.studentFirstName);
        mStudentLastName = findViewById(R.id.studentLastName);
        mStudentTel = findViewById(R.id.studentTel);

        //initialize Spinner
        mStudentDepartment = findViewById(R.id.studentDepartment);
        mStudentSection = findViewById(R.id.studentSection);

        //initialize Button
        Button mRegisterStudent = findViewById(R.id.registerStudent);

        //create a ArrayAdapter object
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departments);

        //set layout for ArrayAdapter object
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //set adapter to Spinner object
        mStudentDepartment.setAdapter(departmentAdapter);

        //set OnItemSelectListener to Spinner
        mStudentDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //get department of student
                studentDepartment = mStudentDepartment.getSelectedItem().toString();
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
        mStudentSection.setAdapter(sectionAdapter);

        //set OnItemSelectListener to Spinner
        mStudentSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //get section of student
                studentSection = Integer.parseInt(mStudentSection.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //set OnClickListener to Button
        mRegisterStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get student details from EditText
                String id = mStudentId.getText().toString();
                String firstname = mStudentFirstName.getText().toString();
                String lastname = mStudentLastName.getText().toString();
                String tel = mStudentTel.getText().toString();

                //checking for blank EditText
                if (id.equals("")) {

                    //set error message
                    mStudentId.setError("Required Field");

                    return;
                }

                if (firstname.equals("")) {

                    //set error message
                    mStudentFirstName.setError("Required Field");

                    return;
                }

                if (lastname.equals("")) {

                    //set error message
                    mStudentLastName.setError("Required Field");

                    return;
                }

                if (tel.equals("")) {

                    //set error message
                    mStudentTel.setError("Required Field");

                    return;
                }

                //create a StudentBean Object
                StudentBean studentBean = new StudentBean();

                //set fields of StudentBean object
                studentBean.setStudentId(id);
                studentBean.setStudentFirstname(firstname);
                studentBean.setStudentLastname(lastname);
                studentBean.setStudentTel(tel);
                studentBean.setStudentDepartment(studentDepartment);
                studentBean.setStudentSection(studentSection);

                //add student to database
                DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(AddStudentActivity.this);

                if (dataBaseAdapter.addStudent(studentBean)) {
                    //show success message
                    Toast.makeText(AddStudentActivity.this, "Student Added To Database", Toast.LENGTH_SHORT).show();

                    reset();
                } else {

                    //show error message
                    Toast.makeText(getApplicationContext(), "Student Already Exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void reset() {

        //reset EditTexts
        mStudentId.setText("");
        mStudentFirstName.setText("");
        mStudentLastName.setText("");
        mStudentTel.setText("");
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
