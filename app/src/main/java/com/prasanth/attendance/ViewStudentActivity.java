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

import com.prasanth.attendance.beans.StudentBean;
import com.prasanth.attendance.context.ApplicationContext;
import com.prasanth.attendance.database.DataBaseAdapter;

import java.util.ArrayList;

public class ViewStudentActivity extends AppCompatActivity {

    private ArrayList<StudentBean> studentBeans;

    private ArrayAdapter<String> studentAdapter;

    private String selectedStudentDepartment = "IT";

    private int selectedStudentSection = 1;

    private DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        //set visibility for card as gone
        findViewById(R.id.card).setVisibility(View.GONE);

        //initialize ListView
        ListView mStudentList = findViewById(R.id.studentList);

        //create ArrayList of String
        final ArrayList<String> studentList = new ArrayList<>();

        //set list title
        studentList.add("STUDENT ID");

        //get Students Details
        studentBeans = ApplicationContext.getInstance().getStudentBeanList();

        //student list is empty
        if (studentBeans.size() == 0) {

            //show message
            Toast.makeText(ViewStudentActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();

            //finish current activity
            finish();

            return;
        }

        //for every student
        for (StudentBean studentBean : studentBeans) {

            //student ID
            String ID = studentBean.getStudentId();

            //add student id to list
            studentList.add(ID);
        }

        //set Adapter with elements
        studentAdapter = new ArrayAdapter<>(this, R.layout.view_student_attendance, R.id.student, studentList);

        //add adapter to ListView
        mStudentList.setAdapter(studentAdapter);

        //set OnItemClickListener to ListView
        mStudentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //student list is empty
                if (studentBeans.size() == 0)
                    return;

                //if title is selected
                if (position == 0)
                    return;

                //create a intent object to UpdateStudentActivity
                Intent intent = new Intent(ViewStudentActivity.this, UpdateStudentActivity.class);

                //get the StudentBean object to selected Student
                StudentBean studentBean = studentBeans.get(position - 1);

                //send student information to intent activity
                intent.putExtra("studentId", studentBean.getStudentId());

                //start UpdateStudentActivity
                startActivity(intent);
            }
        });

        //set OnItemLongClickListener to ListView
        mStudentList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                //student list is empty
                if (studentBeans.size() == 0)
                    return false;

                //if title is selected
                if (position == 0)
                    return false;

                //create a AlertBuilder to Show Message
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewStudentActivity.this);

                //set Builder Title
                builder.setTitle("Delete " + (studentBeans.get(position - 1)).getStudentId() + "?");

                //set icon for builder
                builder.setIcon(R.mipmap.warning);

                //set Button
                builder.setPositiveButton("Yup", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //remove selected student
                        studentList.remove(position);

                        //notify Adapter
                        studentAdapter.notifyDataSetChanged();
                        studentAdapter.notifyDataSetInvalidated();

                        //remove selected student from database
                        dataBaseAdapter.deleteStudent((studentBeans.get(position - 1)).getStudentId());

                        //show message
                        Toast.makeText(ViewStudentActivity.this, (studentBeans.get(position)).getStudentId() + " is removed from database", Toast.LENGTH_SHORT).show();

                        //get students ID
                        studentBeans = dataBaseAdapter.getAllStudentByDepartmentAndSection(selectedStudentDepartment, selectedStudentSection);

                        //for every student
                        for (StudentBean studentBean : studentBeans) {

                            //student ID
                            String ID = studentBean.getStudentId();

                            //add student id to list
                            studentList.add(ID);
                        }
                    }
                });

                //set Button for Builder
                builder.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //cancel dialog
                        dialog.cancel();
                    }
                });

                //show dialog
                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
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
