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
