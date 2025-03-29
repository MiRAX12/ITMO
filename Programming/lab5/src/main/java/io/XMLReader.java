package io;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import exceptions.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import model.Worker;
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

/**
 * XML implementation of the {@link BaseReader} interface. It reads XML file by given path
 * and provides <code>Integer</code>/<code>Worker</code> map.
 *
 * @author Mirax
 * @see BaseReader
 * @since 1.0
 */
public class XMLReader implements BaseReader {
    XmlMapper xmlMapper = new XmlMapperConfig().ConfigureXmlMapper(new XmlMapper());
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    /**
     * Reads string of data from XML file containing workers and its keys using {@link XmlMapper}
     * and provides it as a map interpretation or null if file was empty, or it was not able to parse/
     * <p>
     * Path to XML file is taken from environment variable.
     * {@link XmlMapperConfig} is used to perform configuration of {@link XmlMapper}.
     * </p>
     * <p>
     * It uses {@link BufferedReadWorker} to read file as a string. Then the string is
     * parsed to map using {@code parse}.
     * </p>
     *
     * <p>If any exception occurs, its message is printed to the console and method returns null.
     *
     * @return A map of workers
     * @throws NoSuchEnvironmentVariableException if environment variable wasn't found
     * @throws FileNotExistsException             if file does not exist
     * @throws NotAFileException                  if path does not lead to a file
     * @throws NoReadPermissionException          if reading is prohibited
     * @see XmlMapper
     * @see BufferedReadWorker
     * @see XmlMapperConfig
     * @since 1.0
     */
    @Override
    public Map<Integer, Worker> readFromFile() throws Exception {
        if (System.getenv("xml_file") == null) {
            throw new NoSuchEnvironmentVariableException("xml_file");
        }
        Path path = Paths.get(System.getenv("xml_file"));
        FileConfiguration.checkReadFile(path);

        try (BaseReadWorker bufferedReadWorker = new BufferedReadWorker(path)) {
            String xmlString = bufferedReadWorker.read();
            if (!xmlString.isEmpty()) return parse(xmlString);
        }
        return null;
    }

    /**
     * Parses {@link WorkerKeys} one by one from a string of XML data
     * using overridden {@link JsonParser}, finding max id from collection to set as current
     * and provides workers as a map interpretation or null if it wasn't able to parse
     * <p>
     * {@code JsonParser} iterates string by tokens and parses {@code WorkerKeys} instances.
     * If exception during parsing occurred, advances to the next instance.
     * </p>
     * <p>
     * It uses {@code TreeSet} to collect unique ID and throws an exception when trying to add
     * existing ID. Validation of workers is performed by {@link #validate(WorkerKeys)} method
     * </p>
     *
     * @param s The input string containing XML data to parse
     * @return A map of workers
     * @see JsonParser
     * @see IdGenerator
     * @since 1.0
     */
    private Map<Integer, Worker> parse(String s) throws IOException {
        Map<Integer, Worker> workerMap = new LinkedHashMap<>();
        TreeSet<Long> idTree = new TreeSet<>();
        int damagedWorkers = 0;
        try (JsonParser parser = xmlMapper.getFactory().createParser(s)) {
            parser.nextToken();
            while (parser.nextToken() != JsonToken.END_ARRAY &&
                    parser.currentToken() != null) {
                if (parser.currentToken() == JsonToken.START_OBJECT) {
                    try {
                        WorkerKeys wk = parser.readValueAs(WorkerKeys.class);
                        Worker worker = wk.getWorker();
                        if (worker != null && validate(wk)) {
                            int key = Integer.parseInt(wk.getKey());
                            if (!idTree.add(worker.getId())) {
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
        if (damagedWorkers != 0) System.out.println("Пропущено " +
                damagedWorkers + " поврежденных элементов");
        IdGenerator.getInstance().saveId(idTree.last());
        return workerMap;
    }


    /**
     * Validates {@link WorkerKeys} using Jakarta {@link Validator}
     * <p>
     * Ignores violations from fields of inner objects {@code Person} if it does not exist.
     * If Violations were found, writes a worker, her/his key and invalid fields.
     * </p>
     *
     * @param workerKeys A workerKeys instance for validation
     * @return {@code true} if no violations were found, {@code false} otherwise
     * @see Validator
     * @since 1.0
     */
    private boolean validate(WorkerKeys workerKeys) throws RuntimeException {
        Worker worker = workerKeys.getWorker();
        int key = Integer.parseInt(workerKeys.getKey());
        Set<ConstraintViolation<Worker>> violations = validator.validate(worker);
        if (worker.getPerson().getPassportID() == null && worker.getPerson().getLocation() == null) {
            violations.removeIf(v ->
                    v.getPropertyPath().toString().startsWith("person"));
        }
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



