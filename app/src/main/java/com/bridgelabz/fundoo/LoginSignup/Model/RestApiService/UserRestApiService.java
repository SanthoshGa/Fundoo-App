package com.bridgelabz.fundoo.LoginSignup.Model.RestApiService;

import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseData;
import com.bridgelabz.fundoo.LoginSignup.Model.UserLoginModel;
import com.bridgelabz.fundoo.LoginSignup.Model.UserModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserRestApiService {

    @GET("user")
    Call<List<UserModel>> getUsers();

    @POST("user/userSignUp")
    Call<Map<String, ResponseData>> signUpUser(@Body UserModel user);

    @POST("user/login")
    Call<UserModel> logInUser(@Body UserLoginModel loginModel);
}
