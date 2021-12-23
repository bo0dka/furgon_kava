package ua.edu.nulp.kava.window;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ua.edu.nulp.kava.data.Db;
import ua.edu.nulp.kava.data.DbConfig;
import ua.edu.nulp.kava.util.Config;
import ua.edu.nulp.kava.util.FxModal;
import ua.edu.nulp.kava.util.Utils;

public class SettingsWindow extends Window {
    @FXML
    private TextField txtHost;

    @FXML
    private TextField txtPort;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtDbName;

    @FXML
    private Button btnSave;

    public SettingsWindow() {
        super("SettingsWindow", false);
        setListeners();

        DbConfig dbConfig = Config.getDbConfig();

        if (dbConfig != null) {
            txtHost.setText(dbConfig.getHost());
            txtPort.setText(dbConfig.getPort() + "");
            txtUsername.setText(dbConfig.getUsername());
            txtDbName.setText(dbConfig.getDbName());
        }
    }

    private void setListeners() {
        txtHost.setOnKeyTyped((e) -> lockSaveBtn());
        txtPort.setOnKeyTyped((e) -> lockSaveBtn());
        txtUsername.setOnKeyTyped((e) -> lockSaveBtn());
        txtPassword.setOnKeyTyped((e) -> lockSaveBtn());
        txtDbName.setOnKeyTyped((e) -> lockSaveBtn());
    }

    private void lockSaveBtn() {
        btnSave.setDisable(true);
    }

    private DbConfig readConfig() {
        return new DbConfig(
                txtHost.getText().trim(),
                Utils.strToInt(txtPort.getText().trim()),
                txtUsername.getText().trim(),
                txtPassword.getText().trim(),
                txtDbName.getText().trim()
        );
    }

    @FXML
    private void testConnection() {
        DbConfig dbConfig = readConfig();
        boolean ok = Db.tryConnect(dbConfig);

        btnSave.setDisable(!ok);

        if (!ok) {
            FxModal.MessageBox("Помилка", "Не вдалося підключитися до БД з використанням заданих параметрів");
        }
    }

    @FXML
    private void save() {
        DbConfig dbConfig = readConfig();

        if (!Config.saveDbConfig(dbConfig)) {
            FxModal.MessageBox("Помилка", "Не вдалося зберегти нову конфігурацію");
        } else {
            Db.reloadInstance();
            close();
        }
    }
}
