package entityatt;

public class Change {
    long id;
    long dimension;

    public Change(long id, long dimension) {
        this.id = id;
        this.dimension = dimension;
    }

    public long getId() {
        return id;
    }
    public long getDimension(){return dimension;}

}

