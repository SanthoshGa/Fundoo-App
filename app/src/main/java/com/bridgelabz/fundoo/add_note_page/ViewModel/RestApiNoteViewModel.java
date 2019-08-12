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

import static com.bridgelabz.fundoo.common.Utility.AppConstants.ADD_NOTE_ACTION;
import static com.bridgelabz.fundoo.common.Utility.AppConstants.ADD_REMINDER_TO_NOTES_ACTION;
import static com.bridgelabz.fundoo.common.Utility.AppConstants.CHANGE_COLOR_TO_NOTE_ACTION;
import static com.bridgelabz.fundoo.common.Utility.AppConstants.FETCH_NOTE_ACTION;
import static com.bridgelabz.fundoo.common.Utility.AppConstants.GET_ARCHIVE_NOTES_ACTION;
import static com.bridgelabz.fundoo.common.Utility.AppConstants.GET_REMINDER_NOTES_LIST_ACTION;
import static com.bridgelabz.fundoo.common.Utility.AppConstants.GET_TRASH_NOTES_LIST_ACTION;
import static com.bridgelabz.fundoo.common.Utility.AppConstants.PIN_UNPIN_TO_NOTE_ACTION;
import static com.bridgelabz.fundoo.common.Utility.AppConstants.SET_ARCHIVE_ACTION;
import static com.bridgelabz.fundoo.common.Utility.AppConstants.TRASH_NOTE_ACTION;
import static com.bridgelabz.fundoo.common.Utility.AppConstants.UPDATE_NOTE_ACTION;

public class RestApiNoteViewModel {
    private static final String TAG = "RestApiNoteViewModel";

    private RestApiNoteDataManager restApiNoteDataManager;
    private LocalBroadcastManager localBroadcastManager;

    public RestApiNoteViewModel(Context context) {
        restApiNoteDataManager = new RestApiNoteDataManager(context);
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }


