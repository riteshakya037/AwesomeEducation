package com.awesome_folks.quidity.Notes;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awesome_folks.quidity.BottomSheet.BottomSheetCallback;
import com.awesome_folks.quidity.MainActivity;
import com.awesome_folks.quidity.Parse.NoteRow;
import com.awesome_folks.quidity.Parse.ParseHandler;
import com.awesome_folks.quidity.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotesListDisplay extends RecyclerView.Adapter<NotesListDisplay.NotesViewHolder> {
    private final LayoutInflater inflator;
    private final ParseHandler parseHandler;
    List<NoteRow> cardList = Collections.EMPTY_LIST;
    Context context;
    BottomSheetCallback callback;

    NotesListDisplay(final Context c) {
        this.context = c;
        this.callback=((BottomSheetCallback)context);
        inflator = LayoutInflater.from(c);
        cardList = new ArrayList<>();
        parseHandler = new ParseHandler();
        getData("", false);
    }

    protected void getData(String filter, boolean isRefresh) {
        cardList = parseHandler.getNotes(context, this, filter, isRefresh);
    }

    public void refresh(final Context c, SwipeRefreshLayout swipeRefreshLayout) {
        this.context = c;
        getData("", true);
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.single_note, parent, false);
        NotesViewHolder holder = new NotesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NotesViewHolder holder, final int position) {
        holder.title.setText(cardList.get(position).getTitle());
        holder.publisher.setText(cardList.get(position).getAuthor());
        holder.description.setText(cardList.get(position).getDescription());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof MainActivity)
                    callback.showBottomSheet(cardList.get(position));
            }
        });
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView publisher;
        TextView description;
        RelativeLayout cardView;

        public NotesViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txtTitle);
            publisher = (TextView) itemView.findViewById(R.id.txtPosted);
            description = (TextView) itemView.findViewById(R.id.txtDesc);
            cardView = (RelativeLayout) itemView.findViewById(R.id.wholeCard);
        }
    }

}
