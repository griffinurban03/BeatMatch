package com.example.openingactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {

    // URLs for PUT, and DELETE requests
    private final String PUT_URL = "http://10.90.74.200:8080";
    private final String DEL_URL = "http://10.90.74.200:8080";

    // UI elements
    TextView textGetUser, textGetEmail;
    TextView textGetResponse;
    Button deleteButton, updateAnswer1, updateAnswer2;
    ExecutorService executorService;

    EditText inputAnswer1;
    EditText inputAnswer2;

    // Initialize onCreate Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // Map UI elements to XML elements
        textGetResponse = findViewById(R.id.text_get_response);
        textGetUser = findViewById(R.id.text_get_user_ID);
        textGetEmail = findViewById(R.id.text_get_user_email);
        deleteButton = findViewById(R.id.button_delete_account);
        updateAnswer2 = findViewById(R.id.button_answer_2_update);
        inputAnswer1 = findViewById(R.id.input_security_answer_1);
        inputAnswer2 = findViewById(R.id.input_security_answer_2);
        executorService = Executors.newSingleThreadExecutor();

        // Set the user information in the TextViews
        textGetUser.setText("" + user.getUserID());
        textGetEmail.setText("" + user.getUserEmail());

        // Button to send DELETE request
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        sendDeleteRequest(DEL_URL + "/forgetPassword/" + user.getUserEmail());
                    }
                });

                // Change intent to login activity
                /*
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
                 */
            }
        });

        // Button to send PUT request
        updateAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer1 = inputAnswer1.getText().toString();
                String answer2 = inputAnswer2.getText().toString();
                String email = user.getUserEmail();

                // Create JSON object for PUT request
                JSONObject json = new JSONObject();
                try {
                    json.put("email", email);
                    json.put("ansSecurityQuestion1", answer1);
                    json.put("ansSecurityQuestion2", answer2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        // Send PUT request
                        sendPutRequest(PUT_URL + "/forgetPassword/" + email, json.toString());
                    }
                });
            }
        });
    }

    private void sendPutRequest(String url, String jsonData) {
        try {
            URL urls = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);


            // Write JSON data to the output stream
            OutputStream os = conn.getOutputStream();
            os.write(jsonData.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();


            // Get the response
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }


            conn.disconnect();
            String result = sb.toString();


            // Optionally handle the response from the PUT request
            Log.d("PUT RESPONSE", result);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendDeleteRequest(String url) {
        try {
            URL urls = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);


            // Get the response
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }


            conn.disconnect();
            String result = sb.toString();


            // Optionally handle the response from the POST request
            Log.d("DELETE RESPONSE", result);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
