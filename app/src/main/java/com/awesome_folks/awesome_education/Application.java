package com.awesome_folks.awesome_education;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;

/**
 * Created by Ritesh on 11/22/2014.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "hpuaQokf3I0QP3kQmVd6JUSoFVUEnHaIxGJsam64", "QtlM8ngJMdIlNabKiIvqLsFmcTLETXoyS7MWhfoj");
        if (ParseUser.getCurrentUser() != null) {
            ParseInstallation.getCurrentInstallation().saveInBackground();
            ParseUser.getCurrentUser().fetchInBackground();
            ParsePush.subscribeInBackground("");

        }
        // Also in this method, specify a default Activity to handle push notifications

    }
}
