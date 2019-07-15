package pers.huangyuhui.bookcrawler.dao;

import org.apache.ibatis.session.SqlSession;
import pers.huangyuhui.bookcrawler.pojo.Book;
import pers.huangyuhui.bookcrawler.util.MyBatisUtils;

/**
 * @project: bookcrawler
 * @description: 操控图书信息
 * @author: 黄宇辉
 * @date: 7/15/2019-10:25 AM
 * @version: 2.0
 * @website: https://yubuntu0109.github.io/
 */
public class BookDao {

    private final String NAME_SPACE = "pers.huangyuhui.bookcrawler.mapper.BookMapper.insert";

    /**
     * @description: 将图书信息存储到数据库
     * @param: book
     * @date: 2019-07-15 10:30 AM
     * @return: boolean
     */
    public boolean insert(Book book) {
        try (SqlSession sqlSession = MyBatisUtils.getSession()) {
            return sqlSession.insert(NAME_SPACE, book) > 0;
        }
    }
}