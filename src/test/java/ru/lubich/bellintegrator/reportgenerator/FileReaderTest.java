package ru.lubich.bellintegrator.reportgenerator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.lubich.bellintegrator.reportgenerator.io.read.FileReaderImpl;

import java.io.*;

public class FileReaderTest {

    private static final String SETTINGS_NAME = "settings.xml";
    private static final String SOURCE_DATA_NAME = "source-data.tsv";

    private static FileReaderImpl fileReader;
    private static String absoluteSettingsPath;
    private static String absoluteSourceDataPath;

    @Before
    public void setting(){
        ClassLoader classLoader = getClass().getClassLoader();
        File fileSettings = new File(classLoader.getResource(SETTINGS_NAME).getFile());
        absoluteSettingsPath = fileSettings.getAbsolutePath();

        File fileSourceData = new File(classLoader.getResource(SOURCE_DATA_NAME).getFile());
        absoluteSourceDataPath = fileSourceData.getAbsolutePath();
    }

    @Test
    public void testReadFile() throws IOException {
        fileReader = new FileReaderImpl(absoluteSettingsPath);
        Assert.assertEquals(22, fileReader.readStream().count());

        fileReader = new FileReaderImpl(absoluteSourceDataPath, "UTF-16");
        Assert.assertEquals(5, fileReader.readStream().count());
    }

}
