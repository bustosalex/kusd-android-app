package edu.uwp.kusd.calendar;

import io.realm.RealmObject;

/**
 * Class to contain the data for PdfCalendars.
 */
public class PdfCalendar extends RealmObject {

    /**
     * Title for a Pdf file.
     */
    private String mFileTitle;

    /**
     * Name for a Pdf file - including file extension.
     */
    private String mFileURL;

    /**
     * Default constructor for a PdfCalendar
     */
    public PdfCalendar() {
    }

    /**
     * Constructs a PdfCalendar object with a file title, file name, and file description.
     *
     * @param fileTitle the title of a pdf
     * @param fileURL the Url of a pdf
     */
    public PdfCalendar(String fileTitle, String fileURL) {
        this.mFileTitle = fileTitle;
        this.mFileURL = fileURL;
    }

    /**
     * Gets the title of a file for a pdf
     *
     * @return the title of a pdf
     */
    public String getFileTitle() {
        return mFileTitle;
    }

    /**
     * Sets the title of a file for a pdf
     *
     * @param fileTitle the title of a pdf
     */
    public void setFileTitle(String fileTitle) {
        mFileTitle = fileTitle;
    }

    /**
     * Gets the file url of a pdf
     *
     * @return the file url of a pdf
     */
    public String getFileURL() {
        return mFileURL;
    }

    /**
     * Sets the file url for a pdf
     *
     * @param fileURL the file url for a pdf
     */
    public void setFileURL(String fileURL) {
        mFileURL = fileURL;
    }
}
