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