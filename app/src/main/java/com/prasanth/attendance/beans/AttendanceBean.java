package com.prasanth.attendance.beans;

public class AttendanceBean {

    private int attendanceSessionId;
    private String attendanceStudentId;
    private String status;
    private String date;
    private String time;

    public int getAttendanceSessionId() {
        return attendanceSessionId;
    }

    public void setAttendanceSessionId(int attendanceSessionId) {
        this.attendanceSessionId = attendanceSessionId;
    }

    public String getAttendanceStudentId() {
        return attendanceStudentId;
    }

    public void setAttendanceStudentId(String attendanceStudentId) {
        this.attendanceStudentId = attendanceStudentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

