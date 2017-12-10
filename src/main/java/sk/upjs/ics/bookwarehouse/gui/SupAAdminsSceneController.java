package sk.upjs.ics.bookwarehouse.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.upjs.ics.bookwarehouse.Admin;
import sk.upjs.ics.bookwarehouse.DaoFactory;
import sk.upjs.ics.bookwarehouse.fxmodels.AdminFxModel;
import sk.upjs.ics.bookwarehouse.fxmodels.TeacherFxModel;

public class SupAAdminsSceneController {

    private final AdminFxModel adminFxModel = new AdminFxModel();
    private final TeacherFxModel teacherFxModel = new TeacherFxModel();
    private AdminFxModel selectedAdminFxModel;
    private TeacherFxModel selectedTeacherFxModel;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button backButton;

    @FXML
    private TableView<AdminFxModel> simpleTableView;

    @FXML
    private Button newAdminButton;

    @FXML
    private Button resetPasswordButton;

    @FXML
    private Button deleteAdminButton;

    @FXML
    private TableView<TeacherFxModel> simpleTableViewTeachers;

    @FXML
    private Button deleteTeacherButton;

    @FXML
    void initialize() {
        simpleTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AdminFxModel>() {
            @Override
            public void changed(ObservableValue<? extends AdminFxModel> observable, AdminFxModel oldValue, AdminFxModel newValue) {
                selectedAdminFxModel = simpleTableView.getSelectionModel().getSelectedItem();
                if (selectedAdminFxModel == null) {
                    resetPasswordButton.setDisable(true);
                } else {
                    resetPasswordButton.setDisable(false);
                }
            }
        });

        newAdminButton.setOnAction(eh -> {
            SupANewAdminSceneController controller = new SupANewAdminSceneController();
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("SupANewAdminScene.fxml"));
                loader.setController(controller);

                Parent parentPane = loader.load();
                Scene scene = new Scene(parentPane);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.setTitle("BookWareHouse");
                stage.show();

                // toto sa vykona az po zatvoreni okna
            } catch (IOException iOException) {
                iOException.printStackTrace();
            }
        });

        resetPasswordButton.setOnAction(eh -> {
            SupAResetPasswordSceneController controller = new SupAResetPasswordSceneController(selectedAdminFxModel);
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("SupAResetPasswordScene.fxml"));
                loader.setController(controller);

                Parent parentPane = loader.load();
                Scene scene = new Scene(parentPane);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.setTitle("BookWareHouse");
                stage.show();

                // toto sa vykona az po zatvoreni okna
            } catch (IOException iOException) {
                iOException.printStackTrace();
            }
        });

        backButton.setOnAction(eh -> {
            SupAMainSceneController controller = new SupAMainSceneController();
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("SupAMainScene.fxml"));
                loader.setController(controller);

                Parent parentPane = loader.load();
                Scene scene = new Scene(parentPane);

                Stage stage = (Stage) pane.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("BookWareHouse");
                stage.show();

                // toto sa vykona az po zatvoreni okna
            } catch (IOException iOException) {
                iOException.printStackTrace();
            }
        });

        deleteAdminButton.setOnAction(eh -> {
            showDeleteUserWindow();
        });

        deleteTeacherButton.setOnAction(eh -> {
            showDeleteUserWindow();
        });

        fillSimpleTable(false);

    }

    public void fillSimpleTable(boolean b) {
        simpleTableView.getItems().clear();
        simpleTableView.getColumns().clear();
        if (adminFxModel.getAdmins().size() > 0 || b) {
            adminFxModel.loadAdminToModel();
        }

        TableColumn<AdminFxModel, String> usernameCol = new TableColumn<>("Meno");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        simpleTableView.getColumns().add(usernameCol);

        TableColumn<AdminFxModel, String> emailCol = new TableColumn<>("E-mail");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        simpleTableView.getColumns().add(emailCol);

        simpleTableView.setItems(adminFxModel.getAdminsModel());

        if (teacherFxModel.getTeacherList().size() > 0 || b) {
            teacherFxModel.loadTeacherToModel();
        }

        TableColumn<TeacherFxModel, String> teacherNameCol = new TableColumn<>("Meno");
        teacherNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        simpleTableViewTeachers.getColumns().add(teacherNameCol);

        TableColumn<TeacherFxModel, String> teacherSurnameCol = new TableColumn<>("Priezvisko");
        teacherSurnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        simpleTableViewTeachers.getColumns().add(teacherSurnameCol);

        TableColumn<TeacherFxModel, String> teacherEmailCol = new TableColumn<>("E-mail");
        teacherEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        simpleTableViewTeachers.getColumns().add(teacherEmailCol);

        TableColumn<TeacherFxModel, String> teacherNumberOfStudentsCol = new TableColumn<>("Počet žiakov");
        teacherNumberOfStudentsCol.setCellValueFactory(new PropertyValueFactory<>("numberOfStudentsInClass"));
        simpleTableViewTeachers.getColumns().add(teacherNumberOfStudentsCol);

        simpleTableViewTeachers.setItems(teacherFxModel.getTeachersModel());
    }

    public void showDeleteUserWindow() {
        AlertBoxDeleteUserController controller = new AlertBoxDeleteUserController();
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("AlertBoxDeleteUser.fxml"));
            loader.setController(controller);

            Parent parentPane = loader.load();
            Scene scene = new Scene(parentPane);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("BookWareHouse");
            stage.show();

            // toto sa vykona az po zatvoreni okna
            stage.setOnHidden(eh -> {
                if (selectedAdminFxModel != null) {
                    Admin admin = selectedAdminFxModel.getAdmin();
                    DaoFactory.INSTANCE.getAdminDao().deleteById(admin.getId());
                    adminFxModel.loadAdminToModel();
                }
                fillSimpleTable(true);
            });
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }
}
