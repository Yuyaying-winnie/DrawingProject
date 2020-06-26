package GUI.view.layout;

import GUI.controller.MainController;
import GUI.model.HistoricModel;
import GUI.model.MainModel;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

//布局
@SuppressWarnings("unchecked")
public class MyLayout {

    //生成布局
    public void generateLayout(MainController controller) {
        MainModel model = MainModel.getInstance();
        model.mainPanel.setLayout(new BorderLayout());

        JTabbedPane panelDraw = model.panelDraw;  //选项卡面板
        panelDraw.setPreferredSize(new Dimension(600, 600));
        model.mainPanel.add(panelDraw, BorderLayout.CENTER);

        /****** MENU *****/
        MyMenu panelTop = new MyMenu(model.mainPanel, panelDraw);
        panelTop.generateMenus(controller);
        panelTop.setBackground(new Color(255, 255, 255));
        panelTop.setPreferredSize(new Dimension(0, 25));
        model.mainPanel.add(panelTop, BorderLayout.PAGE_START);

        /**** PANEL ****/
        JPanel panelStatus = new JPanel();
        panelStatus.setPreferredSize(new Dimension(0, 25));
        panelStatus.setBackground(new Color(255, 255, 255));

        panelStatus.add(model.statusBar);

        model.mainPanel.add(panelStatus, BorderLayout.PAGE_END);


        /** TOOLBAR **/
        MyToolbar toolbar = new MyToolbar(model.mainPanel);
        toolbar.generateButtons();
        toolbar.setPreferredSize(new Dimension(150, 0));
        toolbar.setBackground(new Color(187, 214, 211, 255));

        model.mainPanel.add(toolbar, BorderLayout.WEST);
        return;
    }
    //返回主容器
    public JPanel getContent() {
        return MainModel.getInstance().mainPanel;
    }

    public String getName() {
        return "Little Painter";
    }
}
