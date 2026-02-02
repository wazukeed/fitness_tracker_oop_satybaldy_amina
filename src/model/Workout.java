package model;

public abstract class Workout extends BaseEntity implements Validatable {

    protected int durationMinutes;
    protected int caloriesBurned;

    protected Workout(int id, String name, int durationMinutes, int caloriesBurned) {
        super(id, name);
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = caloriesBurned;
    }

    public abstract String getWorkoutType(); 

    @Override
    public String getEntityType() {
        return getWorkoutType();
    }

    @Override
    public void validate() {
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("Duration must be > 0");
        }
        if (caloriesBurned < 0) {
            throw new IllegalArgumentException("Calories must be >= 0");
        }
        Validatable.requireNonBlank(getName(), "Name");
    }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public int getCaloriesBurned() { return caloriesBurned; }
    public void setCaloriesBurned(int caloriesBurned) { this.caloriesBurned = caloriesBurned; }
}
