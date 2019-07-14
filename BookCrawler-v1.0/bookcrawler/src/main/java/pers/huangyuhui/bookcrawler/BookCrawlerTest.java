package pers.huangyuhui.bookcrawler;

import pers.huangyuhui.bookcrawler.pojo.HttpHeader;
import pers.huangyuhui.bookcrawler.task.BookCrawler;

/**
 * @project: BookCrawler
 * @description: 京东商城书籍爬虫v1.0-测试程序
 * @author: 黄宇辉
 * @date: 7/13/2019-5:57 PM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
public class BookCrawlerTest {
    //自定义搜索关键字
    private static String KEY_WORD = "Java";
    //自定义页码数,每页可爬取三十条书籍数据
    private static int currentPageNum = 1;
    private static int endPageNum = 10;
    //自定义存储书籍图片的目录路径
    private static final String IMAGE_PATH = "D:\\BookCrawler\\download\\bookImage\\";
    //初始化书籍页面链接
    private static final String URL = "https://search.jd.com/Search?keyword=" + KEY_WORD + "&enc=utf-8&page=";
    //自定义请求头信息
    private static HttpHeader httpHeader = new HttpHeader();

    //Test
    public static void main(String[] args) {
        httpHeader.setUserAgent("x-x-x-x-x-x");//需指定用户代理
        new BookCrawler().parse(URL, IMAGE_PATH, httpHeader, currentPageNum, endPageNum);
    }
}