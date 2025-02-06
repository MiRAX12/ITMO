package Objects.Entity;

import Exceptions.IHaveNoPlateException;
import Objects.Item.Pan;
import Objects.Item.Plate;
import static java.lang.Math.*;

public class Malysh extends Person {
    public Malysh(String name) {
        super(name);
    }

    public void givePlateTo(Person person) {
        person.setPlate(this.getPlate());
        System.out.println(this.getName() + " дал " + this.getPlate().type()
                + " в руки " + person.getName());
        this.deletePlate();
    }

    @Override
    public void putPorridgeFrom(Pan pan) {
        if (getPlate()==null)
            throw new IHaveNoPlateException("а тарелки то нет, как же без нее?");
        int platePorridgeAmount = 0;
        int plateCapacity = this.getPlate().remainedCapacity();
        String plateType = this.getPlate().type();

        System.out.println(this.getName() + " взял " + pan.getName() +
                " и принялся накладывать кашу в " + plateType);
        while (pan.getPorridgeAmount()>0) {
            if (plateCapacity!=0) {
                int putAmount = min(10, min(pan.getPorridgeAmount(), plateCapacity));
                plateCapacity -= putAmount;
                platePorridgeAmount += putAmount;
                pan.takePorridge(putAmount);
                System.out.println(this.getName() + " накладывал");
            }
            else {
                setPlate(new Plate(plateType, plateCapacity, platePorridgeAmount));
                System.out.println("Тарелка переполнилась");
                break;
            }
        }
        if (pan.getPorridgeAmount()==0)
            System.out.println("Каша в кастрюле закончилась");
    }
}
