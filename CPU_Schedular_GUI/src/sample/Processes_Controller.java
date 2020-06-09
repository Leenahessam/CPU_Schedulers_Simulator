package sample;

import com.sun.javafx.image.BytePixelSetter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
//import org.jfree.data.category.IntervalCategoryDataset;

import javax.print.attribute.standard.OrientationRequested;
import java.io.IOException;

public class Processes_Controller {
    @FXML
    TextField name;
    @FXML
    TextField color;
    @FXML
    TextField arrivalTime;
    @FXML
    TextField burstTime;
    @FXML
    TextField priority;
    @FXML
    Button add;
    @FXML
    Button schedule;


    public void enableAdd(){
        if( Controller.chosen.equals("Priority Scheduling") || Controller.chosen.equals("AG Scheduling") ){
            if(name.getText().isEmpty() || color.getText().isEmpty() || arrivalTime.getText().isEmpty() || burstTime.getText().isEmpty() || priority.getText().isEmpty())
                add.setDisable(true);
            else
                add.setDisable(false);
        }
        else{
            if(name.getText().isEmpty() || color.getText().isEmpty() || arrivalTime.getText().isEmpty() || burstTime.getText().isEmpty())
                add.setDisable(true);
            else
                add.setDisable(false);
        }
    }


    public void Clear(){
        name.clear();
        color.clear();
        arrivalTime.clear();
        burstTime.clear();
        priority.clear();
    }


    public void addButton(){
        String Name = name.getText();
        String Color = color.getText();
        int aTime = Integer.parseInt(arrivalTime.getText());
        int bTime = Integer.parseInt(burstTime.getText());

        if(Controller.chosen.equals("SJF Scheduling") || Controller.chosen.equals("SRTF Scheduling")){
            Schedular.processes.add(new Process(Name, Color, aTime, bTime));
            Schedular.originalProcesses.add(new Process(Name, Color, aTime, bTime));
        }
        else if(Controller.chosen.equals("AG Scheduling")){
            int Priority = Integer.parseInt(priority.getText());
            int Quantum = Controller.input;
            Schedular.processes.add(new Process(Name, Color, aTime, bTime, Priority, Quantum));
            Schedular.originalProcesses.add(new Process(Name, Color, aTime, bTime, Priority, Quantum));
        }

        else if(Controller.chosen.equals("Priority Scheduling")){
            int Priority = Integer.parseInt(priority.getText());
            Schedular.processes.add(new Process(Name, Color, aTime, bTime, Priority));
            Schedular.originalProcesses.add(new Process(Name, Color, aTime, bTime, Priority));
        }

        Clear();
        schedule.setDisable(false);
        add.setDisable(true);
    }


    public void scheduleButton() throws IOException {
        Schedular.nProcess = Schedular.processes.size();

        if(Controller.chosen.equals("SJF Scheduling")){
            SJFScene sjfScene = new SJFScene();
            sjfScene.schedule();
        }

        else if(Controller.chosen.equals("SRTF Scheduling")){
            SRTFScene srtfScene = new SRTFScene();
            srtfScene.setContextSwitching(Controller.input);
            srtfScene.schedule();
        }

        else if(Controller.chosen.equals("Priority Scheduling")){
            PriorityScene priorityScene = new PriorityScene();
            priorityScene.schedulePriority();
        }

        else if(Controller.chosen.equals("AG Scheduling")){
            AGScene agScene = new AGScene();
            agScene.schedule();
        }

        Schedular.calculateAvg();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GanttChart.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle(Controller.chosen + " Technique");
        stage.setScene(new Scene(root1));
        stage.show();

        schedule.setDisable(true);
    }

    public static void reset(){
        Schedular.processes.clear();
        Schedular.originalProcesses.clear();
        Schedular.execution_order.clear();
        Schedular.avg_TurnAround = 0;
        Schedular.avg_Waiting = 0;
        Schedular.time = 0;
        AGScene.Quantums.clear();
        AGScene.halfQuantums.clear();
    }
}
