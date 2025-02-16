package data;

import exceptions.ExitWritten;
import interfaces.Validatable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static data.ParameterConstructor.*;

public class Worker implements Validatable{
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float salary; //Значение поля должно быть больше 0
    private LocalDateTime startDate; //Поле не может быть null
    private ZonedDateTime endDate; //Поле может быть null
    private Status status; //Поле не может быть null
    private Person person; //Поле может быть null
    private static final Scanner consoleRead = new Scanner(System.in);



    public static Worker build() {
        Worker worker = new Worker();
        worker.id = (long) (worker.hashCode());
        worker.name = askParameterString("Введите имя: ");
        worker.coordinates = Coordinates.build();
        worker.creationDate = LocalDate.now();
        worker.salary = askParameterFloat("Введите зарплату: ");
        worker.startDate = askLocalDateTime("Введите дату и время" +
                " в формате 'yyyy-MM-dd HH:mm:ss' (например, '2023-10-05 14:30:00'): ");
        worker.endDate = askZonedDateTime("Введите дату и время" +
                " в формате 'yyyy-MM-dd HH:mm:ss z' (например, '2023-10-05 14:30:00 UTC'): ");
        worker.askStatus();
        worker.person = Person.build();
        return worker;
    }

    public void askStatus() {
        System.out.print("Введите Статус: (FIRED, HIRED," +
                " RECOMMENDED_FOR_PROMOTION, REGULAR, PROBATION): ");
        boolean next = true;
        do {
            try {
                switch (consoleRead.nextLine().trim()) {
                    case ("exit"):
                        throw new ExitWritten("Выход из консоли...");
                    case ("FIRED"):
                        next = false;
                        status = Status.FIRED;
                        break;
                    case ("HIRED"):
                        next = false;
                        status = Status.HIRED;
                        break;
                    case ("RECOMMENDED_FOR_PROMOTION"):
                        next = false;
                        status = Status.RECOMMENDED_FOR_PROMOTION;
                        break;
                    case ("REGULAR"):
                        next = false;
                        status = Status.REGULAR;
                        break;
                    case ("PROBATION"):
                        next = false;
                        status = Status.PROBATION;
                        break;
                    default:
                        System.out.println("Ошибка ввода\nВыберите одно из предоставленных значений");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Пользовательский ввод не обнаружен");
            } catch (IllegalStateException e) {
                System.out.println("Непредвиденная ошибка");
            }
        } while (next);
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


