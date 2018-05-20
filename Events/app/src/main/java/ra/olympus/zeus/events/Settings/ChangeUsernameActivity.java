package ra.olympus.zeus.events.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

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

        SendNetworkRequest();



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

                SendDetails(userprofile);
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
        Call<UserDetails> call = client.getUserDetails(Username);
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if (response.isSuccessful()){

                    UserDetails body = response.body();


                    assert body != null;

                    currentFirst_name.setText("fishes", TextView.BufferType.EDITABLE);
                    currentLast_name.setText(body.getLastName(),TextView.BufferType.EDITABLE);
                    currentUsername.setText(body.getUserName(),TextView.BufferType.EDITABLE);
                    currentEmail.setText(body.getEmail(),TextView.BufferType.EDITABLE);
                    currentNumber.setText(body.getPhoneNumber(),TextView.BufferType.EDITABLE);


                }else{
                    Toast.makeText(ChangeUsernameActivity.this, "coudnt retrieve details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                Toast.makeText(ChangeUsernameActivity.this, "could not connect to server", Toast.LENGTH_SHORT).show();

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

                }



            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(ChangeUsernameActivity.this,"You have no connection", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
