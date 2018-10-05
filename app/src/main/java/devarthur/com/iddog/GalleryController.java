package devarthur.com.iddog;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class GalleryController extends AppCompatActivity {

    //MemberVariables
    private String userToken;

    //Constants
    private static final String FEED_URL = "https://api-iddog.idwall.co/feed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        getDataFromNetWork();

    }

    private String restoreToken() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.APP_PREFS, MODE_PRIVATE);
        return userToken = prefs.getString(MainActivity.TOKEN_KEY,null);
    }


    private void getDataFromNetWork() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        //client.setBasicAuth(restoreEmail(), restoreToken());
        client.addHeader("Content-Type", "application/json");
        client.addHeader("Authorization", restoreToken());
        params.put("category", "hound");

        client.get(FEED_URL,params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("Data", "onSucess " + response.toString());
                Log.d("Data", "status code " + String.valueOf(statusCode));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("Data", "" + errorResponse.toString());
                Log.e("Data", "status code " + String.valueOf(statusCode));

            }
        });
    }

}
