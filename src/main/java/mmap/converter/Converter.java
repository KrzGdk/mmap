package mmap.converter;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import mmap.config.Configuration;
import mmap.converter.styles.CssSelector;
import mmap.converter.styles.StylesConverter;
import mmap.mindmap.MapNode;
import mmap.mindmap.MindMap;
import mmap.mindmap.content.EmptyMapNodeContent;
import mmap.presentation.Slide;
import mmap.xmind.XmindFile;
import mmap.xmind.content.XmapContent;
import mmap.xmind.styles.XmapStyles;
import org.apache.commons.io.FilenameUtils;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Converter {

    private final boolean revisitParent;

    public Converter(boolean revisitParent) {
        this.revisitParent = revisitParent;
    }

    public String convert(File mindMapFile) throws IOException, JAXBException {
        StylesConverter stylesConverter = new StylesConverter();

        createStaticFiles();

        XmindFile xmindFile = new XmindFile(mindMapFile);
        XmapContent xml = xmindFile.getContent();
        XmapStyles styles = xmindFile.getStyles();
        List<CssSelector> cssSelectors = stylesConverter.createStyles(styles);

        InputStream templateStream = getClass().getResourceAsStream("/template.html");
        Template template = Mustache.compiler().compile(new InputStreamReader(templateStream));
        int automaticStylesCount = 0;
        if (styles.getAutomaticStyles() != null) {
            automaticStylesCount = stylesConverter.countAutomaticLineStyles(styles.getAutomaticStyles().getStyles());
        }
        List<Slide> slideList = createSlides(xmindFile, cssSelectors, automaticStylesCount);
        createImages(xmindFile.getImages());

        try (Writer out = new OutputStreamWriter(new FileOutputStream(Configuration.OUTPUT_ROOT_DIR + File.separator + FilenameUtils.getBaseName(mindMapFile.getAbsolutePath()) + ".html"), Charset.forName("UTF-8").newEncoder())) {
            template.execute(new Object() {
                Object slides = slideList;
                Object slidesCount = slideList.size();
                Object mainTitle = slideList.get(0).getTitle();
            }, out);
        }
        System.out.println("done");
        return null;
    }

    private String readPath(URL resource) {
        if (System.getProperty("os.name").contains("indow") && resource.getPath().contains("!"))
            return ".." + resource.getPath().substring(resource.getPath().indexOf("!") + 1);
        else return resource.getPath();
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
        InputStream impressJs = getClass().getResourceAsStream("/js/impress.js");
        Files.copy(impressJs, new File(Configuration.JS_DIR + File.separator + "impress.js").toPath(), StandardCopyOption.REPLACE_EXISTING);
        impressJs.close();
        InputStream demoCss = getClass().getResourceAsStream("/css/demo.css");
        Files.copy(demoCss, new File(Configuration.CSS_DIR + File.separator + "demo.css").toPath(), StandardCopyOption.REPLACE_EXISTING);
        demoCss.close();
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

    public List<Slide> createSlides(XmindFile xmindFile, List<CssSelector> cssSelectors, int automaticStyleCount) throws IOException, JAXBException {
        List<Slide> slides = new ArrayList<>();

        MindMap mindMap = MindMap.create(xmindFile.getContent().getSheet().getTopic());

        Slide titleSlide = new Slide(mindMap.getRoot());
        titleSlide.addCssClass("slide");
        slides.add(titleSlide);

        createSlides(mindMap.getRoot(), slides, cssSelectors, automaticStyleCount, null);

        return slides;
    }

    private void createSlides(MapNode root, List<Slide> slides, List<CssSelector> cssSelectors, int automaticStyleCount, Slide parentSlide) {
        List<MapNode> children = root.getChildren();
        for (int i = 0; i < children.size(); i++) {
            MapNode child = children.get(i);
            Slide slide = new Slide(child);
            slide.setParent(findSlideId(root, slides));
            if (child.getLevel() == 1 && automaticStyleCount != 0) {
                child.addCssClass("autoLine" + (i + 1) % automaticStyleCount);
                slide.addCssClass("xmap-autoLine" + (i + 1) % automaticStyleCount);
            }
            for (String rootCssClass : root.getCssClasses()) {
                for (CssSelector cssSelector : cssSelectors) {
                    if (cssSelector.getName().contains(rootCssClass) && cssSelector.getName().contains(".footer")) {
                        child.addCssClass(rootCssClass);
                        slide.addCssClass("xmap-" + rootCssClass);
                    }
                }

            }
            slide.addCssClass("slide");
            slides.add(slide);
            createSlides(child, slides, cssSelectors, automaticStyleCount, slide);
        }
        if (revisitParent && children.size() > 0 && parentSlide != null) {
            slides.add(parentSlide);
        }
    }

    private int findSlideId(MapNode root, List<Slide> slides) {
        for (int i = 0; i < slides.size(); i++) {
            Slide slide = slides.get(i);
            if (slide.getDataX().equals(root.getPosition().getX()) && slide.getDataY().equals(root.getPosition().getY())) {
                return i;
            }
        }
        return 0;
    }


}
