package com.bridgelabz.fundoo.Dashboard.Model;


import android.util.Log;

import com.bridgelabz.fundoo.LoginSignup.Model.RestApiService.UserRestApiService;
import com.bridgelabz.fundoo.Utility.RetrofitRestApiConnection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestApiUserDataManager {

    public static final String TAG = "RestApiUserDataManager";
    private RestApiUserDataManager restApiUserDataManager;


    // create user
    public void createUser(UserModel userModel, final SignUpCallback userCallback) {
        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();

        UserRestApiService apiService = retrofit.create(UserRestApiService.class);
        Call<Map<String, ResponseData>> responseDataCall = apiService.signUpUser(userModel);
        responseDataCall.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call, Response<Map<String,
                    ResponseData>> response) {
                if (response.isSuccessful()) {
                    ResponseData responseData = response.body().get("data");
//                    Log.e(TAG,  (response.body().get("data") == null) + "");
                    Log.e(TAG, response.body().get("data") + "");

//                    System.out.println(responseData.getSuccess() + " " + responseData.getMessage());
                } else {

                    Gson gson = new Gson();
                    Type type = new TypeToken<Map<String, ResponseError>>() {
                    }.getType();

                    try {
                        Log.e(TAG, response.errorBody().string());
                        Map<String, ResponseError> jsonMap = gson.fromJson(response.errorBody()
                                .string(), type);

//                        ResponseError responseError = new ResponseError(jsonMap.get
//                                ("error").get("statusCode").toString(), null, jsonMap.get
//                                ("error").get("message").toString(), null);
//                        System.out.println(responseError.getStatusCode() + " " +
//                                responseError.getMessage());
                        System.out.println(jsonMap.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable t) {

            }
        });

//        responseDataCall.enqueue(new Callback<ResponseData>() {
//            @Override
//            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
//                if (response.isSuccessful()) {
//                    ResponseData responseData = response.body();
//                    Log.e(TAG, response.body().getSuccess() + "");
//                    System.out.println(responseData.getSuccess() + " " + responseData.getMessage());
//                } else {
//
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<Map<String, Map<String, Object>>>() {
//                    }.getType();
//                    try {
//                        Log.e(TAG, response.errorBody().string());
//                        Map<String, Map<String, Object>> jsonMap =  gson.fromJson(response.errorBody()
//                                .string(), type);
//
////                        ResponseError responseError = new ResponseError(jsonMap.get
////                                ("error").get("statusCode").toString(), null, jsonMap.get
////                                ("error").get("message").toString(), null);
////                        System.out.println(responseError.getStatusCode() + " " +
////                                responseError.getMessage());
//                        System.out.println(jsonMap.toString());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseData> call, Throwable t) {
//
//            }
//        });


//        Call<Map<String, Map<String, Object>>> call = apiService.signUpUser(userModel);
//        call.enqueue(new Callback<Map<String, Map<String, Object>>>() {
//            @Override
//            public void onResponse(Call<Map<String, Map<String, Object>>> call,
//                                   Response<Map<String, Map<String, Object>>> response) {
//                Map<String, Map<String, Object>> responseModel = response.body();
//                if (response.isSuccessful()) {
//                    if (responseModel.containsKey("data")) {
//                        Log.e(TAG, "response is :" + responseModel.get("data"));
//                    }
//
////                    userCallback.onResponse(response.body(), HttpResponseCode.SUCCESS);
//                } else {
////                     if(responseModel.containsKey("error")) {
////                        Log.e(TAG, "response is :" + responseModel.get("error") );
////                    }
//
//                    try {
//                        Log.e(TAG, "response is :" + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//////                    Log.e(TAG, "response is :" + response.code());
////                    if (response.code() == HttpResponseCode.INTERNAL_SERVER_ERROR.getHttpCode()) {
//////                        userCallback.onResponse(null, );
////                        Log.e(TAG, "RESPONSE IS : " + response.errorBody().toString());
////                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Map<String, Map<String, Object>>> call, Throwable throwable) {
//                userCallback.onFailure(throwable);
//            }
//        });


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
                    Log.e(TAG, " Response :" +  response.body() + "");
                    signInCallback.onResponse(userModel, null);
                } else {

                    try {
                        Log.e(TAG, "ERROR Response :" + response.errorBody().string() + "");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable throwable) {

                Log.e(TAG, throwable.getLocalizedMessage());

            }
        });

//        responseDataCall.enqueue(new Callback<UserModel>() {
//            @Override
//            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
//
//                if (response.isSuccessful()) {
//                    UserModel userModel = response.body();
//                    Log.e(TAG, response.body() + "");
//                } else {
//                    Log.e(TAG, response.errorBody().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Map<String, UserModel>> call, Throwable t) {
//
//
//            }
//        });
//        Call<Map<String, ResponseData>> responseDataCall = apiService.logInUser(loginModel);
//
//        responseDataCall.enqueue(new Callback<Map<String, ResponseData>>() {
//            @Override
//            public void onResponse(Call<Map<String, ResponseData>> call, Response<Map<String,
//                    ResponseData>> response) {
//                if(response.isSuccessful()){
//                    ResponseData responseData = response.body().get("data");
//                    Log.e(TAG, response.body().get("data") + "");
//
//                }
//                else{
//
//                    Log.e(TAG, response.errorBody().toString());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Map<String, ResponseData>> call, Throwable t) {
//
//            }
//        });


    }


    public interface SignUpCallback {
        void onResponse(ResponseData responseData, ResponseError responseError);

        void onFailure(Throwable throwable);
    }

    public interface SignInCallback {
        void onResponse(UserModel userModel, ResponseError responseError);

        void onFailure(Throwable throwable);
    }
}

//
//    Call<UserModel> call = apiService.signUpUser(userModel);
//        call.enqueue(new Callback<UserModel>() {
//@Override
//public void onResponse(Call<UserModel> call, Response<UserModel> response) {
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