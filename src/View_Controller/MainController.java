/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
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
 */
/**
 *
 *
 *
 * FUTURE EHANCEMENT:
 *
 * I believe it would be nice to expand the table and see the other data
 * associated with the parts or products, such as the inventory, min, and max
 * values, rather than having to go into the modify section to see the extra
 * data. Perhaps a quick set of labels to show that information would be work.
 */
public class MainController implements Initializable {

    Stage stage;
    Parent scene;

    Product selectedProduct = new Product();

    @FXML
    private TableView<Product> tableViewProducts;

    @FXML
    private TableColumn<Part, Integer> tableViewProduct_Id;
    @FXML
    private TableColumn<Part, String> tableViewProduct_Name;
    @FXML
    private TableColumn<Part, Double> tableViewProduct_Price;
    @FXML
    private TableColumn<Part, Integer> tableViewProduct_Inv;
    @FXML
    private TextField searchFieldProduct;

    @FXML
    private TableView<Part> tableViewParts;

    @FXML
    private TableColumn<Part, Integer> tableView_Id;
    @FXML
    private TableColumn<Part, String> tableView_Name;
    @FXML
    private TableColumn<Part, Double> tableView_Price;
    @FXML
    private TableColumn<Part, Integer> tableView_Inv;
    @FXML
    private TextField searchField;
    @FXML
    private TextField productSearchField;

    private static int savedIndex;
    private static int savedProductIndex;

    public static int getSavedIndex() {
        return savedIndex;
    }

    public int getSavedProductIndex() {
        return savedProductIndex;
    }

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

    public void searchProductButtonPushed() {

        String q = productSearchField.getText();

        ObservableList<Product> products = Inventory.lookupProduct(q);

        tableViewProducts.setItems(products);

        productSearchField.setText("");

        if (products.size() == 0)
            
            
            try {

            {

                int index = Integer.parseInt(q);
                Product product = Inventory.lookupProduct(index);

                if (product != null) {
                    products.add(product);
                } else {
                tableViewProducts.setItems(Inventory.getAllProducts());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error dialog");
                    alert.setContentText("No product found");
                    alert.showAndWait();

                }
            }
        } catch (NumberFormatException e) {
         tableViewProducts.setItems(Inventory.getAllProducts());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error dialog");
                    alert.setContentText("No product found");
                    alert.showAndWait();

        }

    }

    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/Add_Part.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public void deleteButtonPushed() {

        if (tableViewParts.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a part");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to want to DELETE this PART?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(tableViewParts.getSelectionModel().getSelectedItem());
            }
        }

    }

    public void deleteProductButtonPushed() {
        if (tableViewProducts.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a product");
            alert.showAndWait();
        } else {

            if (tableViewProducts.getSelectionModel().getSelectedItem().getAssociatedParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to want to DELETE this PRODUCT?");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(tableViewProducts.getSelectionModel().getSelectedItem());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Cannot delete a product with associated parts");
                alert.showAndWait();

            }
        }

    }

    /**
     *
     * RUNTIME-ERROR:
     *
     * I was running into a problem when I would accidentally click Modify part
     * or Modify product without a part or product selected, and would get an
     * error. To fix this I added a try/catch to the code, where if it ran into
     * a NullPointerException error, it would prompt an ERROR dialog box
     * Notifying the user to select a part or product. *
     */
    public void modifyScreenButtonPushed(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View_Controller/Modify_Part.fxml"));
            loader.load();

            Modify_PartController MPController = loader.getController();

            MPController.sendPart(tableViewParts.getSelectionModel().getSelectedItem());

            savedIndex = tableViewParts.getSelectionModel().getSelectedIndex();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();

            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a part");
            alert.showAndWait();
        }

    }

    public void modifyProductScreenButtonPushed(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View_Controller/Modify_Product.fxml"));
            loader.load();

            Modify_ProductController MProdController = loader.getController();

            MProdController.sendProduct(tableViewProducts.getSelectionModel().getSelectedItem());

            savedProductIndex = tableViewProducts.getSelectionModel().getSelectedIndex();

            savedIndex = tableViewProducts.getSelectionModel().getSelectedIndex();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();

            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a product");
            alert.showAndWait();
        }

    }

    public void addProductScreenButtonPushed(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/Add_Product.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tableViewProduct_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableViewProduct_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableViewProduct_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableViewProduct_Inv.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tableViewProducts.setItems(Inventory.getAllProducts());

        tableView_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableView_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableView_Inv.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tableViewParts.setItems(Inventory.getAllParts());

    }

    public static int savedProductIndex() {
        return savedProductIndex;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void exitButtonPushed(ActionEvent event) {
        System.exit(0);
    }

}
