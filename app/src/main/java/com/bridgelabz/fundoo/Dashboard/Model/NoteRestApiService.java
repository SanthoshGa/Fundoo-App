package com.bridgelabz.fundoo.Dashboard.Model;

import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NoteRestApiService {

//    @Headers("Authourization : " +  )
    @POST("notes/addNotes")
    Call<Map<String, ResponseData>> addNotes(@Body NoteModel noteModel);

}
