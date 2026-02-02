package model;

import java.util.ArrayList;
import java.util.List;

public class StrengthWorkout extends Workout {
    private int totalSets;
    private int totalReps;

    
    private final List<Exercise> exercises = new ArrayList<>();

    public StrengthWorkout(int id, String name, int durationMinutes, int caloriesBurned, int totalSets, int totalReps) {
        super(id, name, durationMinutes, caloriesBurned);
        this.totalSets = totalSets;
        this.totalReps = totalReps;
    }

    @Override
    public String getWorkoutType() {
        return "STRENGTH";
    }

    @Override
    public String describe() {
        return shortInfo()
                + " | duration=" + durationMinutes
                + " | calories=" + caloriesBurned
                + " | sets=" + totalSets
                + " | reps=" + totalReps
                + " | exercises=" + exercises.size();
    }

    @Override
    public void validate() {
        super.validate();
        if (totalSets <= 0) throw new IllegalArgumentException("Total sets must be > 0");
        if (totalReps <= 0) throw new IllegalArgumentException("Total reps must be > 0");
    }

    public void addExercise(Exercise e) { exercises.add(e); }
    public List<Exercise> getExercises() { return exercises; }

    public int getTotalSets() { return totalSets; }
    public void setTotalSets(int totalSets) { this.totalSets = totalSets; }

    public int getTotalReps() { return totalReps; }
    public void setTotalReps(int totalReps) { this.totalReps = totalReps; }
}
