package mmap.mindmap.content;

import java.util.List;

public class TextMapNodeContent extends MapNodeContent {

    private List<String> paragraphs;

    public TextMapNodeContent(List<String> paragraphs) {
        this.paragraphs = paragraphs;
    }

    @Override
    public boolean hasImage() {
        return false;
    }

    @Override
    public boolean hasText() {
        return true;
    }

    public List<String> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<String> paragraphs) {
        this.paragraphs = paragraphs;
    }
}
