package com.bridgelabz.fundoo.LoginSignup.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bridgelabz.fundoo.Dashboard.DashboardActivity;
import com.bridgelabz.fundoo.LoginSignup.Model.Response.ResponseError;
import com.bridgelabz.fundoo.LoginSignup.Model.RestApiUserDataManager;
import com.bridgelabz.fundoo.LoginSignup.Model.UserLoginModel;
import com.bridgelabz.fundoo.LoginSignup.Model.UserModel;
import com.bridgelabz.fundoo.LoginSignup.ViewModel.UserViewModel;
import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.common.SharedPreferencesManager;
import com.bridgelabz.fundoo.firebase_auth_service.FirebaseAuthManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static com.bridgelabz.fundoo.common.Utility.AppConstants.UPLOAD_PROFILE_PICTURE;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LoginActivity.class";
    private EditText mTextEmail;
    private EditText mTextPassword;
    private Button mButtonLogin;
    private TextView mTextRegister;
    private UserViewModel userViewModel;
    private SignInButton mSignIn;
    private GoogleApiClient googleApiClient;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    public static final int REQUEST_CODE = 9001;
    FirebaseAuthManager firebaseAuthManager;
    SharedPreferencesManager sharedPreferencesManager;
    RestApiUserDataManager apiUserDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        apiUserDataManager = new RestApiUserDataManager();

        findViews();
        onClickRegister();
        onClickLogin();
        setClickOnGoogleSignIn();
        registerLocalBroadcasts();

        //Google SignIn
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder
                (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestProfile().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage
                (this, this).addApi(Auth.GOOGLE_SIGN_IN_API,
                googleSignInOptions).build();
        callbackManager = CallbackManager.Factory.create();

        //Facebook SignIn
        loginButton.setPermissions(Arrays.asList("email", "public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login Failure", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, error.toString());

            }
        });

    }

    private void registerLocalBroadcasts() {

    }

    private void setClickOnGoogleSignIn() {
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    private void findViews() {

        userViewModel = new UserViewModel(this);
        mTextEmail = findViewById(R.id.et_email);
        mTextPassword = findViewById(R.id.et_password);
        mButtonLogin = findViewById(R.id.btn_login);
        mTextRegister = findViewById(R.id.tv_register);
        mSignIn = findViewById(R.id.btn_Sign_in);
        mSignIn.setSize(SignInButton.SIZE_STANDARD);
        loginButton = findViewById(R.id.fb_login_btn);
    }

    private void onClickRegister() {
        mTextRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    private void onClickLogin() {
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mTextEmail.getText().toString().trim();
                String password = mTextPassword.getText().toString().trim();

                sharedPreferencesManager.saveLoginDetails(email, password);
//                boolean res = userViewModel.checkUser(email, password);
                Log.e(TAG, "onClick: login button click");

                UserLoginModel loginModel = new UserLoginModel(email, password);

                apiUserDataManager.checkUser(loginModel, new RestApiUserDataManager.SignInCallback() {
                    @Override
                    public void onResponse(UserModel userModel, ResponseError responseError) {
                        Log.e(TAG, "onResponse: " + userModel.toString());
                        String token = userModel.getId();
                        sharedPreferencesManager.setAccessToken(token);
                        openDashBoardActivity(email, null);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e(TAG, throwable.getLocalizedMessage() + "THIS IS THROWABLE");

                    }
                });
            }

            void openDashBoardActivity(String email, String image_url) {
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("imageUrl", image_url);
                startActivity(intent);

            }
        });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn() {

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQUEST_CODE);

    }

    private void handleResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            if (account != null) {
                String email = account.getEmail();
                String imageUrl = account.getPhotoUrl().toString();
                Log.e(TAG, account.getDisplayName() + "\n" + account.getEmail() + "\n"
                        + account.getId() + "\n" + imageUrl + "\n");
//            mTextEmail.setText(email);
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                Intent dIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                dIntent.putExtra("email", email);
                dIntent.putExtra("imageUrl", imageUrl);
                startActivity(dIntent);
            } else {
                Log.e(TAG, "Google Sign In Account is null");
            }

//            updateUI(true);
        } else {
//            updateUI(false);
            Toast.makeText(this, "Login failed!", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateUI(boolean isLogin) {

        if (isLogin) {

        } else {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);

        }
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

        }
    };

    public void loaduserProfile(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name, last_name, email, id");
        request.setParameters(parameters);
        request.executeAsync();

    }
    //BROADCAST RECEIVERS



}


//                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            Toast.makeText(LoginActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
//                            Intent dashboardIntent = new Intent(LoginActivity.this, DashboardActivity.class);
//                            dashboardIntent.putExtra("email", email);
//
//                            startActivity(dashboardIntent);
//                            finish();
//
//                        }
//                        else{
//                            Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });

//                firebaseAuthManager = new FirebaseAuthManager();
//                firebaseAuthManager.doSignInWithFirebase(email, password);


//                if(res)
//                {
//                    Toast.makeText(LoginActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
//                    Intent dashboardIntent = new Intent(LoginActivity.this, DashboardActivity.class);
//                    dashboardIntent.putExtra("email", email);
//                    startActivity(dashboardIntent);
//                    finish();
//                }
//                else{
//                    Toast.makeText(LoginActivity.this, "login error", Toast.LENGTH_SHORT).show();
//
//                }

