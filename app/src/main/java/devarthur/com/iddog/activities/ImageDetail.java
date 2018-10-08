package devarthur.com.iddog.activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import devarthur.com.iddog.R;


public class ImageDetail extends AppCompatActivity {

    //Member Variables
    private TextView mDetailText;
    private ImageView mDetailImageView;
    private Button mButtonClose;
    private RequestOptions mOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        //Glide app configuration.
        mOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE);

        //Link content in the view
        mDetailText = (TextView) findViewById(R.id.imageDetailText);
        mDetailImageView = (ImageView) findViewById(R.id.imageDetailView);
        mButtonClose = (Button) findViewById(R.id.closeImageButton);

        Intent intent = getIntent();
        mDetailText.setText("Image: " + intent.getExtras().getString("extraImageText"));

        String url = intent.getExtras().getString("extraUrl");

        Glide
                .with(getApplicationContext())
                .load(url)
                .into(mDetailImageView);

        mButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {

        Log.d("Dog", "Activity Destroyed");
        //Remove images from glide cache.
        super.onDestroy();
        Intent intent = getIntent();
        String url = intent.getExtras().getString("extraUrl");
        Glide.with(getApplicationContext())
                .load(url)
                .apply(mOptions)
                .into(mDetailImageView);
    }
}
