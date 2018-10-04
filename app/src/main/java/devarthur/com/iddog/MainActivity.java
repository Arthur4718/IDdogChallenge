package devarthur.com.iddog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.support.v7.app.AlertDialog;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;
import android.widget.Button;
import android.content.SharedPreferences;


import cz.msebera.android.httpclient.entity.*;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    //Ui References
    private AutoCompleteTextView userEmailView;
    private Button signInButton;

    //Constants
    //Todo add any contast like api url , token and etc around here
    private static final String API_URL = "https://api-iddog.idwall.co/signup";
    public static final String APP_PREFS = "ChatPrefs";
    public static final String TOKEN_KEY = "token";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userEmailView = (AutoCompleteTextView) findViewById(R.id.register_email);
        //TODO Add an event to check for hover status. Black bg with white text when hovering, and White bg with black text when not
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

        if(TextUtils.isEmpty(email)){

            Log.d("Dog", "Empty email input");
            showErrorDialog("Please type your e-mail");
        }else if(!isEmailValid(email)){

            Log.d("Dog", "Invalid email string");
            showErrorDialog("Your email address is not valid");

        }else{

            Log.d("Dog", "Email valid");

            attempPost();

            //Intent openGallery = new Intent(getApplicationContext(), GalleryActivity.class);
            //startActivity(openGallery);


        }


    }

    private void attempPost() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        String userEmail = userEmailView.getText().toString();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("email", userEmail);
        } catch (JSONException e) {
            e.printStackTrace();


        }

        ByteArrayEntity be = new ByteArrayEntity(jsonParams.toString().getBytes());


        //TODO create a class to handle JSON post, getmethods and parse token value
        client.post(getApplicationContext(), API_URL, be, "application/json", new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("Dog", "onSucess " + response.toString());
                Log.d("Dog", "status code " + String.valueOf(statusCode));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("Dog", "" + errorResponse.toString());
                Log.e("Dog", "status code " + String.valueOf(statusCode));
            }


        });

    }

    private void storeToken(String data){

        


    }

    private boolean isEmailValid(String email){

        return email.contains("@");
    }




    //Show a simple alert if something went wrong with the connnection to firebase.
    private void showErrorDialog(String message){

        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

}
