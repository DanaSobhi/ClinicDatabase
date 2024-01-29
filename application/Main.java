package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("homeScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }

    public static void trySql() throws SQLException, ClassNotFoundException{
        String SQL;

        Connection con = DBUtilities.connectDB();
        System.out.println("Connection established");

        SQL = "select p.PatientID, p.FirstName, p.LastName, ps.Status \n" +
                "from patient p, patientstatus ps\n" +
                "where p.StatusID = ps.StatusID";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while(rs.next())
            System.out.println(rs.getString(1) + " "
                    + rs.getString(2) + " " + rs.getString(3) + " "
                    + rs.getString(4));


    }
}