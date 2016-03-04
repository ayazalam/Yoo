package in.theyoo.yoo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.theyoo.yoo.R;
import in.theyoo.yoo.appIntro.MainAppIntro;
import in.theyoo.yoo.storage.MySharedPreferences;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Shared preference
        MySharedPreferences mPreferences = MySharedPreferences.getInstance(getApplicationContext());

        //check user has opened app for the first time
        if (!mPreferences.getBooleanKey(MySharedPreferences.KEY_MAIN_APP_INTRO)){
            //store value to Shared preference
            mPreferences.setBooleanKey(MySharedPreferences.KEY_MAIN_APP_INTRO);
            //Show main app intro
            startActivity(new Intent(this, MainAppIntro.class));
        }
    }
}
