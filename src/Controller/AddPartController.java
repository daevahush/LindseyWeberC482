package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
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

public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton InHouseRadio;

    @FXML
    private ToggleGroup partSourceTG;

    @FXML
    private RadioButton OutsourcedRadio;

    @FXML
    private TextField AddPartNameText;

    @FXML
    private TextField AddPartInvText;

    @FXML
    private TextField AddPartPriceText;

    @FXML
    private TextField AddPartMaxText;

    @FXML
    private TextField AddPartMinText;

    @FXML
    private TextField sourceText;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label AddPartIDLabel;

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
            scene = FXMLLoader.load(getClass().getResource("../View/MainScreen.fxml"));
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
            int id = Integer.parseInt(AddPartIDLabel.getText());
            String name = AddPartNameText.getText();
            int stock = Integer.parseInt(AddPartInvText.getText());
            double price = Double.parseDouble(AddPartPriceText.getText());
            int max = Integer.parseInt(AddPartMaxText.getText());
            int min = Integer.parseInt(AddPartMinText.getText());

            //Logical Error Handling
            if((stock > max) || (stock < min) || (stock < 0)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter valid inventory value. " +
                        "Cannot be greater or less than minimum and maximum values");
                alert.showAndWait();
            } else if((min > max) || (min < 0) || (max < 0)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter valid min and max values. " +
                        "Minimum must be less than Maximum and vice versa." +
                        "Values can not be below zero.");
                alert.showAndWait();
            } else if(price <=0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter valid price" +
                        "Value can not be zero or below.");
                alert.showAndWait();
            } else if(InHouseRadio.isSelected()) { //Creates new InHouse part
                int machineID = Integer.parseInt(sourceText.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));

                //Moves back to Main screen
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("../View/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else if(OutsourcedRadio.isSelected()) { //Creates new Outsourced part
                String companyName = sourceText.getText();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));

                //Moves back to Main screen
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("../View/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            ++id;
            Inventory.setpartID(id);
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter valid Values");
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //generate an ID based on the number of items in the list to use for new part
        AddPartIDLabel.setText(String.valueOf(Inventory.getpartID()));

        //Set preselected radio button on start
        InHouseRadio.setSelected(true);
        sourceLabel.setText("Machine ID");
    }
}
