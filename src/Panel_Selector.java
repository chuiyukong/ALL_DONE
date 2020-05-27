import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

/**
 * @description:    切换栏类,对按钮面板进行选择
 * @author: 宇智波Akali
 * @time: 2020/5/16 13:07
 * @fromProject: All_Done
 * @Version: V1.0
 */
public class Panel_Selector{
    public static JButton checked_Button  ;         //选中的按钮，相当于一个指针
    public static JPanel checked_Panel;             //选中的面板，也是一个指针
    public static Color theme_color;                //抽取出主题色，加入设置功能

    static JButton button_s = new JButton();       //搜索按钮
    static JButton button1 = new JButton();        //设置
    static JButton button2 = new JButton();        //今日任务
    static JButton button3 = new JButton();        //重要
    static JButton button4 = new JButton();        //所有任务

    public Panel_Selector(JPanel panel01, JPanel panel02, Color theme_color){  //父容器设置了布局，在子容器中的组件就不能用setBounds设置大小
        this.theme_color = theme_color;
        panel01.setLayout(null);
//        theme_color = new Color(102, 143, 215);
        //按钮
//        JButton button_s = new JButton();       //搜索按钮
//        JButton button1 = new JButton();        //设置
//        JButton button2 = new JButton();        //今日任务
//        JButton button3 = new JButton();        //重要
//        JButton button4 = new JButton();        //所有任务

        //创建右侧多面板类
        Panels panels = new Panels(theme_color);

        //按钮代码块01
        {
//            button1.setContentAreaFilled(false);    //取消背景色，可能在监听器中要重新设置，否则设置背景色无效，监听器中设置按钮颜色透明度
            button1.setBackground(new Color(255,255,255));
            button1.setBounds(0,0,220,50);
            button1.setFocusPainted(false);         //取消焦点
            button1.setToolTipText("设置");
            button1.setText("  All Done");
            button1.setFont(new Font("微软雅黑",Font.PLAIN,18));      //字体，监听器中改为粗体，颜色

            //设置按钮图标
            ImageIcon icon_src = new ImageIcon("src/assets/Icon01.png");
            Image icon_tem = icon_src.getImage();
            ImageIcon icon = new ImageIcon(icon_tem);
            button1.setIcon(icon);

            button1.setHorizontalAlignment(SwingConstants.LEFT); //对齐方式
            button1.setBorderPainted(false);
            //监听器
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println("打开设置：这里设置为键盘监听");
                }
            });
        }

        //查找按钮
        {
            button_s.setBounds(220,0,50,50);
//            button_s.setContentAreaFilled(false);
            button_s.setBackground(new Color(255, 255, 255));
            button_s.setFocusPainted(false);
            button_s.setToolTipText("搜索");

            //设置图标
            ImageIcon icon_src = new ImageIcon("src/assets/Component_Icon_01.png");
            Image img = icon_src.getImage();
            Image tmp_img = img.getScaledInstance(20,20,java.awt.Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(tmp_img);
            button_s.setIcon(icon);
//            Image icon_tem = icon_src.getImage();
//            ImageIcon icon = new ImageIcon(icon_tem);
//            button_s.setIcon(icon);
            button_s.setBorderPainted(false);

            //监听器
            button_s.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println("搜索");
                }
            });
        }

        //按钮代码块02
        {
            button2.setBackground(new Color(255, 255, 255));
            button2.setFocusPainted(false);         //取消焦点
            button2.setBounds(0,50,270,50);
            button2.setBorder(BorderFactory.createMatteBorder(0,6,0,0,theme_color)); //边框设置：添加监听器，全局变量，唯一选中
            button2.setToolTipText("今日任务");
            button2.setText("  今日任务                  "+ MyButton.ID_today);
            button2.setFont(new Font("微软雅黑",Font.PLAIN,16));      //字体，监听器中改为粗体
            button2.setHorizontalAlignment(SwingConstants.LEFT); //对齐方式
            //设置按钮图标
            ImageIcon icon_src = new ImageIcon("src/assets/Component_Icon_02.png");
            Image icon_tem = icon_src.getImage();
            ImageIcon icon = new ImageIcon(icon_tem);
            button2.setIcon(icon);

            button2.setBorderPainted(false);

            //监听器
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println("跳转我的一天，设置边框，设置字体加粗，字体颜色，选中背景色");

                    //切换栏选中事件处理
                    {
                        //将上一项的状态恢复，然后将指针后移，进行设置
                        if (checked_Button != null){                    //初始状态可能为空，在这里重置上一个被选按钮的属性
                            checked_Button.setBorderPainted(false);     //将上一个选中的按钮取消选中效果
                            checked_Button.setBackground(new Color(255, 255, 255));//重置背景色
                            checked_Button.setFont(new Font("微软雅黑",Font.PLAIN,16));//重置字体
                            checked_Button.setForeground(Color.black);
                        }
                        checked_Button = button2;                   //指针移动
                        button2.setBorderPainted(true);
                        button2.setBackground(new Color(225, 225, 225));
                        button2.setFont(new Font("微软雅黑",Font.BOLD,18));
                        button2.setForeground(theme_color);        //设置字体颜色
                    }

                    //切换事件处理
                    {
                        JButton button = (JButton) actionEvent.getSource();
                        System.out.println(button.getText());
                        if (checked_Panel != null){
                            checked_Panel.setVisible(false);        //将上一个选中的面板隐藏
                            checked_Panel.setEnabled(false);        //将面板禁用
                        }
                        checked_Panel = panels.panel_1;             //面板指针指向当前面板
                        panels.panel_1.setEnabled(true);            //将选中面板启用，并显示
                        panels.panel_1.setVisible(true);
                        panel02.removeAll();                        //将最开始的文字删除
                        panel02.add(panels.panel_1);
//                        panel02.validate();                         //加载面板
                        panel02.repaint();                          //不进行重绘的话，要点击两下才能显示
                    }

                }
            });

        }

        //按钮3
        {
            button3.setBackground(new Color(255, 255, 255));
            button3.setFocusPainted(false);         //取消焦点
            button3.setBounds(0, 100, 270, 50);
            button3.setBorder(BorderFactory.createMatteBorder(0, 6, 0, 0, theme_color)); //边框设置：添加监听器，全局变量，唯一选中
            button3.setToolTipText("重要");
            button3.setText("  重 要                        "+ MyButton.ID_imp);
            button3.setFont(new Font("微软雅黑", Font.PLAIN, 16));      //字体，监听器中改为粗体
            button3.setHorizontalAlignment(SwingConstants.LEFT); //对齐方式
            //设置按钮图标
            ImageIcon icon_src = new ImageIcon("src/assets/Component_Icon_03.png");
            Image icon_tem = icon_src.getImage();
            ImageIcon icon = new ImageIcon(icon_tem);
            button3.setIcon(icon);
            button3.setBorderPainted(false);
            button3.setHorizontalAlignment(SwingConstants.LEFT); //对齐方式

            //监听器
            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println("跳转重要任务");
                    if (checked_Button != null){
                        checked_Button.setBorderPainted(false);
                        checked_Button.setBackground(new Color(255, 255, 255));
                        checked_Button.setForeground(Color.black);
                        checked_Button.setFont(new Font("微软雅黑",Font.PLAIN,16));
                    }
                    checked_Button = button3;
                    button3.setBorderPainted(true);
                    button3.setBackground(new Color(225, 225, 225));
                    button3.setFont(new Font("微软雅黑",Font.BOLD,18));
                    button3.setForeground(theme_color);

                    //切换事件处理
                    {
                        JButton button = (JButton) actionEvent.getSource();
                        System.out.println(button.getText());
                        if (checked_Panel != null){
                            checked_Panel.setVisible(false);        //将上一个选中的面板隐藏
                            checked_Panel.setEnabled(false);        //将面板禁用
                        }
                        checked_Panel = panels.panel_2;             //面板指针指向当前面板
                        panels.panel_2.setEnabled(true);            //将选中面板启用，并显示
                        panels.panel_2.setVisible(true);
                        panel02.removeAll();
                        panel02.add(panels.panel_2);
//                        panel02.validate();                         //加载面板
                        panel02.repaint();
                    }
                }
            });
        }

        //按钮4：所有任务
        {
            button4.setBackground(new Color(255, 255, 255));
            button4.setFocusPainted(false);         //取消焦点
            button4.setBounds(0, 150, 270, 50);
            button4.setBorder(BorderFactory.createMatteBorder(0, 6, 0, 0, theme_color)); //边框设置：添加监听器，全局变量，唯一选中
            button4.setToolTipText("所有任务");
            button4.setText("  所有任务                  "+ MyButton.ID_all);
            button4.setFont(new Font("微软雅黑", Font.PLAIN, 16));      //字体，监听器中改为粗体
            button4.setHorizontalAlignment(SwingConstants.LEFT); //对齐方式
            //设置按钮图标
            ImageIcon icon_src = new ImageIcon("src/assets/Component_Icon_04.png");
            Image icon_tem = icon_src.getImage();
            ImageIcon icon = new ImageIcon(icon_tem);
            button4.setIcon(icon);
            button4.setBorderPainted(false);
            button4.setHorizontalAlignment(SwingConstants.LEFT); //对齐方式

            //监听器
            button4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println("跳转所有任务");
                    if (checked_Button != null){
                        checked_Button.setBorderPainted(false);
                        checked_Button.setBackground(new Color(255, 255, 255));
                        checked_Button.setForeground(Color.black);
                        checked_Button.setFont(new Font("微软雅黑",Font.PLAIN,16));
                    }
                    checked_Button = button4;
                    button4.setBorderPainted(true);
                    button4.setBackground(new Color(225, 225, 225));
                    button4.setFont(new Font("微软雅黑",Font.BOLD,18));
                    button4.setForeground(theme_color);

                    //切换事件处理
                    {
                        JButton button = (JButton) actionEvent.getSource();
                        System.out.println(button.getText());
                        if (checked_Panel != null){
                            checked_Panel.setVisible(false);        //将上一个选中的面板隐藏
                            checked_Panel.setEnabled(false);        //将面板禁用
                        }
                        checked_Panel = panels.panel_3;             //面板指针指向当前面板
                        panels.panel_3.setEnabled(true);            //将选中面板启用，并显示
                        panels.panel_3.setVisible(true);
                        panel02.removeAll();
                        panel02.add(panels.panel_3);                //添加透明面板
//                        panel02.validate();                         //加载面板
                        panel02.repaint();
                    }
                }
            });
        }

        panel01.add(button_s);
        panel01.add(button1);
        panel01.add(button2);
        panel01.add(button3);
        panel01.add(button4);
    }

}
