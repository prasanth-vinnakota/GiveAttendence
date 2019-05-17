package com.prasanth.attendance.beans;

public class AttendanceSessionBean {

    private int attendanceSessionId;
    private int attendanceSessionFacultyId;
    private String attendanceSessionDepartment;
    private int attendanceSessionSection;
    private String attendanceSessionDate;
    private String attendanceSessionSubject;
    private String attendanceSessionPurpose;
    private String attendanceSessionEventDate;

    public int getAttendanceSessionId() {
        return attendanceSessionId;
    }

    public void setAttendanceSessionId(int attendanceSessionId) {
        this.attendanceSessionId = attendanceSessionId;
    }

    public int getAttendanceSessionFacultyId() {
        return attendanceSessionFacultyId;
    }

    public void setAttendanceSessionFacultyId(int attendanceSessionFacultyId) {
        this.attendanceSessionFacultyId = attendanceSessionFacultyId;
    }

    public String getAttendanceSessionDepartment() {
        return attendanceSessionDepartment;
    }

    public void setAttendanceSessionDepartment(String attendanceSessionDepartment) {
        this.attendanceSessionDepartment = attendanceSessionDepartment;
    }

    public int getAttendanceSessionSection() {
        return attendanceSessionSection;
    }

    public void setAttendanceSessionSection(int attendanceSessionSection) {
        this.attendanceSessionSection = attendanceSessionSection;
    }

    public String getAttendanceSessionDate() {
        return attendanceSessionDate;
    }

    public void setAttendanceSessionDate(String attendanceSessionDate) {
        this.attendanceSessionDate = attendanceSessionDate;
    }

    public String getAttendanceSessionSubject() {
        return attendanceSessionSubject;
    }

    public void setAttendanceSessionSubject(String attendanceSessionSubject) {
        this.attendanceSessionSubject = attendanceSessionSubject;
    }

    public String getAttendanceSessionPurpose() {
        return attendanceSessionPurpose;
    }

    public void setAttendanceSessionPurpose(String attendanceSessionPurpose) {
        this.attendanceSessionPurpose = attendanceSessionPurpose;
    }

    public String getAttendanceSessionEventDate() {
        return attendanceSessionEventDate;
    }

    public void setAttendanceSessionEventDate(String attendanceSessionEventDate) {
        this.attendanceSessionEventDate = attendanceSessionEventDate;
    }
}
