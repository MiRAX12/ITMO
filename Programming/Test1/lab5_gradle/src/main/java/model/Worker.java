package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Worker {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float salary; //Значение поля должно быть больше 0
    private LocalDateTime startDate; //Поле не может быть null
    private ZonedDateTime endDate; //Поле может быть null
    private Status status; //Поле не может быть null
    private Person person; //Поле может быть null

    public Worker(){
        this.id = IdGenerator.updateID();
        this.creationDate = LocalDate.now();
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
        private String name = null;
        private Coordinates coordinates = null;
        private float salary = 0;
        private LocalDateTime startDate = null;
        private ZonedDateTime endDate = null;
        private Status status = null;
        private Person person = null;

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
            worker.name = this.name;
            worker.coordinates = this.coordinates;
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


