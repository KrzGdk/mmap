package mmap.xmind.content;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.ArrayList;
import java.util.List;

public class Children {

    public Topics getTopics() {
        Topics allTopics = new Topics();
        List<Topic> topicsList = new ArrayList<>();
        topics.stream().filter(t -> t.getType().equals("attached")).forEach(t -> topicsList.addAll(t.getTopics()));
        allTopics.setTopics(topicsList);
        return allTopics;
    }

//    public Topics getTopics() {
//        return topics;
//    }

//    public void setTopics(Topics topics) {
//        this.topics = topics;
//    }

    @XmlElements(value = @XmlElement(name = "topics"))
    private List<Topics> topics;
}
