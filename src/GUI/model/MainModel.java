package GUI.model;

import GUI.controller.historic.ActionPanel;
import GUI.controller.panel.ImagePanel;
import GUI.view.MainView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Vector;

@SuppressWarnings("unchecked")
/**
 * 单例模式
 */

public class MainModel extends Observable {

    public JPanel mainPanel;
    public JTabbedPane panelDraw;
    public JLabel statusBar;
    public ArrayList<Thread> filterThread = new ArrayList<Thread>();
    public MainView mainView;

    private MainModel() {
        this.mainPanel = new JPanel();
        this.panelDraw = new JTabbedPane();
        this.statusBar = new JLabel("Welcome to Little Painter :)");
        addListeners();
    }

    private static MainModel INSTANCE = new MainModel();

    public static MainModel getInstance() {
        return INSTANCE;
    }

    /**
     * @return the current image
     */
    public ImagePanel getImg() {
        return (ImagePanel) ((JPanel) this.panelDraw.getSelectedComponent()).getComponents()[0];
    }

    /**
     * 设置当前面板的图像并添加至历史记录
     * @param img image of the panel
     * @param action the action applied
     */
    public void setImg(BufferedImage img, ActionPanel action) {
        if (HistoricModel.getInstance().getHistoric().isEmpty()) return;
        if (this.panelDraw.getTabCount() <= 0) return;
        HistoricModel.getInstance().getHistoric().add(action);//给上一个历史图片添加动作
        setPrivateImg(img);
    }


    /**
     * 设置当前面板的图像，但不加入历史记录中
     * @param img
     */
    public void setPrivateImg(BufferedImage img) {
        if (HistoricModel.getInstance().getHistoric().isEmpty()) return;
        if (this.panelDraw.getTabCount() <= 0) return;
        getImg().setImage(img);
        notifyObservers();
        setChanged();
        statusBar.setText(HistoricModel.getInstance().getHistoric().getLastHistoricName());
        HistoricModel.getInstance().setHistoric();
    }

    /**
     * Add the listeners
     */
    private void addListeners() {
        panelDraw.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (panelDraw.getTabCount() <= 0) {
                    statusBar.setText("");
                    HistoricModel.getInstance().historicList.setListData(new Vector());
                    return;
                }
                statusBar.setText(HistoricModel.getInstance().getHistoric().getLastHistoricName());
                HistoricModel.getInstance().setHistoric();
            }
        });


    }


    public void setStatusBar(String text) {
        this.statusBar.setText(text);
    }

    public String getStatusBar() {
        return this.statusBar.getText();
    }
    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

}