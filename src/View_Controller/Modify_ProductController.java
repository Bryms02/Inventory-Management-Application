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
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author brysa
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
/**
 * FUTURE ENHANCEMENT:
 *
 * I believe a useful future enhancement would be to set labels to how many
 * associated parts there are of each particular part. It seems if a product may
 * contain 5 or more of the same associated parts, the table can get
 * overpopulated quickly. I think a quick label to show the exact number of
 * associated parts on each product would benefit this application.  *
 */
public class Modify_ProductController implements Initializable {

    Stage stage;
    Parent scene;
    private Product product = null;

    @FXML
    private TableView<Part> tableViewParts;
    @FXML
    private TableView<Part> tableViewAssociatedParts;

    @FXML
    private TableColumn<Part, Integer> tableView_Id;
    @FXML
    private TableColumn<Part, String> tableView_Name;
    @FXML
    private TableColumn<Part, Double> tableView_Price;
    @FXML
    private TableColumn<Part, Integer> tableView_Inv;

    @FXML
    private TableColumn<Part, Integer> tableViewAssociated_Id;
    @FXML
    private TableColumn<Part, String> tableViewAssociated_Name;
    @FXML
    private TableColumn<Part, Double> tableViewAssociated_Price;
    @FXML
    private TableColumn<Part, Integer> tableViewAssociated_Inv;

    @FXML
    private TextField searchField;

    @FXML
    private TextField productIdTextField;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField productInvTextField;
    @FXML
    private TextField productPriceTextField;
    @FXML
    private TextField productMaxTextField;
    @FXML
    private TextField productMinTextField;

    @FXML
    private ObservableList<Part> newProductAssociatedParts = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tableView_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableView_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableView_Inv.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tableViewAssociated_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableViewAssociated_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableViewAssociated_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableViewAssociated_Inv.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productIdTextField.setDisable(true);

        tableViewParts.setItems(Inventory.getAllParts());

    }

    @FXML
    private void removeAssociatedPartButtonPushed(ActionEvent event) {

        if (tableViewAssociatedParts.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a part");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to want to REMOVE this associated part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                product.getAssociatedParts().remove(tableViewAssociatedParts.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    private void addAssociatedPartButtonPushed(ActionEvent event) {

        if (tableViewParts.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a part");
            alert.showAndWait();
        } else {
            product.addAssociatedPart(tableViewParts.getSelectionModel().getSelectedItem());
        }

    }

    /**
     *
     */
    @FXML
    public void searchButtonPushed() {


        String q = searchField.getText();

        ObservableList<Part> parts = Inventory.lookupPart(q);

        tableViewParts.setItems(parts);

        searchField.setText("");

        if (parts.size() == 0){
            try {

            {

                int index = Integer.parseInt(q);
                Part part = Inventory.lookupPart(index);
                if (part != null) {
                    parts.add(part);
                } else {
                    tableViewParts.setItems(Inventory.getAllParts());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error dialog");
                    alert.setContentText("No part found");
                    alert.showAndWait();
                    

                }
            }
        } catch (NumberFormatException e) {
            tableViewParts.setItems(Inventory.getAllParts());
            Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error dialog");
                    alert.setContentText("No part found");
                    alert.showAndWait();


        }
            

    }
    }

    @FXML
    private void saveProductButtonPushed(ActionEvent event) throws IOException {

        try {

            int index = MainController.savedProductIndex();

            product.setId(Integer.parseInt(productIdTextField.getText()));
            product.setName(productNameTextField.getText());
            product.setPrice(Double.parseDouble(productPriceTextField.getText()));
            product.setStock(Integer.parseInt(productInvTextField.getText()));
            product.setMin(Integer.parseInt(productMinTextField.getText()));
            product.setMax(Integer.parseInt(productMaxTextField.getText()));

            if ((product.getMax() >= product.getMin()) && (product.getMin() <= product.getMax()) && (product.getStock() >= product.getMin()) && (product.getStock() <= product.getMax())) {

                Inventory.updateProduct(index, product);

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

    /**
     * RUNTIME-ERROR:
     *
     * One main issue I was running into was getting my associated parts table
     * to populate according to the product. I would send the data over from the
     * Main Controller, and the text-fields would fill in properly, however the
     * associated parts table was not be getting the correct object for its
     * parts. It took me some time, but I realized I was never sending that
     * object itself.      *
     * Adding "this.product = product" in the "sendProduct() method fixed it,
     * allowing for our newly created product to become the product that we are
     * sending, thus being able to get the proper associated parts data along
     * with it.
     */
    public void sendProduct(Product product) {

        this.product = product;

        productIdTextField.setText(String.valueOf(product.getId()));
        productNameTextField.setText(product.getName());
        productInvTextField.setText(String.valueOf(product.getStock()));
        productPriceTextField.setText(String.valueOf(product.getPrice()));
        productMinTextField.setText(String.valueOf(product.getMin()));
        productMaxTextField.setText(String.valueOf(product.getMax()));

        tableViewAssociatedParts.setItems(product.getAssociatedParts());

    }

}
