package devarthur.com.iddog;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.AppCompatActivity;

public class UserDataModel extends AppCompatActivity {

    private String mToken;
    public static UserDataModel fromJson(JSONObject jsonObject){
        UserDataModel userData = new UserDataModel();
        try {
            userData.mToken = jsonObject.getString("token");
            return userData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


}
