package com.example.openingactivity;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements Request {
    private ArrayList<Event> eventList;

    public RecyclerAdapter(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ExecutorService executorService;

        private TextView eventName;
        private TextView eventHost;
        private TextView eventLocation;
        private TextView eventDate;
        private ImageView eventThumbnail;
        private Button joinButton;
        private TextView cost;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.textView_event_name);
            eventHost = itemView.findViewById(R.id.textView_event_host);
            eventLocation = itemView.findViewById(R.id.textView_event_location);
            eventDate = itemView.findViewById(R.id.textView_event_date);
            eventThumbnail = itemView.findViewById(R.id.imageView_event_thumbnail);
            joinButton = itemView.findViewById(R.id.button_event_join);
            cost = itemView.findViewById(R.id.textView_event_price);

            executorService = Executors.newSingleThreadExecutor();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String eventName = eventList.get(position).getEventName();
        String eventHost = eventList.get(position).getEventHost();
        String eventLocation = eventList.get(position).getEventLocation();
        String eventDate = eventList.get(position).getEventDate();
        Drawable eventThumbnail = eventList.get(position).getEventThumbnail();
        Button joinButton = holder.joinButton;
        int cost = eventList.get(position).getEventCost();

        holder.eventName.setText(eventName);
        holder.eventHost.setText(eventHost);
        holder.eventLocation.setText(eventLocation);
        holder.eventDate.setText(eventDate);
        holder.eventThumbnail.setImageDrawable(eventThumbnail);
        holder.cost.setText("$" + String.valueOf(cost));

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make a toast saying which event was clicked
                Toast.makeText(v.getContext(), "You've RSVP'd to " + eventName + "!", Toast.LENGTH_SHORT).show();

                // send a POST request
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject();

                            json.put("userID", user.getUserID());
                            json.put("eventName", eventName);



                            String result = sendRequest("POST", "/events/rsvps", json.toString());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                });

                // TODO add event to RSVP table
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
