package com.awesome_folks.awesome_education.DashBoard;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.awesome_folks.awesome_education.R;
import com.balysv.materialripple.MaterialRippleLayout;

import java.util.ArrayList;

/**
 * Created by Ritesh on 11/21/2014.
 */
public class DashListDisplay extends RecyclerView.Adapter<DashListDisplay.DashViewHolder> {
    private final LayoutInflater inflater;
    ArrayList<dashCard> cardList;
    Context context;
    final String[][] additional = {{"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}};

    DashListDisplay(Context c) {
        this.context = c;
        inflater = LayoutInflater.from(c);
        cardList = new ArrayList<>();
        Resources res = c.getResources();
        String[] titles = res.getStringArray(R.array.dashTitle);
        int[] color = res.getIntArray(R.array.dashColor);
        for (int i = 0; i < titles.length; i++) {
            cardList.add(new dashCard(titles[i], additional[i], color[i]));
        }

    }

    public static String truncate(String value) {
        int length = 15;
        if (value != null && value.length() > length) {
            value = value.substring(0, length) + "...";
        }
        return value;
    }

    @Override
    public DashListDisplay.DashViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_dash, parent, false);
        DashViewHolder holder = new DashViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DashListDisplay.DashViewHolder holder, int position) {
        holder.title.setText(cardList.get(position).title);
        holder.title.setTextColor(cardList.get(position).color);
        holder.additional.setText(cardList.get(position).additional[0]);
        holder.additional1.setText(cardList.get(position).additional[1]);
        holder.additional2.setText(cardList.get(position).additional[2]);
        holder.color.setBackgroundColor(cardList.get(position).color);
        holder.materialRippleLayout.setRippleColor(cardList.get(position).color);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }


    class DashViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView additional;
        TextView additional1;
        TextView additional2;
        ImageView color;
        MaterialRippleLayout materialRippleLayout;

        public DashViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.dashTitle);
            additional = (TextView) itemView.findViewById(R.id.dashAdditional);
            additional1 = (TextView) itemView.findViewById(R.id.dashAdditional1);
            additional2 = (TextView) itemView.findViewById(R.id.dashAdditional2);
            color = (ImageView) itemView.findViewById(R.id.dashColorBar);
            materialRippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.feedbackRipple);
        }

        @Override
        public void onClick(final View v) {
        }
    }

    class dashCard {
        String title;
        String additional[];
        int color;

        public dashCard(String title, String additional[], int color) {
            this.title = title;
            this.additional = additional;
            this.color = color;
        }
    }
}