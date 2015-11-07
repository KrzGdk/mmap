package mmap.converter;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import mmap.config.Configuration;
import mmap.converter.styles.CssSelector;
import mmap.converter.styles.StylesConverter;
import mmap.mindmap.MapNode;
import mmap.mindmap.MindMap;
import mmap.presentation.Slide;
import mmap.xmind.XmindFile;
import mmap.xmind.content.XmapContent;
import mmap.xmind.styles.Style;
import mmap.xmind.styles.XmapStyles;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Converter {


    public Converter() {
    }

    public String convert(File file) throws IOException, JAXBException {
        StylesConverter stylesConverter = new StylesConverter();

        createStaticFiles();

        XmindFile xmindFile = new XmindFile(file);
        XmapContent xml = xmindFile.getContent();
        XmapStyles styles = xmindFile.getStyles();
        List<CssSelector> cssSelectors = stylesConverter.createStyles(styles);

        Template template = Mustache.compiler().compile(new FileReader(new File("src/main/resources/template.html")));
        List<Slide> slideList = createSlides(xmindFile, cssSelectors);
        createImages(xmindFile.getImages());

        try (Writer out = new FileWriter(Configuration.OUTPUT_ROOT_DIR + File.separator + "test.html")) {
            template.execute(new Object() {
                Object slides = slideList;
                Object slidesCount = slideList.size();
                Object mainTitle = slideList.get(0).getTitle();
            }, out);
        }
        System.out.println("done");
        return null;
    }


    private void createStaticFiles() throws IOException {
        File outputDir = new File(Configuration.OUTPUT_ROOT_DIR);
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }
        File imagesDir = new File(Configuration.IMAGES_DIR);
        if (!imagesDir.exists()) {
            imagesDir.mkdir();
        }
        File jsDir = new File(Configuration.JS_DIR);
        if (!jsDir.exists()) {
            jsDir.mkdir();
        }
        File cssDir = new File(Configuration.CSS_DIR);
        if (!cssDir.exists()) {
            cssDir.mkdir();
        }
        File impressJsFile = new File("src/main/resources/js/impress.js");
        Files.copy(impressJsFile.toPath(), new File(Configuration.JS_DIR + File.separator + "impress.js").toPath(),
                StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
        File styleFile = new File("src/main/resources/css/demo.css");
        Files.copy(styleFile.toPath(), new File(Configuration.CSS_DIR + File.separator + "demo.css").toPath(),
                StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
    }

    private void createImages(Map<String, InputStream> images) throws IOException {

        for (Map.Entry<String, InputStream> image : images.entrySet()) {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(Configuration.IMAGES_DIR + File.separator + image.getKey()));
            byte[] bytesIn = new byte[4096];
            int read;
            while ((read = image.getValue().read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
            bos.close();
        }
    }

    public List<Slide> createSlides(XmindFile xmindFile, List<CssSelector> cssSelectors) throws IOException, JAXBException {
        List<Slide> slides = new ArrayList<>();

        MindMap mindMap = MindMap.create(xmindFile.getContent().getSheet().getTopic());

        Slide titleSlide = new Slide(mindMap.getRoot());
        slides.add(titleSlide);

        createSlides(mindMap.getRoot(), slides, cssSelectors);

        return slides;
    }

    private void createSlides(MapNode root, List<Slide> slides, List<CssSelector> cssSelectors) {
        for (MapNode child : root.getChildren()) {
            Slide slide = new Slide(child);
            if(cssSelectors.stream()
                    .filter(s -> root.getCssClass()!= null && s.getName().contains(root.getCssClass()) && s.getName().contains(".footer"))
                    .findFirst().isPresent()) {
                child.setCssClass(root.getCssClass());
                slide.setCssClass("xmap-" + root.getCssClass());
            }
            slides.add(slide);
            createSlides(child, slides, cssSelectors);
        }
    }


}
