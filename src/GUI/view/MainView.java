package GUI.view;

import GUI.controller.MainController;
import GUI.model.MainModel;
import GUI.view.layout.MyLayout;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

//观察者模式  实现Observer
//主框架
public class MainView extends JFrame implements Observer {
    private MyLayout layout;
    private MainController controller;

    public MainView() {
        this.layout = new MyLayout();
        MainModel.getInstance().addObserver(this);
    }

    public void addActionsListeners(MainController controller) {
        this.controller = controller;
        this.layout.generateLayout(controller);
        MainModel.getInstance().mainPanel = this.layout.getContent();
    }

    public JPanel getContent() {
        return this.layout.getContent();
    }

    public String getTitle() {
        return this.layout.getName();
    }


    //重写Observer父类方法，目标对象状态一旦发生改变，便会执行此方法
    @Override
    public void update(Observable o, Object arg) {
        MainModel model = MainModel.getInstance();
        model.panelDraw.repaint();
        if(model.panelDraw.getTabCount() <= 0) return;
        model.getImg().repaint();
        model.panelDraw.getSelectedComponent().repaint();
    }
}
