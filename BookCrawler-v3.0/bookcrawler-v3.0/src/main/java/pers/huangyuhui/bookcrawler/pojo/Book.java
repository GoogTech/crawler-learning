package pers.huangyuhui.bookcrawler.pojo;

/**
 * @project: bookcrawler
 * @description: 图书信息
 * @author: 黄宇辉
 * @date: 7/11/2019-9:12 PM
 * @version: 3.0
 * @website: https://yubuntu0109.github.io/
 */
public class Book {

    private Integer id;
    private String shCode;//商品编码
    private String name;
    private double price;//未实现
    private String author;
    private String publishing;//出版社
    private String pubDate;//出版时间
    private String imageName;//图书图片名
    private String bookUrl;//图书商品链接
    private String imageUrl;//图书图片链接

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShCode() {
        return shCode;
    }

    public void setShCode(String shCode) {
        this.shCode = shCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishing() {
        return publishing;
    }

    public void setPublishing(String publishing) {
        this.publishing = publishing;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", shCode='" + shCode + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", author='" + author + '\'' +
                ", publishing='" + publishing + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", imageName='" + imageName + '\'' +
                ", bookUrl='" + bookUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}