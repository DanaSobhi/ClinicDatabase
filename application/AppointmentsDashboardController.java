package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AppointmentsDashboardController implements Initializable {
    @FXML
    private TableView<LastPatientAppointments> lastPatientsTable;

    @FXML
    private TableColumn<LastPatientAppointments, Integer> patientId;

    @FXML
    private TableColumn<LastPatientAppointments, String> patientName;

    @FXML
    private TableColumn<LastPatientAppointments, Integer> specialistId;

    @FXML
    private TableColumn<LastPatientAppointments, String> specialistName;

    @FXML
    private TableColumn<LastPatientAppointments, Date> date;

    @FXML
    private TableColumn<LastPatientAppointments, Time> time;

    @FXML
    private TableColumn<LastPatientAppointments, String> discription;
    private ArrayList<LastPatientAppointments> lastPatientData = new ArrayList<>();
    ObservableList<LastPatientAppointments> lastPatientList;

    @FXML
    private TableView<TodaysAppointmentsTable> todaysPatientsTable;

    @FXML
    private TableView<TodaysAppointmentsTable> todaysAppointmentsTable;
    @FXML
    private TableColumn<TodaysAppointmentsTable, Integer> _appointmentId;

    @FXML
    private TableColumn<TodaysAppointmentsTable, Integer> _patientId;

    @FXML
    private TableColumn<TodaysAppointmentsTable, Integer> _nutritionistId;

    @FXML
    private TableColumn<TodaysAppointmentsTable, Time> _time;

    @FXML
    private TableColumn<TodaysAppointmentsTable, String> _status;
    private ArrayList<TodaysAppointmentsTable> todaysAppointmentsData = new ArrayList<>();
    ObservableList<TodaysAppointmentsTable> todaysAppointmentsList;

    @FXML
    private BarChart<String, Integer> appointmentsPerMonthChart;
    //----------------------------------------------------------------------------------
	@FXML
	private TableView<CancelledAppointments> cancelledAppointmentsTable;
	@FXML
	private TableColumn<CancelledAppointments,Integer> cAppID;
	@FXML
	private TableColumn<CancelledAppointments,Integer> cAppPatientID;
	@FXML
	private TableColumn<CancelledAppointments,Integer> cAppNutriID;
	@FXML
	private TableColumn<CancelledAppointments,Time> cAppTime;

	
    private ArrayList<CancelledAppointments> cancelledAppoitmentsData = new ArrayList<>();
    ObservableList<CancelledAppointments> cancelledAppoitmentsList;
	//------------------------------------------------------------------
	@FXML
	private BorderPane appoitmentTablePane;
	@FXML
	private VBox appoitmentTableBox;
	//-------------------------------------------------------------------
	@FXML
	private TableView<AppoitmentTable> appTableT;
	@FXML
	private TableColumn<AppoitmentTable,Integer> appTableApID;
	@FXML
	private TableColumn<AppoitmentTable,Integer>  appTablePatID;
	@FXML
	private TableColumn<AppoitmentTable,Integer>  appTableNutriID;
	@FXML
	private TableColumn<AppoitmentTable,Date>  appTableDate;
	@FXML
	private TableColumn<AppoitmentTable,Time>  appTableTime;
	@FXML
	private TableColumn<AppoitmentTable,Double>  appTableCost;
	@FXML
	private TableColumn<AppoitmentTable,Date>  appTablePayDate;
	@FXML
	private TableColumn<AppoitmentTable,String>  appTableBried;
	@FXML
	private TableColumn<AppoitmentTable,Integer>  appTableStatus;
	
	
    protected static ArrayList<AppoitmentTable> appoitmentData = new ArrayList<>();
    ObservableList<AppoitmentTable> appoitmentDataList;
    
    protected static ArrayList<NutriView> nutriData = new ArrayList<>();
    protected static ArrayList<PatientView> patientData = new ArrayList<>();
    
    public static ArrayList<AppoitmentTable> getAppoitments() {
    	return appoitmentData;
    	
    }
    public static ArrayList<PatientView> getPatientslist() {
    	return patientData;
    }
    public static ArrayList<NutriView> getNutritionlist() {
    	return nutriData;
    }
	//--------------------------------------------------------------------
    @FXML
    private TextField searchIDText;
	@FXML
	private DatePicker firstDate1;
	@FXML
	private DatePicker secondDate2;

	@FXML
	private Label numOfappMade;
	@FXML
	private Label numOfPatientVisit;
	@FXML
	private Label numOfCost;
	@FXML
	private Label nameOfN;

	//----------------------------------------------------------------------------------
	// Event Listener on Button.onMouseClicked
	@FXML
	public void searchApp(MouseEvent event) {
		//searchIDText
		if(searchIDText.getText() != null) {
		int id = Integer.parseInt(searchIDText.getText().trim());
		try {
			getAppoitment(id);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}
	// Event Listener on Button.onMouseClicked
	@FXML
	public void deleteApp(MouseEvent event)  {
		if(searchIDText.getText() != null) {
		int id = Integer.parseInt(searchIDText.getText().trim());
        Connection con;
		try {
			con = DBUtilities.connectDB();
			Statement stmt = con.createStatement();
			String SQL="delete from appointment where appointmentID = "+id;
			
			stmt.execute(SQL);
			stmt.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		}
				
	}
	
	public void generateNormal() throws ClassNotFoundException, SQLException {
		//numOfappMade

		Connection con;
		con = DBUtilities.connectDB();
		Statement stmt = con.createStatement();
		String SQL="SELECT COUNT(`AppointmentID`)FROM `Appointment`;";
			
	    ResultSet count = stmt.executeQuery(SQL);
	    int totalP=0;
	    while (count.next()) {
	        	totalP =Integer.parseInt(count.getString(1));	        	
	        }
	    numOfappMade.setText(totalP+"");
	    //nameOfN
	    SQL = "select n.nutritionistID, n.FirstName,  n.LastName, \r\n"
	    		+ "count(a.appointmentID) AS TotalAppointments\r\n"
	    		+ "from  nutritionist n \r\n"
	    		+ "inner join appointment a ON n.nutritionistID = a.nutritionistID\r\n"
	    		+ "group by  n.NutritionistID, n.FirstName, n.LastName \r\n"
	    		+ "order by TotalAppointments DESC LIMIT 1;";
	    ResultSet nutri = stmt.executeQuery(SQL);
	    while (nutri.next()) {
	    	nameOfN.setText(nutri.getString(2)+" "+ nutri.getString(3)+"\nTotal: "+nutri.getString(4));
        	
        }	    

	    
	   //numOfCost 
	    SQL = "select sum(cost) as totalcost\r\n"
	    		+ "from appointment;";
	    ResultSet sumcost = stmt.executeQuery(SQL);
	    while (sumcost.next()) {
	    	numOfCost.setText(sumcost.getString(1)+"");
        	
        }
	    
	    //numOfPatientVisit
	    SQL = " select p.PatientID, p.FirstName,  p.LastName,sum(a.cost) AS Paid,\r\n"
	    		+ "count(a.appointmentID) AS TotalAppointments\r\n"
	    		+ "from  patient p \r\n"
	    		+ "inner join appointment a ON p.PatientID = a.PatientID \r\n"
	    		+ "group by  p.PatientID, p.FirstName, p.LastName\r\n"
	    		+ "order by TotalAppointments DESC LIMIT 1;";
	    
	    ResultSet mostVisit = stmt.executeQuery(SQL);
	    while (mostVisit.next()) {
	    	numOfPatientVisit.setText(mostVisit.getString(2)+" "+mostVisit.getString(3)+"\nPaid: "
	    								+mostVisit.getString(4)+" Visited: "+mostVisit.getString(5)+" Times");
	    }
	    

	    
	    mostVisit.close();	    
        stmt.close();
        con.close();

		
			

		
		
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void goToMakeReport(MouseEvent event) throws ClassNotFoundException, SQLException {
		Connection con;
		con = DBUtilities.connectDB();
		Statement stmt = con.createStatement();
		if(firstDate1.getValue() != null && secondDate2.getValue() == null) {
			String SQL="SELECT COUNT(`AppointmentID`)FROM `Appointment` WHERE `PDate` >='"+firstDate1.getValue()+"'";
			
		    ResultSet count = stmt.executeQuery(SQL);
		    int totalP=0;
		    while (count.next()) {
		        	totalP =Integer.parseInt(count.getString(1));	        	
		        }
		    numOfappMade.setText(totalP+"");
		    //nameOfN
		    SQL = "select n.nutritionistID, n.FirstName,  n.LastName, \r\n"
		    		+ "count(a.appointmentID) AS TotalAppointments\r\n"
		    		+ "from  nutritionist n \r\n"
		    		+ "inner join appointment a ON n.nutritionistID = a.nutritionistID\r\n"
		    		+ "where a.PDate >='"+firstDate1.getValue()+"'"
		    		+ "group by  n.NutritionistID, n.FirstName, n.LastName \r\n"
		    		+ "order by TotalAppointments DESC LIMIT 1;";
		    ResultSet nutri = stmt.executeQuery(SQL);
		    while (nutri.next()) {
		    	nameOfN.setText(nutri.getString(2)+" "+ nutri.getString(3)+" Total: "+nutri.getString(4));
	        	
	        }	    

		    
		   //numOfCost 
		    SQL = "select sum(cost) as totalcost\r\n"
		    		+ "from appointment WHERE `PDate` >='"+firstDate1.getValue()+"'";
		    ResultSet sumcost = stmt.executeQuery(SQL);
		    while (sumcost.next()) {
		    	numOfCost.setText(sumcost.getString(1)+"");
	        	
	        }
		    
		    //numOfPatientVisit
		    SQL = " select p.PatientID, p.FirstName,  p.LastName,sum(a.cost) AS Paid,\r\n"
		    		+ "count(a.appointmentID) AS TotalAppointments\r\n"
		    		+ "from  patient p \r\n"
		    		+ "inner join appointment a ON p.PatientID = a.PatientID \r\n"
		    		+ "where a.PDate >='"+firstDate1.getValue()+"'"
		    		+ "group by  p.PatientID, p.FirstName, p.LastName\r\n"
		    		+ "order by TotalAppointments DESC LIMIT 1;";
		    
		    ResultSet mostVisit = stmt.executeQuery(SQL);
		    while (mostVisit.next()) {
		    	numOfPatientVisit.setText(mostVisit.getString(2)+" "+mostVisit.getString(3)+"\nPaid: "
		    								+mostVisit.getString(4)+" Visited: "+mostVisit.getString(5)+" Times");
		    }

		}
		
		//secondDate2;
		if(firstDate1.getValue() != null && secondDate2.getValue() != null) {
		String SQL="SELECT COUNT(`AppointmentID`)FROM `Appointment` WHERE `PDate` >='"
				+firstDate1.getValue()+"' AND `PDate` <='"+secondDate2.getValue()+"'";
		
	    ResultSet count = stmt.executeQuery(SQL);
	    int totalP=0;
	    while (count.next()) {
	        	totalP =Integer.parseInt(count.getString(1));	        	
	        }
	    numOfappMade.setText(totalP+"");
	    //nameOfN
	    SQL = "select n.nutritionistID, n.FirstName,  n.LastName, \r\n"
	    		+ "count(a.appointmentID) AS TotalAppointments\r\n"
	    		+ "from  nutritionist n \r\n"
	    		+ "inner join appointment a ON n.nutritionistID = a.nutritionistID\r\n"
	    		+ "where a.PDate >='"+firstDate1.getValue()+"' and a.PDate <='"+secondDate2.getValue()+"'" 
	    		+ "group by  n.NutritionistID, n.FirstName, n.LastName \r\n"
	    		+ "order by TotalAppointments DESC LIMIT 1;";
	    ResultSet nutri = stmt.executeQuery(SQL);
	    while (nutri.next()) {
	    	nameOfN.setText(nutri.getString(2)+" "+ nutri.getString(3)+" Total: "+nutri.getString(4));
        	
        }	    

	    
	   //numOfCost 
	    SQL = "select sum(cost) as totalcost\r\n"
	    		+ "from appointment WHERE `PDate` >='"+firstDate1.getValue()+"'";
	    ResultSet sumcost = stmt.executeQuery(SQL);
	    while (sumcost.next()) {
	    	numOfCost.setText(sumcost.getString(1)+"");
        	
        }
	    
	    //numOfPatientVisit
	    SQL = " select p.PatientID, p.FirstName,  p.LastName,sum(a.cost) AS Paid,\r\n"
	    		+ "count(a.appointmentID) AS TotalAppointments\r\n"
	    		+ "from  patient p \r\n"
	    		+ "inner join appointment a ON p.PatientID = a.PatientID \r\n"
	    		+ "where a.PDate >='"+firstDate1.getValue()+"' and a.PDate <='"+secondDate2.getValue()+"'" 
	    		+ "group by  p.PatientID, p.FirstName, p.LastName\r\n"
	    		+ "order by TotalAppointments DESC LIMIT 1;";
	    
	    ResultSet mostVisit = stmt.executeQuery(SQL);
	    while (mostVisit.next()) {
	    	numOfPatientVisit.setText(mostVisit.getString(2)+" "+mostVisit.getString(3)+"\nPaid: "
	    								+mostVisit.getString(4)+" Visited: "+mostVisit.getString(5)+" Times");
	    }

	}
		if(firstDate1.getValue() == null && secondDate2.getValue() != null) {
			String SQL="SELECT COUNT(`AppointmentID`)FROM `Appointment` WHERE `PDate` <='"+secondDate2.getValue()+"'";
			
		    ResultSet count = stmt.executeQuery(SQL);
		    int totalP=0;
		    while (count.next()) {
		        	totalP =Integer.parseInt(count.getString(1));	        	
		        }
		    numOfappMade.setText(totalP+"");
		    //nameOfN
		    SQL = "select n.nutritionistID, n.FirstName,  n.LastName, \r\n"
		    		+ "count(a.appointmentID) AS TotalAppointments\r\n"
		    		+ "from  nutritionist n \r\n"
		    		+ "inner join appointment a ON n.nutritionistID = a.nutritionistID\r\n"
		    		+ "where a.PDate <='"+secondDate2.getValue()+"'"
		    		+ "group by  n.NutritionistID, n.FirstName, n.LastName \r\n"
		    		+ "order by TotalAppointments DESC LIMIT 1;";
		    ResultSet nutri = stmt.executeQuery(SQL);
		    while (nutri.next()) {
		    	nameOfN.setText(nutri.getString(2)+" "+ nutri.getString(3)+" Total: "+nutri.getString(4));
	        	
	        }	    

		    
		   //numOfCost 
		    SQL = "select sum(cost) as totalcost\r\n"
		    		+ "from appointment WHERE `PDate` <='"+secondDate2.getValue()+"'";
		    ResultSet sumcost = stmt.executeQuery(SQL);
		    while (sumcost.next()) {
		    	numOfCost.setText(sumcost.getString(1)+"");
	        	
	        }
		    
		    //numOfPatientVisit
		    SQL = " select p.PatientID, p.FirstName,  p.LastName,sum(a.cost) AS Paid,\r\n"
		    		+ "count(a.appointmentID) AS TotalAppointments\r\n"
		    		+ "from  patient p \r\n"
		    		+ "inner join appointment a ON p.PatientID = a.PatientID \r\n"
		    		+ "where a.PDate <='"+secondDate2.getValue()+"'"
		    		+ "group by  p.PatientID, p.FirstName, p.LastName\r\n"
		    		+ "order by TotalAppointments DESC LIMIT 1;";
		    
		    ResultSet mostVisit = stmt.executeQuery(SQL);
		    while (mostVisit.next()) {
		    	numOfPatientVisit.setText(mostVisit.getString(2)+" "+mostVisit.getString(3)+"\nPaid: "
		    								+mostVisit.getString(4)+" Visited: "+mostVisit.getString(5)+" Times");
		    }

		}

		
	}
     

    //-----------------------------------------------------------------------------------
	@FXML
	public void addNewApp(MouseEvent event) {
		appoitmentTablePane.setCenter(appoitmentTableBox);			
		loadto("addAppointments");
	}
    
	@FXML
	public void updateAppointment(MouseEvent event) {
		appoitmentTablePane.setCenter(appoitmentTableBox);
        appTableT.setEditable(true);
	}

	private void loadto(String page) {
		Parent root= null;
		try {
			root = FXMLLoader.load(getClass().getResource(page+".fxml"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		appoitmentTablePane.setCenter(root);
	} 
    
	// Event Listener on Button.onMouseClicked
	@FXML
	public void deleteOldApp(MouseEvent event) {
		if(searchIDText.getText() != null) {
        Connection con;
		try {
			con = DBUtilities.connectDB();
			Statement stmt = con.createStatement();
			String SQL="delete from appointment where `PDate` = curdate();";
			
			stmt.execute(SQL);
			stmt.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		}
	}
	// Event Listener on Button.onMouseClicked
	@FXML
	public void reloadApp(MouseEvent event) {
		appoitmentTablePane.setCenter(appoitmentTableBox);
		appoitmentData.clear();
		cancelledAppoitmentsData.clear();
		todaysAppointmentsData.clear();
		lastPatientData.clear();
        appointmentsPerMonthChart.getData().clear();
        try {
      	   getTableData();
      	   getLastPatientData();
      	   getTodaysAppointmentsData();
      	   getAppointmentsChartData();
      	   getCancelled();
      	   generateNormal();
  		} catch (ClassNotFoundException | SQLException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
		
		
        lastPatientList = FXCollections.observableArrayList(lastPatientData);
        lastPatientsTable.setItems(lastPatientList);
        todaysAppointmentsList = FXCollections.observableArrayList(todaysAppointmentsData);
        todaysAppointmentsTable.setItems(todaysAppointmentsList);       
        cancelledAppoitmentsList = FXCollections.observableArrayList(cancelledAppoitmentsData);
        if (cancelledAppointmentsTable != null) {
            cancelledAppointmentsTable.setItems(cancelledAppoitmentsList);
        } else {
            System.out.println("cancelledAppointmentsTable is null");
        }              
        appoitmentDataList = FXCollections.observableArrayList(appoitmentData);
         if (appTableT != null) {
         	appTableT.setItems(appoitmentDataList);
         } else {
             System.out.println("cancelledAppointmentsTable is null");
         }
         appTableT.setEditable(false);

	}
  
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	//---------------------------------------------------------------------
    	
    	try {
    		getNutri();
			getPatients();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//---------------------------Last Patients--------------------------
        patientId.setCellValueFactory(new PropertyValueFactory<LastPatientAppointments, Integer>("patientId"));
        patientName.setCellValueFactory(new PropertyValueFactory<LastPatientAppointments, String>("patientName"));
        specialistId.setCellValueFactory(new PropertyValueFactory<LastPatientAppointments, Integer>("specialistId"));
        specialistName.setCellValueFactory(new PropertyValueFactory<LastPatientAppointments, String>("specialistName"));
        date.setCellValueFactory(new PropertyValueFactory<LastPatientAppointments, Date>("date"));
        time.setCellValueFactory(new PropertyValueFactory<LastPatientAppointments, Time>("time"));
        discription.setCellValueFactory(new PropertyValueFactory<LastPatientAppointments, String>("description"));
        try{
            getLastPatientData();
        }catch (Exception e){
            e.printStackTrace();
        }
        lastPatientList = FXCollections.observableArrayList(lastPatientData);
        lastPatientsTable.setItems(lastPatientList);
        //-----------------------------Today's Appointment-----------------------------------
        _appointmentId.setCellValueFactory(new PropertyValueFactory<TodaysAppointmentsTable,Integer>("appointmentId"));
        _patientId.setCellValueFactory(new PropertyValueFactory<TodaysAppointmentsTable,Integer>("patientId"));
        _nutritionistId.setCellValueFactory(new PropertyValueFactory<TodaysAppointmentsTable,Integer>("nutritionistId"));
        _time.setCellValueFactory(new PropertyValueFactory<TodaysAppointmentsTable,Time>("time"));
        _status.setCellValueFactory(new PropertyValueFactory<TodaysAppointmentsTable,String>("status"));

        try{
            getTodaysAppointmentsData();
        }catch (Exception e){
            e.printStackTrace();
        }
        
        todaysAppointmentsList = FXCollections.observableArrayList(todaysAppointmentsData);
        todaysAppointmentsTable.setItems(todaysAppointmentsList);
        //----------------------------Chart -----------------------------------------------
        try {
            getAppointmentsChartData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        
        //------------------------------Cancelled Appointments---------------------------------

        cAppID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getcAppID()).asObject());
        cAppPatientID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getcAppPatientID()).asObject());
        cAppNutriID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getcAppNutriID()).asObject());
        cAppTime.setCellValueFactory(cellData -> new SimpleObjectProperty<Time>(cellData.getValue().getcAppTime()));
       try {
			getCancelled();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        cancelledAppoitmentsList = FXCollections.observableArrayList(cancelledAppoitmentsData);
        if (cancelledAppointmentsTable != null) {
            cancelledAppointmentsTable.setItems(cancelledAppoitmentsList);
        } else {
            System.out.println("cancelledAppointmentsTable is null");
        }
        //-----------------------------The table in settings:------------------------------------              
        appTableApID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppTableApID()).asObject());
       //-- no change on the primary key , yet use the rest: 
        appTablePatID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppTablePatID()).asObject());
        appTablePatID.setCellFactory(TextFieldTableCell.<AppoitmentTable,Integer>forTableColumn(new IntegerStringConverter()));
        appTablePatID.setOnEditCommit((CellEditEvent<AppoitmentTable, Integer> t)  -> {
	        	   ((AppoitmentTable) t.getTableView().getItems().get(t.getTablePosition().getRow()))
	        	            .setAppTablePatID(t.getNewValue()); 
	    updatePatientID(t.getRowValue().getAppTableApID() , t.getNewValue());
        });
        //-------------------------------Let each column be modifiable ---------------------------
        appTableNutriID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppTableNutriID()).asObject());
        appTableNutriID.setCellFactory(TextFieldTableCell.<AppoitmentTable,Integer>forTableColumn(new IntegerStringConverter()));
        appTableNutriID.setOnEditCommit((CellEditEvent<AppoitmentTable, Integer> t)  -> {
	        	   ((AppoitmentTable) t.getTableView().getItems().get(t.getTablePosition().getRow()))
	        	            .setAppTableNutriID(t.getNewValue()); 
	    updateNutritionistID(t.getRowValue().getAppTableApID()  , t.getNewValue());
        });
        //----------------------------------------------------------------------------------------
        appTableDate.setCellValueFactory(cellData -> new SimpleObjectProperty<Date>(cellData.getValue().getAppTableDate()));
        appTableDate.setCellFactory(TextFieldTableCell.<AppoitmentTable, Date>forTableColumn(new StringConverter<Date>() {
            @Override
            public String toString(Date date) {
                if (date == null) {
                    return null;
                }
                return date.toString(); // Get the date as string
            }

            @Override
            public Date fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return null;
                }
                return java.sql.Date.valueOf(string); //then modify it to get the value as a Date
            }
        }));
        
        appTableDate.setOnEditCommit((CellEditEvent<AppoitmentTable, Date> t)  -> {
	        	   ((AppoitmentTable) t.getTableView().getItems().get(t.getTablePosition().getRow()))
	        	            .setAppTableDate(t.getNewValue()); 
	   updateAppDate(t.getRowValue().getAppTableApID()  , t.getNewValue());
        });
        //-------------------------------------------------------------------------------------------------
        appTableTime.setCellValueFactory(cellData -> new SimpleObjectProperty<Time>(cellData.getValue().getAppTableTime()));
        appTableTime.setCellFactory(TextFieldTableCell.<AppoitmentTable, Time>forTableColumn(new StringConverter<Time>() {
            @Override
            public String toString(Time time) {
                if (time == null) {
                    return null;
                }
                return time.toString(); // Get the date as string
            }

            @Override
            public Time fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return null;
                }
                return Time.valueOf(string); //then modify it to get the value as a Date
            }
        }));
        
        appTableTime.setOnEditCommit((CellEditEvent<AppoitmentTable, Time> t)  -> {
	        	   ((AppoitmentTable) t.getTableView().getItems().get(t.getTablePosition().getRow()))
	        	            .setAppTableTime(t.getNewValue()); 
	    updateAppTime(t.getRowValue().getAppTableApID() , t.getNewValue());
        });
        //------------------------------------------------------------------------------------------------------------      
        appTableCost.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAppTableCost()).asObject());
        appTableCost.setCellFactory(TextFieldTableCell.<AppoitmentTable,Double>forTableColumn(new DoubleStringConverter()));
        appTableCost.setOnEditCommit((CellEditEvent<AppoitmentTable, Double> t)  -> {
	        	   ((AppoitmentTable) t.getTableView().getItems().get(t.getTablePosition().getRow()))
	        	            .setAppTableCost(t.getNewValue()); 
	        	      
	    updateAppCost(t.getRowValue().getAppTableApID() , t.getNewValue());
        });
        //-------------------------------------------------------------------------------------------------------------
        
        appTablePayDate.setCellValueFactory(cellData -> new SimpleObjectProperty<Date>(cellData.getValue().getAppTablePayDate()));
        appTablePayDate.setCellFactory(TextFieldTableCell.<AppoitmentTable, Date>forTableColumn(new StringConverter<Date>() {
            @Override
            public String toString(Date date) {
                if (date == null) {
                    return null;
                }
                return date.toString(); // Get the date as string
            }

            @Override
            public Date fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return null;
                }
                return java.sql.Date.valueOf(string); //then modify it to get the value as a Date
            }
        }));
        
        appTablePayDate.setOnEditCommit((CellEditEvent<AppoitmentTable, Date> t)  -> {
	        	   ((AppoitmentTable) t.getTableView().getItems().get(t.getTablePosition().getRow()))
	        	            .setAppTablePayDate(t.getNewValue()); 
	        	   	   
	    updateAppPayDate(t.getRowValue().getAppTableApID() , t.getNewValue());
        });
        //------------------------------------------------------------------------------------------------------------- 
        appTableBried.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppTableBried().toString()));
        appTableBried.setCellFactory(TextFieldTableCell.<AppoitmentTable>forTableColumn());
        appTableBried.setOnEditCommit((CellEditEvent<AppoitmentTable, String> t)  -> {
	        	   ((AppoitmentTable) t.getTableView().getItems().get(t.getTablePosition().getRow()))
	        	            .setAppTableBried(t.getNewValue()); 
	        	    	  
	    updateAppdescription(t.getRowValue().getAppTableApID() , t.getNewValue());	
	    });
        //----------------------------------------------------------------------------------------------
        appTableStatus.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppTableStatus()).asObject());
        appTableStatus.setCellFactory(TextFieldTableCell.<AppoitmentTable,Integer>forTableColumn(new IntegerStringConverter()));
        appTableStatus.setOnEditCommit((CellEditEvent<AppoitmentTable, Integer> t)  -> {
	        	   ((AppoitmentTable) t.getTableView().getItems().get(t.getTablePosition().getRow()))
	        	            .setAppTableStatus(t.getNewValue()); 
	    updateAppdescription(t.getRowValue().getAppTableApID() , t.getNewValue());
        });
       /* appoitmentData
        appoitmentDataList*/
       try {
    	   getTableData();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
       appoitmentDataList = FXCollections.observableArrayList(appoitmentData);
        if (appTableT != null) {
        	appTableT.setItems(appoitmentDataList);
        } else {
            System.out.println("cancelledAppointmentsTable is null");
        }
        
        try {
			generateNormal();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }

    //-------------------------------------------------------------------------
    public void getCancelled() throws ClassNotFoundException, SQLException {
    	
    	Connection con = DBUtilities.connectDB();
    	System.out.println("getting Cancelled visits");
    	
    	String SQL= "select a.appointmentID,a.patientID,a.nutritionistID,a.pTime from appointment a,"
    			+ " appointmentSatus ap where a.PDate = curdate() and a.statusID = ap.statusID and ap.StatusID = 3";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
       while (rs.next()){
    	   cancelledAppoitmentsData.add(new CancelledAppointments(
        			Integer.parseInt(rs.getString(1)),
        			Integer.parseInt(rs.getString(2)),
        			Integer.parseInt(rs.getString(3)),
        			Time.valueOf(rs.getString(4))
                )
            );
        }
        rs.close();
        stmt.close();
        con.close();
    }
    //-----------------------------------------------------------------------------
    public void getTableData() throws ClassNotFoundException, SQLException {
    	Connection con = DBUtilities.connectDB();
    	System.out.println("getting Appoitments");
    	
    	String SQL= "select a.AppointmentID,a.PatientID,a.NutritionistID,"
    			+ "a.PDate,a.PTime,a.Cost,a.PaymentDate,a.BriefDescription,"
    			+ "a.StatusID from appointment a";
    	
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
       while (rs.next()){
    	   appoitmentData.add(new AppoitmentTable(
    			   Integer.parseInt(rs.getString(1)),
    			   Integer.parseInt(rs.getString(2)),
    			   Integer.parseInt(rs.getString(3)),
    			   Date.valueOf(rs.getString(4)),
    			   Time.valueOf(rs.getString(5)),
    			   Double.parseDouble(rs.getString(6)),
    			   Date.valueOf(rs.getString(7)),
    			   rs.getString(8).toString(), 
    			   Integer.parseInt(rs.getString(9))
                )
            );
        }
        rs.close();
        stmt.close();
        con.close();
    }
    //-------------------------------------------------------------------------------
    public void getLastPatientData() throws SQLException, ClassNotFoundException{

        Connection con = DBUtilities.connectDB();
        System.out.println("getting last visits");

        String SQL = "select p.PatientID,p.FirstName, p.LastName, n.NutritionistID, n.FirstName, n.LastName,  a.PDate, a.PTime, a.BriefDescription from patient p, nutritionist n, appointment a\n" +
                "where a.PatientID = p.PatientID and a.NutritionistID = n.NutritionistID\n" +
                "and datediff(CURDATE(), a.pdate) <= 31 and datediff(CURDATE(), a.pdate) >= 0";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
       
        while (rs.next()){
            lastPatientData.add( new LastPatientAppointments(
                Integer.parseInt(rs.getString(1)),
                rs.getString(2)+" "+rs.getString(3),
                Integer.parseInt(rs.getString(4)),
                rs.getString(5)+" "+rs.getString(6),
                Date.valueOf(rs.getString(7)),
                Time.valueOf(rs.getString(8)),
                rs.getString(9)
                )
            );
            //System.out.println(data.get(0).getPatientName());
        }


        rs.close();
        stmt.close();

        con.close();
        System.out.println("Connection closed" + lastPatientData.size());
        //------------------------------------------
    }

    public void getTodaysAppointmentsData() throws SQLException, ClassNotFoundException{

        Connection con = DBUtilities.connectDB();
        System.out.println("Getting today appoitments");

        String SQL = "select a.AppointmentID, a.PatientID, a.NutritionistID, a.PTime, s.status from appointment a, appointmentsatus s\n" +
                "where s.StatusID = a.StatusID and a.PDate = curdate()";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        
        while (rs.next()){
            todaysAppointmentsData.add( new TodaysAppointmentsTable(
                            Integer.parseInt(rs.getString(1)),
                            Integer.parseInt(rs.getString(2)),
                            Integer.parseInt(rs.getString(3)),
                            Time.valueOf(rs.getString(4)),
                            rs.getString(5)
                    )
            );
        }


        rs.close();
        stmt.close();

        con.close();
        System.out.println("Connection closed" + todaysAppointmentsData.size());
    }

    public void getAppointmentsChartData() throws SQLException, ClassNotFoundException{
        Connection con = DBUtilities.connectDB();
        System.out.println("getting chart data");


        String SQL = "select Month(PDate) as Month, count(appointmentId) \n" +
                "from appointment \n" +
                "where datediff(curdate(), PDate) <= 365 and datediff(curdate(), PDate) >= 0 \n" +
                "group by Month(PDate)  \n" +
                "order by Month";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        XYChart.Series series = new XYChart.Series();
        series.setName("Appointment per month");
        while (rs.next()){
            series.getData().add(
                    new XYChart.Data(getMonthName(Integer.parseInt(rs.getString(1))), Integer.parseInt(rs.getString(2)))
            );
        }
        appointmentsPerMonthChart.getData().addAll(series);
    }
    private String getMonthName(int monthNumber) {
        switch (monthNumber) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "Invalid month number. Please enter a number between 1 and 12.";
        }
    }
    //-----------------------------------------------------------------------------------------
	public void ExecuteStatement(String SQL) throws SQLException, ClassNotFoundException { // Excuting the SQL statemtn Easier than using it in the funciton itself 

		try {
	        Connection con = DBUtilities.connectDB();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close();
		
			 
		}
		catch(SQLException s) {
			s.printStackTrace();
			System.out.println("SQL statement is not executed!");
			  
		}
		
		
	}
    
