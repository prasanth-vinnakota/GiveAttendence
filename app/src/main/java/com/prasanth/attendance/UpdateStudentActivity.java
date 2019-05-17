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

import com.prasanth.attendance.beans.StudentBean;
import com.prasanth.attendance.database.DataBaseAdapter;

public class UpdateStudentActivity extends AppCompatActivity {

    private EditText mStudentFirstName;
    private EditText mStudentLastName;
    private EditText mStudentTel;

    private TextView mStudentID;

    private DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(UpdateStudentActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        //initialize TextView
        mStudentID = findViewById(R.id.studentNo);
        mStudentID.setText(getIntent().getStringExtra("studentId"));

        //get student details
        StudentBean studentBean = dataBaseAdapter.getStudentById(getIntent().getStringExtra("studentId"));

        //initialize and set EditText
        mStudentFirstName = findViewById(R.id.studentFirstname);
        mStudentFirstName.setText(studentBean.getStudentFirstname());
        mStudentLastName = findViewById(R.id.studentLastname);
        mStudentLastName.setText(studentBean.getStudentLastname());
        mStudentTel = findViewById(R.id.studentTelNo);
        mStudentTel.setText(studentBean.getStudentTel());

        //initialize Button
        Button mUpdateStudent = findViewById(R.id.updateStudent);

        //set OnClickListener to Button
        mUpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get values from View
                String studentId = mStudentID.getText().toString();
                String studentFirstName = mStudentFirstName.getText().toString();
                String studentLastName = mStudentLastName.getText().toString();
                String studentTel = mStudentTel.getText().toString();

                //add values to student bean
                StudentBean studentBean = new StudentBean();
                studentBean.setStudentId(studentId);
                studentBean.setStudentFirstname(studentFirstName);
                studentBean.setStudentLastname(studentLastName);
                studentBean.setStudentTel(studentTel);

                //student is updated
                if (dataBaseAdapter.updateStudent(studentBean)) {

                    //show message
                    Toast.makeText(getApplicationContext(), "Student Details Updated", Toast.LENGTH_LONG).show();
                }
                //student is not updated
                else {
                    //shoe message
                    Toast.makeText(getApplicationContext(), "Unable to Update Student Details", Toast.LENGTH_LONG).show();
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
