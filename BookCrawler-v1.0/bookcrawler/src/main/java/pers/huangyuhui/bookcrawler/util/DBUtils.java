package pers.huangyuhui.bookcrawler.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @project: BookCrawler
 * @description: 数据库工具类
 * @author: 黄宇辉
 * @date: 7/9/2019-5:38 PM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
public class DBUtils {
    private static Connection connection;
    private static ConfigManager config;

    //加载数据库驱动
    static {
        try {
            config = ConfigManager.getInstance();
            Class.forName(config.getString("jdbc.driver.class"));
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * @description: 获取数据库连接对象
     * @date: 2019-07-13 6:17 PM
     * @return: java.sql.Connection
     */
    public static Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(
                        config.getString("jdbc.connection.url"),
                        config.getString("jdbc.connection.username"),
                        config.getString("jdbc.connection.password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}