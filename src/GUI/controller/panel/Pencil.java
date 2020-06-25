package GUI.controller.panel;

import GUI.model.DrawModel;

import java.awt.*;

/** 铅笔 **/
public class Pencil extends Pen{

    Pencil(Graphics2D graphics2D, DrawModel drawModel, Point p) {
        super(graphics2D, drawModel, p);
    }

    @Override
    public void draw() {
        g.fillRect(p.x, p.y, model.getSize(), model.getSize());
    }

}
