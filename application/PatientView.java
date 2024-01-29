package application;

public class PatientView {

	private  int apPatientID;

	private String apPatientName1;

	private String apPatientIName2;

	private String apPatientIStatus;
	

	public PatientView(int apPatientID, String apPatientName1, String apPatientIName2, String apPatientIStatus) {
		super();
		this.apPatientID = apPatientID;
		this.apPatientName1 = apPatientName1;
		this.apPatientIName2 = apPatientIName2;
		this.apPatientIStatus = apPatientIStatus;
	}

	public  int getApPatientID() {
		return apPatientID;
	}

	public void setApPatientID(int apPatientID) {
		this.apPatientID = apPatientID;
	}

	public String getApPatientName1() {
		return apPatientName1;
	}

	public void setApPatientName1(String apPatientName1) {
		this.apPatientName1 = apPatientName1;
	}

	public String getApPatientIName2() {
		return apPatientIName2;
	}

	public void setApPatientIName2(String apPatientIName2) {
		this.apPatientIName2 = apPatientIName2;
	}

	public String getApPatientIStatus() {
		return apPatientIStatus;
	}

	public void setApPatientIStatus(String apPatientIStatus) {
		this.apPatientIStatus = apPatientIStatus;
	}

}
