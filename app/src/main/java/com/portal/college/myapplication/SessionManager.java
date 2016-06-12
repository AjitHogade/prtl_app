package com.portal.college.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.ssl.PrivateKeyDetails;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 16-Feb-16.
 */
public class SessionManager {
    SharedPreferences pref;
    TelephonyManager tel;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;
    private Dialog loadingDialog;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Token
    private static final String ACCESS_TOKEN = "access_token";

//Address
    private static final String ADDRESS = "address";

    //Roll_No
    private static final String USERNAME = "username";


    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";


    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }







    /**
     * Create login session
     * */

    public void createLoginSession(String s) {
        editor.putBoolean(IS_LOGIN, true);
Log.d("newJson",s);
        try{
            JSONArray jArr = new JSONArray(s);
            JSONObject jsonObject = jArr.getJSONObject(0);
            editor.putString(KEY_NAME, jsonObject.getString("f_name")+" "+jsonObject.getString("middle_name")+" "+jsonObject.getString("l_name"));
            editor.putString(ACCESS_TOKEN, jsonObject.getString("access_token"));
            editor.putString(ADDRESS,jsonObject.getString("address"));
            editor.putString(USERNAME,jsonObject.getString("username"));
            editor.commit();

        }catch (Exception e){
        }




        // Storing name in pref

//        tel = (TelephonyManager) _context.getSystemService(Context.TELEPHONY_SERVICE);
//String uid = tel.getDeviceId().toString();
//        Log.d("chil",uid);

//        String test = .imei();

    }

    public void checkValidToken(){
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }else {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            String user_name = pref.getString(USERNAME,"");
            String access_token = this.isValidToken();

            matchToken(user_name,access_token);
/*
            nameValuePairs.add(new BasicNameValuePair("access_token", this.isValidToken()));
            nameValuePairs.add(new BasicNameValuePair("username", user_name));

            Log.d("token", this.isValidToken());
            Log.d("token", pref.getString(ACCESS_TOKEN, ""));


            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.1.102/check_token");
            String responseBody = null;
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                Log.d("myapp", "works till here. 2");
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    responseBody = "dfghjkl";
                    Log.d("myapp", "response " + responseBody);
                } catch (ClientProtocolException e) {
                    Log.d("onCatchMain1",e.toString());
                } catch (IOException e) {
                    Log.d("onCatchMain2",e.toString());
                }
            } catch (UnsupportedEncodingException e) {

                Log.d("onCatchMain3",e.toString());
            }


            if (!this.isValidToken().equals("aaaaaa")) {
                Log.d("token", "Inside " + this.isValidToken());

                Intent i = new Intent(_context, LoginActivity.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // Staring Login Activity
                _context.startActivity(i);

            }*/
        }
    }
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public String isValidToken(){
        return pref.getString(ACCESS_TOKEN,"");
    }

    private void matchToken(final String username, String token){
        class MatchTokenAsync extends AsyncTask<String, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {

                String uname = params[0];
                String token = params[1];
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", uname));
                nameValuePairs.add(new BasicNameValuePair("access_token", token));



                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://192.168.1.102/check_token");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                    System.out.print("Here 1");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    System.out.print("Here 2");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.print("Here 3");
                }

                return result;

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                String s = result.trim();
                //   String s = "success";
               // loadingDialog.dismiss();
                if (s.equalsIgnoreCase("InvalidAccessToken")) {
                    Log.d("recievedRes",s);
                    editor.clear();
                    editor.commit();
                    // user is not logged in redirect him to Login Activity
                    Intent i = new Intent(_context, LoginActivity.class);
                    // Closing all the Activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    // Add new Flag to start new Activity
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Staring Login Activity
                    _context.startActivity(i);
                } else {
                    Log.d("recievedRes",s);
// just pass the welcome Note to toster with user name......
                }
            }

        }
        MatchTokenAsync ma = new MatchTokenAsync();
        ma.execute(username,token);
    }

}
