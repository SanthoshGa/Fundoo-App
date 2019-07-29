package com.bridgelabz.fundoo.Dashboard.View.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.bridgelabz.fundoo.Dashboard.Model.NoteModel;
import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.Utility.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteHolder> implements ItemTouchHelperAdapter, Filterable {

    private List<NoteModel> noteModelArrayList;
    private List<NoteModel> noteModelArrayListFull;

    private ItemTouchHelper itemTouchHelper;
    private OnItemClickListener listener;


    public NotesAdapter(List<NoteModel> noteModelArrayList, OnItemClickListener onItemClickListener) {
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
        NoteModel note = noteModelArrayList.get(position);
        noteHolder.bindNoteToCard(note);
    }

    @Override
    public int getItemCount() {
        return noteModelArrayList.size();
    }

    public NoteModel getNoteAt(int position){
        return noteModelArrayList.get(position);
    }

    @Override
    public void onItemMove(int draggedPosition, int targetPosition) {
        NoteModel draggedNote = noteModelArrayList.get(draggedPosition);
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
            List<NoteModel> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0)
            {
                filteredList.addAll(noteModelArrayListFull);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(NoteModel note : noteModelArrayListFull){
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