package mmap.mindmap;

import mmap.xmind.content.Topic;

public class MindMap {

    private MapNode root;

    private MindMap(){

    }

    public static MindMap create(Topic rootTopic) {
        MindMap mindMap = new MindMap();
        mindMap.root = new MapNode(rootTopic);
        mindMap.root.computeCoordinates(0);
        return mindMap;
    }

    public MapNode getRoot() {
        return root;
    }
}
