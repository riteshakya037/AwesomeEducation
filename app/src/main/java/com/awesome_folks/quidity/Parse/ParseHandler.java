package com.awesome_folks.quidity.Parse;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritesh on 10/24/15.
 */
public class ParseHandler {
    public static String appID = "hpuaQokf3I0QP3kQmVd6JUSoFVUEnHaIxGJsam64";
    public static String clientKey = "QtlM8ngJMdIlNabKiIvqLsFmcTLETXoyS7MWhfoj";


    public ArrayList<NoteRow> getNotes(final Context c, final RecyclerView.Adapter listDisplay, final String filter, boolean isRefresh) {
        final ArrayList<NoteRow> list = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notes");
        query.orderByDescending("createdAt");
        query.whereEqualTo("Subscription", "Kathmandu Engineering College_Computer_1");
        final ProgressDialog dlg;
        dlg = new ProgressDialog(c);
        dlg.setTitle("Please wait.");
        dlg.setMessage("Loading Data");
        if (filter == null) {
            dlg.show();
        }
        if (!isRefresh) {
            query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        } else {
            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ONLY);
        }
        query.findInBackground(new FindCallback<ParseObject>() {
                                   @Override
                                   public void done(List<ParseObject> parseObjects, ParseException e) {
                                       if (e == null) {
                                           list.clear();
                                           for (ParseObject post : parseObjects) {
                                               if (filter == null || filter.equalsIgnoreCase("") ||
                                                       post.getString(String.valueOf(NoteTable.Author.getFieldName())).toUpperCase().contains(filter.toUpperCase()) ||
                                                       post.getString(String.valueOf(NoteTable.Title.getFieldName())).toUpperCase().contains(filter.toUpperCase())) {
                                                   NoteRow note = new NoteRow(post);
                                                   list.add(note);
                                               }
                                           }
                                           listDisplay.notifyDataSetChanged();
                                           dlg.dismiss();
                                       } else {
                                           if (e.getCode() == 100) {
                                               Toast.makeText(c, "Unable To Connect to Internet", Toast.LENGTH_LONG).show();
                                           } else
                                               Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG).show();
                                       }
                                   }
                               }
        );
        return list;
    }
}
