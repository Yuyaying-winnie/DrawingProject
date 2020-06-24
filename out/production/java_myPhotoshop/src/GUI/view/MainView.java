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
    //得到主容器
    public JPanel getContent() {
        return this.layout.getContent();
    }
    //得到名字
    public String getTitle() {
        return this.layout.getName();
    }


    //重写Observer父类方法，目标对象状态一旦发生改变，便会执行此方法
    @Override
    public void update(Observable o, Object arg) {
        MainModel model = MainModel.getInstance();
        /*
              repaint()通过调用线程再由线程去调用update()方法清除当前显示
              并再调用paint()方法进行绘制下一个需要显示的内容．
              这样就起到了一种图片的交替显示从而在视角上形成了动画

         */
        model.panelDraw.repaint();
        if(model.panelDraw.getTabCount() <= 0) return;
        model.getImg().repaint();
        model.panelDraw.getSelectedComponent().repaint();
    }
}
