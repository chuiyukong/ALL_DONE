/**
 * @description:    自定义按钮类
 * @author: 宇智波Akali
 * @time: 2020/5/21 17:24
 * @fromProject: All_Done
 * @Version: V1.0
 */
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;

public class MyButton extends JButton implements MouseListener {

    private Shape shape = null;

    public static int ID_all = 0;               //三类按钮的数量
    public static int ID_today = 0;
    public static int ID_imp = 0;

    private Color click = new Color(192, 193, 203);// 按下时的颜色

    private Color focusIn = new Color(226, 227, 239);// 按下时的颜色

    private Color quit = new Color(255, 255, 255);// 离开时颜色

    public MyButton(String s) {
        super(s);
        addMouseListener(this);
        setContentAreaFilled(false);// 是否显示外围矩形区域 选否
        setHorizontalAlignment(SwingConstants.LEFT);            //默认靠左
        setFont(new Font("微软雅黑",Font.PLAIN,18));
        setSize(new Dimension(680,55));
        setPreferredSize(new Dimension(680,55));
        setFocusPainted(false);
        //设置按钮图标
        ImageIcon icon_src = new ImageIcon("src/assets/Component_Icon_08.png");         //可以试试绘制出来
        Image icon_tem = icon_src.getImage();
        ImageIcon icon = new ImageIcon(icon_tem);
        setIcon(icon);
    }

    public MyButton() {
        super();
        addMouseListener(this);
    }

    public MyButton(String text, Icon icon) {
        super(text, icon);
        addMouseListener(this);
    }

    public MyButton(Icon icon) {
        super(icon);
        addMouseListener(this);
    }

    public void setColor(Color c, Color q,Color f) {
        click = c;
        quit = q;
        focusIn = f;
    }

    public void mouseClicked(MouseEvent e) {
//        System.out.println("mouseClicked");
    }

    public void mousePressed(MouseEvent e) {

//        System.out.println("mousePressed");
    }

    public void mouseReleased(MouseEvent e) {
//        System.out.println("mouseReleased");
    }

    public void mouseExited(MouseEvent e) {
        this.setBackground(quit);
//        System.out.println("mouseExited");
    }

    public void mouseEntered(MouseEvent e) {
        this.setBackground(focusIn);
//        System.out.println("mouseEntered");
    }

    public void paintComponent(Graphics g) {
        //如果按下则为ＣＬＩＣＫ色 否则为 ＱＵＩＴ色
        if (getModel().isArmed()) {
            g.setColor(focusIn);
        } else if (getModel().isPressed()){
            g.setColor(click);

        }else {
            g.setColor(quit);
        }
        //填充圆角矩形区域 也可以为其它的图形
        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 10, 10);
        //必须放在最后 否则画不出来
        super.paintComponent(g);
    }

    public void paintBorder(Graphics g) {
        //画边界区域
        g.setColor(click);
        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 10, 10);
    }

    public boolean contains(int x, int y) {
        //判断点（x,y）是否在按钮内
        if (shape == null || !(shape.getBounds().equals(getBounds()))) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
        }
        return shape.contains(x, y);
    }

//    public static void main(String[] argv) {
//        JFrame f = new JFrame();
//        f.setSize(400, 300);
//
//        MyButton btn = new MyButton("button");
//        btn.setFocusPainted(false);
//        btn.setText("内容");
//        btn.setSize(200,50);
//
//        f.getContentPane().add(btn, BorderLayout.NORTH);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.setVisible(true);
//    }

}
