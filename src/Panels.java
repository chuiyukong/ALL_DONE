import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @description:     切换栏选择器所对应的面板类，包含3个页面，对应三个选项
 * @author: 宇智波Akali
 * @time: 2020/5/21 22:43
 * @fromProject: All_Done
 * @Version: V1.0
 */
public class Panels {

//    private Boolean state = false;          //初始状态定义为不展开
    public  JPanel panel_1;
    public  JPanel panel_2;
    public  JPanel panel_3;

    public Panels(Color theme_color){

        //面板1：今日任务
        {
            panel_1 = new JPanel();
            panel_1.setLayout(null);
            panel_1.setBounds(0,0,780,700);

            new Components(theme_color,panel_1,"  今日任务","src/assets/LOG_TODAY");
            panel_1.setOpaque(false);
        }

        //面板2：重要
        {
            panel_2 = new JPanel();
            panel_2.setLayout(null);
            panel_2.setBounds(0,0,780,700);

            new Components(theme_color,panel_2,"  重 要","src/assets/LOG_IMP");
            panel_2.setOpaque(false);
        }

        //面板3：所有任务
        {
            panel_3 = new JPanel();
            panel_3.setLayout(null);
            panel_3.setBounds(0,0,780,700);

            new Components(theme_color,panel_3,"  所有任务","src/assets/LOG_ALL");
            panel_3.setOpaque(false);
        }
    }
}
