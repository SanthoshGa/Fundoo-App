package com.bridgelabz.fundoo.Dashboard.Model;


import android.util.Log;

import com.bridgelabz.fundoo.Utility.HttpCodeUtil.HttpResponseCode;
import com.bridgelabz.fundoo.Utility.RetrofitRestApiConnection;
import com.bridgelabz.fundoo.Utility.UserRestApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestApiUserDataManager {

    public static final String TAG = "RestApiUserDataManager";
    private RestApiUserDataManager restApiUserDataManager;

    public void createUser(UserModel userModel, final RetroFitUserCallback userCallback){
        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();

        UserRestApiService apiService = retrofit.create(UserRestApiService.class);

        Call<UserModel> call = apiService.signUpUser(userModel);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                UserModel userModel1 = response.body();
                if(response.isSuccessful()){
                    Log.e(TAG, "response is :" + userModel1.toString());
                    userCallback.onResponse(response.body(), HttpResponseCode.SUCCESS);
                }
                else{
                    Log.e(TAG, "response:" + response.code());
                    if(response.code() == HttpResponseCode.INTERNAL_SERVER_ERROR.getErrorCode()) {
                        userCallback.onResponse(null, HttpResponseCode
                                .INTERNAL_SERVER_ERROR);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable throwable) {
                Log.e(TAG, throwable.getLocalizedMessage());
                userCallback.onFailure(throwable);
            }
        });

    }

    public interface RetroFitUserCallback {
        void onResponse(UserModel userModel, HttpResponseCode httpResponseCode);
        void onFailure(Throwable throwable);
    }
}


