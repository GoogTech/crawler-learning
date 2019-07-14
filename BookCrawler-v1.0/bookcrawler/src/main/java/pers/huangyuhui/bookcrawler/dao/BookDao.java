package pers.huangyuhui.bookcrawler.dao;

import pers.huangyuhui.bookcrawler.pojo.Book;
import pers.huangyuhui.bookcrawler.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @project: BookCrawler
 * @description: 操控书籍数据
 * @author: 黄宇辉
 * @date: 7/9/2019-5:36 PM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
public class BookDao {
    private static Connection connection = DBUtils.getConnection();

    /**
     * @description: 将书籍数据存储到数据库
     * @param: book
     * @date: 2019-07-13 7:09 PM
     * @return: boolean
     */
    public boolean insert(Book book) {
        PreparedStatement preparedStatement;
        String sql = "insert into tb_book(sku,name, price,author,publishing,pubDate,imageName,bookUrl,imageUrl) value (?,?,?,?,?,?,?,?,?)";
        try {
            if (book != null) {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, book.getSku());
                preparedStatement.setString(2, book.getName());
                preparedStatement.setDouble(3, book.getPrice());
                preparedStatement.setString(4, book.getAuthor());
                preparedStatement.setString(5, book.getPublishing());
                preparedStatement.setString(6, book.getPubDate());
                preparedStatement.setString(7, book.getImageName());
                preparedStatement.setString(8, book.getBookUrl());
                preparedStatement.setString(9, book.getImageUrl());
                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}