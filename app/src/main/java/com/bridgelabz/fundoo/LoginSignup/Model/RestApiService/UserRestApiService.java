package com.bridgelabz.fundoo.LoginSignup.Model.RestApiService;

import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseData;
import com.bridgelabz.fundoo.LoginSignup.Model.UserLoginModel;
import com.bridgelabz.fundoo.LoginSignup.Model.UserModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserRestApiService {

    @GET("user")
    Call<List<UserModel>> getUsers();

    @POST("user/userSignUp")
    Call<Map<String, ResponseData>> signUpUser(@Body UserModel user);

    @POST("user/login")
    Call<UserModel> logInUser(@Body UserLoginModel loginModel);

    @Multipart
    @POST("user/uploadProfileImage")
    Call<Map<String, ResponseData>> uploadImage(@Part MultipartBody.Part file,
                                                @Header("Authorization") String authKey);
}
