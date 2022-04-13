package com.calebkip.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class NewContactActivity extends AppCompatActivity {

    EditText etnewName,etnewEmail,etnewPhone;
    Button btnnewContact;
    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        etnewName=findViewById(R.id.etnewName);
        etnewEmail=findViewById(R.id.etnewEmail);
        etnewPhone=findViewById(R.id.etnewPhone);
        btnnewContact=findViewById(R.id.btnnewContact);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);
        btnnewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etnewName.getText().toString().isEmpty()||
                        etnewPhone.getText().toString().isEmpty()||
                        etnewEmail.getText().toString().isEmpty()){
                    Toast.makeText(NewContactActivity.this, "please fill all fields", Toast.LENGTH_SHORT).show();
                }else{
                    String name=etnewName.getText().toString().trim();
                    String email= etnewEmail.getText().toString().trim();
                    String phone = etnewPhone.getText().toString().trim();
                    Contact contact = new Contact();
                    contact.setName(name);
                    contact.setEmail(email);
                    contact.setPhone(phone);
                    contact.setUserEmail(ApplicationClass.user.getEmail());

                    showProgress(true);
                    tvLoad.setText("creating new user");
                    Backendless.Persistence.save(contact, new AsyncCallback<Contact>() {
                        @Override
                        public void handleResponse(Contact response) {
                            Toast.makeText(NewContactActivity.this, "Contact saved successfully", Toast.LENGTH_SHORT).show();
                               showProgress(false);
                               etnewName.setText("");
                               etnewEmail.setText("");
                               etnewPhone.setText("");

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(NewContactActivity.this,
                                    "error "+fault.getMessage(), Toast.LENGTH_SHORT).show();
                            showProgress(false);

                        }
                    });
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