package application;

import java.io.*;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SignUpPageController {
	@FXML
	private PasswordField password;
	@FXML
	private TextField number;
	@FXML
	private Label checkErro;
	@FXML
	private Button login;
	
	private UsersClass u = UsersClass.getUsers();	
	protected ArrayList<UsersClass> user =  u.getDataList();


	// Event Listener on Button.onMouseClicked
	@FXML
	public void checkSign(MouseEvent event) throws IOException {
		int notThere =0;
		if(!password.getText().isEmpty() && !number.getText().isEmpty()) {
			for (int i = 0; i < user.size(); i++) {
				UsersClass user1 = new UsersClass(number.getText(), password.getText());

				if (user.contains(user1)) { // here we will use equal , since contain have equal (o==null ?
					checkErro.setText("User Already Exsist Try another number");
				}

				else {
					notThere++;
				}
				
			}
			if(notThere == user.size()) {
			
					u.addUser(number.getText(), password.getText());
					checkErro.setText("User Has been added");
					try (BufferedWriter userInformation = new BufferedWriter(new FileWriter("Login.txt"))) {
						for (UsersClass data : user) {
							userInformation.write(data.getId()+" "+data.getPassword());
							userInformation.newLine(); // Add a newline after each data
						}
					}
			}
			
			
			
		}
		else {
		checkErro.setText("Write in both fields");
		}
	}
	// Event Listener on Button[#login].onMouseClicked
	@FXML
	public void backSignin(MouseEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginPage.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();

        Stage stage = (Stage) login.getScene().getWindow();
        stage.close();
        
		Stage stage2 = new Stage();
    	stage2.setTitle("Login");
    	stage2.setScene(new Scene(root1));
    	stage2.show();
	}
}
