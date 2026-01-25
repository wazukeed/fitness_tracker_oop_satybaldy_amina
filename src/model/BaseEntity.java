package model;

public abstract class BaseEntity {
    private int id;
    private String name;

    protected BaseEntity(int id, String name) {
        this.id = id;
        setName(name);
    }

    // abstract methods (минимум 2)
    public abstract String getEntityType();
    public abstract String describe();

    // concrete method
    public String shortInfo() {
        return "[" + getEntityType() + "] " + id + ": " + name;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        this.name = name.trim();
    }
}
