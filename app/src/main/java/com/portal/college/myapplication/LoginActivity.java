package com.portal.college.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    public static final String USER_NAME = "USER_NAME";

    public static final String PASSWORD = "PASSWORD";
    SessionManager session;
   // private static final String LOGIN_URL = "http://i92.168.1.101.login.php";
    private EditText editTextUserName;
    private EditText editTextPassword;

    String username;
    String password;
    private Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Session Manager
        session = new SessionManager(getApplicationContext());
        setContentView(R.layout.activity_login);
        editTextUserName = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);
    }

    public void invokeLogin(View view) {
        username = editTextUserName.getText().toString();
        password = editTextPassword.getText().toString();
        login(username, password);
    }

    private void login(final String username, String password) {
        class LoginAsync extends AsyncTask<String, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(LoginActivity.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
         /*       HashMap<String,String> data = new HashMap<>();
                data.put("username",params[0]);
                data.put("password",params[1]);
                LoginUserClass luc = new LoginUserClass();

                String result = luc.sendPostRequest(LOGIN_URL,data);
                return result;

*/
                String uname = params[0];
                String pass = params[1];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", uname));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://192.168.1.104/login.php");
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
                loadingDialog.dismiss();
                if (s.equalsIgnoreCase("success")) {

                    session.createLoginSession(username);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(USER_NAME,username);
                    finish();
                    startActivity(intent);
                } else {
                 //   int status = Integer.parseInt(null);
                //    int con = LoginUserClass.getStatus(status);
                    Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(username, password);
    }


}




