package com.awesome_folks.awesome_education.Notes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.awesome_folks.awesome_education.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotesListDisplay extends RecyclerView.Adapter<NotesListDisplay.NotesViewHolder> {
    private final LayoutInflater inflator;
    List<singleCard> cardList = Collections.EMPTY_LIST;
    Context context;


    NotesListDisplay(final Context c) {
        this.context = c;
        inflator = LayoutInflater.from(c);
        cardList = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notes");
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
                                           cardList.clear();
                                           for (ParseObject post : parseObjects) {
                                               singleCard note = new singleCard(post.getObjectId(), post.getString("Title"), post.getString("Author"), post.getString("Description"), post.getString("Link"));
                                               cardList.add(note);
                                           }
                                           notifyDataSetChanged();
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
    }

    public void refresh(final Context c, SwipeRefreshLayout swipeRefreshLayout) {
        this.context = c;
        cardList = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notes");
        query.orderByDescending("createdAt");
        query.whereEqualTo("Subscription", "Kathmandu Engineering College_Computer_1");
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.findInBackground(new FindCallback<ParseObject>() {
                                   @Override
                                   public void done(List<ParseObject> parseObjects, ParseException e) {
                                       if (e == null) {
                                           cardList.clear();
                                           for (ParseObject post : parseObjects) {
                                               singleCard note = new singleCard(post.getObjectId(), post.getString("Title"), post.getString("Author"), post.getString("Description"), post.getString("Link"));
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
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.single_note, parent, false);
        NotesViewHolder holder = new NotesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NotesViewHolder holder, int position) {
        holder.title.setText(cardList.get(position).getTitle());
        holder.publisher.setText(cardList.get(position).getPublisher());
        holder.description.setText(cardList.get(position).getDescription());
        holder.btnDwnld.setContentDescription(cardList.get(position).getID());
        holder.btnShare.setContentDescription(cardList.get(position).getID());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        TextView title;
        TextView publisher;
        TextView description;
        ImageButton btnDwnld;
        ImageButton btnShare;

        public NotesViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txtTitle);
            publisher = (TextView) itemView.findViewById(R.id.txtPosted);
            description = (TextView) itemView.findViewById(R.id.txtDesc);
            btnDwnld = (ImageButton) itemView.findViewById(R.id.btnDwnld);
            btnShare = (ImageButton) itemView.findViewById(R.id.btnShare);
            btnDwnld.setOnClickListener(this);
            btnShare.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            if (v.getId() == R.id.btnDwnld) {
                Context wrapper = new ContextThemeWrapper(v.getContext(),
                        R.style.Base_Widget_AppCompat_PopupMenu);
                PopupMenu popup = new PopupMenu(wrapper, v);
                popup.getMenuInflater().inflate(R.menu.download_menu,
                        popup.getMenu());
                final String ID = v.getContentDescription().toString();
                popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notes");
                        final ProgressDialog dlg = new ProgressDialog(context);
                        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);


                        dlg.setTitle("Please wait.");
                        dlg.setMessage("Loading Data");
                        dlg.show();
                        if (item.getItemId() == R.id.saveDrive) {
                            query.getInBackground(ID, new GetCallback<ParseObject>() {

                                        @Override
                                        public void done(final ParseObject parseObject, ParseException e) {
                                            if (e == null) {
                                                dlg.dismiss();


                                            } else {
                                                if (e.getCode() == 100) {
                                                    Toast.makeText(context, "Unable To Connect to Internet", Toast.LENGTH_LONG).show();
                                                } else
                                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    }
                            );
                            Toast.makeText(v.getContext(), "Saving to Drive",
                                    Toast.LENGTH_LONG).show();
                        } else {

                            query.getInBackground(ID, new GetCallback<ParseObject>() {

                                        @Override
                                        public void done(final ParseObject parseObject, ParseException e) {
                                            if (e == null) {
                                                {
                                                    dlg.setMessage("Downloading " + parseObject.getString("Link"));
                                                    final ParseFile note = (ParseFile) parseObject.get("Note");
                                                    note.getDataInBackground(new GetDataCallback() {
                                                        public void done(byte[] data, ParseException e) {
                                                            if (e == null) {
                                                                dlg.setMessage("Saving...");
                                                                File directory = new File(Environment.getExternalStorageDirectory() + "/Awesome Education/" + parseObject.getString("Author") + "/");
                                                                directory.mkdirs();
                                                                File photo = new File(directory, parseObject.getString("Link"));
                                                                if (photo.exists()) {
                                                                    photo.delete();
                                                                } else {
                                                                    try {
                                                                        photo.createNewFile();
                                                                    } catch (IOException e1) {
                                                                        e1.printStackTrace();
                                                                    }
                                                                }
                                                                try {
                                                                    FileOutputStream fos = new FileOutputStream(photo.getPath());
                                                                    fos.write(data);
                                                                    fos.close();
                                                                    Log.e("PictureDemo", photo.getAbsolutePath());

                                                                } catch (IOException e1) {
                                                                    Log.e("PictureDemo", "Exception in photoCallback", e1);
                                                                } finally {
                                                                    dlg.dismiss();
                                                                }
                                                                try {
                                                                    FileOpen.openFile(context, photo);
                                                                } catch (IOException e1) {
                                                                    e1.printStackTrace();
                                                                }

                                                            } else {
                                                                Log.e("Dwnld", e.getMessage());
                                                            }
                                                        }
                                                    });
                                                }

                                            } else {
                                                dlg.dismiss();
                                                if (e.getCode() == 100) {
                                                    Toast.makeText(context, "Unable To Connect to Internet", Toast.LENGTH_LONG).show();
                                                } else
                                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();

                                            }
                                        }


                                    }
                            );
                        }
                        return false;
                    }
                });
                popup.show();
            }
            if (v.getId() == R.id.btnShare) {
                String ID = v.getContentDescription().toString();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Notes");
                query.whereEqualTo("objectId", ID);
                query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
                final ProgressDialog dlg = new ProgressDialog(context);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Loading Data");
                dlg.show();
                query.getInBackground(ID, new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {
                                if (e == null) {
                                    dlg.dismiss();

                                    Intent i = new Intent(Intent.ACTION_SEND);
                                    i.putExtra(Intent.EXTRA_SUBJECT, parseObject.get("Title").toString());
                                    i.putExtra(Intent.EXTRA_TEXT, parseObject.get("Author").toString() + " - " + parseObject.get("Link").toString());
                                    i.setType("text/plain");
                                    Intent chooser = Intent
                                            .createChooser(i, "Share " + parseObject.get("Title").toString());
                                    v.getContext().startActivity(chooser);


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

}

class singleCard {
    String title;
    String publisher;
    String description;
    String ID;
    String link;


    public singleCard(String ID, String title, String publisher, String description, String link) {
        this.title = title;
        this.publisher = publisher;
        this.description = description;
        this.ID = ID;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return this.getTitle();
    }

    public String getID() {
        return ID;
    }

    public String getLink() {
        return link;
    }


}

class FileOpen {

    public static void openFile(Context context, File url) throws IOException {
        // Create URI
        File file = url;
        Uri uri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        // Check what kind of file you are trying to open, by comparing the url with extensions.
        // When the if condition is matched, plugin sets the correct intent (mime) type,
        // so Android knew what application to use to open the file
        if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if (url.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if (url.toString().contains(".zip")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/zip");
        } else if (url.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/x-rar-compressed");
        } else if (url.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if (url.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
        } else if (url.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {
            //if you want you can also define the intent type for any other file

            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setDataAndType(uri, "*/*");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent chooser = Intent.createChooser(intent, "Send Feedback");
        context.startActivity(chooser);
    }
}