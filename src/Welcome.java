import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * @description: 欢迎界面
 * @author: 宇智波Akali
 * @time: 2020/5/13 20:52
 * @fromProject: All_Done
 * @Version: V1.0
 */
public class Welcome extends JPanel{
    /***********************欢迎界面************************/
    public Welcome() {
        JFrame welcomeFrame = new JFrame();
        welcomeFrame.getContentPane().add(this);
        Image icon = Toolkit.getDefaultToolkit().getImage("src/assets/Icon.png");
        welcomeFrame.setIconImage(icon);
        ImageIcon logo = new ImageIcon("src/assets/Logo.png");


        //使用Label放置图片，但是分辨率不是很好
        JLabel welcomeLabel = new JLabel(logo);
        //布局
        welcomeFrame.getContentPane().add(welcomeLabel);
        welcomeFrame.setBounds(480,260,500,309);        //黄金分割比 0.618
        welcomeFrame.setUndecorated(true); // 去掉窗口的装饰 //要放在setVisible前面
        welcomeFrame.setVisible(true);
        welcomeFrame.setBackground(new Color(255, 255, 255));
        welcomeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //音频
        InputStream input = getClass().getResourceAsStream("assets/Ding.mp3"); //音频存放在src下
        Music music = new Music(input);
        music.start();

        //定时器,2s关闭欢迎窗口
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                welcomeFrame.dispose();
            }
        });
        timer.start();
    }

}
