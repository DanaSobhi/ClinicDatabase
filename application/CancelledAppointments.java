package application;

import java.sql.Time;

public class CancelledAppointments {
	private int appointmentId;
	private int patientId;
	private int nutritionistId;
    private Time time;
    
	public CancelledAppointments(int appointmentId, int patientId, int nutritionistId, Time time) {
		this.appointmentId = appointmentId;
		this.patientId = patientId;
		this.nutritionistId = nutritionistId;
		this.time = time;
	}
	public int getcAppID() {
		return appointmentId;
	}
	public void setcAppID(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	public int getcAppPatientID() {
		return patientId;
	}
	public void setcAppPatientID(int patientId) {
		this.patientId = patientId;
	}
	public int getcAppNutriID() {
		return nutritionistId;
	}
	public void setcAppNutriID(int nutritionistId) {
		this.nutritionistId = nutritionistId;
	}
	public Time getcAppTime() {
		return time;
	}
	public void setcAppTime(Time time) {
		this.time = time;
	}

    
}
