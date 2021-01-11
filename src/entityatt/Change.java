package entityatt;

public class Change {
    private long id;
    private long dimension;

    public Change(long id, long dimension) {
        this.id = id;
        this.dimension = dimension;
    }

    /**
     *
     * @param id of the entity to be changed
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @param dimension of the change
     */
    public void setDimension(long dimension) {
        this.dimension = dimension;
    }

    /**
     *
     * @return the id of the entity to be changed
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @return the dimension of the change
     */
    public long getDimension() {
        return dimension;
    }

}

