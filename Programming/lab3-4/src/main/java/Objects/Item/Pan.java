package Objects.Item;

public class Pan {
    private final String name;
    private int porridgeAmount;

    public Pan(String name, int porridgeAmount) {
        this.name = name;
        this.porridgeAmount = porridgeAmount;
    }

    public String getName(){
        return name;
    }

    public int getPorridgeAmount() {
        return porridgeAmount;
    }

    public void takePorridge(int volume) {
        this.porridgeAmount -= volume;
    }
}
