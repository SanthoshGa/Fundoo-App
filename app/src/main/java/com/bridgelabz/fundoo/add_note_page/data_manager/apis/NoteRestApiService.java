package com.bridgelabz.fundoo.add_note_page.data_manager.apis;

import com.bridgelabz.fundoo.add_note_page.Model.AddNoteModel;
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
                                             @Body AddNoteModel noteModel);
    @GET("notes/getNotesList")
    Call<Map<String, ResponseData>> getNoteList(@Header("Authorization") String authKey);

    @POST("notes/updateNotes")
    Call<Map<String, ResponseData>> updateNotes(@Header("Authorization") String authKey,
                                                 @Body Map<String, String> model);

    @POST("notes/changesColorNotes")
    Call<Map<String, ResponseData>>  changeColorToNote(@Header("Authorization") String authKey,
                                                       @Body Map<String, Object> model);


    @POST("notes/pinUnpinNotes")
    Call<Map<String, ResponseData>> setPinUnpinToNote(@Header("Authorization") String authKey,
                                                      @Body Map<String, Object> model);


    @POST("notes/archiveNotes")
    Call<Map<String, ResponseData>> setArchiveToNote(@Header("Authorization") String authKey,
                                                     @Body Map<String, Object> model);
    @GET("notes/getArchiveNotesList")
    Call<Map<String, ResponseData>> getArchiveNotesList(@Header("Authorization") String authKey);


    @POST("notes/trashNotes")
    Call<Map<String, ResponseData>>  trashNotes(@Header("Authorization") String authKey,
                                                @Body Map<String, Object> model);

    @GET("notes/getTrashNotesList")
    Call<Map<String, ResponseData>> getTrashNotesList(@Header("Authorization") String authKey);

    @POST("notes/deleteForeverNotes")
        Call<Map<String, ResponseData>> deleteForeverNOtes(@Header("Authorization") String authKey,
                                                           @Body Map<String, Object> model);

    @POST("notes/addUpdateReminderNotes")
    Call<Map<String, ResponseData>> addReminder(@Header("Authorization") String authKey,
                                                @Body Map<String, Object> model);

    @GET("notes/getReminderNotesList")
        Call<Map<String, ResponseData>> getReminderNotesList(@Header("Authorization") String authKey);


}
