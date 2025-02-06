package Objects.Entity;

import Enums.Mood;
import Interfaces.Moodable;
import Interfaces.PutPorridge;
import Objects.Item.Buffet;
import Objects.Item.Plate;

import java.util.Arrays;
import java.util.Objects;


public abstract class Person implements PutPorridge {
    private final String name;
    private Plate plate;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPlate(Plate plate) {
        this.plate = plate;
    }

    public Plate getPlate() {return this.plate;}

    public void deletePlate(){
        this.plate = null;
    }

    public void takePlateFrom(Buffet buffet) {
        if (buffet.quantityOfPlates()!=0) {
            Plate[] currentBuffet = buffet.getBuffet();
            Plate[] availablePlates = Arrays.stream(currentBuffet)
                                            .filter(Objects::nonNull)
                                            .toArray(Plate[]::new);

            int plateIndex = (int) (Math.random() * buffet.quantityOfPlates());
            Plate takenPlate = availablePlates[plateIndex];
            setPlate(takenPlate);

            int takenPlateIndex = Arrays.asList(currentBuffet).indexOf(takenPlate);
            buffet.deletePlate(takenPlateIndex);
            System.out.println(this.getName() + " вынул из " + buffet.getName()
                    + " " + this.getPlate().type());
        }
        else {
            System.out.println(this.getName() + " хотел взять тарелку," +
                    " но оказалось, что они закончились");
            System.exit(0);
        }
    }
}

