package ra.olympus.zeus.events;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import ra.olympus.zeus.events.data.models.CreateEvent;
import ra.olympus.zeus.events.data.models.EditEvent;
import ra.olympus.zeus.events.data.models.EventDetail;
import ra.olympus.zeus.events.data.remote.EventHubClient;
import ra.olympus.zeus.events.data.remote.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditEventActivity extends AppCompatActivity {

    //google play services
    private static final String TAG = "EditEventActivity";
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
    private FloatingActionButton editEventFlyerFab;
    private Bitmap mSelectedBitmap;
    private Uri mSelectedUri;
    private EditText eventName, eventDescription;
    private TextView eventTime, eventDate, eventLocation;
    private Button editEvent;
    private Spinner spinner;
    double eventLatitude;
    double eventLongitude;
    private ProgressDialog progressDialog;
    private String Username;
    private int event_id;
    private String initialImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);


        Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            ActionBar bar = getSupportActionBar();
            bar.setDisplayShowHomeEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("EVENTHUB_SHAREDPREF_SIGNIN", Context.MODE_PRIVATE);
        Username = sharedPref.getString("Username",Username);

        Bundle id = getIntent().getExtras();
        event_id = id.getInt("EventId");

        GetEvent();


        eventFlyer = this.findViewById(R.id.edit_event_flyer);
        editEventFlyerFab = this.findViewById(R.id.edit_event_flyer_fab);
        eventName = this.findViewById(R.id.edit_event_name);
        eventDescription = this.findViewById(R.id.edit_event_description);
        editEvent = this.findViewById(R.id.edit_event_button);
        eventDate = this.findViewById(R.id.edit_set_event_date_text_view);
        eventTime = this.findViewById(R.id.edit_set_event_time_text_view);
        eventLocation = this.findViewById(R.id.edit_enter_map_location);
        spinner = this.findViewById(R.id.edit_events_category_spinner);


        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //init();


        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditEventActivity.this, dateSetter, year, month, day);
                //datePickerDialog.getWindow();
                datePickerDialog.show();


                //Toast.makeText(CreateEventActivity.this, "Set Date", Toast.LENGTH_SHORT).show();
                //TODO: Make a date picker to pick event date
            }
        });

        dateSetter = new DatePickerDialog.OnDateSetListener() {
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

                TimePickerDialog timePickerDialog = new TimePickerDialog(EditEventActivity.this, timeSetter, hour, min, true);
                timePickerDialog.show();

            }
        });

        timeSetter = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {

                String time = hour + ":" + min;
                eventTime.setText(time);
            }
        };

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplication(), R.array.category_types, R.layout.spinner_text_view);


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

        editEventFlyerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View alertImageDialog = LayoutInflater.from(EditEventActivity.this).inflate(R.layout.dialog_select_photo, null);
                TextView GalleryImage = alertImageDialog.findViewById(R.id.GalleryImage);

                AlertDialog.Builder builder = new AlertDialog.Builder(EditEventActivity.this);
                builder.setView(alertImageDialog);

                final Dialog create = builder.create();
                create.show();

                GalleryImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        create.dismiss();
                        Log.d(TAG, "accessing phone's gallery");
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, PICKFILE_REQUEST_CODE);
                    }
                });

                TextView OpenCamera = alertImageDialog.findViewById(R.id.OpenCamera);
                OpenCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        create.dismiss();
                        Log.d(TAG, "starting camera");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_REQUEST_CODE);
                    }
                });

            }
        });

        editEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = eventName.getText().toString().trim();
                String date = eventDate.getText().toString().trim();
                String time = eventTime.getText().toString().trim();
                String description = eventDescription.getText().toString().trim();
                String location = eventLocation.getText().toString().trim();
                long category = spinner.getSelectedItemId();


                if (name.isEmpty()){eventName.setError("Enter Name");
                }else if (description.isEmpty()){eventDescription.setError("Enter Description");
                }else if (location.isEmpty()) {eventLocation.setError("Select Location");
                }else if (date.isEmpty()){eventDate.setError("Enter Date");
                }else if (time.isEmpty()){eventTime.setError("Enter Time");
                }else if (category == 0){
                    Toast.makeText(EditEventActivity.this,"Select a Category",Toast.LENGTH_SHORT).show();
                }else {
                        //we have a bitmap and no Uri
                        if (mSelectedBitmap != null && mSelectedUri == null) {
                            uploadNewPhoto(mSelectedBitmap);
                        }
                        //we have no bitmap and a uri
                        else if (mSelectedBitmap == null && mSelectedUri != null) {
                            uploadNewPhoto(mSelectedUri);
                        }else{
                            AlternateRoute();
                        }
                    }


            }

        });

    }

    public void pickerClick(View view) {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(EditEventActivity.this), PLACE_PICKER_REQUEST);


        } catch (GooglePlayServicesRepairableException e) {
            Log.e(TAG, "onClick: GooglePlayServicesRepairableException " + e.getMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(TAG, "onClick: GooglePlayServicesNotAvailableException " + e.getMessage());
        }
    }


    private void init() {


    }


    private void uploadNewPhoto(Bitmap bitmap) {
        Log.d(TAG, "uploadNewPhoto: uploading a new image bitmap to storage");
        BackgroundImageResize resize = new BackgroundImageResize(bitmap);
        Uri uri = null;
        resize.execute(uri);
    }

    private void uploadNewPhoto(Uri imagePath) {
        Log.d(TAG, "uploadNewPhoto: uploading a new image uri to storage.");
        BackgroundImageResize resize = new BackgroundImageResize(null);
        resize.execute(imagePath);
    }


    public class BackgroundImageResize extends AsyncTask<Uri, Integer, byte[]> {

        Bitmap mBitmap;

        public BackgroundImageResize(Bitmap bitmap) {
            if (bitmap != null) {
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

            if (mBitmap == null) {
                try {
                    mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), params[0]);
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: IOException: " + e.getMessage());
                }
            }
            byte[] bytes = null;
            bytes = getBytesFromBitmap(mBitmap, 100);
            Log.d(TAG, "doInBackground: megabytes before compression: " + bytes.length / 1000000);
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

    private void executeUploadTask() {

        progressDialog = new ProgressDialog(EditEventActivity.this);
        progressDialog.setMessage("Editing..."); // Setting Message
        progressDialog.setTitle("Edit Event"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Horizontal
        progressDialog.show(); // Display Progress Dialog
        Log.d(TAG, "onClick: attempting to post...");


        Toast.makeText(this, "Uploading image", Toast.LENGTH_SHORT).show();


        final String postId = FirebaseDatabase.getInstance().getReference().push().getKey();

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("posts/users/" + postId + "/post_image");

        UploadTask uploadTask = storageReference.putBytes(mUploadBytes);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(EditEventActivity.this, "Image Upload Successful", Toast.LENGTH_SHORT).show();

                //insert the download url into the firebase database
                Uri firebaseUri = taskSnapshot.getDownloadUrl();

                Log.d(TAG, "onSuccess: firebase download url: " + firebaseUri.toString());

                //sending to server after receiving firebase uri

                String EventName = eventName.getText().toString().trim();
                long CategoryId = spinner.getSelectedItemId();
                String MainImage = firebaseUri.toString().trim();
                String EventDate = eventDate.getText().toString().trim() + " " + eventTime.getText().toString().trim();
                String Description = eventDescription.getText().toString().trim();
                String LocationName = eventLocation.getText().toString().trim();
                double Latitude = eventLatitude;
                double Longitude = eventLongitude;


                /*EditEvent editEvent = new EditEvent();

                editEvent.setEventName(EventName);
                editEvent.setCategoryId(CategoryId);
                editEvent.setMainImage(MainImage);
                editEvent.setEventDate(EventDate);
                editEvent.setDescription(Description);
                editEvent.setLocationName(LocationName);
                editEvent.setLatitude(Latitude);
                editEvent.setLongitude(Longitude);*/

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("EventName",EventName);
                    jsonObject.put("CategoryId",CategoryId);
                    jsonObject.put("MainImage",MainImage);
                    jsonObject.put("EventDate",EventDate);
                    jsonObject.put("Username",Username);
                    jsonObject.put("Description",Description);
                    jsonObject.put("LocationName",LocationName);
                    jsonObject.put("Latitude",Latitude);
                    jsonObject.put("Longitude",Longitude);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                for (int i = 0; i < 1; i++) {

                    /*SendNetworkRequest(editEvent);*/

                    newFunction(jsonObject);
                }



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditEventActivity.this, "could not upload photo", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double currentProgress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                if (currentProgress > (mProgress + 15)) {
                    mProgress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    Log.d(TAG, "onProgress: upload is " + mProgress + "& done");

                }
            }
        });
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return stream.toByteArray();
    }


    private void SendNetworkRequest(EditEvent editEvent) {
        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
        Call<ResponseBody> call = client.editingEvent(Username,event_id,editEvent);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressDialog.dismiss();

                if (response.code() == 200) {
                    Toast.makeText(EditEventActivity.this, "Event Edited", Toast.LENGTH_SHORT).show();
                    Intent MainActivityIntent = new Intent(getApplicationContext(),MainActivity.class);
                    MainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


                } else {
                    //response code is supposed to come from the back end
                    switch (response.code()) {

                        case 500:
                            Toast.makeText(EditEventActivity.this, "Event not edited", Toast.LENGTH_SHORT).show();

                        default:
                            Toast.makeText(EditEventActivity.this, "Server returned error: Unknown error", Toast.LENGTH_SHORT).show();


                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(EditEventActivity.this, "You have no connection to server", Toast.LENGTH_SHORT).show();

            }
        });


    }


    public Boolean isServicesOK() {
        Log.d(TAG, "isServicesOK(): checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(EditEventActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServicesOK: an error occurred but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(EditEventActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "We cant connect map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Results when selecting new image from gallery
        if (requestCode == PICKFILE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                mSelectedUri = selectedImageUri;
                Log.d(TAG, "onActivityResult: " + selectedImageUri);

                eventFlyer.setImageURI(selectedImageUri);

            }

        }

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "onActivityResult: DOne taking new photo");
            Bitmap bitmap;
            bitmap = (Bitmap) data.getExtras().get("data");
            mSelectedBitmap = bitmap;
            eventFlyer.setImageBitmap(bitmap);

        }


        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("%s", place.getName());
                eventLatitude = place.getLatLng().latitude;
                eventLongitude = place.getLatLng().longitude;

                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                eventLocation.setText(toastMsg);


            }
        }


    }

    public void GetEvent() {
        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
        Call<List<EventDetail>> call = client.gettingEvent(event_id);
        call.enqueue(new Callback<List<EventDetail>>() {
            @Override
            public void onResponse(Call<List<EventDetail>> call, Response<List<EventDetail>> response) {
                if (response.isSuccessful()) {

                    List<EventDetail> body = response.body();

                    Picasso mPicasso = Picasso.with(EditEventActivity.this);

                    assert body != null;
                    eventName.setText(body.get(0).getEventName());
                    mPicasso.load(body.get(0).getMainImage()).into(eventFlyer);
                    eventDescription.setText(body.get(0).getDescription());
                    eventLocation.setText(body.get(0).getLocationName());
                    eventTime.setText(body.get(0).getEventDate().substring(11, 16));
                    eventDate.setText(body.get(0).getEventDate().substring(0, 10));

                    eventLongitude = body.get(0).getLongitude();
                    eventLatitude = body.get(0).getLatitude();

                    String title = body.get(0).getEventName();
                    setTitle("Edit " + title);
                    initialImage = body.get(0).getMainImage();


                } else {
                    Toast.makeText(EditEventActivity.this, "coudnt retrive event", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EventDetail>> call, Throwable t) {
                Toast.makeText(EditEventActivity.this, "could not connect to server", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void AlternateRoute(){
        String EventName = eventName.getText().toString().trim();
        long CategoryId = spinner.getSelectedItemId();
        String MainImage = initialImage.trim();
        String EventDate = eventDate.getText().toString().trim() + " " + eventTime.getText().toString().trim();
        String Description = eventDescription.getText().toString().trim();
        String LocationName = eventLocation.getText().toString().trim();
        double Latitude = eventLatitude;
        double Longitude = eventLongitude;



        /*EditEvent editEvent = new EditEvent();

        editEvent.setEventName(EventName);
        editEvent.setCategoryId(CategoryId);
        editEvent.setMainImage(MainImage);
        editEvent.setEventDate(EventDate);
        editEvent.setDescription(Description);
        editEvent.setLocationName(LocationName);
        editEvent.setLatitude(Latitude);
        editEvent.setLongitude(Longitude);*/

        for (int i = 0; i < 1; i++) {
            progressDialog = new ProgressDialog(EditEventActivity.this);
            progressDialog.setMessage("Editing..."); // Setting Message
            progressDialog.setTitle("Edit Event"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Horizontal
            progressDialog.show(); // Display Progress Dialog

           /* SendNetworkRequest(editEvent);*/

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("EventName",EventName);
                jsonObject.put("CategoryId",CategoryId);
                jsonObject.put("MainImage",MainImage);
                jsonObject.put("EventDate",EventDate);
                jsonObject.put("Username",Username);
                jsonObject.put("Description",Description);
                jsonObject.put("LocationName",LocationName);
                jsonObject.put("Latitude",Latitude);
                jsonObject.put("Longitude",Longitude);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            newFunction(jsonObject);
        }

    }

    public void newFunction(JSONObject jsonObject){
        OkHttpClient client = new OkHttpClient();
        Log.d("JsonObject", String.valueOf(jsonObject));

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, String.valueOf(jsonObject));
        Request request = new Request.Builder()
                .url("http://eventhubbackend.azurewebsites.net/my-events/"+Username+"/"+event_id+"/edit")
                .put(body)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "61fd661e-be6e-5a15-2f97-b6049934edf0")
                .build();

       client.newCall(request).enqueue(new okhttp3.Callback() {
           @Override
           public void onFailure(okhttp3.Call call, IOException e) {
               progressDialog.dismiss();
           }

           @Override
           public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

               progressDialog.dismiss();
               Looper.prepare();
               Toast.makeText(EditEventActivity.this,"Success",Toast.LENGTH_SHORT).show();
           }
       });

    }

}
