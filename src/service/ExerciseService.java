package service;

import exception.DuplicateResourceException;
import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import model.Exercise;
import repository.ExerciseRepository;
import repository.WorkoutRepository;

public class ExerciseService {

    private final ExerciseRepository exRepo = new ExerciseRepository();
    private final WorkoutRepository workoutRepo = new WorkoutRepository();

    public int createExercise(Exercise e) {
        try {
            e.validate();
        } catch (IllegalArgumentException ex) {
            throw new InvalidInputException(ex.getMessage());
        }
        if (workoutRepo.getById(e.getWorkoutId()) == null) {
            throw new ResourceNotFoundException("Workout id=" + e.getWorkoutId() + " not found (FK)");
        }
        boolean duplicate = exRepo.getByWorkoutId(e.getWorkoutId()).stream()
                .anyMatch(x -> x.getName().equalsIgnoreCase(e.getName()));
        if (duplicate) {
            throw new DuplicateResourceException("Exercise '" + e.getName() + "' already exists in workout " + e.getWorkoutId());
        }

        return exRepo.create(e);
    }

    public void deleteExercise(int id) {
        boolean ok = exRepo.delete(id);
        if (!ok) throw new ResourceNotFoundException("Exercise id=" + id + " not found");
    }
}
