package com.bridgelabz.fundoo.Dashboard.View.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bridgelabz.fundoo.Dashboard.Model.Label;
import com.bridgelabz.fundoo.R;

import java.util.List;

public class LabelAdapter extends RecyclerView.Adapter<LabelHolder>
{
    private List<Label> labelModelArrayList;
    private OnItemClickListener listener;
    public LabelAdapter(List<Label> labelModelArrayList, OnItemClickListener onItemClickListener){
        this.labelModelArrayList = labelModelArrayList;
        this.listener = onItemClickListener;
    }
    @Override
    public LabelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.label_itemview, viewGroup,
                false);
        return new LabelHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull LabelHolder labelHolder, int position) {
        Label label = labelModelArrayList.get(position);
        labelHolder.bindNoteToCard(label);
    }

    @Override
    public int getItemCount() {
        return labelModelArrayList.size();
    }
    public interface OnItemClickListener{
        void onItemClick(int labelPosition);
    }
}
