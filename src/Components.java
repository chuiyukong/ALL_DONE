import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * @description:   分割面板中的组件
 * @author: 宇智波Akali
 * @time: 2020/5/23 18:20
 * @fromProject: All_Done
 * @Version: V1.0
 */
public class Components {
    private Boolean state = false;          //初始状态定义为不展开

//    private static int finished_all = 0;               //定义完成的数量，用于绘制已完成的任务进度条
//    private static int finished_imp = 0;               //定义完成的数量，用于绘制已完成的任务进度条
//    private static int finished_today = 0;               //定义完成的数量，用于绘制已完成的任务进度条

    JPanel L_Panel = new JPanel();          //定义分割面板和分割面板中的左右面板
    JPanel R_Panel = new JPanel();

    public Components(Color theme_color,JPanel panel,String labelName,String path){

        /***************************分割面板及左右子面板******************************/

        L_Panel.setOpaque(false);
        L_Panel.setLayout(new BorderLayout(10,5));

        //定义分割面板
        JSplitPane SplitPane = new JSplitPane();
        SplitPane.setDividerLocation(780);
        SplitPane.setLeftComponent(L_Panel);
        SplitPane.setRightComponent(R_Panel);
        SplitPane.setDividerSize(1);
        SplitPane.setContinuousLayout(true);        //持续绘制
        SplitPane.setBounds(0,0,780,660);
        SplitPane.setOpaque(false);
        SplitPane.setBorder(null);

        //左面板
        //左侧面板中的分面板：中间子面板：cPanel
        JPanel cPanel = new JPanel();
        cPanel.setBackground(theme_color);      //传入的背景色
        cPanel.setLayout(new FlowLayout());
        cPanel.setPreferredSize(new Dimension(750,6000));      //大小设置要大于滚动面板

        //左侧面板中的分面板：底部面板：sPanel
        JPanel sPanel = new JPanel();
        sPanel.setOpaque(false);

        //进度条承载面板
        JPanel wPanel = new JPanel();
        wPanel.setOpaque(false);


        //左侧面板中的分面板：中间面板：滚动面板：关系：cPanel放在滚动面板中，按钮添加动作放在cPanel中
        JScrollPane jScrollPane = new JScrollPane(cPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  //把按钮放在JScroll面板
        jScrollPane.setSize(775,650);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(15);            //设置滚动速度
        jScrollPane.getVerticalScrollBar().setUI(new DemoScrollBarUI());    //重写滚动面板UI
        jScrollPane.getHorizontalScrollBar().setUI(new DemoScrollBarUI());
        jScrollPane.setBorder(null);



        /*******************************面板组件**********************************/
        //创建按钮组，无论是在内存中的任务，还是新创建的任务，统一用这个按钮组，并且每次重新执行init函数，就重新从按钮1开始创建
        final MyButton[] buttons = new MyButton[10000];
//        final int[] i = {0};

        /*********************初始化模块：读入各类任务信息，创建按钮*******************/
        L_init(path,cPanel,buttons,SplitPane,wPanel);     //因为需要在按钮中设置分割面板展开监听，所以需要SplitPane,同时需要在cPanel上添加按钮
        jScrollPane.validate();                //重新载入滚动面板
        jScrollPane.repaint();

        //面板标题
        JLabel L_label = new JLabel(labelName);

        if (labelName.equals("  今日任务")){
            ImageIcon icon_src = new ImageIcon("src/assets/Label_Icon_02.png");
            Image icon_tem = icon_src.getImage();
            ImageIcon icon = new ImageIcon(icon_tem);
            L_label.setIcon(icon);
        }else if (labelName.equals("  重 要")){
            ImageIcon icon_src = new ImageIcon("src/assets/Label_Icon_03.png");
            Image icon_tem = icon_src.getImage();
            ImageIcon icon = new ImageIcon(icon_tem);
            L_label.setIcon(icon);
        }else if (labelName.equals("  所有任务")){
            ImageIcon icon_src = new ImageIcon("src/assets/Label_Icon_04.png");
            Image icon_tem = icon_src.getImage();
            ImageIcon icon = new ImageIcon(icon_tem);
            L_label.setIcon(icon);
        }

        L_label.setFont(new Font("微软雅黑", Font.BOLD, 26));
        L_label.setForeground(Color.white);
//        L_label.setBounds(30,0,200,80);
        L_label.setPreferredSize(new Dimension(200,80));

        //输入框
        /*****布局*****/
        JTextField textField = new JTextField();
        textField.setBackground(new Color(92, 112, 190));
        textField.setPreferredSize(new Dimension(400,30));
        textField.setBorder(null);
        textField.setFont(new Font("等线",Font.PLAIN,18));
        textField.setForeground(Color.white);
        textField.addFocusListener(new JTextFieldHintListener(textField,"  +  添加任务"));

        /*******************************监听器：根据监听输入情况，添加按钮列表****************************/
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JTextField textField_Event = (JTextField) actionEvent.getSource();
                String text = textField_Event.getText();
                System.out.println(text);
                if (!text.equals("")){      //输入内容非空

                    /********************************将按钮内容写入日志文件*********************************************/
                    new Log_IO().write(path+"/Mission.txt",text);

                    /********************************监听到输入事件：获取输入内容，创建新按钮****************************/
                    L_init(path,cPanel,buttons,SplitPane,wPanel);            //重新初始化读入内存中的按钮数据，重新绘制按钮
                    wPanel.validate();
                    wPanel.repaint();
                    L_label.validate();
                    L_label.repaint();

                    /********************************监听按钮点击事件：点击按钮->展开分割面板****************************/
//                buttons[i[0]].addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent actionEvent) {
//                        /**************展开动作模块****************/
//                        {
//                            if (state){
//                                SplitPane.setDividerLocation(780);
//                                state = false;
//                            }else {
//                                SplitPane.setDividerLocation(420);
//                                state = true;
//                            }
//                            SplitPane.repaint();
//                        }
//                    }
//                });
                    /************************************中间子面板根据监听器添加按钮**************************/
                    jScrollPane.validate();                //重新载入滚动面板
                    jScrollPane.repaint();
                    L_label.repaint();
                    textField_Event.setText("");
                }
            }
        });

        //底部面板添加 输入框
        sPanel.add(textField);
        sPanel.repaint();

        //在左侧面板中添加组件
        L_Panel.add(L_label,BorderLayout.NORTH);              //顶部添加标题
        L_Panel.add(sPanel,BorderLayout.SOUTH);             //底部添加输入框
        L_Panel.add(jScrollPane,BorderLayout.CENTER);       //中间添加滚动面板
        L_Panel.add(wPanel,BorderLayout.WEST);

        //右面板：在监听器中实时添加

        //添加总分割面板
        panel.add(SplitPane,BorderLayout.CENTER);
    }

    public void L_init(String path,JPanel cPanel,MyButton []buttons,JSplitPane SplitPane, JPanel wPanel){          //按钮初始化函数
//        int finished = 0;               //定义完成的数量，用于绘制已完成的任务进度条
        cPanel.removeAll();                     //重新载入前，移除所有按钮
        String fileName = path + "/Mission.txt";                                                    //对于备注，可以把这个抽取出来
        String [] contents = new Log_IO().read(fileName);
        int finished_all = 0;               //定义完成的数量，用于绘制已完成的任务进度条
        int finished_imp = 0;               //定义完成的数量，用于绘制已完成的任务进度条
        int finished_today = 0;               //定义完成的数量，用于绘制已完成的任务进度条


        /******************************重绘Panel_Selector中的各按钮对应的任务数***************************/
        if (path.equals("src/assets/LOG_ALL")){
            MyButton.ID_all = Integer.parseInt(contents[0]);
            Panel_Selector.button4.setText("  所有任务                  "+ MyButton.ID_all);
            Panel_Selector.button4.repaint();
            Panel_Selector.button4.validate();
        }else if (path.equals("src/assets/LOG_IMP")){
            MyButton.ID_imp =Integer.parseInt(contents[0]);
            Panel_Selector.button3.setText("  重 要                        "+ MyButton.ID_imp);
            Panel_Selector.button3.repaint();
            Panel_Selector.button3.validate();
        }else {
            MyButton.ID_today =Integer.parseInt(contents[0]);
            Panel_Selector.button2.setText("  今日任务                  "+ MyButton.ID_today);
            Panel_Selector.button2.validate();
            Panel_Selector.button2.repaint();
        }

        /********************************面板重载内存中的任务,并创建按钮*******************************/
        for (int i = 1; i <= Integer.parseInt(contents[9999]); i++){   //从1开始，创建已有按钮任务
            if (contents[i].equals("AKali")||contents[i].equals("AKali&")){
                SplitPane.setDividerLocation(780);
                state = false;
            }
            if (!contents[i].equals("AKali") && !contents[i].equals("AKali&")){

                if ((contents[i].substring(contents[i].length()-1)).equals("&")){      //当最后一位为标记完成时设置按钮内容不显示标记
//                    int finished = 0;               //定义完成的数量，用于绘制已完成的任务进度条
//                    finished ++;            //已完成数量

                    buttons[i] = new MyButton("   "+contents[i].substring(0,contents[i].length()-1));        //设置内容

                    /********************************更改完成图标***********************************/
                    ImageIcon icon_src = new ImageIcon("src/assets/Component_Icon_05.png");

                    {
                        JProgressBar progressBar = new JProgressBar(JProgressBar.VERTICAL,0,100);
                        progressBar.setPreferredSize(new Dimension(20,500));
                        progressBar.setStringPainted(true);
                        if (path.equals("src/assets/LOG_ALL")){
                            wPanel.removeAll();
                            if (MyButton.ID_all!=0){

//                                int finished = 0;               //定义完成的数量，用于绘制已完成的任务进度条
                                finished_all += 1;                  //已完成数量
                                System.out.println("所有已完成："+finished_all);
                                System.out.println("所有总任务：" +MyButton.ID_all);

//                                wPanel.removeAll();
                                int res = 0;
                                res = (int) 100.0*finished_all / MyButton.ID_all;
                                System.out.println("所有进度：" + res);
                                progressBar.setValue(res);
                                progressBar.validate();
                                progressBar.repaint();
                                wPanel.add(progressBar);
                                wPanel.validate();
                                wPanel.repaint();
                                L_Panel.validate();
                                L_Panel.repaint();
                                System.out.println("执行进度条");

                            }
                        }else if (path.equals("src/assets/LOG_IMP")){
                            wPanel.removeAll();
                            if (MyButton.ID_imp!=0){

                                finished_imp ++;            //已完成数量
                                System.out.println("重要已完成："+finished_imp);

                                int res = 0;
//                                wPanel.removeAll();
                                res = (int)100.0*finished_imp / MyButton.ID_imp;            //离谱的很，这个BUG因为省了新建变量，很奇怪

                                progressBar.setValue(res);
                                progressBar.validate();
                                progressBar.repaint();

                                wPanel.add(progressBar);
                                wPanel.validate();
                                wPanel.repaint();
                                L_Panel.validate();
                                L_Panel.repaint();
                            }
                        }else {
                            wPanel.removeAll();
                            if (MyButton.ID_today!=0){

//                                int finished = 0;               //定义完成的数量，用于绘制已完成的任务进度条
                                finished_today ++;            //已完成数量
//                                wPanel.removeAll();

                                System.out.println("今日："+ finished_today);
                                int res = 0;
                                res = (int)100.0*finished_today / MyButton.ID_today;

                                progressBar.setValue(res);
                                progressBar.validate();
                                progressBar.repaint();
                                wPanel.add(progressBar);
                                wPanel.validate();
                                wPanel.repaint();
                                L_Panel.validate();
                                L_Panel.repaint();
                            }
                        }
                    }
                    L_Panel.repaint();
                    Image icon_tem = icon_src.getImage();
                    ImageIcon icon = new ImageIcon(icon_tem);
                    buttons[i].setIcon(icon);
                }else {
                    buttons[i] = new MyButton("   "+contents[i]);
                }

                int finalI = i;                                         //获取按钮对应序号i
                buttons[i].addActionListener(new ActionListener() {     //创建监听器
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        /**************展开动作模块****************/
                        {
                            if (state){
                                SplitPane.setDividerLocation(780);
                                state = false;
                            }else {
                                SplitPane.setDividerLocation(420);
                                state = true;
                                R_init(R_Panel,buttons[finalI],contents[finalI],path,cPanel,buttons,SplitPane,wPanel);              /***********监听展开：重绘右侧面板所有内容,同时调用L_panel重绘左侧面板**********/
                            }
                            SplitPane.repaint();
                        }
                    }
                });
                cPanel.add(buttons[i]);
                cPanel.validate();                  //立即重新载入面板
                L_Panel.add(wPanel,BorderLayout.WEST);
            }

        }

