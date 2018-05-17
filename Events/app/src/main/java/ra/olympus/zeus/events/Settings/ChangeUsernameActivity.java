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
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import ra.olympus.zeus.events.R;

/**
 * Created by alfre on 12/05/2018.
 */

public class ChangeUsernameActivity extends AppCompatActivity {

    EditText current_username;
    EditText new_username;
    EditText confirm_username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_username);


        Toolbar toolbar = findViewById(R.id.change_username_toolbar);
        toolbar.setTitle("Change Username");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);

        setUpActionBar();


        current_username = (EditText) findViewById(R.id.current_username);
        new_username = (EditText) findViewById(R.id.new_username);
        confirm_username = (EditText) findViewById(R.id.confirm_username);

        Button changeUsername = this.findViewById(R.id.change_username_button);
        changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref;

                sharedPref = getSharedPreferences("EVENTHUB_SHAREDPREF_SIGNIN", Context.MODE_PRIVATE);

                String username = sharedPref.getString("Username", null);

                if(!new_username.equals(confirm_username)) {

                    Toast.makeText(ChangeUsernameActivity.this,"Confirm password does not match new pasword field", Toast.LENGTH_SHORT).show();
                }

                if (!current_username.getText().toString().equals(username)) {
                    Toast.makeText(ChangeUsernameActivity.this, "Current username does not match this account", Toast.LENGTH_SHORT ).show();

                }

                if (new_username.equals(confirm_username) && current_username.getText().toString().equals(username)){
                    //Make call to backend to change username in the database

                }
            }
        });




    }
    private void setUpActionBar(){
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
