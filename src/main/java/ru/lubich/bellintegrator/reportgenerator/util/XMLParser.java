package ru.lubich.bellintegrator.reportgenerator.util;

import ru.lubich.bellintegrator.reportgenerator.dto.SettingDto;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLParser {

    private File fileSettings;

    public XMLParser(File fileSettings) {
        this.fileSettings = fileSettings;
    }

    public SettingDto parse() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(SettingDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        SettingDto settingDto = (SettingDto)unmarshaller.unmarshal(fileSettings);
        return settingDto;
    }
}
