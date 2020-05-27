import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * @description: 主窗体
 * @author: 宇智波Akali
 * @time: 2020/5/13 20:58
 * @fromProject: All_Done
 * @Version: V1.0
 */
public class MainFrame extends JFrame{
    public MainFrame(){
        try {
            Thread.sleep(2000);     //等欢迎界面结束在启动主窗体
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        init();
    }
    public void init(){
        JFrame frame = this;  //将默认的窗口赋值用来传参
//        frame.setUndecorated(true); // 去掉窗口的标题栏，可以设置一个功能

//        this.getContentPane().setBackground(new Color(102, 143, 215));
        this.setTitle("All Done");
        Image icon = Toolkit.getDefaultToolkit().getImage("src/assets/Icon.png");
        this.setIconImage(icon);
        this.setBounds(228,90,1050,700);
        this.setLayout(null);
        this.setDefaultCloseOperation(3);
        toTray(false,frame);        //传参确定是否最小化到系统托盘

//        //分割面板//取消分割面板，没啥大用处，还丑
//        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
//        splitPane.setDividerLocation(270);
//
//        splitPane.setDividerSize(1);
//        splitPane.repaint();
//        splitPane.setContinuousLayout(true);

        //添加分面板,面板初始化
        JPanel panel01 = new JPanel();
        JPanel panel02 = new JPanel();

        panel01.setBounds(0,0,270,700);
        panel02.setBounds(270,0,780,700);
        panel01.setBackground(new Color(255, 255, 255));        //面板颜色

        Color theme_color = new Color(102, 143, 215);            //设置主题色:蓝色
//        Color theme_color = new Color(188, 149, 215);            //设置主题色:紫色
        panel02.setBackground(theme_color);                              //主题色
        panel02.setLayout(null);                                    //这里不设为null则后面的子组件不能自定义位置

        //右侧面板启动时的显示内容
        {
            JLabel label_init = new JLabel("Have a nice day!");
            label_init.setForeground(Color.white);
            label_init.setFont(new Font("微软雅黑", Font.BOLD, 60));
            label_init.setBounds(150,140,600,200);
            panel02.add(label_init);

            Date date_tmp = new Date();
            String year = String.format("%tY", date_tmp);
            String month = String.format("%tB", date_tmp);
            String day = String.format("%te", date_tmp);
            String date = year + "年 "+month+" " +day+"日";             //有个问题就是不能实时刷新日期，但是对于只显示一次的页面问题不大

            JLabel label_date = new JLabel(date);
            label_date.setForeground(Color.white);
            label_date.setFont(new Font("微软雅黑", Font.ITALIC, 16));
            label_date.setBounds(490,250,300,100);
            panel02.add(label_date);
        }

        //面板设置
        new Panel_Selector(panel01, panel02, theme_color);

//        splitPane.setRightComponent(panel02);
//        splitPane.setLeftComponent(panel01);
//        frame.getContentPane().add(splitPane);
//        splitPane.setDividerSize(1);
//        splitPane.setVisible(true);
        this.add(panel01);
        this.add(panel02);
        this.setResizable(false);
        this.setVisible(true);
    }

    /*************************是否启动最小化到系统托盘*****************************/
    /*
     * @Description:
     * @author: 宇智波Akali
     * @date: 0:28 2020/5/15
     * @param: [t, frame]
     * @return: void
     */
    private static void toTray(Boolean t, JFrame frame) {
        if (t == true){
            new Tray(true,frame);
        }
    }
}
