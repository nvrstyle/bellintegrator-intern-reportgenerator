package ru.lubich.bellintegrator.reportgenerator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.lubich.bellintegrator.reportgenerator.dto.SettingDto;
import ru.lubich.bellintegrator.reportgenerator.io.read.FileReaderImpl;
import ru.lubich.bellintegrator.reportgenerator.util.XMLParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

public class XMLParserTest {

    private static final String SETTINGS_NAME = "settings.xml";
    private XMLParser xmlParser;
    private File fileSettings;

    private static FileReaderImpl fileReader;
    private static String absoluteSettingsPath;

    @Before
    public void setting() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File fileSettings = new File(classLoader.getResource(SETTINGS_NAME).getFile());
        absoluteSettingsPath = fileSettings.getAbsolutePath();
        fileReader = new FileReaderImpl(absoluteSettingsPath);
        this.fileSettings = fileReader.readFile();
    }

    @Test
    public void testParseSettings() throws JAXBException {
        xmlParser = new XMLParser(fileSettings);
        SettingDto settingDto = xmlParser.parse();
        Assert.assertEquals(32, settingDto.getPage().getWidth());
        Assert.assertEquals(12, settingDto.getPage().getHeight());
        Assert.assertEquals(8, settingDto.getColumns().get(0).getWidth());
        Assert.assertEquals("Номер", settingDto.getColumns().get(0).getTitle());
        Assert.assertEquals(7, settingDto.getColumns().get(1).getWidth());
        Assert.assertEquals("Дата", settingDto.getColumns().get(1).getTitle());
        Assert.assertEquals(7, settingDto.getColumns().get(2).getWidth());
        Assert.assertEquals("ФИО", settingDto.getColumns().get(2).getTitle());
    }
}
