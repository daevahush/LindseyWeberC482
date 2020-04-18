package Main;

import Model.InHouse;
import Model.Inventory;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryManager extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Controller/MainScreen.fxml"));
        primaryStage.setScene(new Scene(root, 963, 493));
        primaryStage.show();
    }


    public static void main(String[] args) {

        //create parts and products
        InHouse part1 = new InHouse(1, "mako", 10.00, 15, 0, 99, 5);
        InHouse part2 = new InHouse(2, "leather bracer", 10.00, 3, 0, 99, 22);
        InHouse part3 = new InHouse(3, "titanium bracelet", 100.00, 1, 0, 99, 42);
        InHouse part4 = new InHouse(4, "crescent earring", 5.00, 1, 0, 99, 6);

        Product prod1 = new Product(1, "materia", 500.00, 3, 0, 10);
        prod1.addAssociatedPart(part1);

        Product prod2 = new Product(2, "buster sword", 500.00, 3, 0, 10);
        Product prod3 = new Product(3, "leather gloves", 500.00, 3, 0, 10);
        Product prod4 = new Product(4, "masamune", 500.00, 3, 0, 10);


        //add them to observable list
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addProduct(prod1);
        Inventory.addProduct(prod2);
        Inventory.addProduct(prod3);
        Inventory.addProduct(prod4);


        launch(args);
    }

}


