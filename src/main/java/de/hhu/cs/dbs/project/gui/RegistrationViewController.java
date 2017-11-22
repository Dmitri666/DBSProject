package de.hhu.cs.dbs.project.gui;

import com.alexanderthelen.applicationkit.database.Data;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class RegistrationViewController extends com.alexanderthelen.applicationkit.gui.RegistrationViewController {
    @FXML
    protected TextField usernameTextField;
    @FXML
    protected TextField emailTextField;
    @FXML
    protected PasswordField passwordTextField;
    @FXML
    protected TextField birthdayTextField;
    @FXML
    protected TextField sexTextField;

    protected RegistrationViewController(String name) {
        super(name, RegistrationViewController.class.getResource("RegistrationView.fxml"));
    }

    public static RegistrationViewController createWithName(String name) throws IOException {
        RegistrationViewController viewController = new RegistrationViewController(name);
        viewController.loadView();
        return viewController;
    }

    @Override
    @FXML
    protected void initialize() {

    }

    @Override
    public ArrayList<Node> getInputNodes() {
        ArrayList<Node> inputNodes = new ArrayList<>();
        inputNodes.add(usernameTextField);
        inputNodes.add(emailTextField);
        inputNodes.add(passwordTextField);
        inputNodes.add(birthdayTextField);
        inputNodes.add(sexTextField);
        return inputNodes;
    }

    @Override
    public Data getInputData() {
        Data data = new Data();
        data.put("username", usernameTextField.getText() == null ? null : usernameTextField.getText());
        data.put("email", emailTextField.getText() == null ? null : emailTextField.getText());
        data.put("password", passwordTextField.getText() == null ? null : passwordTextField.getText());
        data.put("birthday", birthdayTextField.getText() == null ? null : birthdayTextField.getText());
        data.put("sex", sexTextField.getText() == null ? null : sexTextField.getText());
        return data;
    }
}
