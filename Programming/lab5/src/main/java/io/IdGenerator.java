package io;

import exceptions.NoSuchEnvironmentVariableException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class IdGenerator {
    private static IdGenerator instance;
    private Long currentId;
    private Path path;

    private IdGenerator() {
        readId();
    }

    public static IdGenerator getInstance() {
        return instance == null ? instance = new IdGenerator() : instance;
    }

    public Long generateId(){
        currentId++;
        saveId(currentId);
        return currentId;
    }

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

    protected void saveId(Long data){
        try(BaseWriteWorker writer = new BufferedWriteWorker(path)) {
            writer.write(Long.toString(data));
            if (currentId != (long) data) this.currentId = data;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Long getCurrentId() {
        return currentId;
    }

}
