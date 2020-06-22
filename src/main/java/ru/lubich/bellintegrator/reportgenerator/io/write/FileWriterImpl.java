package ru.lubich.bellintegrator.reportgenerator.io.write;

import ru.lubich.bellintegrator.reportgenerator.io.FileIO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileWriterImpl extends FileIO {

    private OutputStreamWriter outputStreamWriter;

    public FileWriterImpl(String path) {
        // Set default charset
        this(path, DEFAULT_CHARSET);
    }

    public FileWriterImpl(String path, String charset) {
        this.path = path;
        selectCharset(charset);
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        selectCharset(charset);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

   public void writeString(String content) throws IOException {
       outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path), charset);
       outputStreamWriter.write(content);
       outputStreamWriter.flush();
   }
}