//        /********************************任务完成进度条***************************/
//        {
//            JProgressBar progressBar = new JProgressBar(JProgressBar.VERTICAL,0,100);
//            progressBar.setStringPainted(true);
//            if (path.equals("src/assets/LOG_ALL")){
//                if (MyButton.ID_all!=0){
//                    finished = 100*finished / MyButton.ID_all;
//                    progressBar.setValue(finished);
//                    progressBar.repaint();
//                    L_Panel.add(progressBar,BorderLayout.WEST);
//                }
//            }else if (path.equals("src/assets/LOG_IMP")){
//                if (MyButton.ID_imp!=0){
//                    finished = 100*finished / MyButton.ID_imp;
//                    progressBar.setValue(finished);
//                    progressBar.repaint();
//                    L_Panel.add(progressBar,BorderLayout.WEST);
//                }
//            }else {
//                if (MyButton.ID_today!=0){
//                    finished = 100*finished / MyButton.ID_today;
//                    progressBar.setValue(finished);
//                    progressBar.repaint();
//                    L_Panel.add(progressBar,BorderLayout.WEST);
//                }
//            }
//        }
//        L_Panel.repaint();
    }

    public void R_init(JPanel R_panel,JButton button,String content,String path,JPanel cPanel,MyButton[] buttons,JSplitPane SplitPane,JPanel wPanel){           //这里传入的button后面可以用来设置完成icon
        R_panel.removeAll();                //刷新前清除面板内容
        R_panel.setBackground(new Color(255, 255, 255));

        /************************R_panel上的Label图标********************/
        ImageIcon icon_src = new ImageIcon("src/assets/Component_Icon_08.png");         //可以试试绘制出来
        Image icon_tem = icon_src.getImage();
        ImageIcon icon = new ImageIcon(icon_tem);
        JLabel Icon_label = new JLabel(icon);
        Icon_label.setBounds(25,40,20,20);

        String text_content;
        boolean equals = content.substring(content.length() - 1).equals("&");       //条件：判断是否完成

        if (equals){         //检测到标记完成时
            text_content =   content.substring(0,content.length()-1);
            ImageIcon icon_src_finish = new ImageIcon("src/assets/Component_Icon_05.png");         //可以试试绘制出来
            Image icon_src_finished = icon_src_finish.getImage();
            ImageIcon icon_finish = new ImageIcon(icon_src_finished);
            Icon_label.setIcon(icon_finish);

        }else {
            text_content = content;
        }
        JTextField textField = new JTextField(text_content);
        textField.setFont(new Font("微软雅黑", Font.BOLD, 20));
        textField.setBounds(55,25,360,50);
        textField.setBorder(null);

        /*****************监听R_Panel文本框，实时修改本地任务内容，重新初始化按钮*********************/
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String fileName = path + "/Mission.txt";
                String old_text = content;
                JTextField textField_Event = (JTextField) actionEvent.getSource();
                String new_text = textField_Event.getText();
                if (!new_text.equals("")){
                    new AlterStringInFile(fileName,old_text,new_text);      //监听文本框内容，替换文件内容，然后重新初始化L_panel
                }

                L_init(path,cPanel,buttons,SplitPane,wPanel);
                cPanel.repaint();
                cPanel.validate();
                SplitPane.repaint();
                SplitPane.validate();
            }
        });

        /*******************************添加删除按钮*******************************/
        MyButton del_Button = new MyButton("删除任务");
        del_Button.setBounds(200,600,100,30);
        del_Button.setIcon(null);
        del_Button.setFont(new Font("微软雅黑",Font.PLAIN,16));
        del_Button.setForeground(new Color(109, 110, 118));
        del_Button.setHorizontalAlignment(SwingConstants.CENTER);            //居中

        //删除任务监听
        del_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                state = false;
                String fileName = path + "/Mission.txt";
                String old_text =  textField.getText();
                new AlterStringInFile(fileName,old_text,"AKali");      //监听文本框内容，替换文件内容，然后重新初始化L_panel
                L_init(path,cPanel,buttons,SplitPane,wPanel);

                wPanel.validate();
                wPanel.repaint();
                cPanel.repaint();
                cPanel.validate();
                SplitPane.repaint();
                SplitPane.validate();
                L_Panel.validate();
                L_Panel.repaint();
            }
        });

        /*******************************添加完成按钮*******************************/
        MyButton finish_Button = new MyButton("任务完成");
        if (equals){
            finish_Button.setText("已完成");
            finish_Button.setEnabled(false);
        }
        finish_Button.setBounds(50,600,100,30);
        finish_Button.setIcon(null);
        finish_Button.setFont(new Font("微软雅黑",Font.PLAIN,16));
        finish_Button.setForeground(new Color(0, 0, 0));
        finish_Button.setHorizontalAlignment(SwingConstants.CENTER);
        finish_Button.setBackground(new Color(90, 164, 255));

        //监听完成事件
        finish_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                R_init(R_Panel,button,content,path,cPanel,buttons,SplitPane,wPanel);

                String fileName = path + "/Mission.txt";
                String old_text =  content;
                String temp_text = old_text.substring(old_text.length()-1);         //每个任务的最后一个字符，为“&”则为完成
                String new_text;
                if (!temp_text.equals("&")){                        //任务未完成时点击按钮则
                    new_text = old_text + "&";                                  //添加标记
                    finish_Button.setText("已完成");

                    //完成音效
                    InputStream input = getClass().getResourceAsStream("assets/Ding.mp3"); //音频存放在src下
                    Music music = new Music(input);
                    music.start();


                    finish_Button.setEnabled(false);
                    finish_Button.repaint();
                    SplitPane.setDividerLocation(780);
                    state = false;
                    ImageIcon icon_src_finish = new ImageIcon("src/assets/Component_Icon_05.png");         //可以试试绘制出来
                    Image icon_src_finished = icon_src_finish.getImage();
                    ImageIcon icon_finish = new ImageIcon(icon_src_finished);
                    Icon_label.setIcon(icon_finish);
                    finish_Button.repaint();

                    new AlterStringInFile(fileName,old_text,new_text);      //监听文本框内容，替换文件内容为已完成，然后重新初始化L_panel

                    L_init(path,cPanel,buttons,SplitPane,wPanel);
                    wPanel.validate();
                    wPanel.repaint();
                    L_Panel.repaint();
                    cPanel.repaint();
                    cPanel.validate();
                    SplitPane.repaint();
                    SplitPane.validate();
                    R_init(R_Panel,button,content,path,cPanel,buttons,SplitPane,wPanel);

                    R_panel.repaint();
                }
