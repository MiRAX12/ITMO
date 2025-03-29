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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

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

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder coordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        public Builder salary(float salary) {
            this.salary = salary;
            return this;
        }

        public Builder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(ZonedDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder person(Person person) {
            this.person = person;
            return this;
        }

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

    public Long getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public Status getStatus() {
        return status;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public float getSalary() {
        return salary;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }
}


