package application;

import java.sql.Time;

public class TodaysAppointmentsTable {

    private int appointmentId;
    private int patientId;
    private int nutritionistId;
    private Time time;
    private String status;

    public TodaysAppointmentsTable(int appointmentId, int patientId, int nutritionistId,
                                   Time time,
                                   String status) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.nutritionistId = nutritionistId;
        this.time = time;
        this.status = status;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int appointmentDashboardPatientId) {
        this.patientId = appointmentDashboardPatientId;
    }

    public int getNutritionistId() {
        return nutritionistId;
    }

    public void setNutritionistId(int nutritionistId) {
        this.nutritionistId = nutritionistId;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
}
