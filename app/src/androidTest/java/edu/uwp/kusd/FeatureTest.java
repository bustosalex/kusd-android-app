package edu.uwp.kusd;

import android.support.test.filters.SmallTest;

import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.uwp.kusd.features.Feature;
import edu.uwp.kusd.features.FeaturesActivity;

/**
 * Created by Cabz on 12/6/2016.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest


public class FeatureTest {
    String testString1 = "test1";
    String testString2 = "test2";


        Feature feature = new Feature(testString1,testString2);


    @Test
    public  void notNull(){
   Assert.assertEquals("message", feature,feature);
    }


}
