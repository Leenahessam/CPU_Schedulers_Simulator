package sample;

import java.util.Comparator;

public class Process {

    private String Process_Name;
    private String Color;
    private int Arrival_Time;
    private int Burst_Time;
    private int Priority;
    private int Quantum_Time;
    private int Completion_Time;
    private int TurnAround_Time;
    private int Waiting_Time;
    private int AG_Factor;

    public Process(String process_Name, String color, int arrival_Time, int burst_Time, int priority, int quantum_Time) {
        Process_Name = process_Name;
        Color = color;
        Arrival_Time = arrival_Time;
        Burst_Time = burst_Time;
        Priority = priority;
        Quantum_Time = quantum_Time;
        AG_Factor = Arrival_Time + Burst_Time + Priority;
    }

    public Process(String process_Name, String color, int arrival_Time, int burst_Time, int priority) {
        Process_Name = process_Name;
        Color = color;
        Arrival_Time = arrival_Time;
        Burst_Time = burst_Time;
        Priority = priority;
    }

    public Process(String process_Name, String color, int arrival_Time, int burst_Time) {
        Process_Name = process_Name;
        Color = color;
        Arrival_Time = arrival_Time;
        Burst_Time = burst_Time;
    }

    public int getAG_Factor() {
        return AG_Factor;
    }

    public int getCompletion_Time() {
        return Completion_Time;
    }

    public void setCompletion_Time(int completion_Time) {
        Completion_Time = completion_Time;
    }

    public int getTurnAround_Time() {
        return TurnAround_Time;
    }

    public void setTurnAround_Time(int turnAround_Time) {
        TurnAround_Time = turnAround_Time;
    }

    public int getWaiting_Time() {
        return Waiting_Time;
    }

    public void setWaiting_Time(int waiting_Time) {
        Waiting_Time = waiting_Time;
    }

    public String getProcess_Name() {
        return Process_Name;
    }

    public String getColor() {
        return Color;
    }

    public int getArrival_Time() {
        return Arrival_Time;
    }

    public int getBurst_Time() {
        return Burst_Time;
    }

    public void setBurst_Time(int burst_Time) {
        Burst_Time = burst_Time;
    }

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }

    public int getQuantum_Time() {
        return Quantum_Time;
    }

    public void setQuantum_Time(int quantum_Time) {
        Quantum_Time = quantum_Time;
    }

    public static Comparator<Process> arrTime = new Comparator<Process>() {
        @Override
        public int compare(Process o1, Process o2) {
            return o1.Arrival_Time - o2.Arrival_Time;
        }
    };

    public static Comparator<Process> burstTime = new Comparator<Process>() {
        @Override
        public int compare(Process o1, Process o2) {
            return o1.Burst_Time - o2.Burst_Time;
        }
    };
}
