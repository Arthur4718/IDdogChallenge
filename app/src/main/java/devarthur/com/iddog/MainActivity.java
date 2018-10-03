package devarthur.com.iddog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;

import android.widget.Button;
import android.widget.TextView;

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

                String email = userEmailView.getText().toString();

                if(TextUtils.isEmpty(email)){
                    //Todo show a dialog informing the user to add an email.
                    Log.d("Dog", "Empty email input");
                }else if(!isEmailValid(email)){
                    //Todo show a dialog prompting the user to add a valid e-mail
                    Log.d("Dog", "Invalid email string");

                }else{
                    //Todo call the POST method for the api
                    Log.d("Dog", "Email valid");
                }

            }
        });



    }

    private boolean isEmailValid(String email){

        return email.contains("@");
    }

    //TODO create a method to POST user email into the API


    //TODO if the POST is successful, store the token locally.
}
