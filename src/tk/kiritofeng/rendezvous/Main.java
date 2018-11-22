package tk.kiritofeng.rendezvous;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    static final double THETA_SHUTTLE = 305.26;
    static final double THETA_ISS = 1.7980;
    static final double APOGEE = 327000 + Utils.RE;
    static final double PERIGEE = 292000 + Utils.RE;
    static final double RADIUS = 400000 + Utils.RE;
    static final double MASS_SHUTTLE = 77564.2953;
    static final double MASS_ISS = 417289;
    static final int RENDER_RATE=1;

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("sun.java2d.opengl", "true");
        JFrame frame = new JFrame();
        CelestialBody earth = new CelestialBody(Utils.ME, new Vector(), new Vector(), new Vector());
        Vector pos = Utils.pos(PERIGEE, APOGEE, THETA_SHUTTLE);
        CelestialBody shuttle = new CelestialBody(MASS_SHUTTLE, pos, Utils.vel(PERIGEE,APOGEE,THETA_SHUTTLE),
                Utils.gravity(earth.r, Utils.ME, pos, MASS_SHUTTLE));
        pos = Utils.pos(RADIUS, RADIUS, THETA_ISS);
        CelestialBody ISS = new CelestialBody(MASS_ISS, pos, Utils.vel(RADIUS, RADIUS, THETA_ISS),
                Utils.gravity(earth.r, Utils.ME, pos, MASS_ISS));
        DrawPane pane = new DrawPane(shuttle, earth, ISS);
        pane.setSize(400,400);
        pane.setMinimumSize(new Dimension(400,400));
        frame.add(pane);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setPreferredSize(new Dimension(400,400));
        frame.setTitle("Rendezvous Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        ArrayList<Burn> burns = new ArrayList<>();
        ArrayList<Double> POI = new ArrayList<>();
        ArrayList<Pair> RTS = new ArrayList<>();
        // add burns
        burns.add(new Burn(1.5 * 3600 / Utils.DELTA_T,10 / Utils.DELTA_T,3500,180));
        burns.add(new Burn( (3600 + 40 * 60) / Utils.DELTA_T,5 / Utils.DELTA_T,700,0));
        burns.add(new Burn((5 * 3600 + 50 * 60) / Utils.DELTA_T, 10 / Utils.DELTA_T, 3000, 0));
        //burns.add(new Burn((5 * 3600 + 60 * 60) / Utils.DELTA_T, 5 / Utils.DELTA_T, 500, 0));
        burns.add(new Burn((6 * 3600 + 5 * 60) / Utils.DELTA_T, 55 / Utils.DELTA_T, 1000, 90));
        // r-bar
        //burns.add(new Burn((2 * 3600 + 40 * 60) / Utils.DELTA_T, 5 / Utils.DELTA_T, 400, 270));
        //burns.add(new Burn(3*3600 / Utils.DELTA_T, 5 / Utils.DELTA_T, 100, 180));

        POI.add((6 * 3600 + 50 * 60) / Utils.DELTA_T);
        POI.add((6 * 3600 + 51 * 60) / Utils.DELTA_T);
        POI.add(7 * 3600 / Utils.DELTA_T);

        //RTS.add(new Pair((6 * 3600 + 0 * 60) / Utils.DELTA_T, 120 / Utils.DELTA_T));
        RTS.add(new Pair((6 * 3600 + 40 * 60) / Utils.DELTA_T, 120 / Utils.DELTA_T));
        RTS.add(new Pair((6 * 3600 + 47 * 60) / Utils.DELTA_T, 120 / Utils.DELTA_T));
        RTS.add(new Pair((6 * 3600 + 51 * 60) / Utils.DELTA_T, 120 / Utils.DELTA_T));
        RTS.add(new Pair((6 * 3600 + 53 * 60) / Utils.DELTA_T, 60 / Utils.DELTA_T));
        //RTS.add(new Pair((3 * 3600) / Utils.DELTA_T, 120 / Utils.DELTA_T));

        for(long i = 0, j = 0, k = 0, l = 0; i < 30 * 3600 / Utils.DELTA_T; ++i) {
            if(j < burns.size()) {
                if (burns.get((int) j).getStart() <= i && i <= burns.get((int) j).getStart() + burns.get((int) j).getDuration())
                    Utils.burn(pane.ship, burns.get((int) j));
                else if(burns.get((int) j).getStart() + burns.get((int) j).getDuration() < i) {
                    // probably a good idea to get some info
                    System.out.printf("Executing burn #%d\n", j + 1);
                    System.out.printf("Mass: %f, Velocity: %f, Expected Velocity: %f\n",
                            pane.ship.m, pane.ship.v.magnitude(), Math.sqrt(Utils.G * Utils.ME / pane.ship.r.magnitude()));
                    ++j;
                }
            }
            /*
            if(i % 10000 == 0) {
                int hr = (int)(i / (60 * 60 / Utils.DELTA_T));
                int mn = (int)(i / (60 / Utils.DELTA_T)) % 60;
                int sc = (int)(i * Utils.DELTA_T) % 60;
                int msc = (int)(i % (int)(1/Utils.DELTA_T))/10;

                System.out.printf("%02d:%02d:%02d:%03d\n",hr,mn,sc,msc);
            }
            */
            // Points of Interest
            if(k < POI.size()) {
                if(POI.get((int) k) == i) {
                    int hr = (int)(i / (60 * 60 / Utils.DELTA_T));
                    int mn = (int)(i / (60 / Utils.DELTA_T)) % 60;
                    int sc = (int)(i * Utils.DELTA_T) % 60;
                    int msc = (int)(i % (int)(1/Utils.DELTA_T))/10;

                    System.out.printf("%02d:%02d:%02d:%03d\n",hr,mn,sc,msc);
                    System.out.printf("Mass of Shuttle: %s\nPosition of Shuttle: %s\nVelocity of Shuttle: %s\nPosition of ISS: %s\nVelocity of ISS: %s\nDistance between ISS and Shuttle: %s\n",
                            pane.ship.m,pane.ship.r, pane.ship.v, pane.iss.r,pane.iss.v,pane.iss.r.subtract(pane.ship.r));
                    System.out.printf("Velocity of Shuttle: %f\nExpected Velocity: %f\nRelative Velocity: %f\n",
                            pane.ship.v.magnitude(), Math.sqrt(Utils.G * Utils.ME / pane.ship.r.magnitude()), pane.ship.v.magnitude() - pane.iss.v.magnitude());
                    ++k;
                }
            }
            Utils.update(pane.ship, pane.iss, pane.planet);
            pane.advance();
            if(i % RENDER_RATE == 0)
                pane.repaint();
            // RTS
            if(l < RTS.size()) {
                if((Double)RTS.get((int) l).first <= i) {
                    if((Double)RTS.get((int) l).first + (Double)RTS.get((int) l).second < i) {
                        ++l;
                    } else {
                        if(i % 10000 == 0) {
                            Thread.sleep(100);
                            System.out.printf("ISS: %f, Shuttle: %f\nExpected: %f, Actual: %f\n",
                                    pane.iss.r.magnitude(), pane.ship.r.magnitude(), pane.ship.v.magnitude(), Math.sqrt(Utils.G * Utils.ME / pane.ship.r.magnitude()));
                        }
                    }
                }
            }
        }
    }
}
