package com.bridgelabz.fundoo.add_note_page.View.Adapter;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bridgelabz.fundoo.add_note_page.Model.NoteResponseModel;
import com.bridgelabz.fundoo.R;

public class NoteHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "NoteHolder";
    private TextView mTitle;
    private TextView mDescription;
    private TextView mReminder;
    private CardView noteCard;
    private CardView reminderCard;
    private NotesAdapter.OnItemClickListener listener;

    public NoteHolder(@NonNull View itemView, NotesAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.tv_title);
        mDescription = itemView.findViewById(R.id.tv_description);
        mReminder = itemView.findViewById(R.id.tv_reminder);
        noteCard = itemView.findViewById(R.id.noteItemCard);
        reminderCard = itemView.findViewById(R.id.reminderItemCard);
        this.listener = onItemClickListener;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (listener != null) {
                    listener.onItemClick(position);
                }else {
                    Log.e(TAG, "listener is null");
                }
            }
        });
    }

    public void bindNoteToCard(NoteResponseModel note) {
        mTitle.setText(note.getTitle());
        mDescription.setText(note.getDescription());
//        setReminderView(note);
        if (!note.getReminder().isEmpty()) {
            // show the card
            reminderCard.setCardBackgroundColor(Color.LTGRAY);
            // set the reminder date to reminder text view
            mReminder.setText(note.getReminder().get(0));
            reminderCard.setVisibility(View.VISIBLE);
        } else {
             // hide the card
            reminderCard.setVisibility(View.GONE);
        }
//
        if (note.getColor() != null && !note.getColor().isEmpty()) {
            noteCard.setCardBackgroundColor(Color.parseColor(note.getColor()));
        } else {
            noteCard.setCardBackgroundColor(Color.WHITE);
        }
    }

//    private void setReminderView(NoteResponseModel note) {
//        if(note.getReminder() == null){
//            mReminder.setVisibility(View.GONE);
//        }
//        else{
//            mReminder.setText(note.getReminder());
//            mReminder.setBackgroundColor(Color.parseColor(note.getColor()));
//        }
    }
//}