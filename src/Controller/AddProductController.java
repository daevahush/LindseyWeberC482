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

public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button SearchButton;

    @FXML
    private Button AddPartButton;

    @FXML
    private Button DeletePartButton;

    @FXML
    private Button CancelButton;

    @FXML
    private Button SaveProdButton;

    @FXML
    private TableView<Part> AddPartTableview;

    @FXML
    private TableColumn<Part, Integer> addPartIDColumn;

    @FXML
    private TableColumn<Part, String> addPartNameColumn;

    @FXML
    private TableColumn<Part, Integer> addInvLevelColumn;

    @FXML
    private TableColumn<Part, Double> addPriceColumn;

    @FXML
    private TextField AddProdNameText;

    @FXML
    private TextField AddProdInvText;

    @FXML
    private TextField AddProdPriceText;

    @FXML
    private TextField AddProdMaxText;

    @FXML
    private TextField AddProdMinText;

    @FXML
    private TextField AddProdSearchText;

    @FXML
    private TableView<Part> DeletePartTablevew;

    @FXML
    private TableColumn<Part, Integer> delPartIDColumn;

    @FXML
    private TableColumn<Part, String> delPartNameColumn;

    @FXML
    private TableColumn<Part, Integer> delInvLevelColumn;

    @FXML
    private TableColumn<Part, Double> delPriceColumn;

    @FXML
    private Label AddProdIDLabel;

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
    void saveProdOnClick(MouseEvent event) throws IOException {
        try {
            int id = Integer.parseInt(AddProdIDLabel.getText());
            String name = AddProdNameText.getText();
            int stock = Integer.parseInt(AddProdInvText.getText());
            double price = Double.parseDouble(AddProdPriceText.getText());
            int max = Integer.parseInt(AddProdMaxText.getText());
            int min = Integer.parseInt(AddProdMinText.getText());

            Inventory.addProduct(new Product(id, name, price, stock, min, max));

            //Logical Error Handling

//            if(price < totalPriceForAllAssociatedParts);
//            if(price > totalPriceForAllAssoicatedParts);
//            if(associatedParts.isEmpty()); (must contain at least one part)

            //Moves back to Main screen
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch(NumberFormatException e) {
            System.out.println("Please enter valid values");
        }
    }

    @FXML
    void searchOnClick(MouseEvent event) {
        String query = AddProdSearchText.getText();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //generate an ID based on the number of items in the list to use for new part
        AddProdIDLabel.setText(String.valueOf((Inventory.allProducts.size()) + 1));


        //Populate the add parts table to search through and add from
        AddPartTableview.setItems(Inventory.getAllParts());

        //direct table columns on which values to get for each attribute
        addPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        addInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        addPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));


        //Direct table view to get data from observable list in product file using get associated parts method
        DeletePartTablevew.setItems(Product.getAllAssociatedParts());

        //direct table columns on which values to get for each attribute
        delPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        delPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        delInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        delPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
    }
}
