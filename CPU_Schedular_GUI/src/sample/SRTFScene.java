package sample;

import java.util.ArrayList;

public class SRTFScene extends Schedular{

    private int contextSwitching = 0;
    ArrayList<Process> readyQueue = new ArrayList<>();

    public void setContextSwitching(int contextSwitching) {
        this.contextSwitching = contextSwitching;
    }

    public void IncomingProcess(){
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
        IncomingProcess();

        while(processes.size() != 0){
            if(readyQueue.isEmpty()){
                time++;
                IncomingProcess();
                break;
            }
            if(running != null && running != readyQueue.get(0))
                time += contextSwitching;
            running = readyQueue.get(0);
            execution_order.add(new Running_Process(running.getProcess_Name(), running.getColor(), time, time + 1));
            running.setBurst_Time(running.getBurst_Time()-1);
            time++;
            if(running.getBurst_Time() == 0){
                readyQueue.remove(0);
                int index = search(running.getProcess_Name());
                originalProcesses.get(index).setCompletion_Time(time);
            }
            IncomingProcess();
        }

        while(readyQueue.size() != 0){
            if(running != readyQueue.get(0))
                time += contextSwitching;
            running = readyQueue.get(0);
            execution_order.add(new Running_Process(running.getProcess_Name(), running.getColor(), time, time + running.getBurst_Time()));
            time += running.getBurst_Time();
            int index = search(running.getProcess_Name());
            originalProcesses.get(index).setCompletion_Time(time);
            readyQueue.remove(0);
        }
    }
}
