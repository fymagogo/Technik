package ra.olympus.zeus.events;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.StringTokenizer;

import okhttp3.ResponseBody;
import ra.olympus.zeus.events.data.models.EventDetail;
import ra.olympus.zeus.events.data.models.Update;
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

    int likes;
    String title;
    String location;
    String phone;
    Double lng,lat;


    public static final String Like = "likeKey";
    public static final String Attend = "attendKey";
    public static final String User = "usernameKey";
    private String Username;
    private int event_id;
    SharedPreferences sharedPref;
    String liker;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            ActionBar bar = getSupportActionBar();
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
        }


        sharedPref = getSharedPreferences("EVENTHUB_SHAREDPREF_SIGNIN", Context.MODE_PRIVATE);
        Username = sharedPref.getString("Username",Username);

        Bundle id = getIntent().getExtras();
        event_id = id.getInt("EventId");





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

        like = sharedPref.getBoolean(Like,false);
        if (like) {
            like_fab.setImageResource(R.drawable.ic_favorite_accent);
        }

        attending = sharedPref.getBoolean(Attend,false);
        if (attending) {
            attend_fab.setImageResource(R.mipmap.ic_check);
        }


    }

    public void attend(View view){



        SharedPreferences.Editor editor = sharedPref.edit();

        if(!attending) {

            attend_fab.setImageResource(R.mipmap.ic_check);
            attending = true;
            editor.putBoolean(Attend,true);
            editor.apply();

            Update attend = new Update();

            attend.setUsername(Username);
            SendAttend(attend);




        }else{
            attend_fab.setImageResource(R.mipmap.ic_checkwhite);
            attending = false;
            editor.putBoolean(Attend,false);
            editor.apply();

            Update unattend = new Update();
            unattend.setUsername(Username);
            SendUnattend(unattend);

        }
    }



    public void likeClick(View view){

        SharedPreferences.Editor editor = sharedPref.edit();



        if(!like) {

            like_fab.setImageResource(R.drawable.ic_favorite_accent);
            like = true;
            /*detailInterested.setText(likes + 1);*/
            editor.putBoolean(Like,true);
            editor.putString(User, Username);
            editor.apply();

            Update like = new Update();


            like.setUsername(Username);
            SendLike(like);
            /*detailInterested.setText(liker + 1);*/
        }else{
            like_fab.setImageResource(R.drawable.ic_favorite_fill);
            like = false;
            editor.putBoolean(Like,false);
            editor.apply();

            Update unlike = new Update();

            unlike.setUsername(Username);
            SendUnlike(unlike);

            /*detailInterested.setText(likes - 1);*/
        }
    }

    public void locator(View view){
        Intent intent = new Intent(EventDetailActivity.this, MapActivity.class);
        intent.putExtra("latitude", lat);
        intent.putExtra("longitude", lng);
        intent.putExtra("name", location);
        startActivity(intent);

    }

    public void calling(View view){


        Intent call = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",0 + phone, null));
        startActivity(call);

    }

    int id = position_id;



    public void SendNetworkRequest(){
        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
        Call<List<EventDetail>> call = client.gettingEvent(event_id);
        call.enqueue(new Callback<List<EventDetail>>() {
            @Override
            public void onResponse(Call<List<EventDetail>> call, Response<List<EventDetail>> response) {
                if (response.isSuccessful()){

                    List<EventDetail> body = response.body();

                    Picasso mPicasso = Picasso.with(EventDetailActivity.this);

                    assert body != null;
                    detailName.setText(body.get(0).getEventName());
                    mPicasso.load(body.get(0).getMainImage()).into(detailImage);
                    detailDescription.setText(body.get(0).getDescription());
                    detailLocation.setText(body.get(0).getLocationName());
                    detailTime.setText(body.get(0).getEventDate().substring(11,16));
                    detailDate.setText(body.get(0).getEventDate().substring(0,10));
                    detailAttending.setText(body.get(0).getAttending().toString());
                    detailInterested.setText(body.get(0).getInterested().toString());
                    detailContact.setText(0 + String.format("%d",body.get(0).getContact()));

                    likes = body.get(0).getInterested();
                    liker = body.get(0).getInterested().toString();
                    title = body.get(0).getEventName();
                    location = body.get(0).getLocationName();
                    phone = String.format("%d",body.get(0).getContact());
                    lng = body.get(0).getLongitude();
                    lat = body.get(0).getLatitude();
                    setTitle(title);


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

    private void SendLike(Update like){
        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
        Call<ResponseBody> call = client.liking(event_id,like);

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

    private void SendUnlike(Update unlike){
        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
        Call<ResponseBody> call = client.unliking(event_id,unlike);

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

    private void SendAttend(Update attend){
        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
        Call<ResponseBody> call = client.attending(event_id,attend);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                if(response.code()==200){
                    Toast.makeText(EventDetailActivity.this, "Event Attended", Toast.LENGTH_SHORT).show();




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

    private void SendUnattend(Update unattend){
        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
        Call<ResponseBody> call = client.unattending(event_id,unattend);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                if(response.code()==200){
                    Toast.makeText(EventDetailActivity.this, "Event Unattended", Toast.LENGTH_SHORT).show();




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

}


