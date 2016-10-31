package edu.uwp.kusd.News;

/**
 * Created by Little Cody on 10/6/2016.
 */

public class NewsItems {
    String newsItem;
    String newsDate;
    String newsContent;


    NewsItems(String newsItem,String newsDate ,String newsContent) {
        this.newsItem = newsItem;
        this.newsContent = newsContent;
        this.newsDate = newsDate;

    }

    public String getNewsItem() {
        return newsItem;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public void setNewsItem(String newsItem) {
        this.newsItem = newsItem;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }
}
