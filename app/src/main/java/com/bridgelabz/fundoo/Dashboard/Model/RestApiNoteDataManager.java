package com.bridgelabz.fundoo.Dashboard.Model;

import android.util.Log;

import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseData;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseError;
import com.bridgelabz.fundoo.ObserverPattern.Observable;
import com.bridgelabz.fundoo.ObserverPattern.ObservableImpl;
import com.bridgelabz.fundoo.Utility.RetrofitRestApiConnection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestApiNoteDataManager {

    public static final String TAG = "RestApiNoteDataManager";
    private Map<String, Response> dataResponseErrorMap = new HashMap<>();
    private ObservableImpl<Map<String, Response>> observableResponse =
            new ObservableImpl<>(dataResponseErrorMap);

    public void addNotes(NoteModel noteModel, final AddNoteCallback addNoteCallback) {
        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApiService apiService = retrofit.create(NoteRestApiService.class);
        Call<Map<String, ResponseData>> responseDataCall = apiService.addNotes(noteModel);
        responseDataCall.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call, Response<Map<String, ResponseData>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "RESPONSE IS SUCCESSFUL");
                    ResponseData responseData = response.body().get("data");
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

    public Observable<Map<ResponseData, ResponseError>> getObservableResponse() {
        return observableResponse;
    }

    public interface AddNoteCallback {
        void onResponse(ResponseData responseData, ResponseError responseError);

        void onFailure(Throwable throwable);
    }
}
