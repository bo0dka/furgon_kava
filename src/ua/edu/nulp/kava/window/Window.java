package ua.edu.nulp.kava.window;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ua.edu.nulp.kava.util.FxModal;

import java.net.URL;

public class Window {
    private Stage stage;
    private Scene scene;

    public Window(String fxmlName) {
        init(fxmlName, true);
    }

    public Window(String fxmlName, boolean resizable) {
        init(fxmlName, resizable);
    }

    private void init(String fxmlName, boolean resizable) {
        try {
            URL location = getClass().getResource("/ua/edu/nulp/kava/fxml/" + fxmlName + ".fxml");
            FXMLLoader loader = new FXMLLoader(location);
            loader.setController(this);

            AnchorPane root = loader.load();
            scene = new Scene(root);

            stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Фургон кави");
            stage.setResizable(resizable);
//            stage.getIcons().addAll(
//                    new Image(getClass().getResource("/res/icon16.png").toExternalForm()),
//                    new Image(getClass().getResource("/res/icon32.png").toExternalForm())
//            );
        } catch(Exception e) {
            e.printStackTrace();
            FxModal.MessageBox("Помилка", "Помилка при завантаженні FXML-файлу");
            System.exit(1);
        }
    }

    @FXML
    public void show() {
        stage.show();
    }

    @FXML
    public void showAndWait() {
        stage.showAndWait();
    }

    @FXML
    public void close() {
        stage.close();
    }

    @FXML
    public void hide() {
        stage.hide();
    }
}
