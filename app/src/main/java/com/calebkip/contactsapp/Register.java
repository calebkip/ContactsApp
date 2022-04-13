package com.calebkip.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class Register extends AppCompatActivity {
    private View mProgressView;
    private View mLoginFormView;
    Button registerbtn;
    private TextView tvLoad,etNameRegister,etEmailRegister,etPasswordRegister,etConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);
        etNameRegister=findViewById(R.id.etNameRegister);
        etEmailRegister=findViewById(R.id.etEmailRegister);
        etPasswordRegister=findViewById(R.id.etPasswordRegister);
        etConfirmPassword=findViewById(R.id.etConfirmPassword);
        registerbtn=findViewById(R.id.registerbtn);
        showProgress(true);
         registerbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(etNameRegister.getText().toString().isEmpty()||
                         etEmailRegister.getText().toString().isEmpty()||
                         etPasswordRegister.getText().toString().isEmpty()||etConfirmPassword.getText().toString().isEmpty())
                 {
                     Toast.makeText(Register.this, "please enter all details", Toast.LENGTH_SHORT).show();
                 }else
                     if(etPasswordRegister.getText().toString().equals(etConfirmPassword.getText().toString())){

                         String name=etNameRegister.getText().toString().trim();
                         String email=etEmailRegister.getText().toString().trim();
                         String password=etPasswordRegister.getText().toString().trim();

                         BackendlessUser user = new BackendlessUser();
                         user.setEmail(email);
                         user.setPassword(password);
                         user.setProperty("name","name");
                         showProgress(true);
                         tvLoad.setText("please wait regitering user ");

                         Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                             @Override
                             public void handleResponse(BackendlessUser response) {
                                 showProgress(false);
                                 Toast.makeText(Register.this, "user registered succesfully", Toast.LENGTH_SHORT).show();
                                 Register.this.finish();
                                 
                             }

                             @Override
                             public void handleFault(BackendlessFault fault) {
                                 Toast.makeText(Register.this, "Error:"+fault.getMessage(), Toast.LENGTH_SHORT).show();
                                 showProgress(false);

                             }
                         });

                     }
                     else{
                         Toast.makeText(Register.this, " Please retype password", Toast.LENGTH_SHORT).show();
                     }
             }
         });

    }
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}