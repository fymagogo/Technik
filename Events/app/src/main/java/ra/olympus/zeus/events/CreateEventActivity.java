package ra.olympus.zeus.events;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

import ra.olympus.zeus.events.data.models.CreateEvent;
import ra.olympus.zeus.events.data.remote.APIService;
import ra.olympus.zeus.events.data.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateEventActivity extends AppCompatActivity implements SelectPhotoDialog.OnPhotoSelectedListener{

    //google play services
    private static final String TAG = "CreateEventActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final int REQUEST_CODE = 2;
    private static final int PLACE_PICKER_REQUEST = 67;
    private static final int PLACE_PICKER_RESULT = 5;
    private byte[] mUploadBytes;
    private double mProgress = 0;
    SelectPhotoDialog dialog;

    private DatePickerDialog.OnDateSetListener dateSetter;
    private TimePickerDialog.OnTimeSetListener timeSetter;
    private ImageView eventFlyer;
    private FloatingActionButton changeEventFlyerFab;
    private Bitmap mSelectedBitmap;
    private Uri mSelectedUri;
    private EditText eventName, eventDescription, eventContact;
    private TextView eventTime, eventDate, eventLocation;
    private Button createEvent;
    private ProgressBar mProgressBar;
    private Spinner spinner;
    String locationName=" ";
    double eventLatitude;
    double eventLongitude;
    private APIService mAPIService;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
			.build();
        ImageLoader.getInstance().init(config);


        Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        verifyPermissions();

        if(getSupportActionBar() != null ){
            ActionBar bar = getSupportActionBar();
            bar.setTitle(R.string.text_create_event);
            bar.setDisplayShowHomeEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
        }


        mAPIService = ApiUtils.getAPIService();

        eventFlyer = this.findViewById(R.id.event_flyer);
        changeEventFlyerFab = this.findViewById(R.id.change_event_flyer_fab);
        eventName = this.findViewById(R.id.event_name);
        eventDescription = this.findViewById(R.id.event_description);
        eventContact = this.findViewById(R.id.contact_of_event);
        createEvent = this.findViewById(R.id.create_event_button);
        eventDate = this.findViewById(R.id.set_event_date_text_view);
        eventTime = this.findViewById(R.id.set_event_time_text_view);
        eventLocation = this.findViewById(R.id.enter_map_location);
        spinner = this.findViewById(R.id.events_category_spinner);

        locationName=getIntent().getStringExtra("name");
        if(locationName!=null){
            eventLocation.setText(locationName);}
        eventLatitude=getIntent().getDoubleExtra("latitude",0.00);


        eventLongitude=getIntent().getDoubleExtra("longitude", 0.00);







        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //init();


        eventDate.setOnClickListener(new View.OnClickListener() {
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
                eventDate.setText(date);

            }
        };


         eventTime.setOnClickListener(new View.OnClickListener() {
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
                eventTime.setText(time);
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
     /*   if(isServicesOK()){
            init();
        }*/

        changeEventFlyerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onClick, opening dialog to choose new photo");
                System.out.println("onClick, opening dialog to choose new photo");
                SelectPhotoDialog dialog = new SelectPhotoDialog();
                dialog.show(getSupportFragmentManager(), getString(R.string.dialog_select_photo));
                //dialog.dismiss();

            }
        });

    }

    public void pickerClick(View view){

        startActivity(new Intent(this,MyPlace_Picker.class));
        /*PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(CreateEventActivity.this), PLACE_PICKER_REQUEST);


        } catch (GooglePlayServicesRepairableException e) {
            Log.e(TAG,"onClick: GooglePlayServicesRepairableException " + e.getMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(TAG,"onClick: GooglePlayServicesNotAvailableException " + e.getMessage());
        }*/
    }

    /*public void flyerClick(View view){
        Log.d(TAG,"onClick, opening dialog to choose new photo");
        SelectPhotoDialog dialog = new SelectPhotoDialog();
        dialog.show(getSupportFragmentManager(), getString(R.string.dialog_select_photo));
    }*/

    public void submitClick(View view){
        Log.d(TAG, "onClick: attempting to post...");

        //we have a bitmap and no Uri
        if(mSelectedBitmap != null && mSelectedUri == null){
            uploadNewPhoto(mSelectedBitmap);
        }
        //we have no bitmap and a uri
        else if(mSelectedBitmap == null && mSelectedUri != null){
            uploadNewPhoto(mSelectedUri);
        }
    }


    private void init(){
        TextView enter_map_location = this.findViewById(R.id.enter_map_location);
        enter_map_location.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateEventActivity.this, MapActivity.class);
                startActivity(intent);

                /*PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(CreateEventActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG,"onClick: GooglePlayServicesRepairableException " + e.getMessage());
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG,"onClick: GooglePlayServicesNotAvailableException " + e.getMessage());
                }*/
            }
        });

        /*changeEventFlyerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onClick, opening dialog to choose new photo");
                SelectPhotoDialog dialog = new SelectPhotoDialog();
                dialog.show(getSupportFragmentManager(), getString(R.string.dialog_select_photo));

            }
        });*/

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to post...");

                    //we have a bitmap and no Uri
                    if(mSelectedBitmap != null && mSelectedUri == null){
                        uploadNewPhoto(mSelectedBitmap);
                    }
                    //we have no bitmap and a uri
                    else if(mSelectedBitmap == null && mSelectedUri != null){
                        uploadNewPhoto(mSelectedUri);
                    }
            }
        });
    }

