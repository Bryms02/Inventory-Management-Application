/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
 */
/**
 *
 *
 * FUTURE ENHANCEMENT: * When adding a associated parts to the product, I would
 * have it remove 1 from the inventory of the part that was just added. If the
 * associated part were to be removed, it would add back to the inventory of
 * that part. *
 *
 */
public class Add_ProductController implements Initializable {

    Stage stage;
    Parent scene;
    Product newProduct = new Product();

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

    /**
     * Initializes the controller class.
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

        productIdTextField.setText("Aut-Gen Disabled");
        productIdTextField.setDisable(true);

        tableViewAssociatedParts.setItems(newProduct.getAssociatedParts());

        tableViewParts.setItems(Inventory.getAllParts());

    }

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
    private void addAssociatedPartButtonPushed(ActionEvent event) {

        if (tableViewParts.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a part");
            alert.showAndWait();
        } else {

            newProduct.addAssociatedPart(tableViewParts.getSelectionModel().getSelectedItem());
        }

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
                newProduct.getAssociatedParts().remove(tableViewAssociatedParts.getSelectionModel().getSelectedItem());
            }
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
     * RUNTIME ERROR:
     *
     * I would receive an error setting the stock, price, min, and max of my
     * newProduct. It was not liking that I was trying to use a string to set
     * the variables of the new product. Therefore, using the Integer.parseInt
     * or Double.parseDouble, the setters were able to work appropriately.
     */
    @FXML
    private void saveProductButtonPushed(ActionEvent event) throws IOException {

        try {
            newProduct.setId(Inventory.getAllProducts().size() + 1);

            newProduct.setName(productNameTextField.getText());
            newProduct.setPrice(Double.parseDouble(productPriceTextField.getText()));
            newProduct.setStock(Integer.parseInt(productInvTextField.getText()));
            newProduct.setMin(Integer.parseInt(productMinTextField.getText()));
            newProduct.setMax(Integer.parseInt(productMaxTextField.getText()));

            if ((newProduct.getMax() >= newProduct.getMin()) && (newProduct.getMin() <= newProduct.getMax()) && (newProduct.getStock() >= newProduct.getMin()) && (newProduct.getStock() <= newProduct.getMax())) {

                Inventory.addProduct(newProduct);

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
