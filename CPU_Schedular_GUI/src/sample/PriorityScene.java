package sample;

import java.util.ArrayList;

public class PriorityScene extends Schedular {
    ArrayList<Process> doneProcesses = new ArrayList<>();

    public void schedulePriority() {

        while ((processes.size() != 0) || (doneProcesses.size() != 0)) {
            for (int i = 0; i < processes.size(); i++) {
                if (processes.get(i).getArrival_Time() <= time) {
                    doneProcesses.add(processes.get(i));
                    processes.remove(processes.get(i));
                    i--;
                }
            }
            int highP = getHighPriority();
            if (highP == -1) {
                time++;
                continue;
            }
            execution_order.add(new Running_Process(doneProcesses.get(highP).getProcess_Name(), doneProcesses.get(highP).getColor(), time, time + doneProcesses.get(highP).getBurst_Time()));
            time += doneProcesses.get(highP).getBurst_Time();
            doneProcesses.get(highP).setCompletion_Time(time);
            int index = search(doneProcesses.get(highP).getProcess_Name());
            originalProcesses.get(index).setCompletion_Time(time);
            doneProcesses.remove(highP);

            for (int i = 0; i < doneProcesses.size(); i++)
                doneProcesses.get(i).setPriority(doneProcesses.get(i).getPriority() - 1);

        }
        processes.clear();

    }

    public int getHighPriority() { //Returns the least Integer's index
        int minIntegerIdx = 0;
        boolean notExist = true;
        for (int i = 0; i < doneProcesses.size(); i++) {
            if (doneProcesses.get(i).getPriority() < doneProcesses.get(minIntegerIdx).getPriority()) {
                minIntegerIdx = i;
                notExist = false;
            } else if (doneProcesses.get(i).getPriority() == doneProcesses.get(minIntegerIdx).getPriority()) {
                int temp = minIntegerIdx;
                notExist = false;
                if (doneProcesses.get(i).getArrival_Time() <= doneProcesses.get(minIntegerIdx).getArrival_Time()) {
                    minIntegerIdx = i;
                } else {
                    minIntegerIdx = temp;
                }
            }
        }
        if (notExist)
            return -1;
        return minIntegerIdx;
    }
}
