package edu.uwp.kusd;

/**
 * Class to contain the data for PdfCalendars.
 */
class PdfCalendar {

    /**
     * Title for a Pdf file.
     */
    private String mFileTitle;

    /**
     * Name for a Pdf file - including file extension.
     */
    private String mFileName;

    /**
     * Description of a Pdf file.
     */
    private String mFileDescription;

    /**
     * Constructs a PdfCalendar object with a file title, file name, and file description.
     *
     * @param fileTitle the title of a pdf
     * @param fileDescription the description of a pdf
     * @param fileName the file name of a pdf
     */
    public PdfCalendar(String fileTitle, String fileDescription, String fileName) {
        this.mFileTitle = fileTitle;
        this.mFileDescription = fileDescription;
        this.mFileName = fileName + ".pdf";
    }

    /**
     * Gets the file title for a PdfCalendar.
     *
     * @return
     */
    public String getFileTitle() {
        return  mFileTitle;
    }

    /**
     * Gets the file description for a PdfCalendar.
     *
     * @return
     */
    public String getFileDescription() {
        return mFileDescription;
    }

    /**
     * Gets the file name for a PdfCalendar.
     *
     * @return
     */
    public String getFileName() {
        return mFileName;
    }
}
