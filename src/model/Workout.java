package model;

public abstract class Workout extends BaseEntity implements Validatable {
    private int durationMinutes;
    private int caloriesBurned;

    protected Workout(int id, String name, int durationMinutes, int caloriesBurned) {
        super(id, name);
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = caloriesBurned;
    }

    public abstract String getWorkoutType();

    @Override
    public String getEntityType() {
        return "WORKOUT";
    }

    @Override
    public void validate() {
        if (getName() == null || getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Workout name must not be empty");
        }
        if (durationMinutes <= 0 || durationMinutes > 300) {
            throw new IllegalArgumentException("Duration must be 1..300 minutes");
        }
        if (caloriesBurned < 0) {
            throw new IllegalArgumentException("Calories must be >= 0");
        }
    }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public int getCaloriesBurned() { return caloriesBurned; }
    public void setCaloriesBurned(int caloriesBurned) { this.caloriesBurned = caloriesBurned; }
}
