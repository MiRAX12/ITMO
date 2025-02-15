package Data;

import Interfaces.Validatable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static Data.ParameterConstructor.*;


public class Worker extends AbstractWorker implements Validatable{
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float salary; //Значение поля должно быть больше 0
    private java.time.LocalDateTime startDate = java.time.LocalDateTime.now(); //Поле не может быть null
    private java.time.ZonedDateTime endDate = java.time.ZonedDateTime.now(); //Поле может быть null
    private Status status; //Поле не может быть null
    private Person person; //Поле может быть null
    private static final Scanner consoleRead = new Scanner(System.in);

    @Override
    public Long getId() { //TODO: проверь херню
        return id;
    }

    @Override
    public int compareTo(AbstractWorker worker) {
        return (int)(this.id - worker.getId());
    }

    public static Worker build() {
        Worker worker = new Worker();
        final Long id = (long) (worker.hashCode());
        worker.name = askParameterString("Введите имя: ");
        worker.coordinates = Coordinates.build();
        final java.time.LocalDate creationDate = java.time.LocalDate.now();
        worker.salary = askParameterFloat("Введите зарплату: ");
        worker.startDate = askLocalDateTime("Введите дату и время" +
                " в формате 'yyyy-MM-dd HH:mm:ss' (например, '2023-10-05 14:30:00'): ");
        worker.endDate = askZonedDateTime("Введите дату и время" +
                " в формате 'yyyy-MM-dd HH:mm:ss z' (например, '2023-10-05 14:30:00 UTC'): ");
        Status status = askParameterEnum("Введите Статус: (FIRED, HIRED," +
                " RECOMMENDED_FOR_PROMOTION, REGULAR, PROBATION)", Status.class);
        worker.person = Person.build();
        return worker;
    }

    @Override
    public boolean validate(){
        if (id != null || id <= 0) return false; // auto
        if (name != null || name.isEmpty()) return false;
        if (coordinates != null) return false;
        if (creationDate != null) return false; // auto
        if (salary <=0) return false;
        if (startDate != null) return false;
        if (status != null) return false;
        return true;
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


