package sk.upjs.ics.bookwarehouse.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import sk.upjs.ics.bookwarehouse.DaoFactory;
import sk.upjs.ics.bookwarehouse.Teacher;
import sk.upjs.ics.bookwarehouse.business.DefaultRegistrationManager;
import sk.upjs.ics.bookwarehouse.business.UserIdentificationManager;
import sk.upjs.ics.bookwarehouse.fxmodels.TeacherFxModel;
import sk.upjs.ics.bookwarehouse.storage.TeacherDao;

public class TeacherMyProfileSceneController {

    private TeacherFxModel teacherFxModel = new TeacherFxModel();
    private TeacherDao teacherDao = DaoFactory.INSTANCE.getTeacherDao();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button saveChangesButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField numberOfStudentsTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private PasswordField repeatPasswordTextField;

    @FXML
    void initialize() {
        teacherFxModel.loadActualTeacherToModel(UserIdentificationManager.getId());

        nameTextField.textProperty().bindBidirectional(
                teacherFxModel.nameProperty());

        surnameTextField.textProperty().bindBidirectional(
                teacherFxModel.surnameProperty());

        emailTextField.textProperty().bindBidirectional(
                teacherFxModel.emailProperty());

        passwordTextField.textProperty().bindBidirectional(
                teacherFxModel.passwordProperty());

        numberOfStudentsTextField.textProperty().bindBidirectional(
                teacherFxModel.numberOfStudentsInClassProperty(), new NumberStringConverter());

        saveChangesButton.setOnAction(eh -> {
            Teacher teacher = teacherFxModel.getTeacher();
            if (modificationIsOk(teacher)) {
                boolean isEmailOk = true;
                List<Teacher> list = teacherDao.getAll();
                for (Teacher t : list) {
                    if ((!t.getId().equals(teacher.getId()) && (t.getEmail().equals(teacher.getEmail())))) {
                        isEmailOk = false;
                    }
                }
                
                if (isEmailOk) {
                    teacher = teacherDao.save(teacher);
                    
                    saveChangesButton.getScene().getWindow().hide();
                }
               
            } else {
                // nic
            }
        });

        passwordTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (passwordTextField.getText().equals(repeatPasswordTextField.getText())) {
                    passwordTextField.setStyle("-fx-background-color: white;");
                    repeatPasswordTextField.setStyle("-fx-background-color: white;");
                    saveChangesButton.setDisable(false);
                } else {
                    passwordTextField.setStyle("-fx-background-color: red;");
                    repeatPasswordTextField.setStyle("-fx-background-color: red;");
                    saveChangesButton.setDisable(true);

                }
            }
        });

        repeatPasswordTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (passwordTextField.getText().equals(repeatPasswordTextField.getText())) {
                    passwordTextField.setStyle("-fx-background-color: white;");
                    repeatPasswordTextField.setStyle("-fx-background-color: white;");
                    saveChangesButton.setDisable(false);
                } else {
                    passwordTextField.setStyle("-fx-background-color: red;");
                    repeatPasswordTextField.setStyle("-fx-background-color: red;");
                    saveChangesButton.setDisable(true);
                }
            }
        });

    }

    public boolean modificationIsOk(Teacher t) {
        if (t.getName() == null || t.getName().equals("")) {
            return false;
        }
        if ((t.getEmail() == null) || t.getEmail().equals("")) {
            return false;
        }
        if ((t.getSurname() == null) || t.getSurname().equals("")) {
            return false;
        }
        return true;
    }
}
