package GUI.view.layout;

import GUI.controller.MainController;
import GUI.model.HistoricModel;
import GUI.model.MainModel;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

@SuppressWarnings("unchecked")
public class MyLayout {

    //生成布局
    public void generateLayout(MainController controller) {
        MainModel model = MainModel.getInstance();
        /*
        BorderLayout，边界布局管理器。
        它把 Container 按方位分为 5 个区域（东、西、南、北、中），每个区域放置一个组件。
         */
        model.mainPanel.setLayout(new BorderLayout());

        JTabbedPane panelDraw = model.panelDraw;  //选项卡面板
        panelDraw.setPreferredSize(new Dimension(1000, 600));
        panelDraw.setBackground(new Color(255, 255, 255));
        model.mainPanel.add(panelDraw, BorderLayout.CENTER);

        /****** MENU *****/
        MyMenu panelTop = new MyMenu(model.mainPanel, panelDraw);
        panelTop.generateMenus(controller);

        /**** PANEL ****/

        panelTop.setBackground(new Color(110, 110, 110));
        panelTop.setPreferredSize(new Dimension(0, 25));
        model.mainPanel.add(panelTop, BorderLayout.PAGE_START);

        JPanel panelStatus = new JPanel();
        panelStatus.setPreferredSize(new Dimension(0, 25));
        panelStatus.setBackground(new Color(110, 110, 110));

        panelStatus.add(model.statusBar);

        model.mainPanel.add(panelStatus, BorderLayout.PAGE_END);

        /**** HISTORIC ****/

        JList panelHistoric = HistoricModel.getInstance().historicList;
        panelHistoric.setSize(new Dimension(200,0));
        panelHistoric.setBackground(new Color(150, 150, 150));
        panelHistoric.setSelectionBackground(Color.LIGHT_GRAY);
        panelHistoric.setCellRenderer(new MyHistoricRender());
        JScrollPane spane = new JScrollPane(panelHistoric);  //滚动面板
        spane.getVerticalScrollBar().setMaximumSize(model.mainPanel.getSize());
        spane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        model.mainPanel.add(spane, BorderLayout.EAST);


        /** TOOLBAR **/
        MyToolbar toolbar = new MyToolbar(model.mainPanel);
        toolbar.generateButtons();
        toolbar.setPreferredSize(new Dimension(200, 0));
        toolbar.setBackground(new Color(200, 200, 200));

        model.mainPanel.add(toolbar, BorderLayout.WEST);
        return;
    }
    //返回主容器
    public JPanel getContent() {
        return MainModel.getInstance().mainPanel;
    }

    public String getName() {
        return "DRAWING";
    }
}
