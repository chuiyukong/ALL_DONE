import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @description: 最小化到系统托盘
 * @author: 宇智波Akali
 * @time: 2020/5/15 0:15
 * @fromProject: All_Done
 * @Version: V1.0
 */

public class Tray {
    public Tray(Boolean t, JFrame frame){
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE); // 点击关闭按钮时隐藏窗口

        /*
         * 添加系统托盘
         */
        if(SystemTray.isSupported()){
            // 获取当前平台的系统托盘
            SystemTray tray = SystemTray.getSystemTray();

            // 加载一个图片用于托盘图标的显示
            Image image = Toolkit.getDefaultToolkit().getImage("src/assets/Icon.png");

            // 创建点击图标时的弹出菜单
            PopupMenu popupMenu = new PopupMenu();

            MenuItem openItem = new MenuItem("Open");
            MenuItem exitItem = new MenuItem("exit");

            openItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 点击打开菜单时显示窗口
                    if (!frame.isShowing()) {
                        frame.setVisible(true);
                    }
                }
            });
            exitItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 点击退出菜单时退出程序
                    System.exit(0);
                }
            });

            popupMenu.add(openItem);
            popupMenu.add(exitItem);

            // 创建一个托盘图标
            TrayIcon trayIcon = new TrayIcon(image, "All Done", popupMenu);

            // 托盘图标自适应尺寸
            trayIcon.setImageAutoSize(true);

            trayIcon.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("托盘图标被右键点击");
                }
            });
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount()==2){
                        frame.setVisible(true);
                        System.out.println(e.getClickCount());
                    }
                    switch (e.getButton()) {
                        case MouseEvent.BUTTON1: {
                            System.out.println("托盘图标被鼠标左键被点击");
                            break;
                        }
                        case MouseEvent.BUTTON2: {
                            System.out.println("托盘图标被鼠标中键被点击");
                            break;
                        }
                        case MouseEvent.BUTTON3: {
                            System.out.println("托盘图标被鼠标右键被点击");
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
            });

            // 添加托盘图标到系统托盘
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("当前系统不支持系统托盘");
        }

        frame.setVisible(true);
    }
}
