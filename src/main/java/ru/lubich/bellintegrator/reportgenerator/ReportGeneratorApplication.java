package ru.lubich.bellintegrator.reportgenerator;

import ru.lubich.bellintegrator.reportgenerator.dto.DataSourceDto;
import ru.lubich.bellintegrator.reportgenerator.dto.SettingDto;
import ru.lubich.bellintegrator.reportgenerator.io.read.FileReaderImpl;
import ru.lubich.bellintegrator.reportgenerator.io.write.FileWriterImpl;
import ru.lubich.bellintegrator.reportgenerator.util.TSVParser;
import ru.lubich.bellintegrator.reportgenerator.util.XMLParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public class ReportGeneratorApplication {

    private static FileReaderImpl fileReaderSettings;
    private static FileReaderImpl fileReaderSourceData;
    private static FileWriterImpl fileWriterReport;
    private static String absoluteSettingsPath;
    private static String absoluteSourceDataPath;
    private static String absoluteReportPath;
    private File fileSettings;
    private static Stream<String> dataSourceStream;
    private static XMLParser xmlParser;
    private static TSVParser tsvParser;
    private static ReportBuilder reportBuilder;


    public static void main(String[] args) throws IOException, JAXBException {
        if (args.length == 3) {
            absoluteSettingsPath = args[0];
            absoluteSourceDataPath = args[1];
            absoluteReportPath = args[2];

            fileReaderSettings = new FileReaderImpl(absoluteSettingsPath);
            fileReaderSourceData = new FileReaderImpl(absoluteSourceDataPath, "UTF-16");

            xmlParser = new XMLParser(fileReaderSettings.readFile());
            dataSourceStream = fileReaderSourceData.readStream();
            tsvParser = new TSVParser(dataSourceStream);
            fileWriterReport = new FileWriterImpl(absoluteReportPath, "UTF-16");

            reportBuilder = new ReportBuilder(xmlParser, tsvParser);
            String report = reportBuilder.generate();
            fileWriterReport.writeString(report);
            System.out.println("Complete generated report");

        }
        else {
            System.out.println("Wrong number of arguments");
        }
    }
}
