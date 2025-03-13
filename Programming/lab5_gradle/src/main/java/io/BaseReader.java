package io;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import data.Worker;
import exceptions.NoSuchEnviromentVariableException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public interface BaseReader {

    Map<Integer, Worker> readFromFile();
}
