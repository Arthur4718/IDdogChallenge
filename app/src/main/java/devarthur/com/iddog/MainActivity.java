package devarthur.com.iddog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Ui References
    private AutoCompleteTextView userEmailView;
    private Button signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmailView = (AutoCompleteTextView) findViewById(R.id.register_email);
        signInButton = (Button) findViewById(R.id.signInButton);
    }
}
