package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddAppoitmentController implements Initializable{
	@FXML
	private TextField apPatientIDText;
	@FXML
	private TextField apNutriIDText;
	@FXML
	private TextField apCost;
	@FXML
	private TextField apDes;
	@FXML
	private TextField apStats;
	@FXML
	private DatePicker apDateText;
	@FXML
	private TextField apHourText;
	@FXML
	private TextField apMinText;
	@FXML
	private DatePicker apPayDate;
	//----------------------------------------------------------
	@FXML
	private TableView<PatientView> patientTable;
	@FXML
	private TableColumn<PatientView,Integer> apPatientID;
	@FXML
	private TableColumn<PatientView,String> apPatientName1;
	@FXML
	private TableColumn<PatientView,String> apPatientIName2;
	@FXML
	private TableColumn<PatientView,String> apPatientIStatus;
	
    private ArrayList<PatientView> patientData = AppointmentsDashboardController.getPatientslist();
    ObservableList<PatientView> patientList;
	//-------------------------------------------------------
	@FXML
	private TableView<NutriView> nutritionistTable;
	@FXML
	private TableColumn<NutriView,Integer> apNutriID;
	@FXML
	private TableColumn<NutriView,String> apNutriIName;
	@FXML
	private TableColumn<NutriView,String> apNutriStatus;
    private ArrayList<NutriView> nutriData = AppointmentsDashboardController.getNutritionlist();
    ObservableList<NutriView> nutriList;
	//---------------------------------------------------------
	@FXML
	private TableView<AppoitmentTable> appoitmentsTable;
	@FXML
	private TableColumn<AppoitmentTable,Integer> apAppoinID;
	@FXML
	private TableColumn<AppoitmentTable,Integer> apPatientID1;
	@FXML
	private TableColumn<AppoitmentTable,Integer> apNutriID2;
	@FXML
	private TableColumn<AppoitmentTable,Date> apDate;
	@FXML
	private TableColumn<AppoitmentTable,Time> apTime;
	
	@FXML
	private TableColumn<AppoitmentTable,Integer> apAppStatus;
	@FXML
	private Label emptyField;
	

	private ArrayList<AppoitmentTable> appoitmentData = AppointmentsDashboardController.getAppoitments();
    ObservableList<AppoitmentTable> appoitmentList;
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void insertNewAP(MouseEvent event) throws ClassNotFoundException {
		if(!apPatientIDText.getText().isEmpty() && !apNutriIDText.getText().isEmpty() &&
			!apCost.getText().isEmpty() && !apDes.getText().isEmpty() && 	
			!apStats.getText().isEmpty() && apDateText.getValue() != null &&
			!apHourText.getText().isEmpty() && !apMinText.getText().isEmpty() &&
			apPayDate.getValue() != null) {
			
		int patientID = Integer.parseInt(apPatientIDText.getText().trim());
		int nutriID = Integer.parseInt(apNutriIDText.getText().trim());
		double cost= Double.parseDouble(apCost.getText().trim());
		String description = apDes.getText().toString();
		int status = Integer.parseInt(apStats.getText().trim());
		Date date1 = Date.valueOf(apDateText.getValue().toString());
		String forTime = apHourText.getText()+":"+apMinText.getText()+":"+"00";
		Time timeap = Time.valueOf(forTime);
		Date paydate = Date.valueOf(apPayDate.getValue());

    	Connection con = null;
		try {
			con = DBUtilities.connectDB();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	String SQL;
    	
    	 SQL= "insert into `appointment` (`patientID`, `nutritionistID`, `pDate`, `pTime`, `cost`, `paymentDate`, `briefDescription`, `statusID`)"
    			+ " values ("+patientID+", "+nutriID+", '"+date1+"', '"+timeap+"', "+cost+", '"+paydate+"', '"+description+"', "+status+")";
     	System.out.println("Adding the Appoitments");
        Statement stmt;
        Boolean exception = false;

		try {
			stmt = con.createStatement();
	        stmt.execute(SQL);
	        stmt.close();
	        con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			emptyField.setText("There's Already an appointment at the exact time.");
			 exception = true;
			e.printStackTrace();

			
		}
		if(!exception) {      
        emptyField.setText("Appointment has been arranged");
        //remove all old fields
        apPatientIDText.setText("");
        apNutriIDText.setText("");
        apCost.setText("");
        apDes.setText("");
        apStats.setText("");
        apDateText.setValue(null);
        apHourText.setText("");
        apMinText.setText("");
        apPayDate.setValue(null);
        appoitmentList = FXCollections.observableArrayList(appoitmentData);
	    appoitmentsTable.setItems(appoitmentList);
		}

		
	}	
		else
			emptyField.setText("Add Failed, no field should be empty");
	}
	

	@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	apPatientID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getApPatientID()).asObject());
    	apPatientName1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApPatientName1().toString()));
        apPatientIName2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApPatientIName2().toString()));
        apPatientIStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApPatientIStatus().toString()));
        
/*        try {
			getPatients();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
        patientList = FXCollections.observableArrayList(patientData);
        patientTable.setItems(patientList);
        
        //-----------------------------------------------------------------
        apNutriID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getApNutriID()).asObject());
        apNutriIName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApNutriIName().toString()));
        apNutriStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApNutriStatus().toString()));
        
  /*      try {
			getNutri();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
        
        nutriList = FXCollections.observableArrayList(nutriData);
        nutritionistTable.setItems(nutriList);
        //-------------------------------------------------------------------
        apAppoinID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppTableApID()).asObject());
        apPatientID1.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppTablePatID()).asObject());
        apNutriID2.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppTableNutriID()).asObject());       
        apDate.setCellValueFactory(cellData -> new SimpleObjectProperty<Date>(cellData.getValue().getAppTableDate()));
        apTime.setCellValueFactory(cellData -> new SimpleObjectProperty<Time>(cellData.getValue().getAppTableTime()));
        apAppStatus.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppTableStatus()).asObject());



        appoitmentList = FXCollections.observableArrayList(appoitmentData);
        appoitmentsTable.setItems(appoitmentList);
 
    }
    
    public void getPatients() throws ClassNotFoundException, SQLException {
    	Connection con = DBUtilities.connectDB();
    	System.out.println("getting patients");
    	
    	String SQL= "select p.patientID, p.FirstName , p.LastName, ps.Status"
    			+ " from patient p, patientstatus ps where p.StatusID = ps.StatusID ORDER BY p.patientID";
    	
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

       while (rs.next()){

    	   patientData.add(new PatientView(Integer.parseInt(rs.getString(1)),
    			   rs.getString(2),rs.getString(3),rs.getString(4) ));
        }
        rs.close();
        stmt.close();
        con.close();
    	
    }
    public void getNutri() throws ClassNotFoundException, SQLException {
    	Connection con = DBUtilities.connectDB();
    	System.out.println("getting nutritionists");
    	
    	String SQL= "select n.nutritionistID, n.FirstName , n.LastName, ns.Status"
    			+ " from nutritionist n, nutritioniststatus ns where n.StatusID = ns.StatusID ORDER BY n.nutritionistID";
    	
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

       while (rs.next()){
    	   int id = Integer.parseInt(rs.getString(1));
    	   String name = rs.getString(2)+" "+ rs.getString(3);
    	   String stat = rs.getString(4).toString();
    	   nutriData.add(new NutriView(id,name,stat ));
        }
        rs.close();
        stmt.close();
        con.close();
    	
    }
    

    
	
}

