package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton InHouseRadio;

    @FXML
    private ToggleGroup partSourceTG;

    @FXML
    private RadioButton OutsourcedRadio;

    @FXML
    private TextField partNameText;

    @FXML
    private Label partIDLabel;

    @FXML
    private TextField partInvText;

    @FXML
    private TextField partPriceText;

    @FXML
    private TextField partMaxText;

    @FXML
    private TextField partMinText;

    @FXML
    private TextField sourceText;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label sourceLabel;

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
    void inHouseOnClick(MouseEvent event) {
        sourceLabel.setText("Machine ID");
    }

    @FXML
    void outsourcedOnClick(MouseEvent event) {
        sourceLabel.setText("Company Name");
    }

    @FXML
    void saveOnClick(MouseEvent event) throws IOException {

        try {
            int id = Integer.parseInt(partIDLabel.getText());
            String name = partNameText.getText();
            int stock = Integer.parseInt(partInvText.getText());
            double price = Double.parseDouble(partPriceText.getText());
            int max = Integer.parseInt(partMaxText.getText());
            int min = Integer.parseInt(partMinText.getText());

            //Adds parts to part list
            if(InHouseRadio.isSelected()) {
                int machineID = Integer.parseInt(sourceText.getText());
                Inventory.updatePart(id, new InHouse(id, name, price, stock, min, max, machineID));
            }

            if(OutsourcedRadio.isSelected()) {
                String companyName = sourceText.getText();
                Inventory.updatePart(id, new Outsourced(id, name, price, stock, min, max, companyName));
            }

            //Logical Error Handling
            if((stock > max) || (stock < min) || (stock < 0)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter valid inventory value. " +
                        "Cannot be greater or less than minimum and maximum values");
                alert.showAndWait();
            }

            if((min > max) || (min < 0) || (max < 0)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter valid min and max values. " +
                        "Minimum must be less than Maximum and vice versa." +
                        "Values can not be below zero.");
                alert.showAndWait();
            }

            if(price <=0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter valid price" +
                        "Value can not be zero or below.");
                alert.showAndWait();
            }

            //Moves back to Main screen
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter valid Values");
            alert.showAndWait();
        }
    }

    //Get part details from Main Screen Controller for which part to mod
    public void getPartDetails(Part part) {
        partIDLabel.setText(String.valueOf(part.getPartID()));
        partNameText.setText(part.getPartName());
        partInvText.setText(String.valueOf(part.getPartStock()));
        partPriceText.setText(String.valueOf(part.getPartPrice()));
        partMaxText.setText(String.valueOf(part.getPartMax()));
        partMinText.setText(String.valueOf(part.getPartMin()));

        if(part instanceof InHouse) {
            InHouseRadio.setSelected(true);
            sourceLabel.setText("Machine ID");
            sourceText.setText(String.valueOf(((InHouse) part).getMachineID()));
        }

        if(part instanceof Outsourced) {
            OutsourcedRadio.setSelected(true);
            sourceLabel.setText("Company Name");
            sourceText.setText(((Outsourced) part).getCompanyName());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
