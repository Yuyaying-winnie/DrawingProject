package GUI.view.layout;

import GUI.model.DrawModel;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;


public class MyToolbar extends JPanel {
    private JPanel mainPanel;
    private JPanel drawPanel = new JPanel();
    private JPanel erasePanel = new JPanel();
    private JPanel textPanel = new JPanel();
    private JPanel polygonPanel = new JPanel();
    private JPanel bucketPanel = new JPanel();


    MyToolbar(JPanel mainPanel) {
        this.mainPanel = mainPanel;


        drawPanel.setPreferredSize(new Dimension(200, 600));
        erasePanel.setPreferredSize(new Dimension(200, 600));
        textPanel.setPreferredSize(new Dimension(200, 600));
        polygonPanel.setPreferredSize(new Dimension(200, 600));
        bucketPanel.setPreferredSize(new Dimension(200, 600));

        erasePanel.setBorder(new EtchedBorder());
        drawPanel.setBorder(new EtchedBorder());
        textPanel.setBorder(new EtchedBorder());
        polygonPanel.setBorder(new EtchedBorder());
        bucketPanel.setBorder(new EtchedBorder());


        drawPanel.setVisible(false);
        erasePanel.setVisible(false);
        textPanel.setVisible(false);
        polygonPanel.setVisible(false);
        bucketPanel.setVisible(false);

    }


    public void generateButtons() {
        setBounds(60, 400, 220, 30);

        //DRAW BUTTON
        JButton drawbutton = new JButton();
        drawbutton.setIcon(new ImageIcon("asset/brush.png"));
        drawbutton.addActionListener(e -> {
            reset();

            DrawModel.getInstance().setType("draw");

            drawPanel.setVisible(true);

        });
        add(drawbutton);

        //ERASE BUTTON

        drawbutton = new JButton();
        drawbutton.setIcon(new ImageIcon("asset/eraser.png"));
        drawbutton.addActionListener(e -> {
            reset();

            DrawModel.getInstance().setType("erase");
            erasePanel.setVisible(true);
        });
        add(drawbutton);

        //POLYGON MENU
        drawbutton = new JButton();
        drawbutton.setIcon(new ImageIcon("asset/polygon.png"));
        drawbutton.addActionListener(e -> {
            reset();

            DrawModel.getInstance().setType("polygon");
            polygonPanel.setVisible(true);
        });
        add(drawbutton);

        //BUCKET MENU
        drawbutton = new JButton();
        drawbutton.setIcon(new ImageIcon("asset/bucket.png"));
        drawbutton.addActionListener(e -> {
            reset();

            DrawModel.getInstance().setType("bucket");
            bucketPanel.setVisible(true);
        });
        add(drawbutton);


        setVisible(true);
        generateDrawMenu();
        generateEraseMenu();
        generateTextMenu();
        generatePolygonsMenu();
        generateBucketMenu();
    }

    /** 画笔 **/
    public void generateDrawMenu() {
        JPanel jPanel = new JPanel();
        JLabel color = new JLabel("Your color :");
        color.setIcon(new ImageIcon("asset/color.png"));

        jPanel.add(color);
        jPanel.setPreferredSize(new Dimension(150, 25));


        drawPanel.add(jPanel);
        drawPanel.add(getColorChooser(jPanel));
        drawPanel.add(getSizeChooser(drawPanel));
        String label[] = {"Pencil", "Crayon", "InkBrush"};
        /** 笔形 **/
         drawPanel.add(getShapeChooser(drawPanel, label));
         

        add(drawPanel);

    }

    /** 橡皮擦 **/
    public void generateEraseMenu() {
        erasePanel.add(getSizeChooser(erasePanel));
        erasePanel.add(getOpacityChooser(erasePanel));
        add(erasePanel);

    }

    public void generateTextMenu() {

        JPanel jPanel = new JPanel();
        JLabel color = new JLabel("Your color :");

        jPanel.add(color);
        jPanel.setPreferredSize(new Dimension(190, 25));

        textPanel.add(jPanel);
        textPanel.add(getColorChooser(textPanel));
        textPanel.add(getSizeChooser(textPanel));

        add(textPanel);

    }
     /** 修改，选择画圆、矩形、直线 **/
    public void generatePolygonsMenu() {

        JPanel jPanel = new JPanel();
        JLabel color = new JLabel("Your color :");
        color.setIcon(new ImageIcon("asset/color.png"));

        jPanel.add(color);
        jPanel.setPreferredSize(new Dimension(190, 25));

        polygonPanel.add(jPanel);
        polygonPanel.add(getColorChooser(jPanel));
        polygonPanel.add(getRadiusChooser(polygonPanel));
        String label[] = {"Line", "Rectangle", "Circle"};

        polygonPanel.add(getdrawPolygonChooser(polygonPanel, label));
        polygonPanel.add(getNumberVertices(polygonPanel));

        add(polygonPanel);

    }

    public void generateBucketMenu() {

        JPanel jPanel = new JPanel();
        JLabel color = new JLabel("Your color :");
        color.setIcon(new ImageIcon("asset/color.png"));

        jPanel.add(color);
        jPanel.setPreferredSize(new Dimension(190, 25));

        bucketPanel.add(jPanel);
        bucketPanel.add(getColorChooser(jPanel));

        add(bucketPanel);

    }

