# IDdogChallenge
App created as a challenge from IDwall team.


This app showcases a list of images to users that can login with a valid e-mail. I will describe some of the libraries that i used
during development, and also showcasing some examples if you want to get your feet wet with some popular libraries. 

You should be able to grab an apk here. 

https://drive.google.com/file/d/1dFy22mHxozj0zcRWT4XTbOqpYXZ3aUK7/view?usp=sharing

Just run install it normally in your device or emulator. If the Android system promps you if a message warning about the apk. 
then you should enable its installation by change accepting a security change in your system. Its higly recommended for you to 
turn it back on once you are finished. Also this behaviour might change depending on your system version. If get any trouble
you can get in touch with me at devarthur4718@gmail.com


# Libraries Used 

In order to perform all the actions listed above, i used:

Glidev4 - To list and cache images on disk. 
RecyclerView - List and recycle images on the user display.
Looopj Asynchronous Http Client - A library based on Apache. 
MVC pattenrs - In order to better organize and separate View Actions from Data Actions.


# Glidev4 implementation and usage. 

In the Gradle.App document, i added the following implementations. 

```Java
  implementation 'com.github.bumptech.glide:glide:4.7.1'
  // Glide v4 uses this new annotation processor -- see https://bumptech.github.io/glide/doc/generatedapi.html
    annotationProcessor 'com.github.bumptech.glide:compiler:4.7.1'
```

In order to set DiskCache, Scale Type and other properties, first you should create a Request Options member variable inside your 
class. Then, you can customize all the properties you need. 

```Java
 private RequestOptions mOptions;

```

Then, you can set up the rules for the Glide object at the same time you RecyclerViewAdapter is constructed. 

```Java
  public RecyclerViewAdapter(Context context, List<DogDataModel> data) {
        mContext = context;
        mData = data;


        //Set option for glide.Image place holder for loading images and place holder for erros 
        mOptions = new RequestOptions()
                .centerInside()
                .placeholder(R.drawable.ic_pets_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

    }

```
  Applying these rules can be acoomplished by using Glid object. Above you can see an example of its usage inside 
  the recyclerViewAdapter class. By default Glidev4 diskCacheStrage is set to automatic, you can change to DiskCacheStrage.All. 

```Java
public void onBindViewHolder(mViewHolder holder, final int position) {
        holder.txtImageDisplay.setText("Image: " + String.valueOf(position));

        Glide
                .with(mContext)
                .load(mData.get(position).getImgUrl())
                .apply(mOptions)
                .into(holder.img_data);
                
}                

```
if your are looking to refresh the images, then you should use. 

```Java
  Glide.get(getApplicationContext()).clearMemory();

```

Very useful when loading a whole new set of images from another category. 


# Recycler View implementation and Usage. 

Its very commom to see android tutorials using ListView, but google standard object is now Recycler View. The usage in this project dosent have anything special that you cant learn from the actual documentation of Android. However, if you are looking for some sample code im happy to share with you. 

A Recycler view is basically a colection of views. It will use a XML file as your basic item and populate a list with this view. In this project i created simple ImageView and TextView to use as an item. 

## Item XML file.
```XML
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="200dp"
    android:orientation="vertical"
    android:background="#fff"
    >

    <TextView
        android:id="@+id/imageUrl"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Image: "
        android:textSize="@dimen/imagePromptSize"
        android:textStyle="bold"
        android:textAlignment="center"
        />
    <ImageView
        android:id="@+id/imgContentViewItem"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:scaleType="centerInside"
        android:background="#fff"

        />


</LinearLayout>


```

Then you can add a Recycler view to your Activity. 

```XML
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:layout_behavior="@string/appbar_scrolling_view_behavior"
    tools:context=".activities.ListActivity"
    tools:showIn="@layout/app_bar_list">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/my_recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginBottom="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:scrollbars="vertical"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</android.support.constraint.ConstraintLayout>


```
The next step is to set a RecyclerViewAdapter and a DataModel. The adapter is responsible for fedding data from the DataModel to the View. Here is a sample from this project. 

```java
package devarthur.com.iddog.model;

import java.util.ArrayList;
import java.util.List;

import devarthur.com.iddog.adapters.RecyclerViewAdapter;

public class DogDataModel
{

    private String imgUrl;

    public DogDataModel() {
    }

    public DogDataModel(String imagUrl) {
        this.imgUrl = imagUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public static void clearData(List<ArrayList> dataList, RecyclerViewAdapter mAdapter){
        dataList.clear();
        mAdapter.notifyDataSetChanged();
    }

}


```

Im not looking forward for a step by step lesson in setting up and Adapter, check the Android documentation for a deeper understading. But basically the adapter is going to connect the DataModel with the View using the OnCreateViewHolder and onBindViewHolder.

```java

@Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.dogimage_row_item, parent, false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, final int position) {
        holder.txtImageDisplay.setText("Image: " + String.valueOf(position));

        Glide
                .with(mContext)
                .load(mData.get(position).getImgUrl())
                .apply(mOptions)
                .into(holder.img_data);



```




# Loopj implementation and usage. 

The api Docs state that an Json object must be passed through POST method, and a JSON object will be received after the user has logged in. The loopj implements a JsonReponseHanlder wich is usefull because we can use the onSucess and OnFailure to prompt the user or get done some actions of our own. 

```java
  private void attempPost() {
        String userEmail = userEmailView.getText().toString();

        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("email", userEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ByteArrayEntity be = new ByteArrayEntity(jsonParams.toString().getBytes());

        client.post(getApplicationContext(), API_URL, be, ContentType.APPLICATION_JSON.getMimeType(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                UserDataModel userData = UserDataModel.fromJson(response);
                storeToken(userData.getmToken());
                openListActivity();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                showErrorDialog(getString(R.string.invalidEmailText));

            }
            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
            }
        });
    }


```







