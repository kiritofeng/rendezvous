package tk.kiritofeng.rendezvous;

import javax.swing.*;
import java.awt.*;

public class Main {

    /*
    static final double THETA_SHUTTLE = 305.26;
    static final double THETA_ISS = 1.7980;
    static final double APOGEE = 327000;
    static final double PERIGEE = 292000;
    */
    static final double THETA_SHUTTLE = 322.76;
    static final double THETA_ISS = 128.4;
    static final double APOGEE = 362500;
    static final double PERIGEE = 292000;
    static final double RADIUS = 400000;
    static final double MASS_SHUTTLE = 77564.2953;
    static final double MASS_ISS = 417289;
    // don't render every frame
    static final int RENDER_RATE=1000000;

    public static void main(String[] args) {
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
        for(long i = 0; i < 1.5 * 3600 / Utils.DELTA_T; ++i) {
            Utils.update(pane.ship, pane.iss, pane.planet);
            pane.advance();
            if(i % RENDER_RATE == 0)
                pane.repaint();
        }
    }
}
