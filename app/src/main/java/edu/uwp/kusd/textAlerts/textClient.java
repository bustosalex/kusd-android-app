package edu.uwp.kusd.textAlerts;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Little Cody on 11/19/2016.
 */

interface textClient {
    @Multipart
    @POST("/text-alerts.php")
    Call<String> postPhone(@Part("firstname") String firstname, @Part("lastname") String lastname, @Part("number") String number);
}
