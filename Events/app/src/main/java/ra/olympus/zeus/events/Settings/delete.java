package ra.olympus.zeus.events.Settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import okhttp3.ResponseBody;
import ra.olympus.zeus.events.EventDetailActivity;
import ra.olympus.zeus.events.MainActivity;
import ra.olympus.zeus.events.R;
import ra.olympus.zeus.events.StartUpActivity;
import ra.olympus.zeus.events.data.remote.EventHubClient;
import ra.olympus.zeus.events.data.remote.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class delete extends AppCompatActivity {
    SharedPreferences sharedPref;
    private String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        sharedPref = getSharedPreferences("EVENTHUB_SHAREDPREF_SIGNIN", Context.MODE_PRIVATE);
        Username = sharedPref.getString("Username",Username);
        DeleteyourAccount();

    }

    private void DeleteyourAccount(){
        new AlertDialog.Builder(delete.this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Delete();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();
    }

    private void Delete(){
        EventHubClient client = ServiceGenerator.createService(EventHubClient.class);
        Call<ResponseBody> call = client.deleteAccount(Username);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                if(response.code()==200){
                    Toast.makeText(delete.this, "Account deleted", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPref;
                    sharedPref = getSharedPreferences("EVENTHUB_SHAREDPREF_SIGNIN", Context.MODE_PRIVATE);

                    sharedPref.edit().clear().apply();
                    Intent StartUpintent = new Intent(delete.this,StartUpActivity.class);
                    startActivity(StartUpintent);

                }
                else{
                    //response code is supposed to come from the back end
                    switch (response.code()){

                        case 500:
                            Toast.makeText(delete.this,"Account not Deleted", Toast.LENGTH_SHORT).show();

                        default:
                            Toast.makeText(delete.this, "Server returned error: Unknown error", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(delete.this, "You have no connection to server", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
