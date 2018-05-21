package ra.olympus.zeus.events.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import ra.olympus.zeus.events.R;
import ra.olympus.zeus.events.UserDetails;
import ra.olympus.zeus.events.data.models.UserProfileDetails;
import ra.olympus.zeus.events.data.remote.EventHubClient;
import ra.olympus.zeus.events.data.remote.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alfre on 12/05/2018.
 */

public class ChangeUsernameActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    private EditText currentFirst_name, currentLast_name ,currentUsername, currentEmail, currentNumber;
    private Button submit_edit_profile;
    String Username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_username);

        Toolbar toolbar = findViewById(R.id.change_profile_details_toolbar);
        toolbar.setTitle("User Profile");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);

        setUpActionBar();


        sharedPref = getSharedPreferences("EVENTHUB_SHAREDPREF_SIGNIN", Context.MODE_PRIVATE);
        Username = sharedPref.getString("Username",Username);

        currentFirst_name = findViewById(R.id.current_first_name);
        currentLast_name = findViewById(R.id.current_last_name);
        currentUsername = findViewById(R.id.current_username);
        currentEmail = findViewById(R.id.current_email);
        currentNumber = findViewById(R.id.current_number);
        submit_edit_profile = findViewById(R.id.submit_edit_profile);

        //currentFirst_name.setText("This is stupid");

       SendNetworkRequest();
        //   newFunction();



        submit_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfileDetails userprofile = new UserProfileDetails();

                userprofile.setFirstName(currentFirst_name.getText().toString());
                userprofile.setLastName(currentLast_name.getText().toString());
                userprofile.setUserName(currentUsername.getText().toString());
                userprofile.setEmail(currentEmail.getText().toString());
                userprofile.setPhoneNumber(currentNumber.getText().toString());
                //Place username authentication here

                /*SendDetails(userprofile);*/

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("FirstName",currentFirst_name.getText().toString());
                    jsonObject.put("LastName",currentLast_name.getText().toString());
                    jsonObject.put("UserName",currentUsername.getText().toString());
                    jsonObject.put("Email",currentEmail.getText().toString());
                    jsonObject.put("PhoneNumber",currentNumber.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                newFunction(jsonObject);
            }





        });

    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



    }

    public void SendNetworkRequest(){
        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
        Call<List<UserDetails>> call = client.getUserDetails(Username);
        call.enqueue(new Callback<List<UserDetails>>() {
            @Override
            public void onResponse(Call<List<UserDetails>> call, Response<List<UserDetails>> response) {
                if (response.isSuccessful()){

                    List<UserDetails> body = response.body();


                    assert body != null;
                   // Toast.makeText(ChangeUsernameActivity.this, String.valueOf(body.get(0).getFirstName().toString()),Toast.LENGTH_SHORT).show();

                    currentFirst_name.setText("fishes", TextView.BufferType.EDITABLE);
//                    currentLast_name.setText(body.get(0).getLastName().toString(),TextView.BufferType.EDITABLE);
                    currentUsername.setText(body.get(0).getUserName(),TextView.BufferType.EDITABLE);
                    currentEmail.setText(body.get(0).getEmail(),TextView.BufferType.EDITABLE);
                    currentNumber.setText(body.get(0).getPhoneNumber(),TextView.BufferType.EDITABLE);


                }else{
                    Toast.makeText(ChangeUsernameActivity.this, "coudnt retrieve details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserDetails>> call, Throwable t) {
                Toast.makeText(ChangeUsernameActivity.this, "could not connect to server", Toast.LENGTH_SHORT).show();
                Log.e(
                        "TAG", String.valueOf(t.getMessage())
                );

            }
        });


    }

    private void SendDetails(final UserProfileDetails userprofile){
        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
        Call<ResponseBody> call = client.sendProfileDetails(Username,userprofile);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    SharedPreferences sharedPref;

                    sharedPref = getSharedPreferences("EVENTHUB_SHAREDPREF_SIGNIN", Context.MODE_PRIVATE);
                    sharedPref.edit().putString("Username",userprofile.getUserName());
                    sharedPref.edit().apply();

                    Toast.makeText(ChangeUsernameActivity.this,"Success", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(ChangeUsernameActivity.this,"You have no connection", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void newFunction(JSONObject jsonObject){
        OkHttpClient client = new OkHttpClient();
        Log.d("JsonObject", String.valueOf(jsonObject));

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, String.valueOf(jsonObject));
        Request request = new Request.Builder()
                .url("http://eventhubbackend.azurewebsites.net/settings/edit-profile/"+Username+"")
                .put(body)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "61fd661e-be6e-5a15-2f97-b6049934edf0")
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

                Log.d("Edit","failed");
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {


                Looper.prepare();
                Toast.makeText(ChangeUsernameActivity.this,"Success",Toast.LENGTH_SHORT).show();
                Log.d("Edit","Successfully");
            }
        });

    }

}
