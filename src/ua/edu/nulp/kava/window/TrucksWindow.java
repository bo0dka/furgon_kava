package ua.edu.nulp.kava.window;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ua.edu.nulp.kava.data.Db;
import ua.edu.nulp.kava.data.ProductInTruck;
import ua.edu.nulp.kava.data.Truck;
import ua.edu.nulp.kava.util.FxModal;

public class TrucksWindow extends Window {
    @FXML
    private TableView<Truck> tblTrucks;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn<Truck, String> colLoad;

    @FXML
    private TableColumn<Truck, String> colPrice;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    public TrucksWindow() {
        super("TrucksWindow");
        init();
    }

    private void init() {
        tblTrucks.getSelectionModel().selectedIndexProperty().addListener((observable, oldVal, newVal) -> {
            boolean disabled = (newVal.intValue() == -1);

            btnEdit.setDisable(disabled);
            btnDelete.setDisable(disabled);
        });
        tblTrucks.setPlaceholder(new Label("(пусто)"));

        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colLoad.setCellValueFactory(c -> {
            Truck truck = c.getValue();

            if (truck == null) {
                return new SimpleStringProperty("0%");
            }

            int maxSize = truck.getMaxSize();
            double size = 0;

            for (ProductInTruck product : truck.getProducts()) {
                size += product.getSize() * product.getCount();
            }

            double load = (size / maxSize) * 100;

            if (load == 0) {
                return new SimpleStringProperty("0%");
            }

            return new SimpleStringProperty(load + "%");
        });
        colPrice.setCellValueFactory(c -> {
            Truck truck = c.getValue();

            if (truck == null) {
                return new SimpleStringProperty("0");
            }

            double price = 0;

            for (ProductInTruck product : truck.getProducts()) {
                price += product.getPrice() * product.getCount();
            }

            return new SimpleStringProperty(price + "");
        });

        loadTrucks();
    }

    public void loadTrucks() {
        tblTrucks.getItems().clear();
        tblTrucks.getItems().addAll(Db.getInstance().getTrucks());
    }

    private Truck getSelected() {
        return tblTrucks.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void editSelected() {
        Truck truck = getSelected();

        if (truck == null) {
            return;
        }

        EditTruckWindow window = new EditTruckWindow(truck);
        window.showAndWait();
        loadTrucks();
    }

    @FXML
    private void deleteSelected() {
        if (!FxModal.confirm("Підтвердження", "Видалити даний запис?")) {
            return;
        }

        Truck truck = getSelected();

        if (truck == null) {
            return;
        }

        int id = truck.getId();

        if (!Db.getInstance().deleteTruck(id)) {
            FxModal.MessageBox("Помилка", "Помилка при видаленні запису");
        } else {
            loadTrucks();
        }
    }

    @FXML
    private void addTruck() {
        EditTruckWindow window = new EditTruckWindow();
        window.showAndWait();
        loadTrucks();
    }
}
