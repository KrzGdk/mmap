package mmap.xmind;

import mmap.xmind.content.XmapContent;
import mmap.xmind.styles.XmapStyles;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class XmindFile {

    private ZipFile zip;

    public XmindFile(File xmindFile) throws IOException {
        zip = new ZipFile(xmindFile);
    }

    public XmapContent getContent() throws IOException, JAXBException {
        ZipEntry contentEntry = zip.getEntry("content.xml");
        InputStream contentInputStream = zip.getInputStream(contentEntry);
        JAXBContext ctx = JAXBContext.newInstance(XmapContent.class);
        Unmarshaller um = ctx.createUnmarshaller();
        return (XmapContent) um.unmarshal(contentInputStream);
    }

    public XmapStyles getStyles() throws IOException, JAXBException {
        ZipEntry contentEntry = zip.getEntry("styles.xml");
        InputStream contentInputStream = zip.getInputStream(contentEntry);
        JAXBContext ctx = JAXBContext.newInstance(XmapStyles.class);
        Unmarshaller um = ctx.createUnmarshaller();
        return (XmapStyles) um.unmarshal(contentInputStream);
    }

    public Map<String, InputStream> getImages() throws IOException {
        List<? extends ZipEntry> entries = Collections.list(zip.entries());
        Map<String, InputStream> images = new HashMap<>();

        List<ZipEntry> attachmentsEntries = new ArrayList<>();

        for (ZipEntry entry : entries) {
            if (entry.getName().startsWith("attachments/")) {
                attachmentsEntries.add(entry);
            }
        }


        for (ZipEntry attachmentsEntry : attachmentsEntries) {
            images.put(attachmentsEntry.getName().split("/")[1], zip.getInputStream(attachmentsEntry));
        }

        return images;
    }

}
