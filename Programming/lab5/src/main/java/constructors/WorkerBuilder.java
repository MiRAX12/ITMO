package constructors;

import data.Status;
import data.Worker;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static constructors.ParameterConstructor.*;

//public class WorkerBuilder {
//    private Enum status;
//
//    public static Worker build() throws IOException {
//        ParameterConstructor parameterConstructor = ParameterConstructor.getInstance();
//        Worker worker = new Worker();
//
//        worker.setId((long) (worker.hashCode()));
//        worker.setName(parameterConstructor.askParameter("StringParser",
//                String.class, "Введите имя: "));
//        worker.setCoordinates(CoordinatesBuilder.build());
//        worker.setCreationDate(LocalDate.now());
//        worker.setSalary(parameterConstructor.askParameter("FloatParser",
//                Float.class, "Введите зарплату: "));
//        worker.setStartDate(parameterConstructor.askParameter("LocalDateTimeParser",
//                LocalDateTime.class, "Введите дату и время" +
//                " в формате 'yyyy-MM-dd HH:mm:ss' (например, '2023-10-05 14:30:00'): "));
//        worker.setEndDate(parameterConstructor.askParameter("ZonedDateTimeParser",
//                ZonedDateTime.class, "Введите дату и время" +
//                " в формате 'yyyy-MM-dd HH:mm:ss z' (например, '2023-10-05 14:30:00 UTC'): "));
//        worker.setStatus(parameterConstructor.askParameter("EnumParser",
//                Status.class, "Введите статус (FIRED, HIRED," +
//                " RECOMMENDED_FOR_PROMOTION, REGULAR, PROBATION): "));
//        worker.setPerson(PersonBuilder.build());
//        return worker;
//    }
//
////    public void askStatus() {
////        System.out.print("Введите Статус: (FIRED, HIRED," +
////                " RECOMMENDED_FOR_PROMOTION, REGULAR, PROBATION): ");
////        boolean next = true;
////        do {
////            try {
////                switch (consoleRead.nextLine().trim()) {
////                    case ("exit"):
////                        throw new ExitWritten("Выход из консоли...");
////                    case ("FIRED"):
////                        next = false;
////                        status = Status.FIRED;
////                        break;
////                    case ("HIRED"):
////                        next = false;
////                        status = Status.HIRED;
////                        break;
////                    case ("RECOMMENDED_FOR_PROMOTION"):
////                        next = false;
////                        status = Status.RECOMMENDED_FOR_PROMOTION;
////                        break;
////                    case ("REGULAR"):
////                        next = false;
////                        status = Status.REGULAR;
////                        break;
////                    case ("PROBATION"):
////                        next = false;
////                        status = Status.PROBATION;
////                        break;
////                    default:
////                        System.out.println("Ошибка ввода\nВыберите одно из предоставленных значений");
////                }
////            } catch (NoSuchElementException e) {
////                System.out.println("Пользовательский ввод не обнаружен");
////            } catch (IllegalStateException e) {
////                System.out.println("Непредвиденная ошибка");
////            }
////        } while (next);
////    }
//
//}
