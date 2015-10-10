package mmap.xmind;

import mmap.xmind.content.XmindContent;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class XmindFile {

    ZipFile zip;

    public XmindFile(File xmindFile) throws IOException {
        zip = new ZipFile(xmindFile);
    }

    public XmindContent getContent() throws IOException, JAXBException {
        List<? extends ZipEntry> zipEntries = Collections.list(zip.entries());
        ZipEntry contentEntry = zipEntries.stream()
                .filter(e -> e.getName().equals("content.xml"))
                .findFirst().get();
        InputStream contentInputStream = zip.getInputStream(contentEntry);
        JAXBContext ctx = JAXBContext.newInstance(XmindContent.class);
        Unmarshaller um = ctx.createUnmarshaller();
        return (XmindContent) um.unmarshal(contentInputStream);
    }
}
