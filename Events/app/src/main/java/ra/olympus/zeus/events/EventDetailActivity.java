package ra.olympus.zeus.events;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import ra.olympus.zeus.events.data.models.EventDetail;
import ra.olympus.zeus.events.data.models.UpdateLike;
import ra.olympus.zeus.events.data.remote.EventHubClient;
import ra.olympus.zeus.events.data.remote.MySingleton;
import ra.olympus.zeus.events.data.remote.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity {

    private TextView detailName,detailLocation,detailDescription,detailContact,detailDate,detailTime,detailInterested,detailAttending;
    private ImageView detailImage;
    private FloatingActionButton like_fab;
    Boolean like=false;
    Boolean attending=false;
    private FloatingActionButton attend_fab;
    private FloatingActionButton location_fab;
    int position_id;
    private static final String TAG = "EventDetailActivity";

    String event_url = "http://192.168.43.43:8000/events/1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            ActionBar bar = getSupportActionBar();
            bar.setTitle("GESA Week");
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
        }


        detailName = findViewById(R.id.event_detail_name);
        detailImage = findViewById(R.id.event_detail_flyer);
        detailAttending = findViewById(R.id.number_attending_value_text_view);
        detailContact = findViewById(R.id.event_detail_contact);
        detailDate = findViewById(R.id.event_detail_date);
        detailDescription = findViewById(R.id.event_detail_description);
        detailInterested = findViewById(R.id.number_interested_value_text_view);
        detailTime = findViewById(R.id.event_detail_time);
        detailLocation = findViewById(R.id.event_detail_location);
        like_fab = findViewById(R.id.event_detail_interested_fab);
        attend_fab = findViewById(R.id.event_detail_attending_fab);
        location_fab = findViewById(R.id.event_location_fab);


         /*position_id = Integer.parseInt(getIntent().getStringExtra("position_id"));*/

         SendNetworkRequest();
         //GetRequest();


    }

    public void attend(View view){



        if(attending ==false) {
            attend_fab.setImageResource(R.mipmap.ic_check);
            attending = true;




        }else{
            attend_fab.setImageResource(R.mipmap.ic_checkwhite);
            attending = false;

        }
    }

    public void likeClick(View view){



        if(like ==false) {
            like_fab.setImageResource(R.drawable.ic_favorite_accent);
            like = true;

            UpdateLike like = new UpdateLike();

            String Username = "Goat";
            like.setUsername(Username);
            SendUpdate(like);
            detailInterested.setText("" + 1);
        }else{
            like_fab.setImageResource(R.drawable.ic_favorite_fill);
            like = false;

            UpdateLike unlike = new UpdateLike();

            String Username = "Goat";
            unlike.setUsername(Username);
            SendUnlike(unlike);
        }
    }

    public void locator(View view){
        Intent intent = new Intent(EventDetailActivity.this, MapActivity.class);
        startActivity(intent);

    }

    int id = position_id;



    public void SendNetworkRequest(){
        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
        Call<List<EventDetail>> call = client.gettingEvent(20);
        call.enqueue(new Callback<List<EventDetail>>() {
            @Override
            public void onResponse(Call<List<EventDetail>> call, Response<List<EventDetail>> response) {
                if (response.isSuccessful()){

                    List<EventDetail> body = response.body();


                    assert body != null;
                    detailName.setText(body.get(0).getEventName());
                    Picasso.with(EventDetailActivity.this).load(body.get(0).getMainImage()).into(detailImage);
                    detailDescription.setText(body.get(0).getDescription());
                    detailLocation.setText(body.get(0).getLocationName());
                    detailTime.setText(body.get(0).getEventDate());
                    detailAttending.setText(body.get(0).getAttending().toString());
                    detailInterested.setText(body.get(0).getInterested().toString());
                    detailContact.setText(String.format("%d",body.get(0).getContact()));


                }else{
                    Toast.makeText(EventDetailActivity.this, "coudnt retrive event", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EventDetail>> call, Throwable t) {
                Toast.makeText(EventDetailActivity.this, "could not connect to server", Toast.LENGTH_SHORT).show();

            }
        });




    }

    private void SendUpdate(UpdateLike like){
        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
        Call<ResponseBody> call = client.liking(1,like);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                if(response.code()==200){
                    Toast.makeText(EventDetailActivity.this, "Event Like", Toast.LENGTH_SHORT).show();




                }
                else{
                    //response code is supposed to come from the back end
                    switch (response.code()){

                        case 500:
                            Toast.makeText(EventDetailActivity.this,"Event not created", Toast.LENGTH_SHORT).show();

                        default:
                            Toast.makeText(EventDetailActivity.this, "Server returned error: Unknown error", Toast.LENGTH_SHORT).show();


                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(EventDetailActivity.this, "You have no connection to server", Toast.LENGTH_SHORT).show();

            }
        });




    }

    private void SendUnlike(UpdateLike unlike){
        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
        Call<ResponseBody> call = client.unliking(1,unlike);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                if(response.code()==200){
                    Toast.makeText(EventDetailActivity.this, "Event Unliked", Toast.LENGTH_SHORT).show();




                }
                else{
                    //response code is supposed to come from the back end
                    switch (response.code()){

                        case 500:
                            Toast.makeText(EventDetailActivity.this,"Event not created", Toast.LENGTH_SHORT).show();

                        default:
                            Toast.makeText(EventDetailActivity.this, "Server returned error: Unknown error", Toast.LENGTH_SHORT).show();


                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(EventDetailActivity.this, "You have no connection to server", Toast.LENGTH_SHORT).show();

            }
        });




    }


    private void GetRequest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, event_url,null,
                        new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            /*response = response.getJSONObject("args");*/

                            Log.d(TAG," OnResponse: \n" +
                                    "Name: " + response.getString("EventName") + "\n" +
                                    "Description: " + response.getString("Description") + "\n" +
                                    "Location: " + response.getString("LocationName") + "\n" +
                                    "Time: " + response.getString("EventDate") + "\n" +
                                    "............................");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(EventDetailActivity.this, "Error Somewhere", Toast.LENGTH_SHORT).show();

                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }





}


