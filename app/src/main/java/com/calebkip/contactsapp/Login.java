package com.calebkip.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;

public class Login extends AppCompatActivity {
    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;
    EditText etEmail,etPassword;
    Button btnLogin,btnRegister;
    TextView tvReset;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        tvReset=findViewById(R.id.tvReset);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);


btnRegister.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(Login.this,Register.class));

    }
});
btnLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(etEmail.getText().toString().isEmpty()||etPassword.getText().toString().isEmpty()){
            Toast.makeText(Login.this, "" +
                    "please fill all fiels", Toast.LENGTH_SHORT).show();
        }else{
        String email =etEmail.getText().toString().trim();
        String password =etPassword.getText().toString().trim();
        showProgress(true);
            Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {
                    ApplicationClass.user=response;
                    Toast.makeText(Login.this, "login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this,MainActivity.class));
                    Login.this.finish();

                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(Login.this, "error" +fault.getMessage(), Toast.LENGTH_SHORT).show();
                    showProgress(false);

                }
            },true);
        }

    }
});
tvReset.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (etEmail.getText().toString().isEmpty()){

            Toast.makeText(Login.this, "please fill the email field", Toast.LENGTH_SHORT).show();
        }else{
            String email= etEmail.getText().toString().trim();
            showProgress(true);
            Backendless.UserService.restorePassword(email, new AsyncCallback<Void>() {
                @Override
                public void handleResponse(Void response) {
                    Toast.makeText(Login.this, "password sent to your mail", Toast.LENGTH_SHORT).show();
                    showProgress(false);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(Login.this, "Errror"+fault.getMessage(), Toast.LENGTH_SHORT).show();
                    showProgress(false);

                }

            });
        }

    }
});
Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
    @Override
    public void handleResponse(Boolean response) {
        if(response){
           String  userobjectid = UserIdStorageFactory.instance().getStorage().get();
           Backendless.Data.of(BackendlessUser.class).findById(userobjectid, new AsyncCallback<BackendlessUser>() {
               @Override
               public void handleResponse(BackendlessUser response) {
                   ApplicationClass.user=response;
                   startActivity( new Intent(Login.this,MainActivity.class));
                   Login.this.finish();

               }

               @Override
               public void handleFault(BackendlessFault fault) {
                   Toast.makeText(Login.this, "error"+fault.getMessage(), Toast.LENGTH_SHORT).show();
                   showProgress(false);

               }
           });

        }
        else{

        }


        Toast.makeText(Login.this, "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleFault(BackendlessFault fault) {
        Toast.makeText(Login.this, "error"+ fault.getMessage(), Toast.LENGTH_SHORT).show();
        showProgress(false);
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