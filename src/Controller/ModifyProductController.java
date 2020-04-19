package Controller;

import Model.*;
import javafx.collections.FXCollections;
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

    private ObservableList<Part> currentParts;
    private Product currentProduct;
    private int itemIndex;

    @FXML
    void addPartOnClick(MouseEvent event) {
        try {
            Part partToAdd = AddPartTableview.getSelectionModel().getSelectedItem();
            int partID = partToAdd.getPartID();
            Boolean partExists = false;
            Double totalPrice = partToAdd.getPartPrice();

            for (int i = 0; i < currentParts.size(); i++) {
                Part part = currentParts.get(i);
                totalPrice += part.getPartPrice();
                if (part.getPartID() == partID) {
                    partExists = true;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("This part is already added");
                    alert.showAndWait();
                    break;
                }
            }

            if (!partExists) {
                currentParts.add(partToAdd);

                try {
                    String currentPriceText = prodPriceText.getText();
                    Double currentPrice = Double.parseDouble(currentPriceText.isEmpty() ? "0" : currentPriceText);
                    prodPriceText.setText(String.valueOf(totalPrice < currentPrice ? currentPrice : totalPrice));
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Invalid price. Numbers only please!");
                    alert.showAndWait();
                }
            }

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
            currentParts.remove(DeletePartTablevew.getSelectionModel().getSelectedItem());
        } catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select an item to delete");
            alert.showAndWait();
        }
    }

    @FXML
    void saveOnClick(MouseEvent event) throws IOException {
        try {
            String name = prodNameText.getText();
            int stock = Integer.parseInt(prodInvText.getText());
            double price = Double.parseDouble(prodPriceText.getText());
            int max = Integer.parseInt(prodMaxText.getText());
            int min = Integer.parseInt(prodMinText.getText());

            //Logical Error Handling
            if(name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please name your product");
                alert.showAndWait();
            } else if(currentParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please add at least one part");
                alert.showAndWait();
            } else if ((stock > max) || (stock < min) || (stock < 0)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter valid inventory value. " +
                        "Cannot be greater or less than minimum and maximum values");
                alert.showAndWait();

            } else if ((min > max) || (min < 0) || (max < 0)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter valid min and max values. " +
                        "Minimum must be less than Maximum and vice versa." +
                        "Values can not be below zero.");
                alert.showAndWait();

            } else if (price <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter valid price. " +
                        "Value can not be zero or below.");
                alert.showAndWait();
            } else {
                currentProduct.setProductName(name);
                currentProduct.setProductStock(stock);
                currentProduct.setProductPrice(price);
                currentProduct.setProductMin(min);
                currentProduct.setProductMax(max);

                currentProduct.getAllAssociatedParts().clear();

                for(Part part : currentParts) {
                    currentProduct.addAssociatedPart(part);
                }

                Inventory.updateProduct(itemIndex, currentProduct);

                //Moves back to Main screen
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter valid values");
            alert.showAndWait();
        }
    }

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
                //Catching error when nothing found, empty table returned
            }
        }

        AddPartTableview.setItems(filteredParts);
    }

//  Get product details selected from Main Screen to modify
    public void getProdDetails(Product product, int index) {
        itemIndex = index;
        currentProduct = product;
        currentParts = FXCollections.observableArrayList(product.getAllAssociatedParts());
        prodIDLabel.setText(String.valueOf(product.getProductID()));
        prodNameText.setText(product.getProductName());
        prodInvText.setText(String.valueOf(product.getProductStock()));
        prodPriceText.setText(String.valueOf(product.getProductPrice()));
        prodMaxText.setText(String.valueOf(product.getProductMax()));
        prodMinText.setText(String.valueOf(product.getProductMin()));
        DeletePartTablevew.setItems(currentParts);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//       Set table view data to add parts to product's associated parts
        AddPartTableview.setItems(Inventory.getAllParts());
        addpartIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addpartNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        addinvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        addpriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

//      Set table view data for associated parts
        delpartIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        delpartNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        delinvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        delpriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
    }
}
