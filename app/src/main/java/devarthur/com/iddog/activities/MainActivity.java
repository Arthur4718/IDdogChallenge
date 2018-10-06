package devarthur.com.iddog.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.support.v7.app.AlertDialog;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;
import android.widget.Button;
import android.content.SharedPreferences;


import cz.msebera.android.httpclient.entity.*;
import devarthur.com.iddog.R;
import devarthur.com.iddog.model.UserDataModel;
import devarthur.com.iddog.utilities.Utilities;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    //Ui References
    private AutoCompleteTextView userEmailView;
    private Button signInButton;

    //Constants
    private static final String API_URL = "https://api-iddog.idwall.co/signup";
    public static final String APP_PREFS = "AppPrefs";
    public static final String TOKEN_KEY = "usertoken";
    public static final String EMAIL_KEY = "useremail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmailView = (AutoCompleteTextView) findViewById(R.id.register_email);
        signInButton = (Button) findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkUserEmail();

            }
        });
    }
    private void checkUserEmail(){
        String email = userEmailView.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Utilities.showMessageDialog(
                    getApplicationContext().getString(R.string.blankEmailtext),
                    getApplicationContext());
        }
        else if(!isEmailValid(email))
        {
            Utilities.showMessageDialog(
                    getApplicationContext().getString(R.string.invalidEmailText),
                    getApplicationContext());
        }else{

            attempPost();
            //TODO We have to check if the user INTERNET is really on, and give a timeout message if not
        }

    }
    private void attempPost() {
        String userEmail = userEmailView.getText().toString();

        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("email", userEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ByteArrayEntity be = new ByteArrayEntity(jsonParams.toString().getBytes());

        client.post(getApplicationContext(), API_URL, be, ContentType.APPLICATION_JSON.getMimeType(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                UserDataModel userData = UserDataModel.fromJson(response);
                storeToken(userData.getmToken());
                openListActivity();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Utilities.showMessageDialog(
                        getApplicationContext().getString(R.string.requestFailure),
                        getApplicationContext());
            }
            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
            }
        });
    }
    private void storeToken(String token){
        SharedPreferences prefs = getSharedPreferences(APP_PREFS, 0);
        prefs.edit().putString(TOKEN_KEY, token).apply();
        prefs.edit().putString(EMAIL_KEY,userEmailView.getText().toString()).apply();
    }
    private boolean isEmailValid(String email){
        return email.contains("@");
    }

    private void openListActivity(){
        Intent listIntent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(listIntent);
    }
}
