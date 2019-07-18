package pers.huangyuhui.bookcrawler.dao;

import pers.huangyuhui.bookcrawler.pojo.Book;

import java.util.List;

/**
 * @project: bookcrawler
 * @description: 数据访问层-操控图书信息
 * @author: 黄宇辉
 * @date: 7/11/2019-9:16 PM
 * @version: 3.0
 * @website: https://yubuntu0109.github.io/
 */
public interface BookMapper {

    // TODO: 7/17/2019 根据图书名查询指定图书信息
    Book selectByName(String name);

    // TODO: 7/17/2019 查询所有图书信息
    List<Book> selectAll(Book book);

    // TODO: 7/17/2019 修改指定id的图书信息
    int updateById(Book book);

    // TODO: 7/17/2019 删除指定id的图书信息
    int deleteById(Integer[] id);

}