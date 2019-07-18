package com.bridgelabz.fundoo.Dashboard.View;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bridgelabz.fundoo.Dashboard.Model.RetrofitLoginModel;
import com.bridgelabz.fundoo.Dashboard.Model.RetrofitModel;
import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.Utility.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bridgelabz.fundoo.Utility.Api.BASE_URL;

public class RetrofitActivity extends AppCompatActivity {

    private static final String TAG = "RetrofitActivity";
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

        Api api = retrofit.create(Api.class);
        Call<List<RetrofitModel>> call = api.getUsers();
        call.enqueue(new Callback<List<RetrofitModel>>() {
            @Override
            public void onResponse(Call<List<RetrofitModel>> call, Response<List<RetrofitModel>> response) {
                List<RetrofitModel> userList = response.body();

                for(RetrofitModel retrofitModel : userList) {
                    System.out.println(retrofitModel);
                }
                Log.e(TAG, "onResponse: " + response.code() );
                String[] users = new String[userList.size()];

                for(int i=0; i<userList.size();i++){
                    users[i] = userList.get(i).getEmailId();
//                    System.out.println(users[i]);
                }
//                listView.setAdapter(new ArrayAdapter<>(RetrofitActivity.this,
//                        android.R.layout.simple_list_item_1, users));
            }

            @Override
            public void onFailure(Call<List<RetrofitModel>> call, Throwable t) {
                Toast.makeText(RetrofitActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    // CREATE NEW USER
    private void createUser() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        final RetrofitModel model = new RetrofitModel("abcd", "kgf",
               "121212121", "", "advance", "user",
               "kgf@gmail.com", "", "", "", "kgf@123");

        Api api = retrofit.create(Api.class);
        Call<RetrofitModel> call = api.signUpUser(model);
        call.enqueue(new Callback<RetrofitModel>() {
            @Override
            public void onResponse(Call<RetrofitModel> call, Response<RetrofitModel> response) {
                RetrofitModel model1 = response.body();
                if(response.isSuccessful() && model1 != null){
                    Toast.makeText(RetrofitActivity.this, "response is"
                            + model1.toString() , Toast.LENGTH_SHORT).show();
                }

                else{
                    Log.e(TAG, "response:" + response.code());
                }
            }
            @Override
            public void onFailure(Call<RetrofitModel> call, Throwable t) {
               Log.e(TAG, "response is null");
            }
        });
    }


    // CHECK EXISTING USER
    public void checkUser(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitLoginModel loginModel = new RetrofitLoginModel("kgf@gmail.com", "kgf@123");
        Api api = retrofit.create(Api.class);
        Call<RetrofitModel> call = api.logInUser(loginModel);
        call.enqueue(new Callback<RetrofitModel>() {
            @Override
            public void onResponse(Call<RetrofitModel> call, Response<RetrofitModel> response) {
                RetrofitModel  loginModel1 = response.body();
                if(response.isSuccessful() && loginModel1!= null){
                    Toast.makeText(RetrofitActivity.this, "response is"
                            + loginModel1.toString() , Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "response is:" + loginModel1.toString());

                }

                else{
                    Log.e(TAG, "response:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<RetrofitModel> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());

            }
        });

    }
}

