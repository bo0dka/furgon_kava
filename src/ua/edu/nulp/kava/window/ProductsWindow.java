package ua.edu.nulp.kava.window;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ua.edu.nulp.kava.data.Db;
import ua.edu.nulp.kava.data.Product;
import ua.edu.nulp.kava.util.FxModal;

public class ProductsWindow extends Window {
    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private TableView<Product> tblProducts;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colType;

    @FXML
    private TableColumn colPrice;

    @FXML
    private TableColumn colSize;

    public ProductsWindow() {
        super("ProductsWindow");

        tblProducts.setPlaceholder(new Label("(пусто)"));
        tblProducts.getSelectionModel().selectedIndexProperty().addListener((observable, oldVal, newVal) -> {
            boolean disabled = (newVal.intValue() == -1);

            btnEdit.setDisable(disabled);
            btnDelete.setDisable(disabled);
        });

        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colType.setCellValueFactory(new PropertyValueFactory("type"));
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colSize.setCellValueFactory(new PropertyValueFactory("size"));

        loadProducts();
    }

    public void loadProducts() {
        tblProducts.getItems().clear();
        tblProducts.getItems().addAll(Db.getInstance().getProducts());
    }

    private Product getSelected() {
        return tblProducts.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void editSelected() {
        Product product = getSelected();

        if (product == null) {
            return;
        }

        EditProductWindow window = new EditProductWindow(product);
        window.showAndWait();
        loadProducts();
    }

    @FXML
    private void deleteSelected() {
        if (!FxModal.confirm("Підтвердження", "Видалити даний запис?")) {
            return;
        }

        Product product = getSelected();

        if (product == null) {
            return;
        }

        int id = product.getId();

        if (!Db.getInstance().deleteProduct(id)) {
            FxModal.MessageBox("Помилка", "Помилка при видаленні запису");
        } else {
            loadProducts();
        }
    }

    @FXML
    private void addProduct() {
        EditProductWindow window = new EditProductWindow();
        window.showAndWait();
        loadProducts();
    }
}
