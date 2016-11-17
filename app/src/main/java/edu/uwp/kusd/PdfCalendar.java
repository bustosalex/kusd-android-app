package edu.uwp.kusd;

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

    public String getFileTitle() {
        return mFileTitle;
    }

    public void setFileTitle(String fileTitle) {
        mFileTitle = fileTitle;
    }

    public String getFileURL() {
        return mFileURL;
    }

    public void setFileURL(String fileURL) {
        mFileURL = fileURL;
    }
}
