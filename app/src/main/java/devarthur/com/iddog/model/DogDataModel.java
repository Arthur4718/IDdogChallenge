package devarthur.com.iddog.model;

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
}
