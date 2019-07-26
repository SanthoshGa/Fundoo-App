package com.bridgelabz.fundoo.Dashboard.ViewModel;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bridgelabz.fundoo.Dashboard.Model.NoteModel;
import com.bridgelabz.fundoo.Dashboard.data_manager.RestApiNoteDataManager;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseData;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseError;

public class RestApiNoteViewModel {

    private Context mContext;

    public RestApiNoteViewModel(Context mContext) {
        this.mContext = mContext;
    }

    public void addNotes(NoteModel noteModel) {
        final LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(mContext);

        new RestApiNoteDataManager().addNotes(noteModel, new RestApiNoteDataManager.AddNoteCallback() {
            Intent localIntent = new Intent("com.bridgelabz.fundoo.added_note_action");

            @Override
            public void onResponse(ResponseData responseData, ResponseError responseError) {

                boolean isAddedStatus;
                isAddedStatus = (responseData != null);
                localIntent.putExtra("isNoteAdded", isAddedStatus);
                localBroadcastManager.sendBroadcast(localIntent);
            }

            @Override
            public void onFailure(Throwable throwable) {
                localIntent.putExtra("throwable", throwable);
                localBroadcastManager.sendBroadcast(localIntent);
            }
        });

    }
}
