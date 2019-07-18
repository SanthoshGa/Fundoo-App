package com.bridgelabz.fundoo.LoginSignup.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bridgelabz.fundoo.LoginSignup.ViewModel.UserViewModel;
import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.Utility.ValidationHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterActivity extends AppCompatActivity {

    private EditText mTextEmail;
    private EditText mTextUsername;
    private EditText mTextPassword;
    private EditText mTextConfirmPassword;
    private EditText mTextFirstName;
    private EditText mTextLastName;
    private Button mButtonRegister;
    private TextView mTextViewLogin;
    private UserViewModel userViewModel;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

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

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Registered Successfully",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(RegisterActivity.this, "You are already registered",
                                        Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                });

                if (ValidationHelper.validateEmail(email)) {
                    if (ValidationHelper.validatePassword(password)) {
                        processSignUp(firstName, lastName, email, username, password, confirmPassword);

                    } else {
                        makeToast("Enter valid password ");

                    }
                } else {
                    makeToast("Enter valid Email");

                }


            }
        });
    }

    private void processSignUp(String firstName, String lastName, String email, String username, String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            userViewModel = new UserViewModel(this);
            boolean isSignedUp = userViewModel.addUser(firstName, lastName, username, password, email);
            if (isSignedUp) {
                Toast.makeText(RegisterActivity.this, "you are registered", Toast.LENGTH_SHORT).show();
                Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(moveToLogin);
            } else {
                Toast.makeText(RegisterActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "password not matching", Toast.LENGTH_SHORT).show();

        }

    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }


}