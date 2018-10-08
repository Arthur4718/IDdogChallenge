# IDdogChallenge
App created as a challenge from IDwall hiring team. 


This app showcases a list of images to users that can login with a valid e-mail. I will describe some of the libraries that i used
during development, and also showcasing some examples if you want to get your feet wet with some popular libraries. 


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


# Loopj implementation and usage. 



# Latest apk build. 


