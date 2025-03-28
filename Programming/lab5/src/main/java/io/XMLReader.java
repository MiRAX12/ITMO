package io;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import exceptions.IdAlreadyExists;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import model.Worker;
import exceptions.NoSuchEnvironmentVariableException;
import utility.FileConfiguration;
import utility.XmlMapperConfig;
import utility.wrappers.WorkerKeys;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class XMLReader implements BaseReader {
    XmlMapper xmlMapper = new XmlMapperConfig().ConfigureXmlMapper(new XmlMapper());
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Override
    public Map<Integer, Worker> readFromFile() throws Exception {
        if (System.getenv("xml_file") == null) {
            throw new NoSuchEnvironmentVariableException("xml_file");
        }
        Path path = Paths.get(System.getenv("xml_file"));
        FileConfiguration.checkReadFile(path);

        try (BufferedReadWorker bufferedReadWorker = new BufferedReadWorker(path)) {
            String xmlString = bufferedReadWorker.read();
            if (!xmlString.isEmpty()) return parse(xmlString);
        }
        return null;
    }

    private Map<Integer, Worker> parse(String s) throws IOException {
        Map<Integer, Worker> workerMap = new LinkedHashMap<>();
        TreeSet<Long> idTree = new TreeSet<>();
        int damagedWorkers = 0;
        try (JsonParser parser = xmlMapper.getFactory().createParser(s)) {
            parser.nextToken();
            while (parser.nextToken() != JsonToken.END_ARRAY &&
                    parser.currentToken() != null ) {
                if (parser.currentToken() == JsonToken.START_OBJECT) {
                    try {
                        WorkerKeys wk = parser.readValueAs(WorkerKeys.class);
                        Worker worker = wk.getWorker();
                        if (worker != null && validate(wk)) {
                            int key = Integer.parseInt(wk.getKey());
                            if(!idTree.add(worker.getId())){
                                throw new IdAlreadyExists(key);
                            }
                            workerMap.put(Integer.parseInt(wk.getKey()), worker);
                        }

                    } catch (IdAlreadyExists e) {
                        damagedWorkers++;
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        damagedWorkers++;
                        parser.skipChildren();
                    }
                }
            }
        }
        if (damagedWorkers!=0) System.out.println("Пропущено " +
                damagedWorkers + " поврежденных элементов");
        IdGenerator.getInstance().saveId(idTree.last());
        return workerMap;
    }

    public boolean validate(WorkerKeys workerKeys) throws RuntimeException {
            Worker worker = workerKeys.getWorker();
            int key = Integer.parseInt(workerKeys.getKey());
            Set<ConstraintViolation<Worker>> violations = validator.validate(worker);
            if (!violations.isEmpty()) {
                System.out.println("Worker с ключом " + key + " имеет невалидные поля:");
                for (ConstraintViolation<Worker> violation : violations) {
                    System.out.println("'" + violation.getPropertyPath() +
                            "': " + violation.getMessage());
                    throw new RuntimeException();
                }
                return false;
            }
        return true;
    }

}