//------------------------------------------------Update factors SQL:
	//patient ID
	public void updatePatientID(int apID, int patientId) {
		
		try {
			//print on console the update u made
			String sql;
			sql = "update appointment set patientID = " +patientId+" where appointmentID = "+apID;
			System.out.println(sql);
			ExecuteStatement(sql);
			System.out.println("Connection closed");
			
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	//Nutritionist ID
	public void updateNutritionistID(int apID, int nutritionistId) {
		
		try {
			//print on console the update u made
			String sql;
			sql = "update appointment set nutritionistID = " +nutritionistId+" where appointmentID = "+apID;
			System.out.println(sql);
			ExecuteStatement(sql);
			System.out.println("Connection closed");
			
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	//Appointment Date
	public void updateAppDate(int apID, Date apdate) {
		
		try {
			//print on console the update u made
			String sql;
			sql = "update appointment set pdate = '" +apdate+"' where appointmentID = "+apID;
			System.out.println(sql);
			ExecuteStatement(sql);
			System.out.println("Connection closed");
			
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
    //Appointment Time
	public void updateAppTime(int apID, Time aptime) {
		
		try {
			//print on console the update u made
			String sql;
			sql = "update appointment set ptime = '" +aptime+"' where appointmentID = "+apID;
			System.out.println(sql);
			ExecuteStatement(sql);
			System.out.println("Connection closed");
			
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	//cost 
	public void updateAppCost(int apID, double apCost) {
		
		try {
			//print on console the update u made
			String sql;
			sql = "update appointment set cost = " +apCost+" where appointmentID = "+apID;
			System.out.println(sql);
			ExecuteStatement(sql);
			System.out.println("Connection closed");
			
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	//payment Date
	public void updateAppPayDate(int apID, Date apDate) {
		
		try {
			//print on console the update u made
			String sql;
			sql = "update appointment set paymentDate = '" +apDate+"' where appointmentID = "+apID;
			System.out.println(sql);
			ExecuteStatement(sql);
			System.out.println("Connection closed");
			
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	// description update 
	public void updateAppdescription(int apID, String des) {
		
		try {
			//print on console the update u made
			String sql;
			sql = "update appointment set briefDescription = " +des+" where appointmentID = "+apID;
			System.out.println(sql);
			ExecuteStatement(sql);
			System.out.println("Connection closed");
			
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	//appointment status
	public void updateAppdescription(int apID, int stat) {
		
		try {
			//print on console the update u made
			String sql;
			sql = "update appointment set statusID = " +stat+" where appointmentID = "+apID;
			System.out.println(sql);
			ExecuteStatement(sql);
			System.out.println("Connection closed");
			
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	
//----------------------------------------------------------------------------------------------------
	public void getAppoitment(int apID) throws ClassNotFoundException, SQLException {
    	Connection con = DBUtilities.connectDB();
    	System.out.println("Search the Appoitments");
    	
    	String SQL= "select a.AppointmentID,a.PatientID,a.NutritionistID,"
    			+ "a.PDate,a.PTime,a.Cost,a.PaymentDate,a.BriefDescription,"
    			+ "a.StatusID from appointment a where a.AppointmentID ="+apID;
    	appoitmentData.clear();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
       while (rs.next()){
    	   appoitmentData.add(new AppoitmentTable(
    			   Integer.parseInt(rs.getString(1)),
    			   Integer.parseInt(rs.getString(2)),
    			   Integer.parseInt(rs.getString(3)),
    			   Date.valueOf(rs.getString(4)),
    			   Time.valueOf(rs.getString(5)),
    			   Double.parseDouble(rs.getString(6)),
    			   Date.valueOf(rs.getString(7)),
    			   rs.getString(8).toString(), 
    			   Integer.parseInt(rs.getString(9))
                )
            );
        }
       appoitmentDataList = FXCollections.observableArrayList(appoitmentData);
       if (appTableT != null) {
       	appTableT.setItems(appoitmentDataList);
       } else {
           System.out.println("cancelledAppointmentsTable is null");
       }
        rs.close();
        stmt.close();
        con.close();
		
	}
//---------------------------------------------------------------------------------------
    public void getPatients() throws ClassNotFoundException, SQLException {
    	Connection con = DBUtilities.connectDB();
    	System.out.println("getting patients");
    	patientData.clear();
    	
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
   //-----------------------------------------------------------------------------------------
    public void getNutri() throws ClassNotFoundException, SQLException {
    	Connection con = DBUtilities.connectDB();
    	System.out.println("getting nutritionists");
    	nutriData.clear();
    	
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





