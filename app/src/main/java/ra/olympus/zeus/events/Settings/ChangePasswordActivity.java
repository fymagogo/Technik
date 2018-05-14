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

public class ChangePasswordActivity extends AppCompatActivity {


    EditText current_password;
    EditText new_password;
    EditText confirm_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        Toolbar toolbar = findViewById(R.id.change_password_toolbar);
        toolbar.setTitle("Change Password");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);

        setUpActionBar();

        current_password = (EditText) findViewById(R.id.current_password);
        new_password = (EditText) findViewById(R.id.new_password);
        confirm_password = (EditText) findViewById(R.id.change_password_confirm_password);

        Button changePassword = this.findViewById(R.id.change_password_button);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref;

                sharedPref = getSharedPreferences("EVENTHUB_SHAREDPREF_SIGNIN", Context.MODE_PRIVATE);

                String password = sharedPref.getString("Password", null);

                if (!new_password.equals(confirm_password)) {

                    Toast.makeText(ChangePasswordActivity.this, "Confirm password does not match new password field", Toast.LENGTH_SHORT).show();
                }

                if (!current_password.getText().toString().equals(password)) {
                    Toast.makeText(ChangePasswordActivity.this, "Current password does not match this account", Toast.LENGTH_SHORT).show();


                }

                if (new_password.equals(confirm_password) && current_password.getText().toString().equals(password)) {
                    //Make call to the backend to change the password in the database and return the user to the setting screen


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
}