/*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode ==PLA) {

                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());

                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                eventLocation.setText(toastMsg);


            }
        }

    }*/

    private void uploadNewPhoto(Bitmap bitmap){
        Log.d(TAG, "uploadNewPhoto: uploading a new image bitmap to storage");
        BackgroundImageResize resize = new BackgroundImageResize(bitmap);
        Uri uri = null;
        resize.execute(uri);
    }

    private void uploadNewPhoto(Uri imagePath){
        Log.d(TAG, "uploadNewPhoto: uploading a new image uri to storage.");
        BackgroundImageResize resize = new BackgroundImageResize(null);
        resize.execute(imagePath);
    }

    public class BackgroundImageResize extends AsyncTask<Uri, Integer, byte[]> {

        Bitmap mBitmap;

        public BackgroundImageResize(Bitmap bitmap) {
            if(bitmap != null){
                this.mBitmap = bitmap;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(this, "compressing image", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected byte[] doInBackground(Uri... params) {
            Log.d(TAG, "doInBackground: started.");

            if(mBitmap == null){
                try{
                    //RotateBitmap rotateBitmap = new RotateBitmap();
                    //mBitmap = rotateBitmap.HandleSamplingAndRotationBitmap(getActivity(), params[0]);
                    mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),params[0]);
                }catch (IOException e){
                    Log.e(TAG, "doInBackground: IOException: " + e.getMessage());
                }
            }
            byte[] bytes = null;
            bytes = getBytesFromBitmap(mBitmap, 100);
            Log.d(TAG, "doInBackground: megabytes before compression: " + bytes.length / 1000000 );
            return bytes;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
            mUploadBytes = bytes;
            //execute the upload task
            executeUploadTask();
        }
    }

    private void executeUploadTask(){
        Toast.makeText(this, "uploading image", Toast.LENGTH_SHORT).show();


        final String postId = FirebaseDatabase.getInstance().getReference().push().getKey();

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("posts/users/" + postId+ "/post_image");

        UploadTask uploadTask = storageReference.putBytes(mUploadBytes);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(CreateEventActivity.this, "Post Success", Toast.LENGTH_SHORT).show();

                //insert the download url into the firebase database
                Uri firebaseUri = taskSnapshot.getDownloadUrl();

                Log.d(TAG, "onSuccess: firebase download url: " + firebaseUri.toString());

                //sending to server after receiving firebase uri

                String EventName = eventName.getText().toString().trim();
                long CategoryId = spinner.getSelectedItemId();
                String MainImage = firebaseUri.toString().trim();
                String EventDate = eventDate.getText().toString().trim();
                String Description = eventDescription.getText().toString().trim();
                String LocationName = eventLocation.getText().toString().trim();
                double Latitude = eventLatitude;
                double Longitude = eventLongitude;

                /*sendEvent(EventName,CategoryId,MainImage,EventDate,Description,LocationName,Latitude,Longitude);*/
                sendEvent(EventName,CategoryId,MainImage,Description,LocationName,Latitude,Longitude);






            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateEventActivity.this, "could not upload photo", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double currentProgress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                if( currentProgress > (mProgress + 15)){
                    mProgress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    Log.d(TAG, "onProgress: upload is " + mProgress + "& done");
                    //Toast.makeText(this, mProgress + "%", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public static byte[] getBytesFromBitmap(Bitmap bitmap, int quality){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality,stream);
        return stream.toByteArray();
    }


    public  Boolean isServicesOK(){
        Log.d(TAG, "isServicesOK(): checking google services version");
        int available  = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(CreateEventActivity.this);

        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG, "Google Play Services is working");
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServicesOK: an error occurred but we can fix it");
            Dialog dialog  = GoogleApiAvailability.getInstance().getErrorDialog(CreateEventActivity.this, available , ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(this, "We cant connect map requets", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void getImagePath(Uri imagePath) {
        Log.d(TAG, "getImagePath: setting the image to imageview");
        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
        imageLoader.displayImage(imagePath.toString(), eventFlyer);
        //assign to global variable
        mSelectedBitmap = null;
        mSelectedUri = imagePath;
    }

    @Override
    public void getImageBitmsp(Bitmap bitmap) {

        Log.d(TAG, "getImageBitmap: setting the image to imageview");
        eventFlyer.setImageBitmap(bitmap);
        //assign to a global variable
        mSelectedUri = null;
        mSelectedBitmap = bitmap;


    }

    public void sendEvent(String EventName,long CategoryId,String MainImage,String Description,String LocationName,double Latitude,double Longitude){
        mAPIService.createPost(EventName,CategoryId,MainImage,Description,LocationName,Latitude,Longitude).enqueue(new Callback<CreateEvent>() {
            @Override
            public void onResponse(Call<CreateEvent> call, Response<CreateEvent> response) {
                if (response.isSuccessful()) {
                    String respond = response.body().toString();
                    Toast.makeText(CreateEventActivity.this, respond, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CreateEvent> call, Throwable t) {
                Toast.makeText(CreateEventActivity.this, "Unable to create event", Toast.LENGTH_LONG).show();

            }
        });

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void verifyPermissions(){
        Log.d(TAG,"verifyPermissions: asking user for permission");
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED){
            //setupViewPager(viewPager);

        }else{
            ActivityCompat.requestPermissions(CreateEventActivity.this,
                    permissions,
                    REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermissions();
    }




}
