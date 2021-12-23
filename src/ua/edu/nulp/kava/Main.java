package ua.edu.nulp.kava;

import javafx.application.Application;
import javafx.stage.Stage;
import ua.edu.nulp.kava.window.MainWindow;
import ua.edu.nulp.kava.window.Window;

public class Main extends Application {
    public static void main(String[] args) {
	    launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Window window = new MainWindow();
        window.show();
    }
}
