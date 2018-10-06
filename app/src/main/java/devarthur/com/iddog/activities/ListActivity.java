package devarthur.com.iddog.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import devarthur.com.iddog.R;
import devarthur.com.iddog.adapters.RecyclerViewAdapter;
import devarthur.com.iddog.model.DogDataModel;


public class ListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //MemberVariables
    private String userToken;
    private Button mButton;
    private TextView displayUserEmail;
    private List<DogDataModel> lsdogPhoto;
    private RecyclerView mRecyclerView;


    //Constants
    private static final String FEED_URL = "https://api-iddog.idwall.co/feed";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        TextView displayUserEmail = (TextView) headerView.findViewById(R.id.userEmailTextView);
        displayUserEmail.setText(restoreEmail());

        mRecyclerView = findViewById(R.id.my_recycler_view);
        //Holds all data models in a Array List
        lsdogPhoto = new ArrayList<>();

    }

    private void getDataFromNetWork(String dogCategory) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.addHeader("Content-Type", "application/json");
        client.addHeader("Authorization", restoreToken());
        params.put("category", dogCategory);

        client.get(FEED_URL,params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                super.onSuccess(statusCode, headers, response);

                //TODO apply this logic in a class
                for(int i = 0; i < 10; i ++)
                {
                    DogDataModel dogPhoto = new DogDataModel();
                    try {
                        dogPhoto.setImgUrl(response.getJSONArray("list").getString(i));
                        lsdogPhoto.add(dogPhoto);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                feedRecyclerView(lsdogPhoto);

            }

            private void feedRecyclerView(List<DogDataModel> lsdogPhoto) {
                RecyclerViewAdapter myRecyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(), lsdogPhoto);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mRecyclerView.setAdapter(myRecyclerViewAdapter);


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //TODO REMOVE IMAGES when a new querry is called

        if (id == R.id.nav_husky)
        {

            getDataFromNetWork("husky");
        }
        else if (id == R.id.nav_hound)
        {
            getDataFromNetWork("hound");

        }
        else if (id == R.id.nav_pug)
        {
            getDataFromNetWork("pug");
        }
        else if (id == R.id.nav_labrador)
        {
            getDataFromNetWork("labrador");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private String restoreToken() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.APP_PREFS, MODE_PRIVATE);
        return userToken = prefs.getString(MainActivity.TOKEN_KEY,null);
    }

    private String restoreEmail()
    {
        SharedPreferences prefs = getSharedPreferences(MainActivity.APP_PREFS, MODE_PRIVATE);
        return userToken = prefs.getString(MainActivity.EMAIL_KEY,null);
    }

}
