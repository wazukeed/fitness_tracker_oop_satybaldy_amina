package model;

public abstract class BaseEntity {
    private int id;
    private String name;

    protected BaseEntity(int id, String name) {
        this.id = id;
        setName(name);
    }


    public abstract String getEntityType();
    public abstract String describe();

    public String shortInfo() {
        return "[" + getEntityType() + "] " + id + ": " + name;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) {
        Validatable.requireNonBlank(name, "Name");
        this.name = name.trim();
    }
}

