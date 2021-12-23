package ua.edu.nulp.kava.window;

import javafx.fxml.FXML;

public class MainWindow extends Window {
    public MainWindow() {
        super("MainWindow");
    }

    @FXML
    private void showProducts() {
        Window window = new ProductsWindow();
        window.showAndWait();
    }

    @FXML
    private void showTrucks() {
        Window window = new TrucksWindow();
        window.showAndWait();
    }

    @FXML
    private void showSettings() {
        Window window = new SettingsWindow();
        window.showAndWait();
    }

    @FXML
    private void showAbout() {
        Window window = new AboutWindow();
        window.showAndWait();
    }
}
