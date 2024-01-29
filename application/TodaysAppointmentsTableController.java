package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TodaysAppointmentsTableController implements Initializable {

    @FXML
    private TableView<TodaysAppointmentsTable> todaysAppointmentsTable;
    @FXML
    private TableColumn<TodaysAppointmentsTable, Integer> appointmentId;

    @FXML
    private TableColumn<TodaysAppointmentsTable, Integer> patientId;

    @FXML
    private TableColumn<TodaysAppointmentsTable, Integer> nutritionistId;

    @FXML
    private TableColumn<TodaysAppointmentsTable, Time> time;

    @FXML
    private TableColumn<TodaysAppointmentsTable, String> status;

    private ArrayList<TodaysAppointmentsTable> todaysAppointmentsData = new ArrayList<>();
    ObservableList<TodaysAppointmentsTable> todaysAppointmentsList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentId.setCellValueFactory(new PropertyValueFactory<TodaysAppointmentsTable,Integer>("appointmentId"));
        patientId.setCellValueFactory(new PropertyValueFactory<TodaysAppointmentsTable,Integer>("patientId"));
        nutritionistId.setCellValueFactory(new PropertyValueFactory<TodaysAppointmentsTable,Integer>("nutritionistId"));
        time.setCellValueFactory(new PropertyValueFactory<TodaysAppointmentsTable,Time>("time"));
        status.setCellValueFactory(new PropertyValueFactory<TodaysAppointmentsTable,String>("status"));

        try{
            getTodaysAppointmentsData();
        }catch (Exception e){
            e.printStackTrace();
        }

        todaysAppointmentsList = FXCollections.observableArrayList(todaysAppointmentsData);
        todaysAppointmentsTable.setItems(todaysAppointmentsList);
    }

    public void getTodaysAppointmentsData() throws SQLException, ClassNotFoundException{

        Connection con = DBUtilities.connectDB();
        System.out.println("Connection established");

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
}
