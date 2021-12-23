package ua.edu.nulp.kava.util;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FxModal {
    private static boolean answer;
    
    public static void MessageBox(String title, String msg) {
		Stage stage = new Stage();
		VBox vbox = new VBox(10);
		vbox.setStyle("-fx-background-color: #fff; -fx-padding: 10px;");

		HBox hbox = new HBox(10);
		hbox.setAlignment(Pos.CENTER);

		Label lblTitle = new Label(title);
		lblTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

		Label lblMsg = new Label(msg);
		lblMsg.setStyle("-fx-font-size: 12px;");

		Button btnOk = new Button("OK");
		btnOk.setOnAction(e -> {
			stage.close();
		});

		hbox.getChildren().addAll(btnOk);
		vbox.getChildren().addAll(lblTitle, lblMsg, hbox);

		Scene scene = new Scene(vbox);

		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(scene);
		stage.setTitle(title);
		stage.setMinWidth(300);
		stage.setResizable(false);
		stage.showAndWait();
    }
    
    public static boolean confirm(String title, String msg) {
		Stage stage = new Stage();
		VBox vbox = new VBox(10);
		vbox.setStyle("-fx-background-color: #fff; -fx-padding: 10px;");

		HBox hbox = new HBox(10);
		hbox.setAlignment(Pos.CENTER);

		Label lblTitle = new Label(title);
		lblTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

		Label lblMsg = new Label(msg);
		lblMsg.setStyle("-fx-font-size: 12px;");

		Button btnYes = new Button("Так");
		btnYes.setOnAction(e -> {
			answer = true;
			stage.close();
		});

		Button btnNo = new Button("Ні");
		btnNo.setOnAction(e -> {
			answer = false;
			stage.close();
		});

		hbox.getChildren().addAll(btnYes, btnNo);
		vbox.getChildren().addAll(lblTitle, lblMsg, hbox);

		Scene scene = new Scene(vbox);

		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(scene);
		stage.setTitle(title);
		stage.setMinWidth(300);
		stage.setResizable(false);
		stage.showAndWait();

		return answer;
    }
}