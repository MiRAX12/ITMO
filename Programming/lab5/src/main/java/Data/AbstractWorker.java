package Data;

import Interfaces.Validatable;

public abstract class AbstractWorker implements Comparable<AbstractWorker>, Validatable {

    public abstract Long getId();
}
