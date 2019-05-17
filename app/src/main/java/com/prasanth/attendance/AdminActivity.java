package com.prasanth.attendance;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.prasanth.attendance.beans.AttendanceSessionBean;
import com.prasanth.attendance.context.ApplicationContext;
import com.prasanth.attendance.database.DataBaseAdapter;

import java.util.ArrayList;


public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //initializing Button
        Button mAddStudent = findViewById(R.id.addStudent);
        Button mViewStudentDetails = findViewById(R.id.viewStudentDetails);
        Button mViewCondOrDetained = findViewById(R.id.viewCondOrDetained);
        Button mAddFaculty = findViewById(R.id.addFaculty);
        Button mViewFacultyDetails = findViewById(R.id.viewFacultyDetails);
        Button mViewLog = findViewById(R.id.viewLog);
        Button mViewSelectedLog = findViewById(R.id.viewSelectedLog);
        Button mChangeUserNameAndPassword = findViewById(R.id.changeUsernameAndPassword);
        Button mClearData = findViewById(R.id.clearData);
        Button mLogout = findViewById(R.id.logout);

        //set OnClickListener for Button
        mAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //start AddStudentActivity
                startActivity(new Intent(AdminActivity.this, AddStudentActivity.class));
            }
        });

        //set OnClickListener for Button
        mViewStudentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //start ViewStudentDetailsActivity
                startActivity(new Intent(AdminActivity.this, ViewStudentDetailsActivity.class));

            }
        });

        //set OnClickListener for Button
        mViewCondOrDetained.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create a AlertDialog.Builder object
                final AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);

                //set Title
                builder.setTitle("Enter Subject");

                //set icon for builder
                builder.setIcon(R.mipmap.subject);

                //set views to builder
                final EditText subject = new EditText(AdminActivity.this);
                subject.setHint("Subject Name");
                subject.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                builder.setView(subject);

                //set Button
                builder.setPositiveButton("Detained List", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //check subject is entered or not
                        if (subject.getText().toString().equals("")) {

                            //show message
                            Toast.makeText(getApplicationContext(), "You Need To Enter Subject", Toast.LENGTH_LONG).show();

                            return;
                        }

                        //create DataBaseAdapter class object
                        DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(AdminActivity.this);

                        //get student list
                        ArrayList<String> list = dataBaseAdapter.getDetainedStudentsList(subject.getText().toString().toUpperCase());

                        //checking for NullPointer
                        if (list == null){

                            //show message
                            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //students available
                        if (list.size() == 1) {

                            //show message
                            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //set list to ApplicationContext
                        ApplicationContext.getInstance().setStringArrayList(list);

                        //create Intent object to CondonationOrDetainedList
                        Intent intent = new Intent(AdminActivity.this, CondonationOrDetainedList.class);

                        //send title to Activity
                        intent.putExtra("title", "Detained Students List");

                        //start CondonationOrDetainedList Activity
                        startActivity(intent);
                    }
                });

                //set Button
                builder.setNegativeButton("Condonation List", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //check subject is entered or not
                        if (subject.getText().toString().equals("")) {

                            //show message
                            Toast.makeText(getApplicationContext(), "You Need To Enter Subject", Toast.LENGTH_LONG).show();

                            return;
                        }

                        //create DataBaseAdapter class object
                        DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(AdminActivity.this);

                        //get student list
                        ArrayList<String> list = dataBaseAdapter.getCondonationStudentsList(subject.getText().toString().toUpperCase());

                        //checking for NullPointer
                        if (list == null) {

                            //show message
                            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //students available
                        if (list.size() == 1) {

                            //show message
                            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //set list to ApplicationContext
                        ApplicationContext.getInstance().setStringArrayList(list);

                        //create Intent object to CondonationOrDetainedList
                        Intent intent = new Intent(AdminActivity.this, CondonationOrDetainedList.class);

                        //send title to Activity
                        intent.putExtra("title", "Condonation Students List");

                        //start CondonationOrDetainedList Activity
                        startActivity(intent);
                    }
                });

                //create and show dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        //set OnClickListener for Button
        mAddFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //start AddFacultyActivity
                startActivity(new Intent(AdminActivity.this, AddFacultyActivity.class));
            }
        });

        //set OnClickListener for Button
        mViewFacultyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //start ViewFacultyDetailsActivity
                startActivity(new Intent(AdminActivity.this, ViewFacultyDetailsActivity.class));
            }
        });

        //set OnClickListener for Button
        mViewLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create DataBaseAdapter object
                DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(AdminActivity.this);

                //get attendance session list from database
                ArrayList<AttendanceSessionBean> attendanceSessionBeans = dataBaseAdapter.getAllAttendanceSessions();

                //no attendance session beans are available
                if (attendanceSessionBeans == null) {

                    //show message
                    Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    return;
                }

                //no attendance session beans are available
                if (attendanceSessionBeans.size() == 0) {

                    //show message
                    Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    return;
                }

                //set attendance bean list
                ApplicationContext.getInstance().setAttendanceSessionBeanList(attendanceSessionBeans);

                //start ViewLogActivity
                startActivity(new Intent(AdminActivity.this, ViewLogActivity.class));
            }
        });

        //set OnClickListener for Button
        mViewSelectedLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create AlertDialog.Builder
                final AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);

                //set Title to builder
                builder.setTitle("Enter Faculty ID");

                //set icon to builder
                builder.setIcon(R.mipmap.id);

                //create EditText
                final EditText mFacultyId = new EditText(AdminActivity.this);

                //set input type
                mFacultyId.setInputType(InputType.TYPE_CLASS_NUMBER);

                //add EditText to Builder
                builder.setView(mFacultyId);

                //set Button to Builder
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //check for faculty id
                        if (mFacultyId.getText().toString().equals("")) {

                            //show message
                            Toast.makeText(getApplicationContext(), "Enter Faculty ID", Toast.LENGTH_LONG).show();

                            return;
                        }

                        //get faculty id
                        int faculty_id = Integer.parseInt(mFacultyId.getText().toString());

                        //create DataBaseAdapter object
                        DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(AdminActivity.this);

                        //get attendance session list from database
                        ArrayList<AttendanceSessionBean> attendanceSessionBeans = dataBaseAdapter.getAttendanceSessionsByFacultyId(faculty_id);

                        //no attendance session beans are available
                        if (attendanceSessionBeans == null) {

                            //show message
                            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //no attendance session beans are available
                        if (attendanceSessionBeans.size() == 0) {

                            //show message
                            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //set attendance session bean list
                        ApplicationContext.getInstance().setAttendanceSessionBeanList(attendanceSessionBeans);

                        //start ViewLogActivity
                        startActivity(new Intent(AdminActivity.this, ViewLogActivity.class));
                    }
                });

                //create and show builder
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //set OnClickListener for Button
        mChangeUserNameAndPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a SharedPreferences Object
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("login_credentials", MODE_PRIVATE);

                //create a Editor for SharedPreferences object
                final SharedPreferences.Editor editor = sharedPreferences.edit();

                //create AlertDialog.Builder
                final AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);

                //set Title to builder
                builder.setTitle("Change Username And Password");

                //set icon for builder
                builder.setIcon(R.mipmap.change_password);

                //create Two EditText and add to LinearLayout object
                final EditText mUsername = new EditText(AdminActivity.this);
                final EditText mPassword = new EditText(AdminActivity.this);
                mUsername.setHint("username");
                mPassword.setHint("password");

                //create a linear layout
                LinearLayout linearLayout = new LinearLayout(AdminActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                //set parameter to EditText
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mUsername.setLayoutParams(layoutParams);
                mPassword.setLayoutParams(layoutParams);

                //add Edit text to LinearLayout
                linearLayout.addView(mUsername);
                linearLayout.addView(mPassword);

                //add LinearLayout object to Builder View
                builder.setView(linearLayout);

                //set Button
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //get new username and password
                        String username = mUsername.getText().toString();
                        String password = mPassword.getText().toString();

                        //check for empty string
                        if (username.equals("") || password.equals("")) {

                            //show error
                            Toast.makeText(getApplicationContext(), "Username and Password both Required", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //add new username and password to SharedPreferences File
                        editor.remove("loginName");
                        editor.putString("loginName", username);
                        editor.remove("loginPassword");
                        editor.putString("loginPassword", password);

                        editor.apply();
                        //show message
                        Toast.makeText(getApplicationContext(), "Username and Password are Updated", Toast.LENGTH_LONG).show();

                        //dismiss dialog
                        dialog.dismiss();
                    }
                });

                //create and show builder
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //set OnClickListener to Button
        mClearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create AlertDialog.Builder
                final AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);

                //set Title to builder
                builder.setTitle("Clear Data?");

                //set icon to builder
                builder.setIcon(R.mipmap.warning);

                //set Message
                builder.setMessage("This will clear your app's data including Admin's username and password.\nThis action is irreversible");

                //set cancelable for builder as true
                builder.setCancelable(true);

                //set Button
                builder.setNegativeButton("without student details", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //clear database
                        if (new DataBaseAdapter(AdminActivity.this).clearDatabaseWithoutStudentDetails()) {

                            //get preferences
                            SharedPreferences preferences = getSharedPreferences("login_credentials", MODE_PRIVATE);

                            //set editor to preferences
                            SharedPreferences.Editor editor = preferences.edit();

                            //remove values
                            editor.remove("loginName");
                            editor.remove("loginPassword");

                            //commit
                            editor.apply();

                            //show message
                            Toast.makeText(getApplicationContext(), "App's data cleared", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                //set Button
                builder.setPositiveButton("with student details", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //clear database
                        if (new DataBaseAdapter(AdminActivity.this).clearDatabaseWithStudentDetails()) {

                            //get preferences
                            SharedPreferences preferences = getSharedPreferences("login_credentials", MODE_PRIVATE);

                            //set editor to preferences
                            SharedPreferences.Editor editor = preferences.edit();

                            //remove values
                            editor.remove("loginName");
                            editor.remove("loginPassword");

                            //commit
                            editor.apply();

                            //show message
                            Toast.makeText(getApplicationContext(), "App's data cleared", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                //create and show dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //set OnClickListener for Button
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //start LoginActivity
                startActivity(new Intent(AdminActivity.this, LoginActivity.class));

                //finish current activity
                finish();
            }
        });

    }

    //this method will shuts the app when back button is pressed
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
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
