/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

/**
 *
 * @author brysa
 */

/**

 * 
 * FUTURE ENHANCEMENT:
 * 
 * A method to filter parts that do not have a product association. This could be used to get an idea of "useless" parts to potentially clear out or organize the inventory.
 *  RUNTIME-ERROR:
 This class was straightforward from the UML diagram reference. I did not receive errors in this class.
 */
public class Inventory {

    @FXML
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    @FXML
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static void addPart(Part newPart) {
        allParts.add(newPart);

    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static boolean deletePart(Part part) {
        allParts.remove(part);
        return true;
    }

    public static Part lookupPart(int partId) {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {

        ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                filteredParts.add(part);
            }
        }
        return filteredParts;
    }

    public static Product lookupProduct(int productId) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public static ObservableList<Product> lookupProduct(String productName) {

        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }

}
