package model;

public class Exercise extends BaseEntity implements Validatable {
    private int workoutId; // FK на workouts.id
    private int sets;
    private int reps;

    public Exercise(int id, String name, int workoutId, int sets, int reps) {
        super(id, name);
        this.workoutId = workoutId;
        this.sets = sets;
        this.reps = reps;
    }

    @Override
    public String getEntityType() {
        return "EXERCISE";
    }

    @Override
    public String describe() {
        return shortInfo() + " | workoutId=" + workoutId + " | sets=" + sets + " | reps=" + reps;
    }

    @Override
    public void validate() {
        if (workoutId <= 0) throw new IllegalArgumentException("workoutId must be > 0");
        if (sets <= 0 || sets > 50) throw new IllegalArgumentException("sets must be 1..50");
        if (reps <= 0 || reps > 200) throw new IllegalArgumentException("reps must be 1..200");
    }

    public int getWorkoutId() { return workoutId; }
    public void setWorkoutId(int workoutId) { this.workoutId = workoutId; }

    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }
}
