package application;

public class NutriView {

	private  int apNutriID;

	private String apNutriIName;

	private String apNutriStatus;

	public NutriView(int apNutriID, String apNutriIName, String apNutriStatus) {
		super();
		this.apNutriID = apNutriID;
		this.apNutriIName = apNutriIName;
		this.apNutriStatus = apNutriStatus;
	}

	public  int getApNutriID() {
		return apNutriID;
	}

	public void setApNutriID(int apNutriID) {
		this.apNutriID = apNutriID;
	}

	public String getApNutriIName() {
		return apNutriIName;
	}

	public void setApNutriIName(String apNutriIName) {
		this.apNutriIName = apNutriIName;
	}

	public String getApNutriStatus() {
		return apNutriStatus;
	}

	public void setApNutriStatus(String apNutriStatus) {
		this.apNutriStatus = apNutriStatus;
	}
	
}
