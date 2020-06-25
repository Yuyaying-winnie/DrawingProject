package GUI.controller.panel;

import GUI.controller.MainController;
import GUI.controller.historic.ActionPanel;
import GUI.controller.historic.HistoricController;
import GUI.model.DrawModel;
import GUI.model.HistoricModel;
import GUI.model.MainModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * We give you this class to help you display images.
 * You are free to use it or not, to modify it.
 */
public class ImagePanel extends JPanel implements Serializable, MouseListener, MouseMotionListener {
    private static final long serialVersionUID = -314159265358979323L;
    private String fileName;
    public String path = "";
    public transient int modify;
    private final int width;
    private final int height;
    private final int imageType;
    private final int[] pixels;
    public transient BufferedImage image;
    public transient boolean mouseIn = false;
    public transient boolean dragged = false;
    public HistoricController historic;
    
    public int x1, x2, y1, y2;  //

    /**
     * Create the ImagePanel
     *
     * @param image: image to display
     * @param name:  name of the image
     */
    public ImagePanel(BufferedImage image, String name) {
        fileName = name;
        this.image = image;
        this.modify = 0;
        width = image.getWidth();
        height = image.getHeight();
        imageType = image.getType();
        pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        addMouseMotionListener(this);
        addMouseListener(this);
    }


