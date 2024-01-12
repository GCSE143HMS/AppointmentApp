package com.example.doctorappointmentapp;

public class NewPrescriptionClass {
    private String appId;
    private String userId;
    private String doctorId;
    private String doctorName;
    private String phoneNum;
    private String appDate;
    private String appTime;
    private String qualification;
    private String specialization;
    private String patientName;
    private String pgender;
    private String status;
    public NewPrescriptionClass(String appId, String userId, String doctorId, String doctorName, String phoneNum, String appDate, String appTime, String qualification, String specialization, String gender,
                                String patientName, String pgender, String status) {
        this.appId = appId;
        this.userId = userId;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.phoneNum = phoneNum;
        this.appDate = appDate;
        this.appTime = appTime;
        this.qualification = qualification;
        this.specialization = specialization;
        this.gender = gender;
        this.patientName = patientName;
        this.pgender = pgender;
        this.status = status;
    }

    public NewPrescriptionClass() {
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String gender;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPgender() {
        return pgender;
    }

    public void setPgender(String pgender) {
        this.pgender = pgender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
