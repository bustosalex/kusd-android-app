//TODO: Implement callback methods if needed

package edu.uwp.kusd;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;

/**
 * Class for a fragment for a PdfReader.
 */
public class PdfReaderFragment extends Fragment {

    /**
     * A PdfViewPager for the PdfReader.
     */
    private PDFViewPager pdfViewPager;

    /**
     * The file name for a Pdf.
     */
    private String mFileName;

    /**
     * The file directory for a Pdf.
     */
    private String mFileDir;

    /**
     * Creates the view for the PdfReader.
     *
     * @param inflater the layout inflator for the fragment
     * @param container the container for the fragment
     * @param savedInstanceState the saved bundle of a previous state if any
     * @return a View for the fragment
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pdfreader, container, false);

        //Get the file name and file directory arguments passed in as a bundle
        mFileName = getArguments().getString("FILE_NAME");
        mFileDir = getArguments().getString("FILE_DIR");

        //Instantiate the PdfViewPager
        //pdfViewPager = new PDFViewPager(getContext(), getPdfPathOnSDCard());

        return v;
        //return pdfViewPager;
    }

    /**
     * Default implementation of onAttach.
     *
     * @param context the context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * Closes the PdfViewPager onDestroy.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        //((PDFPagerAdapter)pdfViewPager.getAdapter()).close();
    }

    /**
     * Gets Pdf path on the SD card.
     *
     * @return the path of a Pdf
     */
    protected String getPdfPathOnSDCard() {
        File f = new File(getActivity().getExternalFilesDir(mFileDir), mFileName);
        return f.getAbsolutePath();
    }
}