    public JSlider getSizeChooser(JPanel j) {
        JLabel jLabel = new JLabel("Size :");
        jLabel.setIcon(new ImageIcon("asset/size.png"));
        j.add(jLabel);
        JSlider sizeChooser = new JSlider(JSlider.HORIZONTAL, 0, 50, 25);//滑块
        sizeChooser.setPreferredSize(new Dimension(150,50));
        sizeChooser.setMajorTickSpacing(25);
        sizeChooser.setMinorTickSpacing(1);
        sizeChooser.setPaintTicks(true);
        sizeChooser.setPaintLabels(true);
        sizeChooser.addChangeListener(e -> {
            DrawModel.getInstance().setSize(sizeChooser.getValue());
        });

        return sizeChooser;
    }

    public JSlider getNumberVertices(JPanel j) {
        JLabel jLabel = new JLabel("Vertices :");
        jLabel.setIcon(new ImageIcon("asset/size.png"));
        j.add(jLabel);
        JSlider sizeChooser = new JSlider(JSlider.HORIZONTAL, 5, 15, 7);
        sizeChooser.setPreferredSize(new Dimension(150,50));
        sizeChooser.setMajorTickSpacing(5);
        sizeChooser.setMinorTickSpacing(1);
        sizeChooser.setPaintTicks(true);
        sizeChooser.setPaintLabels(true);
        sizeChooser.addChangeListener(e -> {
            DrawModel.getInstance().setNbshape(sizeChooser.getValue());
            DrawModel.getInstance().resetPoint();
        });

        return sizeChooser;
    }

    public JSlider getRadiusChooser(JPanel j) {
        JLabel jLabel = new JLabel("Radius :");
        jLabel.setIcon(new ImageIcon("asset/rounded.png"));
        j.add(jLabel);
        JSlider sizeChooser = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        sizeChooser.setPreferredSize(new Dimension(150,50));
        sizeChooser.setMajorTickSpacing(25);
        sizeChooser.setMinorTickSpacing(1);
        sizeChooser.setPaintTicks(true);
        sizeChooser.setPaintLabels(true);
        sizeChooser.addChangeListener(e -> {
            DrawModel.getInstance().setRadius(sizeChooser.getValue());
        });

        return sizeChooser;
    }

    /** 改变笔形 **/
    public JList getShapeChooser(JPanel j, String label[]) {
        JLabel jLabel = new JLabel("Shape :");
        jLabel.setIcon(new ImageIcon("asset/shape.png"));
        j.add(jLabel);
        JList list = new JList<>(label);

          DefaultListSelectionModel sm = new DefaultListSelectionModel();
          sm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //一次只能选择一项
          list.setSelectionModel(sm);
          list.addListSelectionListener(e -> DrawModel.getInstance().setShape((String)
          
        		  list.getModel().getElementAt(list.getSelectedIndex())));   //add

        return list;
    }

    public JList getdrawPolygonChooser(JPanel j, String label[]) {
        JLabel jLabel = new JLabel("draw :");
        jLabel.setIcon(new ImageIcon("asset/shape.png"));
        j.add(jLabel);
        JList list = new JList<>(label);

          DefaultListSelectionModel sm = new DefaultListSelectionModel();
          sm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //一次只能选择一项
          list.setSelectionModel(sm);
          list.addListSelectionListener(e -> DrawModel.getInstance().setShape((String)
          list.getModel().getElementAt(list.getSelectedIndex())));   //add

        return list;
    }
    
    public JButton getColorChooser(JPanel jPanel) {

        JButton drawbutton = new JButton();
        drawbutton.setText("Choose your color");
        drawbutton.addActionListener(e -> {
                    DrawModel.getInstance().setColor(JColorChooser.showDialog(null, "Choose your color", Color.WHITE));
                    //remove transparency from colorchooser
                    jPanel.setBackground(new Color(DrawModel.getInstance().getColor().getRGB() | 0xFF000000));
                }
        );
        drawbutton.setPreferredSize(new Dimension(150, 25));

        return drawbutton;
    }

    public JSlider getOpacityChooser(JPanel j) {
        JSlider opacity = new JSlider(JSlider.HORIZONTAL, 1, 100, 50);
        JLabel jLabel = new JLabel("Opacity :");
        jLabel.setIcon(new ImageIcon("asset/opacity.png"));
        j.add(jLabel);

        opacity.setPreferredSize(new Dimension(150,50));
        opacity.setMajorTickSpacing(99);
        opacity.setMinorTickSpacing(25);
        opacity.setPaintTicks(true);
        opacity.setPaintLabels(true);
        opacity.addChangeListener(e -> {
            DrawModel.getInstance().setOpacity(opacity.getValue());
        });
        return opacity;
    }

    public void reset() {
        DrawModel.getInstance().reset();
        drawPanel.removeAll();
        erasePanel.removeAll();
        polygonPanel.removeAll();
        textPanel.removeAll();
        bucketPanel.removeAll();

        generateDrawMenu();
        generateEraseMenu();
        generateTextMenu();
        generatePolygonsMenu();
        generateBucketMenu();


        drawPanel.setVisible(false);
        erasePanel.setVisible(false);
        polygonPanel.setVisible(false);
        textPanel.setVisible(false);
        bucketPanel.setVisible(false);
    }

}
