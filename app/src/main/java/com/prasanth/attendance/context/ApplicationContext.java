package com.prasanth.attendance.context;

import java.util.ArrayList;

import android.app.Application;

import com.prasanth.attendance.beans.AttendanceBean;
import com.prasanth.attendance.beans.AttendanceSessionBean;
import com.prasanth.attendance.beans.FacultyBean;
import com.prasanth.attendance.beans.StudentBean;

public class ApplicationContext extends Application {
    private FacultyBean facultyBean;
    private AttendanceSessionBean attendanceSessionBean;
    private ArrayList<StudentBean> studentBeanList;
    private ArrayList<AttendanceBean> attendanceBeanList;
    private ArrayList<AttendanceSessionBean> attendanceSessionBeanList;
    private ArrayList<String> stringArrayList;

    private static ApplicationContext instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static ApplicationContext getInstance() {
        return instance;
    }


    public FacultyBean getFacultyBean() {
        return facultyBean;
    }

    public void setFacultyBean(FacultyBean facultyBean) {
        this.facultyBean = facultyBean;
    }

    public AttendanceSessionBean getAttendanceSessionBean() {
        return attendanceSessionBean;
    }

    public void setAttendanceSessionBean(AttendanceSessionBean attendanceSessionBean) {
        this.attendanceSessionBean = attendanceSessionBean;
    }

    public ArrayList<StudentBean> getStudentBeanList() {
        return studentBeanList;
    }

    public void setStudentBeanList(ArrayList<StudentBean> studentBeanList) {
        this.studentBeanList = studentBeanList;
    }

    public ArrayList<AttendanceBean> getAttendanceBeanList() {
        return attendanceBeanList;
    }

    public void setAttendanceBeanList(ArrayList<AttendanceBean> attendanceBeanList) {
        this.attendanceBeanList = attendanceBeanList;
    }

    public void setAttendanceSessionBeanList(ArrayList<AttendanceSessionBean> attendanceSessionBeanList) {
        this.attendanceSessionBeanList = attendanceSessionBeanList;
    }

    public ArrayList<AttendanceSessionBean> getAttendanceSessionBeanList() {
        return attendanceSessionBeanList;
    }

    public void setStringArrayList(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }

    public ArrayList<String> getStringArrayList() {
        return stringArrayList;
    }
}
