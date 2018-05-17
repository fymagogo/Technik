package ra.olympus.zeus.events.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import ra.olympus.zeus.events.R;

/**
 * Created by alfre on 12/05/2018.
 */

public class SettingsPreferenceFragment extends PreferenceFragment {

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
