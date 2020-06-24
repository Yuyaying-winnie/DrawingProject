package main;

import GUI.controller.MainController;
import GUI.model.MainModel;
import GUI.view.MainView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static void main(String[] args) {

        MainView view = new MainView(); //顶层框架 继承JFrame
        MainController controller = new MainController(view);
        MainModel.getInstance().setMainView(view);  //单例模式
        view.addActionsListeners(controller); //给controller一个监听者,事件发生传给它
        final JPanel panel = view.getContent();
        final String title = "treibe_a";

        //事件分发线程
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(title);
            frame.setContentPane(panel);
            frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    if(MainModel.getInstance().panelDraw.getTabCount() > 0 && MainModel.getInstance().getImg().modify == 1) {
                        int exit = JOptionPane.showConfirmDialog(frame, "Do you really want to quit ?");
                        if (exit == JOptionPane.YES_OPTION) {
                            System.exit(0);
                        }
                        return;
                    }
                    System.exit(0);

                }
            });
            frame.setVisible(true);
            frame.pack();
        });
    }
}
