package view;

import java.util.Collection;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Module;

public class ReserveModulesPane extends GridPane {
	
	private ListView<Module> usb3, rb3;
	private Button btnAdd, btnRemove, btnConfirm;
	
	public ReserveModulesPane() {
//		this.setGridLinesVisible(true);
		this.setHgap(10);
		this.setVgap(5);
	        
		this.setPadding(new Insets(40, 40, 40, 40));
			
		ColumnConstraints column1 = new ColumnConstraints();
		ColumnConstraints column2 = new ColumnConstraints();
			
		this.getColumnConstraints().addAll(column1);
		this.getColumnConstraints().addAll(column2);
			
		column1.setPrefWidth(150);
		column2.setPrefWidth(150);
			
		column1.setPercentWidth(100);
		column2.setPercentWidth(100);
		
		//
		Label lblusb3 = new Label("Unselected Block 3/4 modules: ");
		Label lblrb3 = new Label("Reserved Block 3/4 modules: ");
		Label lblroo = new Label("Reserve one optional module: ");
		
		usb3 = new ListView<Module>();
		usb3.setPrefSize(200, 300);
		usb3.setMaxHeight(300);
		
		rb3 = new ListView<Module>();
		rb3.setPrefSize(200, 300);
		rb3.setMaxHeight(300);
		
		btnAdd = new Button("Add");
		btnRemove = new Button("Remove");
		btnConfirm = new Button("Confirm");
		
		ButtonBar btnbar1 = new ButtonBar();
		btnbar1.getButtons().addAll(btnAdd, btnRemove, btnConfirm);
		
		HBox button1 = new HBox(lblroo, btnAdd, btnRemove, btnConfirm);
		button1.setAlignment(Pos.CENTER);
		button1.setSpacing(15);
		
		
		this.add(lblusb3, 0, 0);
		this.add(usb3, 0, 1);
		
		this.add(lblrb3, 1, 0);
		this.add(rb3, 1, 1);
		
		
		this.add(button1, 0, 3, 2, 2);
	
	}
	
	public void addUnselectedBlock3(Collection<Module> modules) {
		for (Module a: modules) {		
				usb3.getItems().addAll(a);		
			
		}
	}
	
	public void addReverseBlock(Collection<Module> modules) {
		rb3.getItems().addAll(modules);	
	}
	
	
	public ListView<Module> getUnSelectedBlock3() {
		return usb3;
	} 
	public ListView<Module> getReserveBlock() {
		return rb3;
	}
	
	public Collection<Module> getReserveBlockCollection() {
		Collection<Module> moduleCollection = rb3.getItems();
		return moduleCollection;
	}
	
	public void addAddReserveHandler(EventHandler<ActionEvent> handler) {
		btnAdd.setOnAction(handler);
	}
	
	public void addRemoveReserveHandler(EventHandler<ActionEvent> handler) {
		btnRemove.setOnAction(handler);
	}
	
	public void addConfirmHandler(EventHandler<ActionEvent> handler) {
		btnConfirm.setOnAction(handler);
	}
	


	
}
