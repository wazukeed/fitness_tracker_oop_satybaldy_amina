package repository.interfaces;

import model.Exercise;
import java.util.List;

public interface ExerciseRepository {
    int create(Exercise e);
    List<Exercise> getByWorkoutId(int workoutId);
    void delete(int id);
}
