package io;

import model.Worker;
import java.util.Map;

public interface BaseReader {

    Map<Integer, Worker> readFromFile();
}
