package mmap.mindmap;

public class ImageMapNodeContent extends MapNodeContent {

    private String image;

    public ImageMapNodeContent(String image) {
        this.image = image;
    }

    @Override
    public boolean hasImage() {
        return true;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
