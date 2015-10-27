package mmap.mindmap;

import mmap.config.Configuration;
import mmap.xmind.content.Topic;

import java.util.ArrayList;
import java.util.List;

public class MapNode {
    private int level;
    private Position position;
    private String content;
    private List<MapNode> children = new ArrayList<>();

    public MapNode(Topic topic) {
        this.level = 0;
        this.content = topic.getTitle();
        createChildren(topic);
    }

    MapNode(Topic topic, int level) {
        this.level = level;
        this.content = topic.getTitle();
        createChildren(topic);
    }

    private void createChildren(Topic topic) {
        if (topic.getChildren() != null && topic.getChildren().getTopics() != null
                && topic.getChildren().getTopics().getTopics() != null) {
            topic.getChildren().getTopics().getTopics()
                    .forEach(t -> children.add(new MapNode(t, level + 1)));
        }
    }

    public int computeCoordinates(int position) {

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<MapNode> getChildren() {
        return children;
    }

    public void setChildren(List<MapNode> children) {
        this.children = children;
    }
}
