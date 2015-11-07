package mmap.mindmap.content;

public class EmptyMapNodeContent extends MapNodeContent {
    @Override
    public boolean hasImage() {
        return false;
    }

    @Override
    public boolean hasText() {
        return false;
    }
}
