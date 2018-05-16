package ra.olympus.zeus.events;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("EVENTHUB_SHAREDPREF_SIGNIN", Context.MODE_PRIVATE);


        if ((sharedPref.contains("Username") && sharedPref.contains("Password"))) {

            Intent StartUpIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(StartUpIntent);
            finish();

        }

        Button signInButton = this.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(signInIntent);

            }
        });

        Button signUpButton = this.findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUpIntent);

            }
        });
    }
}
