package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginPageController extends Application implements Initializable{
	@FXML
	private PasswordField password;
	@FXML
	private TextField number;
	@FXML
	private Label checkErro;
	@FXML
	private Button login;
	private UsersClass u = UsersClass.getUsers();	
	protected ArrayList<UsersClass> user = u.getDataList();

	// Event Listener on Button.onMouseClicked
	@FXML
	public void signUp(MouseEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SignUpPage.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();

        Stage stage = (Stage) login.getScene().getWindow();
        stage.close();
        
		Stage stage2 = new Stage();
    	stage2.setTitle("SignUP!");
    	stage2.setScene(new Scene(root1));
    	stage2.show();

	}
	// Event Listener on Button[#login].onMouseClicked
	@FXML
	public void checkLog(MouseEvent event) throws IOException {
		
		int notThere =0;
		
		if(!password.getText().isEmpty() && !number.getText().isEmpty()) {
		for (int i = 0; i < user.size(); i++) {
			UsersClass user1 = new UsersClass(number.getText(), password.getText());

			if (user.contains(user1)) { // here we will use equal , since contain have equal (o==null ?
				if(user.get(i).getPassword().equals(password.getText())) {
					FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("homeScreen.fxml"));
					Parent root1 = (Parent) fxmlLoader.load();
			        Stage stage = (Stage) login.getScene().getWindow();
			        stage.close();
			        
					Stage stage2 = new Stage();
		        	stage2.setTitle("Welcome!");
		        	stage2.setScene(new Scene(root1));
		        	stage2.show();
				}
				else
				{
					checkErro.setText("Wrong Password");
				}
					}
			else {
				notThere++;
			}
			}
		if(notThere == user.size()) {
			checkErro.setText("User Doesnt Exist please sign up to enter");
		}
		}	
		else {
			checkErro.setText("Please enter in both fields");
			
		}
		
		}
		
	

@Override
 public void initialize(URL url, ResourceBundle resourceBundle) {
	File userInformation = new File("Login.txt");
	Scanner input;
	try {
		input = new Scanner(userInformation); // read the file
		while (input.hasNext()) { // while the scanner read 
			String temp = input.nextLine();
			String[] temps = temp.split(" "); // split them with spaces
			u.addUser(temps[0], temps[1]);
			// add the customer to the database
		}
	} catch (FileNotFoundException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
	
	
    	
    }
@Override
public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login!");
        stage.setScene(scene);
        stage.show();
    }

public static void main(String[] args) {
        launch();

    }
}
