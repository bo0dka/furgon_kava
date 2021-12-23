package ua.edu.nulp.kava.data;

public class ProductInTruckItem {
    private int id;
    private int truckId;
    private int productId;
    private int count;

    public ProductInTruckItem(int id, int truckId, int productId, int count) {
        this.id = id;
        this.truckId = truckId;
        this.productId = productId;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTruckId() {
        return truckId;
    }

    public void setTruckId(int truckId) {
        this.truckId = truckId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
