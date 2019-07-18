package pers.huangyuhui.bookcrawler.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.huangyuhui.bookcrawler.pojo.Book;
import pers.huangyuhui.bookcrawler.service.BookService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @project: bookcrawler
 * @description: 图书信息页控制器
 * @author: 黄宇辉
 * @date: 7/12/2019-6:47 AM
 * @version: 3.0
 * @website: https://yubuntu0109.github.io/
 */
@Controller
@RequestMapping("/bookCrawler")
public class BookController {

    @Resource
    private BookService bookService;

    //存储预返回页面的结果对象
    private Map<String, Object> result = new HashMap<>();

    /**
     * @description: 跳转到项目主页
     * @date: 2019-07-12 7:14 AM
     * @return: java.lang.String
     */
    @GetMapping("/goMainView")
    public String goMainView() {
        return "main";
    }

    /**
     * @description: 跳转到图书信息管理页
     * @date: 2019-07-12 7:15 AM
     * @return: java.lang.String
     */
    @GetMapping("goBookListView")
    public String goBookListView() {
        return "bookList";
    }


    /**
     * @description: 分页查询:根据图书名获取指定/所有图书信息
     * @param: page 当前页码数
     * @param: rows 列表行数
     * @param: bookname 图书名称
     * @date: 2019-07-17 8:20 AM
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     */
    @PostMapping("/getBookList")
    @ResponseBody
    public Map<String, Object> getBookList(Integer page, Integer rows, String bookname) {
        //获取查询的图书名
        Book book = new Book();
        book.setName(bookname);
        //设置分页数据
        PageHelper.startPage(page, rows);
        //根据图书名获取指定或所有图书信息
        List<Book> list = bookService.findAll(book);
        //封装查询结果
        PageInfo<Book> pageInfo = new PageInfo<>(list);
        //获取总记录数
        long total = pageInfo.getTotal();
        //获取当前页数据列表
        List<Book> bookList = pageInfo.getList();
        //存储数据对象
        result.put("total", total);
        result.put("rows", bookList);

        return result;
    }

    /**
     * @description: 根据id修改指定图书信息
     * @param: book
     * @date: 2019-07-17 8:23 AM
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     */
    @PostMapping("/editBook")
    @ResponseBody
    public Map<String, Object> editBook(Book book) {
        //需排除只修改图书名以外信息的操作
        Book b = bookService.findByName(book.getName());
        if (b != null) {
            if (!(book.getId().equals(b.getId()))) {
                result.put("success", false);
                result.put("msg", "该图书名称已存在! 请检查后重试哟!");
                return result;
            }
        }
        //添加操作
        if (bookService.modify(book) > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("msg", "添加失败! (ಥ_ಥ)服务器端发生异常!");
        }
        return result;
    }

    /**
     * @description: 删除指定id的图书信息
     * @param: ids 拼接后的id
     * @date: 2019-07-17 8:21 AM
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     */
    @PostMapping("/deleteBook")
    @ResponseBody
    public Map<String, Object> deleteBook(@RequestParam(value = "ids[]", required = true) Integer[] ids) {
        if (bookService.delete(ids) > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        return result;
    }
}