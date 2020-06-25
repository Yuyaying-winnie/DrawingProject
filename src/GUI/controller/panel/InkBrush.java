package GUI.controller.panel;

import GUI.model.DrawModel;

import java.awt.*;

/** 毛笔 **/
public class InkBrush extends Pen {

    InkBrush(Graphics2D graphics2D, DrawModel drawModel, Point p,boolean dragged) {
        super(graphics2D, drawModel, p,dragged);
    }

    @Override
    public void draw() {
        int drawSize = 0;
        while(dragged){
            drawSize++;

            while( drawSize<=model.getSize()) {
                drawSize += 1;
                g.drawOval(p.x, p.y, drawSize, drawSize);
            }

            while(drawSize>0) {
                drawSize-=1;
                g.drawOval(p.x, p.y, drawSize, drawSize);
                dragged = false;
            }
        }
    }
}
