package application;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplementDashboardController implements Initializable {
	@FXML
	private BorderPane supplpanetable;
	@FXML
	private VBox supbox;
    @FXML
    private TableColumn<SupplementExpireTable, Date> EExpireDate;

    @FXML
    private TableColumn<SupplementExpireTable, String> EForm;

    @FXML
    private TableColumn<SupplementExpireTable, String> EName;

    @FXML
    private TableColumn<SupplementExpireTable, Integer> EProductId;

    @FXML
    private TableColumn<SupplementExpireTable, String> ESerialNo;

    @FXML
    private TableView<SupplementExpireTable> ETable;

    private ArrayList<SupplementExpireTable> EData = new ArrayList<>();
    ObservableList<SupplementExpireTable> EList;

    @FXML
    private TableColumn<SupplementMPPTable, String> MPPManufacturer;

    @FXML
    private TableColumn<SupplementMPPTable, String> MPPName;

    @FXML
    private TableColumn<SupplementMPPTable, Double> MPPPrice;

    @FXML
    private TableColumn<SupplementMPPTable, Integer> MPPQuantity;

    @FXML
    private TableColumn<SupplementMPPTable, String> MPPSerialNo;

    @FXML
    private TableView<SupplementMPPTable> MPPTable;

    private ArrayList<SupplementMPPTable> MPPData = new ArrayList<>();
    ObservableList<SupplementMPPTable> MPPList;
    @FXML
    private TableColumn<SupplementOOSTable, String> OOSManufacturer;

    @FXML
    private TableColumn<SupplementOOSTable, String> OOSName;

    @FXML
    private TableColumn<SupplementOOSTable, Integer> OOSProductId;

    @FXML
    private TableColumn<SupplementOOSTable, String> OOSSerialNo;

    @FXML
    private TableView<SupplementOOSTable> OOSTable;

    private ArrayList<SupplementOOSTable> OOSData = new ArrayList<>();
    ObservableList<SupplementOOSTable> OOSList;


    @FXML
    private BarChart<String, Double> monthlyRevenueChart;
    //---------------------------------------------------------------------

    
    //-------------------------------------------------------------------------
	@FXML
	public void toAddSuppage(MouseEvent event) {
		supplpanetable.setCenter(supbox);			
		loadto("addSupplementPage");
	}
	@FXML
	public void modifySup(MouseEvent event) {
		supplpanetable.setCenter(supbox);
	}
	private void loadto(String page) {
		Parent root= null;
		try {
			root = FXMLLoader.load(getClass().getResource(page+".fxml"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		supplpanetable.setCenter(root);
	}
	

	//------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EProductId.setCellValueFactory(new PropertyValueFactory<SupplementExpireTable, Integer>("productId"));
        ESerialNo.setCellValueFactory(new PropertyValueFactory<SupplementExpireTable, String>("serialNo"));
        EName.setCellValueFactory(new PropertyValueFactory<SupplementExpireTable, String>("name"));
        EExpireDate.setCellValueFactory(new PropertyValueFactory<SupplementExpireTable, Date>("expireDate"));
        EForm.setCellValueFactory(new PropertyValueFactory<SupplementExpireTable, String>("form"));

        try{
            getEData();
        }catch (Exception e){
            e.printStackTrace();
        }

        EList = FXCollections.observableArrayList(EData);
        ETable.setItems(EList);


        MPPSerialNo.setCellValueFactory(new PropertyValueFactory<SupplementMPPTable, String>("serialNo"));
        MPPName.setCellValueFactory(new PropertyValueFactory<SupplementMPPTable, String>("name"));
        MPPManufacturer.setCellValueFactory(new PropertyValueFactory<SupplementMPPTable, String>("manufacturer"));
        MPPPrice.setCellValueFactory(new PropertyValueFactory<SupplementMPPTable, Double>("price"));
        MPPQuantity.setCellValueFactory(new PropertyValueFactory<SupplementMPPTable, Integer>("quantity"));

        try{
            getMPPData();
        }catch (Exception e){
            e.printStackTrace();
        }

        MPPList = FXCollections.observableArrayList(MPPData);
        MPPTable.setItems(MPPList);


        OOSSerialNo.setCellValueFactory(new PropertyValueFactory<SupplementOOSTable, String>("serialNo"));
        OOSProductId.setCellValueFactory(new PropertyValueFactory<SupplementOOSTable, Integer>("productId"));
        OOSName.setCellValueFactory(new PropertyValueFactory<SupplementOOSTable, String>("name"));
        OOSManufacturer.setCellValueFactory(new PropertyValueFactory<SupplementOOSTable, String>("manufacturer"));

        try{
            getOOSData();
        }catch (Exception e){
            e.printStackTrace();
        }

        OOSList = FXCollections.observableArrayList(OOSData);
        OOSTable.setItems(OOSList);

        try {
            getRevenueChartData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void getEData() throws SQLException, ClassNotFoundException{
        Connection con = DBUtilities.connectDB();
        System.out.println("Connection established");

        String SQL = "select productid, SerialNo, name, ExpireDate, form \n" +
                "from supplement\n" +
                "where ExpireDate <= curdate()";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()){
            EData.add(
                    new SupplementExpireTable(Integer.parseInt(rs.getString(1)),
                            rs.getString(2),
                            rs.getString(3),
                            Date.valueOf(rs.getString(4)),
                            rs.getString(5)
                    )
            );
        }

        rs.close();
        stmt.close();
        con.close();
        System.out.println("Connection closed" + EData.size());
    }

    public void getMPPData() throws SQLException, ClassNotFoundException{
        Connection con = DBUtilities.connectDB();
        System.out.println("Connection established");

        String SQL = "select s.SerialNo, s.Name, Manufacturer,price,sum(so.Quantity) sales\n" +
                "from supplementorderitem so, supplement s\n" +
                "where so.ProductID = s.ProductID\n" +
                "group by s.ProductID\n" +
                "order by sales desc\n" +
                "limit 10";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()){
            MPPData.add( new SupplementMPPTable(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            Double.parseDouble(rs.getString(4)),
                            Integer.parseInt(rs.getString(5))
                    )
            );
        };

        rs.close();
        stmt.close();
        con.close();
        System.out.println("Connection closed " + MPPData.size());
    }

    public void getOOSData() throws SQLException, ClassNotFoundException{
        Connection con = DBUtilities.connectDB();
        System.out.println("Connection established");

        String SQL = "select productid, serialno, name, manufacturer \n" +
                "from supplement\n" +
                "where quantity = 0";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()){
            OOSData.add( new SupplementOOSTable(
                    Integer.parseInt(rs.getString(1)),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)
                    )
            );
        };

        rs.close();
        stmt.close();
        con.close();
        System.out.println("Connection closed " + OOSData.size());
    }

    public void getRevenueChartData() throws SQLException, ClassNotFoundException{
        Connection con = DBUtilities.connectDB();
        System.out.println("Connection established");

        String SQL = "select Month(so.date) monthly, sum(soi.Quantity * s.price * 0.01*(100-soi.DiscountPercent)* 0.01 *(100-so.SpecialDiscountPercent)) sales\n" +
                "from supplement s, supplementorder so, supplementorderitem soi\n" +
                "where s.ProductID = soi.ProductID \n" +
                "and so.OrderID = soi.OrderID\n" +
                "and datediff(curdate(), so.date) <= 365\n" +
                "and datediff(curdate(), so.date) > 0 \n" +
                "group by monthly\n" +
                "order by monthly;";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        XYChart.Series series = new XYChart.Series();
        series.setName("Revenue per month");
        while (rs.next()){
            series.getData().add(
                    new XYChart.Data(getMonthName(Integer.parseInt(rs.getString(1))), Double.parseDouble(rs.getString(2)))
            );
        }
        monthlyRevenueChart.getData().addAll(series);
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

}
