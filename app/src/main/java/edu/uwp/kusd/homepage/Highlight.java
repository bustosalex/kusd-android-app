package edu.uwp.kusd.homepage;

/**
 * Created by Dakota on 11/17/2016.
 */

public class Highlight {

    private String imageTitle;
    private String imageURL;
    private String imageLink;

    public Highlight(String imageTitle, String imageURL, String imageLink) {
        this.imageTitle = imageTitle;
        this.imageURL = imageURL;
        this.imageLink = imageLink;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
