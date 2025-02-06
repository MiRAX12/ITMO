package Objects.Entity;

import Enums.Mood;
import Exceptions.IHaveNoPlateException;
import Exceptions.PlateIsEmpty;
import Interfaces.CanEat;
import Interfaces.Moodable;
import Objects.Item.Pan;
import Objects.Item.Plate;

import static java.lang.Math.*;

public class Karlson extends Person implements Moodable, CanEat {
    public Karlson(String name) {
        super(name);
    }
    private Mood mood = Mood.SAD;
    private int hunger = (int) (random()*140);

    @Override
    public void setMood(Mood mood) { this.mood = mood; }

    @Override
    public void printCurrentMood(){
        switch (mood){
            case HAPPY:
                System.out.println(getName() + " был счастлив. ");
                break;
            case SAD:
                System.out.println(getName() + " было не до смеха. ");
                break;
            case GLOOMY:
                System.out.println("Всё ещё мрачный как туча, ");
        }
    }

    public int getHunger() {
        return hunger;
    }

    @Override
    public void putPorridgeFrom(Pan pan) {
        if (getPlate()==null)
            throw new IHaveNoPlateException("а тарелки то нет, как же без нее?");
        int plateCapacity = getPlate().remainedCapacity();
        int platePorridgeAmount = 0;
        String plateType = getPlate().type();

        System.out.print(this.getName() + " взял " + pan.getName() +
                " и принялся накладывать кашу в " + plateType);
        while (hunger>0 && pan.getPorridgeAmount()>0) {
            if (plateCapacity!=0) {
                int porridgeAmount = min(min(10, hunger), min(pan.getPorridgeAmount(), plateCapacity));
                plateCapacity -= porridgeAmount;
                platePorridgeAmount += porridgeAmount;
                pan.takePorridge(porridgeAmount);
                System.out.println(this.getName() + " накладывал");
            }
            else {
                setPlate(new Plate(plateType, plateCapacity, platePorridgeAmount));
                System.out.println("Тарелка переполнилась");
                break;
            }
        }
        if (pan.getPorridgeAmount()==0)
            System.out.println("А когда выскреб все до дна" +
                    " стал водить указательным пальцем по стенке кастрюли," +
                    " отколупывая то, что прилипло");
    }

    @Override
    public void eat(){
        int platePorridgeAmount = getPlate().porridgeAmount();
        if (platePorridgeAmount==0)
                throw new PlateIsEmpty(" в тарелке нет каши ");
        if (hunger > 0) {
            hunger -= min(platePorridgeAmount, hunger);
            if (hunger==0)
                System.out.println("Карлсон наелся кашей");
            else
                System.out.println("Карлсон съел всю кашу из тарелки, однако не наелся");
        }
    }
}

