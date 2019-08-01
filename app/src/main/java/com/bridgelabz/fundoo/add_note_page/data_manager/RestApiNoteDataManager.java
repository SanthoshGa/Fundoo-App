package com.bridgelabz.fundoo.add_note_page.data_manager;

import android.content.Context;
import android.util.Log;

import com.bridgelabz.fundoo.add_note_page.Model.AddNoteModel;
import com.bridgelabz.fundoo.add_note_page.Model.NoteResponseModel;
import com.bridgelabz.fundoo.add_note_page.data_manager.apis.NoteRestApiService;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseData;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseError;
import com.bridgelabz.fundoo.Utility.RetrofitRestApiConnection;
import com.bridgelabz.fundoo.common.SharedPreferencesManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestApiNoteDataManager {

    public static final String TAG = "RestApiNoteDataManager";
    private SharedPreferencesManager sharedPreferencesManager;

    public RestApiNoteDataManager(Context context) {
        sharedPreferencesManager = new SharedPreferencesManager(context);
    }

    public void addNote(AddNoteModel noteModel, final AddNoteCallback addNoteCallback) {
        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApiService apiService = retrofit.create(NoteRestApiService.class);
        String authKey = sharedPreferencesManager.getAccessToken();

        Log.e(TAG, "addNote: BaseNoteModel :" + noteModel.toString());

        Call<Map<String, ResponseData>> responseDataCall = apiService.addNotes(authKey, noteModel);
        responseDataCall.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call, Response<Map<String,
                    ResponseData>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "RESPONSE IS SUCCESSFUL");
                    ResponseData responseData = response.body().get("status");
                    Log.e(TAG, response.body().toString());
                    Log.e(TAG, responseData.toString());
                    addNoteCallback.onResponse(responseData, null);
//                    Map<ResponseData, ResponseError> responseErrorMap = new HashMap<>();
//                    responseErrorMap.put(responseData, null);
//                    observableResponse.setData(Map<>);
                } else {
                    try {
                        Log.e(TAG, "Error ResponseModel :" + response.errorBody().string());
                        addNoteCallback.onResponse(null, new ResponseError(
                                response.code() + "", response.errorBody().string(),
                                response.message(),
                                null)
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {
                Log.e(TAG, throwable.getLocalizedMessage());
                addNoteCallback.onFailure(throwable);

            }
        });


    }

    public void getNoteList(final GetNotesCallback getNotesCallback) {
        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApiService apiService = retrofit.create(NoteRestApiService.class);
        String authKey = sharedPreferencesManager.getAccessToken();

        Call<Map<String, ResponseData>> responseDataCall = apiService.getNoteList(authKey);
        responseDataCall.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call, Response<Map<String, ResponseData>> response) {
                if(response.isSuccessful()){
                    Log.e(TAG, "onResponseSuccessful: getNotesList - " + response.body()
                            .toString());
                    ResponseData responseData = response.body().get("data");
                    Log.e(TAG,  responseData.getNoteModelList() + "");
                    getNotesCallback.onResponse(responseData.getNoteModelList(), null);
                }
                else{
                    try {
                        getNotesCallback.onResponse(null, new ResponseError(
                                response.code() + "", response.errorBody().string(),
                                response.message(),
                                null) );
                        Log.e(TAG, "onResponseError: getNotesList - " + response.errorBody()
                                .toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {
                Log.e(TAG, "Throwable: getNotesList - " + throwable.getLocalizedMessage());
                getNotesCallback.onFailure(throwable);

            }
        });

    }

    public void setArchive(AddNoteModel noteModel, final SetArchiveCallback setArchiveCallback){
        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApiService apiService = retrofit.create(NoteRestApiService.class);
        String authKey = sharedPreferencesManager.getAccessToken();

        Map<String, Object> archiveDataMap = new HashMap<>();
        List<String> noteIdList = new ArrayList<>();
        noteIdList.add(noteModel.getId());
        archiveDataMap.put("isArchived", true);
        archiveDataMap.put("noteIdList", noteIdList);
        Call<Map<String, ResponseData>> responseDataCall = apiService.setArchiveToNote(authKey,
                archiveDataMap);
        responseDataCall.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call, Response<Map<String, ResponseData>> response) {
                if(response.isSuccessful()){
                    Log.e(TAG, "Response is successful");
                    Log.e(TAG, "onResponse : setArchiveToNote " + response.body().toString());
                    ResponseData responseData = response.body().get("data");
                    setArchiveCallback.onResponse(responseData, null);
                }
                else{
                    Log.e(TAG, "Error Response Model :" + response.errorBody().toString());
                    try {
                        setArchiveCallback.onResponse(null, new ResponseError(response.code() + "",
                                response.errorBody().string(),
                                response.message(),
                                null));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {
                Log.e(TAG, "Throwable : setArchiveToNote" + throwable.getLocalizedMessage());
                setArchiveCallback.onFailure(throwable);

            }
        });
    }


    public void updateNotes(AddNoteModel addNoteModel, final ResponseCallback responseCallback){
        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApiService apiService = retrofit.create(NoteRestApiService.class);
        String authKey = sharedPreferencesManager.getAccessToken();

        Map<String, String> updateNoteMap = new HashMap<>();
        List<String> noteIdList = new ArrayList<>();
        noteIdList.add(addNoteModel.getId());
        updateNoteMap.put("noteIdList", String.valueOf(noteIdList));
        updateNoteMap.put("title", addNoteModel.getTitle());
        updateNoteMap.put("description", addNoteModel.getDescription());

        Call<Map<String, ResponseData>>  responseDataCall = apiService.updateNotes(authKey, updateNoteMap);
        responseDataCall.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call, Response<Map<String, ResponseData>> response) {
                if(response.isSuccessful()){
                    Log.e(TAG, "Response is Successful");
                    Log.e(TAG, "onResponse: updateNote" + response.body().toString());
                    ResponseData responseData = response.body().get("data");
                    responseCallback.onResponse(responseData, null);
                }
                else{
                    Log.e(TAG, "Error ResponseModel " + response.errorBody().toString());
                    try {
                        responseCallback.onResponse(null, new ResponseError(response.code() + "",
                                response.errorBody().string(),
                                response.message(),
                                null));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {
                Log.e(TAG, "onFailure: updateNotes" + throwable.getLocalizedMessage());
                responseCallback.onFailure(throwable);
            }
        });

    }



    public interface AddNoteCallback {
        void onResponse(ResponseData responseData, ResponseError responseError);

        void onFailure(Throwable throwable);
    }

    public interface ResponseCallback<T, E> {
        void onResponse(T data, E error);
        void onFailure(Throwable throwable);
    }


    public interface GetNotesCallback {
        void onResponse(List<NoteResponseModel> noteModelList, ResponseError responseError);

        void onFailure(Throwable throwable);
    }

    public interface SetArchiveCallback{
        void  onResponse(ResponseData responseData, ResponseError responseError);

        void onFailure(Throwable throwable);
    }

    public interface UpdateNoteCallback{
        void  onResponse(ResponseData responseData, ResponseError responseError);

        void onFailure(Throwable throwable);
    }
}
