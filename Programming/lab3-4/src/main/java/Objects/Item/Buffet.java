package Objects.Item;

import java.util.Arrays;
import java.util.Objects;

public class Buffet {
    private final String name;

    public Buffet(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    private final Plate[] buffet = {
            new Plate("Тарелка", 40,0),
            new Plate("Маленькая тарелка", 20,0),
            new Plate("Большая тарелка", 60,0),
    };

    public Plate[] getBuffet(){
        return buffet;
    }

    public void deletePlate(int index) {
            buffet[index] = null;
    }

    public long quantityOfPlates(){
        return Arrays.stream(buffet)
                     .filter(Objects::nonNull)
                     .count();
    }
}






