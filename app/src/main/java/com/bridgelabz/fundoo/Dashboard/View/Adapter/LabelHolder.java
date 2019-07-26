package com.bridgelabz.fundoo.Dashboard.View.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bridgelabz.fundoo.label_page.model.Label;
import com.bridgelabz.fundoo.R;

class LabelHolder extends RecyclerView.ViewHolder{

    private LabelAdapter.OnItemClickListener listener;
    private TextView mLabel;
    public LabelHolder(@NonNull View labelItemView, LabelAdapter.OnItemClickListener onItemClickListener) {
        super(labelItemView);
        mLabel = labelItemView.findViewById(R.id.tv_label_item_view);
        this.listener = onItemClickListener;

    }
    public void bindNoteToCard(Label label){
        mLabel.setText(label.getLabelName());

    }
}
