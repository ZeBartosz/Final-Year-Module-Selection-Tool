package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class OverviewSelectionPane extends GridPane {

	private TextArea profile, selectedModules, reservedModule;
	private Button so;
	
	public OverviewSelectionPane() {
//		this.setGridLinesVisible(true);
		this.setHgap(10);
		this.setVgap(5);
        
		this.setPadding(new Insets(40, 40, 40, 40));
		
		ColumnConstraints column1 = new ColumnConstraints();
		ColumnConstraints column2 = new ColumnConstraints();
		
		this.getColumnConstraints().addAll(column1);
		this.getColumnConstraints().addAll(column2);
		
		
		column1.setPrefWidth(500);
		column2.setPrefWidth(500);
		
		column1.setPercentWidth(100);
		column2.setPercentWidth(100);
		
		
		
		profile = new TextArea("Profile will appear here ");
		profile.setEditable(false);		
		profile.setPrefSize(100, 100);
		this.add(profile, 0, 0, 2, 1);
			
		selectedModules = new TextArea("Selected module will appear here ");
		selectedModules.setEditable(false);
		selectedModules.setWrapText(true);
		this.add(selectedModules, 0, 1);
			
		reservedModule = new TextArea("Reserved module will appear here ");
		reservedModule.setEditable(false);
		reservedModule.setWrapText(true);
		this.add(reservedModule, 1, 1);
		
		
		so = new Button("Save Overview");
		this.add(so, 1,3,1,3);
	}
	
	public TextArea getProfile() {
		return profile;
	}
	
	public TextArea getSelectedModules() {
		return selectedModules;
	}
	
	public TextArea getReservedModule() {
		return reservedModule;
	}
	
	public void setProfile(String student) {
		profile.setText(student);
	}
	
	public void setSelectedModules(String student) {
		selectedModules.setText(student);
	}
	
	public void setReservedModule(String student) {
		reservedModule.setText(student);
	}
	
	public void addSaveOverview(EventHandler<ActionEvent> handler) {
		so.setOnAction(handler);
	}
}
