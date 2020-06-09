package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeMap;

public class Schedular extends Application {

    public static int time = 0;
    public static int nProcess;
    public static float avg_Waiting;
    public static float avg_TurnAround;
    public static ArrayList<Process> processes = new ArrayList<>();
    public static ArrayList<Process> originalProcesses = new ArrayList<>();
    public static ArrayList<Running_Process> execution_order = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("CPU Schedular");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    public static int search(String processName){
        for (int i = 0; i < originalProcesses.size(); i++) {
            if(originalProcesses.get(i).getProcess_Name().equals(processName))
                return i;
        }
        return -1;
    }


    public static void calculateAvg(){
        for (Process process : originalProcesses) {
            process.setTurnAround_Time(process.getCompletion_Time() - process.getArrival_Time());
            process.setWaiting_Time(process.getTurnAround_Time() - process.getBurst_Time());
            avg_TurnAround += process.getTurnAround_Time();
            avg_Waiting += process.getWaiting_Time();
        }
        avg_TurnAround /= originalProcesses.size();
        avg_Waiting /= originalProcesses.size();
    }
}
