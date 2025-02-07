package Data;

import Interfaces.Validatable;

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

    public static class WorkerBuilder {
        private Long id = 1L;
        private String name = "null";
        private Coordinates coordinates = new Coordinates();
        private java.time.LocalDate creationDate = java.time.LocalDate.now();
        private float salary = 1;
        private java.time.LocalDateTime startDate = java.time.LocalDateTime.now();
        private java.time.ZonedDateTime endDate = java.time.ZonedDateTime.now();
        private Status status = Status.RECOMMENDED_FOR_PROMOTION;
        private Person person = new Person();

        public WorkerBuilder() {
            super();
        }
        public WorkerBuilder id(Long id){
            this.id = id;
            return this;
        }

        public WorkerBuilder name(String name){
            this.name = name;
            return this;
        }

        public WorkerBuilder coordinates(Coordinates coordinates){
            this.coordinates = coordinates;
            return this;
        }

        public WorkerBuilder creationDate(java.time.LocalDate creationDate){
            this.creationDate = creationDate;
            return this;
        }

        public WorkerBuilder salary(float salary){
            this.salary = salary;
            return this;
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
            this.status = status;
            return this;
        }

        public WorkerBuilder person(Person person){
            this.person = person;
            return this;
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


