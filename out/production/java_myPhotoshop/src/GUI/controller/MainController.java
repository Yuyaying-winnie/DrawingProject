package GUI.controller;

import GUI.controller.filters.FiltersController;
import GUI.controller.historic.ActionPanel;
import GUI.controller.menus.MenuController;
import GUI.model.MainModel;
import GUI.view.MainView;
import filter.Filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MainController implements ActionListener {
    public MainView imgv;
    public MainModel model = MainModel.getInstance();
    public FiltersController filtersController;
    public MenuController menuController;

    public MainController(MainView imgv) {
        this.filtersController = new FiltersController();
        this.menuController = new MenuController();
        this.imgv = imgv;
    }

    public void applyFilter(Filter f) {
        if (model.panelDraw.getTabCount() <= 0) return;
        MainModel m = MainModel.getInstance();
        Thread t = new Thread() {
            public void run() {
                int s = model.panelDraw.getSelectedIndex();
                this.setName(f.getName());
                /*
                    BufferedImage 创建一个图像缓冲区
                    主要作用是将一幅图片加载到内存中（BufferedImage生成的图片在内存里有一个图像缓冲区，
                    利用这个缓冲区我们可以很方便地操作这个图片），
                    提供获得绘图对象、图像缩放、选择图像平滑度等功能，
                    通常用来做图片大小变换、图片变灰、设置透明不透明等。
                 */
                BufferedImage bi = f.perform(m.getImg().getImage()); //加滤镜
                applyModification(bi, new ActionPanel(f.getName(), bi));
                m.notifyObservers();
                model.filterThread.remove(this);
            }
        };
        model.filterThread.add(t);
        t.start();

    }


    public static void  applyModification(BufferedImage bi, ActionPanel a) {
        MainModel m = MainModel.getInstance();
        if (m.panelDraw.getTabCount() <= 0) return;
        m.setImg(bi, a);
        m.notifyObservers();
    }

    public static void  applyModification(BufferedImage bi, ActionPanel a, int i) {
        MainModel m = MainModel.getInstance();
        if (m.panelDraw.getTabCount() <= 0) return;
        m.setImg(bi, a);
        m.notifyObservers();
    }

    /*
    比如你创建了一个button，对他添加一个监听器，
    在这个监听器当中就有一个actionPerformed方法。
    如果你要求这个button做一些事情，
    你就可以在actionPerformed方法中写你要做的事情
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        return;
    }


}
