package com.prasanth.attendance.beans;

public class AttendanceBean {

    private int attendanceSessionId;
    private String attendanceStudentId;
    private String status;

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
}

