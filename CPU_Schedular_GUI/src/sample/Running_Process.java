package sample;

public class Running_Process {

    private String name;



    private String color;
    private int start;
    private int end;


    public Running_Process(String name, String color, int start, int end) {
        this.name = name;
        this.color = color;
        this.start = start;
        this.end = end;
    }

    public Running_Process() {

    }

    public String getColorr() {
        return color;
    }

    public String getName() {
        return name;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString(){
        return ("\n- " + name + " -> " + start + " : " + end);
    }
}
