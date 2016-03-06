package in.theyoo.yoo.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;

import in.theyoo.yoo.R;
import in.theyoo.yoo.activity.HomeActivity;
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
        snack.setBackgroundColor(ContextCompat.getColor(context, R.color.errorColor));
        snackbar.show();
    }

    public static void showParsingErrorAlert(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.oops))
                .setMessage(context.getString(R.string.failed_to_parse_response))
                .setNegativeButton(context.getString(R.string.report_issue), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO:: take user to report issue area
                    }
                })
                .setPositiveButton(context.getString(R.string.try_again), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void goToHomeActivity(Context activity){
        Intent intent = new Intent(activity, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }
}
