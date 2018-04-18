package ra.olympus.zeus.events;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class MyPlace_Picker extends AppCompatActivity {

    private static final int PLACE_PICKER_REQUEST = 67;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_place_picker);
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(MyPlace_Picker.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode ==RESULT_OK) {

                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("%s", place.getName());
                double lat =  place.getLatLng().latitude;
                double lng = place.getLatLng().longitude;

                Intent intent=new Intent(this,CreateEventActivity.class);
                intent.putExtra("name",toastMsg);
                intent.putExtra("latitude",lat);
                intent.putExtra("longitude",lng);
                startActivity(intent);
                finish();


                //Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                //eventLocation.setText(toastMsg);


            }
        }

    }

}
