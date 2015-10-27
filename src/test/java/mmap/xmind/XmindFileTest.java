package mmap.xmind;

import mmap.xmind.content.XmindContent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class XmindFileTest {

    private XmindFile xmindFile;

    @Before
    public void setUp() throws IOException {
        xmindFile = new XmindFile(new File("src/test/resources/test.xmind"));
    }

    @Test
    public void testGetContent() throws Exception {
        XmindContent content = xmindFile.getContent();
        Assert.assertEquals("Preparation for a Conversation", content.getSheet().getTopic().getTitle());
    }
}