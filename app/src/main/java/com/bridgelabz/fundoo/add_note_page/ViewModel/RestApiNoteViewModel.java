package com.bridgelabz.fundoo.add_note_page.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bridgelabz.fundoo.add_note_page.Model.AddNoteModel;
import com.bridgelabz.fundoo.add_note_page.Model.NoteResponseModel;
import com.bridgelabz.fundoo.add_note_page.data_manager.RestApiNoteDataManager;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseData;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseError;

import java.io.Serializable;
import java.util.List;

import static com.bridgelabz.fundoo.Utility.AppConstants.ADD_NOTE_ACTION;
import static com.bridgelabz.fundoo.Utility.AppConstants.FETCH_NOTE_ACTION;
import static com.bridgelabz.fundoo.Utility.AppConstants.SET_ARCHIVE_ACTION;

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
            Intent localIntent = new Intent(ADD_NOTE_ACTION);

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
            Intent localIntent = new Intent(FETCH_NOTE_ACTION);

            @Override
            public void onResponse(List<NoteResponseModel> noteModelList, ResponseError responseError) {

                if (noteModelList != null) {
                    Log.e(TAG, "onResponse: successful");
                    localIntent.putExtra("noteList", (Serializable) noteModelList);
                    localBroadcastManager.sendBroadcast(localIntent);
                } else {
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

    public void setArchiveToNote(AddNoteModel addNoteModel) {
        restApiNoteDataManager.setArchive(addNoteModel, new RestApiNoteDataManager.SetArchiveCallback() {
            Intent localIntent = new Intent(SET_ARCHIVE_ACTION);

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
