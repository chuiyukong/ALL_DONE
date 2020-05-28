import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * @description:
 * @author: 宇智波Akali
 * @time: 2020/5/28 12:34
 * @fromProject: ALL_DONE
 * @Version: V1.0
 */
public class startBrowser {
    public startBrowser(String url){
        // 先判断当前平台是否支持桌面
        if (Desktop.isDesktopSupported()) {
            // 获取当前平台桌面实例
            Desktop desktop = Desktop.getDesktop();

            // 使用默认浏览器打开链接
            try {
                desktop.browse(URI.create(url));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 打开指定文件/文件夹
//            desktop.open(new File("C:\\"));
        } else {
            System.out.println("当前平台不支持 Desktop");
        }
    }
}
