package mmap.presentation;

import mmap.mindmap.MapNode;
import mmap.mindmap.content.MapNodeContent;

public class Slide {
    private String cssClass;
    private String cssSecondaryClass;
    private String title;
    private Integer dataX;
    private Integer dataY;
    private MapNodeContent content;

    public Slide(MapNode root) {
        this.title = root.getTitle();
        this.dataX = root.getPosition().getX();
        this.dataY = root.getPosition().getY();
        if(root.getCssClass() != null) {
            this.cssClass = "xmap-" + root.getCssClass();
        }
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

    public MapNodeContent getContent() {
        return content;
    }

    public void setContent(MapNodeContent content) {
        this.content = content;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
}
