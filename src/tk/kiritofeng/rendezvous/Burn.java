package tk.kiritofeng.rendezvous;

public class Burn implements Comparable<Burn> {

    private double start, duration, mass, direction;

    public Burn(double t, double d, double m, double theta) {
        start = t ;
        duration = d;
        mass = m;
        direction = theta;
    }

    // dirrection of burn's velocity vector, going counterclockwise
    public double getDuration() {
        return duration;
    }

    public double getMass() {
        return mass;
    }

    public double getDirection() {
        return direction;
    }

    public double getStart() {
        return start;
    }

    public int compareTo(Burn b) {
        if(start < b.getStart()) return -1;
        else if(start > b.getStart()) return 1;
        return 0;
    }
}
