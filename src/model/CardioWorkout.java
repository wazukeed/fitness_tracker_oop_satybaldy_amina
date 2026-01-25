package model;

public class CardioWorkout extends Workout {
    private double distanceKm;

    public CardioWorkout(int id, String name, int durationMinutes, int caloriesBurned, double distanceKm) {
        super(id, name, durationMinutes, caloriesBurned);
        this.distanceKm = distanceKm;
    }

    @Override
    public String getWorkoutType() {
        return "CARDIO";
    }

    @Override
    public String describe() {
        return shortInfo() + " | type= " + getWorkoutType()
                + " | duration= " + getDurationMinutes()
                + " | calories= " + getCaloriesBurned()
                + " | distanceKm= " + distanceKm;
    }

    @Override
    public void validate() {
        super.validate();
        if (distanceKm <= 0) {
            throw new IllegalArgumentException("Distance must be > 0");
        }
    }

    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }
}
