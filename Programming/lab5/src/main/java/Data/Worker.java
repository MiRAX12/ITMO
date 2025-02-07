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

    public static class Builder {
        private Long id;
        private String name = "null";
        private Coordinates coordinates;
        private java.time.LocalDate creationDate;
        private float salary;
        private java.time.LocalDateTime startDate;
        private java.time.ZonedDateTime endDate;
        private Status status;
        private Person person;
    }
        public Worker Id(Long id){
            this.id = id;
            return this;
        }

        public Worker name(String name){
            this.name = name;
            return this;
        }

        public Worker coordinates(Coordinates coordinates){
            this.coordinates = coordinates;
            return this;
        }

        public Worker creationDate(java.time.LocalDate creationDate){
            this.creationDate = creationDate;
            return this;
        }

        public Worker salary(float salary){
            this.salary = salary;
            return this;
        }

        public Worker startDate(java.time.LocalDateTime startDate){
            this.startDate = startDate;
            return this;
        }

        public Worker endDate(java.time.ZonedDateTime endDate){
            this.endDate = endDate;
            return this;
        }

        public Worker status(Status status){
            this.status = status;
            return this;
        }

        public Worker person(Person person){
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

    @Override
    public boolean validate(){
        if (id != null || id <= 0) return false; // auto
        if (name != null || name.isEmpty()) return false;
        if (coordinates != null) return false;
        if (creationDate != null) return false; // auto
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


