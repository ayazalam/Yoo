package in.theyoo.yoo.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.theyoo.yoo.R;
import in.theyoo.yoo.appIntro.MainAppIntro;
import in.theyoo.yoo.storage.MySharedPreferences;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.phone_edit_text) TextView mPhoneNumber;
    @Bind(R.id.password_edit_text) TextView mPasswordNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //Shared preference
        MySharedPreferences mPreferences = MySharedPreferences.getInstance(getApplicationContext());

        //check user has opened app for the first time
        if (!mPreferences.getBooleanKey(MySharedPreferences.KEY_MAIN_APP_INTRO)){
            //store value to Shared preference
            mPreferences.setBooleanKey(MySharedPreferences.KEY_MAIN_APP_INTRO);
            //Show main app intro
            startActivity(new Intent(this, MainAppIntro.class));
        }

        //Set editText bottom line color
        mPhoneNumber.getBackground().mutate().setColorFilter(
                ContextCompat.getColor(getApplicationContext(),R.color.iconColor), PorterDuff.Mode.ADD);

        mPasswordNumber.getBackground().mutate().setColorFilter(
                ContextCompat.getColor(getApplicationContext(),R.color.iconColor), PorterDuff.Mode.ADD);    }


}
