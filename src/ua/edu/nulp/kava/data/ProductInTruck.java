package ua.edu.nulp.kava.data;

public class ProductInTruck extends Product {
    private int tiId;
    private int count;

    public ProductInTruck(Product product, int tiId, int count) {
        super(product.getId(), product.getName(), product.getType(), product.getPrice(), product.getSize());
        this.tiId = tiId;
        this.count = count;
    }

    public ProductInTruck(Product product, int count) {
        super(product.getId(), product.getName(), product.getType(), product.getPrice(), product.getSize());
        this.count = count;
    }

    public int getTiId() {
        return tiId;
    }

    public void setTiId(int tiId) {
        this.tiId = tiId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
