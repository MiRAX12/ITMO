import Objects.Entity.*;
import Objects.Item.*;
import Enums.Mood;

import static java.lang.Math.*;

public class Main {

    public static void main(String[] args) {
        Malysh malysh = new Malysh("Малыш");
        Karlson karlson = new Karlson("Карлсон");
        Buffet buffet = new Buffet("Буфет");
        Pan pan = new Pan("Кастрюля", (int) (random()*20 + 120));

        karlson.printCurrentMood();
        malysh.takePlateFrom(buffet);

        if (random() < 0.5) {
            System.out.print("Малыш сказал Карлсону, чтобы он сам наложил себе, сколько хочет. ");
            karlson.setMood(Mood.GLOOMY);
            malysh.givePlateTo(karlson);
            karlson.printCurrentMood();

            while (karlson.getHunger() != 0 && pan.getPorridgeAmount()!=0) {
                karlson.putPorridgeFrom(pan);
                karlson.eat();
                if (karlson.getHunger() != 0 && pan.getPorridgeAmount()!=0)
                    karlson.takePlateFrom(buffet);

            }
        }
        else {
            System.out.println("Малыш сказал Карлсону, что он сам ему всё наложит. ");
            karlson.setMood(Mood.HAPPY);
            karlson.printCurrentMood();

            while (karlson.getHunger() != 0 && pan.getPorridgeAmount()!=0) {
                malysh.putPorridgeFrom(pan);
                malysh.givePlateTo(karlson);
                karlson.eat();
                if (karlson.getHunger() != 0 && pan.getPorridgeAmount()!=0)
                    malysh.takePlateFrom(buffet);
            }
        }
    }
}