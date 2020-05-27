import java.io.*;

/**
 * @description:
 * @author: 宇智波Akali
 * @time: 2020/5/24 14:17
 * @fromProject: All_Done
 * @Version: V1.0
 */
public class Log_IO {
    public void write(String path, String content){
        try {
            File writeName = new File(path); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName,true);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(content+"\r\n");  // \r\n即为换行
                out.flush();                    // 把缓存区内容压入文件
            }
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    //文件读取，传入地址，返回内容数组：第0号为任务总数
    public String[] read(String fileName){
        //不关闭文件会导致资源的泄露
        String [] contents = new String[10000];             //最大一万个任务存储
        try (FileReader reader = new FileReader(fileName);          /****************BUG预警：需要创建好三个txt文件，否则读取报错，或者可以先执行写入函数，写入 空内容***********/
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            int num = 0;
            int i = 0;
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                if(!line.equals("AKali") && !line.equals("AKali&")){       //不等于特殊值则计数
                    num ++;                 //实际包含的任务数量
                }
                i++;                        //用i来计数,num用来记录有效值
                contents[i] = line;       //从1开始将任务内容传递给数组
                System.out.println(line);
            }
            contents[0] = "" + num;               //有效任务数，用于传给左侧面板计数
            contents[9999] = "" +  i;            //9999号位置放置总任务数,这里的任务数用于循环中生成按钮
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }
}
