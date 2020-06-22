package ru.lubich.bellintegrator.reportgenerator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.lubich.bellintegrator.reportgenerator.dto.DataSourceDto;
import ru.lubich.bellintegrator.reportgenerator.io.read.FileReaderImpl;
import ru.lubich.bellintegrator.reportgenerator.util.TSVParser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class TSVParserTest {

    private static final String SOURCE_DATA_NAME = "source-data.tsv";

    private static FileReaderImpl fileReader;
    private static TSVParser tsvParser;
    private static String absoluteSourceDataPath;
    private static Stream<String> dataSourceStream;

    @Before
    public void setting() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File fileSourceData = new File(classLoader.getResource(SOURCE_DATA_NAME).getFile());
        absoluteSourceDataPath = fileSourceData.getAbsolutePath();
        fileReader = new FileReaderImpl(absoluteSourceDataPath, "UTF-16");
        dataSourceStream = fileReader.readStream();
    }

    @Test
    public void testParseDataSource(){
        tsvParser = new TSVParser(dataSourceStream);
        List<DataSourceDto> dataSourceDtoList = tsvParser.parse();
        Assert.assertEquals("25/11", dataSourceDtoList.get(0).getDate());
        Assert.assertEquals("26/11",dataSourceDtoList.get(1).getDate());
        Assert.assertEquals("27/11",dataSourceDtoList.get(2).getDate());
        Assert.assertEquals("28/11",dataSourceDtoList.get(3).getDate());
        Assert.assertEquals("29/11/2009",dataSourceDtoList.get(4).getDate());
    }

}
