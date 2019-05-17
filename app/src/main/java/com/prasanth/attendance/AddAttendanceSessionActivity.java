package com.prasanth.attendance;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.prasanth.attendance.beans.AttendanceBean;
import com.prasanth.attendance.beans.AttendanceSessionBean;
import com.prasanth.attendance.beans.FacultyBean;
import com.prasanth.attendance.beans.StudentBean;
import com.prasanth.attendance.context.ApplicationContext;
import com.prasanth.attendance.database.DataBaseAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddAttendanceSessionActivity extends AppCompatActivity {

    private EditText mDate;
    private EditText mSubject;

    private Spinner mDepartment;
    private Spinner mSection;

    private String department = "IT";

    private int section = 1;
    private int day;
    private int month;
    private int year;

    private Integer[] sections = new Integer[]{1, 2, 3, 4};

    private String[] departments = new String[]{"IT", "CSE", "CIVIL", "MEC", "CHEM", "EEE", "ECE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendance_session);

        //initialize Button
        Button mAddAttendance = findViewById(R.id.addAttendance);
        Button mViewAttendance = findViewById(R.id.viewAttendance);
        Button mViewTotalAttendance = findViewById(R.id.viewTotalAttendance);
        Button mViewAttendanceByPercentage = findViewById(R.id.viewAttendanceByPercentage);
        Button mViewClassesDates = findViewById(R.id.viewClassesDates);
        Button mAddOrUpdateAttendance = findViewById(R.id.addOrUpdateAttendance);
        Button mLogout = findViewById(R.id.logoutFromAddAttendanceSessionActivity);
        Button mChangePassword = findViewById(R.id.changeFacultyPassword);

        //initialize ImageButton
        ImageButton mDateImageButton = findViewById(R.id.dateImageButton);

        //initialize EditText
        mDate = findViewById(R.id.selectDay);
        mDate.setEnabled(false);
        mSubject = findViewById(R.id.subject);

        //get current Date and time
        mDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
        final String eventDate = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());

        //initialize Spinner
        mDepartment = findViewById(R.id.department);
        mSection = findViewById(R.id.section);

        //create a ArrayAdapter object
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departments);

        //set layout for ArrayAdapter object
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //set adapter to Spinner object
        mDepartment.setAdapter(departmentAdapter);

        //set OnItemSelectListener to Spinner
        mDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //get selected department
                department = mDepartment.getSelectedItem().toString();
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
        mSection.setAdapter(sectionAdapter);

        //set OnItemSelectedListener to Spinner
        mSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //get section
                section = Integer.parseInt(mSection.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //get calender instance
        Calendar calendar = Calendar.getInstance();

        //get day, moth and year from instance
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        //set OnClickListener to Button
        mDateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create a DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddAttendanceSessionActivity.this, datePickerListener, year, month, day);

                //show dialog
                datePickerDialog.show();
            }
        });

        //set OnClickListener to Button
        mChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create a AttendanceSession object
                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();

                //get FacultyBean object from ApplicationContext
                final FacultyBean facultyBean = ApplicationContext.getInstance().getFacultyBean();

                //set values to AttendanceSessionBean object
                attendanceSessionBean.setAttendanceSessionFacultyId(facultyBean.getFacultyId());
                attendanceSessionBean.setAttendanceSessionDepartment(" ");
                attendanceSessionBean.setAttendanceSessionSection(0);
                attendanceSessionBean.setAttendanceSessionDate(" ");
                attendanceSessionBean.setAttendanceSessionSubject(" ");
                attendanceSessionBean.setAttendanceSessionPurpose("CHANGE PASSWORD");
                attendanceSessionBean.setAttendanceSessionEventDate(eventDate);

                //create a DataBaseAdapter object and add current session
                final DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(AddAttendanceSessionActivity.this);

                //add session to database
                dataBaseAdapter.addAttendanceSession(attendanceSessionBean);

                //create AlertDialog.Builder
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddAttendanceSessionActivity.this);

                //set Title to builder
                builder.setTitle("Change Password");

                //set icon to builder
                builder.setIcon(R.mipmap.change_password);

                //create Two EditText
                final EditText mOldPassword = new EditText(AddAttendanceSessionActivity.this);
                final EditText mNewPassword = new EditText(AddAttendanceSessionActivity.this);
                mOldPassword.setHint("old password");
                mNewPassword.setHint("new password");

                //create LinearLayout with orientation vertical
                LinearLayout linearLayout = new LinearLayout(AddAttendanceSessionActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                //set parameters to EditText
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mOldPassword.setLayoutParams(layoutParams);
                mNewPassword.setLayoutParams(layoutParams);

                //add EditText to LinearLayout object
                linearLayout.addView(mOldPassword);
                linearLayout.addView(mNewPassword);

                //add LinearLayout object to Builder View
                builder.setView(linearLayout);

                //set Button
                builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //get new username and password
                        String old_password = mOldPassword.getText().toString();
                        String new_password = mNewPassword.getText().toString();

                        if (new_password.equals("")) {

                            //show message
                            Toast.makeText(getApplicationContext(), "You need to enter a New Password", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //change password
                        dataBaseAdapter.changeFacultyPassword(facultyBean.getFacultyId(), old_password, new_password);

                        //dismiss dialog
                        dialog.dismiss();
                    }
                });

                //build builder
                AlertDialog dialog = builder.create();

                //show dialog
                dialog.show();
            }
        });

        //set OnClickListener to Button
        mAddAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check for empty EditText
                if (mSubject.getText().toString().equals("")) {

                    //set error message
                    mSubject.setError("Enter Subject");

                    return;
                }


                //create a AttendanceSession object
                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();

                //get FacultyBean object from ApplicationContext
                final FacultyBean facultyBean = ApplicationContext.getInstance().getFacultyBean();

                //set values to AttendanceSessionBean object
                attendanceSessionBean.setAttendanceSessionFacultyId(facultyBean.getFacultyId());
                attendanceSessionBean.setAttendanceSessionDepartment(department);
                attendanceSessionBean.setAttendanceSessionSection(section);
                attendanceSessionBean.setAttendanceSessionDate(mDate.getText().toString());
                attendanceSessionBean.setAttendanceSessionSubject(mSubject.getText().toString());
                attendanceSessionBean.setAttendanceSessionPurpose("ADD ATTENDANCE");
                attendanceSessionBean.setAttendanceSessionEventDate(eventDate);

                //create a DataBaseAdapter object
                DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(AddAttendanceSessionActivity.this);

                //get student by department and section
                ArrayList<StudentBean> studentBeans = dataBaseAdapter.getAllStudentByDepartmentAndSection(department, section);

                //check size of student bean
                if (studentBeans.size() == 0) {
                    //show message
                    Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    return;
                }

                //add session to log
                int sessionId = dataBaseAdapter.addAttendanceSession(attendanceSessionBean);

                //set StudentBean list to ApplicationContext
                ApplicationContext.getInstance().setStudentBeanList(studentBeans);

                //create a Intent object to AddAttendanceActivity
                Intent intent = new Intent(AddAttendanceSessionActivity.this, AddAttendanceActivity.class);

                //send session to AddAttendanceActivity
                intent.putExtra("sessionId", sessionId);

                //start AddAttendanceActivity
                startActivity(intent);

            }
        });

        //set OnClickListener to Button
        mViewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check for empty EditText
                if (mSubject.getText().toString().equals("")) {

                    //set error message
                    mSubject.setError("Enter Subject");

                    return;
                }

                //create a AttendanceSession object
                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();

                //get FacultyBean object from ApplicationContext
                final FacultyBean facultyBean = ApplicationContext.getInstance().getFacultyBean();

                //set values to AttendanceSessionBean object
                attendanceSessionBean.setAttendanceSessionFacultyId(facultyBean.getFacultyId());
                attendanceSessionBean.setAttendanceSessionDepartment(department);
                attendanceSessionBean.setAttendanceSessionSection(section);
                attendanceSessionBean.setAttendanceSessionDate(mDate.getText().toString());
                attendanceSessionBean.setAttendanceSessionSubject(mSubject.getText().toString());
                attendanceSessionBean.setAttendanceSessionPurpose("VIEW ATTENDANCE");
                attendanceSessionBean.setAttendanceSessionEventDate(eventDate);

                //create a DataBaseAdapter object
                DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(AddAttendanceSessionActivity.this);

                //get attendance from database
                ArrayList<AttendanceBean> attendanceBeans = dataBaseAdapter.getAttendance(attendanceSessionBean);

                //check for AttendanceBean list size
                if (attendanceBeans == null) {

                    //show message
                    Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    return;
                }

                //add session to log
                dataBaseAdapter.addAttendanceSession(attendanceSessionBean);

                //set AttendanceSessionBean list to ApplicationContext
                ApplicationContext.getInstance().setAttendanceBeanList(attendanceBeans);

                //start ViewAttendanceByFacultyActivity
                startActivity(new Intent(AddAttendanceSessionActivity.this, ViewAttendanceByFacultyActivity.class));

            }
        });

        //set OnClickListener to Button
        mViewTotalAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check for empty EditText
                if (mSubject.getText().toString().equals("")) {

                    //set error message
                    mSubject.setError("Enter Subject");

                    return;
                }

                //create a AttendanceSession object
                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();

                //get FacultyBean object from ApplicationContext
                final FacultyBean facultyBean = ApplicationContext.getInstance().getFacultyBean();

                //set values to AttendanceSessionBean object
                attendanceSessionBean.setAttendanceSessionFacultyId(facultyBean.getFacultyId());
                attendanceSessionBean.setAttendanceSessionDepartment(department);
                attendanceSessionBean.setAttendanceSessionSection(section);
                attendanceSessionBean.setAttendanceSessionDate(mDate.getText().toString());
                attendanceSessionBean.setAttendanceSessionSubject(mSubject.getText().toString());
                attendanceSessionBean.setAttendanceSessionPurpose("VIEW TOTAL");
                attendanceSessionBean.setAttendanceSessionEventDate(eventDate);

                //create a DataBaseAdapter object and add current session
                DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(AddAttendanceSessionActivity.this);

                //get total attendance from database
                ArrayList<AttendanceBean> attendanceBeans = dataBaseAdapter.getTotalAttendance(attendanceSessionBean);

                if (attendanceBeans == null) {

                    Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    return;
                }

                //add session to log
                dataBaseAdapter.addAttendanceSession(attendanceSessionBean);

                //set AttendanceSessionBean list to ApplicationContext
                ApplicationContext.getInstance().setAttendanceBeanList(attendanceBeans);

                //start ViewAttendanceByFacultyActivity
                startActivity(new Intent(AddAttendanceSessionActivity.this, ViewAttendanceByFacultyActivity.class));
            }
        });

        //set OnClickListener to Button
        mViewAttendanceByPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check for empty EditText
                if (mSubject.getText().toString().equals("")) {

                    //set error message
                    mSubject.setError("Enter Subject");

                    return;
                }

                //get FacultyBean object from ApplicationContext
                final FacultyBean facultyBean = ApplicationContext.getInstance().getFacultyBean();

                //create a AttendanceSession object
                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();

                //set values to AttendanceSessionBean object
                attendanceSessionBean.setAttendanceSessionFacultyId(facultyBean.getFacultyId());
                attendanceSessionBean.setAttendanceSessionDate(mDate.getText().toString());
                attendanceSessionBean.setAttendanceSessionSubject(mSubject.getText().toString());
                attendanceSessionBean.setAttendanceSessionSection(section);
                attendanceSessionBean.setAttendanceSessionDepartment(department);
                attendanceSessionBean.setAttendanceSessionPurpose("VIEW PERCENTAGE");
                attendanceSessionBean.setAttendanceSessionEventDate(eventDate);

                //create a DataBaseAdapter object
                DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(AddAttendanceSessionActivity.this);

                //get list from database
                ArrayList<String> attendanceList = dataBaseAdapter.getStudentAttendancePercentage(attendanceSessionBean, dataBaseAdapter.getAllStudentByDepartmentAndSection(department, section));

                //if list is null
                if (attendanceList == null) {

                    //show message
                    Toast.makeText(getApplicationContext(), "No Data Fount", Toast.LENGTH_SHORT).show();

                    return;
                }

                //check if list is empty
                if (attendanceList.size() == 1) {

                    //show message
                    Toast.makeText(getApplicationContext(), "No Data Fount", Toast.LENGTH_SHORT).show();

                    return;
                }

                //add session to log
                dataBaseAdapter.addAttendanceSession(attendanceSessionBean);

                //set AttendanceSessionBean object to ApplicationContext
                ApplicationContext.getInstance().setAttendanceSessionBean(attendanceSessionBean);

                //set attendance list to Application Context
                ApplicationContext.getInstance().setStringArrayList(attendanceList);

                //start ViewAttendanceByPercentage activity
                startActivity(new Intent(AddAttendanceSessionActivity.this, ViewAttendanceByPercentage.class));
            }
        });

        //set OnClickListener to Button
        mViewClassesDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check for empty EditText
                if (mSubject.getText().toString().equals("")) {

                    //set error message
                    mSubject.setError("Enter Subject");

                    return;
                }

                //get FacultyBean object from ApplicationContext
                final FacultyBean facultyBean = ApplicationContext.getInstance().getFacultyBean();

                //create a AttendanceSession object
                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();

                //set values to AttendanceSessionBean object
                attendanceSessionBean.setAttendanceSessionFacultyId(facultyBean.getFacultyId());
                attendanceSessionBean.setAttendanceSessionDate(mDate.getText().toString());
                attendanceSessionBean.setAttendanceSessionSubject(mSubject.getText().toString());
                attendanceSessionBean.setAttendanceSessionSection(section);
                attendanceSessionBean.setAttendanceSessionDepartment(department);
                attendanceSessionBean.setAttendanceSessionPurpose("CLASSES DATES");
                attendanceSessionBean.setAttendanceSessionEventDate(eventDate);

                //create a DataBaseAdapter object
                DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(AddAttendanceSessionActivity.this);

                //get dates from database
                ArrayList<String> dateList = dataBaseAdapter.getClassesDates(attendanceSessionBean);

                if (dateList == null) {

                    //show message
                    Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    return;
                }

                //add session to log
                dataBaseAdapter.addAttendanceSession(attendanceSessionBean);

                //set date list to Application context
                ApplicationContext.getInstance().setStringArrayList(dateList);

                //set AttendanceSessionBean object to ApplicationContext
                ApplicationContext.getInstance().setAttendanceSessionBean(attendanceSessionBean);

                //start ViewAttendanceByPercentage activity
                startActivity(new Intent(AddAttendanceSessionActivity.this, ViewClassesDatesActivity.class));
            }
        });

        //set OnClickListener to Button
        mAddOrUpdateAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check for empty EditText
                if (mSubject.getText().toString().equals("")) {

                    //set error message
                    mSubject.setError("Enter Subject");

                    return;
                }

                //get FacultyBean object from ApplicationContext
                final FacultyBean facultyBean = ApplicationContext.getInstance().getFacultyBean();

                //create a AttendanceSession object
                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();

                //set values to AttendanceSessionBean object
                attendanceSessionBean.setAttendanceSessionFacultyId(facultyBean.getFacultyId());
                attendanceSessionBean.setAttendanceSessionDate(mDate.getText().toString());
                attendanceSessionBean.setAttendanceSessionSubject(mSubject.getText().toString());
                attendanceSessionBean.setAttendanceSessionSection(section);
                attendanceSessionBean.setAttendanceSessionDepartment(department);
                attendanceSessionBean.setAttendanceSessionPurpose("ADD / UPDATE");
                attendanceSessionBean.setAttendanceSessionEventDate(eventDate);

                //create a DataBaseAdapter object
                DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(AddAttendanceSessionActivity.this);

                ArrayList<StudentBean> studentBeans = dataBaseAdapter.getAllStudentByDepartmentAndSection(attendanceSessionBean.getAttendanceSessionDepartment(), attendanceSessionBean.getAttendanceSessionSection());

                //student bean is null
                if (studentBeans == null){

                    //show message
                    Toast.makeText(getApplicationContext(), "No Student Data Available", Toast.LENGTH_SHORT).show();

                    return;
                }

                //no students available
                if (studentBeans.size() == 0) {

                    //show message
                    Toast.makeText(getApplicationContext(), "No Student Data Available", Toast.LENGTH_SHORT).show();

                    return;
                }

                //add student beans to ApplicationContext
                ApplicationContext.getInstance().setStudentBeanList(studentBeans);

                //add session to log
                dataBaseAdapter.addAttendanceSession(attendanceSessionBean);

                //set AttendanceSessionBean object to ApplicationContext
                ApplicationContext.getInstance().setAttendanceSessionBean(attendanceSessionBean);

                //start SelectStudentAndStatus activity
                startActivity(new Intent(AddAttendanceSessionActivity.this, SelectStudentAndStatusActivity.class));
            }
        });

        //set OnClickListener to Button
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get FacultyBean object from ApplicationContext
                final FacultyBean facultyBean = ApplicationContext.getInstance().getFacultyBean();

                //create a AttendanceSession object
                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();

                //set values to AttendanceSessionBean object
                attendanceSessionBean.setAttendanceSessionFacultyId(facultyBean.getFacultyId());
                attendanceSessionBean.setAttendanceSessionDate(" ");
                attendanceSessionBean.setAttendanceSessionSubject(" ");
                attendanceSessionBean.setAttendanceSessionSection(0);
                attendanceSessionBean.setAttendanceSessionDepartment(" ");
                attendanceSessionBean.setAttendanceSessionPurpose("LOGOUT");
                attendanceSessionBean.setAttendanceSessionEventDate(eventDate);

                //create a DataBaseAdapter object
                DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(AddAttendanceSessionActivity.this);

                //add session to log
                dataBaseAdapter.addAttendanceSession(attendanceSessionBean);

                //start LoginActivity
                startActivity(new Intent(AddAttendanceSessionActivity.this, LoginActivity.class));

                //finish current activity
                finish();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            String display;
            if (month > 9) {

                //set date with format to string
                display = dayOfMonth + "/" + (month + 1) + "/" + year;
            } else {

                //set date with format to string
                display = dayOfMonth + "/0" + (month + 1) + "/" + year;
            }

            //set string to EditText
            mDate.setText(display);
        }
    };

    //this method will closes the Application when back is presses
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
