package edu.uwp.kusd.textAlerts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import edu.uwp.kusd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TextAlertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_alert);
        TextClient client = ServiceGenerator.createService(TextClient.class);
        Call<String> call = client.postPhone("Joe","Smith","1231231234");
        call.enqueue(new Callback<String>() {
                         @Override
                         public void onResponse(Call<String> call, Response<String> response) {
                             //If it works
                             if(response.isSuccessful()){
                                 Log.d("Retrofit Worked", response.body());
                             }
                             //If you give it bad data
                             else{
                                 Log.d("Retrofit Kinda Worked" , response.message());
                             }
                         }
                         //If connection fails
                         @Override
                         public void onFailure(Call<String> call, Throwable t) {
                            Log.d("Retrofit Failed", t.getMessage());
                         }
                     }

        );
    }
}
