package GUI.controller.historic;

import GUI.controller.panel.ImagePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * 记录Action
 */
public class ActionPanel implements Serializable {
    public String name;
    public ImagePanel imgp;
    public transient BufferedImage img;

    public ActionPanel(String name,BufferedImage img) {
        copyImage(img);
        this.name = name;
        this.imgp = new ImagePanel(this.img, name);
    }

    public void rebuildImages() {
        this.imgp.buildImage();
        this.img = this.imgp.getImage();
    }
    public BufferedImage getImg() {
        return img;
    }
    public String getName() {
        return name;
    }

    public void copyImage(BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        //自己创建的副本用完要销毁掉
        g.dispose();
        this.img = b;
    }
}
