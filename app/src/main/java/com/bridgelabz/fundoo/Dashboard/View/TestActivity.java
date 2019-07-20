package com.bridgelabz.fundoo.Dashboard.View;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bridgelabz.fundoo.Dashboard.Model.RetrofitLoginModel;
import com.bridgelabz.fundoo.Dashboard.Model.UserModel;
import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.Utility.UserRestApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bridgelabz.fundoo.Utility.UserRestApiService.BASE_URL;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        listView = findViewById(R.id.listViewData);

        getUsers();
        createUser();
        checkUser();
    }



    // GET ALL LIST OF USERS
    private void getUsers() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create()).build();

        UserRestApiService userRestApiService = retrofit.create(UserRestApiService.class);
        Call<List<UserModel>> call = userRestApiService.getUsers();
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                List<UserModel> userList = response.body();

                for(UserModel userModel : userList) {
                    System.out.println(userModel);
                }
                Log.e(TAG, "onResponse: " + response.code() );
                String[] users = new String[userList.size()];

                for(int i=0; i<userList.size();i++){
                    users[i] = userList.get(i).getEmailId();
//                    System.out.println(users[i]);
                }
//                listView.setAdapter(new ArrayAdapter<>(TestActivity.this,
//                        android.R.layout.simple_list_item_1, users));
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(TestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    // CREATE NEW USER
    private void createUser() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        final UserModel model = new UserModel("abcd", "kgf",
               "121212121", "", "advance", "user",
               "kgf@gmail.com", "", "", "", "kgf@123");

        UserRestApiService userRestApiService = retrofit.create(UserRestApiService.class);
        Call<UserModel> call = userRestApiService.signUpUser(model);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                UserModel model1 = response.body();
                if(response.isSuccessful() && model1 != null){
                    Toast.makeText(TestActivity.this, "response is"
                            + model1.toString() , Toast.LENGTH_SHORT).show();
                }

                else{
                    Log.e(TAG, "response:" + response.code());
                }
            }
            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
               Log.e(TAG, "response is null");
            }
        });
    }


    // CHECK EXISTING USER
    public void checkUser(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitLoginModel loginModel = new RetrofitLoginModel("kgf@gmail.com", "kgf@123");
        UserRestApiService userRestApiService = retrofit.create(UserRestApiService.class);
        Call<UserModel> call = userRestApiService.logInUser(loginModel);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                UserModel loginModel1 = response.body();
                if(response.isSuccessful() && loginModel1!= null){
                    Toast.makeText(TestActivity.this, "response is"
                            + loginModel1.toString() , Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "response is:" + loginModel1.toString());

                }

                else{
                    Log.e(TAG, "response:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());

            }
        });

    }
}

