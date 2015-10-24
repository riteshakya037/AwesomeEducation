package com.awesome_folks.quidity.Notes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.awesome_folks.quidity.Parse.NoteRow;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ritesh on 10/24/15.
 */
public class FileDownloadHandler {

    private static ProgressDialog dlg;
    private static Context context;
    private static NoteRow noteRow;

    public static void download(final Context context, final NoteRow noteRow, final boolean toLocal) {
        FileDownloadHandler.context = context;
        FileDownloadHandler.noteRow = noteRow;
        dlg = new ProgressDialog(context);
        dlg.setTitle("Please wait.");
        dlg.setMessage("Loading Data");
        dlg.show();

        if (!toLocal) {
            saveToDrive();
        } else {
            saveToLocal();
        }
    }

    private static void saveToLocal() {
        dlg.setMessage("Downloading " + noteRow.getLink());
        final ParseFile note = noteRow.getNote();
        note.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    dlg.setMessage("Saving...");
                    File directory = new File(Environment.getExternalStorageDirectory() + "/Awesome Education/" + noteRow.getAuthor() + "/");
                    directory.mkdirs();
                    File photo = new File(directory, noteRow.getLink());
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

    private static void saveToDrive() {
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
