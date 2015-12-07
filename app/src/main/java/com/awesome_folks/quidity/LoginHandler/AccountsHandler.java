package com.awesome_folks.quidity.LoginHandler;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.awesome_folks.quidity.R;
import com.google.android.gms.auth.GoogleAuthUtil;

import java.util.ArrayList;

/**
 * Created by ritesh on 10/24/15.
 */
public class AccountsHandler {
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";
    AccountManager mAccountManager;
    private AppCompatActivity appCompatActivity;
    private int savedPosition = 0;

    public AccountsHandler(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
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

    public void syncGoogleAccount(final Context context) {
        if (isNetworkAvailable()) {
            String[] account_arrs = getAccountNames();
            if (account_arrs.length > 0) {
                final ArrayList<String> gUsernameList = new ArrayList<String>();
                AccountManager accountManager = AccountManager.get(context);
                Account[] accounts = accountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);

                gUsernameList.clear();
                for (Account account : accounts) {
                    gUsernameList.add(account.name);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose you Gmail Account");

                ListView lv = new ListView(context);

                ArrayAdapter<String> adapter = new ArrayAdapter<>
                        (context, android.R.layout.simple_list_item_1, android.R.id.text1,
                                gUsernameList);

                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long
                            id) {
                        Log.d("", gUsernameList.get(position));
                        savedPosition = position;
                    }
                });

                builder.setView(lv);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ProgressDialog dlg = new ProgressDialog(context);
                        dlg.setTitle("Please wait.");
                        dlg.setMessage("Loading Data");
                        getTask(appCompatActivity, gUsernameList.get(savedPosition), SCOPE, dlg).execute();
                        dialog.dismiss();
                        dlg.show();
                    }
                });
                final Dialog dialog = builder.create();
                dialog.show();

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
