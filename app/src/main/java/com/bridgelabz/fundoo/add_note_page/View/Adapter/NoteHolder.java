package com.bridgelabz.fundoo.add_note_page.View.Adapter;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private ConstraintLayout noteContainerLayout;
    private NotesAdapter.OnItemClickListener listener;
    Animation animFade;

    public NoteHolder(@NonNull final View itemView, NotesAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.tv_title);
        mDescription = itemView.findViewById(R.id.tv_description);
        mReminder = itemView.findViewById(R.id.tv_reminder);
        noteCard = itemView.findViewById(R.id.noteItemCard);
        noteContainerLayout = itemView.findViewById(R.id.layout_item_container);
        reminderCard = itemView.findViewById(R.id.reminderItemCard);
        this.listener = onItemClickListener;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = getAdapterPosition();
                animFade = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.zoom_in_expand);
                noteContainerLayout.startAnimation(animFade);
                if (listener != null) {
                    listener.onItemClick(position);
                }else {
                    Log.e(TAG, "listener is null");
                }
            }
        });
    }

    public void bindNoteToCard(NoteResponseModel note) {
//        animFade = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.zoom_in);
//        noteContainerLayout.startAnimation(animFade);
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
            Log.e(TAG, "bindNoteToCard: hide the card");
            reminderCard.setVisibility(View.GONE);
        }

            if (note.getColor() != null && !note.getColor().isEmpty()) {
                noteCard.setCardBackgroundColor(Color.parseColor(note.getColor()));
            } else {
                noteCard.setCardBackgroundColor(Color.WHITE);
            }
    }

//    private void setReminderView(NoteResponseModel note) {
//       if(!note.getReminder().isEmpty()){
//            mReminder.setVisibility(View.GONE);
//       }
//        else{
//            mReminder.setText(note.getReminder().get(0));
//            mReminder.setBackgroundColor(Color.parseColor(note.getColor()));
//      }
//  }
}