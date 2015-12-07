package com.awesome_folks.quidity.LoginHandler;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.awesome_folks.quidity.DispatchActivity;
import com.parse.LogInCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ritesh on 10/24/15.
 */
public class SaveUser {

    byte[] array;
    private String userImageUrl;
    private String textName;
    private String textID;
    private Bitmap image;
    private String mEmail;
    private AppCompatActivity context;
    private Dialog dlg;
    private ParseFile profilePic;

    public void saveParseUser(String mEmail, final AppCompatActivity context, Dialog dlg) {
        this.mEmail = mEmail;
        this.context = context;
        this.dlg = dlg;

        try {
            JSONObject profileData = new JSONObject(AbstractGetNameTask.GOOGLE_USER_DATA);
            System.out.println("profileData = " + profileData);
//            if (profileData.has("picture")) {
//                userImageUrl = profileData.getString("picture");
//                GetImageFromUrl getImageFromUrl = new GetImageFromUrl() {
//                    @Override
//                    protected void onPostExecute(Bitmap bitmap) {
//                        super.onPostExecute(bitmap);
//                        int bytes = bitmap.getByteCount();
//                        ByteBuffer buffer = ByteBuffer.allocate(bytes);
//                        bitmap.copyPixelsToBuffer(buffer);
//                        array = buffer.array();
//                        profilePic = new ParseFile("userpicture.png", array);
//                        profilePic.saveInBackground(new SaveCallback() {
//                            @Override
//                            public void done(ParseException e) {
//                                // If successful add file to user and signUpInBackground
//                                if (null == e)
//                                    addParseUser();
//                            }
//                        });
//                    }
//                };
//                getImageFromUrl.execute(userImageUrl);
//            }
            if (profileData.has("name")) {
                textName = profileData.getString("name");
            }
            if (profileData.has("name")) {
                textID = profileData.getString("id");
            }
//            if (profileData.has("gender")) {
//                textGender = profileData.getString("gender");
//            }
            addParseUser();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addParseUser() {
        ParseUser user = new ParseUser();
        ParseACL roleACL = new ParseACL();
        roleACL.setPublicReadAccess(true);
        user.put("Name", textName);
        user.setEmail(mEmail);
        user.setUsername(textID);
        user.setPassword(textID);
//        user.put("profilePic", profilePic);

        user.put("College", "");
        user.put("Faculty", "");
        user.put("Year", 4);
        user.put("Subscription", "");
        ParseRole role = new ParseRole("Student", roleACL);
        role.getUsers().add(user);
        // Call the Parse signup method
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    if (e.getCode() == ParseException.CONNECTION_FAILED) {
                        Toast.makeText(context, "Unable To Connect to Internet", Toast.LENGTH_LONG).show();
                    } else if (e.getCode() == ParseException.USERNAME_TAKEN || e.getCode() == ParseException.EMAIL_TAKEN) {
                        ParseUser.logInInBackground(textID, textID, new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (e != null) {
                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    // Start an intent for the dispatch activity
                                    Intent intent = new Intent(context, DispatchActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                    dlg.dismiss();

                                }
                            }
                        });
                    } else
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    System.out.println(" Created User ");
                    // Start an intent for the dispatch activity
                    Intent intent = new Intent(context, DispatchActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    dlg.dismiss();

                }
            }
        });
    }
}
