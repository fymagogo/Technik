package ra.olympus.zeus.events.Settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import ra.olympus.zeus.events.R;
import ra.olympus.zeus.events.StartUpActivity;

/**
 * Created by alfre on 12/05/2018.
 */

public class SettingsPreferenceFragment extends PreferenceFragment {
    private Context mCtx;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        findPreference("change_password");
        Preference change_password = (Preference) findPreference("change_password");

        findPreference("change_username");
        Preference change_username = (Preference) findPreference("change_username");




    }





}
