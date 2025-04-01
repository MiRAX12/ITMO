package io;

/**
 * A singleton class which generates unique ID
 *
 * @author Mirax
 *
 * @since 1.0
 */
public class IdGenerator {
    private static IdGenerator instance;
    private Long currentId = 0L;

    /**
     * Private constructor to prevent instantiation
     */
    private IdGenerator() {
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
     * Generates a new unique ID
     * <p>
     * The method increments the current ID and then returns
     * the new value.
     * </p>
     *
     * @return newly generated unique ID
     */
    public Long generateId(){
        currentId++;
        return currentId;
    }

    /**
     * Takes id and sets it as new current ID.
     *
     * @param id An ID to be saved
     *
     */
    protected void setId(Long id){
        this.currentId = id;
    }

    /**
     * Returns current ID
     */
    public Long getCurrentId() {
        return currentId;
    }

}
