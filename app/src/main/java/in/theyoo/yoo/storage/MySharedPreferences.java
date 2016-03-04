package in.theyoo.yoo.storage;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.Key;

/**
 * Created by husain on 3/4/2016.
 * Singleton class for SharedPreference
 */
public class MySharedPreferences {
    //instance field
    private static SharedPreferences mSharedPreference;
    private static MySharedPreferences mInstance = null;
    private static Context mContext;


    //Shared Preferece key
    private static String KEY_PREFERENCE_NAME = "yoo_preference";
    public static String KEY_MAIN_APP_INTRO = "main_app_intro";

    private MySharedPreferences(){
        mSharedPreference = mContext.getSharedPreferences(KEY_PREFERENCE_NAME,Context.MODE_PRIVATE);
    }

    public static MySharedPreferences getInstance(Context context){
        mContext = context;
        if(mInstance == null){
            mInstance = new MySharedPreferences();
        }
        return mInstance;
    }

    //Method to set boolean for (AppIntro)
    public void setBooleanKey(String keyname){
        mSharedPreference.edit().putBoolean(keyname,true).apply();
    }

    /*
    * Method to get boolan key
    * true = means set
    * false = not set (show app intro)
    * */
    public boolean getBooleanKey(String keyname){
        return mSharedPreference.getBoolean(keyname,false);
    }

}
