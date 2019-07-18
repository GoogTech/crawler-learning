package pers.huangyuhui.bookcrawler.service;

import pers.huangyuhui.bookcrawler.pojo.Book;

import java.util.List;

/**
 * @project: bookcrawler
 * @description: 业务层-操控图书信息
 * @author: 黄宇辉
 * @date: 7/11/2019-9:16 PM
 * @version: 3.0
 * @website: https://yubuntu0109.github.io/
 */
public interface BookService {

    // TODO: 7/17/2019 根据图书名查询指定图书信息
    Book findByName(String name);

    // TODO: 7/17/2019 查询所有图书信息
    List<Book> findAll(Book book);

    // TODO: 7/17/2019 修改指定id的图书信息
    int modify(Book book);

    // TODO: 7/17/2019 删除指定id的图书信息
    int delete(Integer[] id);
}