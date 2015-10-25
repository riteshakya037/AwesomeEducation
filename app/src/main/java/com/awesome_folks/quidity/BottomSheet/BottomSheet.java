package com.awesome_folks.quidity.BottomSheet;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awesome_folks.quidity.Notes.FileDownloadHandler;
import com.awesome_folks.quidity.Parse.NoteRow;
import com.awesome_folks.quidity.R;

/**
 * Created by Ritesh on 7/8/2015.
 */
public class BottomSheet extends Dialog {
    private Context context;
    NoteRow noteRow;
    LinearLayout saveToDrive, saveToLocal, share;
    TextView title;

    public BottomSheet(Context context, NoteRow noteRow) {
        super(context, R.style.BottomSheet_Dialog);
        this.context = context;
        this.noteRow = noteRow;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_out_menu);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        params.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        getWindow().setAttributes(params);
        setCanceledOnTouchOutside(true);

        //-------------------------END-------------------------------------/
        title = (TextView) findViewById(R.id.fileTitle);
        title.setText(noteRow.getLink());
        saveToDrive = (LinearLayout) findViewById(R.id.saveToDrive);
        saveToDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileDownloadHandler.download(context, noteRow, false);
            }
        });
        saveToLocal = (LinearLayout) findViewById(R.id.saveToLocal);
        saveToLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileDownloadHandler.download(context, noteRow, true);
            }
        });
        share = (LinearLayout) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(v);
            }
        });
    }

    private void share(final View v) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_SUBJECT, noteRow.getTitle());
        i.putExtra(Intent.EXTRA_TEXT, noteRow.getAuthor() + " - " + noteRow.getLink());
        i.setType("text/plain");
        Intent chooser = Intent
                .createChooser(i, "Share " + noteRow.getTitle());
        v.getContext().startActivity(chooser);
    }
}
