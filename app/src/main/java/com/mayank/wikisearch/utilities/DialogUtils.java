package com.mayank.wikisearch.utilities;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.mayank.wikisearch.R;

/**
 * DialogUtils Class Responsible for showing Alerts when communicating with server
 * Also handles to display error in between app due to any issues which results,
 * showing the error into AlertBox for user attention to take care e.g. When No Internet Connection...
 */

public class DialogUtils {
    private static AlertDialog mProgressDialog;

    public static void showAlertDialog(Activity activity, String msg, @StringRes int buttonText, DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, android.R.style.Theme_Holo_Light_Dialog_NoActionBar)
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton(buttonText, clickListener);
        builder.show();
    }

    public static void showProgressLoader(Activity activity, @StringRes int msg) {
        if (mProgressDialog == null || mProgressDialog.getOwnerActivity() == null || mProgressDialog.getOwnerActivity().isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.DialogStyle);
            builder.setView(R.layout.layout_progress_loader)
                    .setCancelable(false);
            mProgressDialog = builder.create();
            mProgressDialog.setOwnerActivity(activity);
        }
        mProgressDialog.show();
        ((TextView) mProgressDialog.findViewById(R.id.loaderMsg)).setText(msg);
    }

    public static void hideLoader() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
