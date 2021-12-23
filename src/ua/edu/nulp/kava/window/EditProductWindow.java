package ua.edu.nulp.kava.window;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ua.edu.nulp.kava.data.Db;
import ua.edu.nulp.kava.data.ProductType;
import ua.edu.nulp.kava.data.Product;
import ua.edu.nulp.kava.util.FxModal;
import ua.edu.nulp.kava.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class EditProductWindow extends Window {
    @FXML
    private TextField txtName;

    @FXML
    private ComboBox<ProductType> comboType;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtSize;

    private Product product;

    public EditProductWindow() {
        super("EditProductWindow", false);
        init();
    }

    public EditProductWindow(Product product) {
        super("EditProductWindow", false);
        this.product = product;
        init();
    }

    private void init() {
        comboType.getItems().addAll(ProductType.values());
        comboType.getSelectionModel().select(0);

        txtPrice.textProperty().addListener((obs, ov, nv) -> {
            double val = Utils.strToDouble(nv);

            if (Double.compare(val, 0) < 0) {
                txtPrice.setText("0");
            }
        });

        txtSize.textProperty().addListener((obs, ov, nv) -> {
            double val = Utils.strToDouble(nv);

            if (Double.compare(val, 0) < 0) {
                txtSize.setText("0");
            }
        });

        if (product != null) {
            txtName.setText(product.getName());
            txtPrice.setText(product.getPrice() + "");
            txtSize.setText(product.getSize() + "");

            ProductType type = product.getType();

            if (type != null) {
                comboType.getSelectionModel().select(type);
            }
        }
    }

    @FXML
    private void save() {
        String name = txtName.getText().trim();
        ProductType type = comboType.getValue();
        double price = Utils.strToDouble(txtPrice.getText());
        double size = Utils.strToDouble(txtSize.getText());
        List<String> errors = new ArrayList<>();

        if (name.length() < 2) {
            errors.add("Мінімальна кількість символів у назві - 2");
        } else if (name.length() > 100) {
            errors.add("Максимальна кількість символів у назві - 100");
        }

        if (type == null) {
            errors.add("Не задано тип");
        }

        if (Double.compare(price, 0) <= 0) {
            errors.add("Не задано ціну");
        }

        if (Double.compare(size, 0) <= 0) {
            errors.add("Не задано об'єм");
        }

        if (!errors.isEmpty()) {
            FxModal.MessageBox("Помилка", String.join("\n", errors));
            return;
        }

        boolean saved;

        if (product == null) {
            saved = Db.getInstance().addProduct(name, type, price, size);
        } else {
            saved = Db.getInstance().editProduct(product.getId(), name, type, price, size);
        }

        if (!saved) {
            FxModal.MessageBox("Помилка", "Помилка при збереженні даних");
        } else {
            close();
        }
    }
}
