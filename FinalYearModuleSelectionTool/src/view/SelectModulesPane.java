package view;

import javafx.scene.control.Label;

import java.util.Collection;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Block;
import model.Module;

public class SelectModulesPane extends GridPane{

	private ListView<Module> sb1, sb2, usb4, sb4;
	private TextField finalScore;
	private int counter;
	private Button btnAdd, btnRemove, btnReset, btnSubmit;
	
	public SelectModulesPane() {
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
		counter = 0;
		
		Label lblsb1 = new Label("Selected Block 1 modules: ");
		Label lblsb2 = new Label("Selected Block 2 modules: ");
		Label lblusb4 = new Label("Unselected Block 3/4 modules: ");
		Label lblsb4 = new Label("Selected Block 3/4 modules: ");
		Label lblb = new Label("Block 3/4 ");
		Label lblcc = new Label("Current credits: ");
		
		sb1 = new ListView<Module>();
		sb1.setPrefSize(200, 300);
		sb1.setMaxHeight(300);
		
		sb2 = new ListView<Module>();
		sb2.setPrefSize(200, 300);
		sb2.setMaxHeight(300);
		
		usb4 = new ListView<Module>();
		usb4.setPrefSize(200, 300);
		usb4.setMaxHeight(300);		
		
		sb4 = new ListView<Module>();	
		sb4.setPrefSize(200, 300);
		sb4.setMaxHeight(300);
		
		finalScore = new TextField("0");
		finalScore.setEditable(false);
		finalScore.setPrefSize(40, 10);
		
		btnAdd = new Button("Add");
		btnRemove = new Button("Remove");
		btnReset = new Button("Reset");
		btnSubmit = new Button("Submit");
		
		HBox button1 = new HBox(btnReset, btnSubmit);
		button1.setAlignment(Pos.CENTER);
		button1.setSpacing(15);
		
		HBox lblscore = new HBox(lblcc);
		lblscore.setAlignment(Pos.CENTER_RIGHT);
		
		HBox score = new HBox(finalScore);
		score.setSpacing(10);
		
		GridPane.setHalignment(lblb, HPos.CENTER);
		lblcc.setAlignment(Pos.CENTER);
		
		HBox buttons2 = new HBox(lblb, btnAdd, btnRemove);
		buttons2.setSpacing(10);
		buttons2.setAlignment(Pos.CENTER);
		
		VBox grindListButtons = new VBox(10, usb4, buttons2);
		
		//

		
		
		
		this.add(lblsb1, 0, 0);
		this.add(sb1, 0, 1);
		
		this.add(lblsb2, 0, 2);
		this.add(sb2, 0, 3);
		
		this.add(lblusb4, 1, 0);
		this.add(grindListButtons, 1, 1);
		
		this.add(lblsb4, 1, 2);
		this.add(sb4, 1, 3);
		
		this.add(lblscore, 0, 4);
		this.add(score, 1, 4);
		
		this.add(button1, 0, 6, 2, 2); 		
		
	}
	
	public void addSelectedBlock1(Collection<Module> modules) {
		for (Module a: modules) {
			if (a.getRunPlan() == Block.BLOCK_1) {
				sb1.getItems().addAll(a);
				setCounterBy30(a.getModuleCredits());
				finalScore.setText(String.valueOf(getCounter()));
			}
			
			
		}
	}
	
	public void addSelectedBlock2(Collection<Module> modules) {
		for (Module a: modules) {
			if (a.getRunPlan().equals(Block.BLOCK_2) ) {
				sb2.getItems().addAll(a);
				setCounterBy30(a.getModuleCredits());
				finalScore.setText(String.valueOf(getCounter()));
			}
		}
	}
	
	public void addUnselectedBlock34(Collection<Module> modules) {
		for (Module a: modules) {
			if (a.getRunPlan().equals(Block.BLOCK_3_4) && !a.isMandatory()) {
				usb4.getItems().addAll(a);
			}
		}
	}
	
	public void addSelectedBlock34(Collection<Module> modules) {
		for (Module a: modules) {
			if (a.getRunPlan().equals(Block.BLOCK_3_4) && a.isMandatory()) {
				sb4.getItems().addAll(a);
				setCounterBy30(a.getModuleCredits());
				finalScore.setText(String.valueOf(getCounter()));
			}
		}
	}
	

	
	public ListView<Module> getSelectedBlock1() {
		return sb1;
	}
	
	public ListView<Module> getSelectedBlock2() {
		return sb2;
	}
	
	public ListView<Module> getUnselectedBlock34() {
		return usb4;
	}
	
	public ListView<Module> getSelectedBlock34() {
		return sb4;
	}
	
	public Collection<Module> getSelectedBlockCollection1() {
		Collection<Module> moduleCollection = sb1.getItems();
		return moduleCollection;
	}
	
	public Collection<Module> getSelectedBlockCollection2() {
		Collection<Module> moduleCollection = sb2.getItems();
		return moduleCollection;
	}
	
	public Collection<Module> getUnselectedBlockCollection34() {
		Collection<Module> moduleCollection = usb4.getItems();
		return moduleCollection;
	}
	
	public Collection<Module> getSelectedBlockCollection34() {
		Collection<Module> moduleCollection = sb4.getItems();
		return moduleCollection;
	}
	
	public void setFinalScore(String score) {
		finalScore.setText(score);
	}
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(Integer num) {
		counter = num;
	}
	
	public void setCounterBy30(Integer count) {
		counter = counter + count;
	}
	
	public void setCounter30(Integer count) {
		counter = counter - count;
	}
	
	public void addAddHandler(EventHandler<ActionEvent> handler) {
		btnAdd.setOnAction(handler);
	}
	
	public void addRemoveHandler(EventHandler<ActionEvent> handler) {
		btnRemove.setOnAction(handler);
	}
	
	public void addResetHandler(EventHandler<ActionEvent> handler) {
		btnReset.setOnAction(handler);
	}
	
	public void addSubmitHandler(EventHandler<ActionEvent> handler) {
		btnSubmit.setOnAction(handler);
	}
	
}
