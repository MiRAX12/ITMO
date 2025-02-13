package Data;

import Interfaces.Validatable;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Worker implements Validatable{
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float salary; //Значение поля должно быть больше 0
    private java.time.LocalDateTime startDate; //Поле не может быть null
    private java.time.ZonedDateTime endDate; //Поле может быть null
    private Status status; //Поле не может быть null
    private Person person; //Поле может быть null
    private static Scanner consoleRead = new Scanner(System.in);

    public static class WorkerBuilder {
        private final Long id = (long)(this.hashCode());
        private String name = "null";
        private Coordinates coordinates = new Coordinates();
        private java.time.LocalDate creationDate = java.time.LocalDate.now();
        private float salary = 1;
        private java.time.LocalDateTime startDate = java.time.LocalDateTime.now();
        private java.time.ZonedDateTime endDate = java.time.ZonedDateTime.now();
        private Status status;
        private Person person = new Person();

        public WorkerBuilder() {
            super();
        }

        public void name(String name){
            while (true) {
                System.out.print("Введите имя: ");
                try {
                    this.name = consoleRead.nextLine().trim();
                    break;
                }catch(NoSuchElementException e) {
                    System.out.println("Ошибка ввода ");
                }
            }
        }

        public void coordinates(Coordinates coordinates){
            this.coordinates = new Coordinates.CoordinatesBuilder().build();
        }

        public void salary(float salary){
            while (true) {
                System.out.print("Введите зарплату: ");
                try {
                    this.salary = Float.parseFloat(consoleRead.nextLine().trim());
                    break;
                }catch(NoSuchElementException | NumberFormatException e) {
                    System.out.println("Ошибка ввода ");
                }
            }
        }

        public WorkerBuilder startDate(java.time.LocalDateTime startDate){
            this.startDate = startDate;
            return this;
        }

        public WorkerBuilder endDate(java.time.ZonedDateTime endDate){
            this.endDate = endDate;
            return this;
        }

        public WorkerBuilder status(Status status){
            status = new Status();
            while (true) {
                System.out.print("Введите Статус: (FIRED, HIRED," +
                        " RECOMMENDED_FOR_PROMOTION, REGULAR, PROBATION)");
                try {
                    this.salary = Float.parseFloat(consoleRead.nextLine().trim());
                    break;
                }catch(NoSuchElementException | NumberFormatException e) {
                    System.out.println("Ошибка ввода ");
                }
            }
        }
        }

        public void person(Person person){
            this.person = new Person.PersonBuilder().build();
        }

        public Worker build(){ //Тут остановка 21:57
            Worker worker = new Worker();
            worker.id = id;
            worker.name = name;
            worker.coordinates = coordinates;
            worker.creationDate = creationDate;
            worker.salary = salary;
            worker.startDate = startDate;
            worker.endDate = endDate;
            worker.status = status;
            worker.person = person;
            return worker;
        }
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
}


