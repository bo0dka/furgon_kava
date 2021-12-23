package ua.edu.nulp.kava.window;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ua.edu.nulp.kava.data.Db;
import ua.edu.nulp.kava.data.Product;
import ua.edu.nulp.kava.data.ProductInTruck;
import ua.edu.nulp.kava.data.Truck;
import ua.edu.nulp.kava.util.FxModal;
import ua.edu.nulp.kava.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class EditTruckWindow extends Window {
    @FXML
    private TextField txtName;

    @FXML
    private TextField txtMaxSize;

    @FXML
    private ComboBox<Product> comboProduct;

    @FXML
    private TextField txtCount;

    @FXML
    private TableView<ProductInTruck> tblProducts;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colType;

    @FXML
    private TableColumn colPrice;

    @FXML
    private TableColumn colSize;

    @FXML
    private TableColumn colCount;

    @FXML
    private Button btnDelete;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblLoad;

    private Truck truck;
    private ArrayList<Integer> deleteProducts = new ArrayList<>();

    public EditTruckWindow() {
        super("EditTruckWindow");
        init();
    }

    public EditTruckWindow(Truck truck) {
        super("EditTruckWindow");
        this.truck = truck;
        init();
    }

    private void init() {
        txtMaxSize.textProperty().addListener((obs, oldValue, newValue) -> {
            int size = Math.max(0, Utils.strToInt(newValue));
            txtMaxSize.setText(size + "");
            updateInfo();
        });

        comboProduct.getItems().clear();
        comboProduct.getItems().addAll(Db.getInstance().getProducts());

        txtCount.textProperty().addListener((obs, oldValue, newValue) -> {
            int count = Math.max(0, Utils.strToInt(newValue));
            txtCount.setText(count + "");
        });

        tblProducts.setPlaceholder(new Label("(пусто)"));
        tblProducts.getSelectionModel().selectedIndexProperty().addListener((observable, oldVal, newVal) -> {
            btnDelete.setDisable((newVal.intValue() == -1));
        });

        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colType.setCellValueFactory(new PropertyValueFactory("type"));
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colSize.setCellValueFactory(new PropertyValueFactory("size"));
        colCount.setCellValueFactory(new PropertyValueFactory("count"));

        if (truck != null) {
            txtName.setText(truck.getName());
            txtMaxSize.setText(truck.getMaxSize() + "");
            tblProducts.getItems().addAll(truck.getProducts());
        }

        updateInfo();
    }

    private void updateInfo() {
        double price = 0;
        double size = 0;
        int maxSize = Utils.strToInt(txtMaxSize.getText());

        for (ProductInTruck product : tblProducts.getItems()) {
            int count = product.getCount();

            price += product.getPrice() * count;
            size += product.getSize() * count;
        }

        double load = tblProducts.getItems().isEmpty() ? 0 : (size / maxSize) * 100;

        lblPrice.setText(price + "");
        lblLoad.setText(load + "%");

        lblLoad.getStyleClass().clear();

        if (Double.compare(load, (double) maxSize) <= 0) {
            lblLoad.getStyleClass().add("text-green");
        } else {
            lblLoad.getStyleClass().add("text-red");
        }
    }

    @FXML
    private void addProduct() {
        Product product = comboProduct.getValue();

        if (product == null) {
            FxModal.MessageBox("Помилка", "Продукт не вибрано");
            return;
        }

        int count = Utils.strToInt(txtCount.getText().trim());

        if (count < 1) {
            FxModal.MessageBox("Помилка", "Не задано кількість");
            return;
        }

        tblProducts.getItems().add(new ProductInTruck(product, 0, count));
        updateInfo();
    }

    @FXML
    private void deleteSelected() {
        if (!FxModal.confirm("Підтвердження", "Видалити даний запис?")) {
            return;
        }

        int index = tblProducts.getSelectionModel().getSelectedIndex();

        if (index < 0) {
            return;
        }

        ProductInTruck product = tblProducts.getItems().get(index);

        deleteProducts.add(product.getTiId());
        tblProducts.getItems().remove(index);
        updateInfo();
    }

    @FXML
    private void save() {
        String name = txtName.getText().trim();
        int maxSize = Utils.strToInt(txtMaxSize.getText());
        List<String> errors = new ArrayList<>();

        if (name.length() < 2) {
            errors.add("Мінімальна кількість символів у назві - 2");
        } else if (name.length() > 100) {
            errors.add("Максимальна кількість символів у назві - 100");
        }

        if (!errors.isEmpty()) {
            FxModal.MessageBox("Помилка", String.join("\n", errors));
            return;
        }

        Db db = Db.getInstance();
        int truckId;

        if (truck == null) {
            truckId = db.addTruck(name, maxSize);
        } else {
            truckId = truck.getId();
            db.editTruck(truckId, name, maxSize);
        }

        for (ProductInTruck product : tblProducts.getItems()) {
            int dbId = product.getTiId();

            if (dbId == 0) {
                db.addTruckItem(truckId, product.getId(), product.getCount());
            }
        }

        if (truck != null) {
            for (Integer id : deleteProducts) {
                db.deleteTruckItem(id);
            }
        }

        close();
    }
}
