package ra.olympus.zeus.events;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.preference.PreferenceActivity;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;

public class SignInActivity extends AppCompatActivity {



    EditText Username;
    EditText Password;


    ScrollView SignInLayout;
    AnimationDrawable animationDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


         Username = (EditText) findViewById(R.id.Username_of_user_edit_text_sign_in);
         Password = (EditText) findViewById(R.id.password_of_user_edit_text);

        TextView dontHaveAnAccountTextView = this.findViewById(R.id.dont_have_an_account_text_view);
        dontHaveAnAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUpIntent);
                finish();
            }
        });




        Button signInButton = this.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserSignIn user = new UserSignIn();
                user.setUsername(Username.getText().toString());
                user.setPassword(Password.getText().toString());

                SendSignInRequest(user);




            }
        });


        SignInLayout = (ScrollView) findViewById(R.id.mySignInLayout);

        animationDrawable = (AnimationDrawable) SignInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(650);
        animationDrawable.setExitFadeDuration(650);
        animationDrawable.start();


    }

    private void SendSignInRequest(final UserSignIn user) {


        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);


        Call<ResponseBody> call = client.sendSignInDetails(user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){

                    SharedPreferences sharedPref;

                    sharedPref = getSharedPreferences("EVENTHUB_SHAREDPREF_SIGNIN", Context.MODE_PRIVATE);

                    sharedPref.edit().putString("Username",user.getUsername()).commit();
                    sharedPref.edit().putString("Password", user.getPassword()).commit();


                    Intent MainActivityIntent = new Intent(getApplicationContext(),MainActivity.class);
                    MainActivityIntent.putExtra("Username",user.getUsername());
                    startActivity(MainActivityIntent);
                    finish();


                }

                else {

                    switch (response.code()){

                        case 403:
                            Toast.makeText(SignInActivity.this,"Your Password/Username is incorrect",Toast.LENGTH_SHORT ).show();
                            break;



                    }


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(SignInActivity.this,"You have no connection", Toast.LENGTH_SHORT).show();
            }
        });


    }




}



