package controller;


import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Block;
import model.Course;
import model.Module;
import model.Name;
import model.StudentProfile;
import view.FinalYearOptionsRootPane;
import view.OverviewSelectionPane;
import view.ReserveModulesPane;
import view.SelectModulesPane;
import view.CreateStudentProfilePane;
import view.FinalYearOptionsMenuBar;

public class FinalYearOptionsController {

	//fields to be used throughout class
	private FinalYearOptionsRootPane view;
	private StudentProfile model;
	
	private CreateStudentProfilePane cspp;
	private FinalYearOptionsMenuBar mstmb;
	private SelectModulesPane smp;
	private ReserveModulesPane rmp;
	private OverviewSelectionPane ovp;

	public FinalYearOptionsController(FinalYearOptionsRootPane view, StudentProfile model) {
		//initialise view and model fields
		this.view = view;
		this.model = model;
		
		//initialise view subcontainer fields
		cspp = view.getCreateStudentProfilePane();
		mstmb = view.getModuleSelectionToolMenuBar();
		smp = view.getSelectModulesPane();
		rmp = view.getReserveModulesPane();
		ovp = view.getOverviewSelectionPane();

		//add courses to combobox in create student profile pane using the buildModulesAndCourses helper method below
		cspp.addCourseDataToComboBox(buildModulesAndCourses());

		//attach event handlers to view using private helper method
		this.attachEventHandlers();
	}

	
	//helper method - used to attach event handlers
	private void attachEventHandlers() {
		//attach an event handler to the create student profile pane
		cspp.addCreateStudentProfileHandler(new CreateStudentProfileHandler());
		mstmb.addAboutHandler(e -> this.alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", null, "Creates a student profile and allows the student to pick the modules for next year"));
		mstmb.addSaveHandler(e -> writeNamesToFile());
		
		smp.addAddHandler(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
   
    			Module selected =  smp.getUnselectedBlock34().getSelectionModel().getSelectedItem();
    			if (smp.getCounter() != 120){   				
    				if (selected != null) {
    					smp.getSelectedBlock34().getItems().add(selected);
    					smp.getUnselectedBlock34().getItems().remove(selected);
    					
    					smp.setCounterBy30(selected.getModuleCredits());
    					smp.setFinalScore(String.valueOf(smp.getCounter()));
    					
    				}
    				
    			}
    		}
        });
		
