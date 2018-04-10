package ra.olympus.zeus.events;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.ImageReader;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageSwitcher;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateEventActivity extends AppCompatActivity {

    //google play services
    private static final String TAG = "CreateEventActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private DatePickerDialog.OnDateSetListener dateSetter;
    private TimePickerDialog.OnTimeSetListener timeSetter;
    private CircleImageView eventFlyer;
    private FloatingActionButton change_image;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);


        Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null ){
            ActionBar bar = getSupportActionBar();
            bar.setTitle(R.string.text_create_event);
            bar.setDisplayShowHomeEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
        }

        CircleImageView eventFlyer = this.findViewById(R.id.image_of_user);
        FloatingActionButton chnage_image = this.findViewById(R.id.change_user_image_fab);



        final TextView setEventDate = this.findViewById(R.id.set_event_date_text_view);
        setEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog= new DatePickerDialog(CreateEventActivity.this,dateSetter,year,month,day);
                //datePickerDialog.getWindow();
                datePickerDialog.show();



                //Toast.makeText(CreateEventActivity.this, "Set Date", Toast.LENGTH_SHORT).show();
                //TODO: Make a date picker to pick event date
            }
        });

        dateSetter = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                setEventDate.setText(date);

            }
        };

        final TextView setEventTime = this.findViewById(R.id.set_event_time_text_view);
        setEventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEventActivity.this,timeSetter, hour, min, true);
                timePickerDialog.show();


                //Toast.makeText(CreateEventActivity.this, "Set Time", Toast.LENGTH_SHORT).show();
                //TODO: Make a time picker to pick event time
            }
        });

        timeSetter = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {

                String time = hour + ":" + min;
                setEventTime.setText(time);
            }
        };

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplication(),R.array.category_types,R.layout.spinner_text_view);
        Spinner spinner = this.findViewById(R.id.events_category_spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(adapter);
        if(isServicesOK()){
            init();
        }
    }



    private void init(){
        TextView enter_map_location = this.findViewById(R.id.enter_map_location);
        enter_map_location.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateEventActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        change_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onClick, opening dialog to choose new photo");
                SelectPhotoDialog dialog = new SelectPhotoDialog();
                dialog.show(getFragmentManager(), getString(R.string.GalleryImage));
            }
        });
    }


    public  Boolean isServicesOK(){
        Log.d(TAG, "isServicesOK(): checking google services version");
        int available  = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(CreateEventActivity.this);

        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG, "Google Play Services is working");
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog  = GoogleApiAvailability.getInstance().getErrorDialog(CreateEventActivity.this, available , ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(this, "We cant connect map requets", Toast.LENGTH_SHORT).show();;
        }
        return false;
    }
}
