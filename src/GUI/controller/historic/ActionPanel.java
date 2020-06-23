package GUI.controller.historic;

import GUI.controller.panel.ImagePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Created by jean on 27/04/2016.
 */
public class ActionPanel implements Serializable {
    public String name;
    public ImagePanel imgp;
    /*
    java的serialization提供了一个非常棒的存储对象状态的机制，
    说白了serialization就是把对象的状态存储到硬盘上去，
    等需要的时候就可以再把它读出来使用。
    有些时候像银行卡号这些字段是不希望在网络上传输的，
    transient的作用就是把这个字段的生命周期仅存于调用者的内存中而不会写到磁盘里持久化，
    意思是transient修饰的age字段，他的生命周期仅仅在内存中，不会被写到磁盘中。
     */
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
        Graphics g = b.getGraphics(); //相当于画布
        g.drawImage(source, 0, 0, null);
        //自己创建的副本用完要销毁掉
        g.dispose();
        this.img = b;
    }
}
