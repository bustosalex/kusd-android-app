package edu.uwp.kusd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.net.Uri;


import static android.content.pm.PackageManager.GET_META_DATA;
import static android.content.pm.PackageManager.NameNotFoundException;



public class InfiniteCampusActivity extends AppCompatActivity {


    private final String campusExt = "com.infinitecampus.mobilePortal";
    private final String campusExtExt = "&hl=en";
    private final String campusURL = "market://details?id=" + campusExt + campusExtExt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infinite_campus);

        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(campusExt);
        if (launchIntent != null) {
            startActivity(launchIntent);//null pointer check in case package name was not found

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Infinite Campus is not installed.\nOpening Play Store.", Toast.LENGTH_LONG);
            toast.show();
            Intent campIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(campusURL));
            try {
                // Attempt to launch Infinite Campus
                startActivity(campIntent);
            } catch (ActivityNotFoundException e) {
                // Failed to launch, display exception to user
                raiseException(e.getMessage());


            }

        }
    }

    private void raiseException(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message).setPositiveButton("Dismiss", null);

        AlertDialog alert = builder.create();
        alert.show();
    }
}