package tk.kiritofeng.rendezvous;

import javax.swing.*;
import java.awt.*;

public class DrawPane extends JPanel {

    protected CelestialBody ship, planet, iss;

    // define the 4 corners of space
    private double x1,x2,y1,y2;

    // what tick are we on?
    private long tick;

    public DrawPane(CelestialBody s, CelestialBody p, CelestialBody i) {
        ship = s;
        planet = p;
        iss = i;
        x1 = -1e6;
        x2 = 1e6;
        y1 = -1e6;
        y2 = 1e6;
        tick = 0;
    }

    public void paintComponent(Graphics G) {
        G.setColor(Color.BLACK);
        G.fillRect(0,0,getWidth(),getHeight());
        G.setColor(Color.RED);
        G.fillOval((int)Math.round(getWidth()*(ship.r.x-x1)/(x2-x1)),(int)Math.round(getHeight()*(y2 - ship.r.y)/(y2-y1)),10,10);
        G.setColor(Color.GREEN);
        G.fillOval((int)Math.round(getWidth()*(iss.r.x-x1)/(x2-x1)),(int)Math.round(getHeight()*(y2 - iss.r.y)/(y2-y1)),10,10);
        G.setColor(Color.BLUE);
        G.fillOval((int)Math.round(getWidth()*(planet.r.x-x1)/(x2-x1)),(int)Math.round(getHeight()*(y2 - planet.r.y)/(y2-y1)),10,10);
        G.setColor(Color.WHITE);
        int hr = (int)(tick/(60 * 60 / Utils.DELTA_T));
        int mn = (int)(tick/(60 / Utils.DELTA_T)) % 60;
        int sc = (int)(tick * Utils.DELTA_T) % 60;
        int msc = (int)(tick % (int)(1/Utils.DELTA_T));

        G.drawString(String.format("%02d:%02d:%02d:%06d\n",hr,mn,sc,msc),5,15);
    }

    public void advance() {
        tick++;
    }
}
