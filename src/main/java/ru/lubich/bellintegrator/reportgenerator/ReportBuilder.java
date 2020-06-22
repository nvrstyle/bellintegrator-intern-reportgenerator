package ru.lubich.bellintegrator.reportgenerator;

import ru.lubich.bellintegrator.reportgenerator.dto.DataSourceDto;
import ru.lubich.bellintegrator.reportgenerator.dto.SettingDto;
import ru.lubich.bellintegrator.reportgenerator.util.TSVParser;
import ru.lubich.bellintegrator.reportgenerator.util.XMLParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
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
    private StringBuilder reportLineBody;
    private int countRows = 0;

    public ReportBuilder(XMLParser xmlParser, TSVParser tsvParser) {
        this.xmlParser = xmlParser;
        this.tsvParser = tsvParser;
    }

    public String generate() throws JAXBException {
        settings = xmlParser.parse();
        dataSourceList = tsvParser.parse();

        int pageWidth = settings.getPage().getWidth();
        reportBody = new StringBuilder(pageWidth);

        generatePageHeader();
        for (DataSourceDto dataSourceListItem : dataSourceList) {
            generateLineBody(dataSourceListItem);
            countRows+= reportLineBody.toString().split("\n").length;
            if (countRows % 12 == 0) {
                reportBody.append("~\n");
                generatePageHeader();
            }
            reportBody.append(reportLineBody);
        }

        //System.out.print(reportBody);
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
    private void generateLineBody(DataSourceDto dataSourceListItem) {

        int pageWidth = settings.getPage().getWidth();
        reportLineBody = new StringBuilder(pageWidth);
        String numDataTmp;
        String dateDataTmp;
        String fullNameDataTmp;

        for (int i = 0; i < pageWidth; i++) {
            reportLineBody.append(LINE_SEPARATOR);
        }
        reportLineBody.append("\n");
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
                reportLineBody.append(COLUMN_SEPARATOR + numDataTmp);
            } else {
                numDataTmp = String.format(" %-" + settings.getColumns().get(0).getWidth() + "s ", dataSourceListItem.getNumber());
                reportLineBody.append(COLUMN_SEPARATOR + numDataTmp);
                dataSourceListItem.setNumber("");
            }
            //Processing Date column
            if (dataSourceListItem.getDate().length() > dateColumnWidth) {
                dateDataTmp = dataSourceListItem.getDate().substring(0, dateColumnWidth);
                dateDataTmp = String.format(" %-" + settings.getColumns().get(1).getWidth() + "s ", dateDataTmp);
                dataSourceListItem.setDate(dataSourceListItem.getDate().substring(dateColumnWidth));
                reportLineBody.append(COLUMN_SEPARATOR + dateDataTmp);
            } else {
                dateDataTmp = String.format(" %-" + settings.getColumns().get(1).getWidth() + "s ", dataSourceListItem.getDate());
                reportLineBody.append(COLUMN_SEPARATOR + dateDataTmp);
                dataSourceListItem.setDate("");

            }
            //Processing FullName column
            if (dataSourceListItem.getFullName().length() > fullNameColumnWidth) {
                dataSourceListItem.setFullName(dataSourceListItem.getFullName().trim());
                fullNameDataTmp = dataSourceListItem.getFullName().substring(0, fullNameColumnWidth);
                fullNameDataTmp = String.format(" %-" + settings.getColumns().get(2).getWidth() + "s ", fullNameDataTmp);
                dataSourceListItem.setFullName(dataSourceListItem.getFullName().substring(fullNameColumnWidth));
                reportLineBody.append(COLUMN_SEPARATOR + fullNameDataTmp + COLUMN_SEPARATOR);
            } else {
                fullNameDataTmp = String.format(" %-" + settings.getColumns().get(2).getWidth() + "s ", dataSourceListItem.getFullName());
                reportLineBody.append(COLUMN_SEPARATOR + fullNameDataTmp + COLUMN_SEPARATOR);
                dataSourceListItem.setFullName("");
            }

            reportLineBody.append("\n");
        }

        dataSourceListItem.setFullName(dataSourceListItem.getFullName().trim());
        if (dataSourceListItem.getNumber().length() != 0 ||
                dataSourceListItem.getDate().length() != 0 ||
                dataSourceListItem.getFullName().length() != 0) {
            reportLineBody.append(COLUMN_SEPARATOR + String.format(" %-" +
                    settings.getColumns().get(0).getWidth() + "s ", dataSourceListItem.getNumber())
                    + COLUMN_SEPARATOR + String.format(" %-" +
                    settings.getColumns().get(1).getWidth() + "s ", dataSourceListItem.getDate())
                    + COLUMN_SEPARATOR + String.format(" %-" +
                    settings.getColumns().get(2).getWidth() + "s ", dataSourceListItem.getFullName())
                    + COLUMN_SEPARATOR + "\n");
        }
    }
}