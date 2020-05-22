package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class MainScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button searchPartButton;

    @FXML
    private Button addPartButton;

    @FXML
    private Button modifyPartButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private TextField searchPartText;

    @FXML
    private TableView<Part> partTableview;

    @FXML
    private TableColumn<Part, Integer> partIDColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partInvLevelColumn;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    @FXML
    private Button exitProgramButton;

    @FXML
    private Button searchProdButton;

    @FXML
    private Button addProductButton;

    @FXML
    private Button modifyProductButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private TextField searchProdText;

    @FXML
    private TableView<Product> prodTableview;

    @FXML
    private TableColumn<Product, Integer> prodIDColumn;

    @FXML
    private TableColumn<Product, String> prodNameColumn;

    @FXML
    private TableColumn<Product, Integer> prodInvLevelColumn;

    @FXML
    private TableColumn<Product, Double> prodPriceColumn;

    @FXML
    void deletePartOnClick(MouseEvent event) {
        try {
            Part selectedPart = partTableview.getSelectionModel().getSelectedItem();
            Predicate<Product> hasPart = (product) -> product.getAllAssociatedParts().contains(selectedPart);
            ObservableList<Product> productsWithPart = Inventory.getAllProducts().filtered(hasPart);

            if(productsWithPart.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure you want to delete this item? ");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    Inventory.deletePart(selectedPart);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("This part is in use by a product");
                alert.showAndWait();
            }

        } catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select an item to delete");
            alert.showAndWait();
        }
    }

    @FXML
    void deleteProdOnClick(MouseEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete this item? ");

            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK) {
                Inventory.deleteProduct(prodTableview.getSelectionModel().getSelectedItem());
            }

        } catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select an item to delete");
            alert.showAndWait();
        }
    }

    @FXML
    void displayAddPartOnClick(MouseEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../View/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void displayAddProdOnClick(MouseEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../View/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void displayModifyPartOnClick(MouseEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/ModifyPart.fxml"));
            loader.load();

            ModifyPartController ModPartController = loader.getController();
            Part selectedItem = partTableview.getSelectionModel().getSelectedItem();
            int itemIndex = partTableview.getSelectionModel().getSelectedIndex();

            ModPartController.getPartDetails(selectedItem, itemIndex);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select an item to modify");
            alert.showAndWait();
        }

    }

    @FXML
    void displayModifyProdOnClick(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController ModProdController = loader.getController();
            Product selectedItem = prodTableview.getSelectionModel().getSelectedItem();
            int itemIndex = prodTableview.getSelectionModel().getSelectedIndex();

            ModProdController.getProdDetails(selectedItem, itemIndex);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select an item to modify");
            alert.showAndWait();
        }
    }

    @FXML
    void exitProgramOnClick(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to exit");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    void searchPartOnClick(MouseEvent event) {
        String query = searchPartText.getText();
        ObservableList<Part> filteredParts = Inventory.lookupPart(query);

        if(filteredParts.size() == 0) {
            try {
                int id = Integer.parseInt(query);
                Part part = Inventory.lookupPart(id);
                if(part != null) {
                    filteredParts.add(part);
                }
            } catch(NumberFormatException e) {
                //insert error alert here
            }
        }

        partTableview.setItems(filteredParts);
    }

    @FXML
    void searchProdOnClick(MouseEvent event) {
        String query = searchProdText.getText();
        ObservableList<Product> filteredProducts = Inventory.lookupProduct(query);

        if(filteredProducts.size() == 0) {
            try {
                int id = Integer.parseInt(query);
                Product product = Inventory.lookupProduct(id);
                if(product != null) {
                    filteredProducts.add(product);
                }
            } catch(NumberFormatException e) {
                //Catching error if nothing found for ID, empty table being returned either way
            }
        }

        prodTableview.setItems(filteredProducts);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Direct table view to get data from observable list in Inventory file using get parts method
        partTableview.setItems(Inventory.getAllParts());

        //direct table columns on which values to get for each attribute
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        //Same deal but for the products side
        prodTableview.setItems(Inventory.getAllProducts());
        prodIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        prodNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        prodInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        prodPriceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
    }
}
