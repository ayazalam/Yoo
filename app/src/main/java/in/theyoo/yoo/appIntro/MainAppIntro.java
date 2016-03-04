package in.theyoo.yoo.appIntro;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import in.theyoo.yoo.R;
import in.theyoo.yoo.activity.MainActivity;

/**
 * Class to show app intro on startup of the app
 */
public class MainAppIntro extends AppIntro {
    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        addSlide(AppIntroFragment.newInstance("Try new apps", "Try new & cool apps and get free recharge", R.drawable.main_app_intro_1, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Recharge your phone", "Recharge your phone for free in seconds", R.drawable.main_app_intro_2, Color.parseColor("#03A9F4")));
        addSlide(AppIntroFragment.newInstance("Happy Earning", "Login now and start earning", R.drawable.main_app_intro_3, Color.parseColor("#FD5054")));


        showStatusBar(false);

        // Hide Skip/Done button
        showSkipButton(true);
        setDoneText("Login");

        setDepthAnimation();
    }

    @Override
    public void onSkipPressed() {
        Toast.makeText(getApplicationContext(), R.string.app_intro_was_skipped,Toast.LENGTH_LONG).show();
        //go to Main activity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        finish();
    }

    @Override
    public void onSlideChanged() {

    }
}
