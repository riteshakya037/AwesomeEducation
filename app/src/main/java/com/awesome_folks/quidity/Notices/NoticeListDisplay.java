package com.awesome_folks.quidity.Notices;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.awesome_folks.quidity.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class NoticeListDisplay extends RecyclerView.Adapter<NoticeListDisplay.NoticeViewHolder> {
    private final LayoutInflater inflator;
    List<noticeCard> cardList = Collections.EMPTY_LIST;
    Context context;

    NoticeListDisplay(final Context c) {
        this.context = c;
        inflator = LayoutInflater.from(c);
        cardList = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notice");
        query.orderByDescending("createdAt");
        query.whereEqualTo("Subscription", "Kathmandu Engineering College_Computer_1");
        final ProgressDialog dlg = new ProgressDialog(c);
        dlg.setTitle("Please wait.");
        dlg.setMessage("Loading Data");
        dlg.show();
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.findInBackground(new FindCallback<ParseObject>() {
                                   @Override
                                   public void done(List<ParseObject> parseObjects, ParseException e) {
                                       if (e == null) {
                                           dlg.dismiss();
                                           cardList.clear();
                                           for (ParseObject post : parseObjects) {
                                               noticeCard note = new noticeCard(post.getObjectId(), post.getString("Title"), post.getString("Description"), post.getString("Date"));
                                               cardList.add(note);
                                               notifyDataSetChanged();
                                           }

                                       } else {
                                           if (e.getCode() == 100) {
                                               Toast.makeText(c, "Unable To Connect to Internet", Toast.LENGTH_LONG).show();
                                           } else
                                               Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG).show();

                                       }
                                   }
                               }
        );

    }

    public void refresh(final Context c, SwipeRefreshLayout swipeRefreshLayout) {
        cardList = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notice");
        query.orderByDescending("createdAt");
        query.whereEqualTo("Subscription", "Kathmandu Engineering College_Computer_1");
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ONLY);
        query.findInBackground(new FindCallback<ParseObject>() {
                                   @Override
                                   public void done(List<ParseObject> parseObjects, ParseException e) {
                                       if (e == null) {
                                           for (ParseObject post : parseObjects) {
                                               noticeCard note = new noticeCard(post.getObjectId(), post.getString("Title"), post.getString("Description"), post.getString("Date"));
                                               cardList.add(note);
                                               notifyDataSetChanged();
                                           }

                                       } else {
                                           if (e.getCode() == 100) {
                                               Toast.makeText(c, "Unable To Connect to Internet", Toast.LENGTH_LONG).show();
                                           } else
                                               Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG).show();

                                       }
                                   }
                               }
        );
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.single_notice, parent, false);
        NoticeViewHolder holder = new NoticeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NoticeViewHolder holder, int position) {
        noticeCard temp = cardList.get(position);
        holder.title.setText(temp.title);
        holder.date.setText(temp.date);
        holder.description.setText(temp.Description);
        holder.btnCalender.setContentDescription(temp.ID);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    class NoticeViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        TextView title;
        TextView date;
        TextView description;
        ImageButton btnCalender;

        public NoticeViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txtTitle);
            date = (TextView) itemView.findViewById(R.id.txtDueDate);
            description = (TextView) itemView.findViewById(R.id.txtDescription);
            btnCalender = (ImageButton) itemView.findViewById(R.id.btnCalender);
            btnCalender.setOnClickListener(this);

        }

        @Override
        public void onClick(final View v) {
            String ID = v.getContentDescription().toString();
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Notice");
            query.orderByDescending("createdAt");
            query.whereEqualTo("objectId", ID);
            final ProgressDialog dlg = new ProgressDialog(context);
            dlg.setTitle("Please wait.");
            dlg.setMessage("Loading Data");
            dlg.show();
            query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
            query.getInBackground(ID, new GetCallback<ParseObject>() {

                        @Override
                        public void done(final ParseObject parseObject, ParseException e) {
                            if (e == null) {
                                if (e == null) {
                                    dlg.dismiss();
                                    String startDate = parseObject.getString("Date");
                                    Date fulldate = null;

                                    try {
                                        fulldate = new SimpleDateFormat("dd/MM/yyyy-HH:mm").parse(startDate + "-" + "00:00");
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }

                                    Intent intent = new Intent(Intent.ACTION_EDIT);
                                    intent.setType("vnd.android.cursor.item/event");
                                    intent.putExtra("beginTime", fulldate.getTime());
                                    intent.putExtra("beginTime", fulldate.getTime() + 60 * 60 * 1000);
                                    intent.putExtra(CalendarContract.Events.TITLE, parseObject.getString("Title"));
                                    intent.putExtra(CalendarContract.Events.DESCRIPTION, parseObject.getString("Description"));
                                    Intent chooser = Intent
                                            .createChooser(intent, "Add " + parseObject.getString("Title"));
                                    v.getContext().startActivity(chooser);
                                }

                            } else {
                                if (e.getCode() == 100) {
                                    Toast.makeText(context, "Unable To Connect to Internet", Toast.LENGTH_LONG).show();
                                } else
                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                    }
            );
        }
    }

}


class noticeCard {
    String Description;
    String title;
    String date;
    String ID;

    public noticeCard(String ID, String title, String Description, String date) {
        this.title = title;
        this.ID = ID;
        this.Description = Description;
        this.date = date;
    }

}
