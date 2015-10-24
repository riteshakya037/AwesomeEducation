package com.awesome_folks.quidity.LoginHandler;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.IOException;

/**
 * Created by Ritesh Shakya on 10/6/2015.
 */
public class GetNameInForeground extends AbstractGetNameTask {

    public GetNameInForeground(AppCompatActivity mActivity, String mEmail, String mScope, Dialog dlg) {
        super(mActivity, mEmail, mScope,dlg);
    }

    @Override
    protected String fetchToken() throws IOException {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int code = googleApiAvailability.isGooglePlayServicesAvailable(context);
        try {
            return GoogleAuthUtil.getToken(context, mEmail, mScope);
        } catch (GooglePlayServicesAvailabilityException playEx) {
            googleApiAvailability.getErrorDialog(context, code, 9000).show();
        } catch (UserRecoverableAuthException urae) {
            context.startActivityForResult(urae.getIntent(), mRequest);
        } catch (GoogleAuthException fatalException) {
            fatalException.printStackTrace();
        }

        return null;
    }
}
