package pers.huangyuhui.bookcrawler.task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pers.huangyuhui.bookcrawler.dao.BookDao;
import pers.huangyuhui.bookcrawler.pojo.Book;
import pers.huangyuhui.bookcrawler.pojo.HttpHeader;
import pers.huangyuhui.bookcrawler.util.HttpUtils;

/**
 * @project: BookCrawler
 * @description: 爬取书籍数据
 * @author: 黄宇辉
 * @date: 7/9/2019-6:37 PM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
public class BookCrawler {

    //书籍数据
    private String sku;
    private String name;
    private double price;
    private String author;
    private String publishing;
    private String pubDate;
    private String imageName;
    private String bookUrl;
    private String imageUrl;

    private static int count = 1;
    private static Book book = new Book();
    private static BookDao bookDao = new BookDao();

    /**
     * @description: 爬取书籍数据并存储
     * @param: url 图书列表页链接
     * @param: imagePath 存储图片的目录路径
     * @param: httpHeader 请求头信息
     * @param: currentPageNum 当前页码数
     * @param: endPageNum 末尾页码数
     * @date: 2019-07-14 11:27 AM
     * @return: void
     */
    public void parse(String url, String imagePath, HttpHeader httpHeader, int currentPageNum, int endPageNum) {
        while (currentPageNum < endPageNum) {
            String htmlContent = HttpUtils.doGetHtml(url + currentPageNum, httpHeader);//获取图书列表页的HTML
            System.out.println("\n[message]------>Begin to crawling this page : [" + url + currentPageNum + "]");
            Document doc = Jsoup.parse(htmlContent);//解析HTML,获取其Document对象
            Elements elements = doc.select("div#J_goodsList > ul > li"); //解析Document,获取页面所有书籍元素
            //遍历元素,提取元素中的书籍数据
            for (Element element : elements) {
                //爬取图书列表页数据
                sku = element.attr("data-sku");//data-sku
                bookUrl = "https://item.jd.com/" + sku + ".html";//个别书籍url规范不一致,既而以拼接的方式获取url
                price = Double.valueOf(element.select("div.p-price i").text());
                imageUrl = "https:" + element.select("div.p-img img").attr("source-data-lazy-img");
                imageName = HttpUtils.doGetImage(imageUrl, imagePath, httpHeader); //下载书籍图片并获取重命名后的图片名
                //深入爬寻:根据书籍链接爬取书籍详情页的数据
                String conent = HttpUtils.doGetHtml(bookUrl, httpHeader);//获取书籍详情页的HTML
                name = Jsoup.parse(conent).select("div#itemInfo div.sku-name").text();
                author = Jsoup.parse(conent).select("div#itemInfo div#p-author").text();
                publishing = Jsoup.parse(conent).select("div.p-parameter a").first().text();
                pubDate = Jsoup.parse(conent).select("div.p-parameter li:matches(出版时间)").text();
                //将数据存储到数据库
                if (bookDao.insert(saveData(book))) {
                    printLog();
                }
            }
            System.out.println("[message]------>This page has had crawled completly : [" + url + "]\n");
            currentPageNum += 2;//下一页的页码数为当前页码数加二
        }
    }


    /**
     * @description: 保存书籍数据
     * @param: book
     * @date: 2019-07-14 9:56 AM
     * @return: void
     */
    private Book saveData(Book book) {
        book.setSku(sku);
        book.setName(name);
        book.setPrice(price);
        book.setAuthor(author);
        book.setBookUrl(bookUrl);
        book.setPublishing(publishing);
        book.setPubDate(pubDate);
        book.setImageName(imageName);
        book.setImageUrl(imageUrl);
        return book;
    }

    /**
     * @description: 打印日志信息
     * @date: 2019-07-14 9:56 AM
     * @return: void
     */
    private void printLog() {
        System.out.println("\n\n\\\\\\\\\\\\\\\\\\\\第 [" + count++ + "] 本\\\\\\\\\\\\\\\\\\\\");
        System.out.println(book.toString());
        System.out.println("[message]------>Data was added to the database successfully ヾ(◍°∇°◍)ﾉﾞ");
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\n\n");
    }
}