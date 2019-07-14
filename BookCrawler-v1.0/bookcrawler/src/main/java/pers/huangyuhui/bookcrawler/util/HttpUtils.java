package pers.huangyuhui.bookcrawler.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import pers.huangyuhui.bookcrawler.pojo.HttpHeader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @project: BookCrawler
 * @description: 爬虫工具类
 * @author: 黄宇辉
 * @date: 7/9/2019-6:56 PM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
public class HttpUtils {
    private static String imageName = null;
    private static String htmlContent = null;
    private static PoolingHttpClientConnectionManager phccm;//连接池管理器

    public HttpUtils() {
        phccm = new PoolingHttpClientConnectionManager();
        phccm.setMaxTotal(100);//最大连接数
        phccm.setDefaultMaxPerRoute(10);//每个主机的最大连接数
    }

    /**
     * @description: 获取页面的静态HTML
     * @param: url
     * @param: httpHeader
     * @date: 2019-07-14 11:37 AM
     * @return: java.lang.String
     */
    public static String doGetHtml(String url, HttpHeader httpHeader) {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(phccm).build();
        //创建HttpGet请求对象,设置url地址
        HttpGet httpGet = new HttpGet(url);
        //设置请求信息
        httpGet.setConfig(getConfig());
        httpGet.setHeader("User-Agent", httpHeader.getUserAgent());
        //发起请求,获取响应
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            //解析响应,获取数据
            if (response.getStatusLine().getStatusCode() == 200) {
                //获取响应体是否为空
                if (response.getEntity() != null) {
                    htmlContent = EntityUtils.toString(response.getEntity(), "utf-8");//获取静态页面
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return htmlContent;
    }

    /**
     * @description: 下载图书图片并返回重命名后的图片名
     * @param: url
     * @param: imagePath
     * @param: httpHeader
     * @date: 2019-07-14 11:37 AM
     * @return: java.lang.String
     */
    public static String doGetImage(String url, String imagePath, HttpHeader httpHeader) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(phccm).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(getConfig());
        httpGet.setHeader("User-Agent", httpHeader.getUserAgent());
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                if (response.getEntity() != null) {
                    String suffix = url.substring(url.lastIndexOf("."));//获取图片后缀
                    imageName = UUID.randomUUID().toString() + suffix;//重命名图片名
                    //若存储图片的目录不存在,则创建该目录
                    File file = new File(imagePath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    //将图片下载到指定文件夹
                    response.getEntity().writeTo(new FileOutputStream(imagePath + imageName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageName;
    }

    /**
     * @description: 设置请求配置信息
     * @date: 2019-07-09 7:29 PM
     * @return: org.apache.http.client.config.RequestConfig
     */
    private static RequestConfig getConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(10000) //创建连接的最长时间
                .setConnectionRequestTimeout(20000) //获取连接的最长时间
                .setSocketTimeout(20000) //数据传输的最长时间
                .build();
    }
}