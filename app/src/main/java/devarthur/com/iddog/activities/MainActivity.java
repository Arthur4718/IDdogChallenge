package devarthur.com.iddog.activities;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.Toast;


import cz.msebera.android.httpclient.entity.*;
import devarthur.com.iddog.R;
import devarthur.com.iddog.model.UserDataModel;


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
            showErrorDialog(getString(R.string.blankEmailtext));
        }
        else if(!isEmailValid(email))
        {
            showErrorDialog(getString(R.string.invalidEmailText));
        }else{

            if(!userHasConnection()){
                showErrorDialog(getString(R.string.noConnectionText));
            }
            else{
                attempPost();
            }


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

                showErrorDialog(getString(R.string.invalidEmailText));

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

    private void showErrorDialog(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private boolean userHasConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        else{
            return false;
        }

    }

}
