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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

* FUTURE ENHANCEMENT
* 
* I believe a label that would show which products the part is associated with would be useful. Having a quick look at the associated products 
* could be useful information when updating or modifying parts. 
* 
 */


public class Modify_PartController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton inhouseRadio;
    @FXML
    private RadioButton outsourceRadio;
    @FXML
    private Label machineIdLabel;
    @FXML
    private TextField partIdTextField;
    @FXML
    private TextField partNameTextField;
    @FXML
    private TextField partInvTextField;
    @FXML
    private TextField partPriceTextField;
    @FXML
    private TextField partMaxTextField;
    @FXML
    private TextField partMinTextField;
    @FXML
    private Label companyNameLabel;
    @FXML
    private TextField specialTextField;

    private ToggleGroup group = new ToggleGroup();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        inhouseRadio.setToggleGroup(group);
        outsourceRadio.setToggleGroup(group);

        partIdTextField.setDisable(true);

    }

    @FXML
    private void inHouseOrOutsource(ActionEvent event) {
        if (inhouseRadio.isSelected()) {
            machineIdLabel.setVisible(true);

            //partMachineIdTextField.setVisible(true);
            specialTextField.setVisible(true);

            companyNameLabel.setVisible(false);

        } else if (outsourceRadio.isSelected()) {

            companyNameLabel.setVisible(true);
            machineIdLabel.setVisible(false);

            //partMachineIdTextField.setVisible(false);
            specialTextField.setVisible(true);
        }

    }
    
    /**
     RUNTIME-ERROR: 
      I had gotten an error when updating the part. The machineID and companyName variables were not being properly updated. 

      To fix the error, I realized that I had to declare the machineId and companyName variables within the conditional radio button statements. 
     They had immediately precede the updatePart method. Doing this, it would only declare the variable if the corresponding radio button was selected,
     allowing the variable to get the correct data from the "specialTextField".

     */

    @FXML
    private void updateButtonPushed(ActionEvent event) throws IOException {

        try {

            int index = MainController.getSavedIndex();

            int id = Integer.parseInt(partIdTextField.getText());

            String name = partNameTextField.getText();
            double price = Double.parseDouble(partPriceTextField.getText());
            int stock = Integer.parseInt(partInvTextField.getText());
            int min = Integer.parseInt(partMinTextField.getText());
            int max = Integer.parseInt(partMaxTextField.getText());

            if ((max >= min) && (min <= max) && (stock >= min) && (stock <= max)) {

                if (inhouseRadio.isSelected()) {
                    int machineId = Integer.parseInt(specialTextField.getText());
                    Inventory.updatePart(index, new InHouse(id, name, price, stock, min, max, machineId));
                }
                if (outsourceRadio.isSelected()) {
                    String companyName = specialTextField.getText();
                    Inventory.updatePart(index, new Outsource(id, name, price, stock, min, max, companyName));
                }

                Parent tableViewParent = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
                Scene tableViewScene = new Scene(tableViewParent);

                //This line gets the Stage information
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();
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

    @FXML
    private void cancelScreenButtonPushed(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

    }

    public void sendPart(Part part) {

        partIdTextField.setText(String.valueOf(part.getId()));
        partNameTextField.setText(part.getName());
        partInvTextField.setText(String.valueOf(part.getStock()));
        partPriceTextField.setText(String.valueOf(part.getPrice()));
        partMinTextField.setText(String.valueOf(part.getMin()));
        partMaxTextField.setText(String.valueOf(part.getMax()));

        if (part instanceof InHouse) {
            companyNameLabel.setVisible(false);

            inhouseRadio.setSelected(true);
            specialTextField.setText(String.valueOf(((InHouse) part).getMachineId()));

        }
        if (part instanceof Outsource) {
            machineIdLabel.setVisible(false);

            outsourceRadio.setSelected(true);
            specialTextField.setText(((Outsource) part).getCompanyName());
        }

    }

}
