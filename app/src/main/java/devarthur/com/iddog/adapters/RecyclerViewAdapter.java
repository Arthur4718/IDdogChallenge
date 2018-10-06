package devarthur.com.iddog.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


import devarthur.com.iddog.model.DogDataModel;
import devarthur.com.iddog.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.mViewHolder> {

    private Context mContext;
    private List<DogDataModel> mData;


    public RecyclerViewAdapter(Context context, List<DogDataModel> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.dogimage_row_item, parent, false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {

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
