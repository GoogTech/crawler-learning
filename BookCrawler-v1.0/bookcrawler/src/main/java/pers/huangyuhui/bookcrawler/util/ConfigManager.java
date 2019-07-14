package pers.huangyuhui.bookcrawler.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @project: BookCrawler
 * @description: 读取配置文件的工具类
 * @author: 黄宇辉
 * @date: 7/13/2019-9:25 PM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
public class ConfigManager {

    private static ConfigManager configManager;
    private static Properties properties;

    //加载配置文件
    private ConfigManager() {
        String configFile = "db.properties";
        properties = new Properties();
        try (InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream(configFile)) {
            if (is != null) {
                properties.load(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 获取配置文件工具类的实例
     * @date: 2019-07-14 10:57 AM
     * @return: pers.huangyuhui.bookcrawler.util.ConfigManager
     */
    public static ConfigManager getInstance() {
        if (configManager == null) {
            configManager = new ConfigManager();
        }
        return configManager;
    }

    /**
     * @description: 根据配置文件中的key获取其value值
     * @param: key
     * @date: 2019-07-14 10:57 AM
     * @return: java.lang.String
     */
    public String getString(String key) {
        return properties.getProperty(key);
    }

}