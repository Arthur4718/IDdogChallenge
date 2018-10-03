package devarthur.com.iddog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.support.v7.app.AlertDialog;
import com.loopj.android.http.*;

import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

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
            //Todo call the POST method for the api
            Log.d("Dog", "Email valid");

            //attempPost();

        }


    }

    private void attempPost() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        String url = "https://api-iddog.idwall.co/signup";
        String userEmail = userEmailView.getText().toString();

        JSONObject jsonEmail = new JSONObject();
        try {
            jsonEmail.put("email", userEmail);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Dog", " " + jsonEmail.toString());


        params.put("-d", jsonEmail);
        params.put("Content-Type", "application/json");
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Dog", "onSucess" + responseBody.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Log.e("Dog", "" + error.toString());
                Log.e("Dog", "status code " + String.valueOf(statusCode));

            }
        });

    }

    private boolean isEmailValid(String email){

        return email.contains("@");
    }

    //TODO create a method to POST user email into the API


    //TODO if the POST is successful, store the token locally.

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
