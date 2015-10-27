package mmap.converter;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import mmap.mindmap.MapNode;
import mmap.mindmap.MindMap;
import mmap.presentation.Slide;
import mmap.xmind.XmindFile;
import mmap.xmind.content.XmindContent;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Converter {

    private final String OUTPUT_ROOT_DIR = "out";
    private final String IMAGES_DIR = OUTPUT_ROOT_DIR + File.separator + "images";
    private final String JS_DIR = OUTPUT_ROOT_DIR + File.separator + "js";
    private final String CSS_DIR = OUTPUT_ROOT_DIR + File.separator + "css";

    public Converter() {
    }

    public String convert(File file) throws IOException, JAXBException {
        createStaticFiles();

        XmindFile xmindFile = new XmindFile(file);
        XmindContent xml = xmindFile.getContent();
        Template template = Mustache.compiler().compile(new FileReader(new File("src/main/resources/template.html")));
        List<Slide> slideList = createSlides(xmindFile);
        createImages(xmindFile.getImages());

        try (Writer out = new FileWriter(OUTPUT_ROOT_DIR + File.separator + "test.html")) {
            template.execute(new Object() {
                Object slides = slideList;
                Object mainTitle = slideList.get(0).getTitle();
            }, out);
        }
        System.out.println("done");
        return null;
    }

    private void createStaticFiles() throws IOException {
        File outputDir = new File(OUTPUT_ROOT_DIR);
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }
        File imagesDir = new File(IMAGES_DIR);
        if (!imagesDir.exists()) {
            imagesDir.mkdir();
        }
        File jsDir = new File(JS_DIR);
        if (!jsDir.exists()) {
            jsDir.mkdir();
        }
        File cssDir = new File(CSS_DIR);
        if (!cssDir.exists()) {
            cssDir.mkdir();
        }
        File impressJsFile = new File("src/main/resources/js/impress.js");
        Files.copy(impressJsFile.toPath(), new File(JS_DIR + File.separator + "impress.js").toPath(),
                StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
        File styleFile = new File("src/main/resources/css/demo.css");
        Files.copy(styleFile.toPath(), new File(CSS_DIR + File.separator + "demo.css").toPath(),
                StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
    }

    private void createImages(Map<String, InputStream> images) throws IOException {

        for (Map.Entry<String, InputStream> image : images.entrySet()) {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(IMAGES_DIR + File.separator + image.getKey()));
            byte[] bytesIn = new byte[4096];
            int read;
            while ((read = image.getValue().read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
            bos.close();
        }
    }

    public List<Slide> createSlides(XmindFile xmindFile) throws IOException, JAXBException {
        List<Slide> slides = new ArrayList<>();

        MindMap mindMap = MindMap.create(xmindFile.getContent().getSheet().getTopic());

        Slide titleSlide = new Slide(mindMap.getRoot());
        slides.add(titleSlide);

        createSlides(mindMap.getRoot(), slides);

        return slides;
    }

    private void createSlides(MapNode root, List<Slide> slides) {
        for (MapNode child : root.getChildren()) {
            slides.add(new Slide(child));
            createSlides(child, slides);
        }
    }


}
