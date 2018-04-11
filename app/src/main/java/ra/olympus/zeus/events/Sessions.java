package ra.olympus.zeus.events;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by alfre on 11/04/2018.
 */

public class Sessions {
    private SharedPreferences prefs;

    public Sessions(Context context){
        //TODO Auto generated constructor stub

        prefs = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public Sessions() {

    }

    public void setUsername (String username){
        prefs.edit().putString("Username",username).commit();

    }

    public String getUsername (){
        String username = prefs.getString("Username","");
        return username;
    }

    public void setPassword (String password){
        prefs.edit().putString("Password",password).commit();

    }

    public String getPassword (){
        String password = prefs.getString("Username","") ;
        return password;

    }

}
