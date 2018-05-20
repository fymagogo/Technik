package ra.olympus.zeus.events;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import ra.olympus.zeus.events.data.remote.EventHubClient;
import ra.olympus.zeus.events.data.remote.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    ScrollView mylayout;
    AnimationDrawable animationDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView alreadyHaveAnAccountTextView = this.findViewById(R.id.already_have_an_account_text_view);
        alreadyHaveAnAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(signInIntent);

            }
        });

        final EditText FirstName = (EditText) findViewById(R.id.name_of_user_edit_text);
        final EditText LastName = (EditText) findViewById(R.id.last_name_of_user);
        final EditText UserName = (EditText) findViewById(R.id.Username_of_user);
        final EditText PhoneNumber = (EditText) findViewById(R.id.phone_number_of_user_edit_text);
        final EditText Email = (EditText) findViewById(R.id.email_of_user_edit_text_sign_up);
        final EditText Password = (EditText) findViewById(R.id.password_of_user_edit_text_sign_up);
        final EditText confirmPassword = (EditText) findViewById(R.id.confirm_password_edit_text);
        CheckBox showpassword = findViewById(R.id.show_password_check_box);


        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(!isChecked){
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else{
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

                }
            }
        );





        Button signUpButton = this.findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserDetails user = new UserDetails();

                user.setEmail(Email.getText().toString());
                user.setFirstName(FirstName.getText().toString());
                user.setLastName(LastName.getText().toString());
                user.setPassword(Password.getText().toString());
                user.setPhoneNumber(PhoneNumber.getText().toString());
                user.setUserName(UserName.getText().toString());
                //Place username authentication here

                SendNetworkRequest(user);


            }
        });



        mylayout = (ScrollView) findViewById(R.id.mylayout);

        animationDrawable = (AnimationDrawable) mylayout.getBackground();
        animationDrawable.setEnterFadeDuration(650);
        animationDrawable.setExitFadeDuration(650);
        animationDrawable.start();

    }





    private void SendNetworkRequest(final UserDetails user){
        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
        Call<ResponseBody> call = client.sendUserDetails(user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    SharedPreferences sharedPref;

                    sharedPref = getSharedPreferences("EVENTHUB_SHAREDPREF_SIGNIN", Context.MODE_PRIVATE);
                    sharedPref.edit().putString("Username",user.getUserName());
                    sharedPref.edit().putString("Password",user.getPassword());
                    sharedPref.edit().apply();

                    Intent MainActivityIntent = new Intent(getApplicationContext(),MainActivity.class);
                    MainActivityIntent.putExtra("Username",user.getUserName());
                    startActivity(MainActivityIntent);
                    finish();

                }

                else{

                    switch (response.code()){

                        case 500:
                            Toast.makeText(SignUpActivity.this,"Server returned error: Duplicate detected", Toast.LENGTH_SHORT).show();
                            break;

                    }

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(SignUpActivity.this,"You have no connection", Toast.LENGTH_SHORT).show();

            }
        });




    }
}