    public void addNotes(AddNoteModel noteModel) {
        restApiNoteDataManager.addNote(noteModel, new RestApiNoteDataManager.
                ResponseCallback<ResponseData, ResponseError>() {
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
        restApiNoteDataManager.getNoteList(new RestApiNoteDataManager.
                ResponseCallback<List<NoteResponseModel>, ResponseError>() {
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

    public void getArchiveNotesList() {
        restApiNoteDataManager.getArchiveNotesList(new RestApiNoteDataManager.
                ResponseCallback<List<NoteResponseModel>, ResponseError>() {
            Intent localIntent = new Intent(GET_ARCHIVE_NOTES_ACTION);

            @Override
            public void onResponse(List<NoteResponseModel> noteModelList, ResponseError responseError) {
                if (noteModelList != null) {
                    Log.e(TAG, "onResponse : archiveList Successful");

                    localIntent.putExtra("archiveNoteList", (Serializable) noteModelList);
                    localBroadcastManager.sendBroadcast(localIntent);
                } else {
                    Log.e(TAG, "onResponse: archiveList Unsuccessful");
                    Log.e(TAG, "onResponse: archive " + responseError.getStatusCode());
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
        restApiNoteDataManager.setArchive(addNoteModel, new RestApiNoteDataManager.
                ResponseCallback<ResponseData, ResponseError>() {
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

    public void updateNotes(AddNoteModel addNoteModel) {
        restApiNoteDataManager.updateNotes(addNoteModel, new RestApiNoteDataManager.ResponseCallback
                <ResponseData, ResponseError>() {

            Intent localIntent = new Intent(UPDATE_NOTE_ACTION);

            @Override
            public void onResponse(ResponseData data, ResponseError error) {
                boolean isNoteEdit;
                isNoteEdit = (data != null);
                Log.e(TAG, "onResponse isNoteEdit: " + isNoteEdit);

                localIntent.putExtra("isNoteEdit", isNoteEdit);
                localBroadcastManager.sendBroadcast(localIntent);
            }

            @Override
            public void onFailure(Throwable throwable) {
                localIntent.putExtra("error", throwable.getLocalizedMessage());
                localBroadcastManager.sendBroadcast(localIntent);

            }
        });
    }

    public void changeColorToNote(AddNoteModel addNoteModel) {
        restApiNoteDataManager.changeColorToNotes(addNoteModel, new RestApiNoteDataManager.ResponseCallback
                <ResponseData, ResponseError>() {
            Intent localIntent = new Intent(CHANGE_COLOR_TO_NOTE_ACTION);

            @Override
            public void onResponse(ResponseData data, ResponseError error) {
                boolean isColorEdit;
                isColorEdit = (data != null);
                Log.e(TAG, "onResponse: isColorEdit " + isColorEdit);

                localIntent.putExtra("isColorEdit", isColorEdit);
                localBroadcastManager.sendBroadcast(localIntent);
            }

            @Override
            public void onFailure(Throwable throwable) {
                localIntent.putExtra("throwable", throwable);
                localBroadcastManager.sendBroadcast(localIntent);

            }
        });
    }

    public void pinUnpinToNote(AddNoteModel addNoteModel) {
        restApiNoteDataManager.pinUnpinToNote(addNoteModel, new RestApiNoteDataManager.ResponseCallback
                <ResponseData, ResponseError>() {
            Intent localIntent = new Intent(PIN_UNPIN_TO_NOTE_ACTION);

            @Override
            public void onResponse(ResponseData data, ResponseError error) {
                boolean isPinned;
                isPinned = (data != null);
                Log.e(TAG, "onResponse: isPinned" + isPinned);
                localIntent.putExtra("isPinned", isPinned);
                localBroadcastManager.sendBroadcast(localIntent);
            }

            @Override
            public void onFailure(Throwable throwable) {
                localIntent.putExtra("throwable", throwable);
                localBroadcastManager.sendBroadcast(localIntent);
            }
        });
    }

    public void trashNotes(NoteResponseModel noteResponseModel) {
        restApiNoteDataManager.trashedNotes(noteResponseModel, new RestApiNoteDataManager.ResponseCallback
                <ResponseData, ResponseError>() {
            Intent localIntent = new Intent(TRASH_NOTE_ACTION);

            @Override
            public void onResponse(ResponseData data, ResponseError error) {
                boolean isTrashed;
                isTrashed = (data != null);
                Log.e(TAG, "onResponse: isTrashed" + isTrashed);
                localIntent.putExtra("isTrashed", isTrashed);
                localBroadcastManager.sendBroadcast(localIntent);
            }

            @Override
            public void onFailure(Throwable throwable) {
                localIntent.putExtra("throwable", throwable);
                localBroadcastManager.sendBroadcast(localIntent);
            }
        });
    }

    public void getTrashNotesList(){
        restApiNoteDataManager.getTrashNotesList(new RestApiNoteDataManager.
                ResponseCallback<List<NoteResponseModel>, ResponseError>() {
            Intent localIntent = new Intent(GET_TRASH_NOTES_LIST_ACTION);
            @Override
            public void onResponse(List<NoteResponseModel> noteModelList, ResponseError responseError) {
                if (noteModelList != null) {
                    Log.e(TAG, "onResponseTrashNotesList: successful");
                    localIntent.putExtra("trashNotesList", (Serializable) noteModelList);
                    localBroadcastManager.sendBroadcast(localIntent);
                } else {
                    Log.e(TAG, "onResponseTrashNotesList: unsuccessful");
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
    public void getReminderNotesList(){
        restApiNoteDataManager.getReminderNotesList(new RestApiNoteDataManager.
                ResponseCallback<List<NoteResponseModel>, ResponseError>() {
            Intent localIntent = new Intent(GET_REMINDER_NOTES_LIST_ACTION);
            @Override
            public void onResponse(List<NoteResponseModel> noteModelList, ResponseError responseError) {
                if (noteModelList != null){
                    Log.e(TAG, "onResponse: getReminderNotesList Successful");
                    localIntent.putExtra("reminderNotesList", (Serializable) noteModelList);
                    localBroadcastManager.sendBroadcast(localIntent);
                }else {
                    Log.e(TAG, "onResponseReminderNotesList: unsuccessful");
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

    public void addReminderToNotes(AddNoteModel addNoteModel){
        Log.e(TAG, "addReminderToNotes: " +  addNoteModel.getReminder());

        restApiNoteDataManager.addReminder(addNoteModel, new RestApiNoteDataManager.
                ResponseCallback<ResponseData, ResponseError>() {
            Intent localIntent = new Intent(ADD_REMINDER_TO_NOTES_ACTION);
            @Override
            public void onResponse(ResponseData data, ResponseError error) {
                boolean addReminder;
                addReminder = (data != null);
                Log.e(TAG, "onResponse: addReminder" + addReminder);
                localIntent.putExtra("addReminder", addReminder);
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
