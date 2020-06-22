package ru.lubich.bellintegrator.reportgenerator.io;

import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.*;
import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class FileIO {

    protected static final String DEFAULT_CHARSET = "UTF-8";
    protected Charset charset;
    protected String path;

    protected void selectCharset(String charset) {
        switch (charset) {
            case "UTF-16":
                this.charset = UTF_16;
                break;
            case "UTF-16BE":
                this.charset = UTF_16BE;
                break;
            case "UTF-16LE":
                this.charset = UTF_16LE;
                break;
            default:
                this.charset = UTF_8;
                break;
        }
    }

}
