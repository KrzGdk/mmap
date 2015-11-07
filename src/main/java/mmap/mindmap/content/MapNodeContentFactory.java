package mmap.mindmap.content;

import mmap.xmind.content.Topic;

import java.io.File;

public class MapNodeContentFactory {

    public static MapNodeContent create(Topic topic) {
        MapNodeContent content = new EmptyMapNodeContent();
        if(hasImage(topic) && hasText(topic)) {
            content = new ImageTextMapNodeContent("images" + File.separator + topic.getImg().getSrc().split("/")[1], topic.getNotes().getHtml().getParagraphs());
        }
        if (hasImage(topic) && !hasText(topic)) {
            content = new ImageMapNodeContent("images" + File.separator + topic.getImg().getSrc().split("/")[1]);
        }
        if (!hasImage(topic) && hasText(topic)) {
            content = new TextMapNodeContent(topic.getNotes().getHtml().getParagraphs());
        }
        return content;
    }

    private static boolean hasImage(Topic topic) {
        return topic.getImg() != null;
    }

    private static boolean hasText(Topic topic) {
        return topic.getNotes() != null && topic.getNotes().getHtml() != null
                && topic.getNotes().getHtml().getParagraphs() != null && !topic.getNotes().getHtml().getParagraphs().isEmpty();
    }
}
