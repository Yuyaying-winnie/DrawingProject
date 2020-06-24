package GUI.view.layout;

import GUI.controller.MainController;
import GUI.model.HistoricModel;
import GUI.model.MainModel;
import filter.Filter;

import javax.swing.*;
import java.awt.event.KeyEvent;

//我的标题栏
public class MyMenu extends JMenuBar {
    public JPanel mainPanel;
    public JTabbedPane panelDraw;
    public JFileChooser fc; //文件选择器

    MyMenu(JPanel mainPanel, JTabbedPane panelDraw) {
        this.mainPanel = mainPanel;
        this.panelDraw = panelDraw;
        this.fc = new JFileChooser();
    }
    //生成一系列菜单
    public void generateMenus(MainController controller) {
        generateFileMenu(controller);
        generateEditMenu(controller);
        generateFiltersMenu(controller);
    }

    private void generateFileMenu(MainController controller) {

        /****** FILE MENU *****/
        JMenu file = new JMenu(); //菜单名

        file.setText("File");
        file.setIcon(new ImageIcon("asset/file.png"));

        this.add(file);

        JMenuItem mi = new JMenuItem();//菜单项
        /***创建项目***/
        mi.setText("Create");
        mi.setIcon(new ImageIcon("asset/new.png"));
        file.add(mi);
        //快捷键
        mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));

        //创建项目
        mi.addActionListener(e -> controller.menuController.createProject());

        /***打开项目***/
        mi = new JMenuItem();
        mi.setText("Open");
        mi.setIcon(new ImageIcon("asset/open.png"));

        file.add(mi);
        fc = new JFileChooser("~");
        mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));

        mi.addActionListener(e -> controller.menuController.performOpen());

        /***从剪贴板打开***/
        mi = new JMenuItem();
        mi.setText("Open from clipboard");
        mi.setIcon(new ImageIcon("asset/clipboard.png"));

        file.add(mi);
        mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
        mi.addActionListener(e -> controller.menuController.performOpenFromClipboard());

        /***保存***/
        mi = new JMenuItem();
        mi.setText("Save");
        mi.setIcon(new ImageIcon("asset/save.png"));
        file.add(mi);
        mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));

        mi.addActionListener(e -> controller.menuController.performSave());

        /***另存为***/
        mi = new JMenuItem();
        mi.setText("Save As..");
        mi.setIcon(new ImageIcon("asset/saveas.png"));
        file.add(mi);
        mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK + KeyEvent.SHIFT_DOWN_MASK));
        mi.addActionListener(e -> controller.menuController.performSaveAs());

        /***导出***/
        JMenu exportMenu = new JMenu();
        exportMenu.setText("Export as");
        exportMenu.setIcon(new ImageIcon("asset/export.png"));
        mi = new JMenuItem();
        mi.setText("jpg");
        mi.addActionListener(e -> controller.menuController.performExportAs("jpg"));
        exportMenu.add(mi);

        mi = new JMenuItem();
        mi.setText("png");
        mi.addActionListener(e -> controller.menuController.performExportAs("png"));
        exportMenu.add(mi);

        mi = new JMenuItem();
        mi.setText("bmp");
        mi.addActionListener(e -> controller.menuController.performExportAs("bmp"));
        exportMenu.add(mi);

        file.add(exportMenu);

        mi = new JMenuItem();
        mi.setText("Close");
        mi.setIcon(new ImageIcon("asset/close.png"));
        file.add(mi);
        mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK));
        mi.addActionListener(e -> controller.menuController.performClose());


        mi = new JMenuItem();
        mi.setText("Close All");
        mi.setIcon(new ImageIcon("asset/closeall.png"));
        file.add(mi);
        mi.addActionListener(e -> controller.menuController.performCloseAll());

    }

    private void generateEditMenu(MainController controller) {
        /****** EDIT MENU *****/

        JMenu edit = new JMenu();
        edit.setIcon(new ImageIcon("asset/edit.png"));

        edit.setText("Edit");
        this.add(edit);

        JMenuItem mi;

        mi = new JMenuItem();
        mi.setText("Undo");
        mi.setIcon(new ImageIcon("asset/undo.png"));
        mi.addActionListener(e -> HistoricModel.getInstance().undo());
        mi.setAccelerator((KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK)));
        edit.add(mi);

        mi = new JMenuItem();
        mi.setText("Redo");
        mi.setIcon(new ImageIcon("asset/redo.png"));
        mi.addActionListener(e -> HistoricModel.getInstance().redo());
        mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK + KeyEvent.SHIFT_DOWN_MASK));
        edit.add(mi);

        mi = new JMenuItem();
        mi.setText("Cancel Filter");
        mi.setIcon(new ImageIcon("asset/cancel.png"));
        mi.addActionListener(e -> MainModel.getInstance().cancelFilter());
        edit.add(mi);

        mi = new JMenuItem();
        mi.setText("Change historic size");
        mi.setIcon(new ImageIcon("asset/history.png"));

        mi.addActionListener(e -> HistoricModel.getInstance().setSize());
        edit.add(mi);


    }


    private void generateFiltersMenu(MainController controller) {
        /****** FILTERS MENU *****/


        JMenu filters = new JMenu();
        filters.setText("Filters");
        filters.setIcon(new ImageIcon("asset/filter.png"));

        filters.setMnemonic(KeyEvent.VK_I);
        this.add(filters);

        for (Class aClass : controller.filtersController.getClasses()) {
            JMenuItem mi = new JMenuItem();
            try {
                Filter filter = (Filter)aClass.newInstance();
                mi.setText(filter.getName());
                mi.addActionListener(e -> controller.applyFilter(filter));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            filters.add(mi);
        }

    }
}
