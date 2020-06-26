package GUI.model;

import GUI.controller.historic.HistoricController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

//历史记录
public class HistoricModel {

    private ArrayList<HistoricController> historic;
    public JList historicList;  //存放历史数据的列表
    private static HistoricModel INSTANCE = new HistoricModel(); //单例模式

    public static HistoricModel getInstance() {
        return INSTANCE;
    }

    private HistoricModel() {
        this.historic = new ArrayList<>();
        this.historicList = new JList<>();


        historicList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (historicList.getSelectedIndex() > getHistoric().getCurrentId()) {
                    redo(historicList.getSelectedIndex());
                } else if (historicList.getSelectedIndex() < getHistoric().getCurrentId())
                    undo(historicList.getSelectedIndex());
                if (getHistoric() != null) {
                    historicList.setSelectedIndex(getHistoric().getCurrentId());
                    MainModel.getInstance().getImg().repaint();
                }
            }
        });


        historicList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    Thread t = new Thread() {
                        public void run() {
                            historicList.repaint();
                        }
                    };
                    t.run();
                }
            }
        });
    }

    //GETTER AND SETTER

    public void deleteHistoric() {
        if (historic.size() <= 0) return;
        historic.remove(MainModel.getInstance().panelDraw.getSelectedIndex());
    }

    public void deleteAllHistoric() {
        historic = new ArrayList<HistoricController>();
    }

    public void setHistoric() {
        Vector actions = getHistoric().getActionsNames();
        historicList.setListData(actions);
        historicList.setSelectedIndex(getHistoric().getCurrentId());
    }

    //返回选中的历史图片
    public HistoricController getHistoric() {
        MainModel model = MainModel.getInstance();
        for (HistoricController historicController : historic) {
            if (historicController.id == model.panelDraw.getSelectedIndex()) {
                return historicController;
            }
        }
        historic.add(new HistoricController(model.panelDraw.getSelectedIndex(), model.getImg().getImage()));
        return historic.get(historic.size() - 1);
    }
    //添加图片到历史容器列表中
    public void setHistoric(HistoricController _historic) {
        historic.add(_historic);
        _historic.setId(historic.size() - 1);
    }

    //设置输入消息对话框，输入历史记录的长度
    public void setSize() {
        try {
            int size = Integer.parseInt(JOptionPane.showInputDialog(MainModel.getInstance().mainPanel,
                    "size of the history"));
            if (size <= 0) {
                JOptionPane.showMessageDialog(MainModel.getInstance().mainPanel, "Your input is incorrect");
            }
            getHistoric().setSize(size);
            MainModel.getInstance().getImg().repaint();

            setHistoric();
            historicList.repaint();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(MainModel.getInstance().mainPanel, "Your input is incorrect");
        }

    }

    //UNDO / REDO
    public void undo() {
        if (MainModel.getInstance().panelDraw.getTabCount() <= 0) return;

        if (getHistoric().isEmpty()) return;
        getHistoric().undo();
        MainModel.getInstance().setPrivateImg(getHistoric().getHistoricImage());

        if (!getHistoric().isLast())
            MainModel.getInstance().setStatusBar("Undo to " + MainModel.getInstance().getStatusBar());
    }

    public void redo() {
        if (MainModel.getInstance().panelDraw.getTabCount() <= 0) return;
        getHistoric().redo();
        MainModel.getInstance().setPrivateImg(getHistoric().getHistoricImage());

        if (!getHistoric().isLast())
            MainModel.getInstance().setStatusBar("Redo to " + MainModel.getInstance().getStatusBar());
    }


    private void undo(int i) {
        MainModel model = MainModel.getInstance();
        if (model.panelDraw.getTabCount() <= 0) return;
        if (getHistoric().isEmpty()) return;
        int j = 0;
        i = getHistoric().getCurrentId() - i;
        while (j < i) {
            getHistoric().undo();
            j++;
        }
        model.setPrivateImg(getHistoric().getHistoricImage());

        if (!getHistoric().isLast())
            model.setStatusBar("Undo to " + model.getStatusBar());
    }

    private void redo(int i) {
        MainModel model = MainModel.getInstance();
        if (model.panelDraw.getTabCount() <= 0) return;
        if (getHistoric().isEmpty()) return;
        int j = 0;
        i = i - getHistoric().getCurrentId();
        while (j < i) {
            getHistoric().redo();
            j++;
        }
        model.setPrivateImg(getHistoric().getHistoricImage());
        if (!getHistoric().isLast())
            model.setStatusBar("Redo to " + model.getStatusBar());
    }
}
