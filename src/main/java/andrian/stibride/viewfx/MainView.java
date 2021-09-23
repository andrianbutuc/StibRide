package andrian.stibride.viewfx;
import andrian.stibride.model.searchpath.Stib;
import andrian.stibride.presenter.StibPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The start point of application .
 */
public class MainView extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Stets up and starts the application .
     * @param stage a Stage .
     * @throws Exception a exception .
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stib_view.fxml"));

        Stib model = new Stib();
        VBox root = loader.load();

        ViewFxConstructor fxConstructor = loader.getController();

        StibPresenter presenter = new StibPresenter(model,fxConstructor);

        model.addObserver(presenter);
        fxConstructor.setButtonHandlers(presenter);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
