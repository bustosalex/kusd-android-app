/*
//TODO: Implement callback methods

package edu.uwp.kusd;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

*/
/**
 * Class for an activity to house the PdfReaderFragment
 *//*

public class PdfReaderActivity extends AppCompatActivity {

    */
/**
     * Key for the Pdf file name extra.
     *//*

    private static final String EXTRA_PDF_NAME = "pdf_file_name";

    */
/**
     * Key for the Pdf file directory extra.
     *//*

    private static final String EXTRA_PDF_DIR = "pdf_file_dir";

    */
/**
     * The Pdf file name.
     *//*

    private String mPdfFileName;

    */
/**
     * The Pdf file directory.
     *//*

    private String mPdfFileDir;

    */
/**
     * Sets the view for the activity and creates the fragment for the PdfReader to be housed within the activity.
     *
     * @param savedInstanceState
     *//*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader);

        //Gets data from the Intent
        mPdfFileName = getIntent().getStringExtra(EXTRA_PDF_NAME);
        mPdfFileDir = getIntent().getStringExtra(EXTRA_PDF_DIR);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.pdf_fragment_container);

        if (fragment == null) {
            //Pass data to the PdfReaderFragment
            Bundle fileNameBundle = new Bundle();
            fileNameBundle.putString("FILE_NAME", mPdfFileName);
            fileNameBundle.putString("FILE_DIR", mPdfFileDir);
            fragment = new PdfReaderFragment();
            fragment.setArguments(fileNameBundle);
            fm.beginTransaction().add(R.id.pdf_fragment_container, fragment).commit();
        }
    }

    */
/**
     * Custom intent for passing for PdfReaderActivity.
     *
     * @param packageContext the packageContext
     * @param fileName the file name of a Pdf
     * @param fileDir the directory of a Pdf
     * @return an intent for PdfReaderActivity
     *//*

    public static Intent newIntent(Context packageContext, String fileName, String fileDir) {
        Intent i = new Intent(packageContext, PdfReaderActivity.class);
        i.putExtra(EXTRA_PDF_NAME, fileName);
        i.putExtra(EXTRA_PDF_DIR, fileDir);
        return i;
    }
}
*/
