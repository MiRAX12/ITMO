package model;

import io.IdGenerator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Worker class.
 */
public class Worker {
    @NotNull(message = "id не может быть null")
    @Positive(message = "id должно быть больше 0")
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @NotNull(message = "Имя не может быть null")
    @NotEmpty(message = "Имя не может быть пустым")
    private String name; //Поле не может быть null, Строка не может быть пустой
    @Valid
    @NotNull(message = "coordinates не может быть null")
    private Coordinates coordinates; //Поле не может быть null
    @NotNull(message = "creationDate не может быть null")
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Positive(message = "Salary должно быть больше 0")
    private float salary; //Значение поля должно быть больше 0
    @NotNull(message = "startDate не может быть null")
    private LocalDateTime startDate; //Поле не может быть null
    private ZonedDateTime endDate; //Поле может быть null
    @NotNull(message = "status не может быть null")
    private Status status; //Поле не может быть null
    @Valid
    private Person person; //Поле может быть null

    public Worker(){
    }

    /**
     * set id
     *
     * @param id worker's id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * set name
     *
     * @param name worker's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set coordinates
     *
     * @param coordinates Coordinates instance
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * set creationDate
     *
     * @param creationDate worker's creationDate
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * set salary
     *
     * @param salary worker's salary
     */
    public void setSalary(float salary) {
        this.salary = salary;
    }

    /**
     * set startDate
     *
     * @param startDate worker's startDate
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * set endDate
     *
     * @param endDate worker's endDate
     */
    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * set status
     *
     * @param status worker's status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * set person
     *
     * @param person worker's person
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", salary=" + salary +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", person=" + person +
                '}';
    }

    /**
     * A Builder's pattern
     */
    public static class Builder {
        private Long id = IdGenerator.getInstance().generateId();
        private String name = null;
        private Coordinates coordinates = null;
        private LocalDate creationDate = LocalDate.now();
        private float salary = 0;
        private LocalDateTime startDate = null;
        private ZonedDateTime endDate = null;
        private Status status = null;
        private Person person;

        /**
         * @param name - Worker's name
         *
         * @return - builder's object of written value
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @param coordinates instance of coordinates
         *
         * @return - builder's object of written value
         */
        public Builder coordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        /**
         * @param salary - Worker's salary
         *
         * @return - builder's object of written value
         */
        public Builder salary(float salary) {
            this.salary = salary;
            return this;
        }

        /**
         * @param startDate - startDate of Worker
         *
         * @return - builder's object of written value
         */
        public Builder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        /**
         * @param endDate - endDate of Worker
         *
         * @return - builder's object of written value
         */
        public Builder endDate(ZonedDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        /**
         * @param status - status of worker
         *
         * @return - builder's object of written value
         */
        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        /**
         * @param person - person instance
         *
         * @return - builder's object of written value
         */
        public Builder person(Person person) {
            this.person = person;
            return this;
        }

        /**
         * Build a Worker instance
         *
         * @return {@link Worker} instance
         */
        public Worker build() {
            Worker worker = new Worker();
            worker.id = this.id;
            worker.name = this.name;
            worker.coordinates = this.coordinates;
            worker.creationDate = this.creationDate;
            worker.salary = this.salary;
            worker.startDate = this.startDate;
            worker.endDate = this.endDate;
            worker.status = this.status;
            worker.person = this.person;
            return worker;
        }
    }

    /**
     * get id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * get person
     *
     * @return person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * get status
     *
     * @return status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * get endDate
     *
     * @return endDate
     */
    public ZonedDateTime getEndDate() {
        return endDate;
    }

    /**
     * get startDate
     *
     * @return StartDate
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * get salary
     *
     * @return salary
     */
    public float getSalary() {
        return salary;
    }

    /**
     * get creationDate.
     *
     * @return creationDate
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * get coordinates.
     *
     * @return coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }
}


