package mmap.presentation;

import mmap.mindmap.MapNode;
import mmap.mindmap.content.MapNodeContent;

import java.util.ArrayList;
import java.util.List;

public class Slide {
    private List<String> cssClasses = new ArrayList<>();
    private String cssSecondaryClass;
    private String title;
    private Integer dataX;
    private Integer dataY;
    private MapNodeContent content;
    private int parent;

    public Slide(MapNode root) {
        this.title = root.getTitle();
        this.dataX = root.getPosition().getX();
        this.dataY = root.getPosition().getY();
        root.getCssClasses().forEach(s -> this.cssClasses.add("xmap-" + s));
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

    public List<String> getCssClasses() {
        return cssClasses;
    }

    public void addCssClass(String cssClass) {
        this.cssClasses.add(cssClass);
    }

    public void setParent(int parent) {
        this.parent = parent;
    }
}
