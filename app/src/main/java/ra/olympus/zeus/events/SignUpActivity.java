package ra.olympus.zeus.events;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {

    String UsernameSignUpResponse;
    ScrollView mylayout;
    AnimationDrawable animationDrawable;

  /*  public boolean isValidText (String textstring){
        return textstring.matches("^([A-Za-z]+)(\\\\s[A-Za-z]+)*\\\\s?$");

    }*/

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
                finish();
            }
        });

        final EditText FirstName = (EditText) findViewById(R.id.name_of_user_edit_text);
        final EditText LastName = (EditText) findViewById(R.id.last_name_of_user);
        final EditText UserName = (EditText) findViewById(R.id.Username_of_user);
        final EditText PhoneNumber = (EditText) findViewById(R.id.phone_number_of_user_edit_text);
        final EditText Email = (EditText) findViewById(R.id.email_of_user_edit_text_sign_up);
        final EditText Password = (EditText) findViewById(R.id.password_of_user_edit_text_sign_up);
        final EditText confirmPassword = (EditText) findViewById(R.id.confirm_password_edit_text);



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

                if (user.getUserName()==UsernameSignUpResponse){

                    Intent MainActivityIntent = new Intent(getApplicationContext(),MainActivity.class);
                    MainActivityIntent.putExtra("Username",UsernameSignUpResponse);
                    startActivity(MainActivityIntent);
                    finish();
                }



            }
        });



        mylayout = (ScrollView) findViewById(R.id.mylayout);

        animationDrawable = (AnimationDrawable) mylayout.getBackground();
        animationDrawable.setEnterFadeDuration(650);
        animationDrawable.setExitFadeDuration(650);
        animationDrawable.start();

     }

     private void SendNetworkRequest(UserDetails user){
         EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
         Call<ResponseBody> call = client.sendUserDetails(user);

         call.enqueue(new Callback<ResponseBody>() {
             @Override
             public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                 if(response.isSuccessful()){
                     Toast.makeText(SignUpActivity.this,"Success",Toast.LENGTH_SHORT).show();

                     Intent SignInIntent = new Intent(getApplicationContext(),SignInActivity.class);
                     startActivity(SignInIntent);
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
