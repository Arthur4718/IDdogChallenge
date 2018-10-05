package devarthur.com.iddog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDataModel extends AppCompatActivity {

    private String mToken;


    public static UserDataModel fromJson(JSONObject jsonObject){

        UserDataModel userData = new UserDataModel();

        try {
            userData.mToken = jsonObject.getJSONObject("user").getString("token");

            return userData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
    public String getmToken() {
        return mToken;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
