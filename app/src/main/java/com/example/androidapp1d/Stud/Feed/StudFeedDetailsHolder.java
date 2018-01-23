package com.example.androidapp1d.Stud.Feed;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidapp1d.R;
import com.example.androidapp1d.Stud.Booking.CardItemClickListener;

/**
 * Created by ASUS on 12/5/2017.
 */

public class StudFeedDetailsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView announcement, mod, prof, creator, description;
    Button details;

    CardItemClickListener itemClickListener;

    public StudFeedDetailsHolder(View itemView) {
        super(itemView);
        this.announcement = (TextView) itemView.findViewById(R.id.announcement);
        this.mod = (TextView) itemView.findViewById(R.id.mod);
        this.prof = (TextView) itemView.findViewById(R.id.feedProf);
        this.creator = (TextView) itemView.findViewById(R.id.creator);
        this.description = (TextView) itemView.findViewById(R.id.feedDescription);
        this.details = (Button) itemView.findViewById(R.id.feedDetailsButton);

        details.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    public void setItemClickListener(CardItemClickListener ic){
        this.itemClickListener = ic;
    }
}
