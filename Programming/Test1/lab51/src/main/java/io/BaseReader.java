package io;

import data.Worker;

import java.util.Map;

public interface BaseReader {

    Map<Integer, Worker> readFromFile();
}
