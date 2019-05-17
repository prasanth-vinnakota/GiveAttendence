package com.prasanth.attendance;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.prasanth.attendance.beans.AttendanceSessionBean;
import com.prasanth.attendance.beans.FacultyBean;
import com.prasanth.attendance.context.ApplicationContext;
import com.prasanth.attendance.database.DataBaseAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private EditText mUserName;
    private EditText mPassword;
    private Spinner mLoginAs;
    private String user;
    private String[] users = new String[] {"ADMIN", "FACULTY"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initiate EditText
        mUserName = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);

        //initiate Button
        Button mLogin = findViewById(R.id.login);

        //initiate Spinner
        mLoginAs = findViewById(R.id.login_as);

        //assign users array to ArrayAdapter
        ArrayAdapter<String> user_adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,users);

        //set layout for ArrayAdapter
        user_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //set adapter to Spinner
        mLoginAs.setAdapter(user_adapter);

        //set OnItemSelectListener to Spinner
        mLoginAs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //getChild of Spinner
                parent.getChildAt(position);

                //assign selected item to String object
                user = mLoginAs.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //set OnClickListener to Button
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //user is CR
                if (user.equals(users[0])){

                    //get data from EditText
                    String username = mUserName.getText().toString();
                    String password = mPassword.getText().toString();

                    //checking if username is empty
                    if (username.equals("")){

                        //show error
                        mUserName.setError("Invalid user Name");

                        return;
                    }


                    //create a SharedPreferences Object
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("login_credentials",Context.MODE_PRIVATE);

                    //no file exists then create 'cr' as username and '' as password
                    if (sharedPreferences.getString("loginName", null) == null){

                        //create AlertDialog.Builder object
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                        //set title
                        builder.setTitle("Get Started");

                        //set icon
                        builder.setIcon(R.mipmap.start);

                        //set message
                        builder.setMessage("username : admin\npassword : ");

                        //set Button
                        builder.setPositiveButton("Gotcha", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        //build and show builder
                        AlertDialog dialog = builder.create();
                        dialog.show();

                        //get editor for shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        //put loginName to shared preferences
                        editor.putString("loginName","admin");

                        //commit
                        editor.apply();
                    }

                    if (sharedPreferences.getString("loginPassword", null) == null){
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("loginPassword","");

                        editor.apply();
                    }
                    //login credentials are valid
                    if (username.equals(sharedPreferences.getString("loginName", null)) && password.equals(sharedPreferences.getString("loginPassword", null))) {

                        //start AdminActivity
                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));

                        //finish current Activity
                        finish();

                        //show message
                        Toast.makeText(getApplicationContext(), "Logging in", Toast.LENGTH_SHORT).show();
                    }
                    //login credentials are invalid
                    else
                        //show message
                        Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
                //user is Faculty
                else{

                    //get data from EditText
                    String username = mUserName.getText().toString();
                    String password = mPassword.getText().toString();

                    //checking if username is empty
                    if (username.equals("")){

                        //show error
                        mUserName.setError("Invalid user Name");

                        return;
                    }

                    //create object for DataBaseAdapter
                    DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(LoginActivity.this);

                    //create object for FacultyBean and validate username and password
                    FacultyBean facultyBean = dataBaseAdapter.validateFaculty(username, password);

                    //FacultyBean object is assigned
                    if (facultyBean != null){

                        //create a AttendanceSession object
                        AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();

                        //set values to AttendanceSessionBean object
                        attendanceSessionBean.setAttendanceSessionFacultyId(facultyBean.getFacultyId());
                        attendanceSessionBean.setAttendanceSessionDepartment(" ");
                        attendanceSessionBean.setAttendanceSessionSection(0);
                        attendanceSessionBean.setAttendanceSessionDate(" ");
                        attendanceSessionBean.setAttendanceSessionSubject(" ");
                        attendanceSessionBean.setAttendanceSessionPurpose("LOGIN");
                        attendanceSessionBean.setAttendanceSessionEventDate(new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date()));

                        //add session to log
                        dataBaseAdapter.addAttendanceSession(attendanceSessionBean);

                        //start AddAttendSessionActivity
                        startActivity(new Intent(LoginActivity.this, AddAttendanceSessionActivity.class));

                        //finish current Activity
                        finish();

                        //set FacultyBean object to current Activity
                        ((ApplicationContext)LoginActivity.this.getApplicationContext()).setFacultyBean(facultyBean);

                        //show message
                        Toast.makeText(getApplicationContext(), "Logging in", Toast.LENGTH_SHORT).show();
                    }
                    //FacultyBean object is not assigned
                    else
                        //show message
                        Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        //start MainActivity
        startActivity(new Intent(LoginActivity.this,MainActivity.class));

        //finish current activity
        finish();
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
