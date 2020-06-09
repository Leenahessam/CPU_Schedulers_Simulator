package sample;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class AGScene extends Schedular {

    private Queue<Process> readyQueue = new LinkedList<>();
    private ArrayList<Process> dieList = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> Quantums = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> halfQuantums = new ArrayList<>();

    public void updateQueue(){
        for (int i = 0; i < processes.size();) {
            if(processes.get(i).getArrival_Time() > time)
                break;
            readyQueue.add(processes.get(i));
            processes.remove(i);
        }
    }


    public Process getLeast_AG_Factor(){
        if(readyQueue.isEmpty())
            return null;
        Process min  = readyQueue.peek();
        for(Process process : readyQueue){
            if(process.getAG_Factor() < min.getAG_Factor())
                min = process;
        }
        return min;
    }


    public void updateQuantums(ArrayList<Process> temp){
        ArrayList<Integer> Q = new ArrayList<>();
        ArrayList<Integer> halfQ = new ArrayList<>();
        for (Process process : temp){
            Q.add(process.getQuantum_Time());
            int halfQuantum = (int) Math.ceil(process.getQuantum_Time()/2.0);
            halfQ.add(halfQuantum);
        }
        Quantums.add(Q);
        halfQuantums.add(halfQ);
    }


    public void schedule(){
        ArrayList<Process> temporary = new ArrayList<>();
        temporary.addAll(processes);
        processes.sort(Process.arrTime);
        readyQueue.add(processes.get(0));
        time = processes.get(0).getArrival_Time();
        processes.remove(0);
        Process running = readyQueue.poll();
        execution_order.add(new Running_Process(running.getProcess_Name(), running.getColor(), time, 0));
        updateQuantums(temporary);

        while (dieList.size() != nProcess){
            updateQueue();

            int halfQuantum = time + (int) Math.ceil(running.getQuantum_Time()/2.0);
            int end = time + running.getQuantum_Time();
            Process current = running;
            while (time < end && running.getBurst_Time() != 0){
                if(time >= halfQuantum){
                    Process min = getLeast_AG_Factor();
                    if(min != null && min.getAG_Factor() < running.getAG_Factor()){
                        running.setQuantum_Time(running.getQuantum_Time() + (end - time));
                        current  = running;
                        readyQueue.add(running);
                        running = min;
                        readyQueue.remove(min);
                        execution_order.add(new Running_Process(running.getProcess_Name(), running.getColor(), time, 0));
                        updateQuantums(temporary);
                        break;
                    }
                }
                time++;
                updateQueue();
                running.setBurst_Time(running.getBurst_Time()-1);
            }
            if(running == current){
                if(running.getBurst_Time() == 0){
                    running.setQuantum_Time(0);
                    running.setCompletion_Time(time);
                    int index = search(running.getProcess_Name());
                    originalProcesses.get(index).setCompletion_Time(time);
                    dieList.add(running);
                }
                else{
                    float meanQ = running.getQuantum_Time();
                    for (Process process : readyQueue)
                        meanQ += process.getQuantum_Time();
                    meanQ /= readyQueue.size() + 1;
                    int temp = (int) Math.ceil(meanQ * (0.1));
                    running.setQuantum_Time(running.getQuantum_Time() + temp);
                    readyQueue.add(running);
                }
                running = readyQueue.poll();
                if(running != null)
                    execution_order.add(new Running_Process(running.getProcess_Name(), running.getColor(), time, 0));
                updateQuantums(temporary);
            }
        }

        for (int i = 0; i < execution_order.size()-1; i++)
            execution_order.get(i).setEnd(execution_order.get(i+1).getStart());
        execution_order.get(execution_order.size()-1).setEnd(time);
    }
}
