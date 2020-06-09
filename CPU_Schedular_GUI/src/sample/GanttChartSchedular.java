package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.paint.Paint;


public class GanttChartSchedular implements Initializable{

    @FXML
    TableView<Process> tableView;
    @FXML
    TableColumn<Process, String> nameCol = new TableColumn<>("Product_Name");
    @FXML
    TableColumn<Process, String> colorCol = new TableColumn<>("Color");
    @FXML
    TableColumn<Process, Integer> turnAroundCol = new TableColumn<>("TurnAround_Time");
    @FXML
    TableColumn<Process, Integer> waitingCol = new TableColumn<>("Waiting_Time");
    @FXML
    TextArea Averages;
    @FXML
    TextArea Execution_order;
    @FXML
    HBox hbox;

    ObservableList<Process> data;
    ArrayList<Rectangle> rectangles = new ArrayList<>();


    public void fillTable(){
        data = FXCollections.observableArrayList();
        for (Process process : Schedular.originalProcesses) {
            data.add(process);
        }
        tableView.setItems(data);
    }

    public String setExecution_order(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Execution order of the processes:\n");
        for (Running_Process process : Schedular.execution_order)
            stringBuilder.append(process.toString());
        return stringBuilder.toString();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Process_Name"));
        colorCol.setCellValueFactory(new PropertyValueFactory<>("Color"));
        turnAroundCol.setCellValueFactory(new PropertyValueFactory<>("TurnAround_Time"));
        waitingCol.setCellValueFactory(new PropertyValueFactory<>("Waiting_Time"));
        fillTable();

        Averages.setText("- Average Turnaround Time = " + Schedular.avg_TurnAround + "\n- Average Waiting Time = " + Schedular.avg_Waiting);
        Execution_order.setText(setExecution_order());
        if(Controller.chosen.equals("AG Scheduling"))
            Execution_order.appendText(QuantumsHistory());
        Displayy();
        Processes_Controller.reset();
    }

    public String QuantumsHistory(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\nHistory update of quantum time for each process:");
        for (int i = 0; i < AGScene.Quantums.size()-1; i++)
            stringBuilder.append("\n- " + AGScene.Quantums.get(i) + " -> ceil (50%) = " + AGScene.halfQuantums.get(i) + "  " + Schedular.execution_order.get(i).getName() + " Running");
        stringBuilder.append("\n- " + AGScene.Quantums.get(AGScene.Quantums.size()-1));
        return stringBuilder.toString();
    }

    public void Displayy(){
        displayGantt();
        for (int i = 0; i < rectangles.size(); i++) {
            hbox.getChildren().add(rectangles.get(i));
        }
        hbox.setAlignment(Pos.BASELINE_CENTER);
        hbox.setSpacing(3);
    }


    public void displayGantt() {
        for (int i = 1; i <= Schedular.time; i++) {
            Rectangle rect = new Rectangle();
            rect.setWidth(20);
            rect.setHeight(35);
            rect.setVisible(true);
            String colorOfProcess = searchProcess(i).getColorr();
            rect.setFill(Paint.valueOf(colorOfProcess));
            rectangles.add(rect);
        }
    }

    public Running_Process searchProcess(int time){
        Running_Process running = new Running_Process();

        for (Running_Process running_process : Schedular.execution_order) {
            if(time <= running_process.getEnd())
                return running_process;
        }

        return running;
    }


}
