package pers.huangyuhui.bookcrawler.crawler.util;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @project: bookcrawler
 * @description: 文件工具类
 * @author: 黄宇辉
 * @date: 7/17/2019-6:47 AM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
public class FileUtils {
    private static String dirPath = null;

    /**
     * @description: 获取项目下存储书籍图片的文件夹路径
     * @param: downloadPath
     * @date: 2019-07-17 6:57 AM
     * @return: java.lang.String
     */
    public static String getDirPath(String downloadPath) {
        try {
            //指定存储书籍图片文件夹的完整路径(项目发布路径): 若不使用绝对路径,则Spring boot会默认将上传的文件存储到临时目录中
            dirPath = URLDecoder.decode(ResourceUtils.getURL("classpath:").getPath(), StandardCharsets.UTF_8) + downloadPath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //debug: /E:/Intellij IDEA/workbench/workbench_project/SpringBoot/bookcrawler-v3.0/target/classes//static/icon/bookImage/
        return dirPath;
    }
}