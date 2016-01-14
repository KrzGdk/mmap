package mmap.mindmap.content;

import mmap.xmind.content.Topic;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MapNodeContentFactory {

    public static MapNodeContent create(Topic topic) {
        MapNodeContent content = new EmptyMapNodeContent();
        if(hasImage(topic) && hasText(topic)) {
            content = new ImageTextMapNodeContent("images" + File.separator + topic.getImg().getSrc().split("/")[1], Arrays.asList(topic.getNotes().getPlain().split("\r\n")));
        }
        if (hasImage(topic) && !hasText(topic)) {
            content = new ImageMapNodeContent("images" + File.separator + topic.getImg().getSrc().split("/")[1]);
        }
        if (!hasImage(topic) && hasText(topic)) {
            content = new TextMapNodeContent(Arrays.asList(topic.getNotes().getPlain().split("\r\n")));
        }
        return content;
    }

    private static boolean hasImage(Topic topic) {
        return topic.getImg() != null;
    }

    private static boolean hasText(Topic topic) {
        return topic.getNotes() != null && topic.getNotes().getHtml() != null
                && topic.getNotes().getPlain() != null && !topic.getNotes().getPlain().isEmpty();
    }
}
