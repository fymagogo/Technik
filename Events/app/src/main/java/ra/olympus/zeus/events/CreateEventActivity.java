package ra.olympus.zeus.events;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.LayoutInflater;
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

import okhttp3.ResponseBody;

import ra.olympus.zeus.events.data.models.CreateEvent;
import ra.olympus.zeus.events.data.remote.EventHubClient;
import ra.olympus.zeus.events.data.remote.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateEventActivity extends AppCompatActivity {

    //google play services
    private static final String TAG = "CreateEventActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final int REQUEST_CODE = 2;
    private static final int PLACE_PICKER_REQUEST = 67;
    private static final int PLACE_PICKER_RESULT = 5;
    private byte[] mUploadBytes;
    private double mProgress = 0;

    private static final int PICKFILE_REQUEST_CODE = 1234;
    private static final int CAMERA_REQUEST_CODE = 4321;

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
        //verifyPermissions();

        if(getSupportActionBar() != null ){
            ActionBar bar = getSupportActionBar();
            bar.setTitle(R.string.text_create_event);
            bar.setDisplayShowHomeEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
        }



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
                String date = year + "-" + month + "-" + day;
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
       /* if(isServicesOK()){
            init();
        }*/

        changeEventFlyerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View alertImageDialog = LayoutInflater.from(CreateEventActivity.this).inflate(R.layout.dialog_select_photo, null);
                TextView GalleryImage = alertImageDialog.findViewById(R.id.GalleryImage);

                AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
                builder.setView(alertImageDialog);

                final Dialog create =  builder.create();
                create.show();

                GalleryImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        create.dismiss();
                        Log.d(TAG,"accessing phone's gallery");
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, PICKFILE_REQUEST_CODE );
                    }
                });

                TextView OpenCamera =  alertImageDialog.findViewById(R.id.OpenCamera);
                OpenCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        create.dismiss();
                        Log.d(TAG,"starting camera");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_REQUEST_CODE );
                    }
                });

            }
        });

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

    public void pickerClick(View view){

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(CreateEventActivity.this), PLACE_PICKER_REQUEST);


        } catch (GooglePlayServicesRepairableException e) {
            Log.e(TAG,"onClick: GooglePlayServicesRepairableException " + e.getMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(TAG,"onClick: GooglePlayServicesNotAvailableException " + e.getMessage());
        }
    }




    private void init(){


    }




        private void uploadNewPhoto (Bitmap bitmap){
            Log.d(TAG, "uploadNewPhoto: uploading a new image bitmap to storage");
            BackgroundImageResize resize = new BackgroundImageResize(bitmap);
            Uri uri = null;
            resize.execute(uri);
        }

        private void uploadNewPhoto (Uri imagePath){
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
        Toast.makeText(this, "Uploading image", Toast.LENGTH_SHORT).show();


        final String postId = FirebaseDatabase.getInstance().getReference().push().getKey();

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("posts/users/" + postId+ "/post_image");

        UploadTask uploadTask = storageReference.putBytes(mUploadBytes);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(CreateEventActivity.this, "Image Upload Successful", Toast.LENGTH_SHORT).show();

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
                String Username = "Goat";

                CreateEvent createEvent = new CreateEvent();

                createEvent.setEventName(EventName);
                createEvent.setCategoryId(CategoryId);
                createEvent.setMainImage(MainImage);
                createEvent.setEventDate(EventDate);
                createEvent.setUsername(Username);
                createEvent.setDescription(Description);
                createEvent.setLocationName(LocationName);
                createEvent.setLatitude(Latitude);
                createEvent.setLongitude(Longitude);

                for (int i = 0;i < 1 ; i++) {

                    SendNetworkRequest(createEvent);
                }


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

                }
            }
        });
    }
    public static byte[] getBytesFromBitmap(Bitmap bitmap, int quality){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality,stream);
        return stream.toByteArray();
    }


    private void SendNetworkRequest(CreateEvent createEvent){
        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
        Call<ResponseBody> call = client.creatingEvent(createEvent);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                /*//we have a bitmap and no Uri
                if(mSelectedBitmap != null && mSelectedUri == null){
                    uploadNewPhoto(mSelectedBitmap);
                }
                //we have no bitmap and a uri
                else if(mSelectedBitmap == null && mSelectedUri != null){
                    uploadNewPhoto(mSelectedUri);
                }*/

                if(response.code()==200){
                    Toast.makeText(CreateEventActivity.this, "Event Created", Toast.LENGTH_SHORT).show();




                }
                else{
                    //response code is supposed to come from the back end
                    switch (response.code()){

                        case 500:
                            Toast.makeText(CreateEventActivity.this,"Event not created", Toast.LENGTH_SHORT).show();

                        default:
                            Toast.makeText(CreateEventActivity.this, "Server returned error: Unknown error", Toast.LENGTH_SHORT).show();


                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(CreateEventActivity.this, "You have no connection to server", Toast.LENGTH_SHORT).show();

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
            Log.d(TAG, "isServicesOK: an error occurred but we can fix it");
            Dialog dialog  = GoogleApiAvailability.getInstance().getErrorDialog(CreateEventActivity.this, available , ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(this, "We cant connect map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }




   /* @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void verifyPermissions(){
        Log.d(TAG,"verifyPermissions: asking user for permission");
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED){

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
*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Results when selecting new image from gallery
        if (requestCode == PICKFILE_REQUEST_CODE ){
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                mSelectedUri = selectedImageUri;
                Log.d(TAG,"onActivityResult: " + selectedImageUri);

                eventFlyer.setImageURI(selectedImageUri);

            }

        }

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Log.d(TAG,"onActivityResult: DOne taking new photo");
            Bitmap bitmap;
            bitmap = (Bitmap) data.getExtras().get("data");
            mSelectedBitmap = bitmap;
            eventFlyer.setImageBitmap(bitmap);

        }


        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode ==RESULT_OK) {

                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("%s", place.getName());
                eventLatitude = place.getLatLng().latitude;
                eventLongitude = place.getLatLng().longitude;

                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                eventLocation.setText(toastMsg);


            }
        }
    }




}
