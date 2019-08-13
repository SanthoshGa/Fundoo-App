package com.bridgelabz.fundoo.LoginSignup.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseData;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseError;
import com.bridgelabz.fundoo.LoginSignup.Model.RestApiUserDataManager;

import java.io.File;

import okhttp3.MultipartBody;

import static com.bridgelabz.fundoo.common.Utility.AppConstants.UPLOAD_PROFILE_PICTURE;

public class RestApiUserViewModel{

    private static final String TAG = "RestApiUserViewModel";

    private RestApiUserDataManager restApiUserDataManager;
    private LocalBroadcastManager localBroadcastManager;

    public RestApiUserViewModel(Context context) {
        restApiUserDataManager = new RestApiUserDataManager();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }


    public void uploadImage(MultipartBody.Part file){
        restApiUserDataManager.uploadImage(file, new RestApiUserDataManager.UploadImageCallBack() {
            Intent localIntent = new Intent(UPLOAD_PROFILE_PICTURE);
            @Override
            public void onResponse(ResponseData responseData, ResponseError responseError) {
                boolean isImageAdded;
                isImageAdded = (responseData != null);
                Log.e(TAG, "onResponse isImageAdded: " + isImageAdded);
                localIntent.putExtra("isImageAdded", isImageAdded);
                localBroadcastManager.sendBroadcast(localIntent);
            }

            @Override
            public void onFailure(Throwable throwable) {
                localIntent.putExtra("throwable", throwable);
                localBroadcastManager.sendBroadcast(localIntent);
            }
        });
    }
}
