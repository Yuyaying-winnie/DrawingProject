package GUI.controller.panel;

import GUI.model.DrawModel;

import java.awt.*;

/** 铅笔 **/
public class Pencil extends Pen{
    public static int startX,startY,endX,endY;
    Pencil(Graphics2D graphics2D, DrawModel drawModel,Point p,int n) {
        super(graphics2D, drawModel,p,n);
    }

    @Override
    public void draw() {
        g.setStroke(new BasicStroke(model.getSize()));
        if(n==0) {
            startX = p.x;
            startY = p.y;
            n++;
        }else if(n==1){
            endX = p.x;
            endY = p.y;
            g.drawLine(startX, startY, endX, endY);
            startX = endX;
            startY = endY;
        }
        n=0;


    }

}
