/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsource;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author brysa
 */

/**
  
  FUTURE ENHANCEMENT
  
  I believe a useful future enhancement for this class would be to check if a part already exists by name. 
  It might be easy to get lost in a table full of parts, so a method doing a quick checker could save a possible future error. 
  
  
  
*/


public class Add_PartController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField partIdTextField;
    @FXML
    private TextField partNameTextField;
    @FXML
    private TextField partPriceTextField;
    @FXML
    private TextField partInvTextField;
    @FXML
    private TextField partMinTextField;
    @FXML
    private TextField partMaxTextField;

    @FXML
    private TextField specialTextField;

    @FXML
    private RadioButton inhouseRadio;
    @FXML
    private RadioButton outsourceRadio;
    private ToggleGroup group = new ToggleGroup();

    @FXML
    private Label machineIdLabel;
    @FXML
    private Label companyNameLabel;

    private static int id;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        inhouseRadio.setToggleGroup(group);
        outsourceRadio.setToggleGroup(group);

        inhouseRadio.setSelected(true);

        partIdTextField.setDisable(true);

        partIdTextField.setText("Aut-Gen Disabled");

        companyNameLabel.setVisible(false);
        machineIdLabel.setVisible(true);

        specialTextField.setVisible(true);

    }

    public void cancelScreenButtonPushed(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all textfield values, do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }

    }

    public void inHouseOrOutsource() {
        if (inhouseRadio.isSelected()) {
            machineIdLabel.setVisible(true);

            specialTextField.setVisible(true);

            companyNameLabel.setVisible(false);

        } else if (outsourceRadio.isSelected()) {

            companyNameLabel.setVisible(true);
            machineIdLabel.setVisible(false);

            specialTextField.setVisible(true);
        }

    }
    
    /**  RUNTIME-ERROR
  
  I was running into an issue where my min/max/stock conditional statement was not working when I was saving the part.
  To correct the error, I had to move my if statement after the declared set of variables. 
*/

    public void savePart(ActionEvent event) throws IOException {

        try {
            
            String name = partNameTextField.getText();

            double price = Double.parseDouble(partPriceTextField.getText());
            int stock = Integer.parseInt(partInvTextField.getText());
            int min = Integer.parseInt(partMinTextField.getText());
            int max = Integer.parseInt(partMaxTextField.getText());

            if ((max >= min) && (min <= max) && (stock >= min) && (stock <= max)) {

                id++;
                
                if (inhouseRadio.isSelected()) {
                    int machineId = Integer.parseInt(specialTextField.getText());
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                }

                if (outsourceRadio.isSelected()) {
                    String companyName = specialTextField.getText();
                    Inventory.addPart(new Outsource(id, name, price, stock, min, max, companyName));

                }

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Check MIN must be less than MAX, Inventory must be between MIN and MAX");
                alert.showAndWait();

            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for each text field");
            alert.showAndWait();
        }

    }
}
