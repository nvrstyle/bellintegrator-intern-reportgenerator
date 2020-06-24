package ru.lubich.bellintegrator.reportgenerator;

import ru.lubich.bellintegrator.reportgenerator.dto.DataSourceDto;
import ru.lubich.bellintegrator.reportgenerator.dto.SettingDto;
import ru.lubich.bellintegrator.reportgenerator.util.TSVParser;
import ru.lubich.bellintegrator.reportgenerator.util.XMLParser;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

public class ReportBuilder {

    private static final String COLUMN_SEPARATOR = "|";
    private static final String LINE_SEPARATOR = "-";

    private List<DataSourceDto> dataSourceList;
    private SettingDto settings;
    private TSVParser tsvParser;
    private XMLParser xmlParser;
    private StringBuilder reportHeader;
    private StringBuilder reportBody;
    private StringBuilder reportPageBody;
    private List<String> reportPages;
    private StringBuilder reportPageItemBody;
    private int countRows = 0;

    public ReportBuilder(XMLParser xmlParser, TSVParser tsvParser) {
        this.xmlParser = xmlParser;
        this.tsvParser = tsvParser;
    }

    public String generate() throws JAXBException {
        settings = xmlParser.parse();
        dataSourceList = tsvParser.parse();

        int pageWidth = settings.getPage().getWidth();
        int pageHeight = settings.getPage().getHeight();
        reportBody = new StringBuilder(pageWidth);
        reportPageBody = new StringBuilder(pageWidth);
        reportPages = new ArrayList<>();
        // Generate report pages
        for (DataSourceDto dataSourceListItem : dataSourceList) {
            generatePageItemBody(dataSourceListItem);
            int currentPageItemLength = reportPageItemBody.toString().split("\n").length;
            countRows += currentPageItemLength;
            if (countRows >= pageHeight) {
                countRows = currentPageItemLength;
                reportPages.add(reportPageBody.toString());
                reportPageBody = new StringBuilder(pageWidth);
            }
            reportPageBody.append(reportPageItemBody);
        }
        //Added last page in list of pages
        reportPages.add(reportPageBody.toString());
        //Generate complete report
        int reportPageSize = reportPages.size();
        for (int i = 0; i < reportPageSize; i++) {
            if (i != 0)
                reportBody.append("~\n");
            generatePageHeader();
            reportBody.append(reportPages.get(i));
        }
        return reportBody.toString();
    }

    public void generatePageHeader() {
        int pageWidth = settings.getPage().getWidth();
        reportHeader = new StringBuilder(pageWidth);
        for (SettingDto.Column column : settings.getColumns()) {
            reportHeader.append(COLUMN_SEPARATOR);
            reportHeader.append(" ");
            int columnSpace = column.getWidth() - column.getTitle().length();
            reportHeader.append(column.getTitle());
            for (int i = 0; i < columnSpace + 1; i++) {
                reportHeader.append(" ");
            }
        }
        reportHeader.append(COLUMN_SEPARATOR);
        reportBody.append(reportHeader);
        reportBody.append("\n");
    }
    //Generate one line of report body
    private void generatePageItemBody(DataSourceDto dataSourceListItem) {

        int pageWidth = settings.getPage().getWidth();
        reportPageItemBody = new StringBuilder(pageWidth);
        String numDataTmp;
        String dateDataTmp;
        String fullNameDataTmp;

        for (int i = 0; i < pageWidth; i++) {
            reportPageItemBody.append(LINE_SEPARATOR);
        }
        reportPageItemBody.append("\n");
        int numberColumnWidth = settings.getColumns().get(0).getWidth();
        int dateColumnWidth = settings.getColumns().get(1).getWidth();
        int fullNameColumnWidth = settings.getColumns().get(2).getWidth();

        while (dataSourceListItem.getNumber().length() > numberColumnWidth ||
                dataSourceListItem.getDate().length() > dateColumnWidth ||
                dataSourceListItem.getFullName().length() > fullNameColumnWidth) {

            //Processing Number column
            if (dataSourceListItem.getNumber().length() > numberColumnWidth) {
                numDataTmp = dataSourceListItem.getNumber().substring(0, numberColumnWidth);
                numDataTmp = String.format(" %-" + settings.getColumns().get(0).getWidth() + "s ", numDataTmp);
                dataSourceListItem.setNumber(dataSourceListItem.getNumber().substring(numberColumnWidth));
                reportPageItemBody.append(COLUMN_SEPARATOR + numDataTmp);
            } else {
                numDataTmp = String.format(" %-" + settings.getColumns().get(0).getWidth() + "s ", dataSourceListItem.getNumber());
                reportPageItemBody.append(COLUMN_SEPARATOR + numDataTmp);
                dataSourceListItem.setNumber("");
            }
            //Processing Date column
            if (dataSourceListItem.getDate().length() > dateColumnWidth) {
                dateDataTmp = dataSourceListItem.getDate().substring(0, dateColumnWidth);
                dateDataTmp = String.format(" %-" + settings.getColumns().get(1).getWidth() + "s ", dateDataTmp);
                dataSourceListItem.setDate(dataSourceListItem.getDate().substring(dateColumnWidth));
                reportPageItemBody.append(COLUMN_SEPARATOR + dateDataTmp);
            } else {
                dateDataTmp = String.format(" %-" + settings.getColumns().get(1).getWidth() + "s ", dataSourceListItem.getDate());
                reportPageItemBody.append(COLUMN_SEPARATOR + dateDataTmp);
                dataSourceListItem.setDate("");

            }
            //Processing FullName column
            if (dataSourceListItem.getFullName().length() > fullNameColumnWidth) {
                dataSourceListItem.setFullName(dataSourceListItem.getFullName().trim());
                fullNameDataTmp = dataSourceListItem.getFullName().substring(0, fullNameColumnWidth);
                fullNameDataTmp = String.format(" %-" + settings.getColumns().get(2).getWidth() + "s ", fullNameDataTmp);
                dataSourceListItem.setFullName(dataSourceListItem.getFullName().substring(fullNameColumnWidth));
                reportPageItemBody.append(COLUMN_SEPARATOR + fullNameDataTmp + COLUMN_SEPARATOR);
            } else {
                fullNameDataTmp = String.format(" %-" + settings.getColumns().get(2).getWidth() + "s ", dataSourceListItem.getFullName());
                reportPageItemBody.append(COLUMN_SEPARATOR + fullNameDataTmp + COLUMN_SEPARATOR);
                dataSourceListItem.setFullName("");
            }

            reportPageItemBody.append("\n");
        }

        dataSourceListItem.setFullName(dataSourceListItem.getFullName().trim());
        if (dataSourceListItem.getNumber().length() != 0 ||
                dataSourceListItem.getDate().length() != 0 ||
                dataSourceListItem.getFullName().length() != 0) {
            reportPageItemBody.append(COLUMN_SEPARATOR + String.format(" %-" +
                    settings.getColumns().get(0).getWidth() + "s ", dataSourceListItem.getNumber())
                    + COLUMN_SEPARATOR + String.format(" %-" +
                    settings.getColumns().get(1).getWidth() + "s ", dataSourceListItem.getDate())
                    + COLUMN_SEPARATOR + String.format(" %-" +
                    settings.getColumns().get(2).getWidth() + "s ", dataSourceListItem.getFullName())
                    + COLUMN_SEPARATOR + "\n");
        }
    }
}