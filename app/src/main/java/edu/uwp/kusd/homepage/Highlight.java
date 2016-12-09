package edu.uwp.kusd.homepage;

/**
 * Created by Dakota on 11/17/2016.
 */

public class Highlight {

    /**
     * The title of the image for the highligh
     */
    private String imageTitle;

    /**
     * The image url for the highlight photo
     */
    private String imageURL;

    /**
     * The link for the image highlight
     */
    private String imageLink;

    /**
     * Constructs a highlight
     *
     * @param imageTitle the title of the image for the highlight
     * @param imageURL the image url for the highlight
     * @param imageLink the image link for the highlight
     */
    public Highlight(String imageTitle, String imageURL, String imageLink) {
        this.imageTitle = imageTitle;
        this.imageURL = imageURL;
        this.imageLink = imageLink;
    }

    /**
     * Gets the image url for the highlight
     *
     * @return the image url for the highlight
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Gets the image link for the highlight
     *
     * @return the image link for the highlight
     */
    public String getImageLink() {
        return imageLink;
    }
}
