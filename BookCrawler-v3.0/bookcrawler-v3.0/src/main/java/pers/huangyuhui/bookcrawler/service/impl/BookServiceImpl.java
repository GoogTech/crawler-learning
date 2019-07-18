package pers.huangyuhui.bookcrawler.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.huangyuhui.bookcrawler.dao.BookMapper;
import pers.huangyuhui.bookcrawler.pojo.Book;
import pers.huangyuhui.bookcrawler.service.BookService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @project: bookcrawler
 * @description: 业务层实现类-操控图书信息
 * @author: 黄宇辉
 * @date: 7/11/2019-9:16 PM
 * @version: 3.0
 * @website: https://yubuntu0109.github.io/
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Resource
    private BookMapper bookMapper;


    @Override
    public Book findByName(String name) { return bookMapper.selectByName(name); }

    @Override
    public List<Book> findAll(Book book) {
        return bookMapper.selectAll(book);
    }

    @Override
    public int modify(Book book) {
        return bookMapper.updateById(book);
    }

    @Override
    public int delete(Integer[] id) {
        return bookMapper.deleteById(id);
    }
}