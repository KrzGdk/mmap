package mmap.mindmap.content;

import java.util.List;

public class ImageTextMapNodeContent extends MapNodeContent {
    private String image;
    private List<String> paragraphs;

    public ImageTextMapNodeContent(String image, List<String> paragraphs) {
        this.image = image;
        this.paragraphs = paragraphs;
    }

    @Override
    public boolean hasImage() {
        return true;
    }

    @Override
    public boolean hasText() {
        return true;
    }

    public String getImage() {
        return image;
    }

    public List<String> getParagraphs() {
        return paragraphs;
    }
}