		smp.addRemoveHandler(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
    
    			Module selected = smp.getSelectedBlock34().getSelectionModel().getSelectedItem();
    			if (!selected.isMandatory()) {
    				if (selected != null) {
    					smp.getUnselectedBlock34().getItems().add(selected);
    					smp.getSelectedBlock34().getItems().remove(selected);
    			
    					smp.setCounter30(selected.getModuleCredits());
						smp.setFinalScore(String.valueOf(smp.getCounter()));
    				}
    			}
    			
    		}
        });
		
		smp.addResetHandler(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		
        		smp.getSelectedBlock1().getItems().clear();
        		smp.getSelectedBlock2().getItems().clear();
        		smp.getUnselectedBlock34().getItems().clear();
        		smp.getSelectedBlock34().getItems().clear();     		
        		
        		smp.addSelectedBlock1(model.getStudentCourse().getAllModulesOnCourse());
    			smp.addSelectedBlock2(model.getStudentCourse().getAllModulesOnCourse());
    			smp.addSelectedBlock34(model.getStudentCourse().getAllModulesOnCourse());
    			smp.addUnselectedBlock34(model.getStudentCourse().getAllModulesOnCourse());
    			
    			smp.setCounter(90);
    			smp.setFinalScore("90");
    			
    		}
        });
		
		smp.addSubmitHandler(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {     		
				
				if(smp.getCounter() != 120) {
					alertDialogBuilder(AlertType.ERROR, "Error Dialog", null, "The credit score must be 120!");
				} else {
					view.getReserveModulesPane().addUnselectedBlock3(smp.getUnselectedBlockCollection34());
        		
        			for (Module a: smp.getSelectedBlockCollection1()) {
        				model.addSelectedModule(a);
        			}
        		
        			for (Module a: smp.getSelectedBlockCollection2()) {
        				model.addSelectedModule(a);
        			}
        		
        			for (Module a: smp.getSelectedBlockCollection34()) {
        				model.addSelectedModule(a);
        			}
        		
        			view.changeTab(2);
				}
    		}
        });
		
		rmp.addAddReserveHandler(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
   
    			Module selected = (rmp.getUnSelectedBlock3()).getSelectionModel().getSelectedItem();
    			int num = 0;
    			for(Module a: rmp.getReserveBlockCollection()) {
    				num = num + 1;
    				
    			}
    			
    			if (num != 1) {
    				if (selected != null) {
    					rmp.getReserveBlock().getItems().add(selected);
    					rmp.getUnSelectedBlock3().getItems().remove(selected);
    					num = 1;
    				}
    			}
    		}
        });
		
		rmp.addRemoveReserveHandler(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
    
        		Module selected = (rmp.getReserveBlock()).getSelectionModel().getSelectedItem();
 
        		
        		if (selected != null) {
        			rmp.getReserveBlock().getItems().remove(selected);
    				rmp.getUnSelectedBlock3().getItems().add(selected);
        		}
    		}
        });
		
		rmp.addConfirmHandler(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		var num = 0;
        		for (Module a: rmp.getReserveBlockCollection()) {
        			model.addReservedModule(a);
        			num = 1;
        		}
    			
        		if(num != 1) {
    				alertDialogBuilder(AlertType.ERROR, "Error Dialog", null, "Need to pick a reserve module!");
    			} else {
    				view.changeTab(3);
        		
        			var text = "Name: " + model.getStudentName().getFullName().toString() + "\nPNo: " + model.getStudentPnumber().toString() + "\nEmail: " + model.getStudentEmail().toString() + "\nDate: " + model.getSubmissionDate().toString() + "\nCourse: " + model.getStudentCourse().toString();
              		ovp.setProfile(text);
              	
              		var modules = "Selected Modules: \n=========";
              		for (Module a: model.getAllSelectedModules()) {
              			modules = modules + "\n" + "Module code: " + a.getModuleCode() + ", Module name: " + a.getModuleName() + ", Module Credits: " + a.getModuleCredits() + ", Mandatory on your course? " + a.isMandatory() + ", Block: " + a.getRunPlan() + "\n";
              		}           	           	
              		ovp.setSelectedModules(modules);
              	
              		var reservedModules = "Reserved modules: \n=========";
              		for (Module a: model.getAllReservedModules()) {
              			reservedModules = reservedModules + "\n" + "Module code: " + a.getModuleCode() + ", Module name: " + a.getModuleName() + ", Module Credits: " + a.getModuleCredits() + ", Mandatory on your course? " + a.isMandatory() + ", Block: " + a.getRunPlan() + "\n";
              		}
              		ovp.setReservedModule(reservedModules);
    			}
              	
    		}
        });
		
		ovp.addSaveOverview(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		try (PrintWriter writer = new PrintWriter(new FileWriter("studentOverview.txt"))) {                                              
                    var text = "Name: " + model.getStudentName().getFullName().toString() + "\nPNo: " + model.getStudentPnumber().toString() + "\nEmail: " + model.getStudentEmail().toString() + "\nDate: " + model.getSubmissionDate().toString() + "\nCourse: " + model.getStudentCourse().toString();
                    writer.println(text);
                    writer.println();

                    var modules = "Selected Modules: \n=========";
                  	for (Module a: model.getAllSelectedModules()) {
                  		modules = modules + "\n" + "Module code: " + a.getModuleCode() + ", Module name: " + a.getModuleName() + ", Module Credits: " + a.getModuleCredits() + ", Mandatory on your course? " + a.isMandatory() + ", Block: " + a.getRunPlan() + "\n";
                  	}           	           	
                  	writer.println(modules);
                  	writer.println();

                  	var reservedModules = "Reserved modules: \n=========";
                  	for (Module a: model.getAllReservedModules()) {
                  		reservedModules = reservedModules + "\n" + "Module code: " + a.getModuleCode() + ", Module name: " + a.getModuleName() + ", Module Credits: " + a.getModuleCredits() + ", Mandatory on your course? " + a.isMandatory() + ", Block: " + a.getRunPlan() + "\n";
                  	}
                  	writer.println(reservedModules);
                  	writer.println();

                    alertDialogBuilder(AlertType.INFORMATION, "Save Success", "Student Overview Saved", "Student overview saved to studentOverview.txt");
                } catch (IOException e1) {
                    e1.printStackTrace();
                    alertDialogBuilder(AlertType.ERROR, "Save Error", "Error Saving Student Overview", "An error occurred while saving the student overview.");
                }
            }
        });
		
		//attach an event handler to the menu bar that closes the application
		mstmb.addExitHandler(e -> System.exit(0));
	}
	
	//event handler (currently empty), which can be used for creating a profile
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			
			Name name = cspp.getStudentName();
			String pnum = cspp.getStudentPnumber();
			String email = cspp.getStudentEmail();
			LocalDate date = cspp.getStudentDate();
			
			if(name.getFirstName().equals("") || name.getFamilyName().equals("") || pnum.equals("") || email.equals("") || date == null) {
				alertDialogBuilder(AlertType.ERROR, "Error Dialog", null, "Need to add all details!");
			} else {
			
			model.setStudentCourse(view.getCreateStudentProfilePane().getSelectedCourse());
			model.setStudentPnumber(view.getCreateStudentProfilePane().getStudentPnumber());
			model.setStudentName(view.getCreateStudentProfilePane().getStudentName());
			model.setStudentEmail(view.getCreateStudentProfilePane().getStudentEmail());
			model.setSubmissionDate(view.getCreateStudentProfilePane().getStudentDate());
			
			
			smp.addSelectedBlock1(model.getStudentCourse().getAllModulesOnCourse());
			smp.addSelectedBlock2(model.getStudentCourse().getAllModulesOnCourse());
			smp.addSelectedBlock34(model.getStudentCourse().getAllModulesOnCourse());
			smp.addUnselectedBlock34(model.getStudentCourse().getAllModulesOnCourse());
			
			view.changeTab(1);
			}
		}
		
		
	}

	
	

	//helper method - builds modules and course data and returns courses within an array
	private Course[] buildModulesAndCourses() {
		Module ctec3701 = new Module("CTEC3701", "Software Development: Methods & Standards", 30, true, Block.BLOCK_1);

		Module ctec3702 = new Module("CTEC3702", "Big Data and Machine Learning", 30, true, Block.BLOCK_2);
		Module ctec3703 = new Module("CTEC3703", "Mobile App Development and Big Data", 30, true, Block.BLOCK_2);

		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, Block.BLOCK_3_4);

		Module ctec3704 = new Module("CTEC3704", "Functional Programming", 30, false, Block.BLOCK_3_4);
		Module ctec3705 = new Module("CTEC3705", "Advanced Web Development", 30, false, Block.BLOCK_3_4);

		Module imat3711 = new Module("IMAT3711", "Privacy and Data Protection", 30, false, Block.BLOCK_3_4);
		Module imat3722 = new Module("IMAT3722", "Fuzzy Logic and Inference Systems", 30, false, Block.BLOCK_3_4);

		Module ctec3706 = new Module("CTEC3706", "Embedded Systems and IoT", 30, false, Block.BLOCK_3_4);


		Course compSci = new Course("Computer Science");
		compSci.addModule(ctec3701);
		compSci.addModule(ctec3702);
		compSci.addModule(ctec3451);
		compSci.addModule(ctec3704);
		compSci.addModule(ctec3705);
		compSci.addModule(imat3711);
		compSci.addModule(imat3722);

		Course softEng = new Course("Software Engineering");
		softEng.addModule(ctec3701);
		softEng.addModule(ctec3703);
		softEng.addModule(ctec3451);
		softEng.addModule(ctec3704);
		softEng.addModule(ctec3705);
		softEng.addModule(ctec3706);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}
	
	private void alertDialogBuilder(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	public void writeNamesToFile() {
        //using try-with-resources statement, the stream is automatically closed when exiting the try block.
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("StudentObjs.dat"))) {
			
           	oos.writeObject(model.getStudentName().getFullName().toString());
           	oos.writeObject(model.getStudentPnumber().toString());
           	oos.writeObject(model.getStudentEmail().toString());
           	oos.writeObject(model.getSubmissionDate().toString());
           	oos.writeObject(model.getStudentCourse().toString());
            oos.close();

            alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", "Save success", "Student saved to StudentObjs.dat");
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Error writing");
        }
    }
	

}
