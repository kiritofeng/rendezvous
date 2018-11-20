package tk.kiritofeng.rendezvous;

public abstract class Utils {

    public static final double DELTA_T = 1e-6;
    public static  final double G = 6.67408e-11;
    public static final double ME = 5.972e24;
    public static final double EXHAUST_V = 2500;

    public static void update(CelestialBody ship, CelestialBody iss, CelestialBody planet) {
        // update position of ship
        ship.r = ship.r.add(ship.v.times(DELTA_T)).add(ship.a.times(0.5 * Math.pow(DELTA_T, 2)));
        // update position of iss
        iss.r = iss.r.add(iss.v.times(DELTA_T)).add(iss.a.times(0.5 * Math.pow(DELTA_T, 2)));
        // update velocity of ship
        ship.v = ship.v.add(ship.a.times(DELTA_T));
        // update velocity of iss
        iss.v = iss.v.add(iss.a.times(DELTA_T));
        // compute gravity between ship and planet
        double FG1 = G * ship.m * planet.m / Math.pow(ship.r.subtract(planet.r).magnitude(),2);
        // F = ma
        ship.a = ship.r.subtract(planet.r).normalize().times(-FG1).divide(ship.m);
        //System.out.println(ship.a);
        // compute gravity between iss and planet
        double FG2 = G * iss.m * planet.m / Math.pow(iss.r.subtract(planet.r).magnitude(),2);
        // F = ma
        iss.a = iss.r.subtract(planet.r).normalize().times(-FG2).divide(iss.m);
        //System.out.println(iss.a);
    }

    public static void burn(CelestialBody ship, double mass) {
        // burn for a single tick
        ship.v = ship.v.add(ship.v.normalize().times(mass * Math.log(ship.m/(ship.m-mass))));
        ship.m -= mass;
    }

    public static Vector pos(double p, double a, double theta) {
        return new Vector(p * Math.cos(Math.toRadians(theta)), p * Math.sin(Math.toRadians(theta)));
    }

    public static Vector vel(double p, double a, double theta) {
        double v = Math.sqrt(G * ME * (2 / p - 1/a));
        return new Vector(-v * Math.sin(Math.toRadians(theta)), v * Math.cos(Math.toRadians(theta)));
    }

    // points towards p1
    public static Vector gravity(Vector p1, double m1, Vector p2, double m2) {
        return p1.subtract(p2).normalize().times(G * m1 * m2 / Math.pow(p1.subtract(p2).magnitude(),2));
    }
}
