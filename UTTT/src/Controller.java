import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button PVC;

    @FXML
    private Button PVP;

    @FXML
    private Button Easy;

    @FXML
    private Button Hard;

    @FXML
    private Button Medium;
    @FXML
    void initialize() {
        assert PVC != null : "fx:id=\"PVC\" was not injected: check your FXML file 'Menu.fxml'.";
        assert PVP != null : "fx:id=\"PVP\" was not injected: check your FXML file 'Menu.fxml'.";
        assert Easy != null : "fx:id=\"Easy\" was not injected: check your FXML file 'Difficulty.fxml'.";
        assert Hard != null : "fx:id=\"Hard\" was not injected: check your FXML file 'Difficulty.fxml'.";
        assert Medium != null : "fx:id=\"Medium\" was not injected: check your FXML file 'Difficulty.fxml'.";
    }


    public void PlayPVP(Event event) throws Exception
    {
            Main game=new Main(true);
            game.start((Stage)((Node)event.getSource()).getScene().getWindow());
    }

    public void playPVC(Event event) throws IOException
    {
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene;
        Parent root= FXMLLoader.load(getClass().getResource("Difficulty.fxml"));
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void chooseDif(Event event) throws Exception
    {
        Main game=new Main(false);
        game.start((Stage)((Node)event.getSource()).getScene().getWindow());
    }
}

