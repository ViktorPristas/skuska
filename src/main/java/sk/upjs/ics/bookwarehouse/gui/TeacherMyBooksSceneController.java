package sk.upjs.ics.bookwarehouse.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sk.upjs.ics.bookwarehouse.business.UserIdentificationManager;
import sk.upjs.ics.bookwarehouse.fxmodels.BookFxModel;
import sk.upjs.ics.bookwarehouse.fxmodels.BookLendingFxModel;

public class TeacherMyBooksSceneController {

    private final BookLendingFxModel bookLendingFxModel = new BookLendingFxModel();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button backButton;

    @FXML
    private TableView<BookLendingFxModel> simpleTableView;

    @FXML
    private Button exportToExcelButton;

    @FXML
    void initialize() {
        backButton.setOnAction(eh -> {
            MainSceneTeacherController controller = new MainSceneTeacherController();
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("MainSceneTeacher.fxml"));
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

        if (bookLendingFxModel.getLendings().size() > 0) {
            bookLendingFxModel.loadBookLendingToModel(UserIdentificationManager.getId());
        }

        TableColumn<BookLendingFxModel, String> authorCol = new TableColumn<>("Autor");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        simpleTableView.getColumns().add(authorCol);

        TableColumn<BookLendingFxModel, String> titleCol = new TableColumn<>("Názov");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        simpleTableView.getColumns().add(titleCol);

        TableColumn<BookLendingFxModel, Integer> yearOfReturnCol = new TableColumn<>("rok vratenia");
        yearOfReturnCol.setCellValueFactory(new PropertyValueFactory<>("yearOfReturn"));
        simpleTableView.getColumns().add(yearOfReturnCol);

        TableColumn<BookLendingFxModel, Integer> lendedCol = new TableColumn<>("pocet rozdanych");
        lendedCol.setCellValueFactory(new PropertyValueFactory<>("lended"));
        simpleTableView.getColumns().add(lendedCol);

        TableColumn<BookLendingFxModel, Integer> returnedCol = new TableColumn<>("pocet vratenych");
        returnedCol.setCellValueFactory(new PropertyValueFactory<>("returned"));
        simpleTableView.getColumns().add(returnedCol);

        TableColumn<BookLendingFxModel, String> commentCol = new TableColumn<>("koment");
        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));
        simpleTableView.getColumns().add(commentCol);

        TableColumn<BookLendingFxModel, String> approvedCol = new TableColumn<>("Je potvrdené");
        approvedCol.setCellValueFactory(new PropertyValueFactory<>("approvedString"));
        simpleTableView.getColumns().add(approvedCol);

        simpleTableView.setItems(bookLendingFxModel.getBookLendingsModel());

    }
}
