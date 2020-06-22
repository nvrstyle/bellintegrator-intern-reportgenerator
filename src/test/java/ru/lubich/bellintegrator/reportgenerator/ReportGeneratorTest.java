package ru.lubich.bellintegrator.reportgenerator;

import org.junit.Before;
import org.junit.Test;
import ru.lubich.bellintegrator.reportgenerator.ReportBuilder;
import ru.lubich.bellintegrator.reportgenerator.io.read.FileReaderImpl;
import ru.lubich.bellintegrator.reportgenerator.io.write.FileWriterImpl;
import ru.lubich.bellintegrator.reportgenerator.util.TSVParser;
import ru.lubich.bellintegrator.reportgenerator.util.XMLParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public class ReportGeneratorTest {

    private static final String SETTINGS_NAME = "settings.xml";
    private static final String SOURCE_DATA_NAME = "source-data.tsv";
    private static final String REPORT_NAME = "report.txt";

    private static FileReaderImpl fileReaderSettings;
    private static FileReaderImpl fileReaderSourceData;
    private static FileWriterImpl fileWriterReport;
    private static String absoluteSettingsPath;
    private static String absoluteSourceDataPath;
    private static String absoluteReportPath = "src/main/resources/" + REPORT_NAME;
    private File fileSettings;
    private static Stream<String> dataSourceStream;
    private XMLParser xmlParser;
    private static TSVParser tsvParser;
    private static ReportBuilder reportBuilder;

    @Before
    public void setting() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File fileSettings = new File(classLoader.getResource(SETTINGS_NAME).getFile());
        absoluteSettingsPath = fileSettings.getAbsolutePath();

        File fileSourceData = new File(classLoader.getResource(SOURCE_DATA_NAME).getFile());
        absoluteSourceDataPath = fileSourceData.getAbsolutePath();

        fileReaderSettings = new FileReaderImpl(absoluteSettingsPath);
        fileReaderSourceData = new FileReaderImpl(absoluteSourceDataPath, "UTF-16");

        this.fileSettings = fileReaderSettings.readFile();
        xmlParser = new XMLParser(this.fileSettings);
        dataSourceStream = fileReaderSourceData.readStream();
        tsvParser = new TSVParser(dataSourceStream);

        fileWriterReport = new FileWriterImpl(absoluteReportPath, "UTF-16");
    }

    @Test
    public void testGenerateReport() throws IOException, JAXBException {
        reportBuilder = new ReportBuilder(xmlParser, tsvParser);
        reportBuilder.generate();
    }

    @Test
    public void testWriteReport() throws JAXBException, IOException {
        reportBuilder = new ReportBuilder(xmlParser, tsvParser);
        fileWriterReport.writeString(reportBuilder.generate());
    }

}
