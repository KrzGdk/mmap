package mmap.mindmap;

import mmap.config.Configuration;
import mmap.mindmap.content.MapNodeContent;
import mmap.mindmap.content.MapNodeContentFactory;
import mmap.xmind.content.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapNode {
    private List<String> cssClasses = new ArrayList<>();
    private int level;
    private Position position;
    private String title;
    private MapNodeContent content;
    private List<MapNode> children = new ArrayList<>();

    MapNode(Topic topic) {
        this(topic, 0);
    }

    private MapNode(Topic topic, int level) {
        this.level = level;
        this.title = topic.getTitle();
        if (topic.getStyleId() != null) {
            this.cssClasses.add(topic.getStyleId());
        }
        this.content = MapNodeContentFactory.create(topic);
        createChildren(topic);
    }

    private void createChildren(Topic topic) {
        if (topic.getChildren() != null && topic.getChildren().getTopics() != null
                && topic.getChildren().getTopics().getTopics() != null) {
            topic.getChildren().getTopics().getTopics()
                    .forEach(t -> children.add(new MapNode(t, level + 1)));
        }
    }

    int computeCoordinates(int position) {

        if (getChildren().size() == 0) {
            setPosition(new Position(Math.round(position * Configuration.SLIDE_WIDTH), level * Configuration.SLIDE_HEIGHT));
            return 1;
        } else {
            int sum = position;
            for (MapNode child : getChildren()) {
                sum += child.computeCoordinates(sum);
            }
            int leaves = sum - position;
            setPosition(new Position(Math.round((position + (((float) leaves) / 2) - 0.5f) * Configuration.SLIDE_WIDTH), level * Configuration.SLIDE_HEIGHT));
            return leaves;
        }
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MapNode> getChildren() {
        return children;
    }

    public void setChildren(List<MapNode> children) {
        this.children = children;
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

    public void addCssClasses(List<String> cssClasses) {
        this.cssClasses.addAll(cssClasses);
    }

    public int getLevel() {
        return level;
    }
}
