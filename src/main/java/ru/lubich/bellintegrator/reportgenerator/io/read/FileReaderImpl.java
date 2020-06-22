package ru.lubich.bellintegrator.reportgenerator.io.read;

import ru.lubich.bellintegrator.reportgenerator.io.FileIO;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import static java.nio.charset.StandardCharsets.*;

public class FileReaderImpl extends FileIO {

    public FileReaderImpl(String path) {
        // Set default charset
        this(path, DEFAULT_CHARSET);
    }

    public FileReaderImpl(String path, String charset) {
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

    public File readFile() {
        return new File(path);
    }

    public Stream<String> readStream() throws IOException {
        Stream<String> dataStream = Files.lines(Paths.get(path), charset);
        return dataStream;
    }

}
