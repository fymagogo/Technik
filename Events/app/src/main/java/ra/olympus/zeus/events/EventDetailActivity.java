package ra.olympus.zeus.events;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

import ra.olympus.zeus.events.data.models.CreateEvent;
import ra.olympus.zeus.events.data.models.EventDetail;
import ra.olympus.zeus.events.data.remote.EventHubClient;
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
    int position_id;


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
        detailDescription = findViewById(R.id.event_description);
        detailInterested = findViewById(R.id.number_interested_value_text_view);
        detailTime = findViewById(R.id.event_detail_time);
        detailLocation = findViewById(R.id.event_detail_location);
        like_fab = findViewById(R.id.event_detail_interested_fab);
        attend_fab = findViewById(R.id.event_detail_attending_fab);


         /*position_id = Integer.parseInt(getIntent().getStringExtra("position_id"));*/

         SendNetworkRequest();


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
        }else{
            like_fab.setImageResource(R.drawable.ic_favorite_fill);
            like = false;
        }
    }

    public void locator(View view){
        Intent intent = new Intent(EventDetailActivity.this, MapActivity.class);
        startActivity(intent);

    }

    int id = position_id;



    private void SendNetworkRequest(){
        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
        Call<JSONArray> call = client.gettingEvent(1);
        call.enqueue(new Callback<JSONArray>() {
            @Override
            public void onResponse(Call<JSONArray> call, Response<JSONArray> response) {
                if (response.isSuccessful()){
                    String json = String.valueOf(response.body());
                    Type type = new TypeToken<List<EventDetail>>(){}.getType();
                    List<EventDetail> rvents = new Gson().fromJson(json, type);


;
                    EventDetail body = rvents.get(0);
                    assert body != null;
                    detailName.setText(body.getEventName());
                    Picasso.with(EventDetailActivity.this).load(body.getMainImage()).into(detailImage);
                    detailDescription.setText(body.getDescription());
                    detailLocation.setText(body.getLocationName());
                    detailTime.setText(body.getEventDate());



                }else{
                    Toast.makeText(EventDetailActivity.this, "coudnt retrive event", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JSONArray> call, Throwable t) {
                Toast.makeText(EventDetailActivity.this, "could not connect to server", Toast.LENGTH_SHORT).show();

            }
        });




    }




}


