package sample;

import java.util.ArrayList;

public class SJFScene extends Schedular{

    private ArrayList<Process> readyQueue = new ArrayList<>();
    private ArrayList<Process> dieList = new ArrayList<>();

    public void updateQueue(){
        for (int i = 0; i < processes.size();) {
            if(processes.get(i).getArrival_Time() > time)
                break;
            readyQueue.add(processes.get(i));
            processes.remove(i);
        }
        readyQueue.sort(Process.burstTime);
    }

    public void schedule(){
        processes.sort(Process.arrTime);
        Process running = null;

        while(dieList.size() != nProcess){
            updateQueue();
            if(readyQueue.isEmpty()){
                time++;
                break;
            }
            running = readyQueue.get(0);
            int index = search(running.getProcess_Name());
            originalProcesses.get(index).setCompletion_Time(time + running.getBurst_Time());
            execution_order.add(new Running_Process(running.getProcess_Name(), running.getColor(), time, time + running.getBurst_Time()));
            time += running.getBurst_Time();
            readyQueue.remove(0);
            dieList.add(running);
        }

    }

}
