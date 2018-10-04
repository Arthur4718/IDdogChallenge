package devarthur.com.iddog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.AutoCompleteTextView;
import android.support.v7.app.AlertDialog;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;
import android.widget.Button;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.entity.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    //Ui References
    private AutoCompleteTextView userEmailView;
    private Button signInButton;

    //Constants
    //Todo add any contast like api url , token and etc around here


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


        String url = "https://api-iddog.idwall.co/signup";
        String userEmail = userEmailView.getText().toString();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("email", userEmail);
        } catch (JSONException e) {
            e.printStackTrace();


        }

        ByteArrayEntity be = new ByteArrayEntity(jsonParams.toString().getBytes());


        client.post(getApplicationContext(), url, be, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Dog", "onSucess" + responseBody.toString());
                Log.d("Dog", "status code " + String.valueOf(statusCode));

                //TODO if the POST is successful, store the token locally. Shared prefereences

                //TODO open the next screen


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("Dog", "" + error.toString());
                Log.e("Dog", "status code " + String.valueOf(statusCode));
            }
        });

    }

    private void storeToken(byte token){
        
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
