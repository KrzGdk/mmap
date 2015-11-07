package mmap.xmind;

import mmap.xmind.content.XmapContent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class XmindFileTest {

    private XmindFile xmindFile;

    @Before
    public void setUp() throws IOException {
        xmindFile = new XmindFile(new File("src/test/resources/test.xmind"));
    }

    @Test
    public void testGetContent() throws Exception {
        XmapContent content = xmindFile.getContent();
        Assert.assertEquals("Preparation for a Conversation", content.getSheet().getTopic().getTitle());
    }
}