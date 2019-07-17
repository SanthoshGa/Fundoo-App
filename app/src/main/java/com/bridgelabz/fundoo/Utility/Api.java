package com.bridgelabz.fundoo.Utility;

import com.bridgelabz.fundoo.Dashboard.Model.RetrofitLoginModel;
import com.bridgelabz.fundoo.Dashboard.Model.RetrofitModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    String BASE_URL = "http://34.213.106.173/api/";

    @GET("user")
    Call<List<RetrofitModel>> getUsers();

    @POST("user/userSignUp")
    Call<RetrofitModel> signUpUser(@Body RetrofitModel user);

    @POST("user/userLogIn")
    Call<RetrofitLoginModel> logInUser(@Body RetrofitLoginModel model);

}
