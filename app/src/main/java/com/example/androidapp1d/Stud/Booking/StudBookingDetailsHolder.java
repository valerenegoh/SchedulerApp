package com.example.androidapp1d.Stud.Booking;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidapp1d.R;

/**
 * Created by ASUS on 12/5/2017.
 */

public class StudBookingDetailsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title, prof, venue, date, description;
    Button details, delete;

    CardItemClickListener itemClickListener;

    public StudBookingDetailsHolder(View itemView) {
        super(itemView);
        this.title = (TextView) itemView.findViewById(R.id.bookingsTitle);
        this.prof = (TextView) itemView.findViewById(R.id.bookingsProf);
        this.venue = (TextView) itemView.findViewById(R.id.bookingsVenue);
        this.date = (TextView) itemView.findViewById(R.id.bookingsDate);
        this.description = (TextView) itemView.findViewById(R.id.bookingsDescription);
        this.details = (Button) itemView.findViewById(R.id.bookingcardDetailsButton);
        this.delete = (Button) itemView.findViewById(R.id.bookingcardDeleteorWithdrawButton);

        details.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    public void setItemClickListener(CardItemClickListener ic){
        this.itemClickListener = ic;
    }
}
