package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partID) {
        ObservableList<Part> allParts = getAllParts();

        for(int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);

            if(part.getPartID() == partID) {
                return part;
            }
        }
        return null;
    }

    public static Product lookupProduct(int productID) {
        ObservableList<Product> allProducts = getAllProducts();

        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);

            if(product.getProductID() == productID) {
                return product;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        partName = partName.toLowerCase();

        for(Part part : getAllParts()) {
            String name = part.getPartName().toLowerCase();

            if(name.contains(partName)) {
                filteredParts.add(part);
            }
        }
        return filteredParts;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        productName = productName.toLowerCase();

        for(Product product : getAllProducts()) {
            String name = product.getProductName().toLowerCase();

            if(name.contains(productName)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public static void updatePart(int index, Part selectedPart) {
        int indexCount = 1;

        for(Part part : Inventory.getAllParts()) {
            indexCount++;

            if(part.getPartID() == (index)) {
                Inventory.getAllParts().set(index, selectedPart);
            }
        }
    }

    public static void updateProduct(int index, Product selectedProduct) {
        int indexCount = 1;

        for(Product product : Inventory.getAllProducts()) {
            indexCount++;

            if(product.getProductID() == (index + 1)) {
                Inventory.getAllProducts().set(index, selectedProduct);
            }
        }

    }

    public static boolean deletePart(Part selectedPart) {
        for(Part part : Inventory.getAllParts()) {

            if(part.getPartID() == selectedPart.getPartID()) {
                Inventory.getAllParts().remove(selectedPart);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteProduct(Product selectedProduct) {
        for(Product product : Inventory.getAllProducts()) {

            if(product.getProductID() == selectedProduct.getProductID()) {
                Inventory.getAllProducts().remove(selectedProduct);
                return true;
            }
        }
        return false;
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
