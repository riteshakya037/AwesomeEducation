package com.awesome_folks.quidity.LoginHandler;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;

/**
 * Created by ritesh on 10/24/15.
 */
public class AccountsHandler {
    AccountManager mAccountManager;
    private AppCompatActivity appCompatActivity;
    private Dialog dlg;
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";

    public AccountsHandler(AppCompatActivity appCompatActivity, Dialog dlg) {
        this.appCompatActivity = appCompatActivity;
        this.dlg = dlg;
    }

    private String[] getAccountNames() {
        mAccountManager = AccountManager.get(appCompatActivity);
        Account[] accounts = mAccountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String[] names = new String[accounts.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = accounts[i].name;
        }
        return names;
    }

    private AbstractGetNameTask getTask(AppCompatActivity activity, String email, String scope, Dialog dlg) {
        return new GetNameInForeground(activity, email, scope, dlg);
    }

    public void syncGoogleAccount() {
        if (isNetworkAvailable()) {
            String[] account_arrs = getAccountNames();
            if (account_arrs.length > 0) {
                getTask(appCompatActivity, account_arrs[0], SCOPE, dlg).execute();
            } else {
                Toast.makeText(appCompatActivity, "No Google Account Sync!",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(appCompatActivity, "No Network Service!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) appCompatActivity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.e("Network Testing", "Available");
            return true;
        }

        Log.e("Network Testing", "Not Available");
        return false;
    }
}
