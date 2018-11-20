package tk.kiritofeng.rendezvous;

public class Vector {
    double x, y, z;

    public Vector() {
        x = y = 0;
    }

    public Vector(double _x, double _y) {
        x = _x;
        y = _y;
        z = 0;
    }

    public Vector(double _x, double _y, double _z) {
        x = _x;
        y = _y;
        z = _z;
    }

    public Vector add(Vector V) {
        return new Vector(x + V.x, y + V.y, z + V.z);
    }

    public Vector subtract(Vector V) {
        return new Vector(x - V.x, y - V.y, z - V.z);
    }

    public Vector times(double d) {
        return new Vector(x * d, y * d, z * d);
    }

    public Vector divide(double d) {
        return new Vector(x / d, y / d, z / d);
    }

    public double dot(Vector V) {
        return x * V.x + y * V.y + z * V.z;
    }

    public Vector cross(Vector V) {
        return new Vector(y * V.z - z * V.y,z * V.x - x * V.z,x * V.y - y * V.x);
    }

    public double magnitude() {
        return Math.sqrt(dot(this));
    }

    public Vector normalize() {
        return divide(magnitude());
    }

    public String toString() {
        return String.format("<%f, %f, %f>", x, y, z);
    }
}

class CelestialBody {

    Vector r, v, a;
    double m;

    public CelestialBody(double mass, Vector position, Vector velocity, Vector acceleration) {
        m = mass;
        r = position;
        v = velocity;
        a = acceleration;
    }
}
