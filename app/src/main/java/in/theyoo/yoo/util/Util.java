package in.theyoo.yoo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import in.theyoo.yoo.R;
import in.theyoo.yoo.application.MyApplication;

/**
 * Class for utility methods
 */
public class Util {
    public static boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() &&
                networkInfo.isConnected();
    }

    public static void noInternetSnackBar(View snackBarLayout){
        final Snackbar snackbar = Snackbar.make(snackBarLayout, R.string.check_internet_connection, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("hide", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    public static void redSnackbar(View layout, String text) {
        Context context = MyApplication.getAppContext();

        Snackbar snackbar = Snackbar.make(layout, text, Snackbar.LENGTH_LONG);
        View snack = snackbar.getView();
        snack.setBackgroundColor(ContextCompat.getColor(context,R.color.errorColor));
        snackbar.show();
    }
}
