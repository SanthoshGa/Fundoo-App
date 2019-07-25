package com.bridgelabz.fundoo.LoginSignup.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bridgelabz.fundoo.LoginSignup.Model.RestApiUserDataManager;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseData;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseError;
import com.bridgelabz.fundoo.LoginSignup.Model.UserModel;
import com.bridgelabz.fundoo.LoginSignup.Model.User;
import com.bridgelabz.fundoo.LoginSignup.ViewModel.UserViewModel;
import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.Utility.ValidationHelper;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "RegisterActivity.class";

    private EditText mTextEmail;
    private EditText mTextUsername;
    private EditText mTextPassword;
    private EditText mTextConfirmPassword;
    private EditText mTextFirstName;
    private EditText mTextLastName;
    private Button mButtonRegister;
    private TextView mTextViewLogin;
    private UserViewModel userViewModel;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        findViews();
        onClickLogin();
        onClickRegister();

    }

    private void findViews() {

        mTextEmail = findViewById(R.id.et_email);
        mTextUsername = findViewById(R.id.et_username);
        mTextPassword = findViewById(R.id.et_password);
        mTextConfirmPassword = findViewById(R.id.et_confirmpassword);
        mTextFirstName = findViewById(R.id.et_firstName);
        mTextLastName = findViewById(R.id.et_lastName);
        mButtonRegister = findViewById(R.id.btn_register);
        mTextViewLogin = findViewById(R.id.tv_login);

    }

    private void onClickLogin() {
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this,
                        LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    private void onClickRegister() {
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = mTextFirstName.getText().toString().trim();
                String lastName = mTextLastName.getText().toString().trim();
                String email = mTextEmail.getText().toString().trim();
                String username = mTextUsername.getText().toString().trim();
                String password = mTextPassword.getText().toString().trim();
                String confirmPassword = mTextPassword.getText().toString().trim();

//                user = new User(firstName, lastName, email, username, password);


//                FirebaseAuthManager firebaseAuthManager = new FirebaseAuthManager();
//                firebaseAuthManager.doSignUpWithFirebase(email, password);

                if (ValidationHelper.validateEmail(email)) {
                    if (ValidationHelper.validatePassword(password) && password.equals(confirmPassword)) {
                        UserModel userModel = new UserModel(firstName, lastName,
                                "123456789", "", "advance", "user",
                                email, username, "", "", password);
                        processSignUp(userModel);

                    } else {
                        makeToast("Enter valid password ");

                    }
                } else {
                    makeToast("Enter valid Email");

                }
            }
        });
    }

    private void processSignUp(UserModel userModel) {
        userViewModel = new UserViewModel(this);

        RestApiUserDataManager apiUserDataManager = new RestApiUserDataManager();

//        apiUserDataManager.createUser(userModel, new RestApiUserDataManager.SignUpCallback() {
//            @Override
//            public void onResponse(UserModel userModel, HttpResponseCode httpResponseCode) {
//
//                Log.e(TAG, httpResponseCode.getLocalizedDescription() + httpResponseCode
//                        .getHttpCode());
//
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                if (throwable != null) {
//                    Log.e(TAG, throwable.getLocalizedMessage() + "    i am throwable" );
//                } else {
//                    Log.e(TAG, "throwable is null");
//                }
//            }
//
//        });

        apiUserDataManager.createUser(userModel, new RestApiUserDataManager.SignUpCallback() {
            @Override
            public void onResponse(ResponseData responseData, ResponseError responseError) {

                if (responseData != null) {
                    Toast.makeText(RegisterActivity.this, responseData.getMessage()
                            , Toast.LENGTH_SHORT).show();
//                    finish();
                } else {

                }
//                Log.e(TAG, responseError.getStatusCode() + responseError.getMessage());
            }

            @Override
            public void onFailure(Throwable throwable) {

                if (throwable != null) {
                    Log.e(TAG, throwable.getLocalizedMessage() + "This is throwable");
                } else {
                    Log.e(TAG, "throwable is null");
                }

            }
        });

//        Map<String,Map<String, Object>>

//        {
//            "error": {
//            "statusCode": 500,
//                    "message": "Please provide password",
//                    "success": false
//        }
//        }


//        boolean isSignedUp = userViewModel.addUser(user);
//        if (isSignedUp) {
//            Toast.makeText(RegisterActivity.this, "you are registered", Toast.LENGTH_SHORT).show();
//            Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
//            startActivity(moveToLogin);
//        } else {
//            Toast.makeText(RegisterActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
//        }
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }


}