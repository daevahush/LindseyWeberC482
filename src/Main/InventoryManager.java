package Main;

import Model.InHouse;
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryManager extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        primaryStage.setScene(new Scene(root, 900, 800));
//        primaryStage.getStyleSheets().add("/CSS/css.css");
        primaryStage.setTitle("Charlie Foxtrot Inventory Manager");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Inventory.setpartID(1);
        Inventory.setproductID(1);

        // TODO remove this test code when done
        InHouse newPart = new InHouse(5, "part", 5.00, 10, 1, 20, 345);
        Product newProd = new Product(1, "Product", 10.00, 5, 1, 10);

        Inventory.addPart(newPart);
        Inventory.addProduct(newProd);

        launch(args);
    }
}
