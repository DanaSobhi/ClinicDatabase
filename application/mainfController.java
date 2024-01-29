package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.BorderPane;

public class mainfController {
	@FXML
	private BorderPane bp;
	@FXML
	private AnchorPane ap;

	// Event Listener on Button.onMouseClicked
	@FXML
	public void homePage(MouseEvent event) {
		bp.setCenter(ap);
	}
	@FXML
	public void appointmentPage(MouseEvent event) {
		loadPage("applicationPage");
	}
	// Event Listener on Button.onMouseClicked
	@FXML
	public void supplementPage(MouseEvent event) {
		loadPage("supplementPage");
	}
	// Event Listener on Button.onMouseClicked
	@FXML
	public void mhistoryPage(MouseEvent event) {
		bp.setCenter(ap);
	}
	// Event Listener on Button.onMouseClicked
	@FXML
	public void refreshDB(MouseEvent event) {
		bp.setCenter(ap);
	}
	
	private void loadPage(String page) {
		Parent root= null;
		try {
			root = FXMLLoader.load(getClass().getResource(page+".fxml"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bp.setCenter(root);
	}

}
