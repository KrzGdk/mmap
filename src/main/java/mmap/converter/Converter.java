package mmap.converter;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import mmap.mindmap.MapNode;
import mmap.presentation.Slide;
import mmap.xmind.XmindFile;
import mmap.xmind.content.XmindContent;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Converter {

    public Converter() {
    }

    public String convert(File file) throws IOException, JAXBException {
        XmindFile xmindFile = new XmindFile(file);
        XmindContent xml = xmindFile.getContent();
        Template template = Mustache.compiler().compile(new FileReader(new File("src/main/resources/template.html")));
        List<Slide> slideList = createSlides(xmindFile);
        try (Writer out = new FileWriter("src/main/resources/test.html")) {
            template.execute(new Object() {
                Object slides = slideList;
                Object mainTitle = slideList.get(0).getContent();
            }, out);
        }
        System.out.println("done");
        return null;
    }

    public List<Slide> createSlides(XmindFile xmindFile) throws IOException, JAXBException {
        List<Slide> slides = new ArrayList<>();

        MapNode root = new MapNode(xmindFile.getContent().getSheet().getTopic());
        root.computeCoordinates(0);

        Slide titleSlide = new Slide(root.getContent(), root.getPosition().getX(), root.getPosition().getY());
        slides.add(titleSlide);

        createSlides(root, slides);

        return slides;
    }

    private void createSlides(MapNode root, List<Slide> slides) {
        for (MapNode child : root.getChildren()) {
            slides.add(new Slide(child.getContent(), child.getPosition().getX(), child.getPosition().getY()));
            createSlides(child, slides);
        }
    }


}
