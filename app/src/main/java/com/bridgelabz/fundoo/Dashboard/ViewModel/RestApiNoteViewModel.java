package com.bridgelabz.fundoo.Dashboard.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bridgelabz.fundoo.Dashboard.Model.NoteModel;
import com.bridgelabz.fundoo.Dashboard.data_manager.RestApiNoteDataManager;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseData;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseError;

import java.io.Serializable;
import java.util.List;

public class RestApiNoteViewModel {
    private static final String TAG = "RestApiNoteViewModel";

    RestApiNoteDataManager restApiNoteDataManager;
    LocalBroadcastManager localBroadcastManager;

    public RestApiNoteViewModel(Context context) {
        restApiNoteDataManager = new RestApiNoteDataManager(context);
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }


    public void addNotes(NoteModel noteModel) {
        restApiNoteDataManager.addNote(noteModel, new RestApiNoteDataManager.AddNoteCallback() {
            Intent localIntent = new Intent("com.bridgelabz.fundoo.added_note_action");

            @Override
            public void onResponse(ResponseData responseData, ResponseError responseError) {

                boolean isAddedStatus;
                isAddedStatus = (responseData != null);
                Log.e(TAG, "onResponse isAdded: " + isAddedStatus);
//                Log.e(TAG, "onResponse ResponseData: " + responseData.toString());


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

    public void fetchNoteList() {
        restApiNoteDataManager.getNoteList(new RestApiNoteDataManager.GetNotesCallback() {
            Intent localIntent = new Intent("com.bridgelabz.fundoo.fetch_notes_action");
            @Override
            public void onResponse(List<NoteModel> noteModelList, ResponseError responseError) {

                localIntent.putExtra("noteList", (Serializable) noteModelList);
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
