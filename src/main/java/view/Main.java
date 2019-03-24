package view;

import bean.Point2d;
import bean.Point3d;
import bean.ResultParameters4;
import bean.ResultParameters7;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import factory.DataFormFactory;
import singleton.Choice;
import singleton.Data;
import tools.PramSCals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main {
    private JButton button1;
    private JButton button2;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JScrollPane scrollPane1;
    private JScrollPane scrollPane2;
    private JLabel label1;
    private JButton FlashButton;

    private JTable jTable1;
    private JTable jTable2;
    private MyTableModel myTableModel1;
    private MyTableModel myTableModel2;

    JMenuBar jMenuBar = new JMenuBar();
    private JMenuItem openMenuItem;
    private JMenuItem exportMenuItem;
    private JMenuItem openConfigItem;
    private JMenuItem exitMenuItem;
    private JMenu fileMenu = new JMenu("文件");
    private JMenu aboutMenu = new JMenu("关于");


    public Main() {
        $$$setupUI$$$();

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showImportDialog();
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (DataFormFactory.isTrans(Choice.getInstance().getMethod()))
                    showOutputDialog();
                else {
                    showPramSCalsDialog();
                }
            }
        });
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showImportDialog();
            }
        });
        exportMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showOutputDialog();
            }
        });
        openConfigItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Runtime.getRuntime().exec("cmd.exe /c notepad.exe config.ini");
                } catch (Exception ee) {
                    ee.printStackTrace();
                    System.out.println("无法打开配置文件");
                }
            }
        });
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        FlashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Data.getInstance().setOriginalData(myTableModel1.getData());
                updateTable();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Win_BLHtoXYZ");
        Main win = new Main();
        frame.setContentPane(new Main().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        int windowWidth = frame.getWidth(); //获得窗口宽
        //System.out.println(windowWidth);
        int windowHeight = frame.getHeight(); //获得窗口高
        //System.out.println(windowHeight);
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示
        frame.setJMenuBar(win.jMenuBar);
        frame.setVisible(true);
    }

    private void showPramSCalsDialog() {
        System.out.println(1111111);
        if (Choice.getInstance().getMethod().equals("求四参数")) {

            ResultParameters4 resultParameters4 = Data.getInstance().getResultParameters4();
            String message = "计算结果\n"
                    + "rota: " + resultParameters4.rota + "\n"
                    + "scale: " + resultParameters4.scale + "\n"
                    + "dx: " + resultParameters4.dx + "\n"
                    + "dy: " + resultParameters4.dy + "\n";

            JOptionPane.showMessageDialog(panel1, message, " 计算结果", JOptionPane.PLAIN_MESSAGE);
        } else if (Choice.getInstance().getMethod().equals("求七参数")) {

            ResultParameters7 resultParameters7 = Data.getInstance().getResultParameters7();
            String message = "计算结果\n"
                    + "rotax: " + resultParameters7.rotax + "\n"
                    + "rotay: " + resultParameters7.rotay + "\n"
                    + "rotaz: " + resultParameters7.rotaz + "\n"
                    + "scale: " + resultParameters7.scale + "\n"
                    + "dx: " + resultParameters7.dx + "\n"
                    + "dy: " + resultParameters7.dy + "\n"
                    + "dz: " + resultParameters7.dz + "\n";

            JOptionPane.showMessageDialog(panel1, message, " 计算结果", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void showOutputDialog() {
        OutputDialog dialog = new OutputDialog();
        dialog.setLocationRelativeTo(null);
        dialog.pack();
        int windowWidth = dialog.getWidth(); //获得窗口宽
        //System.out.println(windowWidth);
        int windowHeight = dialog.getHeight(); //获得窗口高
        //System.out.println(windowHeight);
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        dialog.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示
        dialog.setVisible(true);
    }

    private void showImportDialog() {
        ImportDialog dialog = new ImportDialog(this);
        dialog.setTitle("导入操作设置");
        dialog.setLocationRelativeTo(null);
        dialog.pack();
        int windowWidth = dialog.getWidth(); //获得窗口宽
        //System.out.println(windowWidth);
        int windowHeight = dialog.getHeight(); //获得窗口高
        //System.out.println(windowHeight);
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        dialog.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示
        dialog.setVisible(true);
    }

    private void initTable() {
        Choice.getInstance().init();
        jTable1 = new JTable();
        jTable2 = new JTable();
        myTableModel1 = new MyTableModel();
        myTableModel2 = new MyTableModel();
        myTableModel1.setData(Data.getInstance().getOriginalData());
        myTableModel1.setColumnNames(Data.getInstance().getTableHeader1());
        myTableModel2.setData(Data.getInstance().getTranData());
        myTableModel2.setColumnNames(Data.getInstance().getTableHeader2());
        jTable1.setModel(myTableModel1);
        jTable2.setModel(myTableModel2);
        scrollPane1.setViewportView(jTable1);
        scrollPane2.setViewportView(jTable2);
    }

    public void updateTable() {
        //System.out.println("updata");
        System.out.println("原始数据行列：" + Data.getInstance().getOriginalData().length + "/" + Data.getInstance().getOriginalData()[0].length);
        System.out.println("转换数据行列：" + Data.getInstance().getTranData().length + "/" + Data.getInstance().getTranData()[0].length);
        if (!DataFormFactory.isTrans(Choice.getInstance().getMethod()))
            button2.setText("计算并显示结果");
        label1.setText("转换方法：" + Choice.getInstance().getMethod());
        myTableModel1.setData(Data.getInstance().getOriginalData());
        myTableModel1.setColumnNames(Data.getInstance().getTableHeader1());
        myTableModel1.fireTableStructureChanged();
        myTableModel2.setData(Data.getInstance().getTranData());
        myTableModel2.setColumnNames(Data.getInstance().getTableHeader2());
        myTableModel2.fireTableStructureChanged();
        jTable1.setModel(myTableModel1);
        jTable2.setModel(myTableModel2);
        scrollPane1.setViewportView(jTable1);
        scrollPane2.setViewportView(jTable2);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        scrollPane1 = new JScrollPane();
        scrollPane2 = new JScrollPane();
        label1 = new JLabel("");
        initTable();

        //创建三个菜单项
        openMenuItem = new JMenuItem("导入");
        exportMenuItem = new JMenuItem("导出");
        openConfigItem = new JMenuItem("打开坐标系配置文件");
        exitMenuItem = new JMenuItem("退出");

        //把菜单项加到菜单上
        fileMenu.add(openMenuItem);
        fileMenu.add(exportMenuItem);
        fileMenu.add(openConfigItem);
        fileMenu.addSeparator();       // 添加一条分割线
        fileMenu.add(exitMenuItem);

        //把菜单加到菜单条上
        jMenuBar.add(fileMenu);
        jMenuBar.add(aboutMenu);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel1.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        button1 = new JButton();
        button1.setText("导入");
        panel2.add(button1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        FlashButton = new JButton();
        FlashButton.setText("刷新");
        panel2.add(FlashButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button2 = new JButton();
        button2.setText("导出");
        panel2.add(button2, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel5, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("  ");
        panel5.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel1.add(scrollPane1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(500, 800), new Dimension(500, 800), new Dimension(500, 800), 0, false));
        panel1.add(scrollPane2, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(500, 800), new Dimension(500, 800), new Dimension(500, 800), 0, false));
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("原始数据");
        panel3.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel4, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("转换数据");
        panel4.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel6, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        label1.setText("");
        panel6.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