//                L_init(path,cPanel,buttons,SplitPane,wPanel);
//                else {                                             //完成则
//                    new_text = old_text.substring(0,old_text.length()-1);       //消除标记
//                    finish_Button.setText("完成任务");
//                    new AlterStringInFile(fileName,old_text,new_text);      //监听文本框内容，替换文件内容为已完成，然后重新初始化L_panel
//                    L_init(path,cPanel,buttons,SplitPane);
//                    cPanel.repaint();
//                    cPanel.validate();
//                    SplitPane.repaint();
//                    SplitPane.validate();
//                    R_panel.repaint();
//                    R_init(R_Panel,button,content,path,cPanel,buttons,SplitPane);
//                    L_init(path,cPanel,buttons,SplitPane);
//                    finish_Button.repaint();
//                }
//                finish_Button.repaint();
//                new AlterStringInFile(fileName,old_text,new_text);      //监听文本框内容，替换文件内容为已完成，然后重新初始化L_panel
//                L_init(path,cPanel,buttons,SplitPane);
//                cPanel.repaint();
//                cPanel.validate();
//                SplitPane.repaint();
//                SplitPane.validate();
//                R_panel.repaint();
            }
        });

        /************************************************备注栏************************************************/
        String path_PS = path + "/PostScript.txt";
        JTextArea textArea = new JTextArea();
        String [] PS_contents = new Log_IO().read(path_PS);
        textArea.setText(PS_contents[1]);
        textArea.setBounds(25,170,290,120);
        textArea.setBackground(new Color(226, 227, 239));
        textArea.setLineWrap(true);

        JLabel label_PS = new JLabel("添加备注:");
        label_PS.setForeground(new Color(123, 122, 122));
        label_PS.setBounds(25,120,100,50);
