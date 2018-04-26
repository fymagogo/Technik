package ra.olympus.zeus.events;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by alfre on 18/04/2018.
 */

public class SplashActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent SplashIntent = new Intent(this,MainActivity.class);
        startActivity(SplashIntent);
        finish();
    }
}
