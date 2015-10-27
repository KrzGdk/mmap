package mmap.presentation;

import mmap.mindmap.MapNode;
import mmap.mindmap.MapNodeContent;

public class Slide {
    private String title;
    private Integer dataX;
    private Integer dataY;
    private MapNodeContent content;

    public Slide(MapNode root) {
        this.title = root.getTitle();
        this.dataX = root.getPosition().getX();
        this.dataY = root.getPosition().getY();
        this.content = root.getContent();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDataX() {
        return dataX;
    }

    public void setDataX(Integer dataX) {
        this.dataX = dataX;
    }

    public Integer getDataY() {
        return dataY;
    }

    public void setDataY(Integer dataY) {
        this.dataY = dataY;
    }

    public void moveX(int delta) {
        dataX += delta;
    }

    public MapNodeContent getContent() {
        return content;
    }

    public void setContent(MapNodeContent content) {
        this.content = content;
    }
}
