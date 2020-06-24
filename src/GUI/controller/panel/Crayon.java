package GUI.controller.panel;

import GUI.model.DrawModel;

import java.awt.*;

/** 蜡笔 **/
public class Crayon extends Pen{

    Crayon(Graphics2D g, DrawModel model,Point p) {
        super(g, model,p);
    }

    @Override
    public void draw() {
        g.fillOval(p.x, p.y, model.getSize(), model.getSize());
       // g.setStroke(new BasicStroke(model.getSize()));

    }
}

