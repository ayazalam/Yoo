package in.theyoo.yoo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.theyoo.yoo.R;
import in.theyoo.yoo.appIntro.MainAppIntro;
import in.theyoo.yoo.extra.ApiUrl;
import in.theyoo.yoo.extra.Keys;
import in.theyoo.yoo.network.MyVolley;
import in.theyoo.yoo.parser.JsonParser;
import in.theyoo.yoo.pojo.SimplePojo;
import in.theyoo.yoo.storage.MySharedPreferences;
import in.theyoo.yoo.util.Util;

public class MainActivity extends AppCompatActivity {
    //view
    @Bind(R.id.phone_edit_text) TextView mPhoneNumber;
    @Bind(R.id.continue_button) Button mContinue;
    @Bind(R.id.layoutForSnackbar) RelativeLayout mLayoutForSnackBar;

    //member variables
    private RequestQueue mRequestQueue;
    private ProgressDialog pd;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //setup Progress dialog
        pd = new ProgressDialog(this);
        pd.setMessage(getString(R.string.please_wait_requesting_otp));
        pd.setCancelable(true);

        //Shared preference
        MySharedPreferences mPreferences = MySharedPreferences.getInstance(getApplicationContext());

        //check user has opened app for the first time
        if (!mPreferences.getBooleanKey(MySharedPreferences.KEY_MAIN_APP_INTRO)) {
            //store value to Shared preference
            mPreferences.setBooleanKey(MySharedPreferences.KEY_MAIN_APP_INTRO);
            //Show main app intro
            startActivity(new Intent(this, MainAppIntro.class));
        }

        //Instanciate Volley
        mRequestQueue = MyVolley.getInstance().getRequestQueue();

        //Set editText bottom line color
        mPhoneNumber.getBackground().mutate().setColorFilter(
                ContextCompat.getColor(getApplicationContext(), R.color.iconColor), PorterDuff.Mode.ADD);


        //When Continue button is pressed
        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get mobile number
                String mobile = mPhoneNumber.getText().toString().trim();
                checkInternetAndDoLogin(mobile);
            }
        });
    }

    /*
    * Method to check internet connection and do login or signup
    * */
    private void checkInternetAndDoLogin(String mobile) {
        //check internet
        if (Util.isNetworkAvailable()){
            //check phone number is valid
            if (mobile.length() == 10){
                doLoginInBackground(mobile);
            }else{
                Util.redSnackbar(mLayoutForSnackBar,getString(R.string.invalid_mobile_number));
            }
        }else{
           Util.noInternetSnackBar(mLayoutForSnackBar);
        }
    }

    /*
    * Method to do login & register in background
    *
    * */
    private void doLoginInBackground(final String mobile) {
        Log.d(TAG,"HUS: number 1 "+mobile);
        pd.show();
        StringRequest request = new StringRequest(Request.Method.POST,ApiUrl.LOGIN_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.hide();
                //Parse Json Response
                parseLoginResponse(response,mobile);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.hide();
                Log.e(TAG, "HUS: doLoginInBackground: " + error.getMessage());
                String errorString = MyVolley.handleVolleyError(error);
                Util.redSnackbar(mLayoutForSnackBar,errorString);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put(Keys.KEY_COM_MOBILE,mobile);
                return map;
            }
        };

        mRequestQueue.add(request);
    }

    private void parseLoginResponse(String response,String mobile) {
        try {
            SimplePojo pojo = JsonParser.SimpleParser(response);
            if (pojo.getReturned()){ //true success

                Log.d(TAG,"HUS: number 2 "+mobile);
                //Send user to OtpVerificationActivity
                Intent OtpVerificationIntent = new Intent(getApplication(),OtpVerificationActivity.class);
                OtpVerificationIntent.putExtra(Keys.KEY_COM_MOBILE,mobile);
                startActivity(OtpVerificationIntent);

            }else{ //error
                Util.redSnackbar(mLayoutForSnackBar,pojo.getMessage());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG,"HUS: parseLoginResponse: response : "+response);
            Log.e(TAG, "HUS: parseLoginResponse: " + e.getMessage());
            Util.showParsingErrorAlert(MainActivity.this);
        }
    }
}
