package com.bridgelabz.fundoo.Utility;

import com.bridgelabz.fundoo.Dashboard.Model.RetrofitLoginModel;
import com.bridgelabz.fundoo.Dashboard.Model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserRestApiService {

    @GET("user")
    Call<List<UserModel>> getUsers();

    @POST("user/userSignUp")
    Call<UserModel> signUpUser(@Body UserModel user);

    @POST("user/login")
    Call<UserModel> logInUser(@Body RetrofitLoginModel model);
}
