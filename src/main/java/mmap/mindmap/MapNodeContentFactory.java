package mmap.mindmap;

import mmap.xmind.content.Topic;

import java.io.File;

public class MapNodeContentFactory {

    private final String IMAGES_DIR = "images";

    public static MapNodeContent create(Topic topic) {
        MapNodeContent content;
        if (topic.getImg() != null) {
            content = new ImageMapNodeContent("images" + File.separator + topic.getImg().getSrc().split("/")[1]);
        } else {
            content = new EmptyMapNodeContent();
        }
        return content;
    }
}
