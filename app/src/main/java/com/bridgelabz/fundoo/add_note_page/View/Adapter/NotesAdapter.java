package com.bridgelabz.fundoo.add_note_page.View.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.bridgelabz.fundoo.add_note_page.Model.BaseNoteModel;
import com.bridgelabz.fundoo.add_note_page.Model.NoteResponseModel;
import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.Utility.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteHolder> implements ItemTouchHelperAdapter, Filterable {

    private List<NoteResponseModel> noteModelArrayList;
    private List<NoteResponseModel> noteModelArrayListFull;

    private ItemTouchHelper itemTouchHelper;
    private OnItemClickListener listener;


    public NotesAdapter(List<NoteResponseModel> noteModelArrayList, OnItemClickListener onItemClickListener) {
        this.noteModelArrayList = noteModelArrayList;
        this.listener = onItemClickListener;
        noteModelArrayListFull = new ArrayList<>(noteModelArrayList);
    }
    @Override
    public NoteHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemview, viewGroup,
                false);
        return new NoteHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(NoteHolder noteHolder, int position) {
        NoteResponseModel note = noteModelArrayList.get(position);
        noteHolder.bindNoteToCard(note);
    }

    @Override
    public int getItemCount() {
        return noteModelArrayList.size();
    }

    public BaseNoteModel getNoteAt(int position){
        return noteModelArrayList.get(position);
    }

    @Override
    public void onItemMove(int draggedPosition, int targetPosition) {
        NoteResponseModel draggedNote = noteModelArrayList.get(draggedPosition);
        noteModelArrayList.remove(draggedNote);
        noteModelArrayList.add(targetPosition, draggedNote);
        notifyItemMoved(draggedPosition, targetPosition);
    }

    @Override
    public void onItemSwiped(int position) {
        noteModelArrayList.remove(position);
        notifyItemRemoved(position);

    }

    public void setTouchHelper(ItemTouchHelper touchHelper){
        this.itemTouchHelper = touchHelper;
    }
    public void setNoteModelArrayList(List noteModelArrayList){
        this.noteModelArrayList = noteModelArrayList;

    }

    public interface OnItemClickListener{
        void onItemClick(int notePosition);
    }

//    public void setOnItemClickListener(OnItemClickListener listener){
//        this.listener = listener;
//
//    }


    @Override
    public Filter getFilter() {
        return noteFilter;
    }
    private Filter noteFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<BaseNoteModel> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0)
            {
                filteredList.addAll(noteModelArrayListFull);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(BaseNoteModel note : noteModelArrayListFull){
                    if(note.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(note);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            noteModelArrayList.clear();
            noteModelArrayList.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };
}