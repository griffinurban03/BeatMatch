package com.example.openingactivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddEventActivity extends AppCompatActivity implements Request{

    Button buttonCreate;
    Button buttonCancel;
    EditText editTextEventName;
    EditText editTextEventHost;
    EditText editTextEventLocation;
    EditText editTextEventDate;
    EditText editTextEventCost;
    EditText editTextEventImage;
    ExecutorService executorService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        buttonCreate = findViewById(R.id.button_event_create);
        buttonCancel = findViewById(R.id.button_event_cancel);

        editTextEventName = findViewById(R.id.editText_event_name);
        editTextEventHost = findViewById(R.id.editText_event_host);
        editTextEventLocation = findViewById(R.id.editText_event_location);
        editTextEventDate = findViewById(R.id.editText_event_date);
        editTextEventCost = findViewById(R.id.editText_event_cost);
        editTextEventImage = findViewById(R.id.editText_event_image);
        executorService = Executors.newSingleThreadExecutor();

        /**
         * Create Event listener
         * Takes all inputted text and fills a JSONObject to send as a POST to backend.
         */
        buttonCreate.setOnClickListener(v -> {
            // Create a new event object
            String eventName = editTextEventName.getText().toString();
            String eventHost = editTextEventHost.getText().toString();
            String eventLocation = editTextEventLocation.getText().toString();
            String eventDate = editTextEventDate.getText().toString();
            int eventCost = Integer.parseInt(editTextEventImage.getText().toString());

            Drawable eventImage;
            // case switch for all image choices 1 through 6, this shit is obnoxiously complicated, need to fix later probs
            switch (editTextEventImage.getText().toString()) {
                case "1":
                    eventImage = getDrawable(R.mipmap.ic_concert1_foreground);
                    break;
                    case "2":
                    eventImage = getDrawable(R.mipmap.ic_concert2_foreground);
                    break;
                    case "3":
                    eventImage = getDrawable(R.mipmap.ic_concert3_foreground);
                    break;
                    case "4":
                    eventImage = getDrawable(R.mipmap.ic_concert4_foreground);
                    break;
                    case "5":
                    eventImage = getDrawable(R.mipmap.ic_concert5_foreground);
                    break;
                    case "6":
                        eventImage = getDrawable(R.mipmap.ic_concert6_foreground);
                    break;
                    default:
                    eventImage = getDrawable(R.mipmap.ic_concert1_foreground);
                    break;
            }

                JSONObject eventJSON = new JSONObject();

                try {
                    eventJSON.put("eventName", eventName);
                    eventJSON.put("eventHost", eventHost);
                    eventJSON.put("eventDate", eventDate);
                    eventJSON.put("eventLocation", eventLocation);
                    eventJSON.put("eventThumbnail", eventImage);
                    eventJSON.put("eventCost", eventCost);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String result = sendRequest("POST", "/events", eventJSON.toString());
                        System.out.println(result);
                    }
                });

                // Go back to the previous activity
                finish();

        });

        /**
         * Cancel button finishes activity
         */
        buttonCancel.setOnClickListener(v -> {
            // Go back to the previous activity
            finish();
        });


    }
}
