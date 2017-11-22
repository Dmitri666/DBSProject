package de.hhu.cs.dbs.project.gui;

import com.alexanderthelen.applicationkit.database.Data;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
    protected ChoiceBox sexTextField;
    @FXML
    protected RadioButton yesRedakteurRadioButton;
    @FXML
    protected RadioButton noRedakteurRadioButton;
    @FXML
    protected ToggleGroup redakteurToggleGroup;
    @FXML
    protected Label nachnameLabel;
    @FXML
    protected TextField nachnameTextField;
    @FXML
    protected Label vornameLabel;

    @FXML
    protected TextField vornameTextField;
    @FXML
    protected Label biographieLabel;

    @FXML
    protected TextField biographieTextField;
    @FXML
    protected Label chefredakteurLabel;


    @FXML
    protected RadioButton yesChefredakteurRadioButton;
    @FXML
    protected RadioButton noChefredakteurRadioButton;
    @FXML
    protected ToggleGroup chefredakteurToggleGroup;
    @FXML
    protected TextField telefonnummerTextField;
    @FXML
    protected Label telefonnummerLabel;


    private String required = "required";

    final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

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
        setUpValidation(usernameTextField);//.pseudoClassStateChanged(errorClass, true);
        setUpValidation(emailTextField);
        setUpValidation(passwordTextField);
        setUpValidation(birthdayTextField);
        setUpValidation(nachnameTextField);
        setUpValidation(vornameTextField);
        setUpValidation(telefonnummerTextField);



        sexTextField.getItems().addAll("maennlich", "weiblich");
        sexTextField.setValue("maennlich");

        chefredakteurToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue == yesChefredakteurRadioButton) {
                        telefonnummerLabel.setVisible(true);
                        telefonnummerTextField.setVisible(true);
                    }
                    else {
                        telefonnummerLabel.setVisible(false);
                        telefonnummerTextField.setVisible(false);
                    }
                });

        redakteurToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == yesRedakteurRadioButton) {
                nachnameLabel.setVisible(true);
                nachnameTextField.setVisible(true);
                nachnameTextField.setOnKeyReleased(event -> this.Validate(event.getTarget()));

                vornameLabel.setVisible(true);
                vornameTextField.setVisible(true);
                vornameTextField.setOnKeyReleased(event -> this.Validate(event.getTarget()));

                biographieLabel.setVisible(true);
                biographieTextField.setVisible(true);

                chefredakteurLabel.setVisible(true);
                noChefredakteurRadioButton.setSelected(true);
                yesChefredakteurRadioButton.setVisible(true);
                noChefredakteurRadioButton.setVisible(true);

            } else {
                nachnameLabel.setVisible(false);
                nachnameTextField.setVisible(false);
                vornameLabel.setVisible(false);
                vornameTextField.setVisible(false);
                biographieLabel.setVisible(false);
                biographieTextField.setVisible(false);

                yesChefredakteurRadioButton.setVisible(false);
                noChefredakteurRadioButton.setSelected(true);
                noChefredakteurRadioButton.setVisible(false);


                telefonnummerLabel.setVisible(false);
                telefonnummerTextField.setVisible(false);


            }
        });
    }

    @Override
    public ArrayList<Node> getInputNodes() {
        ArrayList<Node> inputNodes = new ArrayList<>();
        inputNodes.add(usernameTextField);
        inputNodes.add(emailTextField);
        inputNodes.add(passwordTextField);
        inputNodes.add(birthdayTextField);
        inputNodes.add(sexTextField);
        if(yesRedakteurRadioButton.isSelected()) {
            inputNodes.add(nachnameTextField);
            inputNodes.add(vornameTextField);
            inputNodes.add(biographieTextField);
        }
        if(yesChefredakteurRadioButton.isSelected()) {
            inputNodes.add(telefonnummerTextField);
        }
        return inputNodes;
    }

    @Override
    public Data getInputData() {
        Data data = new Data();
        data.put("username", usernameTextField.getText() == null ? null : usernameTextField.getText());
        data.put("email", emailTextField.getText() == null ? null : emailTextField.getText());
        data.put("password", passwordTextField.getText() == null ? null : passwordTextField.getText());
        data.put("birthday", birthdayTextField.getText() == null ? null : birthdayTextField.getText());
        data.put("sex", sexTextField.getValue() == null ? null : sexTextField.getValue());
        data.put("isRedakteur", yesRedakteurRadioButton.isSelected() ? true : false);
        data.put("isChefredakteur", yesChefredakteurRadioButton.isSelected() ? true : false);
        if(yesRedakteurRadioButton.isSelected()) {
            data.put("nachname", nachnameTextField.getText() == null ? null : nachnameTextField.getText());
            data.put("vorname", vornameTextField.getText() == null ? null : vornameTextField.getText());
            data.put("biographie", biographieTextField.getText() == null ? null : biographieTextField.getText());
        }
        if(yesChefredakteurRadioButton.isSelected()) {
            data.put("telefonnummer", telefonnummerTextField.getText() == null ? null : biographieTextField.getText());
        }
        return data;
    }


    private void Validate(EventTarget target) {
        TextField field = (TextField) target;
        ObservableList<String> sc = field.getStyleClass();
        if(field.getText().isEmpty()) {
            if(!sc.contains(this.required)) {
                sc.add(this.required);
            }
        }
        else {
            if(sc.contains(this.required)) {
                sc.remove(this.required);
            }
        }
    }


    private void setUpValidation(final TextField tf) {
        tf.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                validate(tf);
            }

        });

        validate(tf);
    }

    private void validate(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        if (tf.getText().trim().length()==0) {
            if (! styleClass.contains("error")) {
                styleClass.add("error");
            }
        } else {
            // remove all occurrences:
            styleClass.removeAll(Collections.singleton("error"));
        }
    }
}
