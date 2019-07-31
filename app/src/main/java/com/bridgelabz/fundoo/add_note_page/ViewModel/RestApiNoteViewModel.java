package com.bridgelabz.fundoo.add_note_page.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bridgelabz.fundoo.add_note_page.Model.AddNoteModel;
import com.bridgelabz.fundoo.add_note_page.Model.NoteListModel;
import com.bridgelabz.fundoo.add_note_page.data_manager.RestApiNoteDataManager;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseData;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseError;

import java.io.Serializable;
import java.util.List;

public class RestApiNoteViewModel {
    private static final String TAG = "RestApiNoteViewModel";

    private RestApiNoteDataManager restApiNoteDataManager;
    private LocalBroadcastManager localBroadcastManager;

    public RestApiNoteViewModel(Context context) {
        restApiNoteDataManager = new RestApiNoteDataManager(context);
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }


    public void addNotes(AddNoteModel noteModel) {
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
            public void onResponse(List<NoteListModel> noteModelList, ResponseError responseError) {

                if(noteModelList != null) {
                    Log.e(TAG, "onResponse: successful");
                    localIntent.putExtra("noteList", (Serializable) noteModelList);
                    localBroadcastManager.sendBroadcast(localIntent);
                }else {
                    Log.e(TAG, "onResponse: unsuccessful");
                    Log.e(TAG, "onResponse: " + responseError.getStatusCode());
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                localIntent.putExtra("throwable", throwable);
                localBroadcastManager.sendBroadcast(localIntent);
            }
        });

    }
    public void setArchiveToNote(){
        restApiNoteDataManager.setArchive(new RestApiNoteDataManager.SetArchiveCallback() {
            Intent localIntent = new Intent("com.bridgelabz.fundoo.set_archive_action");
            @Override
            public void onResponse(ResponseData responseData, ResponseError responseError) {

                boolean isAddedArchive;
                isAddedArchive = (responseData != null);
                Log.e(TAG, "onResponse isAddedArchive: " + isAddedArchive);

                localIntent.putExtra("isNoteArchived", isAddedArchive);
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
