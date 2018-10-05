package devarthur.com.iddog;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class GalleryActivity extends AppCompatActivity {

    private String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        restoreToken();

    }

    @Override
    protected void onResume() {
        super.onResume();

        getDataFromNetWork();

    }

    private void restoreToken() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.APP_PREFS, MODE_PRIVATE);
        userToken = prefs.getString(MainActivity.TOKEN_KEY,null);
    }

    private void getDataFromNetWork() {
        //TODO create a get method using API doc and parse the data
    }


}
