package ru.lubich.bellintegrator.reportgenerator.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name = "settings")
@XmlType(propOrder = {"page", "columns"})
public class SettingDto {

    private Page page;
    private List<Column> columns;

    public SettingDto(Page page, List<Column> columns) {
        this.page = page;
        this.columns = columns;
    }

    public SettingDto() {}

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<Column> getColumns() {
        return columns;
    }

    @XmlElement(name = "column")
    @XmlElementWrapper
    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public static class Page {

        private int width;
        private int height;

        public Page(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public Page() {}

        public int getWidth() {
            return width;
        }

        @XmlElement(name = "width")
        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        @XmlElement(name = "height")
        public void setHeight(int height) {
            this.height = height;
        }
    }

    public static class Column {

        private String title;
        private int width;

        public Column(String title, int width) {
            this.title = title;
            this.width = width;
        }

        public Column() {}

        public String getTitle() {
            return title;
        }

        @XmlElement(name = "title")
        public void setTitle(String title) {
            this.title = title;
        }

        public int getWidth() {
            return width;
        }

        @XmlElement(name = "width")
        public void setWidth(int width) {
            this.width = width;
        }
    }
}
