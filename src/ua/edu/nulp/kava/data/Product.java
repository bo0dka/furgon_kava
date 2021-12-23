package ua.edu.nulp.kava.data;

public class Product {
    private int id;
    private String name;
    private ProductType type;
    private double price;
    private double size;

    public Product(int id, String name, ProductType type, double price, double size) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return getName();
    }
}