    /**
     * Create the ImagePanel
     *
     * @param file: image to display
     */
    public ImagePanel(File file) {
        try {
            image = ImageIO.read(file);
            fileName = file.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = image.getWidth();
        height = image.getHeight();
        imageType = image.getType();
        pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    /**
     * Create the bufferImage after deserialization.
     */
    public void buildImage() {
        image = new BufferedImage(width, height, imageType);
        image.setRGB(0, 0, width, height, pixels, 0, width);

    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.modify = 1;
        this.image = image;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setHistoric() {
        this.historic = HistoricModel.getInstance().getHistoric();
    }

    public HistoricController getHistoric() {
        return historic;
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getX() > 0 && e.getX() < image.getWidth() && e.getY() > 0 && e.getY() < image.getHeight())
            mouseIn = true;
        else
            mouseIn = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        DrawModel model = DrawModel.getInstance();
        if (!mouseIn) return;
        if (model.getType().equals("bucket")) {    
        	bucketFill(e.getPoint());
        }
        if (!model.getType().equals("polygon")) {
        	return;
        }
        /*
        if (model.getShape().equals("Polygon")) {
        	
            model.addPoint(e.getPoint());
            System.out.print("点击Polygon");
            if (model.getNbshape() == model.getClickPoints().size())
                drawPolygon();
            return;
        }
        */
        //if polygon is not selected, reset all point
        if (model.getClickPoints().size() > 1) model.resetPoint();
        if (model.getClickPoints().isEmpty()) {
            model.addPoint(e.getPoint());
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(model.getColor());
        //drawRectangle(e.getPoint(), model.getClickPoints().get(0), g);
        g.dispose();
        repaint();
        model.resetPoint();
        MainController.applyModification(image, new ActionPanel(DrawModel.getInstance().getType(), image));
    }

    /**鼠标拖动**/
    @Override
    public void mouseDragged(MouseEvent e) {
        DrawModel model = DrawModel.getInstance();
        dragged = true;
        //Graphics g = image.getGraphics();  //画笔
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(model.getColor());   //取颜色

        Point p = e.getPoint();
        switch (model.getType()) {
            case "draw":
                if (model.getShape().equals("Pencil")) {
                    Pencil pencil = new Pencil(g,model,p);
                    pencil.draw();
                } else if (model.getShape().equals("Crayon")){
                     Crayon crayon = new Crayon(g,model,p);
                     crayon.draw();
                } else if (model.getShape().equals("InkBrush")) {
                    InkBrush inkBrush = new InkBrush(g,model,p,dragged);
                    inkBrush.draw();
                }
                break;
            case "erase":
                g.setColor(new Color(255, 255, 255, model.getOpacity()));
                g.fillOval(p.x, p.y, model.getSize(), model.getSize());
                break;        
        }
        g.dispose();
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	x1 = e.getX();
		y1 = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	DrawModel model = DrawModel.getInstance();
    	x2 = e.getX();
		y2 = e.getY();
        //if (!mouseIn || !dragged || DrawModel.getInstance().getType().equals("polygon")|| DrawModel.getInstance().getType().equals("bucket")) return;
        if (!mouseIn || !dragged || DrawModel.getInstance().getType().equals("bucket")) return;
        
        if(!DrawModel.getInstance().getType().equals("erase") && DrawModel.getInstance().getShape().isEmpty()) return;
        MainController.applyModification(image, new ActionPanel(DrawModel.getInstance().getType(), image));
        dragged = false;
        //Graphics g = image.getGraphics();
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(model.getColor());
        
        drawshape(x1, y1, x2, y2,g);

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    //修改
    public void drawshape(int x1, int y1, int x2, int y2, Graphics g) {
    	//System.out.println("绘图");
        DrawModel model = DrawModel.getInstance();
        switch (model.getShape()) {
            case "Line":
            	g.drawLine(x1, y1, x2, y2);
            	break;
            case "Rectangle":		
            	g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));			
            	break;
            case "Circle":
            	int r = Math.abs(x1 - x2);					
            	g.drawOval(x2 - r, y2 - r, 2 * r, 2 * r);
            	
        }
    }
    /*
    //画多边形
    public void drawPolygon() {
        DrawModel drawModel = DrawModel.getInstance();
        Graphics g = image.getGraphics();
        g.setColor(drawModel.getColor());


        int[] xpoint = new int[drawModel.getNbshape()];
        int[] ypoint = new int[drawModel.getNbshape()];

        for (int i = 0; i < drawModel.getNbshape(); i++) {
            xpoint[i] = (int) drawModel.getClickPoints().get(i).getX();
            ypoint[i] = (int) drawModel.getClickPoints().get(i).getY();
        }

        Polygon p = new Polygon(xpoint, ypoint, drawModel.getNbshape());
        g.fillPolygon(p);
        g.dispose();
        repaint();
        drawModel.resetPoint();
        MainController.applyModification(image, new ActionPanel(DrawModel.getInstance().getType(), image));
        return;
    }
	*/
    public void bucketFill(Point p) {
        DrawModel drawModel = DrawModel.getInstance();
        BufferedImage img = MainModel.getInstance().getImg().getImage();
        fillPixels(img, img.getRGB(p.x, p.y), p, drawModel.getColor().getRGB());
        repaint();
        drawModel.resetPoint();
        MainController.applyModification(image, new ActionPanel(DrawModel.getInstance().getType(), image));
        return;
    }


    public void fillPixels(BufferedImage img, int color, Point p, int c) {
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(p);
        if(c == color) return;
        while (points.size() != 0) {
            p = points.get(0);
            if (img.getRGB(p.x + 1, p.y) == color) {
                Point p1 = new Point(p.x + 1, p.y);
                if (!points.contains(p1) && p.x + 1 < img.getWidth() - 1)
                    points.add(p1);
            }
            if (img.getRGB(p.x - 1, p.y) == color) {
                Point p1 = new Point(p.x - 1, p.y);
                if (!points.contains(p1) && p.x - 1 > 1)
                    points.add(p1);
            }
            if (img.getRGB(p.x, p.y + 1) == color) {
                Point p1 = new Point(p.x, p.y + 1);
                if (!points.contains(p1) && p.y + 1 < img.getHeight() - 1)
                    points.add(p1);
            }

            if (img.getRGB(p.x, p.y - 1) == color) {
                Point p1 = new Point(p.x, p.y - 1);
                if (!points.contains(p1) && p.y - 1 > 1)
                    points.add(p1);
            }

            img.setRGB(p.x, p.y, c);
            if (points.size() > 10000)
                return;
            points.remove(0);
        }
    }


}

