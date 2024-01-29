package application;

import java.sql.Date;
import java.sql.Time;

public class LastPatientAppointments {
    private int patientId;
    private String patientName;
    private int specialistId;
    private String specialistName;
    private Date date;
    private Time time;
    private String description;

    public LastPatientAppointments(int patientId, String patientName, int specialistId, String specialistName, Date date, Time time, String description) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.specialistId = specialistId;
        this.specialistName = specialistName;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(int specialistId) {
        this.specialistId = specialistId;
    }

    public String getSpecialistName() {
        return specialistName;
    }

    public void setSpecialistName(String specialistName) {
        this.specialistName = specialistName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
