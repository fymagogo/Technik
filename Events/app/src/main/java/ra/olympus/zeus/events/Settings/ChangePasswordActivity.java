package ra.olympus.zeus.events.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import okhttp3.ResponseBody;
import ra.olympus.zeus.events.R;
import ra.olympus.zeus.events.data.models.ChangePassword;
import ra.olympus.zeus.events.data.remote.EventHubClient;
import ra.olympus.zeus.events.data.remote.ServiceGenerator;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alfre on 12/05/2018.
 */

public class ChangePasswordActivity extends AppCompatActivity {


    EditText current_password;
    EditText new_password;
    EditText confirm_password;
    String Username;
    SharedPreferences sharedPref;
    ChangePassword change;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        final Toolbar toolbar = findViewById(R.id.change_password_toolbar);
        toolbar.setTitle("Change Password");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);

        setUpActionBar();

        current_password = (EditText) findViewById(R.id.current_password);
        new_password = (EditText) findViewById(R.id.new_password);
        confirm_password = (EditText) findViewById(R.id.change_password_confirm_password);

        sharedPref = getSharedPreferences("EVENTHUB_SHAREDPREF_SIGNIN", Context.MODE_PRIVATE);
        Username = sharedPref.getString("Username",Username);



        Button changePassword = this.findViewById(R.id.change_password_button);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                change  = new ChangePassword();
                change.setOldPassword(current_password.getText().toString());
                change.setNewPassword(new_password.getText().toString());

                String password = sharedPref.getString("Password", null);

                if (!new_password.getText().toString().equals(confirm_password.getText().toString())) {

                    Toast.makeText(ChangePasswordActivity.this, "Confirm password does not match new password field", Toast.LENGTH_SHORT).show();
                }

                if (!current_password.getText().toString().equals(password)) {
                    Toast.makeText(ChangePasswordActivity.this, "Current password does not match this account", Toast.LENGTH_SHORT).show();

                }

                if (new_password.equals(confirm_password) && current_password.getText().toString().equals(password)) {
                    //Make call to the backend to change the password in the database and return the user to the setting screen
                    Toast.makeText(ChangePasswordActivity.this,"This matches", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(ChangePasswordActivity.this,"This matches else", Toast.LENGTH_SHORT).show();
                    changeMyPassword(change);

                }


            }
        });

    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



    }



    private void changeMyPassword(ChangePassword change){

        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
        retrofit2.Call<ResponseBody> call = client.changepassword(Username,change);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    SharedPreferences sharedPref;

                    sharedPref = getSharedPreferences("EVENTHUB_SHAREDPREF_SIGNIN",Context.MODE_PRIVATE);

                    sharedPref.edit().putString("Password",new_password.toString()).apply();

                    Toast.makeText(ChangePasswordActivity.this, "Your password has been changed",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "You have no connection",Toast.LENGTH_SHORT).show();
            }
        });


    }
}