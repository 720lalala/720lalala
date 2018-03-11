package cy.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import org.controlsfx.dialog.Dialogs;

import cy.makery.address.MainApp;
import cy.makery.address.model.Person;
import cy.makery.address.util.DateUtil;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PersonOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	// 用两列初始化人员表格。
        firstNameColumn.setCellValueFactory(
        		cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(
        		cellData -> cellData.getValue().lastNameProperty());
        
        // 清除人物细节
        showPersonDetails(null);

        // 收听选择更改并在更改时显示个人详情。
		personTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        personTable.setItems(mainApp.getPersonData());
    }
    
    /**
	 *填写所有文本字段以显示有关此人的详细信息。
	 *如果指定的人员为空，则清除所有文本字段。
	 *
	 * @param person the person or null
     */
    private void showPersonDetails(Person person) {
    	if (person != null) {
    		// 用人物对象的信息填充标签。
    		firstNameLabel.setText(person.getFirstName());
    		lastNameLabel.setText(person.getLastName());
    		streetLabel.setText(person.getStreet());
    		postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
    		cityLabel.setText(person.getCity());
			// TODO：我们需要一种方法将生日转换为字符串！
    		birthdayLabel.setText(DateUtil.format(person.getBirthday()));
    	} else {
    		//  birthdayLabel.setText（...）;
    		firstNameLabel.setText("");
    		lastNameLabel.setText("");
    		streetLabel.setText("");
    		postalCodeLabel.setText("");
    		cityLabel.setText("");
    		birthdayLabel.setText("");
    	}
    }

	/**
	 *当用户点击删除按钮时调用。
	 */
	@FXML
	private void handleDeletePerson() {
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			personTable.getItems().remove(selectedIndex);
		} else {
			//没有选择
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table");
			alert.showAndWait();

		}
	}
	
	/**
	 *当用户点击新按钮时调用。打开一个对话框进行编辑
	 *新人的详细信息。
	 */
	@FXML
	private void handleNewPerson() {
		Person tempPerson = new Person();
		boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
		if (okClicked) {
			mainApp.getPersonData().add(tempPerson);
		}
	}

	/**
	 *当用户点击编辑按钮时调用。打开一个对话框进行编辑
	 *所选人员的详细信息。
	 */
	@FXML
	private void handleEditPerson() {
		Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
			if (okClicked) {
				showPersonDetails(selectedPerson);
			}

		} else {
			//没有选择。
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");
			alert.showAndWait();

		}
	}
}