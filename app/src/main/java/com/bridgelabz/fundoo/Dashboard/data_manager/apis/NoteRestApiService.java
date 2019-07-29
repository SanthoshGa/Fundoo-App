package com.bridgelabz.fundoo.Dashboard.data_manager.apis;

import com.bridgelabz.fundoo.Dashboard.Model.NoteModel;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface NoteRestApiService {

    @POST("notes/addNotes")
    Call<Map<String, ResponseData>> addNotes(@Header("Authorization") String authKey,
                                             @Body NoteModel noteModel);
    @GET("notes/getNotesList")
    Call<Map<String, ResponseData>> getNoteList(@Header("Authorization") String authKey);

//    @GET("notes/getArchiveNotesList")
//    Call<>


}
