package ua.edu.nulp.kava.data;

import java.util.ArrayList;
import java.util.List;

public class Truck {
    private int id;
    private String name;
    private int maxSize;
    private List<ProductInTruck> products;

    public Truck(int id, String name, int maxSize) {
        this.id = id;
        this.name = name;
        this.maxSize = maxSize;
        this.products = new ArrayList<>();
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

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public List<ProductInTruck> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInTruck> products) {
        this.products = products;
    }
}
