package mmap.xmind;

import mmap.xmind.content.XmindContent;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class XmindFile {

    ZipFile zip;

    public XmindFile(File xmindFile) throws IOException {
        zip = new ZipFile(xmindFile);
    }

    public XmindContent getContent() throws IOException, JAXBException {
        ZipEntry contentEntry = zip.getEntry("content.xml");
        InputStream contentInputStream = zip.getInputStream(contentEntry);
        JAXBContext ctx = JAXBContext.newInstance(XmindContent.class);
        Unmarshaller um = ctx.createUnmarshaller();
        return (XmindContent) um.unmarshal(contentInputStream);
    }

    public Map<String, InputStream> getImages() throws IOException {
        List<? extends ZipEntry> entries = Collections.list(zip.entries());
        Map<String, InputStream> images = new HashMap<>();

        List<ZipEntry> attachmentsEntries = entries.stream()
                .filter(e -> e.getName().startsWith("attachments/"))
                .collect(Collectors.toList());

        for (ZipEntry attachmentsEntry : attachmentsEntries) {
            images.put(attachmentsEntry.getName().split("/")[1], zip.getInputStream(attachmentsEntry));
        }

        return images;
    }

}
