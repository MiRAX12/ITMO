package data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Scanner;

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

    public void setId(Long id) {
        if (id==null) throw new IllegalArgumentException("id не может быть null");
        if (id <= 0) throw new IllegalArgumentException("id должен быть больше 0");
        this.id = id;
    }

    public void setName(String name) {
        if (name==null) throw new IllegalArgumentException("name не может быть null");
        if (name.isEmpty()) throw new IllegalArgumentException("Строка не может быть пустой");
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates==null) throw new IllegalArgumentException("coordinates не может быть null");
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDate creationDate) {
        if (creationDate==null) throw new IllegalArgumentException("creationDate не может быть null");
        this.creationDate = creationDate;
    }

    public void setSalary(float salary) {
        if (salary <= 0) throw new IllegalArgumentException("salary должно быть больше 0");
        this.salary = salary;
    }

    public void setStartDate(LocalDateTime startDate) {
        if (startDate==null) throw new IllegalArgumentException("startDate не может быть null");
        this.startDate = startDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public void setStatus(Status status) {
        if (status==null) throw new IllegalArgumentException("status не может быть null");
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


