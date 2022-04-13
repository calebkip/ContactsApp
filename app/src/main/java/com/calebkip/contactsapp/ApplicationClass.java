package com.calebkip.contactsapp;

import android.app.Application;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.util.List;

public class ApplicationClass extends Application {


    public static final String APPLICATION_ID = "75662064-868A-B9EB-FFFC-5412CA933200";
    public static final String API_KEY = "02B3F672-130F-4F36-84D7-D730BEBDA4C6";
    public static final String SERVER_URL = "https://api.backendless.com";
    public  static BackendlessUser user ;
    public  static List<Contact> contacts;

    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.setUrl( SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );



    }



}
