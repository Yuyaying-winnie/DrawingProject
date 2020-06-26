package GUI.controller;


import GUI.controller.historic.ActionPanel;
import GUI.controller.menus.MenuController;
import GUI.model.MainModel;
import GUI.view.MainView;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MainController implements ActionListener {
    public MainView imgv;
    public MainModel model = MainModel.getInstance();
    public MenuController menuController;

    public MainController(MainView imgv) {
        this.menuController = new MenuController();
        this.imgv = imgv;
    }


    public static void  applyModification(BufferedImage bi, ActionPanel a) {
        MainModel m = MainModel.getInstance();
        if (m.panelDraw.getTabCount() <= 0) return;
        m.setImg(bi, a);
        m.notifyObservers();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        return;
    }


}
