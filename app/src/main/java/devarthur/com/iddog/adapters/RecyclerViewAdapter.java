package devarthur.com.iddog.adapters;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import devarthur.com.iddog.activities.ImageDetail;
import devarthur.com.iddog.model.DogDataModel;
import devarthur.com.iddog.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.mViewHolder> {

    private Context mContext;
    private List<DogDataModel> mData;
    //Glide member variable
    //Sets glide behavior and rules like diskCache
    private RequestOptions mOptions;


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

        holder.img_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageDetail.class);
                intent.putExtra("extraImageText", String.valueOf(position));
                intent.putExtra("extraUrl",mData.get(position).getImgUrl());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {

        TextView txtImageDisplay;
        ImageView img_data;

        public mViewHolder(View itemView) {
            super(itemView);

            txtImageDisplay = itemView.findViewById(R.id.imageUrl);
            img_data = itemView.findViewById(R.id.imgContentViewItem);
        }

    }

}
