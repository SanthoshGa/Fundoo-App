package com.bridgelabz.fundoo.Dashboard.data_manager;

import android.content.Context;
import android.util.Log;

import com.bridgelabz.fundoo.Dashboard.Model.NoteModel;
import com.bridgelabz.fundoo.Dashboard.data_manager.apis.NoteRestApiService;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseData;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseError;
import com.bridgelabz.fundoo.Utility.RetrofitRestApiConnection;
import com.bridgelabz.fundoo.common.SharedPreferencesManager;

import java.io.IOException;
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

    public void addNote(NoteModel noteModel, final AddNoteCallback addNoteCallback) {
        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApiService apiService = retrofit.create(NoteRestApiService.class);
        String authKey = sharedPreferencesManager.getAccessToken();

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
                    Log.e(TAG, "onResponse: response is successful");
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {
                Log.e(TAG, throwable.getLocalizedMessage());
                getNotesCallback.onFailure(throwable);

            }
        });

    }

    public interface AddNoteCallback {
        void onResponse(ResponseData responseData, ResponseError responseError);

        void onFailure(Throwable throwable);
    }

    public interface GetNotesCallback {
        void onResponse(List<NoteModel> noteModelList, ResponseError responseError);

        void onFailure(Throwable throwable);
    }
}