//        textArea.addFocusListener(new JTextFieldHintListener(textField,"  +  添加任务"));

        MyButton button_PS = new MyButton("确认");
        button_PS.setBounds(250,300,65,20);
        button_PS.setIcon(null);
        button_PS.setFont(new Font("微软雅黑",Font.PLAIN,14));
        button_PS.setForeground(new Color(109, 110, 118));
        button_PS.setHorizontalAlignment(SwingConstants.CENTER);            //居中
        button_PS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                String path_PS = path + "/PostScript.txt";
                try {
                    File writeName = new File(path_PS); // 相对路径，如果没有则要建立一个新的output.txt文件
                    writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
                    try (FileWriter writer = new FileWriter(writeName);
                         BufferedWriter out = new BufferedWriter(writer)
                    ) {
                        out.write(textArea.getText()+"\r\n");  // \r\n即为换行
                        out.flush();                    // 把缓存区内容压入文件
                    }
                } catch ( IOException e) {
                    e.printStackTrace();
                }
//                new AlterStringInFile(path_PS,PS_contents[1],textArea.getText());
                R_init(R_Panel,button,content,path,cPanel,buttons,SplitPane,wPanel);
//                textArea.validate();
//                textArea.repaint();
                R_panel.validate();
                R_panel.repaint();
