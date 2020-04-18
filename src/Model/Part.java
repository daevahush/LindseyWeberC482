package Model;

public abstract class Part {

    protected int partID;
    protected String partName;
    protected double partPrice;
    protected int partStock;
    protected int partMin;
    protected int partMax;

    public Part(int id, String name, double price, int stock, int min, int max) {
        this.partID = id;
        this.partName = name;
        this.partPrice = price;
        this.partStock = stock;
        this.partMin = min;
        this.partMax = max;
    }


    public void setPartID(int partID) {
        this.partID = partID;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public void setPartPrice(double partPrice) {
        this.partPrice = partPrice;
    }

    public void setPartStock(int partStock) {
        this.partStock = partStock;
    }

    public void setPartMin(int partMin) {
        this.partMin = partMin;
    }

    public void setPartMax(int partMax) {
        this.partMax = partMax;
    }

    public int getPartID() {
        return this.partID;
    }

    public String getPartName() {
        return this.partName;
    }

    public double getPartPrice() {
        return this.partPrice;
    }

    public int getPartStock() {
        return this.partStock;
    }

    public int getPartMin() {
        return this.partMin;
    }

    public int getPartMax() {
        return this.partMax;
    }
}
