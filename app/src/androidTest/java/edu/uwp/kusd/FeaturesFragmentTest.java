package edu.uwp.kusd;

/**
 * Created by Cabz on 12/6/2016.
 */


import android.support.test.filters.SmallTest;

import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import edu.uwp.kusd.features.Feature;
import edu.uwp.kusd.features.FeaturesActivity;

/**
 * Created by Cabz on 12/6/2016.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest


public class FeaturesFragmentTest {




    @Test
    public  void notEmpty(){
        List<Feature> features = new ArrayList<>();
        String testString1 = "test1";
        String testString2 = "test2";
        String testString3 = "test3";
        String testString4 = "test4";

        Feature feature1 = new Feature(testString1,testString2);
        Feature feature2 = new Feature(testString3,testString4);

        features.add(feature1);
        features.add(feature2);
        Assert.assertEquals("message", features.isEmpty(),false);
    }

    @Test
    public  void correctObject(){
        List<Feature> features = new ArrayList<>();
        String testString1 = "test1";
        String testString2 = "test2";
        String testString3 = "test3";
        String testString4 = "test4";

        Feature feature1 = new Feature(testString1,testString2);
        Feature feature2 = new Feature(testString3,testString4);

        features.add(feature1);
        features.add(feature2);
        Assert.assertEquals("message", features.get(0), feature1);
        Assert.assertEquals("message", features.get(1), feature2);
    }



}