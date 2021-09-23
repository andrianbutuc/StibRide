package andrian.stibride.viewfx;

import andrian.stibride.model.db.dto.FavoriteDto;
import andrian.stibride.model.db.dto.StationDto;
import andrian.stibride.model.db.exeception.RepositoryException;
import andrian.stibride.model.db.repository.FavoriteRepository;
import andrian.stibride.model.db.repository.StationRepository;
import andrian.stibride.model.searchpath.StationView;
import andrian.stibride.presenter.StibPresenter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class represents the view of application .
 */
public class ViewFxConstructor implements Initializable {

    @FXML
    private SearchableComboBox<StationDto> choiceOrigin;

    @FXML
    private SearchableComboBox<StationDto> choiceDestination;

    @FXML
    private Button search;

    @FXML
    private TableView<StationView> tablePath;
    @FXML
    private TableColumn<StationView, String> columnStations;

    @FXML
    private TableColumn<StationView, String> columnLines;

    @FXML
    private TableView<FavoriteDto> tableFavorite;

    @FXML
    private TableColumn<FavoriteDto, String> columnName;

    @FXML
    private TableColumn<FavoriteDto, StationDto> columnOrigin;

    @FXML
    private TableColumn<FavoriteDto, StationDto> columnDestination;

    @FXML
    private Button delete;
    @FXML
    private Button addFavorite;
    @FXML
    private Label status;
    @FXML
    private Label nbStation;
    @FXML
    private Label error;
    @FXML
    private TextField favoriteName;

    /**
     * Initializes the view fx
     * @param url an url .
     * @param resourceBundle a resource bundle .
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        columnStations.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnStations.setSortable(false);
        columnLines.setCellValueFactory(new PropertyValueFactory<>("lines"));
        columnLines.setSortable(false);
        tablePath.setEditable(true);

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());

        var stringConverterStationDto =getStringConverter();

        columnOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        columnOrigin.setCellFactory(TextFieldTableCell.forTableColumn(stringConverterStationDto));
        columnDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        columnDestination.setCellFactory(TextFieldTableCell.forTableColumn(stringConverterStationDto));

        populateOriginDestination();
        populateFavoriteTable();
        status.setVisible(false);
        nbStation.setVisible(false);
        error.setVisible(false);
    }

    /**
     * Returns a string converter from StationDto to a string and the reverse .
     * @return a string converter from StationDto to a string and the reverse .
     */
    private StringConverter<StationDto> getStringConverter() {
        return new StringConverter<>() {
            @Override
            public String toString(StationDto stationDto) {
                return stationDto.toString();
            }

            @Override
            public StationDto fromString(String s) {
                var all = choiceOrigin.getItems();
                Integer id = null;
                s = s.toUpperCase();
                for (StationDto stationDto : all) {
                    if (stationDto.getName().equals(s)) {
                        id = stationDto.getKey();
                    }
                }
                if (id == null) {
                   showErrorMessage("Station inconnu ,vérifier l'orthograph");
                }
                return new StationDto(id, s);
            }
        };
    }

    /**
     * Populates the origin and destination SearchableComboBox with stations .
     */
    private void populateOriginDestination(){
        try {
            var repository = new StationRepository();
            var allStations = repository.getAll();
            for (StationDto station : allStations) {
                choiceOrigin.getItems().add(station);
                choiceDestination.getItems().add(station);
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the table view with favorite paths that exists.
     */
    private void populateFavoriteTable() {
        try {
            var favoriteRepository = new FavoriteRepository();
            var allFavorite = favoriteRepository.getAll();
            for (FavoriteDto favorite : allFavorite) {
                tableFavorite.getItems().add(favorite);
            }

        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a favorite .
     * @param presenter a StibPresenter .
     */
    private void handlerDeleteFavorite(StibPresenter presenter) {
        var toRemove = tableFavorite.getSelectionModel().getSelectedItem();
        presenter.deleteFavorite(toRemove);
        tableFavorite.getItems().removeAll(toRemove);
    }

    /**
     * Add a searched path to favorite .
     * @param presenter a StibPresenter .
     */
    private void handlerAddToFavorite(StibPresenter presenter) {
        String name = favoriteName.getText();
        if (name.isEmpty()) {
           showErrorMessage("Nom favorite vide ");
        } else {
            var origin = choiceOrigin.getValue();
            var dest = choiceDestination.getValue();
            // here the given id doesn't matter because the bd is the responsible of that choice
            var fav = new FavoriteDto(-1, name, origin, dest);
            presenter.addFavorite(fav);
            favoriteName.clear();
        }

    }

    /**
     * Sets actions(add handlers for update columns and a handler for the click on row) on favorite table view .
     * @param presenter a StibPresenter .
     */
    private void setActionOnFavoriteTable(StibPresenter presenter) {
        columnName.setOnEditCommit(favoritePathStringCellEditEvent -> {
            var f = favoritePathStringCellEditEvent.getRowValue();
            var newVal = favoritePathStringCellEditEvent.getNewValue();
            f.setName(newVal);
            presenter.updateFavorite(f);
        });

        columnOrigin.setOnEditCommit(favoritePathStringCellEditEvent -> {
            var f = favoritePathStringCellEditEvent.getRowValue();
            var newVal = favoritePathStringCellEditEvent.getNewValue();
            f.setOrigin(newVal);
            presenter.updateFavorite(f);
        });

        columnDestination.setOnEditCommit(favoritePathStringCellEditEvent -> {
            var f = favoritePathStringCellEditEvent.getRowValue();
            var newVal = favoritePathStringCellEditEvent.getNewValue();
            f.setDestination(newVal);
            presenter.updateFavorite(f);
        });

        tableFavorite.setOnMouseClicked(mouseEvent -> {
            var tmp = tableFavorite.getSelectionModel().getSelectedItem();
            if(mouseEvent.getTarget() != null && tmp != null){
                presenter.search(tmp.getOrigin(),tmp.getDestination());
                choiceDestination.setValue(tmp.getDestination());
                choiceOrigin.setValue(tmp.getOrigin());
            }
        });

    }

    /**
     * Shows a error message .
     * @param message a string .
     */
    public void showErrorMessage(String message){
        error.setText(message);
        error.setVisible(true);
    }

    /**
     * Shows the path in dedicated table view for this..
     * @param path a list of station view .
     */
    public void showPath(List<StationView> path ){
        tablePath.getItems().clear();
        status.setVisible(true);
        nbStation.setText("Nombre de station : " + path.size());
        nbStation.setVisible(true);
        for (StationView s : path) {
            tablePath.getItems().add(s);
        }
    }

    /**
     * Adds a favorite in favorite table view .
     * @param dto a favorite dto .
     */
    public void addFavoriteInTable(FavoriteDto dto){
        tableFavorite.getItems().add(dto);
    }

    /**
     * Sets button handlers for all buttons in application .
     * @param presenter a StibPresenter .
     */
    public void setButtonHandlers(StibPresenter presenter) {
        search.setOnMouseClicked(mouseEvent -> {
            var origin = choiceOrigin.getValue();
            var dest = choiceDestination.getValue();
            presenter.search(origin, dest);
        });
        addFavorite.setOnMouseClicked(mouseEvent -> handlerAddToFavorite(presenter));
        delete.setOnMouseClicked(mouseEvent -> handlerDeleteFavorite(presenter));
        setActionOnFavoriteTable(presenter);
    }

}
