package com.bridgelabz.fundoo.LoginSignup.Model;


import android.util.Log;

import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseData;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseError;
import com.bridgelabz.fundoo.LoginSignup.Model.RestApiService.UserRestApiService;
import com.bridgelabz.fundoo.common.Utility.RetrofitRestApiConnection;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestApiUserDataManager {

    public static final String TAG = "RestApiUserDataManager";

    // create user
    public void createUser(UserModel userModel, final SignUpCallback signUpCallback) {
        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();

        UserRestApiService apiService = retrofit.create(UserRestApiService.class);
        Call<Map<String, ResponseData>> responseDataCall = apiService.signUpUser(userModel);
        responseDataCall.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call, Response<Map<String,
                    ResponseData>> response) {
                if (response.isSuccessful()) {
                    ResponseData responseData = response.body().get("data");
                    Log.e(TAG, response.body().toString());
                    signUpCallback.onResponse(responseData, null);
                    Log.e(TAG, response.body().get("data") + "");


                } else {
                    try {
                        Log.e(TAG, "Error ResponseModel :" + response.errorBody().string());
                        signUpCallback.onResponse(null, new ResponseError(
                                response.code() + "", response.errorBody().string(),
                                response.message(),
                                null));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {
                Log.e(TAG, throwable.getLocalizedMessage());
                signUpCallback.onFailure(throwable);

            }
        });

    }


    // checkUser
    public void checkUser(UserLoginModel loginModel, final SignInCallback signInCallback) {
        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();

        UserRestApiService apiService = retrofit.create(UserRestApiService.class);
        Call<UserModel> responseDataCall = apiService.logInUser(loginModel);
        responseDataCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel userModel = response.body();
                    Log.e(TAG, " ResponseModel :" +  response.body() + "");
                    signInCallback.onResponse(userModel, null);
                } else {

                    try {
                        Log.e(TAG, "ERROR ResponseModel :" + response.errorBody().string() + "");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable throwable) {

                Log.e(TAG, throwable.getLocalizedMessage());
                signInCallback.onFailure(throwable);
            }
        });

    }

    public void  uploadImage(File file, final UploadImageCallback uploadImageCallback){
        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        UserRestApiService apiService = retrofit.create(UserRestApiService.class);
        Call<UserModel> responseDataCall = apiService.uploadImage(file);
        responseDataCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel userModel = response.body();
                    Log.e(TAG, " ResponseModel :" +  response.body() + "");
                    uploadImageCallback.onResponse(userModel, null);
                }
                else{
                    try {
                        Log.e(TAG, "ERROR ResponseModel :" + response.errorBody().string() + "");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable throwable) {
                Log.e(TAG, throwable.getLocalizedMessage());
                uploadImageCallback.onFailure(throwable);
            }
        });
    }


    public interface SignUpCallback {
        void onResponse(ResponseData responseData, ResponseError responseError);

        void onFailure(Throwable throwable);
    }

    public interface SignInCallback {
        void onResponse(UserModel userModel, ResponseError responseError);

        void onFailure(Throwable throwable);
    }
    public interface UploadImageCallback{
        void onResponse(UserModel userModel, ResponseError responseError);
        void onFailure(Throwable throwable);
    }
}

//
//    Call<UserModel> call = apiService.signUpUser(userModel);
//        call.enqueue(new Callback<UserModel>() {
//@Override
//public void onResponse(Call<UserModel> call, ResponseModel<UserModel> response) {
//
//        Map<String, Map<String, Object>> response = response.body();
//        if(response.isSuccessful()){
//        Log.e(TAG, "response is :" + userModel1.toString());
//        userCallback.onResponse(response.body(), HttpResponseCode.SUCCESS);
//        }
//        else{
//        Log.e(TAG, "response:" + response.code());
//        if(response.code() == HttpResponseCode.INTERNAL_SERVER_ERROR.getHttpCode()) {
//        userCallback.onResponse(null, HttpResponseCode
//        .INTERNAL_SERVER_ERROR);
//        }
//        }
//        }
//
//@Override
//public void onFailure(Call<UserModel> call, Throwable throwable) {
//        Log.e(TAG, throwable.getLocalizedMessage());
//        userCallback.onFailure(throwable);
//        }
//        });