import javax.swing.*;
/**
 * @description:    启动器
 * @author: 宇智波Akali
 * @time: 2020/5/13 13:47
 * @fromProject: All_Done
 * @Version: V1.0
 */
public class Launcher extends JFrame {
    public Launcher() {
        new Welcome();      //欢迎窗口
        new MainFrame();    //主窗口
    }

    public static void main(String[] args) {
        new Launcher();
    }
}
