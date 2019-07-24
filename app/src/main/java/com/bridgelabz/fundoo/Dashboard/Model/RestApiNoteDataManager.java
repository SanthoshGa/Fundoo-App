package com.bridgelabz.fundoo.Dashboard.Model;

import android.util.Log;

import com.bridgelabz.fundoo.LoginSignup.Model.ResponseData;
import com.bridgelabz.fundoo.LoginSignup.Model.ResponseError;
import com.bridgelabz.fundoo.Utility.RetrofitRestApiConnection;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestApiNoteDataManager {

    public static final String TAG = "RestApiNoteDataManager";


    public  void addNotes(NoteModel noteModel, final  AddNoteCallback addNoteCallback ){

        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApiService apiService = retrofit.create(NoteRestApiService.class);
        Call<Map<String, ResponseData>> responseDataCall = apiService.addNotes(noteModel);
        responseDataCall.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call, Response<Map<String, ResponseData>> response) {
                if(response.isSuccessful()){
                    ResponseData responseData = response.body().get("data");
                    addNoteCallback.onResponse(responseData, null);
                }
                else{
                    try {
                        Log.e(TAG, "Error Response :" + response.errorBody().string());
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

    public interface AddNoteCallback {
        void onResponse(ResponseData responseData, ResponseError responseError);

        void onFailure(Throwable throwable);
    }
}
