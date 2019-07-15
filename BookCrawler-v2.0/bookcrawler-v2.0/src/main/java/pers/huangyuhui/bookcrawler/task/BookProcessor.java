package pers.huangyuhui.bookcrawler.task;

import pers.huangyuhui.bookcrawler.dao.BookDao;
import pers.huangyuhui.bookcrawler.pojo.Book;
import pers.huangyuhui.bookcrawler.util.HttpUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @project: bookcrawler
 * @description: 爬虫程序
 * @author: 黄宇辉
 * @date: 7/12/2019-9:00 AM
 * @version: 2.0
 * @website: https://yubuntu0109.github.io/
 */
public class BookProcessor implements PageProcessor {

    //爬虫配置信息
    private String url;
    private int endPageNum;
    private String imagePath;
    private int currentPageNum;

    private static int count = 1;
    private Book book = new Book();
    private BookDao bookDao = new BookDao();

    //初始化爬虫配置信息
    public BookProcessor(String url, String imagePath, int currentPageNum, int endPageNum) {
        this.url = url;
        this.imagePath = imagePath;
        this.endPageNum = endPageNum;
        this.currentPageNum = currentPageNum;
    }

    //对爬虫进行一些自定义配置
    private static Site site = Site.me()
            .addHeader("User-Agent", "x-x-x-x-x-x")//设置请求头信息(not null)
            .setCharset("gbk")
            .setTimeOut(10000)//设置超时时间:10s
            .setRetrySleepTime(2000)//设置重试间隔时间:2s
            .setRetryTimes(5);//设置重试次数:5次

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        //解析图书列表页
        List<Selectable> list = page.getHtml().css("div#J_goodsList > ul > li").nodes();
        //如果list为空,则表示url指向的是图书详情页:爬取所需图书数据并保存
        //如果list不为空,则表示url指向的是图书列表页:爬取图书详情页链接
        if (list.size() == 0) {
            saveBookData(page);
        } else {
            //解析图书列表页面,获取当前图书列表页面所有图书详情页的链接,并将其存放到任务队列中(会自动对其发起请求)
            for (Selectable selectable : list) {
                page.addTargetRequest(selectable.links().toString());
            }
            //初始化下一页图书列表页的链接,并将其存放到任务队列中
            if (currentPageNum < endPageNum) {
                page.addTargetRequest(url + currentPageNum);
            }
            currentPageNum += 2;//下一页的页码数为当前页码数加二
        }
    }

    /**
     * @description: 解析图书详情页, 爬取图书数据并将其保存
     * @param: page
     * @date: 2019-07-12 10:27 AM
     * @return: void
     */
    private void saveBookData(Page page) {
        ////深度爬取:进入图书详情页,获取其页面HTML继而进行解析,爬取所需的图书数据////
        book.setBookUrl(page.getUrl().toString());
        Html html = page.getHtml();
        //模块:div#preview
        book.setImageUrl("https:" + html.css("div#preview div#spec-n1 img", "src").get());
        //模块:div#itemInfo
        book.setName(html.getDocument().select("div#itemInfo div.sku-name").text());
        book.setAuthor(html.getDocument().select("div#itemInfo div#p-author").text());
        //模块:div.p-parameter
        book.setPublishing(html.css("div.p-parameter ul.p-parameter-list li a", "text").regex(".*出版社.*").get());
        book.setPubDate(html.css("div.p-parameter ul.p-parameter-list li", "text").regex(".*出版时间.*").get());
        book.setShCode(html.css("div.p-parameter ul.p-parameter-list li", "text").regex(".*商品编码.*").get());
        //下载图片,获取重命名后的图片名
        book.setImageName(HttpUtils.doGetImage(book.getImageUrl(), imagePath));
        //存储所爬取的数据并输出日志信息
        printLog(bookDao.insert(book));
    }

    /**
     * @description: 保存数据并输出日志信息
     * @param: result
     * @date: 2019-07-15 5:27 PM
     * @return: void
     */
    private void printLog(boolean result) {
        if (result) {
            System.out.println("\n\n\\\\\\\\\\\\\\\\\\\\第 [" + count++ + "] 本\\\\\\\\\\\\\\\\\\\\");
            System.out.println(book.toString());
            System.out.println("[success]:This book data was added into the database successfully");
            System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\n\n");
        } else {
            System.err.println("\n[error]:Data wasn't added into the database");
        }
    }

    /**
     * @description: 爬虫启动程序
     * @date: 2019-07-12 9:09 AM
     * @return: void
     */
    public void run() {
        Spider.create(new BookProcessor(url, imagePath, currentPageNum, endPageNum))
                .addUrl(url)
                .thread(1)//若线程数过多则无法查看清晰的日志信息哟
                .run();
    }

}