package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String productName;
    private double productPrice;
    private int productStock;
    private int productMin;
    private int productMax;

    public Product(int id, String name, double price, int stock, int min, int max){
        setProductID(id);
        setProductName(name);
        setProductPrice(price);
        setProductStock(stock);
        setProductMin(min);
        setProductMax(max);
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public void setProductMin(int productMin) {
        this.productMin = productMin;
    }

    public void setProductMax(int productMax) {
        this.productMax = productMax;
    }

    public int getProductID() {
        return this.productID;
    }

    public String getProductName() {
        return this.productName;
    }

    public double getProductPrice() {
        return this.productPrice;
    }

    public int getProductStock() {
        return this.productStock;
    }

    public int getProductMin() {
        return this.productMin;
    }

    public int getProductMax() {
        return this.productMax;
    }

    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    public boolean deleteAssociatedPart(Part selectedAsPart) {
       for(Part part : getAllAssociatedParts()) {

           if(part.getPartID() == selectedAsPart.getPartID()) {
               getAllAssociatedParts().remove(selectedAsPart);
               return true;
           }
       }
       return false;
   }

    public  ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
   }

}
