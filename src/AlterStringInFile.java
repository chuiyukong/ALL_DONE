import java.io.*;

/**
 * @description:
 * @author: 宇智波Akali
 * @time: 2020/5/24 14:56
 * @fromProject: All_Done
 * @Version: V1.0
 */
public class AlterStringInFile {

    public AlterStringInFile(String path,String oldString,String newString){        //传入文件路径，旧字符串，新字符串
        try {
            File file = new File(path); //创建目标文件

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream( file))); //创建对目标文件读取流
            File newFile = new File("src/newFile"); //创建临时文件
            if (!newFile.exists()){
                newFile.createNewFile(); //不存在则创建
            }
            //创建对临时文件输出流，并追加
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(newFile,true)));
            String string = null; //存储对目标文件读取的内容
            while ((string = br.readLine()) != null){
                //判断读取的内容是否包含原字符串
                if (string.contains(oldString)){
                    //替换读取内容中的原字符串为新字符串
                    string = new String(string.replace(oldString,newString));
                }
                bw.write(string);
                bw.newLine(); //添加换行                /**********可能会产生多余的换行*************/
            }
            br.close(); //关闭流，对文件进行删除等操作需先关闭文件流操作
            bw.close();
            String filePath = file.getPath();
            file.delete(); //删除源文件
            newFile.renameTo(new File(filePath)); //将新文件更名为源文件
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}