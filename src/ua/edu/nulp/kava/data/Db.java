package ua.edu.nulp.kava.data;

import ua.edu.nulp.kava.util.Config;
import ua.edu.nulp.kava.util.FxModal;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Db {
    private static Db instance = null;

    private Connection connection;

    private Db() {
        DbConfig dbConfig = Config.getDbConfig();

        try {
            String username = dbConfig.getUsername();
            String password = dbConfig.getPassword();

            connection = DriverManager.getConnection(getConnectionString(dbConfig), username, password);
        } catch (Exception ex) {
            FxModal.MessageBox("Помилка", "Не вдалося підключитися до БД");
            System.err.println("Error while connecting to DB: " + ex.getMessage());
            System.exit(1);
        }
    }

    private static String getConnectionString(DbConfig dbConfig) {
        String host = dbConfig.getHost();
        int port = dbConfig.getPort();
        String dbName = dbConfig.getDbName();

        return "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useUnicode=yes&characterEncoding=UTF-8";
    }

    public static boolean tryConnect(DbConfig dbConfig) {
        try {
            String username = dbConfig.getUsername();
            String password = dbConfig.getPassword();

            DriverManager.getConnection(getConnectionString(dbConfig), username, password).close();

            return true;
        } catch (Exception ignored) { }

        return false;
    }

    public static void reloadInstance() {
        instance = new Db();
    }

    public static Db getInstance() {
        if (instance == null) {
            reloadInstance();
        }

        return instance;
    }

    private int getLastId(PreparedStatement preparedStatement) {
        try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
            if (keys.next()) {
                return keys.getInt(1);
            }
        } catch (Exception ignored) { }

        return 0;
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM products ORDER BY product_id");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("product_id");
                String name = rs.getString("product_name");
                String typeStr = rs.getString("product_type");
                double price = rs.getDouble("product_price");
                double size = rs.getDouble("product_size");
                ProductType type = ProductType.get(typeStr);

                products.add(new Product(id, name, type, price, size));
            }

            stmt.close();
            rs.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return products;
    }

    public Map<Integer, Product> getProductsMap() {
        Map<Integer, Product> products = new HashMap<>();

        for (Product product : getProducts()) {
            products.put(product.getId(), product);
        }

        return products;
    }

    public boolean addProduct(String name, ProductType type, double price, double size) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO products (product_name, product_type, product_price, product_size) VALUES (?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, type.name());
            stmt.setDouble(3, price);
            stmt.setDouble(4, size);
            stmt.execute();
            stmt.close();

            return true;
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean editProduct(int id, String name, ProductType type, double price, double size) {
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE products SET product_name = ?, product_type = ?, product_price = ?, product_size = ? WHERE product_id = ?");
            stmt.setString(1, name);
            stmt.setString(2, type.name());
            stmt.setDouble(3, price);
            stmt.setDouble(4, size);
            stmt.setInt(5, id);
            stmt.execute();
            stmt.close();

            return true;
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean deleteProduct(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM products WHERE product_id = ?");
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();

            return true;
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public List<Truck> getTrucks() {
        List<Truck> trucks = new ArrayList<>();
        Map<Integer, Product> productMap = getProductsMap();

        try (
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM trucks ORDER BY truck_id DESC");
            ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next()) {
                int id = rs.getInt("truck_id");
                String name = rs.getString("truck_name");
                int maxSize = rs.getInt("truck_max_size");

                Truck truck = new Truck(id, name, maxSize);
                List<ProductInTruckItem> items = getTruckItems(id);

                for (ProductInTruckItem item : items) {
                    Product product = productMap.get(item.getProductId());

                    if (product == null) {
                        continue;
                    }

                    truck.getProducts().add(new ProductInTruck(product, item.getId(), item.getCount()));
                }

                trucks.add(truck);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return trucks;
    }

    public int addTruck(String name, int maxSize) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO trucks (truck_name, truck_max_size) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setInt(2, maxSize);
            stmt.execute();

            int id = getLastId(stmt);

            stmt.close();

            return id;
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return 0;
    }

    public boolean editTruck(int id, String name, int maxSize) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE trucks SET truck_name = ?, truck_max_size = ? WHERE truck_id = ?")) {
            stmt.setString(1, name);
            stmt.setInt(2, maxSize);
            stmt.setInt(3, id);
            stmt.execute();

            return true;
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean deleteTruck(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM trucks WHERE truck_id = ?")) {
            stmt.setInt(1, id);
            stmt.execute();

            return true;
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public List<ProductInTruckItem> getTruckItems(int truckId) {
        List<ProductInTruckItem> ids = new ArrayList<>();

        try (
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM truck_items WHERE ti_truck_id = ?");
        ) {
            stmt.setInt(1, truckId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ids.add(new ProductInTruckItem(
                    rs.getInt("ti_id"),
                    rs.getInt("ti_truck_id"),
                    rs.getInt("ti_product_id"),
                    rs.getInt("ti_count")
                ));
            }

            rs.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return ids;
    }

    public boolean addTruckItem(int truckId, int productId, int count) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO truck_items (ti_truck_id, ti_product_id, ti_count) VALUES (?, ?, ?)")) {
            stmt.setInt(1, truckId);
            stmt.setInt(2, productId);
            stmt.setInt(3, count);
            stmt.execute();

            return true;
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean deleteTruckItem(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM truck_items WHERE ti_id = ?")) {
            stmt.setInt(1, id);
            stmt.execute();

            return true;
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
