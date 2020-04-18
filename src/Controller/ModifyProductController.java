package Controller;

import Model.*;
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

public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    private Button searchPartButton;

    @FXML
    private Button addPartButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    private TableView<Part> AddPartTableview;


    @FXML
    private TableColumn<?, ?> addpartIDColumn;

    @FXML
    private TableColumn<?, ?> addpartNameColumn;

    @FXML
    private TableColumn<?, ?> addinvLevelColumn;

    @FXML
    private TableColumn<?, ?> addpriceColumn;

    @FXML
    private TextField prodNameText;

    @FXML
    private TextField prodInvText;

    @FXML
    private TextField prodPriceText;

    @FXML
    private TextField prodMaxText;

    @FXML
    private TextField prodMinText;

    @FXML
    private TextField searchPartText;

    @FXML
    private TableView<Part> DeletePartTablevew;

    @FXML
    private TableColumn<?, ?> delpartIDColumn;

    @FXML
    private TableColumn<?, ?> delpartNameColumn;

    @FXML
    private TableColumn<?, ?> delinvLevelColumn;

    @FXML
    private TableColumn<?, ?> delpriceColumn;

    @FXML
    private Label prodIDLabel;

    @FXML
    void addPartOnClick(MouseEvent event) {
        try {
            Product.addAssociatedPart(AddPartTableview.getSelectionModel().getSelectedItem());

        } catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select an item to add");
            alert.showAndWait();
        }
    }

    @FXML
    void cancelOnClick(MouseEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to go back? " +
                "Any changes won't be saved");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void deletePartOnClick(MouseEvent event) {
        try {
            Product.deleteAssociatedPart(AddPartTableview.getSelectionModel().getSelectedItem());
        } catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select an item to delete");
            alert.showAndWait();
        }
    }

    @FXML
    void saveOnClick(MouseEvent event) throws IOException {

        int id = Integer.parseInt(prodIDLabel.getText());
        String name = prodNameText.getText();
        int stock = Integer.parseInt(prodInvText.getText());
        double price = Double.parseDouble(prodPriceText.getText());
        int max = Integer.parseInt(prodMaxText.getText());
        int min = Integer.parseInt(prodMinText.getText());

        Inventory.updateProduct(Integer.parseInt(prodIDLabel.getText()), new Product(id, name, price, stock, min, max));

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    } //BROKEN

    @FXML
    void searchOnClick(MouseEvent event) {
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

        AddPartTableview.setItems(filteredParts);
    }

//  Get product details selected from Main Screen to modify
    public void getProdDetails(Product product) {
        prodIDLabel.setText(String.valueOf(product.getProductID()));
        prodNameText.setText(product.getProductName());
        prodInvText.setText(String.valueOf(product.getProductStock()));
        prodPriceText.setText(String.valueOf(product.getProductPrice()));
        prodMaxText.setText(String.valueOf(product.getProductMax()));
        prodMinText.setText(String.valueOf(product.getProductMin()));
        DeletePartTablevew.setItems(product.getAllAssociatedParts());
        //Include one more to populate Delete table with items from associated parts list

    } //THIS NEEDS TO BE FIXED

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//       Set table view data to add parts to product's associated parts
        AddPartTableview.setItems(Inventory.getAllParts());
        addpartIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addpartNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        addinvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        addpriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

//      Set tableview data for associated parts
        DeletePartTablevew.setItems(Product.getAllAssociatedParts());
        delpartIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        delpartNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        delinvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        delpriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
    }
}
