package com.bridgelabz.fundoo.add_label_page.view_model;

import android.content.Context;

import com.bridgelabz.fundoo.add_label_page.model.Label;
import com.bridgelabz.fundoo.add_label_page.model.LabelDatabaseManager;

import java.util.List;

public class LabelViewModel {
    private LabelDatabaseManager labelDbManager;

    public LabelViewModel(Context context){
        labelDbManager = new LabelDatabaseManager(context);
    }

    public boolean addLabel(Label label){
        return labelDbManager.addLabel(label);
    }
    public boolean deleteLabel(Label label){
        return labelDbManager.deleteLabel(label);
    }
    public boolean updateLabel(Label label){
        return labelDbManager.updateLabel(label);
    }
    public List<Label> getAllLabelData(){
        return labelDbManager.getAllLabelData();
    }
    public boolean deleteAllLabels(){
        return labelDbManager.deleteAllLabels();
    }
}
