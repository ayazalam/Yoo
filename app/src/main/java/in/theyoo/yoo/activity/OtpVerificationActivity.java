package in.theyoo.yoo.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.theyoo.yoo.R;
import in.theyoo.yoo.extra.ApiUrl;
import in.theyoo.yoo.extra.Keys;
import in.theyoo.yoo.network.MyVolley;
import in.theyoo.yoo.parser.JsonParser;
import in.theyoo.yoo.pojo.SimplePojo;
import in.theyoo.yoo.util.Util;

public class OtpVerificationActivity extends AppCompatActivity {
    private static final String TAG = OtpVerificationActivity.class.getSimpleName();
    //View
    @Bind(R.id.verify_button) Button mVerifyButton;
    @Bind(R.id.otp_edit_text) EditText mOtpEditText;
    @Bind(R.id.otp_has_been_send_to_textview) TextView mOtpSendPlaceholder;
    @Bind(R.id.layoutForSnackbar) RelativeLayout mLayoutForSnackbar;

    //Member variable
    private String mMobile;
    private RequestQueue mRequestQueue;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        ButterKnife.bind(this);

        mMobile = getIntent().getExtras().getString(Keys.KEY_COM_MOBILE);
        Log.d(TAG, "HUS: number 3 "+getIntent().getExtras().getString(Keys.KEY_COM_MOBILE));
        Log.d(TAG, "HUS: number 4 "+mMobile);

        //Instanciate Volley
        mRequestQueue = MyVolley.getInstance().getRequestQueue();

        //instanciate Progress Dialog
        pd = new ProgressDialog(this);
        pd.setMessage(getString(R.string.verifying_otp));
        pd.setCancelable(true);

        //set placeholder
        mOtpSendPlaceholder.append(" " + mMobile);

        //When verify button is clicked
        mVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get Entered OTP
                String otp = mOtpEditText.getText().toString().trim();
                checkInternetAndVerifyOtp(otp);
            }
        });
    }

    /*
    * Method to check internet and verify otp
    * */
    private void checkInternetAndVerifyOtp(String otp) {
        //Check internet
        if (Util.isNetworkAvailable()){ //conected
            //check otp is of 4 char
            if (otp.length() == 4){
                verifyOtpInBackground(otp);
            }else{
                Util.redSnackbar(mLayoutForSnackbar,getString(R.string.inavlid_otp_code));
            }
        }else { //no internet
            Util.noInternetSnackBar(mLayoutForSnackbar);
        }
    }

    /*
    * Method to verify OTP in background
    * */
    private void verifyOtpInBackground(final String otp) {
        pd.show();
        StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.VERIFY_OTP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                parseVerifyOtpResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Log.e(TAG, "HUS: verifyOtpInBackground: " + error.getMessage());
                String errorString = MyVolley.handleVolleyError(error);
                Util.redSnackbar(mLayoutForSnackbar, errorString);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put(Keys.KEY_COM_MOBILE,mMobile);
                param.put(Keys.PARAM_VERIFY_OTP_OTP,otp);
                return param;
            }
        };
        mRequestQueue.add(request);
    }

    /*
    * Method to verify OTP response
    * */
    private void parseVerifyOtpResponse(String response) {
        try {
            SimplePojo current = JsonParser.SimpleParser(response);
            if (current.getReturned()){ //OTP verified
                Toast.makeText(getApplicationContext(),current.getMessage(),Toast.LENGTH_LONG).show();
                //go to Home Activity
                Util.goToHomeActivity(getApplication());
            }else{ //Otp not verified Some error
                Util.redSnackbar(mLayoutForSnackbar,current.getMessage());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG,"HUS: parseVerifyOtpResponse: response : "+response);
            Log.e(TAG, "HUS: parseVerifyOtpResponse: " + e.getMessage());
            Util.showParsingErrorAlert(OtpVerificationActivity.this);
        }
    }
}
