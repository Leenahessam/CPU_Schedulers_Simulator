package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;


public class Controller {
    @FXML
    ComboBox choice;
    @FXML
    Button scheduleButton;
    @FXML
    TextField textField1;

    public static String chosen;
    public static int input;


    public void comboAction(){
        chosen = choice.getSelectionModel().getSelectedItem().toString();

        if (chosen.equals("SRTF Scheduling")) {
            textField1.setPromptText("Enter context switching");
            textField1.setVisible(true);
        }

        else if(chosen.equals("AG Scheduling")){
            textField1.setPromptText("Enter time quantum");
            textField1.setVisible(true);
        }

        else
            scheduleButton.setDisable(false);
    }


    public void enable(){
        input = Integer.parseInt(textField1.getText());
        scheduleButton.setDisable(false);
    }


    public void schedulerChoice() throws IOException {

        chosen = choice.getSelectionModel().getSelectedItem().toString();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Processes.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Processes Schedular");
        stage.setScene(new Scene(root1));
        stage.show();

        textField1.clear();
        textField1.setVisible(false);
        scheduleButton.setDisable(true);
    }

}
