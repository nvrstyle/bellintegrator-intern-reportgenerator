package ru.lubich.bellintegrator.reportgenerator.util;

import ru.lubich.bellintegrator.reportgenerator.dto.DataSourceDto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class TSVParser {

    private DataSourceDto dataSourceDto;
    private Stream<String> dataSourceStream;

    public TSVParser(Stream<String> dataSourceStream){
        this.dataSourceStream = dataSourceStream;
    }

    public List<DataSourceDto> parse() {
        List<DataSourceDto> dataSourceDtoList = new ArrayList<>();
        dataSourceStream.map(s -> s.split("\t"))
                        .forEach(dataSourceItem -> {
                            dataSourceDto = new DataSourceDto();
                            dataSourceDto.setNumber(dataSourceItem[0]);
                            dataSourceDto.setDate(dataSourceItem[1]);
                            dataSourceDto.setFullName(dataSourceItem[2]);
                            dataSourceDtoList.add(dataSourceDto);
                        });
        return dataSourceDtoList;
    }

    //public Map<String,DataSourceDto> parse(){
    //    Map<String, DataSourceDto> dataSourceDtoMap = new LinkedHashMap<>();
    //    dataSourceStream.map(s -> s.split("\t"))
    //                    .forEach(dataSourceItem -> {
    //                        dataSourceDto = new DataSourceDto();
    //                        dataSourceDto.setNumber(dataSourceItem[0]);
    //                        dataSourceDto.setDate(dataSourceItem[1]);
    //                        dataSourceDto.setFullName(dataSourceItem[2]);
    //                        dataSourceDtoMap.put(dataSourceDto.getNumber(), dataSourceDto);
    //                    });
    //    return dataSourceDtoMap;
    //}


}