//                new Log_IO().write(path_PS,textArea.getText());
            }
        });


        /*********************************BUG报告：如果  输入内容为AKali，则不会显示内容，这就假装是一个彩蛋******************************************/
        /*********************************BUG报告：不能有两条相同的任务，因为任务删除和完成实际上是在文件中替换文本内容，这也TM算彩蛋吧******************/
        /*********************************BUG预告：如果有相同的任务，那么更改一处，所有相同地方都会本更改，应为这里没有设置id，后面添加备注可能需要修改***/

        /********* √ 已解决 ：让文本框修改后面板关闭并初始化*****BUG报告：文本框只能更改一次，第二次更改后就不会写入，这个大概率是监听器执行了一次，因为在后面的完成按钮设置中也出现了类似的BUG********************************/
        //原因分析，事件监听器只能获取到一次的内容；也可能是这里在监听器中涉及循环调用；这个BUG就先不解决了。
        /********* √ 已解决 ：添加重新初始化，重绘**************BUG报告：修改了任务内容之后，直接点击完成任务会显示错误****************************************************/
        /********* √ 已解决 ：新建变量就好了 ******************BUG预告：如果连续添加任务并点击完成，就会出现新建任务后进度条100%的情况，同时如果删除的任务是已完成的，那么也会报错*,还有如果有两个任务完成，进度条也会变成100：大概率是监听器只能调用一次，然后里面的参数就不会再刷新值*************/
        /********* √ 已解决 ：改成任务完成后不能撤销，只能删除***BUG预告：现在空文件打开会报错，设置为完成就不能取消，应该是文件写入的问题，重新载入,重绘的问题*********************************/

        R_panel.setLayout(null);
        R_panel.add(textArea);
        R_panel.add(label_PS);
        R_panel.add(textField);
        R_panel.add(Icon_label);
        R_panel.add(del_Button);
        R_panel.add(finish_Button);
        R_panel.add(button_PS);
    }
}
