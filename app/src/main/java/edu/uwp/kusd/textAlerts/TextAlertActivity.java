package edu.uwp.kusd.textAlerts;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.uwp.kusd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TextAlertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_alert);

        Button submit = (Button) findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                EditText inputFirstName = (EditText) findViewById(R.id.firstNameText);
                String firstName = inputFirstName.getText().toString();

                EditText inputLastName = (EditText) findViewById(R.id.lastNameText);
                String lastName = inputLastName.getText().toString();

                EditText inputPhoneNumber = (EditText) findViewById(R.id.phoneText);
                String phoneNumber = inputPhoneNumber.getText().toString();


                final AlertDialog.Builder builder = new AlertDialog.Builder(TextAlertActivity.this);

                textClient client = ServiceGenerator.createService(textClient.class);
                Call<String> call = client.postPhone(firstName, lastName, phoneNumber);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        //If it works
                        if (response.isSuccessful()) {

                            builder.setMessage("Submission successful, thank you for signing up for text alerts")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                        //If you give it bad data
                        else {
                            builder.setMessage("Submission unsuccessful, please enter a proper 10 digit phone number")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }

                    //If connection fails
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        builder.setMessage("Please check your data connection")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //do things
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }


                });
            }


        });
    }}







