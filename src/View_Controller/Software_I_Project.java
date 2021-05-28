/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author brysa
 */
public class Software_I_Project extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    
    /**
    JavaDocs (dist) folder located in BSawchyn_Software_I_Task folder
     */
    public static void main(String[] args) {

        /**
         * InHouse dummyPart1 = new InHouse(1, "aaaaa", 3.99, 5, 2, 4, 3);
         * InHouse dummyPart2 = new InHouse(2, "bbbbb", 1.99, 3, 2, 4, 2);
         * InHouse dummyPart3 = new InHouse(3, "ccccc", 2.99, 10, 2, 4, 1);
         * InHouse dummyPart4 = new InHouse(4, "ddddd", 5.99, 8, 2, 4, 9);
         *
         * Inventory.addPart(dummyPart1); Inventory.addPart(dummyPart2);
         * Inventory.addPart(dummyPart3); Inventory.addPart(dummyPart4);
        * *
         */
        launch(args);
    }

}
