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

import ra.olympus.zeus.events.data.models.EventDetail;

public class EventDetailActivity extends AppCompatActivity {

    private TextView detailName,detailLocation,detailDescription,detailContact,detailDate,detailTime,detailInterested,detailAttending;
    private ImageView detailImage;
    private FloatingActionButton like_fab;
    Boolean like=false;
    Boolean attending=false;
    private FloatingActionButton attend_fab;


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


    }

    public void likeClick(View view){



        if(like ==false) {
            attend_fab.setImageResource(R.mipmap.ic_check);
            like = true;
        }else{
            attend_fab.setImageResource(R.mipmap.ic_checkwhite);
            like = false;
        }
    }

    public void attend(View view){



        if(attending ==false) {
            like_fab.setImageResource(R.drawable.ic_favorite_accent);
            attending = true;
        }else{
            like_fab.setImageResource(R.drawable.ic_favorite_fill);
            attending = false;
        }
    }

    public void locator(View view){
        Intent intent = new Intent(EventDetailActivity.this, MapActivity.class);
        startActivity(intent);

    }



}


