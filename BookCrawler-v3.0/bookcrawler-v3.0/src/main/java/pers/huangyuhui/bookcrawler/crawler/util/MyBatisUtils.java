package pers.huangyuhui.bookcrawler.crawler.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @project: bookcrawler
 * @description: MyBatis工具类
 * @author: 黄宇辉
 * @date: 7/15/2019-9:27 AM
 * @version: 2.0
 * @website: https://yubuntu0109.github.io/
 */
public class MyBatisUtils {
    private static SqlSessionFactory sqlSessionFactory = null;

    //初始化SqlSessionFactory对象
    static {
        try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 获取SqlSession对象
     * @date: 2019-07-15 9:29 AM
     * @return: org.apache.ibatis.session.SqlSession
     */
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession(true);//设置为自动提交
    }
}