
![](https://raw.githubusercontent.com/YUbuntu0109/Crawler-learning/master/bookcrawler-icon.png)  
  
## JD商城图书爬虫 ：`JDBookCrawler`

### 版本介绍
#### JDBookCrawler 1.0
*涉及技术 : `HttpClient`,`Jsoup`,`MySQL`,详细介绍及使用指南见我的博客文章 : https://yubuntu0109.github.io/2019/07/14/%E5%B0%8F%E7%88%AC%E8%99%AB-JDBookCrawler-V1-0/*

#### JDBookCrawler 2.0
*涉及技术 : `WebMagic`,`MyBatis`,`MySQL`,详细介绍及使用指南见我的博客文章 : https://yubuntu0109.github.io/2019/07/15/%E5%B0%8F%E7%88%AC%E8%99%AB-JDBookCrawler-V2-0/*

#### JDBookCrawler 3.0
*涉及技术 : `WebMagic`,`Spring Boot`,`MyBatis`,`MySQL`,爬虫功能基于`JDBookCrawler 2.0`,前端设计参考项目[`SpringBoot-curd-memo`](https://github.com/YUbuntu0109/SpringBoot-CURD-Memo),简单点说`v3.0`等于`v2.0`+`Spring Boot`,😅不尴尬嘻嘻~*


### 项目概述(`JDBookCrawler 3.0`)
#### 项目结构图
```
├─bookcrawler-v3.0
│  │  crawler.sql
│  │  mvnw
│  │  mvnw.cmd
│  │  pom.xml
│  │
│  │
│  └─src
│      └─main
│          ├─java
│          │  └─pers
│          │      └─huangyuhui
│          │          └─bookcrawler
│          │              │  BookcrawlerApplication.java
│          │              │
│          │              ├─controller
│          │              │      BookController.java
│          │              │
│          │              ├─crawler
│          │              │  │  BookCrawlerTest.java
│          │              │  │
│          │              │  ├─dao
│          │              │  │      BookDao.java
│          │              │  │
│          │              │  ├─mapper
│          │              │  │      BookMapper.xml
│          │              │  │
│          │              │  ├─pojo
│          │              │  │      Book.java
│          │              │  │
│          │              │  ├─resources
│          │              │  │      db.properties
│          │              │  │      log4j.properties
│          │              │  │      mybatis-config.xml
│          │              │  │
│          │              │  ├─task
│          │              │  │      BookProcessor.java
│          │              │  │
│          │              │  └─util
│          │              │          FileUtils.java
│          │              │          HttpUtils.java
│          │              │          MyBatisUtils.java
│          │              │
│          │              ├─dao
│          │              │      BookMapper.java
│          │              │      BookMapper.xml
│          │              │
│          │              ├─pojo
│          │              │      Book.java
│          │              │
│          │              └─service
│          │                  │  BookService.java
│          │                  │
│          │                  └─impl
│          │                          BookServiceImpl.java
│          │
│          └─resources
│              │  application.properties
│              │
│              ├─static
│              │  │  exist.txt
│              │  │
│              │  └─easyui
│              │      │  jquery.easyui.min.js
│              │      │  jquery.min.js
│              │      │
│              │      ├─css
│              │      │      default.css
│              │      │      demo.css
│              │      │
│              │      ├─js
│              │      │      outlook2.js
│              │      │      validateExtends.js
│              │      │
│              │      └─themes
|              |         |(略..)
│              │         
│              │
|              |        
│              └─templates
│                      bookList.html
│                      intro.html
│                      main.html
│
└─demonstration-images
        BookCrawler-V3.0-bookList.PNG
        BookCrawler-V3.0-Intro.PNG
```


#### 程序运行指南
1. *crawler.sql : 数据库文件*
```
BookCrawler-v3.0/bookcrawler-v3.0/crawler.sql
```

2. *BookCrawlerTest.java : 爬虫启动类(`crawler`包存放的是`JDBookCrawler-v2.0`哟 ~)*
```java
package pers.huangyuhui.bookcrawler.crawler;


import pers.huangyuhui.bookcrawler.crawler.task.BookProcessor;
import pers.huangyuhui.bookcrawler.crawler.util.FileUtils;

/**
 * @project: bookcrawler
 * @description: 爬虫测试程序
 * @author: 黄宇辉
 * @date: 7/11/2019-9:12 PM
 * @version: 2.0
 * @website: https://yubuntu0109.github.io/
 */
public class BookCrawlerTest {
    //自定义搜索关键字
    private static final String KEY_WORD = "网络爬虫";
    //自定义页码数,每页可爬取三十条数据(注:下一页的页码数为前一页的页码数加二)
    private static final int END_PAGE_NUM = 2;
    private static final int CURRENT_PAGE_NUM = 1;
    //获取项目下存储书籍图片的文件夹路径
    private static final String IMAGE_PATH = FileUtils.getDirPath("/static/download/bookImage/");
    //书籍列表页面的链接
    private static final String URL = "https://search.jd.com/Search?keyword=" + KEY_WORD + "&enc=utf-8&page=";

    /**
     * @description: 🕷启动爬虫
     * @date: 2019-07-15 4:09 PM
     */
    public static void main(String[] args) {
        new BookProcessor(URL, IMAGE_PATH, CURRENT_PAGE_NUM, END_PAGE_NUM).run();
    }
}
```
*启动爬虫后其控制台输出的日志信息请参考 : [`JDBookCrawler-v2.0`](https://yubuntu0109.github.io/2019/07/15/%E5%B0%8F%E7%88%AC%E8%99%AB-JDBookCrawler-V2-0/)*


3. *BookcrawlerApplication.java : 项目启动类(爬虫程序成功运行完成后,就可以启动该项目啦,这操作不尴尬哈哈哈 ~)*
```java
package pers.huangyuhui.bookcrawler;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @project: bookcrawler
 * @description: 项目启动类
 * @author: 黄宇辉
 * @date: 7/11/2019-9:16 PM
 * @version: 3.0
 * @website: https://yubuntu0109.github.io/
 */
@SpringBootApplication
@MapperScan("pers.huangyuhui.bookcrawler.dao") //扫描Mapper接口
public class BookcrawlerApplication {

    public static void main(String[] args) { SpringApplication.run(BookcrawlerApplication.class, args); }

}
```

4. *成功启动项目后,其项目主页,图书数据管理页如下图所示 :*

![](https://raw.githubusercontent.com/YUbuntu0109/Crawler-learning/master/BookCrawler-v3.0/demonstration-images/BookCrawler-V3.0-Intro.PNG)

![](https://raw.githubusercontent.com/YUbuntu0109/Crawler-learning/master/BookCrawler-v3.0/demonstration-images/BookCrawler-V3.0-bookList.PNG)
