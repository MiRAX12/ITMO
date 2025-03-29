package io;

import exceptions.NoSuchEnvironmentVariableException;
import utility.Request;

import java.io.BufferedReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * A singleton class which generates
 * unique ID and uses a text file to collect current ID.
 *
 * @author Mirax
 *
 * @since 1.0
 */
public class IdGenerator {
    private static IdGenerator instance;
    private Long currentId;
    private Path path;

    /**
     * Private constructor for initializing the {@code IdGenerator} and current ID.
     */
    private IdGenerator() {
        readId();
    }

    /**
     * Returns the singleton instance of the {@code IdGenerator}.
     * <p>
     * If the instance does not already exist, it is created. Otherwise, the existing
     * instance is returned.
     * </p>
     *
     * @return the singleton instance of {@code IdGenerator}
     */
    public static IdGenerator getInstance() {
        return instance == null ? instance = new IdGenerator() : instance;
    }

    /**
     * Generates a new unique ID and saves it to the file.
     * <p>
     * The method increments the current ID, saves it to the file, and then returns
     * the new value.
     * </p>
     *
     * @return newly generated unique ID
     */
    public Long generateId(){
        currentId++;
        saveId(currentId);
        return currentId;
    }

    /**
     * Reads ID from text file
     * <p>
     * Path to file is taken from environment variable.
     * </p>
     * <p>
     * If file contains ID, reads it and sets it as current ID, otherwise resets current ID to 0.
     * If file does not contain a parsable number, resets current ID to 0.
     * </p>
     *
     */
    private void readId(){
        if (System.getenv("id_collector") == null) {
            throw new NoSuchEnvironmentVariableException("id_collector");
        }
        this.path = Paths.get(System.getenv("id_collector"));

        try(BaseReadWorker reader = new BufferedReadWorker(path)) {
            String lastId = reader.read();
            currentId = lastId != null ? Long.parseLong(lastId) : 0;

        } catch (NumberFormatException e){
            saveId(currentId = 0L);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Takes id and writes it to the file from environment variable using {@link BufferedReadWorker}
     * If {@code currentID} is different from the ID to be saved, sets it as new current ID.
     *
     * @param id An ID to be saved
     * @see BufferedReadWorker
     */
    protected void saveId(Long id){
        try(BaseWriteWorker writer = new BufferedWriteWorker(path)) {
            writer.write(Long.toString(id));
            if (currentId != (long) id) this.currentId = id;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns current ID
     */
    public Long getCurrentId() {
        return currentId;
    }

}
