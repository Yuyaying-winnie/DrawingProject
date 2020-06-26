package GUI.controller.panel;

import GUI.model.DrawModel;

import java.awt.*;

/** 画笔 **/
public abstract class Pen {
    public Graphics2D g;
    public DrawModel model;
    public Point p;
    public transient boolean dragged;
    public int n;

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

    Pen(Graphics2D graphics2D,DrawModel drawModel,Point p,int n){
        this.g = graphics2D;
        this.model = drawModel;
        this.p = p;
        this.n = n;
    }

    public abstract void draw();
}
