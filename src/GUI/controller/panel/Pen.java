package GUI.controller.panel;

/**
 * @description:
 * @author: Winnie
 * @time: 2020/6/24 15:41
 */

import GUI.model.DrawModel;

import java.awt.*;

/** 画笔 **/
public abstract class Pen {
    public Graphics2D g;
    public DrawModel model;
    public Point p;
    public transient boolean dragged;

    Pen(Graphics2D graphics2D,DrawModel drawModel,Point p){
       this.g = graphics2D;
       this.model = drawModel;
       this.p = p;
    }

    Pen(Graphics2D graphics2D,DrawModel drawModel,Point p,boolean dragged){
        this.g = graphics2D;
        this.model = drawModel;
        this.p = p;
        this.dragged = dragged;
    }

    public abstract void draw();
}
