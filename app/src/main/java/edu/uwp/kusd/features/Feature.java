package edu.uwp.kusd.features;

/**
 * Created by Cabz on 12/1/2016.
 */

public class Feature {

    private String imageURL;
    private String imageLink;

    public Feature(String imageURL, String imageLink) {
        this.imageURL = imageURL;
        this.imageLink = imageLink;
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